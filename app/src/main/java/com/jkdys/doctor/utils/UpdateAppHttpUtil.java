package com.jkdys.doctor.utils;

import android.support.annotation.NonNull;

import com.jkdys.doctor.MyApplication;
import com.jkdys.doctor.data.network.DaYiShiServiceApi;
import com.jkdys.doctor.data.network.UploadImageUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.vector.update_app.HttpManager;

import java.io.File;
import java.util.Map;

public class UpdateAppHttpUtil implements HttpManager {

    DaYiShiServiceApi api;

    public UpdateAppHttpUtil() {
    }

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {
        callBack.onResponse("{\n" +
                "  \"update\": \"Yes\",\n" +
                "  \"new_version\": \"0.8.4\",\n" +
                "  \"apk_file_url\": \"https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk\",\n" +
                "  \"update_log\": \"1，添加删除信用卡接口。\\r\\n2，添加vip认证。\\r\\n3，区分自定义消费，一个小时不限制。\\r\\n4，添加放弃任务接口，小时内不生成。\\r\\n5，消费任务手动生成。\",\n" +
                "  \"target_size\": \"5M\",\n" +
                "  \"new_md5\":\"b97bea014531123f94c3ba7b7afbaad2\",\n" +
                "  \"constraint\": false\n" +
                "}");
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {

    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull FileCallback callback) {
        FileDownloader.setup(MyApplication.getInstance());
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        callback.onBefore();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        callback.onProgress((float) soFarBytes/ (float) totalBytes, totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        callback.onResponse(new File(task.getTargetFilePath()));
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }
}
