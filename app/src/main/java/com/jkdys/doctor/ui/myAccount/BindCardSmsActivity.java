package com.jkdys.doctor.ui.myAccount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.myAccount.bank.BindCardSmsPresenter;
import com.jkdys.doctor.ui.myAccount.bank.BindCardSmsView;
import com.jkdys.doctor.widget.CountdownView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BindCardSmsActivity extends MvpActivity<BindCardSmsView, BindCardSmsPresenter> implements BindCardSmsView {

    @Inject
    BindCardSmsPresenter bindCardSmsPresenter;

    @BindView(R.id.smsDescTxt)
    TextView smsDescTxt;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.edt_verify_code)
    EditText edtVerifyCode;
    @BindView(R.id.codeBtn)
    CountdownView countDownBtn;
    @BindView(R.id.btn_ok)
    Button btnNextStep;

    @NonNull
    @Override
    public BindCardSmsPresenter createPresenter() {
        getActivityComponent().inject(this);
        return bindCardSmsPresenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("验证手机号");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verify_bind_phone_info;
    }

    @OnClick(R.id.btn_ok)
    void onNextStepClick() {
        //mPresenter.verifyBindPhone(bizType,edtVerifyCode.getText().toString(),advanceVoucherNo,bankAccountId);
    }

}
