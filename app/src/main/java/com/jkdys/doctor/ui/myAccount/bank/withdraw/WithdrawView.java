package com.jkdys.doctor.ui.myAccount.bank.withdraw;

import com.jkdys.doctor.ui.myAccount.bank.BankCardListView;

public interface WithdrawView extends BankCardListView {
    void onWithdrawSuccess();
    void onRequestCodeSuccess();
}
