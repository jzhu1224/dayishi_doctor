package com.jkdys.doctor.ui.verify;

import android.app.Activity;
import android.content.Intent;

import com.jkdys.doctor.ui.verify.doctorVerify.DoctorVerifyActivity;
import com.jkdys.doctor.ui.verify.personalInfo.PersonalInfoActivity;
import com.jkdys.doctor.ui.verify.userVerify.IdentityActivity;

import javax.inject.Inject;

public class JumpHelper {

    @Inject
    public JumpHelper() {

    }

    public boolean jump(Activity mActivity, int redirect) {
        if (redirect == 0)
            return false;
        Intent intent = new Intent();

        switch (redirect) {
            case 1:
                //实名认证页面
                intent.setClass(mActivity, IdentityActivity.class);
                break;
            case 2:
                //所属医院页面
                intent.setClass(mActivity, PersonalInfoActivity.class);
                break;
            case 3:
                //上传医生上岗证和医院工牌页面
                intent.setClass(mActivity, DoctorVerifyActivity.class);
                break;
        }
        mActivity.startActivity(intent);
        mActivity.finish();
        return true;
    }
}
