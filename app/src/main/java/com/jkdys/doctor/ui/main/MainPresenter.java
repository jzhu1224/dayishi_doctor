package com.jkdys.doctor.ui.main;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hyphenate.EMCallBack;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.data.db.ChatDBManager;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import javax.inject.Inject;

public class MainPresenter extends MvpBasePresenter<MainView> {

    LoginInfoUtil loginInfoUtil;

    @Inject
    public MainPresenter(LoginInfoUtil loginInfoUtil) {
        this.loginInfoUtil = loginInfoUtil;
    }

    public void logout() {
        ChatHelper.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        loginInfoUtil.clear();
        ChatDBManager.getInstance().closeDB();
    }
}
