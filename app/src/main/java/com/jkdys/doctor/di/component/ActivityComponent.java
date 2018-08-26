package com.jkdys.doctor.di.component;

import com.jkdys.doctor.di.PerActivity;
import com.jkdys.doctor.di.module.ActivityModule;
import com.jkdys.doctor.ui.chat.MyFriendsFragment;
import com.jkdys.doctor.ui.chat.YunFragment;
import com.jkdys.doctor.ui.chat.doctor.DoctorDetailActivity;
import com.jkdys.doctor.ui.chat.doctor.search.SearchDoctorFragment;
import com.jkdys.doctor.ui.consult.ConsultFragment;
import com.jkdys.doctor.ui.consult.DoorFragment;
import com.jkdys.doctor.ui.consult.PhoneFragment;
import com.jkdys.doctor.ui.consult.diagnosis.DelayRecordFragment;
import com.jkdys.doctor.ui.consult.diagnosis.DiagnosisOnPhoneActivity;
import com.jkdys.doctor.ui.consult.diagnosis.diagnosisFTF.DiagnosisFace2FaceActivity;
import com.jkdys.doctor.ui.customer.NormalCustomerFragment;
import com.jkdys.doctor.ui.customer.VipCustomerFragment;
import com.jkdys.doctor.ui.fee.Face2FaceDiagnosisFeeActivity;
import com.jkdys.doctor.ui.fee.PhoneDiagnosisFeeActivity;
import com.jkdys.doctor.ui.fee.PhoneVipDiagnosisFeeActivity;
import com.jkdys.doctor.ui.login.LoginActivity;
import com.jkdys.doctor.ui.login.LoginSmsActivity;
import com.jkdys.doctor.ui.main.MainActivity;
import com.jkdys.doctor.ui.mine.MineFragment;
import com.jkdys.doctor.ui.myAccount.BindCardSmsActivity;
import com.jkdys.doctor.ui.myAccount.MyAccountActivity;
import com.jkdys.doctor.ui.myAccount.bank.BankCardListActivity;
import com.jkdys.doctor.ui.myAccount.bank.BindBankCardActivity;
import com.jkdys.doctor.ui.myAccount.bank.BindCardVerifyCardInfoActivity;
import com.jkdys.doctor.ui.myAccount.bank.SupportBankListFragment;
import com.jkdys.doctor.ui.myAccount.bank.tradeDetail.TradeDetailActivity;
import com.jkdys.doctor.ui.myAccount.bank.tradeRecord.TradeRecordFragment;
import com.jkdys.doctor.ui.myAccount.bank.withdraw.WithdrawActivity;
import com.jkdys.doctor.ui.order.AllOrderFragment;
import com.jkdys.doctor.ui.order.CanceledOrderFragment;
import com.jkdys.doctor.ui.order.CompletedOrderFragment;
import com.jkdys.doctor.ui.order.InvalidedOrderFragment;
import com.jkdys.doctor.ui.order.ProcessOrderFragment;
import com.jkdys.doctor.ui.profile.EditGoodAtTagActivity;
import com.jkdys.doctor.ui.profile.EditPersonalIntroduceActivity;
import com.jkdys.doctor.ui.profile.PersonalProfileActivity;
import com.jkdys.doctor.ui.profile.changeMobile.ChangeMobileActivity;
import com.jkdys.doctor.ui.scan.ScanActivity;
import com.jkdys.doctor.ui.search.searchDepartment.SearchDepartmentActivity;
import com.jkdys.doctor.ui.search.searchHospital.SearchHospitalActivity;
import com.jkdys.doctor.ui.search.searchPhysiciansTitle.SearchPhysiciansTitleActivity;
import com.jkdys.doctor.ui.search.selectArea.SelectAreaActivity;
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
    void inject(BindBankCardActivity bindBankCardActivity);
    void inject(BindCardVerifyCardInfoActivity bindCardVerifyCardInfoActivity);
    void inject(BindCardSmsActivity  bindCardSmsActivity);
    void inject(PersonalProfileActivity personalProfileActivity);
    void inject(DoctorDetailActivity doctorDetailActivity);
    void inject(WithdrawActivity withdrawActivity);
    void inject(SearchDoctorFragment searchDoctorFragment);
    void inject(DiagnosisOnPhoneActivity diagnosisOnPhoneActivity);
    void inject(DiagnosisFace2FaceActivity diagnosisFace2FaceActivity);
    void inject(EditPersonalIntroduceActivity editPersonalIntroduceActivity);
    void inject(EditGoodAtTagActivity editGoodAtTagActivity);
    void inject(ChangeMobileActivity changeMobileActivity);
    void inject(DelayRecordFragment delayRecordFragment);
    void inject(TradeRecordFragment tradeRecordFragment);
    void inject(Face2FaceDiagnosisFeeActivity face2FaceDiagnosisFeeActivity);
    void inject(PhoneDiagnosisFeeActivity phoneDiagnosisFeeActivity);
    void inject(PhoneVipDiagnosisFeeActivity phoneVipDiagnosisFeeActivity);
    void inject(MyFriendsFragment myFriendsFragment);
    void inject(ScanActivity scanActivity);
    void inject(TradeDetailActivity tradeDetailActivity);
    void inject(NormalCustomerFragment normalCustomerFragment);
    void inject(VipCustomerFragment vipCustomerFragment);
}
