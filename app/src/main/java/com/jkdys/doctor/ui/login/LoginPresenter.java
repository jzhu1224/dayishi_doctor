package com.jkdys.doctor.ui.login;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.UserInfoModel;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import java.util.HashMap;
import javax.inject.Inject;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    DaYiShiServiceApi daYiShiServiceApi;

    @Inject
    public LoginPresenter(DaYiShiServiceApi daYiShiServiceApi) {
        this.daYiShiServiceApi = daYiShiServiceApi;
    }

    public void login(String mobilePhone) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("mobile",mobilePhone);
        daYiShiServiceApi.login(map).enqueue(new BaseCallback<BaseResponse<UserInfoModel>>() {
            @Override
            public void onBusinessSuccess(BaseResponse<UserInfoModel> response) {

            }
        });
    }
}
