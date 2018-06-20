package com.jkdys.doctor.di.component;

import android.content.Context;

import com.jkdys.doctor.MyApplication;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.di.ApplicationContext;
import com.jkdys.doctor.di.module.ApiServiceModule;
import com.jkdys.doctor.di.module.ApplicationModule;
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

    DaYiShiServiceApi getDaYiShiServiceApi();

    Gson getGson();

    LoginInfoUtil getLoginInfoUtil();
}
