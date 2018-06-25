package com.framework.share.login;

/**
 * Created by yanxin on 2018/3/14.
 */

public class ILoginManger {

    private static final ILogin iLogin = new ILoginImpl();

    public static void login(LoginType loginType,ILoginCallback callback) {
        iLogin.login(loginType,callback);
    }

    public static void login(LoginType loginType) {
        login(loginType,null);
    }

}
