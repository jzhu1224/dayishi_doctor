package com.jkdys.doctor.ui.profile;

import com.jkdys.doctor.ui.BaseView;

public interface EditProfileView extends BaseView{
    void onUpdateSuccess();
    void onRequestProfileSuccess(String content);
}
