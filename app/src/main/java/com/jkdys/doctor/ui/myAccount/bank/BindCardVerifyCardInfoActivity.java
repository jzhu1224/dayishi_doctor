package com.jkdys.doctor.ui.myAccount.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.myAccount.BindCardSmsActivity;
import com.jkdys.doctor.widget.PhoneEditTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class BindCardVerifyCardInfoActivity extends MvpActivity<BindCardVerifyCardInfoView, BindCardVerifyCardInfoPresenter> implements BindCardVerifyCardInfoView {

    @BindView(R.id.tv_bank_card_info)
    TextView tvBankCardInfo; //银行卡开户行
    @BindView(R.id.nameTxt)
    TextView nameTxt; //姓名
    @BindView(R.id.identifyTxt)
    TextView identifyTxt; //身份证号码
    @BindView(R.id.identifyTypeTxt)
    TextView identifyTypeTxt;
    @BindView(R.id.edt_phone)
    PhoneEditTextView edtPhone;

    @BindView(R.id.btn_next_step)
    Button btnNextStep;
    @BindView(R.id.agreementCheck)
    CheckBox agreementCheck;

    @Inject
    BindCardVerifyCardInfoPresenter bindCardVerifyCardInfoPresenter;

    @NonNull
    @Override
    public BindCardVerifyCardInfoPresenter createPresenter() {
        getActivityComponent().inject(this);
        return bindCardVerifyCardInfoPresenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        toolbar.setTitle("绑定银行卡");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verify_bank_card;
    }

    @OnClick(R.id.btn_next_step)
    void onNextStepClick() {
        startActivity(new Intent(mActivity, BindCardSmsActivity.class));
    }

    @OnTextChanged(value = R.id.edt_phone,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterBankCardNoChanged(Editable editable) {
        btnNextStep.setEnabled(!TextUtils.isEmpty(editable.toString().trim()));
    }

    @OnClick(R.id.phoneGuide)
    void oncodeGuideClick() {

    }
}
