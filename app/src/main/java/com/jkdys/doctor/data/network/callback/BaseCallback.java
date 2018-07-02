package com.jkdys.doctor.data.network.callback;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.ui.BaseFragment;
import com.jkdys.doctor.ui.BaseView;
import com.jkdys.doctor.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T extends BaseResponse> implements Callback<T> {

    private BaseView view;

    public BaseCallback(BaseView view) {
        this.view = view;
    }

    private boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        BaseResponse baseResponse = response.body();
        if (!isViewAttached() || baseResponse == null)
            return;
        view.showContent();

        if (baseResponse.getCode() == 1) {
            onBusinessSuccess(response.body());
        } else if (baseResponse.getCode() == 100) {

            Activity activity = null;
            //token失效
            if (view instanceof Activity) {
                activity = (Activity)view;
            } else if (view instanceof BaseFragment) {
                activity = ((BaseFragment)view).getActivity();
            }

            if (activity == null)
                return;

            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("TOKEN_EXPIRED",true);
            activity.startActivity(intent);

        } else if (baseResponse.isShowdialog()) {
            view.showError(baseResponse.getMsg());
        } else if (baseResponse.isShowmessage()) {
            view.showMessage(baseResponse.getMsg());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!isViewAttached())
            return;
        view.showContent();
        view.showMessage(t.getMessage());
    }

    public abstract void onBusinessSuccess(T response);
}
