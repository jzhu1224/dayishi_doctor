package com.jkdys.doctor.ui.customer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.PatientInfo;
import com.jkdys.doctor.ui.BaseLoadMoreView;
import com.jkdys.doctor.ui.base.BaseRefreshLoadMoreFrament;
import java.util.List;

import javax.inject.Inject;

public class VipCustomerFragment extends BaseRefreshLoadMoreFrament<PatientInfo,BaseLoadMoreView<PatientInfo>,CustomerPresenter> {

    @Inject
    CustomerPresenter customerPresenter;

    @Override
    protected BaseQuickAdapter<PatientInfo, BaseViewHolder> createAdapter(List<PatientInfo> mDatas) {
        return new CustomerAdapter(mDatas);
    }

    @NonNull
    @Override
    public CustomerPresenter createPresenter() {
        getActivityComponent().inject(this);
        return customerPresenter;
    }

    @Override
    protected void afterCreatePresenter() {
        super.afterCreatePresenter();
        customerPresenter.setGrouptype(1);
    }

    class CustomerAdapter extends BaseQuickAdapter<PatientInfo,BaseViewHolder> {

        public CustomerAdapter(@Nullable List<PatientInfo> data) {
            super(R.layout.item_my_patient, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PatientInfo patientInfo) {
            helper.setText(R.id.tv_name, patientInfo.getPatientname());
            helper.setText(R.id.tv_age, patientInfo.getAge());
            helper.setText(R.id.tv_gender, patientInfo.getGender());
            ImageLoader.with(getContext()).placeholder(R.drawable.img_doctor).load(patientInfo.getHeadurl()).into(helper.getView(R.id.img_header));
        }

    }
}
