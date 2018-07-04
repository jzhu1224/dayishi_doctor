package com.jkdys.doctor.ui.verify.doctorVerify;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.UploadImageUtil;

import java.util.List;

import javax.inject.Inject;

public class DoctorVerifyPresenter extends MvpBasePresenter<DoctorVerifyView> {

    DaYiShiServiceApi api;
    UploadImageUtil uploadImageUtil;

    @Inject
    public DoctorVerifyPresenter(DaYiShiServiceApi api, UploadImageUtil uploadImageUtil) {
        this.api = api;
        this.uploadImageUtil = uploadImageUtil;
    }

    public void uploadImage(String path, int requestCode) {

        ifViewAttached(view -> view.showLoading(false));

        uploadImageUtil.uploadImage(2,path, new UploadImageUtil.UploadImageListener() {
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
                    view.onUploadImageSuccess(url, requestCode);
                    view.showContent();
                });
            }
        });

    }
}
