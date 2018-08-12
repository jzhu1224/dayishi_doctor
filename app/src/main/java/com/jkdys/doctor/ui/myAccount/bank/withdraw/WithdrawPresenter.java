package com.jkdys.doctor.ui.myAccount.bank.withdraw;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.profile.changeMobile.ChangeMobileView;

import java.util.HashMap;

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

    public void withdraw(String money, String code) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("money", money);
        params.put("verificationCode", code);
        api.withdraw(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(WithdrawView::onWithdrawSuccess);
            }
        });
    }

    public void getCode() {

        //"cellphone": "18818223565", //手机号
        //    "code": "5" // 2是更改手机，3是电话就诊预约，4是门诊挂号预约，5是银行卡绑定
        HashMap<String, Object> params = new HashMap<>();
        params.put("cellphone", loginInfoUtil.getLoginResponse().getDoctor().getCellphoneno());
        params.put("code", 6);

        ifViewAttached(view -> view.showLoading(false));
        api.sendCommonVerificationCode(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(WithdrawView::onRequestCodeSuccess);
            }
        });
    }
}
