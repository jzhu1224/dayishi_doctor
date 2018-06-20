package com.jkdys.doctor.ui.login;

import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.ui.BaseView;

public interface LoginView extends BaseView {
    void sendVerifyCodeSuccess();
    void loginSuccess(LoginResponse loginResponse);
}
