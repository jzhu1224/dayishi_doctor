package com.jkdys.doctor.ui.myAccount;

import com.jkdys.doctor.data.model.AccountData;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.ui.BaseView;

public interface MyAccountView extends BaseView {
    void onRequestSuccess(AccountData accountData);
}
