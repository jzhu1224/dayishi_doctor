package com.jkdys.doctor.data.network;

import com.jkdys.doctor.data.model.AccountData;
import com.jkdys.doctor.data.model.AppUpgradeBean;
import com.jkdys.doctor.data.model.BankCardInfo;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.BindBankCardData;
import com.jkdys.doctor.data.model.CityData;
import com.jkdys.doctor.data.model.DepartmentData;
import com.jkdys.doctor.data.model.DiagnosisFeeData;
import com.jkdys.doctor.data.model.Doctor;
import com.jkdys.doctor.data.model.DoctorDetailData;
import com.jkdys.doctor.data.model.DoctorWorkInfo;
import com.jkdys.doctor.data.model.Face2FaceOrderDetail;
import com.jkdys.doctor.data.model.HospitalData;
import com.jkdys.doctor.data.model.LoginResponse;
import com.jkdys.doctor.data.model.MyFriendData;
import com.jkdys.doctor.data.model.OrderInfo;
import com.jkdys.doctor.data.model.PatientGroup;
import com.jkdys.doctor.data.model.PhoneNumberDetail;
import com.jkdys.doctor.data.model.PhoneOrderDetail;
import com.jkdys.doctor.data.model.Physicianstitle;
import com.jkdys.doctor.data.model.ProcessFace2FaceOrder;
import com.jkdys.doctor.data.model.ProvinceData;
import com.jkdys.doctor.data.model.SearchDoctorData;
import com.jkdys.doctor.data.model.TradeDetailData;
import com.jkdys.doctor.data.model.TradeRecord;
import com.jkdys.doctor.data.model.UploadImageData;
import com.jkdys.doctor.data.model.VerifyBankCardData;

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

    @Multipart
    @POST("/api/Doctor/Common/UploadPhotos")
    Call<BaseResponse<UploadImageData>> postImage(@Part("imgtype") RequestBody type,@Part MultipartBody.Part file);

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

    @POST("/api/Doctor/Doctor/GetDoctorAccountAndBank")
    Call<BaseResponse<AccountData>> getAccountData();

    @POST("/api/Doctor/BaseData/GetDoctorBankList") //获取绑定银行卡的信息
    Call<BaseResponse<BankCardInfo>> getDoctorBankList();

    @POST("/api/Doctor/BaseData/GetBankNamebyAccount")
    Call<BaseResponse<BindBankCardData>> getBankNameByAccount(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/BaseData/GetSupportBankList")
    Call<BaseResponse<List<BankCardInfo>>> getSupportBankList();

    @POST("/api/Doctor/Access/DoctorPapersCheck")
    Call<BaseResponse<LoginResponse>> doctorPapersCheck(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/BaseData/DelDoctorBankInfo")
    Call<BaseResponse<Object>> unbindBankCard();

    @POST("/api/Doctor/BaseData/AddDoctorBankInfo")
    Call<BaseResponse<Object>> bindBankCard(@Body HashMap<String, Object> params);

    //修改医生地区、医院、科室、职称
    @POST("/api/Doctor/Hospital/ModifyDoctorWorkInfo")
    Call<BaseResponse<Object>> modifyDoctorWorkInfo(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/GetDoctorListByDoctorSearch")
    Call<BaseResponse<List<SearchDoctorData>>> searchDoctor(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/GetOtherDoctorDetailInfo")
    Call<BaseResponse<DoctorDetailData>> getDoctorDetail(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/OrderInfo/GetPatientOrderInfoDetail")
    Call<BaseResponse<PhoneOrderDetail>> getOrderDetail(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/OrderInfo/GetPatientOrderInfoDetail")
    Call<BaseResponse<Face2FaceOrderDetail>> getFace2FaceOrderDetail(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/ManageDoctorGoodAtDetailInfo")
    Call<BaseResponse<Object>> updateDoctorProfile(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/GetDoctorGoodAtDetailInfo")
    Call<BaseResponse<String>> getDoctorProfile(@Body HashMap<String,Object> params);

    @POST("/api/Doctor/OrderInfo/ManageDoctorRegOrder")
    Call<BaseResponse<Object>> processFace2FaceOrder(@Body ProcessFace2FaceOrder processFace2FaceOrder);

    @POST("/api/Doctor/BaseData/CheckDoctorBankInfo")
    Call<BaseResponse<VerifyBankCardData>> verifyBankCardInfo(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/BaseData/AddDoctorBankInfo")
    Call<BaseResponse<Object>> bindingBankCard(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/AddDoctorFriends")
    Call<BaseResponse<Object>> addFriends(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/ModifyDoctorCellPhone")
    Call<BaseResponse<Object>> changeMobile(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Common/SentCommonVerificationCode")
    Call<BaseResponse<Object>> sendCommonVerificationCode(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/GetDoctorTradeRecord")
    Call<BaseResponse<List<TradeRecord>>> getTradeRecord(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/ModifyDoctorFeeSetDetail")
    Call<BaseResponse<Object>> modifyDiagnosisFee(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/GetDoctorFeeSetDetail")
    Call<BaseResponse<DiagnosisFeeData>> diagnosisFeeDetail(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/OrderInfo/ManageDoctorTelOrder")
    Call<BaseResponse<PhoneNumberDetail>> dail(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/OrderInfo/UnbindDoctorCellphone")
    Call<BaseResponse<Object>> cancelCall();

    @POST("/api/Doctor/Common/UpgradeCheck")
    Call<BaseResponse<AppUpgradeBean>> checkUpdate();

    @POST("/api/Doctor/Doctor/GetDoctorMyFriends")
    Call<BaseResponse<List<MyFriendData>>> myFriends(@Body HashMap<String, Object> params);

    @POST("/api/Doctor/Doctor/GetDoctorTradeRecordDetail")
    Call<BaseResponse<TradeDetailData>> getTradeRecordDetail(@Body HashMap<String, Object> params);
}
