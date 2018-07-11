package com.jkdys.doctor.ui.chat.doctor;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.DoctorDetailData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class DoctorDetailPresenter extends MvpBasePresenter<DoctorDetailView> {

    DaYiShiServiceApi api;

    @Inject
    public DoctorDetailPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void getDoctorDetail(String doctorId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("doctorid", doctorId);
        ifViewAttached(view ->  view.showLoading(false));
        api.getDoctorDetail(params).enqueue(new BaseCallback<BaseResponse<DoctorDetailData>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<DoctorDetailData> response) {
                ifViewAttached(view -> view.onRequestSuccess(response.getData()));
            }
        });
    }
}
