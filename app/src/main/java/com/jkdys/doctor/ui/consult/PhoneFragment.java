package com.jkdys.doctor.ui.consult;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;
import com.jkdys.doctor.ui.consult.adapter.OrderAdapter;
import com.jkdys.doctor.ui.consult.diagnosis.DiagnosisOnPhoneActivity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class PhoneFragment extends BaseRefreshLoadMoreFrament<OrderInfo,BaseLoadMoreView<OrderInfo>,OrderPresenter> {

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
        orderPresenter.setParams("0","1");
    }

    @Override
    protected void onItemClicked(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClicked(adapter, view, position);
        Intent intent = new Intent(getActivity(), DiagnosisOnPhoneActivity.class);
        intent.putExtra("orderId", ((OrderInfo) Objects.requireNonNull(adapter.getItem(position))).getOrderid());
        Objects.requireNonNull(getActivity()).startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadData(true,true);
    }

    @Override
    protected BaseQuickAdapter<OrderInfo,BaseViewHolder> createAdapter(List<OrderInfo> mDatas) {
        return new OrderAdapter(mDatas);
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.show(getActivity(),msg);
    }

    @Override
    public void setData(List<OrderInfo> data) {
        super.setData(data);
    }
}
