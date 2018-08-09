package com.jkdys.doctor.di.module;

import android.app.Activity;
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

    private Activity mAppCompatActivity;

    public ActivityModule(Activity mAppCompatActivity) {
        this.mAppCompatActivity = mAppCompatActivity;
    }

    @Provides
    Activity provideActivity() {
        return mAppCompatActivity;
    }

    @Provides
    FragmentManager provideSupportFragmentManager(Activity mAppCompatActivity) {
        return mAppCompatActivity.getFragmentManager();
    }

    @Provides
    LifecycleProvider<Lifecycle.Event> providerLifecycleProvider(Activity mAppCompatActivity) {
        return AndroidLifecycle.createLifecycleProvider((AppCompatActivity)mAppCompatActivity);
    }
}
