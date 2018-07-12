package com.jkdys.doctor.ui.consult.diagnosis;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.DoctorDetailData;
import com.jkdys.doctor.data.model.PhoneOrderDetail;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class DiagnosisOnPhonePresenter extends MvpBasePresenter<DiagnosisOnPhoneView> {

    DaYiShiServiceApi api;
    @Inject
    public DiagnosisOnPhonePresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void getOrderDetail(String orderId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderid", orderId);
        ifViewAttached(view -> view.showLoading(false));
        api.getOrderDetail(params).enqueue(new BaseCallback<BaseResponse<PhoneOrderDetail>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<PhoneOrderDetail> response) {
                ifViewAttached(view -> view.onRequestSuccess(response.getData()));
            }
        });
    }
}
