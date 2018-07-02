package com.jkdys.doctor.ui.consult;

import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;
import com.jkdys.doctor.ui.consult.adapter.OrderAdapter;

import java.util.List;

import javax.inject.Inject;

public class DoorFragment extends BaseRefreshLoadMoreFrament<OrderInfo,BaseLoadMoreView<OrderInfo>,OrderPresenter> {

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
        super.afterCreatePresenter();
        orderPresenter.setParams(0,2);
    }

    @Override
    protected BaseQuickAdapter<OrderInfo, BaseViewHolder> createAdapter(List<OrderInfo> mDatas) {
        return new OrderAdapter(mDatas);
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.show(getActivity(),msg);
    }
}
