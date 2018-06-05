package com.chairoad.framework.util;

import android.os.Build;

/**
 * Created by yanxin on 17/9/20.
 */

public class PermissionUtil {

    public static boolean isXiaomi() {
        String device = Build.MANUFACTURER;
        return device.equalsIgnoreCase( "Xiaomi" );
    }

    public static boolean isHuaWei() {
        String device = Build.MANUFACTURER;
        return device.equalsIgnoreCase( "HUAWEI" );
    }

    public static boolean isOppo() {
        String device = Build.MANUFACTURER;
        return device.equalsIgnoreCase( "OPPO" );
    }

    public static boolean isViovo() {
        String device = Build.MANUFACTURER;
        return device.equalsIgnoreCase( "VIVO" );
    }

    //获取手机厂商
    public static String getModel() {
        return Build.MANUFACTURER;
    }

}
