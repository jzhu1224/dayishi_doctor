package com.jkdys.doctor.ui.myAccount.bank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;
import com.jkdys.doctor.utils.BankUtil;
import java.util.List;

import javax.inject.Inject;

public class SupportBankListFragment extends BaseRefreshLoadMoreFrament<BankCardInfo,BaseLoadMoreView<BankCardInfo>,SupportBankListPresenter> {

    @Inject
    SupportBankListPresenter supportBankListPresenter;

    @Override
    protected BaseQuickAdapter<BankCardInfo, BaseViewHolder> createAdapter(List<BankCardInfo> mDatas) {
        return new BankCardInfoAdapter(mDatas);
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        super.initViews(view, savedInstanceState);
        toolbar.setTitle("支持银行");
        toolbar.addLeftBackImageButton().setOnClickListener(view1 -> getActivity().finish());
    }

    @NonNull
    @Override
    public SupportBankListPresenter createPresenter() {
        getActivityComponent().inject(this);
        return supportBankListPresenter;
    }

    class BankCardInfoAdapter extends BaseQuickAdapter<BankCardInfo, BaseViewHolder> {

        public BankCardInfoAdapter(@Nullable List<BankCardInfo> data) {
            super(R.layout.item_support_bank,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BankCardInfo item) {
            helper.setText(R.id.tv_bank_name,item.getBankname());
            helper.setImageResource(R.id.ivBankIcon, BankUtil.getBankIconById(item.getBankid()));
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
}
