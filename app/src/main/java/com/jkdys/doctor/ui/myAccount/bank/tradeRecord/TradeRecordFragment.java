package com.jkdys.doctor.ui.myAccount.bank.tradeRecord;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.TradeRecord;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class TradeRecordFragment extends BaseRefreshLoadMoreFrament<TradeRecord, BaseLoadMoreView<TradeRecord>, TradeRecordPresenter>{

    @Inject
    TradeRecordPresenter presenter;

    @Override
    protected BaseQuickAdapter<TradeRecord, BaseViewHolder> createAdapter(List<TradeRecord> mDatas) {
        return new TradeRecordAdapter(mDatas);
    }

    @NonNull
    @Override
    public TradeRecordPresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        super.initViews(view, savedInstanceState);
        toolbar.setTitle("交易记录");
        toolbar.addLeftBackImageButton().setOnClickListener(view1 -> Objects.requireNonNull(getActivity()).finish());
    }

    protected int getLayoutId() {
        return R.layout.fragment_customer;
    }


    class TradeRecordAdapter extends BaseQuickAdapter<TradeRecord, BaseViewHolder> {

        public TradeRecordAdapter(@Nullable List<TradeRecord> data) {
            super(R.layout.item_trade_record, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TradeRecord item) {
            helper.setText(R.id.tv_status, item.getStatusname());
            helper.setText(R.id.tv_time, item.getDate());
            helper.setText(R.id.tv_trade_money, item.getShowmoney());
            // TODO: 2018/7/19 状态颜色需要根据不同状态调整
        }
    }
}
