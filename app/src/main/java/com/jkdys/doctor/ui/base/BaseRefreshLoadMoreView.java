package com.jkdys.doctor.ui.base;

import com.jkdys.doctor.ui.BaseView;

import java.util.List;

public interface BaseRefreshLoadMoreView<T> extends BaseView {
    void onLoadMoreSuccess(List<T> mList, int page);
}
