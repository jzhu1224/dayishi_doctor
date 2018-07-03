package com.jkdys.doctor.di.component;

import com.jkdys.doctor.di.PerActivity;
import com.jkdys.doctor.di.module.ActivityModule;
import com.jkdys.doctor.ui.chat.YunFragment;
import com.jkdys.doctor.ui.consult.ConsultFragment;
import com.jkdys.doctor.ui.consult.DoorFragment;
import com.jkdys.doctor.ui.consult.PhoneFragment;
import com.jkdys.doctor.ui.customer.CustomerFragment;
import com.jkdys.doctor.ui.login.LoginActivity;
import com.jkdys.doctor.ui.login.LoginSmsActivity;
import com.jkdys.doctor.ui.main.MainActivity;
import com.jkdys.doctor.ui.mine.MineFragment;
import com.jkdys.doctor.ui.myAccount.MyAccountActivity;
import com.jkdys.doctor.ui.myAccount.bank.BankCardListActivity;
import com.jkdys.doctor.ui.myAccount.bank.SupportBankListFragment;
import com.jkdys.doctor.ui.order.AllOrderFragment;
import com.jkdys.doctor.ui.order.CanceledOrderFragment;
import com.jkdys.doctor.ui.order.CompletedOrderFragment;
import com.jkdys.doctor.ui.order.InvalidedOrderFragment;
import com.jkdys.doctor.ui.order.ProcessOrderFragment;
import com.jkdys.doctor.ui.search.searchDepartment.SearchDepartmentActivity;
import com.jkdys.doctor.ui.search.searchHospital.SearchHospitalActivity;
import com.jkdys.doctor.ui.search.searchPhysiciansTitle.SearchPhysiciansTitleActivity;
import com.jkdys.doctor.ui.search.selectArea.SelectAreaActivity;
import com.jkdys.doctor.ui.verify.JumpHelper;
import com.jkdys.doctor.ui.verify.doctorVerify.DoctorVerifyActivity;
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
    void inject(SearchPhysiciansTitleActivity identityActivity);
    void inject(SearchDepartmentActivity searchDepartmentActivity);
    void inject(SelectAreaActivity selectAreaActivity);
    void inject(SearchHospitalActivity searchHospitalActivity);
    void inject(YunFragment yunFragment);
    void inject(DoctorVerifyActivity doctorVerifyActivity);
    void inject(AllOrderFragment allOrderFragment);
    void inject(ProcessOrderFragment allOrderFragment);
    void inject(CompletedOrderFragment completedOrderFragment);
    void inject(CanceledOrderFragment completedOrderFragment);
    void inject(InvalidedOrderFragment completedOrderFragment);
    void inject(BankCardListActivity bankCardListActivity);
    void inject(SupportBankListFragment supportBankListFragment);
}
