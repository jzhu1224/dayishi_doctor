package com.jkdys.doctor.ui.profile;

import com.jkdys.doctor.ui.BaseView;
import com.jkdys.doctor.ui.search.SearchData;

public interface PersonalProfileView extends BaseView{
    void onModifySuccess(int requestCode, SearchData searchData);
    void onModifyAvatarSuccess(String url);
}
