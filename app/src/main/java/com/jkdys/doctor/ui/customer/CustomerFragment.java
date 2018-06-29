package com.jkdys.doctor.ui.customer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;

import java.util.List;

import javax.inject.Inject;

public class CustomerFragment extends BaseRefreshLoadMoreFrament<MyPatientSection,BaseLoadMoreView<MyPatientSection>,CustomerPresenter,CustomerFragment.CustomerViewHolder> {

    @Inject
    CustomerPresenter consultPresenter;

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        toolbar.setTitle("我的患者");
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_customer;
    }

    @Override
    protected BaseQuickAdapter<MyPatientSection, CustomerViewHolder> createAdapter(List<MyPatientSection> mDatas) {
        return new CustomerAdapter(mDatas);
    }

    @NonNull
    @Override
    public CustomerPresenter createPresenter() {
        getActivityComponent().inject(this);
        return consultPresenter;
    }

    class CustomerAdapter extends BaseSectionQuickAdapter<MyPatientSection,CustomerViewHolder> {

        public CustomerAdapter(@Nullable List<MyPatientSection> data) {
            super(R.layout.item_my_patient,R.layout.item_section_header, data);
        }

        @Override
        protected void convert(CustomerViewHolder helper, MyPatientSection item) {

        }

        @Override
        protected void convertHead(CustomerViewHolder helper, MyPatientSection item) {
            helper.setText(R.id.tv_header, item.header);
        }
    }

    public static class CustomerViewHolder extends BaseViewHolder {

        public CustomerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
