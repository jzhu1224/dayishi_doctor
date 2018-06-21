package com.jkdys.doctor.ui.verify.personalInfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import javax.inject.Inject;
import butterknife.OnClick;

public class PersonalInfoActivity extends MvpActivity<PersonalInfoView,PersonalInfoPresenter> implements PersonalInfoView {

    @Inject
    PersonalInfoPresenter personalInfoPresenter;


    @NonNull
    @Override
    public PersonalInfoPresenter createPresenter() {
        getActivityComponent().inject(this);
        return personalInfoPresenter;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_info_user;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());
        toolbar.setTitle("个人信息录入");

    }

    @OnClick(R.id.btn_next)
    public void onNextStepClick() {

    }

    @Override
    public void onIdentitySuccess() {
        ToastUtil.show(mActivity,"实名认证成功");
    }
}
