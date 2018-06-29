package com.jkdys.doctor.ui.verify.personalInfo;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.DoctorWorkInfo;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

public class PersonalInfoPresenter extends MvpBasePresenter<PersonalInfoView> {

    DaYiShiServiceApi daYiShiServiceApi;

    @Singleton
    private LoginInfoUtil loginInfoUtil;

    @Inject
    public PersonalInfoPresenter(DaYiShiServiceApi daYiShiServiceApi, LoginInfoUtil loginInfoUtil) {
        this.daYiShiServiceApi = daYiShiServiceApi;
        this.loginInfoUtil = loginInfoUtil;
    }

    public void updateWorkInfo(DoctorWorkInfo doctorWorkInfo) {
        ifViewAttached(view -> view.showLoading(false));
        daYiShiServiceApi.doctorWorkInfoCheck(doctorWorkInfo).enqueue(new BaseCallback<BaseResponse<LoginResponse>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<LoginResponse> response) {
                loginInfoUtil.saveLoginResponse(response.getData());
                ifViewAttached(view -> view.onPersonalInfoUpdateSuccess(response.getData()));
            }
        });
    }
}
