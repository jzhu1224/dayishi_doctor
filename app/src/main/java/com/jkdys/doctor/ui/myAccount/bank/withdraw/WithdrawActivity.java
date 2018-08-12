package com.jkdys.doctor.ui.myAccount.bank.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.event.RequestSmsCodeEvent;
import com.jkdys.doctor.event.WithdrawEvent;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.myAccount.bank.widget.BankCardView;
import com.jkdys.doctor.utils.AndroidBug5497Workaround;
import com.jkdys.doctor.utils.DecimalDigitsInputFilter;
import com.jkdys.doctor.utils.ManyiUtils;
import com.jkdys.doctor.utils.SoftKeyBoardListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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

    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;

    String money = "";

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
        EventBus.getDefault().register(this);
        toolbar.setTitle("提现");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> {
            finish();
            ManyiUtils.closeKeyBoard(mActivity, edtWithdraw);
        });
        ManyiUtils.showKeyBoard(mActivity, edtWithdraw);

        money = getIntent().getStringExtra("money");

        tvTotalMoney.setText(money);

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                btnWithdraw.setVisibility(View.GONE);
            }

            @Override
            public void keyBoardHide(int height) {
                btnWithdraw.setVisibility(View.VISIBLE);
            }
        });

        edtWithdraw.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(9,2)});
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

    @Override
    public void onUnbindCardSuccess() {

    }

    @OnClick(R.id.btn_withdraw)
    void onBtnWithdrawClick() {
        if (!TextUtils.isEmpty(edtWithdraw.getText().toString())) {
            presenter.getCode();
        }
    }

    private void showSmsCodeDialog() {
        ManyiUtils.closeKeyBoard(mActivity, edtWithdraw);
        edtWithdraw.postDelayed(() -> {
            SMSCodeDialogFragment smsCodeDialogFragment = new SMSCodeDialogFragment();
            smsCodeDialogFragment.show(getSupportFragmentManager(),"tag");
        },200);
    }

    @OnClick(R.id.tv_withdraw_all)
    void onWithdrawClick() {
        edtWithdraw.setText(String.valueOf(money));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ManyiUtils.closeKeyBoard(mActivity, edtWithdraw);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestSmsCodeEvent(RequestSmsCodeEvent event) {
        presenter.getCode();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWithdrawClickEvent(WithdrawEvent event) {
        presenter.withdraw(edtWithdraw.getText().toString(),event.getCode());
    }

    @Override
    public void onWithdrawSuccess() {
        //提现成功
        Intent intent = new Intent(mActivity, WithdrawResultActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestCodeSuccess() {
        showSmsCodeDialog();
    }
}
