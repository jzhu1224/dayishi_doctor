package com.jkdys.doctor.ui.myAccount.bank.withdraw;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import javax.inject.Inject;

public class WithdrawPresenter extends MvpBasePresenter<WithdrawView> {

    DaYiShiServiceApi api;
    LoginInfoUtil loginInfoUtil;

    @Inject
    public WithdrawPresenter(DaYiShiServiceApi api, LoginInfoUtil loginInfoUtil) {
        this.api = api;
        this.loginInfoUtil = loginInfoUtil;
    }

    public void getBankCardInfo() {

        if (loginInfoUtil.getBindBanCard() != null) {
            ifViewAttached(view -> view.onRequestSuccess(loginInfoUtil.getBindBanCard()));
            return;
        }

        ifViewAttached(view -> view.showLoading(false));
        api.getDoctorBankList().enqueue(new BaseCallback<BaseResponse<BankCardInfo>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<BankCardInfo> response) {

//                BankCardInfo bankCardInfo = new BankCardInfo();
//                bankCardInfo.setBankid("7886E2DA-8847-4570-9390-DD1BA8739FD9");
//                bankCardInfo.setBankname("招商银行");
//                bankCardInfo.setBankaccount("6222*********4541");
//                loginInfoUtil.saveBindBankCard(bankCardInfo);

                loginInfoUtil.saveBindBankCard(response.getData());
                ifViewAttached(view -> view.onRequestSuccess(response.getData()));
            }
        });
    }
}
