package com.dayishiapp.doctor.di.component;

import android.content.Context;

import com.dayishiapp.doctor.MyApplication;
import com.dayishiapp.doctor.di.ApplicationContext;
import com.dayishiapp.doctor.di.module.ApiServiceModule;
import com.dayishiapp.doctor.di.module.ApplicationModule;
import com.google.gson.Gson;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by zhujiang on 2018/3/19.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiServiceModule.class})
public interface ApplicationComponent {

    void inject(MyApplication myApplication);

    @ApplicationContext
    Context getContext();

    //KaiYanServiceApi getKaiYanServiceApi();

    Gson getGson();
}
