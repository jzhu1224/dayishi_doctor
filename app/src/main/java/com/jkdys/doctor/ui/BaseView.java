package com.jkdys.doctor.ui;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by zhujiang on 2018/3/23.
 */

public interface BaseView extends MvpView {
    
    void showLoading(boolean pullToRefresh);

    void showContent();

    void showMessage(String msg);

    void showError(String message);

    void showDialog(String msg);
}
