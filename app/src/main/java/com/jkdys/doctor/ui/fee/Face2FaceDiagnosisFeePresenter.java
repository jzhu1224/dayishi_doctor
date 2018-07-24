package com.jkdys.doctor.ui.fee;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;

import javax.inject.Inject;

public class Face2FaceDiagnosisFeePresenter extends MvpBasePresenter<Face2FaceDiagnosieFeeView> {

    DaYiShiServiceApi api;

    @Inject
    public Face2FaceDiagnosisFeePresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void editPrice(String price) {
        ifViewAttached(view -> view.onEditSuccess());
    }
}
