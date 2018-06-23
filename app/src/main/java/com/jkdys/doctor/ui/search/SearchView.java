package com.jkdys.doctor.ui.search;

import com.jkdys.doctor.ui.BaseView;

import java.util.List;

public interface SearchView extends BaseView {
    void onSearchResult(List<SearchData> searchData);
}
