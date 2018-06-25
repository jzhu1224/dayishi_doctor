package com.framework.share.login;

/**
 * Created by yanxin on 2018/3/14.
 */

public interface ILogin {

    void login(LoginType loginType,ILoginCallback callback);

}
