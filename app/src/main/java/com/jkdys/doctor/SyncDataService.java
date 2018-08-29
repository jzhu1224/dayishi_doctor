package com.jkdys.doctor;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import com.chairoad.framework.util.LogUtil;
import com.hyphenate.easeui.domain.EaseUser;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.core.chat.LoginListener;
import com.jkdys.doctor.data.db.ChatDBManager;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.MyFriendData;
import com.jkdys.doctor.data.model.UserInfo;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.di.component.DaggerServiceComponent;
import com.jkdys.doctor.di.component.ServiceComponent;
import com.jkdys.doctor.event.SyncDataCompleteEvent;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class SyncDataService extends IntentService {

    @Inject
    LoginInfoUtil loginInfoUtil;

    @Inject
    DaYiShiServiceApi api;

    public SyncDataService() {
        super("SyncDataService");
        ServiceComponent serviceComponent = DaggerServiceComponent.builder()
                .applicationComponent(MyApplication.getInstance().getApplicationComponent())
                .build();
        serviceComponent.inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (null == loginInfoUtil.getLoginResponse())
            return;

        String hxUserName = loginInfoUtil.getHxId();
        String hxPwd = loginInfoUtil.getHxPwd();

        UserInfo userInfo = new UserInfo();
        userInfo.setHxUserName(loginInfoUtil.getHxId());
        userInfo.setNickName(loginInfoUtil.getDoctor().getName());
        userInfo.setHeadImgUrl(loginInfoUtil.getAvatar());
        ChatHelper.getInstance().setCurrentUser(userInfo);

        ChatHelper.getInstance().login(hxUserName, hxPwd, new LoginListener() {
            @Override
            public void onLoginSuccess() {

                LogUtil.e("zj","huan xin login success");

                HashMap<String, Object> params = new HashMap<>();
                params.put("pageindex", 1);
                params.put("pagesize", 1000);
                api.myFriends(params).enqueue(new BaseCallback<BaseResponse<List<MyFriendData>>>() {
                    @Override
                    public void onBusinessSuccess(BaseResponse<List<MyFriendData>> response) {

                        List<EaseUser> easeUsers = new ArrayList<>();

                        List<MyFriendData> myFriendData = response.getData();
                        for (MyFriendData data:myFriendData) {
                            EaseUser easeUser = new EaseUser(data.getHxid());
                            easeUser.setNickname(data.getName());
                            easeUser.setAvatar(data.getPicheadurl());
                            easeUsers.add(easeUser);
                        }
                        ChatDBManager.getInstance().saveContactList(easeUsers);

                        EventBus.getDefault().postSticky(new SyncDataCompleteEvent());

                    }
                });
            }

            @Override
            public void onLoginFail(String msg) {
                LogUtil.e("zj", "环信登录失败:" + msg);
            }
        });
    }

}
