package com.jkdys.doctor.ui.order;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chairoad.framework.util.LogUtil;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;
import com.jkdys.doctor.ui.consult.OrderPresenter;
import com.jkdys.doctor.ui.consult.adapter.OrderAdapter;
import com.jkdys.doctor.ui.consult.diagnosis.DiagnosisOnPhoneActivity;
import com.jkdys.doctor.ui.consult.diagnosis.diagnosisFTF.DiagnosisFace2FaceActivity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class AllOrderFragment extends BaseRefreshLoadMoreFrament<OrderInfo,BaseLoadMoreView<OrderInfo>,OrderPresenter> {

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
        orderPresenter.setParams("-1","0");
    }

    @Override
    protected BaseQuickAdapter<OrderInfo,BaseViewHolder> createAdapter(List<OrderInfo> mDatas) {
        return new OrderAdapter(mDatas);
    }

    @Override
    protected void onItemClicked(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClicked(adapter, view, position);

        OrderInfo orderInfo = (OrderInfo) (adapter.getItem(position));

        if (orderInfo == null)
            return;

        Intent intent;

        if (orderInfo.getOrdertype().equals("1")) {
            intent = new Intent(getActivity(), DiagnosisOnPhoneActivity.class);
        } else {
            intent = new Intent(getActivity(), DiagnosisFace2FaceActivity.class);
        }

        intent.putExtra("orderId", orderInfo.getOrderid());
        startActivity(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("AllOrderFragment","onDestroy:"+this.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e("AllOrderFragment","onDestroyView:"+this.toString());
    }
}
