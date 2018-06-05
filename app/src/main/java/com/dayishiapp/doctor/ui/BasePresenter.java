package com.dayishiapp.doctor.ui;

import android.arch.lifecycle.Lifecycle;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by zhujiang on 2018/3/21.
 */

public class BasePresenter {
    protected LifecycleProvider<Lifecycle.Event> provider;

    public BasePresenter(LifecycleProvider<Lifecycle.Event> provider) {
        this.provider = provider;
    }
}
