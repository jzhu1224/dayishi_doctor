package com.jkdys.doctor.ui.main;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hyphenate.easeui.domain.EaseUser;
import com.jkdys.doctor.data.db.ChatDBManager;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.MyFriendData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter extends MvpBasePresenter<MainView> {

    DaYiShiServiceApi api;

    @Inject
    public MainPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void syncData() {

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

                new Thread(() -> {
                    ChatDBManager.getInstance().saveContactList(easeUsers);
                }).start();
            }
        });
    }

    public void logout() {

    }
}
