package com.jkdys.doctor.ui.search.searchDepartment;

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

public class SearchDepartmentActivity extends BaseSearchActivity<SearchView,SearchDepartmentPresenter> {

    public static final String KEY_HOSPITAL_ID = "key_hospital_id";
    public static final String KEY_HOSPITAL_NAME = "key_hospital_name";

    @Inject
    SearchDepartmentPresenter searchDepartmentPresenter;

    String hospitalId,hospitalName;
    Filter filter;

    List<SearchData> searchDataList;

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        hospitalId = getIntent().getStringExtra(KEY_HOSPITAL_ID);
        hospitalName = getIntent().getStringExtra(KEY_HOSPITAL_NAME);
        edtContent.setHint("搜索科室");

        toolbar.setTitle(hospitalName+"->科室");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = searchDataList.size();
                    results.values = searchDataList;
                } else {//do the search
                    List<SearchData> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString();
                    for (SearchData o : searchDataList)
                        if (o.getText().contains(searchStr)) resultsData.add(o);
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mData.clear();
                mData.addAll((ArrayList<SearchData>) filterResults.values);
                searchAdapter.notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public SearchDepartmentPresenter createPresenter() {
        getActivityComponent().inject(this);
        return searchDepartmentPresenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchDepartmentPresenter.search(hospitalId);
    }

    @Override
    protected void onSearch(String text) {
        searchAdapter.getFilter().filter(text);
    }

    @Override
    protected void onTextChangedEmpty() {
        rlClear.setVisibility(View.GONE);
        mData.clear();
        mData.addAll(searchDataList);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchResult(List<SearchData> searchData) {
        searchDataList = searchData;
        mData.clear();
        mData.addAll(searchData);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onClearClicked() {
        rlClear.setVisibility(View.GONE);
        edtContent.setText("");
        mData.clear();
        mData.addAll(searchDataList);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    protected Filter getFilter() {
        return filter;
    }
}
