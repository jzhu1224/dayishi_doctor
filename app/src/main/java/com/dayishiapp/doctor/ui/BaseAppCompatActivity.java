package com.dayishiapp.doctor.ui;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dayishiapp.doctor.MyApplication;
import com.dayishiapp.doctor.R;
import com.dayishiapp.doctor.di.ApplicationContext;
import com.dayishiapp.doctor.di.component.ActivityComponent;
import com.dayishiapp.doctor.di.component.DaggerActivityComponent;
import com.dayishiapp.doctor.di.module.ActivityModule;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.trello.rxlifecycle2.LifecycleProvider;

import javax.inject.Inject;

import butterknife.BindView;
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

    @BindView(R.id.toolbar)
    @Nullable
    protected QMUITopBar toolbar;

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
        initStatusBar(getResources().getColor(R.color.color_white));
    }

    protected void initStatusBar(int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT
                // Essential Phone 不支持沉浸式，否则系统又不从状态栏下方开始布局又给你下发 WindowInsets
                && !Build.BRAND.toLowerCase().contains("essential")) {
            // 版本小于4.4，绝对不考虑沉浸式
            return;
        }
        // 小米和魅族4.4 以上版本支持沉浸式
        if (QMUIDeviceHelper.isMeizu() || QMUIDeviceHelper.isMIUI()) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
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

//    protected void initToolBar(Toolbar toolbar) {
//        if (toolbar == null) {
//            return;
//        }
//        setSupportActionBar(toolbar);
//        ActionBar supportActionBar = getSupportActionBar();
//        if (supportActionBar != null) {
//            supportActionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        toolbar.setContentInsetStartWithNavigation(0);
//    }

}
