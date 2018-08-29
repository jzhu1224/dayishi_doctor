package com.jkdys.doctor;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.jkdys.doctor.core.chat.ChatHelper;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.di.component.ApplicationComponent;
import com.jkdys.doctor.di.component.DaggerApplicationComponent;
import com.jkdys.doctor.di.module.ApplicationModule;
import com.jkdys.doctor.utils.AppLogger;

/**
 * Created by zhujiang on 2018/3/19.
 */

public class MyApplication extends MultiDexApplication {

    ApplicationComponent applicationComponent;

    private static MyApplication instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);

        AppLogger.initLogger();
        ChatHelper.getInstance().init(this);
        ImageLoader.init(this);
        Fresco.initialize(this);
        Stetho.initializeWithDefaults(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
