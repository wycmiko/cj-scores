package com.cj.shop.ucapi.client;

import com.cj.shop.ucapi.message.*;

/**
 * <p>Create Time: 2018年03月21日</p>
 * <p>@author tangxd</p>
 **/
public interface IClient {
    void init();

    void shutdown() throws InterruptedException;

    Result<UserDto> getUser(long uid);

    Result<UserDto> getByToken(String token);

    Result<ExistUserResponse> isExistUser(UserNameRequest req);

    Result<TokenDto> signIn(SignInRequest req);

    Result<TokenDto> oauthSignIn(OAuthSignInRequest req);

    Result<UserDto> register(UserNameRegisterRequest req);

    Result<UserDto> oauthRegister(OAuthRegisterRequest req);

    /**
     * 只检查token是否合法
     * @param token
     * @return
     */
    Result<YesOrNoDto> checkToken(String token);

    /**
     * 验证是否绑定
     */
    Result<YesOrNoDto> oauthBindMobile(OAuthBindMobileRequest req);

    Result setPassword(SetPasswordRequest req);

    Result refreshToken(TokenRequest token);
}
