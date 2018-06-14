package com.jkdys.doctor.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkdys.doctor.data.network.Api;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.di.ApplicationContext;
import com.jkdys.doctor.utils.CommonHeaderInterceptor;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.orhanobut.logger.Logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiServiceModule {
    private static final String TAG = ApiServiceModule.class.getSimpleName();


    @Singleton
    @Provides
    SharedPrefsCookiePersistor providesSharedPrefsCookiePersistor(@ApplicationContext Context context) {
        return new SharedPrefsCookiePersistor(context);
    }

    @Singleton
    @Provides
    SetCookieCache providesSetCookieCache() {
        return new SetCookieCache();
    }

    @Singleton
    @Provides
    PersistentCookieJar providesPersistentCookieJar(SharedPrefsCookiePersistor sharedPrefsCookiePersistor, SetCookieCache setCookieCache) {
        return new PersistentCookieJar(setCookieCache, sharedPrefsCookiePersistor);
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Logger.t(TAG).d("HttpLog:" + message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient(CommonHeaderInterceptor commonHeaderInterceptor, HttpLoggingInterceptor httpLoggingInterceptor,
                                      PersistentCookieJar persistentCookieJar) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(commonHeaderInterceptor);
        builder.addInterceptor(httpLoggingInterceptor);
        builder.cookieJar(persistentCookieJar);
        return builder.build();

    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Api.KAIYAN_DOMAIN)
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    DaYiShiServiceApi providesDaYiShiServiceApi(Retrofit retrofit) {
        return retrofit.create(DaYiShiServiceApi.class);
    }
}
