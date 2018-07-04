package com.jkdys.doctor.ui.verify.doctorVerify;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.E;
import com.chairoad.framework.util.ToastUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.ui.MvpActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class DoctorVerifyActivity extends MvpActivity<DoctorVerifyView, DoctorVerifyPresenter> implements DoctorVerifyView {

    public final int REQUEST_IMAGE_1 = 10000;
    public final int REQUEST_IMAGE_2 = 10001;

    @Inject
    DoctorVerifyPresenter doctorVerifyPresenter;

    @BindView(R.id.takePicLL)
    View takePicLL;
    @BindView(R.id.takePic1)
    View takePic1;
    @BindView(R.id.pic1descTxt)
    TextView pic1descTxt;
    @BindView(R.id.icon1)
    ImageView icon1;
    @BindView(R.id.icon2)
    ImageView icon2;
    @BindView(R.id.takePic2)
    View takePic2;
    @BindView(R.id.pic2descTxt)
    TextView pic2descTxt;
    @BindView(R.id.image1)
    ImageView imageView1;
    @BindView(R.id.image2)
    ImageView imageView2;
    @BindView(R.id.delete1)
    ImageView deletebtn1;
    @BindView(R.id.delete2)
    ImageView deletebtn2;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private String sgzUrl;
    private String ghUrl;

    @NonNull
    @Override
    public DoctorVerifyPresenter createPresenter() {
        getActivityComponent().inject(this);
        return doctorVerifyPresenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);
        initPhotoError();
        toolbar.setTitle("医生认证");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        Dexter.withActivity(mActivity)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                } else {
                    ToastUtil.show(mActivity,"访问相机或者读取媒体权限被拒绝");
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                // TODO: 2018/7/4  弹出对话框引导用户开启权限
            }

        }).withErrorListener(error -> ToastUtil.show(mActivity,"error:"+error.name())).check();

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_doctor_verify;
    }

    @OnClick(R.id.takePic1)
    void onTakePic1Clicked() {
        multiImageSelector(REQUEST_IMAGE_1);
    }

    @OnClick(R.id.takePic2)
    void onTakePic2Clicked() {
        multiImageSelector(REQUEST_IMAGE_2);
    }

    /**
     * 图片选择器调用
     */
    public void multiImageSelector(int requestCode) {
        Intent intent = new Intent(mActivity, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            doctorVerifyPresenter.uploadImage(paths.get(0), requestCode);
        }
    }

    @Override
    public void onUploadImageSuccess(String url, int requestCode) {
        if (requestCode == REQUEST_IMAGE_1) {
            ImageLoader.with(getApplicationContext()).load(url).into(imageView1);
            pic1descTxt.setVisibility(View.INVISIBLE);
            icon1.setVisibility(View.INVISIBLE);
            deletebtn1.setVisibility(View.VISIBLE);
            sgzUrl = url;
        } else if (requestCode == REQUEST_IMAGE_2) {
            ImageLoader.with(getApplicationContext()).load(url).into(imageView2);
            pic2descTxt.setVisibility(View.INVISIBLE);
            icon2.setVisibility(View.INVISIBLE);
            deletebtn2.setVisibility(View.VISIBLE);
            ghUrl = url;
        }
        btnSubmit.setEnabled(!TextUtils.isEmpty(sgzUrl) && !TextUtils.isEmpty(ghUrl));
    }

    @OnClick(R.id.delete1)
    void onDelete1Click() {
        pic1descTxt.setVisibility(View.VISIBLE);
        icon1.setVisibility(View.VISIBLE);
        deletebtn1.setVisibility(View.INVISIBLE);
        imageView1.setImageResource(R.drawable.default_sgz);
    }

    @OnClick(R.id.delete2)
    void onDelete2Click() {
        pic2descTxt.setVisibility(View.VISIBLE);
        icon2.setVisibility(View.VISIBLE);
        deletebtn2.setVisibility(View.INVISIBLE);
        imageView2.setImageResource(R.drawable.default_sgz);
    }

    @OnClick(R.id.btn_submit)
    void onBtnSubmitClick() {

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void initPhotoError(){
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
