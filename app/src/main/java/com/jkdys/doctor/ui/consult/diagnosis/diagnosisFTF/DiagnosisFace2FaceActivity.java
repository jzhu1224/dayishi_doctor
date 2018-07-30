package com.jkdys.doctor.ui.consult.diagnosis.diagnosisFTF;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
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
import com.jkdys.doctor.ui.consult.diagnosis.DelayRecordActivity;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @BindView(R.id.ll_time)
    View vTime;
    @BindView(R.id.tv_process_time)
    TextView tvProcessTime;
    @BindView(R.id.rl_record)
    View rlRecord;
    @BindView(R.id.tv_id_number)
    TextView tvIdNumber;

    @BindView(R.id.btn_accept)
    Button btnAccept;

    @BindView(R.id.btn_complete)
    Button btnComplete;

    @BindView(R.id.btn_delay)
    Button btnDelay;

    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @BindView(R.id.btn_confirm_delay)
    Button btnConfirmDelay;

    Face2FaceOrderDetail face2FaceOrderDetail;

    String orderId;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    boolean delayMode = false;

    @NonNull
    @Override
    public DiagnosisFace2FacePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        vTime.setEnabled(false);
        delayMode = getIntent().getBooleanExtra("delayMode", false);
        toolbar.setTitle(delayMode?"延期门诊订单":"门诊订单详情");
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
                .setThemeColor(getResources().getColor(R.color.color_white))
                .setTitleTextColor(getResources().getColor(R.color.color_394043))
                .setCancelTextColor(getResources().getColor(R.color.color_757575))
                .setSureTextColor(getResources().getColor(R.color.color_003d87))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.color_212121))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.color_a5a5a5))
                .setWheelItemTextSize(16)
                .build().show(getSupportFragmentManager(),"timePicker");
    }

    @OnClick(R.id.rl_record)
    void onRecordClick() {
        Intent intent = new Intent(mActivity, DelayRecordActivity.class);
        intent.putParcelableArrayListExtra("delayRecordList", (ArrayList<? extends Parcelable>) face2FaceOrderDetail.getDelayrecord());
        startActivity(intent);
    }

    @OnClick(R.id.btn_complete)
    void onCompleteClick() {
        //完成订单
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(mActivity);
        builder.setTitle("请输入就诊码以完成订单").setPlaceholder("就诊码").setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    dialog.dismiss();
                    String code = builder.getEditText().getText().toString();
                    ProcessFace2FaceOrder processFace2FaceOrder = new ProcessFace2FaceOrder();
                    processFace2FaceOrder.setOrderid(orderId);
                    processFace2FaceOrder.setHandletype("2");
                    processFace2FaceOrder.setMedicalcode(code);
                    presenter.processOrder(processFace2FaceOrder);
                }).show();
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


        if (delayMode) {
            btnAccept.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            btnDelay.setVisibility(View.GONE);
            btnComplete.setVisibility(View.GONE);
            btnConfirmDelay.setVisibility(View.VISIBLE);

            edtAddress.setEnabled(true);
            vTime.setEnabled(true);
            tvProcessTime.setText("");

            return;
        }

        if (face2FaceOrderDetail.getRegstatus().equals("0")) {
            //待处理的时候可以编辑
            btnAccept.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            btnDelay.setVisibility(View.GONE);
            btnComplete.setVisibility(View.GONE);
            edtAddress.setEnabled(true);
            vTime.setEnabled(true);
        } else if (face2FaceOrderDetail.getRegstatus().equals("1")) {
            if (face2FaceOrderDetail.getCandelay() != null && face2FaceOrderDetail.getCandelay()) {
                //可以延期
                btnDelay.setVisibility(View.VISIBLE);
                btnAccept.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
            } else {
                btnCancel.setVisibility(View.VISIBLE);
                btnAccept.setVisibility(View.GONE);
                btnDelay.setVisibility(View.GONE);
            }
            btnComplete.setVisibility(View.VISIBLE);
            edtAddress.setEnabled(false);
            vTime.setEnabled(false);

        } else if (face2FaceOrderDetail.getRegstatus().equals("2")) {
            //已完成
            btnDelay.setVisibility(View.GONE);
            btnAccept.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            edtAddress.setEnabled(false);
            vTime.setEnabled(false);
        } else if (face2FaceOrderDetail.getRegstatus().equals("3")) {
            //已取消
            btnDelay.setVisibility(View.GONE);
            btnAccept.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            edtAddress.setEnabled(false);
            vTime.setEnabled(false);
        }

        if (face2FaceOrderDetail.getDelayrecord() != null &&
                face2FaceOrderDetail.getDelayrecord().size() > 0) {
            rlRecord.setVisibility(View.VISIBLE);
        } else {
            rlRecord.setVisibility(View.GONE);
        }
    }

    @Override
    public void onProcessSuccess(String type) {
        if (type.equals("3")) {
            setResult(RESULT_OK);
            finish();
        } else {
            presenter.getFace2FaceOrderDetail(orderId);
        }
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

        Intent intent = new Intent(mActivity, DiagnosisFace2FaceActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("delayMode", true);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            presenter.getFace2FaceOrderDetail(orderId);
            toolbar.setTitle(delayMode?"延期门诊订单":"门诊订单详情");
        }
    }

    @OnClick(R.id.btn_cancel)
    void onBtnCancelClick() {
        //取消订单
        ProcessFace2FaceOrder processFace2FaceOrder = new ProcessFace2FaceOrder();
        processFace2FaceOrder.setOrderid(orderId);
        processFace2FaceOrder.setHandletype("4");
        presenter.processOrder(processFace2FaceOrder);
    }

    @OnClick(R.id.btn_confirm_delay)
    void onConfirmDelayClick() {
        //延期订单

        if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            ToastUtil.show(mActivity, "就诊地点不能为空");
            return;
        }

        if (TextUtils.isEmpty(tvProcessTime.getText().toString())) {
            ToastUtil.show(mActivity, "就诊时间不能为空");
            return;
        }

        ProcessFace2FaceOrder processFace2FaceOrder = new ProcessFace2FaceOrder();
        processFace2FaceOrder.setOrderid(orderId);
        processFace2FaceOrder.setRegplace(edtAddress.getText().toString());
        processFace2FaceOrder.setRegtime(tvProcessTime.getText().toString());
        processFace2FaceOrder.setHandletype("3");
        presenter.processOrder(processFace2FaceOrder);
    }
}
