package com.dayishiapp.doctor.ui.consult;

import android.support.annotation.NonNull;
import android.view.View;

import com.dayishiapp.doctor.R;
import com.dayishiapp.doctor.ui.MvpFragment;
import javax.inject.Inject;

public class DoorFragment extends MvpFragment<ConsultView,ConsultPresenter> {

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
        return R.layout.fragment_door_consult;
    }
}
