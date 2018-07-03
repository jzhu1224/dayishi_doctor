package com.jkdys.doctor.ui.myAccount.bank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.widget.PhoneEditTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verify_bank_card;
    }

    @OnClick(R.id.btn_next_step)
    void onNextStepClick() {

    }

    @OnClick(R.id.phoneGuide)
    void oncodeGuideClick() {

    }
}
