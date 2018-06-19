package com.cj.shop.ucapi.client;

import com.alibaba.fastjson.JSON;
import com.cj.shop.ucapi.cfg.UcApiConfig;
import com.cj.shop.ucapi.cloud.CloudServiceInstance;
import com.cj.shop.ucapi.cloud.ConsulCloudServiceDiscovery;
import com.cj.shop.ucapi.cloud.route.RibbonAlgorithm;
import com.cj.shop.ucapi.cloud.route.RouterAlgorithm;
import com.cj.shop.ucapi.encrypt.AESUtil;
import com.cj.shop.ucapi.grpc.User;
import com.cj.shop.ucapi.grpc.UserServiceGrpc;
import com.cj.shop.ucapi.message.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.cj.shop.ucapi.message.Status.CONNECT_FAIL;


/**
 * <p>Create Time: 2018年03月21日</p>
 * <p>@author tangxd</p>
 **/
@Service
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Client implements IClient {

    @Autowired
    private UcApiConfig ucConfig;

    @Autowired
    private ConsulCloudServiceDiscovery cloudServiceDiscovery;

    private ManagedChannel channel;

    private UserServiceGrpc.UserServiceBlockingStub stub;

    @Override
    public void init() {
        CloudServiceInstance instance = this.getServiceAddress(ucConfig.getServiceName());
        if (instance != null) {
            channel = ManagedChannelBuilder.forAddress(instance.getHost(), instance.getPort())
                    .usePlaintext(true)
                    .build();
            stub = UserServiceGrpc.newBlockingStub(channel);
        }
    }

    private final static Map<String, RouterAlgorithm> ROUTERS = new ConcurrentHashMap<>();

    private CloudServiceInstance getServiceAddress(String serviceName) {
        RouterAlgorithm router;
        if (!ROUTERS.containsKey(serviceName)) {
            router = new RibbonAlgorithm(cloudServiceDiscovery, serviceName);
            ROUTERS.put(serviceName, router);
        } else {
            router = ROUTERS.get(serviceName);
        }
        CloudServiceInstance instance = router.get();
        if (null == instance) {
            return null;
        }
        return instance;
    }

    @Override
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private String encrypt(String body) {
        try {
            return AESUtil.encrypt(body, ucConfig.getSecret());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    private String decrypt(String body) {
        try {
            return AESUtil.decrypt(body, ucConfig.getSecret());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private <T> Result<T> transformResult(User.Result r, T obj) {
        Status status = Status.getEnum(r.getStatusValue());
        return new Result<>(status, r.getMessage(), obj);
    }

    private User.Request buildRequest(String body) {
        User.Request request = User.Request.newBuilder()
                .setAppId(ucConfig.getAppId())
                .setBody(encrypt(body))
                .build();
        return request;
    }

    @Override
    public Result<UserDto> getUser(long uid) {
        UidRequest req = new UidRequest();
        req.setUid(uid);
        return request(req, UserDto.class, r -> stub.getByUid(r));
    }

    @Override
    public Result<UserDto> getByToken(String token) {
        TokenRequest req = new TokenRequest();
        req.setToken(token);
        return request(req, UserDto.class, r -> stub.getByToken(r));
    }

    @Override
    public Result<ExistUserResponse> isExistUser(UserNameRequest req) {
        return request(req, ExistUserResponse.class, r -> stub.isExist(r));
    }

    @Override
    public Result<TokenDto> signIn(SignInRequest req) {
        return request(req, TokenDto.class, r -> stub.signIn(r));
    }

    @Override
    public Result<TokenDto> oauthSignIn(OAuthSignInRequest req) {
        return request(req, TokenDto.class, r -> stub.oAuthSignIn(r));
    }

    @Override
    public Result<UserDto> register(UserNameRegisterRequest req) {
        return request(req, UserDto.class, r -> stub.register(r));
    }

    @Override
    public Result<UserDto> oauthRegister(OAuthRegisterRequest req) {
        return request(req, UserDto.class, r -> stub.oAuthRegister(r));
    }

    @Override
    public Result<YesOrNoDto> checkToken(String token) {
        TokenRequest req = new TokenRequest();
        req.setToken(token);
        return request(req, YesOrNoDto.class, r -> stub.checkToken(r));
    }

    @Override
    public Result<YesOrNoDto> oauthBindMobile(OAuthBindMobileRequest req) {
        return request(req, YesOrNoDto.class, r -> stub.oAuthBindMobile(r));
    }

    @Override
    public Result<Object> setPassword(SetPasswordRequest req) {
        HandleRequest handleReq = buildHandleRequest("SetPasswordHandle", req);
        return request(handleReq, Object.class, r -> stub.handle(r));
    }

    @Override
    public Result<Object> refreshToken(TokenRequest req) {
        HandleRequest handleReq = buildHandleRequest("RefreshToken", req);
        return request(handleReq, Object.class, r -> stub.handle(r));
    }

    private <T> HandleRequest buildHandleRequest(String handleName, T obj) {
        HandleRequest req = new HandleRequest();
        req.setHandle(handleName);
        req.setBody(JSON.toJSONString(obj));
        return req;
    }

    private synchronized <T, R> Result<R> request(T req, Class<R> clazz, Function<User.Request, User.Result> call) {
        User.Request request = buildRequest(JSON.toJSONString(req));
        User.Result response;
        try {
            response = call.apply(request);
            if (response.getStatus() == User.Result.Status.SUCCESS) {
                String str = decrypt(response.getBody());
                R dto = JSON.parseObject(str, clazz);
                return transformResult(response, dto);
            }
            return transformResult(response, null);
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {0}", e.getStatus());
            this.resetStub();
        }
        return new Result<>(CONNECT_FAIL, "连接失败", null);
    }

    private void resetStub() {
        try {
            this.channel.shutdownNow();
            this.init();
        } catch (StatusRuntimeException e) {
            log.warn("stub failed: {0}", e.getStatus());
        }
    }
}
