package com.jkdys.doctor.ui.myAccount.bank;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.VerifyBankCardData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class BindCardVerifyCardInfoPresenter extends MvpBasePresenter<BindCardVerifyCardInfoView> {

    DaYiShiServiceApi api;

    @Inject
    public BindCardVerifyCardInfoPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void bindBankCard(String mobile) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("cellphone", mobile);
        api.verifyBankCardInfo(params).enqueue(new BaseCallback<BaseResponse<VerifyBankCardData>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<VerifyBankCardData> response) {
                ifViewAttached(view -> view.onBindSuccess(response.getData().getBindingcode()));
            }
        });
    }

}
