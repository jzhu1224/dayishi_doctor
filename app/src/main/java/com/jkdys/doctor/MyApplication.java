package com.jkdys.doctor;


import android.support.multidex.MultiDexApplication;

import com.jkdys.doctor.di.component.ApplicationComponent;
import com.jkdys.doctor.di.component.DaggerApplicationComponent;
import com.jkdys.doctor.di.module.ApplicationModule;
import com.jkdys.doctor.utils.AppLogger;

/**
 * Created by zhujiang on 2018/3/19.
 */

public class MyApplication extends MultiDexApplication {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);

        AppLogger.initLogger();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}