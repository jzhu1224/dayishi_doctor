package com.jkdys.doctor.data.network;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.data.model.Physicianstitle;
import com.jkdys.doctor.data.model.UserInfoModel;
import com.jkdys.doctor.data.network.callback.BaseCallback;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DaYiShiServiceApi {
    @POST("/api/Doctor/Access/SentVerificationCode")
    Call<BaseResponse<Object>> sentVerificationCode(@Body HashMap<String,Object> map);

    /**
     *
     *File file = new File(filePath);
     *RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
     *MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
     *RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
     * @param image
     * @param name
     * @return
     */

    @Multipart
    @POST("/")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);

    @POST("/api/Doctor/Access/LoginByCode")
    Call<BaseResponse<LoginResponse>> login(@Body HashMap<String,Object> map);

    @POST("/api/Doctor/Access/VerifyUser")
    Call<BaseResponse<Object>> verifyUser(@Body HashMap<String, Object> map);

    @POST("/api/Doctor/Hospital/GetPhysiciansTitleList")
    Call<BaseResponse<List<Physicianstitle>>> getPhysiciansTitleList();
}
