package com.jkdys.doctor.ui.myAccount.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class BindBankCardActivity extends MvpActivity<BindBankCardView, BindBankCardPresenter> {

    @Inject
    BindBankCardPresenter bindBankCardPresenter;

    @BindView(R.id.descTxt)
    TextView descTxt;
    @BindView(R.id.edt_bank_card)
    EditText edtBankCard;
    @BindView(R.id.btn_next_step)
    Button btnNextStep;

    @NonNull
    @Override
    public BindBankCardPresenter createPresenter() {
        getActivityComponent().inject(this);
        return bindBankCardPresenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("绑定银行卡");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @OnTextChanged(value = R.id.edt_bank_card,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterBankCardNoChanged(Editable editable) {
        btnNextStep.setEnabled(!TextUtils.isEmpty(editable.toString().trim()));
    }

    @OnClick(R.id.btn_next_step)
    void onNextStepClick() {
        startActivity(new Intent(mActivity, BindCardVerifyCardInfoActivity.class));
    }

    @OnClick(R.id.tv_support_bank)
    void onSupportBanClick() {
        startActivity(new Intent(mActivity, SupportBankListActivity.class));
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_bind_bank_card;
    }
}
