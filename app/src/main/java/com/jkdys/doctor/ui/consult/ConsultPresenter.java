package com.jkdys.doctor.ui.consult;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.ShareData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import javax.inject.Inject;

public class ConsultPresenter extends MvpBasePresenter<ConsultView> {

    DaYiShiServiceApi api;

    @Inject
    public ConsultPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void share() {
        ifViewAttached(view -> view.showLoading(false));
        api.share().enqueue(new BaseCallback<BaseResponse<ShareData>>() {
            @Override
            public void onBusinessSuccess(BaseResponse<ShareData> response) {
                ifViewAttached(view -> view.onGetShareDataSuccess(response.getData()));
            }
        });
    }
}
