package com.dayishiapp.doctor.ui.consult;

import android.support.annotation.NonNull;
import android.view.View;

import com.dayishiapp.doctor.R;
import com.dayishiapp.doctor.ui.MvpFragment;

import javax.inject.Inject;

public class ConsultFragment extends MvpFragment<ConsultView,ConsultPresenter> implements ConsultView{

    @Inject
    ConsultPresenter consultPresenter;
    @Override
    protected void initViews(View view) {
        toolbar.setTitle(R.string.consult);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_consult;
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showError(String message) {

    }

    @NonNull
    @Override
    public ConsultPresenter createPresenter() {
        getActivityComponent().inject(this);
        return consultPresenter;
    }
}
