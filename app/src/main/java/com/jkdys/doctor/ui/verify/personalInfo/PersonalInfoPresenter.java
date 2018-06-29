package com.jkdys.doctor.ui.verify.personalInfo;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.DoctorWorkInfo;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class PersonalInfoPresenter extends MvpBasePresenter<PersonalInfoView> {

    DaYiShiServiceApi daYiShiServiceApi;

    @Inject
    public PersonalInfoPresenter(DaYiShiServiceApi daYiShiServiceApi) {
        this.daYiShiServiceApi = daYiShiServiceApi;
    }

    public void updateWorkInfo(DoctorWorkInfo doctorWorkInfo) {
        ifViewAttached(view -> view.showLoading(false));
        daYiShiServiceApi.doctorWorkInfoCheck(doctorWorkInfo).enqueue(new BaseCallback<BaseResponse<LoginResponse>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<LoginResponse> response) {
                ifViewAttached(view -> view.onPersonalInfoUpdateSuccess(response.getData()));
            }
        });
    }
}
