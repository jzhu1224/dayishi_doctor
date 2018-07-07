package com.jkdys.doctor.ui.chat.doctor;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import javax.inject.Inject;

import butterknife.BindView;

public class DoctorDetailActivity extends MvpActivity<DoctorDetailView, DoctorDetailPresenter> implements DoctorDetailView {

    @Inject
    DoctorDetailPresenter doctorDetailPresenter;
    @BindView(R.id.my_toolbar)
    FrameLayout myToolbar;

    @NonNull
    @Override
    public DoctorDetailPresenter createPresenter() {
        getActivityComponent().inject(this);
        return doctorDetailPresenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 14) {
            myToolbar.setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(mActivity),0,0);
        }
        myToolbar.setBackgroundColor(getResources().getColor(R.color.color_transparent));
        toolbar.setBackgroundAlpha(0);
        toolbar.addLeftImageButton(R.drawable.ic_back_white,R.id.qmui_topbar_item_left_back).setOnClickListener(view -> finish());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_doctor_detail;
    }
}
