package com.jkdys.doctor.ui.consult;

import android.support.annotation.NonNull;
import android.view.View;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpFragment;
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
