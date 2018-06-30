package com.jkdys.doctor.ui.order;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;
import com.jkdys.doctor.ui.consult.OrderPresenter;
import com.jkdys.doctor.ui.consult.adapter.OrderAdapter;

import java.util.List;

import javax.inject.Inject;

public class AllOrderFragment extends BaseRefreshLoadMoreFrament<OrderInfo,BaseLoadMoreView<OrderInfo>,OrderPresenter,OrderAdapter.OrderViewHolder> {

    @Inject
    OrderPresenter orderPresenter;

    @NonNull
    @Override
    public OrderPresenter createPresenter() {
        getActivityComponent().inject(this);
        return orderPresenter;
    }

    @Override
    protected void afterCreatePresenter() {
        orderPresenter.setParams(0,1);
    }

    @Override
    protected BaseQuickAdapter<OrderInfo,OrderAdapter.OrderViewHolder> createAdapter(List<OrderInfo> mDatas) {
        return new OrderAdapter(mDatas);
    }
}
