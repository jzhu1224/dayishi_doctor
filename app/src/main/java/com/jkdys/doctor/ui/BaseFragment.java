package com.jkdys.doctor.ui;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.MyApplication;
import com.jkdys.doctor.R;
import com.jkdys.doctor.di.component.ActivityComponent;
import com.jkdys.doctor.di.component.DaggerActivityComponent;
import com.jkdys.doctor.di.module.ActivityModule;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhujiang on 2018/3/23.
 */

public abstract class BaseFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    protected final LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(this);
    private ActivityComponent mActivityComponent;
    protected Activity activity;
    protected boolean mIsLoadedData;

    @Nullable
    @BindView(R.id.toolbar)
    protected QMUITopBar toolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule((AppCompatActivity) activity))
                .applicationComponent(((MyApplication)getActivity().getApplication()).getApplicationComponent())
                .build();
    }

    protected abstract void initViews(View view, @Nullable Bundle saveInstanceState);

    protected abstract int getLayoutId();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this,view);
        initViews(view,savedInstanceState);
        return view;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            handleOnVisibilityChangedToUser(isVisibleToUser);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(false);
        }
    }

    /**
     * 处理对用户是否可见
     *
     * @param isVisibleToUser 可见
     */
    private void handleOnVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            // 对用户可见
            if (!mIsLoadedData) {
                mIsLoadedData = true;
                onLazyLoadOnce();
            }
            onVisibleToUser();
        } else {
            // 对用户不可见
            onInvisibleToUser();
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected void onLazyLoadOnce() {
    }

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    protected void onVisibleToUser() {
    }

    /**
     * 对用户不可见时触发该方法
     */
    protected void onInvisibleToUser() {
    }

    public String getTitle() {
        return "";
    }

    /**
     * 带动画的启动activity
     */
    public void startActivityWithAnimotion(Intent intent) {
        startActivity(intent);
        playAnimation();
    }

    /**
     * 带动画的启动activity
     */
    public void startActivityForResultWithAnimotion(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        playAnimation();
    }

    private void playAnimation() {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.side_out_left);
        }
    }

    public void showMessage(String message) {
        ToastUtil.show(getActivity(),message);
    }

    public void showDialog(String msg) {
        if (activity instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) activity).showDialog(msg);
        }
    }

}
