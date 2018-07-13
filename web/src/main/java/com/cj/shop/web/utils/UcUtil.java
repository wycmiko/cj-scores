package com.cj.shop.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.cj.shop.common.utils.HttpClientUtils;
import com.cj.shop.ucapi.client.IClient;
import com.cj.shop.ucapi.message.*;
import com.cj.shop.web.cfg.IClientManager;
import com.cj.shop.web.consts.ResultConsts;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;


/**
 * 校验请求Token
 *
 * @author yuchuanWeng( )
 * @date 2018/4/3
 * @since 1.0
 */
@Component
public class UcUtil {

    @Value("${cjmatch.user-app-id}")
    public String userAppId;

    @Value("${wechat.app-id}")
    public String appId;
    @Value("${wechat.redirect-uri}")
    public String redirectUri;
    @Value("${wechat.app-secret}")
    public String appSecret;

    @Autowired
    private IClientManager iClientBuilder;

    /**
     * 校验Token是否合法
     *
     * @param token
     * @return
     * @throws Exception
     */
    public boolean checkToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        try {
            Result<YesOrNoDto> dtoResult = iClient.checkToken(token);
            if (dtoResult.getObj() != null) {
                return dtoResult.getObj().isYN();
            }
        } finally {
            //远程校验
            pool.returnObject(iClient);
        }
        return false;
    }


    /**
     * 根据Token获取UserDto对象
     *
     * @param token
     * @return
     * @throws Exception
     */
    public UserDto getByToken(String token) throws Exception {
        //远程校验
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        try {
            Result<UserDto> client = iClient.getByToken(token);
            if (client != null) {
                return client.getObj();
            }
        } finally {
            pool.returnObject(iClient);
        }
        return new UserDto();
    }

    /**
     * 根据UserDto获取Uid
     *
     * @param userDto
     * @return
     * @throws Exception
     */
    public int getUidByUserDto(UserDto userDto) throws Exception {
        //远程校验
        if (userDto != null) {
            return (int) userDto.getUid();
        }
        return -1;
    }

    /**
     * 根据Token获取Uid
     *
     * @param token
     * @return
     * @throws Exception
     */
    public long getUidByToken(String token) throws Exception {
        //远程校验
        UserDto userDto = getByToken(token);
        return getUidByUserDto(userDto);
    }


    /**
     * token校验失败返回Result
     *
     * @return
     */
    public com.cj.shop.web.dto.Result invaildTokenFailedResult() {
        com.cj.shop.web.dto.Result result = new com.cj.shop.web.dto.Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.TOKEN_FAILURE_MSG);
        result.setData(ResultConsts.TOKEN_FAILURE);
        return result;
    }


    /**
     * （微信）用户登入 通过openId
     */
    public Result<TokenDto> oauthSignIn(OAuthSignInRequest req) throws Exception {
        //远程校验
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        Result<TokenDto> tokenDtoResult = null;
        try {
            tokenDtoResult = iClient.oauthSignIn(req);
        } finally {
            pool.returnObject(iClient);
        }
        return tokenDtoResult;
    }

    public Result<UserDto> oauthRegister(OAuthRegisterRequest req) throws Exception {
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        Result<UserDto> tokenDtoResult = null;
        try {
            tokenDtoResult = iClient.oauthRegister(req);
        } finally {
            pool.returnObject(iClient);
        }
        return tokenDtoResult;
    }

    public Result<YesOrNoDto> oauthBindMobile(OAuthBindMobileRequest req) throws Exception {
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        Result<YesOrNoDto> tokenDtoResult = null;
        try {
            tokenDtoResult = iClient.oauthBindMobile(req);
        } finally {
            pool.returnObject(iClient);
        }
        return tokenDtoResult;
    }

    /**
     * 手机号登入
     * @param req
     * @return
     * @throws Exception
     */
    public Result<TokenDto> signIn(SignInRequest req) throws Exception {
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        Result<TokenDto> tokenDtoResult = null;
        try {
            tokenDtoResult = iClient.signIn(req);
        } finally {
            pool.returnObject(iClient);
        }
        return tokenDtoResult;
    }

    /**
     * 刷新token
     * @param req
     * @return
     * @throws Exception
     */
    public Result<TokenRequest> refreshToken(TokenRequest req) throws Exception {
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        Result<TokenRequest> dtoResult = null;
        try {
            dtoResult = iClient.refreshToken(req);
        } finally {
            pool.returnObject(iClient);
        }
        return dtoResult;
    }

    /**
     * 用户是否存在
     * @return
     * @throws Exception
     */
    public ExistUserResponse isExistUser(String mobile) throws Exception {
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        Result<ExistUserResponse> tokenDtoResult = null;
        ExistUserResponse obj = null;
        try {
            UserNameRequest req = new UserNameRequest();
            req.setUserName(mobile);
            tokenDtoResult = iClient.isExistUser(req);
            obj = tokenDtoResult.getObj();
        } finally {
            pool.returnObject(iClient);
        }
        return obj;
    }

    /**
     * 密码重置
     * @param req
     * @return
     * @throws Exception
     */
    public Result setPassword(SetPasswordRequest req) throws Exception {
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        Result tokenDtoResult = null;
        try {
            tokenDtoResult = iClient.setPassword(req);
        } finally {
            pool.returnObject(iClient);
        }
        return tokenDtoResult;
    }

    /**
     * 手机注册
     * @param req
     * @return
     * @throws Exception
     */
    public Result<UserDto> register(UserNameRegisterRequest req) throws Exception {
        GenericObjectPool<IClient> pool = iClientBuilder.getPool();
        IClient iClient = pool.borrowObject();
        Result<UserDto> tokenDtoResult = null;
        try {
            tokenDtoResult = iClient.register(req);
        } finally {
            pool.returnObject(iClient);
        }
        return tokenDtoResult;
    }




    /**
     * 获取微信access_token
     * <p>
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     */
    public JSONObject getAccessToken(String code) throws IOException {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSecret
                + "&code=" + code + "&grant_type=authorization_code";
        String s = HttpClientUtils.httpGetData(url);
        //返回获取Code错误提示信息
        return JSONObject.parseObject(s);
    }


    /**
     * 获取微信用户个人信息
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     */
    public JSONObject getWeChatUserInfo(String access_token, String open_id) throws IOException {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + open_id + "&lang=zh_CN";
        String data = HttpClientUtils.httpGetData(url);
        return JSONObject.parseObject(data);
    }
}
