package com.framework.share.login;

/**
 * Created by yanxin on 2018/3/14.
 */

public enum LoginType {

    WECHAT("微信"),
    QQ("QQ");

    private String name;
    LoginType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
