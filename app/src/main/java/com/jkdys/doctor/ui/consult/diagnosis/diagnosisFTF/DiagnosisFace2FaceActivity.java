package com.jkdys.doctor.ui.consult.diagnosis.diagnosisFTF;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.Face2FaceOrderDetail;
import com.jkdys.doctor.ui.MvpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DiagnosisFace2FaceActivity extends MvpActivity<DiagnosisFace2FaceView, DiagnosisFace2FacePresenter> implements DiagnosisFace2FaceView {

    @Inject
    DiagnosisFace2FacePresenter presenter;

    @BindView(R.id.img_avatar)
    ImageView imgHeader;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.tv_process_time)
    TextView tvProcessTime;
    @BindView(R.id.rl_record)
    View rlRecord;
    @BindView(R.id.tv_id_number)
    TextView tvIdNumber;

    @NonNull
    @Override
    public DiagnosisFace2FacePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("门诊订单详情");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
        presenter.getFace2FaceOrderDetail(getIntent().getStringExtra("orderId"));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_diagnosis_face_to_face;
    }

    @OnClick(R.id.ll_time)
    void onProcessTimeClick() {

    }

    @OnClick(R.id.rl_record)
    void onRecordClick() {

    }

    @Override
    public void onRequestSuccess(Face2FaceOrderDetail face2FaceOrderDetail) {
        ImageLoader.with(mActivity).load(face2FaceOrderDetail.getPicheadurl())
                .placeholder(R.drawable.img_doctor)
                .into(imgHeader);
        tvName.setText(face2FaceOrderDetail.getPatientname());
        tvAge.setText(String.valueOf(face2FaceOrderDetail.getAge()));
        tvGender.setText(face2FaceOrderDetail.getGender().equals("1")?"男":"女");
        tvPrice.setText(String.valueOf(face2FaceOrderDetail.getAmount()));
        tvIdNumber.setText(face2FaceOrderDetail.getCertificateno());

        // TODO: 2018/7/13 根据状态来判断是否显示

    }
}
