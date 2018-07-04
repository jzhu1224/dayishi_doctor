package com.jkdys.doctor.data.network;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.IntRange;

import com.jkdys.doctor.data.model.BaseResponse;
import com.jkdys.doctor.data.model.UploadImageData;
import com.jkdys.doctor.utils.ImageUtil;

import java.io.File;
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
                File newFile = new File(Objects.requireNonNull(compressAndSaveImg(file.getPath())));
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

    private String compressAndSaveImg(String path) {
        //质量压缩保存图片
        String savePath = ImageUtil.saveCompressBitmap(BitmapFactory.decodeFile(path), path);
        final File newFile = new File(savePath);
        if (newFile == null || !newFile.exists()) {
            return null;
        }

        long length = ImageUtil.getBytesFromFile(newFile).length;
        if (length == 0) {
            return null;
        }
        return savePath;
    }

    public interface UploadImageListener {
        void onUploadStart();
        void onUploadFail(String msg);
        void onUploadSuccess(String url);
    }
}
