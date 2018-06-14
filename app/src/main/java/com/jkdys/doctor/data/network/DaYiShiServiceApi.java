package com.jkdys.doctor.data.network;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.UserInfoModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DaYiShiServiceApi {
    @POST("/login")
    public Call<BaseResponse<UserInfoModel>> login(@Body HashMap<String,Object> map);
}
