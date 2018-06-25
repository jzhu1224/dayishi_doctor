package com.jkdys.doctor.ui.chat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseImageCache;
import com.hyphenate.easeui.utils.EaseLoadLocalBigImgTask;
import com.hyphenate.easeui.widget.photoview.EasePhotoView;
import com.hyphenate.easeui.widget.photoview.PhotoViewAttacher;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.BaseAppCompatActivity;

import java.io.File;

import butterknife.BindView;

/**
 * @author zhujiang
 */
public class ShowBigImageActivity extends BaseAppCompatActivity {

    private static final String TAG = "ShowBigImage";
    private ProgressDialog pd;

    @BindView(R.id.image)
    EasePhotoView image;
    private int default_res = R.drawable.no_cash_images_message;
    private String localFilePath;
    private Bitmap bitmap;
    private boolean isDownloaded;


    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        initView();
    }

    public void initView() {

        toolbar.setTitle("查看图片");
        toolbar.addLeftBackImageButton().setOnClickListener( view -> finish());

        image.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                finish();
            }
        });

        ProgressBar loadLocalPb = (ProgressBar) findViewById(R.id.pb_load_local);
        default_res = getIntent().getIntExtra("default_image", R.drawable.no_cash_images_message);
        Uri uri = getIntent().getParcelableExtra("uri");
        localFilePath = getIntent().getExtras().getString("localUrl");
        String msgId = getIntent().getExtras().getString("messageId");
        EMLog.d(TAG, "show big msgId:" + msgId );

        //show the image if it exist in local path
        if (uri != null && new File(uri.getPath()).exists()) {
            EMLog.d(TAG, "showbigimage file exists. directly show it");
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            // int screenWidth = metrics.widthPixels;
            // int screenHeight =metrics.heightPixels;
            bitmap = EaseImageCache.getInstance().get(uri.getPath());
            if (bitmap == null) {
                EaseLoadLocalBigImgTask task = new EaseLoadLocalBigImgTask(this, uri.getPath(), image, loadLocalPb, ImageUtils.SCALE_IMAGE_WIDTH,
                        ImageUtils.SCALE_IMAGE_HEIGHT);
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    task.execute();
                }
            } else {
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                image.setImageBitmap(bitmap);
            }
        } else if(msgId != null) {
            downloadImage(msgId);
        }else {
            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image.setImageResource(default_res);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("NewApi")
    private void downloadImage(final String msgId) {
        EMLog.e(TAG, "download with messageId: " + msgId);
        String str1 = getResources().getString(com.hyphenate.easeui.R.string.Download_the_pictures);
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(str1);
        pd.show();
        File temp = new File(localFilePath);
        final String tempPath = temp.getParent() + "/temp_" + temp.getName();
        final EMCallBack callback = new EMCallBack() {
            public void onSuccess() {
                EMLog.e(TAG, "onSuccess" );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new File(tempPath).renameTo(new File(localFilePath));

                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        int screenWidth = metrics.widthPixels;
                        int screenHeight = metrics.heightPixels;

                        bitmap = ImageUtils.decodeScaleImage(localFilePath, screenWidth, screenHeight);
                        if (bitmap == null) {
                            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            image.setImageResource(default_res);
                        } else {
                            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            image.setImageBitmap(bitmap);
                            EaseImageCache.getInstance().put(localFilePath, bitmap);
                            isDownloaded = true;
                        }
                        if (isFinishing() || isDestroyed()) {
                            return;
                        }
                        if (pd != null) {
                            pd.dismiss();
                        }
                    }
                });
            }

            public void onError(int error, String msg) {
                EMLog.e(TAG, "offline file transfer error:" + msg);
                File file = new File(tempPath);
                if (file.exists()&&file.isFile()) {
                    file.delete();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ShowBigImageActivity.this.isFinishing() || ShowBigImageActivity.this.isDestroyed()) {
                            return;
                        }
                        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        image.setImageResource(default_res);
                        pd.dismiss();
                    }
                });
            }

            public void onProgress(final int progress, String status) {
                EMLog.d(TAG, "Progress: " + progress);
                final String str2 = getResources().getString(com.hyphenate.easeui.R.string.Download_the_pictures_new);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ShowBigImageActivity.this.isFinishing() || ShowBigImageActivity.this.isDestroyed()) {
                            return;
                        }
                        pd.setMessage(str2 + progress + "%");
                    }
                });
            }
        };

        EMMessage msg = EMClient.getInstance().chatManager().getMessage(msgId);
        msg.setMessageStatusCallback(callback);

        EMLog.e(TAG, "downloadAttachement");
        EMClient.getInstance().chatManager().downloadAttachment(msg);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_show_big_image;
    }
}