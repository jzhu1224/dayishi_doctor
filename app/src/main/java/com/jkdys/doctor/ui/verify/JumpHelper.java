package com.jkdys.doctor.ui.verify;

import android.app.Activity;
import android.content.Intent;

import com.jkdys.doctor.ui.main.MainActivity;
import com.jkdys.doctor.ui.verify.doctorVerify.DoctorVerifyActivity;
import com.jkdys.doctor.ui.verify.personalInfo.PersonalInfoActivity;
import com.jkdys.doctor.ui.verify.userVerify.IdentityActivity;

import javax.inject.Inject;

public class JumpHelper {

    @Inject
    public JumpHelper() {

    }

    public boolean jump(Activity mActivity, int redirect) {
        Intent intent = null;
        switch (redirect) {
            case 0:
                if (mActivity instanceof MainActivity) {
                    return false;
                }
                intent = new Intent(mActivity, MainActivity.class);
                break;
            case 1:
                //实名认证页面
                intent = new Intent(mActivity, IdentityActivity.class);
                break;
            case 2:
                //所属医院页面
                intent = new Intent(mActivity, PersonalInfoActivity.class);
                break;
            case 3:
                //上传医生上岗证和医院工牌页面
                intent = new Intent(mActivity, DoctorVerifyActivity.class);
                break;
        }

        if (intent == null)
            return false;

        mActivity.startActivity(intent);
        mActivity.finish();
        return true;
    }
}
