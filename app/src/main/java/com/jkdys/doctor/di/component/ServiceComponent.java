package com.jkdys.doctor.di.component;

import com.jkdys.doctor.SyncDataService;
import com.jkdys.doctor.di.PerActivity;
import com.jkdys.doctor.di.module.ServiceModule;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ServiceModule.class)
public interface ServiceComponent {
    void inject(SyncDataService syncDataService);
}
