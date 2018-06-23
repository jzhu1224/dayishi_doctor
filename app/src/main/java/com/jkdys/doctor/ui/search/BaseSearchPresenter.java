package com.jkdys.doctor.ui.search;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import java.util.List;

public abstract class BaseSearchPresenter<V extends SearchView> extends MvpBasePresenter<V> {
    public abstract void search(String... params);
    protected void onSearchDataReturn(List<SearchData> searchDataList) {
        getView().onSearchResult(searchDataList);
    }
}
