package com.jkdys.doctor.ui.verify.doctorVerify;

import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.ui.BaseView;

public interface DoctorVerifyView extends BaseView {
    void onUploadImageSuccess(String url, int requestCode);
    void onVerifySuccess(LoginResponse loginResponse);
}
