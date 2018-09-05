package com.jkdys.doctor.ui.profile;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.UploadImageUtil;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import com.jkdys.doctor.ui.search.SearchData;

import java.util.HashMap;

import javax.inject.Inject;

public class PersonalProfilePresenter extends MvpBasePresenter<PersonalProfileView> {

    DaYiShiServiceApi api;
    LoginInfoUtil loginInfoUtil;
    UploadImageUtil uploadImageUtil;

    @Inject
    public PersonalProfilePresenter(DaYiShiServiceApi api, LoginInfoUtil loginInfoUtil, UploadImageUtil uploadImageUtil) {
        this.api = api;
        this.loginInfoUtil = loginInfoUtil;
        this.uploadImageUtil = uploadImageUtil;
    }

    public void modifyDoctorInfo(int requestCode, SearchData searchData) {
        ifViewAttached(view -> view.showLoading(false));

        /**
         *
         "areacode": "ACCBE1B6-81E0-4306-B65F-263345E4DA3B",//所属区域
         "hospitalcode": "C88615E8-1BAF-455F-BD67-4085AAD39055",//所属医院id
         "facultycode": "12546AFB-A01A-455A-B98E-1AFADE90A3AC",//所属科室id
         "titlecode": "2676E3F1-B74C-4095-B0D9-AF550E960AA5"//所属职称id
         *
         */
        HashMap<String, Object> params = new HashMap<>();
        if (requestCode == 1) {
            params.put("facultycode", searchData.getId());
        } else if (requestCode == 2) {
            params.put("titlecode", searchData.getId());
        } else if (requestCode == 3) {
            params.put("hospitalcode", searchData.getId());
        }

        api.modifyDoctorWorkInfo(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(view -> view.onModifySuccess(requestCode, searchData));
            }
        });
    }

    public void uploadImage(String path) {

        ifViewAttached(view -> view.showLoading(false));

        uploadImageUtil.uploadImage(1,path, new UploadImageUtil.UploadImageListener() {
            @Override
            public void onUploadStart() {

            }

            @Override
            public void onUploadFail(String msg) {
                ifViewAttached(view -> {
                    view.showMessage(msg);
                    view.showContent();
                });
            }

            @Override
            public void onUploadSuccess(String url) {
                ifViewAttached(view -> {
                    HashMap<String,Object> params = new HashMap<>();
                    params.put("headpicurl", url);
                    api.modifyDoctorHeadPicUrl(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
                        @Override
                        public void onBusinessSuccess(BaseResponse<Object> response) {
                            loginInfoUtil.saveAvatar(url);
                            view.onModifyAvatarSuccess(url);
                            view.showContent();
                        }
                    });
                });
            }
        });

    }
}
