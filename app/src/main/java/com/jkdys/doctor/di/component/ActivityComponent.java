package com.jkdys.doctor.di.component;

import com.jkdys.doctor.di.PerActivity;
import com.jkdys.doctor.di.module.ActivityModule;
import com.jkdys.doctor.ui.consult.ConsultFragment;
import com.jkdys.doctor.ui.consult.DoorFragment;
import com.jkdys.doctor.ui.consult.PhoneFragment;
import com.jkdys.doctor.ui.customer.CustomerFragment;
import com.jkdys.doctor.ui.login.LoginActivity;
import com.jkdys.doctor.ui.login.LoginSmsActivity;
import com.jkdys.doctor.ui.main.MainActivity;
import com.jkdys.doctor.ui.mine.MineFragment;
import com.jkdys.doctor.ui.myAccount.MyAccountActivity;
import com.jkdys.doctor.ui.search.SearchDepartmentActivity;
import com.jkdys.doctor.ui.search.SearchDepartmentPresenter;
import com.jkdys.doctor.ui.verify.personalInfo.PersonalInfoActivity;
import com.jkdys.doctor.ui.verify.userVerify.IdentityActivity;

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
    void inject(MineFragment mineFragment);
    void inject(LoginActivity loginActivity);
    void inject(PhoneFragment phoneFragment);
    void inject(DoorFragment doorFragment);
    void inject(LoginSmsActivity loginSmsActivity);
    void inject(MyAccountActivity myAccountActivity);
    void inject(IdentityActivity identityActivity);
    void inject(PersonalInfoActivity identityActivity);
    void inject(SearchDepartmentActivity identityActivity);
}
