package com.jkdys.doctor.di.module;

import android.app.Application;
import android.content.Context;

import com.jkdys.doctor.di.ApplicationContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by zhujiang on 2018/3/19.
 */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    public Application providesApplication() {
        return  mApplication;
    }

    @Provides
    @ApplicationContext
    Context providesContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    Gson providesGson() {
        return  new GsonBuilder().create();
    }
}
