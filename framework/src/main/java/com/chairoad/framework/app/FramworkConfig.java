package com.chairoad.framework.app;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by jaki on 2018/3/7.
 */

public class FramworkConfig {

    //是否是生产环境
    public static boolean isProd;
    public static Context application;
    public static int versionCode = 0;
    public static String versionName = "";
    public static String u_ticket = ""; // 已登录用户的cookie，默认""
    public static String user_id = "";

    private static String host_name = "";
    private static String host_port = "";
    private static String host_protocol = "http";
    private static String host_path = "";

    private static String host_appkey = "";
    private static String host_env = "";

    public static String getRootUrl() {
        if (TextUtils.isEmpty(host_port)) {
            return host_protocol + "://" + host_name + host_path;
        } else {
            return host_protocol + "://" + host_name + ":" + host_port + host_path;
        }
    }

    public static String getDomain() {
        if (TextUtils.isEmpty(host_port)) {
            return host_protocol + "://" + host_name;
        } else {
            return host_protocol + "://" + host_name + ":" + host_port;
        }
    }

    public static void set_host_env(String p_host_env) {
        FramworkConfig.host_env = p_host_env;
    }

    public static String get_host_env() {
        return host_env;
    }

    public static void set_host_protocol(String p_host_protocol) {
        FramworkConfig.host_protocol = p_host_protocol;
    }

    public static String get_host_protocol() {
        return host_protocol;
    }

    public static void set_host_path(String p_host_path) {
        FramworkConfig.host_path = p_host_path;
    }

    public static String get_host_path() {
        return host_path;
    }

    public static void set_host_name(String p_host_name) {
        FramworkConfig.host_name = p_host_name;
    }

    public static String get_host_name() {
        return host_name;
    }

    public static void set_host_port(String p_host_port) {
        FramworkConfig.host_port = p_host_port;
    }

    public static String get_host_port() {
        return host_port;
    }

    public static void set_appkey(String p_appkey) {
        FramworkConfig.host_appkey = p_appkey;
    }

    public static String get_appkey() {
        return host_appkey;
    }

    public static boolean isDebugIP() {
        return !"prod".equals(get_host_env());
    }

    //是否是生产环境
    public static boolean isProd() {
        return isProd;
    }

}
