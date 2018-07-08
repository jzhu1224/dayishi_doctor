package com.jkdys.doctor.ui.myAccount.bank.withdraw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.myAccount.bank.widget.BankCardView;
import com.jkdys.doctor.utils.AndroidBug5497Workaround;
import com.jkdys.doctor.utils.ManyiUtils;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawActivity extends MvpActivity<WithdrawView, WithdrawPresenter> implements WithdrawView {

    @Inject
    WithdrawPresenter withdrawPresenter;

    @BindView(R.id.bankcard)
    BankCardView bankCardView;

    @BindView(R.id.edt_withdraw)
    EditText edtWithdraw;

    @NonNull
    @Override
    public WithdrawPresenter createPresenter() {
        getActivityComponent().inject(this);
        return withdrawPresenter;
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
        AndroidBug5497Workaround.assistActivity(this);
        withdrawPresenter.getBankCardInfo();
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("提现");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> {
            finish();
            ManyiUtils.closeKeyBoard(mActivity, edtWithdraw);
        });
        ManyiUtils.showKeyBoard(mActivity, edtWithdraw);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void onRequestSuccess(BankCardInfo bankCardInfo) {
        if (bankCardInfo != null) {
            bankCardView.init(bankCardInfo.getBankid(), bankCardInfo.getBankname(), bankCardInfo.getBankaccount());
        }
    }

    @OnClick(R.id.btn_withdraw)
    void onBtnWithdrawClick() {
        showSmsCodeDialog();
    }

    private void showSmsCodeDialog() {
        ManyiUtils.closeKeyBoard(mActivity, edtWithdraw);
        edtWithdraw.postDelayed(() -> {
            SMSCodeDialogFragment smsCodeDialogFragment = new SMSCodeDialogFragment();
            smsCodeDialogFragment.show(getSupportFragmentManager(),"tag");
        },200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ManyiUtils.closeKeyBoard(mActivity, edtWithdraw);
    }
}
