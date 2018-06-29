package com.jkdys.doctor.ui.myAccount;


import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import javax.inject.Inject;

public class MyAccountPresenter extends MvpBasePresenter<MyAccountView> {

    DaYiShiServiceApi api;

    @Inject
    public MyAccountPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void getDoctorInfo() {
        ifViewAttached(view -> view.showLoading(false));
        api.getDoctorDetailInfo().enqueue(new BaseCallback<BaseResponse<Doctor>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Doctor> response) {
                ifViewAttached(view -> view.onRequestSuccess(response.getData()));
            }
        });
    }
}
