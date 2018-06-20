package com.jkdys.doctor.ui.login;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import java.util.HashMap;
import javax.inject.Inject;
import javax.inject.Singleton;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    private DaYiShiServiceApi daYiShiServiceApi;

    @Singleton
    private LoginInfoUtil loginInfoUtil;

    @Inject
    public LoginPresenter(DaYiShiServiceApi daYiShiServiceApi, LoginInfoUtil loginInfoUtil) {
        this.daYiShiServiceApi = daYiShiServiceApi;
        this.loginInfoUtil = loginInfoUtil;
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

    public void loginByVerifyCode(String cellPhone, String verifyCode) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> map = new HashMap<>();
        map.put("cellphone", cellPhone);
        map.put("verificationCode", verifyCode);
        daYiShiServiceApi.login(map).enqueue(new BaseCallback<BaseResponse<LoginResponse>>(getView()) {
            @Override
            public void onBusinessSuccess(final BaseResponse<LoginResponse> response) {
                if (response.getData() == null)
                    return;
               ifViewAttached(view -> view.loginSuccess(response.getData()));
               loginInfoUtil.saveLoginResponse(response.getData());
            }
        });
    }
}
