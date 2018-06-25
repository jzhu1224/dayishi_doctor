package com.jkdys.doctor.ui.chat;

import com.chairoad.framework.util.LogUtil;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.core.chat.LoginListener;
import com.jkdys.doctor.core.event.ChatLoginEvent;
import org.greenrobot.eventbus.EventBus;
import javax.inject.Inject;


public class YunPresenter extends MvpBasePresenter<IYunView> {

    @Inject
    YunModel yunModel;

    @Inject
    public YunPresenter(YunModel yunModel) {
        this.yunModel = yunModel;
    }

    public void loadList() {
//        UserInfo userInfo = UserInfoUtil.instence().get();
//        if (TextUtils.isEmpty(userInfo.getHxUserName()) || TextUtils.isEmpty(userInfo.getHxPassword()))
//            return;

        String hxUserName = "test";
        String hxPwd = "test";

        ChatHelper.getInstance().login(hxUserName, hxPwd, new LoginListener() {
            @Override
            public void onLoginSuccess() {
                EventBus.getDefault().post(new ChatLoginEvent());
                ifViewAttached(view -> view.onLoadSuccess(yunModel.loadConversationList()));
            }

            @Override
            public void onLoginFail(String msg) {
                LogUtil.e("YunPresenter", "环信登录失败:" + msg);
            }
        });
    }

}
