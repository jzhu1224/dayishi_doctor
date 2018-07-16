package com.jkdys.doctor.ui.myAccount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.myAccount.bank.BindCardSmsPresenter;
import com.jkdys.doctor.ui.myAccount.bank.BindCardSmsView;
import com.jkdys.doctor.widget.CountdownView1;

import java.util.Objects;

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
    CountdownView1 countDownBtn;
    @BindView(R.id.btn_ok)
    Button btnNextStep;

    String bindingCode;

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

        bindingCode = Objects.requireNonNull(getIntent().getExtras()).getString("bindingCode");
        tvPhone.setText(Objects.requireNonNull(getIntent().getExtras().getString("mobile")));
        countDownBtn.start();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verify_bind_phone_info;
    }

    @OnClick(R.id.btn_ok)
    void onNextStepClick() {
        //mPresenter.verifyBindPhone(bizType,edtVerifyCode.getText().toString(),advanceVoucherNo,bankAccountId);

        if (TextUtils.isEmpty(edtVerifyCode.getText().toString())) {
            ToastUtil.show(mActivity, "请输入验证码");
            return;
        }

        presenter.bindingBankCard(bindingCode, edtVerifyCode.getText().toString());
    }

    @Override
    public void onBindSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
