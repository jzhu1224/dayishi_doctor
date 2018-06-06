package com.dayishiapp.doctor.di.component;

import com.dayishiapp.doctor.di.PerActivity;
import com.dayishiapp.doctor.di.module.ActivityModule;
import com.dayishiapp.doctor.ui.consult.ConsultFragment;
import com.dayishiapp.doctor.ui.customer.CustomerFragment;
import com.dayishiapp.doctor.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by zhujiang on 2018/3/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(ConsultFragment consultFragment);
    void inject(CustomerFragment consultFragment);
}
