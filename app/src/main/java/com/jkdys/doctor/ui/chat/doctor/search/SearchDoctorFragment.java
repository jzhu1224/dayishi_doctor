package com.jkdys.doctor.ui.chat.doctor.search;

import android.support.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jkdys.doctor.data.model.SearchDoctorData;
import com.jkdys.doctor.ui.base.BaseSearchRefreshLoadMoreFragment;
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
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected boolean enableRefresh() {
        return false;
    }

    @NonNull
    @Override
    public SearchDoctorPresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }


}
