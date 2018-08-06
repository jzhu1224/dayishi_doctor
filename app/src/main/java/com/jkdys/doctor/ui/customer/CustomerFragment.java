package com.jkdys.doctor.ui.customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.PatientInfo;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;

import java.util.List;

import javax.inject.Inject;

public class CustomerFragment extends BaseRefreshLoadMoreFrament<MyPatientSection,BaseLoadMoreView<MyPatientSection>,CustomerPresenter> {

    @Inject
    CustomerPresenter consultPresenter;

    @Override
    protected void initViews(View view, Bundle saveInstanceState) {
        super.initViews(view, saveInstanceState);
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
    protected BaseQuickAdapter<MyPatientSection, BaseViewHolder> createAdapter(List<MyPatientSection> mDatas) {
        return new CustomerAdapter(mDatas);
    }

    @NonNull
    @Override
    public CustomerPresenter createPresenter() {
        getActivityComponent().inject(this);
        return consultPresenter;
    }

    class CustomerAdapter extends BaseSectionQuickAdapter<MyPatientSection,BaseViewHolder> {

        public CustomerAdapter(@Nullable List<MyPatientSection> data) {
            super(R.layout.item_my_patient,R.layout.item_section_header, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyPatientSection item) {
            PatientInfo patientInfo = item.t;
            helper.setText(R.id.tv_name, patientInfo.getPatientname());
            helper.setText(R.id.tv_age, patientInfo.getAge());
            helper.setText(R.id.tv_gender, patientInfo.getGender());
            ImageLoader.with(getContext()).placeholder(R.drawable.img_doctor).load(patientInfo.getHeadurl()).into(helper.getView(R.id.img_header));
        }

        @Override
        protected void convertHead(BaseViewHolder helper, MyPatientSection item) {
            helper.setText(R.id.tv_header, item.header);
        }
    }
}
