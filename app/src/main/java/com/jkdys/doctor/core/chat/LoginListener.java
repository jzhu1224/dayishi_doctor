package com.jkdys.doctor.core.chat;

/**
 * Created by zhujiang on 2017/11/23.
 */

public interface LoginListener {
    void onLoginSuccess();
    void onLoginFail(String msg);
}
