package com.jkdys.doctor.ui.profile.changeMobile;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;

import java.util.HashMap;

import javax.inject.Inject;

public class ChangeMobilePresenter extends MvpBasePresenter<ChangeMobileView> {

    DaYiShiServiceApi api;
    LoginInfoUtil loginInfoUtil;

    @Inject
    public ChangeMobilePresenter(DaYiShiServiceApi api, LoginInfoUtil loginInfoUtil) {
        this.api = api;
        this.loginInfoUtil = loginInfoUtil;
    }

    public void getCode(String newMobile) {

        //"cellphone": "18818223565", //手机号
        //    "code": "5" // 2是更改手机，3是电话就诊预约，4是门诊挂号预约，5是银行卡绑定
        HashMap<String, Object> params = new HashMap<>();
        params.put("cellphone", newMobile);
        params.put("code", 2);

        ifViewAttached(view -> view.showLoading(false));
        api.sendCommonVerificationCode(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(ChangeMobileView::onRequestCodeSuccess);
            }
        });
    }

    public void changeMobile(String newMobile,String code) {
        //"cellphone": "18866663565",//手机号
        //"verificationcode": "8888" //短信验证码
        HashMap<String, Object> params = new HashMap<>();
        params.put("cellphone", newMobile);
        params.put("verificationcode", code);
        ifViewAttached(view -> view.showLoading(false));
        api.changeMobile(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                loginInfoUtil.updateMobile(newMobile);
                ifViewAttached(ChangeMobileView::onChangeMobileSuccess);
            }
        });
    }
}
