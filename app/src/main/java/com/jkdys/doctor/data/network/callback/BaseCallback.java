package com.jkdys.doctor.data.network.callback;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.event.NetworkRequestEvent;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T extends BaseResponse> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        BaseResponse baseResponse = response.body();

        if (baseResponse == null)
            return;

        if (baseResponse.getCode() == 0) {
            onBusinessSuccess(response.body());
        } else if (baseResponse.isShowdialog()) {
            postEvent(NetworkRequestEvent.DIALOG,baseResponse.getMsg());
        } else if (baseResponse.isShowmessage()) {
            postEvent(NetworkRequestEvent.TOAST,baseResponse.getMsg());
        }
        postEvent(NetworkRequestEvent.HIDING_LOADING,"");
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        postEvent(NetworkRequestEvent.TOAST,"network error:"+t.getMessage());
    }

    public abstract void onBusinessSuccess(T response);

    private void postEvent(@NetworkRequestEvent.Type int type, String msg) {
        EventBus.getDefault().post(new NetworkRequestEvent(type,msg));
    }
}
