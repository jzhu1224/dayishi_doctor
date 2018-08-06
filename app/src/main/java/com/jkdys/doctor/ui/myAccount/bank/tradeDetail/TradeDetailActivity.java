package com.jkdys.doctor.ui.myAccount.bank.tradeDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.TradeDetailData;
import com.jkdys.doctor.ui.MvpActivity;

import javax.inject.Inject;

public class TradeDetailActivity extends MvpActivity<TradeDetailView, TradeDetailPresenter> implements TradeDetailView{

    @Inject
    TradeDetailPresenter presenter;

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
        String type = getIntent().getStringExtra("type");

        presenter.getTradeDetail(rId,type);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_trade_detail;
    }

    @Override
    public void onRequestSuccess(TradeDetailData data) {
        ToastUtil.show(mActivity, "data:"+data.getAccountname());
    }
}
