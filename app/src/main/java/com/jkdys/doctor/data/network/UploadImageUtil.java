package com.jkdys.doctor.data.network;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.IntRange;

import com.chairoad.framework.util.LogUtil;
import com.jkdys.doctor.MyApplication;
import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.UploadImageData;
import com.jkdys.doctor.utils.ImageUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;

@Singleton
public class UploadImageUtil {

    DaYiShiServiceApi api;

    Handler handler;

    @Inject
    public UploadImageUtil(DaYiShiServiceApi api) {
        this.api = api;
        handler = new Handler();
    }

    public void uploadImage(@IntRange(from = 1,to = 2) int type, @NonNull String path, @NonNull UploadImageListener listener) {
        uploadImage(type, new File(path),listener);
    }

    public void uploadImage(@IntRange(from = 1,to = 2) int type,@NonNull File file, @NonNull UploadImageListener listener) {
        listener.onUploadStart();
        new Thread(() -> {
            try {
                File newFile = compressAndSaveImg(file.getPath());

                if (newFile == null || !newFile.exists()) {
                    LogUtil.e("uploadImageUtil", "compress fail");
                    return;
                }

                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), newFile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("imgfile", newFile.getName(), reqFile);
                RequestBody requestBodyType = RequestBody.create(MediaType.parse("multipart/form-data"), type+"");
                api.postImage(requestBodyType,body).enqueue(new Callback<BaseResponse<UploadImageData>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<UploadImageData>> call, Response<BaseResponse<UploadImageData>> response) {
                        BaseResponse<UploadImageData> baseResponse = response.body();
                        if (baseResponse == null) {
                            handler.post(() -> {
                                listener.onUploadFail("response is null!");
                            });
                            return;
                        }

                        if (baseResponse.getCode() == 1) {
                            handler.post(() -> listener.onUploadSuccess(baseResponse.getData().getPicurl()));
                        } else {
                            handler.post(() -> listener.onUploadFail(baseResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<UploadImageData>> call, Throwable t) {
                        handler.post(() -> listener.onUploadFail(t.getMessage()));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> listener.onUploadFail(e.getMessage()));
            }
        }).start();

    }

    private File compressAndSaveImg(String path) {

        File savePath = null;
        try {
            savePath = Luban.with(MyApplication.getInstance())
                    .load(path)
                    .ignoreBy(100)
                    .get().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savePath;
    }

    public interface UploadImageListener {
        void onUploadStart();
        void onUploadFail(String msg);
        void onUploadSuccess(String url);
    }
}
