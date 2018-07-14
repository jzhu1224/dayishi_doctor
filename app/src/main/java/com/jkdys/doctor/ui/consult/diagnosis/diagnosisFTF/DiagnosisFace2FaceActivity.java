package com.jkdys.doctor.ui.consult.diagnosis.diagnosisFTF;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.Face2FaceOrderDetail;
import com.jkdys.doctor.data.model.ProcessFace2FaceOrder;
import com.jkdys.doctor.ui.MvpActivity;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DiagnosisFace2FaceActivity extends MvpActivity<DiagnosisFace2FaceView, DiagnosisFace2FacePresenter> implements DiagnosisFace2FaceView {

    @Inject
    DiagnosisFace2FacePresenter presenter;

    @BindView(R.id.img_avatar)
    ImageView imgHeader;

    @BindView(R.id.tv_state)
    TextView tvState;

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

    @BindView(R.id.btn_accept)
    Button btnAccept;

    @BindView(R.id.btn_delay)
    Button btnDelay;

    @BindView(R.id.btn_cancel)
    Button btnCancel;

    Face2FaceOrderDetail face2FaceOrderDetail;

    String orderId;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        orderId = getIntent().getStringExtra("orderId");
        presenter.getFace2FaceOrderDetail(orderId);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_diagnosis_face_to_face;
    }

    @OnClick(R.id.ll_time)
    void onProcessTimeClick() {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        new TimePickerDialog.Builder()
                .setCallBack((timePickerView, millseconds) -> {
                    tvProcessTime.setText(sf.format(new Date(millseconds)));
                })
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("请选择诊断时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.color_6196FF))
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build().show(getSupportFragmentManager(),"timePicker");
    }

    @OnClick(R.id.rl_record)
    void onRecordClick() {

    }

    @Override
    public void onRequestSuccess(Face2FaceOrderDetail face2FaceOrderDetail) {

        this.face2FaceOrderDetail = face2FaceOrderDetail;

        ImageLoader.with(mActivity).load(face2FaceOrderDetail.getPicheadurl())
                .placeholder(R.drawable.img_doctor)
                .into(imgHeader);
        tvName.setText(face2FaceOrderDetail.getPatientname());
        tvAge.setText(String.valueOf(face2FaceOrderDetail.getAge()));
        tvGender.setText(face2FaceOrderDetail.getGender().equals("1")?"男":"女");
        tvPrice.setText(String.valueOf(face2FaceOrderDetail.getAmount()));
        tvIdNumber.setText(face2FaceOrderDetail.getCertificateno());
        edtAddress.setText(face2FaceOrderDetail.getRegplace());
        tvProcessTime.setText(face2FaceOrderDetail.getRegtime());


        // "status": "0",//0，待处理、1，已完成、2，已取消'
        // "regstatus": "0"//门诊订单状态。0待处理、1处理中、2已完成、3已取消

        if (face2FaceOrderDetail.getRegstatus().equals("0")) {
            //待处理的时候可以编辑
            btnAccept.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            tvState.setText("待处理");
        } else if (face2FaceOrderDetail.getRegstatus().equals("1")) {
            if (face2FaceOrderDetail.getCandelay() != null && face2FaceOrderDetail.getCandelay()) {
                btnDelay.setVisibility(View.VISIBLE);
            }
            btnCancel.setVisibility(View.VISIBLE);
            tvState.setText("处理中");
        } else if (face2FaceOrderDetail.getRegstatus().equals("2")) {
            //已完成
            tvState.setText("已完成");
        } else if (face2FaceOrderDetail.getRegstatus().equals("3")) {
            //已取消
            tvState.setText("已取消");
        }
    }

    @Override
    public void onProcessSuccess() {
        presenter.getFace2FaceOrderDetail(orderId);
    }

    @OnClick(R.id.btn_accept)
    void onBtnAcceptClick() {
        //同意订单
        if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            ToastUtil.show(mActivity, "请输入诊断地点");
            return;
        }

        if (TextUtils.isEmpty(tvProcessTime.getText().toString())) {
            ToastUtil.show(mActivity, "请输入诊断时间");
            return;
        }

        ProcessFace2FaceOrder processFace2FaceOrder = new ProcessFace2FaceOrder();
        processFace2FaceOrder.setOrderid(orderId);
        processFace2FaceOrder.setRegtime(tvProcessTime.getText().toString());
        processFace2FaceOrder.setRegplace(edtAddress.getText().toString());
        processFace2FaceOrder.setHandletype("1");
        presenter.processOrder(processFace2FaceOrder);
    }

    @OnClick(R.id.btn_delay)
    void onBtnDelayClick() {
        //延期订单
        toolbar.setTitle("延期门诊订单");

    }

    @OnClick(R.id.btn_cancel)
    void onBtnCancelClick() {

    }
}
