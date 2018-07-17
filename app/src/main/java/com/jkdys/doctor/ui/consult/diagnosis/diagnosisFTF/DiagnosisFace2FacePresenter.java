package com.jkdys.doctor.ui.consult.diagnosis.diagnosisFTF;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.Face2FaceOrderDetail;
import com.jkdys.doctor.data.model.PhoneOrderDetail;
import com.jkdys.doctor.data.model.ProcessFace2FaceOrder;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class DiagnosisFace2FacePresenter extends MvpBasePresenter<DiagnosisFace2FaceView> {

    DaYiShiServiceApi api;

    @Inject
    public DiagnosisFace2FacePresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void getFace2FaceOrderDetail(String orderId) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderid", orderId);
        api.getFace2FaceOrderDetail(params).enqueue(new BaseCallback<BaseResponse<Face2FaceOrderDetail>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Face2FaceOrderDetail> response) {
                ifViewAttached(view -> view.onRequestSuccess(response.getData()));
            }
        });
    }

    public void processOrder(ProcessFace2FaceOrder processFace2FaceOrder) {
        ifViewAttached(view -> view.showLoading(false));
        api.processFace2FaceOrder(processFace2FaceOrder).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(view -> view.onProcessSuccess(processFace2FaceOrder.getHandletype()));
            }
        });
    }

}
