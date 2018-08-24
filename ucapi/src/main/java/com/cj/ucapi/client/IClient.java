package com.cj.ucapi.client;

import com.cj.ucapi.message.*;

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

    /**
     * 密码重置
     * @param req
     * @return
     */
    Result setPassword(SetPasswordRequest req);

    /**
     * 修改昵称
     * @param req
     * @return
     */
    Result updateNickname(UpdateNicknameRequest req);

    /**
     * 更新扩展信息
     * @param req
     * @return
     */
    Result updateProperties(UpdateNicknameRequest req);

    /**
     * 刷新token
     * @param token
     * @return
     */
    Result refreshToken(TokenRequest token);
}
