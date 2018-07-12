package com.jkdys.doctor.ui.chat.doctor;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.DoctorDetailData;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.widget.DoctorGoodAtTag;
import com.nex3z.flowlayout.FlowLayout;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import javax.inject.Inject;

import butterknife.BindView;

public class DoctorDetailActivity extends MvpActivity<DoctorDetailView, DoctorDetailPresenter> implements DoctorDetailView {

    @Inject
    DoctorDetailPresenter doctorDetailPresenter;
    @BindView(R.id.my_toolbar)
    FrameLayout myToolbar;

    @BindView(R.id.profile_image)
    ImageView imgHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_doctor_info)
    TextView tvDoctorInfo;
    @BindView(R.id.fl_good_at)
    FlowLayout flowLayout;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.btn_add)
    Button btnAdd;


    @NonNull
    @Override
    public DoctorDetailPresenter createPresenter() {
        getActivityComponent().inject(this);
        return doctorDetailPresenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        String doctorId = getIntent().getStringExtra("doctorId");
        doctorDetailPresenter.getDoctorDetail(doctorId);
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

    @Override
    public void onRequestSuccess(DoctorDetailData doctorDetailData) {
        tvName.setText(doctorDetailData.getDoctorname());
        tvDoctorInfo.setText(doctorDetailData.getFacultyname()+" "+doctorDetailData.getTitlename());
        tvDesc.setText(doctorDetailData.getPersonalprofile());
        ImageLoader.with(mActivity).load(doctorDetailData.getDoctorpicheadurl()).placeholder(R.drawable.img_doctor).into(imgHeader);
        btnAdd.setVisibility(doctorDetailData.isIsfriend()? View.GONE:View.VISIBLE);
        String sGoodAt = doctorDetailData.getGoodat();
        if (!TextUtils.isEmpty(sGoodAt)) {
            String[] goodAt = sGoodAt.split(",");
            for (int i = 0; i < goodAt.length; i++) {
                DoctorGoodAtTag goodAtTag = new DoctorGoodAtTag(mActivity);
                goodAtTag.setText(goodAt[i]);
                flowLayout.addView(goodAtTag);
            }
        }
    }
}
