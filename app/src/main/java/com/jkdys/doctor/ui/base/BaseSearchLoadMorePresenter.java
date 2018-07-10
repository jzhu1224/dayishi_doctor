package com.jkdys.doctor.ui.base;

import com.jkdys.doctor.ui.BaseLoadMoreView;

public abstract class BaseSearchLoadMorePresenter<T,V extends BaseLoadMoreView<T>> extends BaseRefreshLoadMorePresenter<V,T>{
  public abstract void search(String keywords);
  public abstract void loadMore(boolean pullToRefresh);
}
