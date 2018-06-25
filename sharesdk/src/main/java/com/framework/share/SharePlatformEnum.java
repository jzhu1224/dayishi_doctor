package com.framework.share;

/**
 * Created by yanxin on 2018/4/13.
 */

public enum SharePlatformEnum {

    WECHAI("微信"),
    WechatMoments("微信"),
    SINAWEIBO("新浪微博");

    private String name;

    SharePlatformEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
