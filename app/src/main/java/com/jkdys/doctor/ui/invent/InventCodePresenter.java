package com.jkdys.doctor.ui.invent;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.ui.BaseView;

import java.util.HashMap;

import javax.inject.Inject;

public class InventCodePresenter extends MvpBasePresenter<InventView> {

    DaYiShiServiceApi api;

    @Inject
    public InventCodePresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void setInventCode(String code) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("invitationcode", code);
        api.updateInventionCode(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(InventView::onInventUpdateSuccess);
            }
        });
    }
}
