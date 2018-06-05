package com.chairoad.framework.encrypt;

/**
 * Created by yanxin on 2018/3/14.
 */

public class Encrypt {

    public static String decrypt(String params,String secret) {
        return MD5Util.getMD5String(params+"&"+secret);
    }

}
