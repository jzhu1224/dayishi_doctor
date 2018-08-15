package com.jkdys.doctor.ui.myAccount.bank.tradeDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.TradeDetailData;
import com.jkdys.doctor.ui.MvpActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class TradeDetailActivity extends MvpActivity<TradeDetailView, TradeDetailPresenter> implements TradeDetailView{

    @Inject
    TradeDetailPresenter presenter;

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_account)
    TextView tvBankAccount;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_trade_time)
    TextView tvTradeTime;
    @BindView(R.id.tv_trade_no)
    TextView tvTradeNo;
    @BindView(R.id.tv_remark)
    TextView tvRemark;

    @BindView(R.id.textView2)
    TextView tv2;
    @BindView(R.id.textView3)
    TextView tv3;
    @BindView(R.id.textView4)
    TextView tv4;
    @BindView(R.id.textView5)
    TextView tv5;
    @BindView(R.id.textView6)
    TextView tv6;
    @BindView(R.id.textView7)
    TextView tv7;
    @BindView(R.id.textView9)
    TextView tv9;

    String type;

    @NonNull
    @Override
    public TradeDetailPresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("交易详情");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();

        String rId = getIntent().getStringExtra("rId");
        type = getIntent().getStringExtra("type");

        presenter.getTradeDetail(rId,type);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_trade_detail;
    }

    @Override
    public void onRequestSuccess(TradeDetailData data) {
        tvTradeTime.setText(data.getTradetime());
        tvMoney.setText(data.getMoneyshow());
        if (type.equals("2")) {
            tvStatus.setText(data.getStatusname());
            tvAccountName.setText(data.getAccountname());
            tvBankAccount.setText(data.getBankaccount());
            tvBankName.setText(data.getBankname());
            tvPhone.setText(data.getCellphone());
            tvTradeNo.setText(data.getTradeserialnumber());
            tvRemark.setText(data.getTraderemark());
        } else {
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            tv9.setVisibility(View.GONE);
            tv6.setText("订单号");
            tvTradeNo.setText(data.getOrderid());
            tv7.setText("订单类型");
            tvRemark.setText(data.getOrdertypename());
        }


    }
}
