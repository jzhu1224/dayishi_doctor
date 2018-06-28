package com.jkdys.doctor.ui.search.searchHospital;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Filter;
import android.widget.TextView;

import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.search.BaseSearchActivity;
import com.jkdys.doctor.ui.search.SearchData;
import com.jkdys.doctor.ui.search.SearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchHospitalActivity extends BaseSearchActivity<SearchView,SearchHospitalPresenter> {

    @Inject
    SearchHospitalPresenter searchHospitalPresenter;

    public static final String KEY_PROVINCE_ID = "key_province_id";
    public static final String KEY_CITY_ID = "key_city_id";
    public static final String KEY_AREA_NAME = "key_area_name";

    @BindView(R.id.tv_area)
    TextView tvArea;

    String provinceId, cityId, areaName;

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        toolbar.setTitle("医院");
        edtContent.setHint("搜索医院");

        provinceId = getIntent().getStringExtra(KEY_PROVINCE_ID);
        cityId = getIntent().getStringExtra(KEY_CITY_ID);
        areaName = getIntent().getStringExtra(KEY_AREA_NAME);

        if (areaName.length()>5) {
            areaName = areaName.substring(0,5).concat("...");
        }

        tvArea.setText(areaName);

        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_hospital;
    }

    @NonNull
    @Override
    public SearchHospitalPresenter createPresenter() {
        getActivityComponent().inject(this);
        return searchHospitalPresenter;
    }

    @Override
    protected void onSearch(String text) {
        if (text.length() >3)
            searchHospitalPresenter.search(provinceId,cityId,text);
    }

    @OnClick(R.id.ll_area)
    void onAreaClicked() {
        finish();
    }
}
