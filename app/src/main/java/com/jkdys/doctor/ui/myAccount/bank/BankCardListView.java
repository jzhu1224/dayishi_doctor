package com.jkdys.doctor.ui.myAccount.bank;

import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.ui.BaseView;

public interface BankCardListView extends BaseView {
    void onRequestSuccess(BankCardInfo bankCardInfo);
    void onUnbindCardSuccess();
}
