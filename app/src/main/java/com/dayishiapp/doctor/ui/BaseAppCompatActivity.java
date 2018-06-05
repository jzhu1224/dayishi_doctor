package com.dayishiapp.doctor.ui;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.dayishiapp.doctor.MyApplication;
import com.dayishiapp.doctor.R;
import com.dayishiapp.doctor.di.ApplicationContext;
import com.dayishiapp.doctor.di.component.ActivityComponent;
import com.dayishiapp.doctor.di.component.DaggerActivityComponent;
import com.dayishiapp.doctor.di.module.ActivityModule;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.trello.rxlifecycle2.LifecycleProvider;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by zhujiang on 2018/3/19.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Inject
    protected LifecycleProvider<Lifecycle.Event> lifecycleProvider;

    protected boolean existActivityWithAnimation = true;
    private ActivityComponent mActivityComponent;

    protected AppCompatActivity mActivity;

    @ApplicationContext
    protected Context appContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MyApplication)getApplication()).getApplicationComponent())
                .build();
        setContentView(getLayout());
        ButterKnife.bind(this);
        afterBindView(savedInstanceState);
    }

    protected abstract int getLayout();

    protected void afterBindView(@Nullable Bundle savedInstanceState) {

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    /**
     * 带动画的启动activity
     */
    public void startActivityWithAnimotion(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.side_out_left);
    }

    /**
     * 带动画的启动activity
     */
    public void startActivityForResultWithAnimotion(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_right, R.anim.side_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (existActivityWithAnimation) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.side_out_right);
        }
    }

    protected void initToolBar(Toolbar toolbar) {
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setContentInsetStartWithNavigation(0);
    }

}
