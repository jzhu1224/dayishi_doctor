package com.jkdys.doctor.data.network;

import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.CityData;
import com.jkdys.doctor.data.model.DepartmentData;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.data.model.DoctorWorkInfo;
import com.jkdys.doctor.data.model.HospitalData;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.data.model.PatientGroup;
import com.jkdys.doctor.data.model.Physicianstitle;
import com.jkdys.doctor.data.model.ProvinceData;

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

    @POST("/api/Doctor/Hospital/GetHospitalListByHospitalName")
    Call<BaseResponse<List<HospitalData>>> getHospitalListByHospitalName(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Hospital/GetFacultyInfoListByHospitalId")
    Call<BaseResponse<List<DepartmentData>>> getFacultyInfoListByHospitalId(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Hospital/GetProvinceList")
    Call<BaseResponse<List<ProvinceData>>> getProvinceList();

    @POST("/api/Doctor/Hospital/GetCityList")
    Call<BaseResponse<List<CityData>>> getCityList(@Body HashMap<String, Object> params);


    @POST("/api/Doctor/OrderInfo/GetDoctorOrderInfo")
    Call<BaseResponse<List<OrderInfo>>> getDoctorOrderInfo(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Hospital/GetHospitalList")
    Call<BaseResponse<List<HospitalData>>> getHospitalList(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Access/DoctorWorkInfoCheck")
    Call<BaseResponse<LoginResponse>> doctorWorkInfoCheck(@Body DoctorWorkInfo doctorWorkInfo);

    @POST("/api/Doctor/Doctor/GetMyPatientList")
    Call<BaseResponse<List<PatientGroup>>> getMyPatientList(); //病人列表

    @POST("/api/Doctor/Doctor/GetDoctorDetailInfo")
    Call<BaseResponse<Doctor>> getDoctorDetailInfo();//获取医生个人信息

    @POST("/api/Doctor/BaseData/GetDoctorBankList") //获取绑定银行卡的信息
    Call<BaseResponse<BankCardInfo>> getDoctorBankList();

    @POST("/api/Doctor/BaseData/GetSupportBankList")
    Call<BaseResponse<List<BankCardInfo>>> getSupportBankList();

}
