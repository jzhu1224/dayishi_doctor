package com.jkdys.doctor.data.sharedpreferences;

import android.content.Context;

import com.google.gson.Gson;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.di.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginInfoUtil {

    Context context;

    Gson gson;

    @Inject
    public LoginInfoUtil(@ApplicationContext Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    public String getToken() {
        if (getLoginResponse() == null)
            return "";
        return getLoginResponse().getToken();
    }

    public LoginResponse getLoginResponse() {
        String sLoginResponse = (String) SharedPreferencesUtils.get(context,"loginResponse","");
        return gson.fromJson(sLoginResponse,LoginResponse.class);
    }

    public int getRedirecttopage() {
        if (getLoginResponse() == null)
            return -1;
        return getLoginResponse().getDoctorauthstatus().getRedirecttopage();
    }

    public void updateRedirecttopage(int page) {
        LoginResponse loginResponse = getLoginResponse();
        if (loginResponse != null) {
            loginResponse.getDoctorauthstatus().setRedirecttopage(page);
            saveLoginResponse(loginResponse);
        }

    }

    public void saveLoginResponse(LoginResponse loginResponse) {
        SharedPreferencesUtils.put(context,"loginResponse",gson.toJson(loginResponse));
    }

    public void clear() {
        SharedPreferencesUtils.put(context,"loginResponse","");
    }
}
