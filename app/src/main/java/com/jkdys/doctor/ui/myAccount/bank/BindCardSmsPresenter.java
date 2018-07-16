package com.jkdys.doctor.ui.myAccount.bank;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class BindCardSmsPresenter extends MvpBasePresenter<BindCardSmsView> {

    DaYiShiServiceApi api;
    @Inject
    public BindCardSmsPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void bindingBankCard(String bindingCode, String verificationCode) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("bindingcode",bindingCode);
        params.put("verificationcode", verificationCode);
        api.bindingBankCard(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(BindCardSmsView::onBindSuccess);
            }
        });
    }
}
