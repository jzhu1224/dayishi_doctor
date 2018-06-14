package com.jkdys.doctor.ui;

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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.MyApplication;
import com.jkdys.doctor.R;
import com.jkdys.doctor.di.ApplicationContext;
import com.jkdys.doctor.di.component.ActivityComponent;
import com.jkdys.doctor.di.component.DaggerActivityComponent;
import com.jkdys.doctor.di.module.ActivityModule;
import com.jkdys.doctor.event.NetworkRequestEvent;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
    }

    protected abstract int getLayout();

    protected void afterBindView(@Nullable Bundle savedInstanceState) {

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void setContentView(int layoutResID) {
        initStatusBar(getResources().getColor(android.R.color.black));
        super.setContentView(layoutResID);
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
            QMUIStatusBarHelper.setStatusBarLightMode(this);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && QMUIStatusBarHelper.supportTransclentStatusBar6()) {
                // android 6以后可以改状态栏字体颜色，因此可以自行设置为透明
                // ZUK Z1是个另类，自家应用可以实现字体颜色变色，但没开放接口
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                // android 5不能修改状态栏字体颜色，因此直接用FLAG_TRANSLUCENT_STATUS，nexus表现为半透明
                // 魅族和小米的表现如何？
                // update: 部分手机运用FLAG_TRANSLUCENT_STATUS时背景不是半透明而是没有背景了。。。。。
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                // 采取setStatusBarColor的方式，部分机型不支持，那就纯黑了，保证状态栏图标可见
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetworkRequestEvent event) {
        switch (event.type) {
            case NetworkRequestEvent.TOAST:
                ToastUtil.show(mActivity,event.msg);
                break;
            case NetworkRequestEvent.DIALOG:

                QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(mActivity);
                builder.setTitle("提示")
                .setMessage(event.msg)
                        .addAction("确定", (dialog, index) -> dialog.dismiss())
                        .show();
                break;
            case NetworkRequestEvent.HIDING_LOADING:

                break;
            default:
                break;
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
