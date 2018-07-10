package com.jkdys.doctor.ui.chat.doctor.search;

import android.text.TextUtils;

import com.jkdys.doctor.data.model.SearchDoctorData;
import com.jkdys.doctor.ui.base.BaseSearchLoadMorePresenter;

import javax.inject.Inject;

public class SearchDoctorPresenter extends BaseSearchLoadMorePresenter<SearchDoctorData,SearchDoctorView> {

    private String keywords;

    @Inject
    public SearchDoctorPresenter() {

    }

    @Override
    public void search(String keywords) {
        this.keywords = keywords;
        if (TextUtils.isEmpty(keywords)) {
            return;
        }
    }

    @Override
    public void loadMore(boolean pullToRefresh) {

    }
}
