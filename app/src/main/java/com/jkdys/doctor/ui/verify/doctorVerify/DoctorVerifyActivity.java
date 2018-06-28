package com.jkdys.doctor.ui.verify.doctorVerify;

import android.support.annotation.NonNull;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;

import javax.inject.Inject;

public class DoctorVerifyActivity extends MvpActivity<DoctorVerifyView, DoctorVerifyPresenter> implements DoctorVerifyView {

    @Inject
    DoctorVerifyPresenter doctorVerifyPresenter;

    @NonNull
    @Override
    public DoctorVerifyPresenter createPresenter() {
        getActivityComponent().inject(this);
        return doctorVerifyPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_doctor_verify;
    }
}
