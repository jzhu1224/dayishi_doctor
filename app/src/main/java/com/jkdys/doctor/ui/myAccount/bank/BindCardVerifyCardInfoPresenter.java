package com.jkdys.doctor.ui.myAccount.bank;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;

import javax.inject.Inject;

public class BindCardVerifyCardInfoPresenter extends MvpBasePresenter<BindCardVerifyCardInfoView> {

    DaYiShiServiceApi api;

    @Inject
    public BindCardVerifyCardInfoPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void bindBankCard() {

    }

}
