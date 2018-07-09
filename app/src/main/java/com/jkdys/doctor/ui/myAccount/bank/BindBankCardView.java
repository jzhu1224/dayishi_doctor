package com.jkdys.doctor.ui.myAccount.bank;

import com.jkdys.doctor.data.model.BindBankCardData;
import com.jkdys.doctor.ui.BaseView;

public interface BindBankCardView extends BaseView{
    void onRequestSuccess(BindBankCardData data);
}
