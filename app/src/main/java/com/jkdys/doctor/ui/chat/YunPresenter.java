package com.jkdys.doctor.ui.chat;

import android.text.TextUtils;

import com.chairoad.framework.util.LogUtil;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.core.chat.LoginListener;
import com.jkdys.doctor.core.event.ChatLoginEvent;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;

import org.greenrobot.eventbus.EventBus;
import javax.inject.Inject;


public class YunPresenter extends MvpBasePresenter<IYunView> {

    @Inject
    YunModel yunModel;

    LoginInfoUtil loginInfoUtil;

    @Inject
    public YunPresenter(YunModel yunModel, LoginInfoUtil loginInfoUtil) {
        this.yunModel = yunModel;
        this.loginInfoUtil = loginInfoUtil;
    }

    public void loadList() {
//        UserInfo userInfo = UserInfoUtil.instence().get();
//        if (TextUtils.isEmpty(userInfo.getHxUserName()) || TextUtils.isEmpty(userInfo.getHxPassword()))
////            return;

        if (null == loginInfoUtil.getLoginResponse())
            return;

        String hxUserName = loginInfoUtil.getHxId();
        String hxPwd = loginInfoUtil.getHxPwd();

        if (TextUtils.isEmpty(hxUserName) || TextUtils.isEmpty(hxPwd))
            return;

        ChatHelper.getInstance().login(hxUserName, hxPwd, new LoginListener() {
            @Override
            public void onLoginSuccess() {
                LogUtil.e("zj"," YunPresenter huan xin login success");
                EventBus.getDefault().post(new ChatLoginEvent());
                ifViewAttached(view -> view.onLoadSuccess(yunModel.loadConversationList()));
            }

            @Override
            public void onLoginFail(String msg) {
                LogUtil.e("zj", "YunPresenter 环信登录失败:" + msg);
            }
        });
    }

}
