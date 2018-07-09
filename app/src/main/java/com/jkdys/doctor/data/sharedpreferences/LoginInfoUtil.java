package com.jkdys.doctor.data.sharedpreferences;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.data.model.Doctor;
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

        if (TextUtils.isEmpty(sLoginResponse))
            return null;

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
        SharedPreferencesUtils.put(context, "bankCardInfo", "");
    }

    public void saveBindBankCard(BankCardInfo bankCardInfo) {
        if (bankCardInfo == null)
            return;
        SharedPreferencesUtils.put(context, "bankCardInfo", gson.toJson(bankCardInfo));
    }

    public void clearBindBankCard() {
        SharedPreferencesUtils.put(context, "bankCardInfo", "");
    }

    public BankCardInfo getBindBanCard() {
        String sBankCardInfo = (String) SharedPreferencesUtils.get(context,"bankCardInfo","");
        if (TextUtils.isEmpty(sBankCardInfo))
            return null;
        return gson.fromJson(sBankCardInfo,BankCardInfo.class);
    }

    public void saveDoctor(Doctor doctor) {
        LoginResponse loginResponse = getLoginResponse();
        loginResponse.setDoctor(doctor);
        saveLoginResponse(loginResponse);
    }

    public Doctor getDoctor() {
        return getLoginResponse().getDoctor();
    }

    /**
     *
     *  tvHospital.setText(doctor.getHospital());
     tvDepartment.setText(doctor.getFaculty());
     tvTitle.setText(doctor.getTitle());
     */

    public String getHospital() {
        return getDoctor().getHospital();
    }

    public void saveHospital(String hospital) {
        Doctor doctor = getDoctor();
        doctor.setHospital(hospital);
        saveDoctor(doctor);
    }

    public String getFaculty() {
        return getDoctor().getFaculty();
    }

    public void saveFaculty(String faculty) {
        Doctor doctor = getDoctor();
        doctor.setFaculty(faculty);
        saveDoctor(doctor);
    }

    public String getTitle() {
        return getDoctor().getTitle();
    }

    public void saveTitle(String title) {
        Doctor doctor = getDoctor();
        doctor.setTitle(title);
        saveDoctor(doctor);
    }

    public String getAvatar() {
        return getDoctor().getPicheadurl();
    }

    public void saveAvatar(String avatar) {
        Doctor doctor = getDoctor();
        doctor.setPicheadurl(avatar);
        saveDoctor(doctor);
    }
}
