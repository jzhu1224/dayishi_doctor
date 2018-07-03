package com.jkdys.doctor.ui.myAccount.bank;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import javax.inject.Inject;

public class BankCardListPresenter extends MvpBasePresenter<BankCardListView> {

    DaYiShiServiceApi api;

    @Inject
    public BankCardListPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void getBankCardInfo() {
        ifViewAttached(view -> view.showLoading(false));
        api.getDoctorBankList().enqueue(new BaseCallback<BaseResponse<BankCardInfo>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<BankCardInfo> response) {
                ifViewAttached(view -> view.onRequestSuccess(response.getData()));
            }
        });
    }
}
