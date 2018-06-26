package com.jkdys.doctor.ui.base;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.ui.BaseLoadMoreView;

public abstract class BaseRefreshLoadMorePresenter<V extends BaseLoadMoreView<T>, T> extends MvpBasePresenter<V> {
    public abstract void loadMore(boolean pullToRefresh);
}
