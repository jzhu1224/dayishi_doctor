package com.jkdys.doctor.ui.search.searchHospital;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Filter;

import com.jkdys.doctor.ui.search.BaseSearchActivity;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.SearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchHospitalActivity extends BaseSearchActivity<SearchView,SearchHospitalPresenter> {

    @Inject
    SearchHospitalPresenter searchDepartmentPresenter;

    public static final String KEY_PROVINCE_ID = "key_province_id";

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("医院");
        edtContent.setHint("搜索医院");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @NonNull
    @Override
    public SearchHospitalPresenter createPresenter() {
        getActivityComponent().inject(this);
        return searchDepartmentPresenter;
    }

    @Override
    protected void onSearch(String text) {

    }
}
