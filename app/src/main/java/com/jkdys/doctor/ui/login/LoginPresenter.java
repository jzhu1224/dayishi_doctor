package com.jkdys.doctor.ui.login;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import java.util.HashMap;
import javax.inject.Inject;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private DaYiShiServiceApi daYiShiServiceApi;

    @Inject
    public LoginPresenter(DaYiShiServiceApi daYiShiServiceApi) {
        this.daYiShiServiceApi = daYiShiServiceApi;
    }

    public void login(String mobilePhone) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String,Object> map = new HashMap<>();
        map.put("cellphone",mobilePhone);
        daYiShiServiceApi.sentVerificationCode(map).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(LoginView::sendVerifyCodeSuccess);
            }
        });
    }
}
