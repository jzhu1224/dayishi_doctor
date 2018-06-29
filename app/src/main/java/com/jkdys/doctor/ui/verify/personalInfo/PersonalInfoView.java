package com.jkdys.doctor.ui.verify.personalInfo;

import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.ui.BaseView;

public interface PersonalInfoView extends BaseView{
    void onPersonalInfoUpdateSuccess(LoginResponse loginResponse);
}
