package com.jkdys.doctor.ui.scan;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chairoad.framework.util.LogUtil;
import com.google.zxing.client.android.ViewfinderView;
import com.jkdys.doctor.MyApplication;
import com.jkdys.doctor.R;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.di.component.ActivityComponent;
import com.jkdys.doctor.di.component.DaggerActivityComponent;
import com.jkdys.doctor.di.module.ActivityModule;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.scan.framework.ui.BaseScanActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yanxin on 2018/6/26.
 */

public class ScanActivity extends BaseScanActivity {

    @BindView(R.id.toolbar)
    QMUITopBar titleBar;

    @BindView(R.id.my_toolbar)
    FrameLayout myToolbar;

    @BindView(R.id.preview_view)
    SurfaceView surfaceView;
    @BindView(R.id.viewfinder_view)
    ViewfinderView viewfinderView;

    @Inject
    DaYiShiServiceApi api;

    @Override
    public void setContentView(int layoutResID) {
        initStatusBar(getResources().getColor(android.R.color.black));
        QMUIStatusBarHelper.setStatusBarDarkMode(ScanActivity.this);
        super.setContentView(layoutResID);

        ActivityComponent mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MyApplication)getApplication()).getApplicationComponent())
                .build();
        mActivityComponent.inject(this);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 14) {
            myToolbar.setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(ScanActivity.this),0,0);
        }
        myToolbar.setBackgroundColor(getResources().getColor(R.color.color_transparent));
        titleBar.setBackgroundAlpha(0);
        titleBar.addLeftImageButton(R.drawable.ic_back_white,R.id.qmui_topbar_item_left_back).setOnClickListener(view -> finish());

        TextView textView = new TextView(ScanActivity.this);
        textView.setText("扫一扫");
        textView.setTextColor(getResources().getColor(R.color.color_white));

        titleBar.setCenterView(textView);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_capture;
    }

    @Override
    public ViewfinderView getFinderView() {
        return viewfinderView;
    }

    @Override
    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    @Override
    public void onScan(String code) {
        LogUtil.e("Scan","code: "+code);
        if(!TextUtils.isEmpty(code)) {

        }
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

}
