package com.jkdys.doctor.ui.verify.userVerify;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

public class IdentityPresenter extends MvpBasePresenter<IdentityView> {

    DaYiShiServiceApi daYiShiServiceApi;

    @Inject
    public IdentityPresenter(DaYiShiServiceApi daYiShiServiceApi) {
        this.daYiShiServiceApi = daYiShiServiceApi;
    }


    public void verifyUser(String name, String certificateno) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("certificateno",certificateno);
        ifViewAttached(view -> view.showLoading(false));
        daYiShiServiceApi.verifyUser(map).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(IdentityView::onIdentitySuccess);
            }
        });
    }

}
