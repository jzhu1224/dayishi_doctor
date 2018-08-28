package com.jkdys.doctor.ui.setting;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Call;

public class SettingPresenter extends MvpBasePresenter<SettingView> {

    DaYiShiServiceApi api;

    @Inject
    public SettingPresenter(DaYiShiServiceApi api) {
        this.api = api;
    }

    public void switchNotification(boolean on) {
        ifViewAttached(view -> view.showLoading(false));
        HashMap<String, Object> params = new HashMap<>();
        params.put("acceptsms", on?1:0);
        api.switchNotification(params).enqueue(new BaseCallback<BaseResponse<Object>>(getView()) {
            @Override
            public void onBusinessSuccess(BaseResponse<Object> response) {
                ifViewAttached(view -> view.onSettingSuccess(on));
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                super.onFailure(call, t);

            }
        });
    }
}
