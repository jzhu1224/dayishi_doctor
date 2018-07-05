package com.jkdys.doctor.ui.verify.doctorVerify;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.UploadImageUtil;
import com.jkdys.doctor.data.network.callback.BaseCallback;
import com.jkdys.doctor.data.sharedpreferences.LoginInfoUtil;
import java.util.HashMap;
import javax.inject.Inject;

public class DoctorVerifyPresenter extends MvpBasePresenter<DoctorVerifyView> {

    DaYiShiServiceApi api;
    UploadImageUtil uploadImageUtil;
    LoginInfoUtil loginInfoUtil;

    @Inject
    public DoctorVerifyPresenter(DaYiShiServiceApi api, UploadImageUtil uploadImageUtil, LoginInfoUtil loginInfoUtil) {
        this.api = api;
        this.uploadImageUtil = uploadImageUtil;
        this.loginInfoUtil = loginInfoUtil;
    }

    public void uploadImage(int imgType, String path, int requestCode) {

        ifViewAttached(view -> view.showLoading(false));

        uploadImageUtil.uploadImage(imgType,path, new UploadImageUtil.UploadImageListener() {
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

    public void verify(String zgzUrl, String ghUrl) {
        ifViewAttached(view -> view.showLoading(false));

        HashMap<String,Object> params = new HashMap<>();
        params.put("imgdoctorlicenseurl", zgzUrl);
        params.put("imgworkcardurl", ghUrl);
        api.doctorPapersCheck(params)
                .enqueue(new BaseCallback<BaseResponse<LoginResponse>>(getView()) {
                    @Override
                    public void onBusinessSuccess(BaseResponse<LoginResponse> response) {
                        loginInfoUtil.saveLoginResponse(response.getData());
                        ifViewAttached(view -> view.onVerifySuccess(response.getData()));
                    }
                });
    }
}
