package com.jkdys.doctor.ui.chat.doctor.search;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.data.model.SearchDoctorData;
import com.jkdys.doctor.ui.base.BaseSearchRefreshLoadMoreFragment;
import com.jkdys.doctor.ui.chat.doctor.DoctorDetailActivity;

import java.util.List;
import javax.inject.Inject;

public class SearchDoctorFragment extends BaseSearchRefreshLoadMoreFragment<SearchDoctorData,SearchDoctorView, SearchDoctorPresenter> implements SearchDoctorView {

    @Inject
    SearchDoctorPresenter presenter;

    @Override
    protected BaseQuickAdapter<SearchDoctorData, BaseViewHolder> createAdapter(List<SearchDoctorData> mDatas) {
        return new SearchDoctorAdapter(mDatas);
    }

    @Override
    protected void onLazyLoadOnce() {

    }

    @Override
    protected void onItemClicked(BaseQuickAdapter adapter, View view, int position) {
        SearchDoctorData searchDoctorData = (SearchDoctorData) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), DoctorDetailActivity.class);
        intent.putExtra("doctorId", searchDoctorData.getDid());
        startActivity(intent);
    }

    @NonNull
    @Override
    public SearchDoctorPresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }


}
