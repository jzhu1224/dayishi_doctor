package com.dayishiapp.doctor.di.module;

import android.app.FragmentManager;
import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zhujiang on 2018/3/19.
 */
@Module
public class ActivityModule {

    private AppCompatActivity mAppCompatActivity;

    public ActivityModule(AppCompatActivity mAppCompatActivity) {
        this.mAppCompatActivity = mAppCompatActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mAppCompatActivity;
    }

    @Provides
    FragmentManager provideSupportFragmentManager(AppCompatActivity mAppCompatActivity) {
        return mAppCompatActivity.getFragmentManager();
    }

    @Provides
    LifecycleProvider<Lifecycle.Event> providerLifecycleProvider(AppCompatActivity mAppCompatActivity) {
        return AndroidLifecycle.createLifecycleProvider(mAppCompatActivity);
    }

}
