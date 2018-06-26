package com.jkdys.doctor.ui.consult;

import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;

import java.util.List;

import javax.inject.Inject;

public class PhoneFragment extends BaseRefreshLoadMoreFrament<OrderInfo,BaseLoadMoreView<OrderInfo>,ConsultPresenter> {

    @Inject
    ConsultPresenter consultPresenter;

    @NonNull
    @Override
    public ConsultPresenter createPresenter() {
        getActivityComponent().inject(this);
        return consultPresenter;
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_consult;
    }

    @Override
    protected BaseQuickAdapter<OrderInfo, BaseViewHolder> createAdapter(List<OrderInfo> mDatas) {
        return null;
    }

    @Override
    public void showMessage(String msg) {
        ToastUtil.show(getActivity(),msg);
    }
}
