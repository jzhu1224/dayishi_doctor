package com.jkdys.doctor.ui.myAccount.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.BindBankCardData;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.myAccount.BindCardSmsActivity;
import com.jkdys.doctor.widget.PhoneEditTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
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

        BindBankCardData bindBankCardData = getIntent().getParcelableExtra("bindBankCardData");
        tvBankCardInfo.setText(bindBankCardData.getBankname());
        nameTxt.setText(bindBankCardData.getName());
        identifyTxt.setText(bindBankCardData.getCertificateno());

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verify_bank_card;
    }

    @OnClick(R.id.btn_next_step)
    void onNextStepClick() {
        if (TextUtils.isEmpty(edtPhone.getTextString())) {
            ToastUtil.show(mActivity, "请输入手机号");
            return;
        }

        if (!agreementCheck.isChecked()) {
            ToastUtil.show(mActivity, "请同意用户协议");
            return;
        }

        presenter.bindBankCard(edtPhone.getTextString());
    }

    @OnTextChanged(value = R.id.edt_phone,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterBankCardNoChanged(Editable editable) {
        btnNextStep.setEnabled(!TextUtils.isEmpty(editable.toString().trim()));
    }

    @OnClick(R.id.phoneGuide)
    void oncodeGuideClick() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBindSuccess(String bindingCode) {

        Intent intent = new Intent(mActivity, BindCardSmsActivity.class);
        intent.putExtra("bindingCode", bindingCode);
        intent.putExtra("mobile", edtPhone.getTextString());

        startActivityForResult(intent,1);
    }
}
