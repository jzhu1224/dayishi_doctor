package com.jkdys.doctor.ui.myAccount.bank.withdraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawResultActivity extends BaseAppCompatActivity{

    @BindView(R.id.accounting_date)
    TextView accountingDate;
    @BindView(R.id.accounting_state)
    TextView accountingState;

    @Override
    protected int getLayout() {
        return R.layout.activity_withdraw_result;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("提现结果");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        //String bizStatus = getIntent().getStringExtra("bizState");
        String arriveTime = getIntent().getStringExtra("arriveTime");
        accountingDate.setText(arriveTime);
        //状态：S成功、P处理中、F失败（上个页面失败Tosat）
        accountingState.setText("提现申请已提交，等待处理");
    }

    @OnClick(R.id.confirm_btn)
    public void onSuccessClick() {
        finish();
    }
}
