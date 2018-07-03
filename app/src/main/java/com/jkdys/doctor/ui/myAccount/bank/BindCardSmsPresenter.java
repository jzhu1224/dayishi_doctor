package com.jkdys.doctor.ui.myAccount.bank;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;

import javax.inject.Inject;

public class BindCardSmsPresenter extends MvpBasePresenter<BindCardSmsView> {

    DaYiShiServiceApi api;
    @Inject
    public BindCardSmsPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }
}
