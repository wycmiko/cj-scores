package com.cj.scores.web.validator;

import com.cj.scores.api.consts.ResultConsts;
import com.cj.scores.web.cfg.IClientManager;
import com.cj.ucapi.client.IClient;
import com.cj.ucapi.message.Result;
import com.cj.ucapi.message.TokenRequest;
import com.cj.ucapi.message.UserDto;
import com.cj.ucapi.message.YesOrNoDto;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

//import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * 校验请求Token
 *
 * @author yuchuanWeng( )
 * @date 2018/4/3
 * @since 1.0
 */
@Component
public class TokenValidator {

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
    public com.cj.scores.api.pojo.Result invaildTokenFailedResult() {
        com.cj.scores.api.pojo.Result result = new com.cj.scores.api.pojo.Result(ResultConsts.REQUEST_FAILURE_STATUS, ResultConsts.TOKEN_FAILURE_MSG);
        result.setData(ResultConsts.TOKEN_FAILURE);
        return result;
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
}
