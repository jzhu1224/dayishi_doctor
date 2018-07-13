package com.jkdys.doctor.ui.profile;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class EditProfilePresenter extends MvpBasePresenter<EditProfileView> {

    DaYiShiServiceApi api;

    @Inject
    public EditProfilePresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void updateProfile(int type, String content) {
        /**
         "type": "1", //类型，1是擅长疾病，2是个人简介
         "content": "测试" //内容
         */
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("content", content);

        ifViewAttached(view -> view.showLoading(false));

        api.updateDoctorProfile(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(EditProfileView::onUpdateSuccess);
            }
        });
    }

    public void getProfile(int type) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        ifViewAttached(view -> view.showLoading(false));
        api.getDoctorProfile(params).enqueue(new BaseCallback<BaseResponse<String>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<String> response) {
                ifViewAttached(view -> view.onRequestProfileSuccess(response.getData()));
            }
        });
    }
}
