package com.jkdys.doctor.ui.consult.diagnosis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.data.model.Face2FaceOrderDetail;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class DelayRecordFragment extends BaseRefreshLoadMoreFrament<Face2FaceOrderDetail.DelayRecord, BaseLoadMoreView<Face2FaceOrderDetail.DelayRecord>, DelayRecordPresenter> {


    @Inject
    DelayRecordPresenter presenter;

    @Override
    protected BaseQuickAdapter<Face2FaceOrderDetail.DelayRecord, BaseViewHolder> createAdapter(List mDatas) {
        return new DelayRecordAdapter(mDatas);
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        super.initViews(view, savedInstanceState);

        toolbar.setTitle("延期记录");
        toolbar.addLeftBackImageButton().setOnClickListener(view1 -> Objects.requireNonNull(getActivity()).finish());

        assert getArguments() != null;
        List<Face2FaceOrderDetail.DelayRecord> delayRecordList = getArguments().getParcelableArrayList("delayRecordList");
        adapter.setNewData(delayRecordList);
    }


    @NonNull
    @Override
    public DelayRecordPresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    class DelayRecordAdapter extends BaseQuickAdapter<Face2FaceOrderDetail.DelayRecord, BaseViewHolder> {

        public DelayRecordAdapter(@Nullable List<Face2FaceOrderDetail.DelayRecord> data) {
            super(R.layout.item_delay_record,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Face2FaceOrderDetail.DelayRecord item) {
            helper.setText(R.id.tv_time, item.getModifytime());
            helper.setText(R.id.tv_address, item.getRegplace());
            helper.setText(R.id.tv_date, item.getRegtime());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_customer;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected boolean enableRefresh() {
        return false;
    }
}
