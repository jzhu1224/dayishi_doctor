package com.jkdys.doctor.ui.consult;

import android.support.annotation.NonNull;
import android.view.View;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpFragment;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import javax.inject.Inject;

public class PhoneFragment extends MvpFragment<ConsultView,ConsultPresenter> {

    @Inject
    ConsultPresenter consultPresenter;

    @NonNull
    @Override
    public ConsultPresenter createPresenter() {
        getActivityComponent().inject(this);
        return consultPresenter;
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_consult;
    }
}
