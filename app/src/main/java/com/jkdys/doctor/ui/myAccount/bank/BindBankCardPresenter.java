package com.jkdys.doctor.ui.myAccount.bank;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.BindBankCardData;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class BindBankCardPresenter extends MvpBasePresenter<BindBankCardView> {

    DaYiShiServiceApi api;
    @Inject
    public BindBankCardPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void getBankNameByAccount(String bankCardNo) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("bankaccount",bankCardNo);
        api.getBankNameByAccount(params).enqueue(new BaseCallback<BaseResponse<BindBankCardData>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<BindBankCardData> response) {
                ifViewAttached(view -> view.onRequestSuccess(response.getData()));
            }
        });
    }
}
