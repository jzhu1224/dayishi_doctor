package com.jkdys.doctor.ui.consult.diagnosis;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chairoad.framework.util.SystemUtil;
import com.chairoad.framework.util.ToastUtil;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.DiagnosisImage;
import com.jkdys.doctor.data.model.PhoneNumberDetail;
import com.jkdys.doctor.data.model.PhoneOrderDetail;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.common.img.views.ImageOverlayView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class DiagnosisOnPhoneActivity extends MvpActivity<DiagnosisOnPhoneView, DiagnosisOnPhonePresenter> implements DiagnosisOnPhoneView{


    @Inject
    DiagnosisOnPhonePresenter presenter;

    @BindView(R.id.nine_grid_img)
    NineGridImageView<DiagnosisImage> nineGridImageView;

    @BindView(R.id.img_avatar)
    ImageView imgHeader;

    @BindView(R.id.tv_state)
    TextView tvState;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_age)
    TextView tvAge;

    @BindView(R.id.tv_patient_detail)
    TextView tvPatientDetai;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.img_vip)
    ImageView imgVip;

    PhoneOrderDetail phoneOrderDetail;


    @NonNull
    @Override
    public DiagnosisOnPhonePresenter createPresenter() {
        getActivityComponent().inject(this);
        return presenter;
    }

    @Override
    protected void afterBindView(@Nullable Bundle savedInstanceState) {
        super.afterBindView(savedInstanceState);

        toolbar.setTitle("电话诊断详情");
        toolbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        nineGridImageView.setAdapter(mAdapter);

    }

    @Override
    protected void afterMvpDelegateCreateInvoked() {
        super.afterMvpDelegateCreateInvoked();
        presenter.getOrderDetail(getIntent().getStringExtra("orderId"));
    }

    @Override
    protected int getLayout() {
        return R.layout.diagnosis_online;
    }

    private void showBigPicture(List<DiagnosisImage> imgs,int position) {

        ImageViewer.Builder builder = new ImageViewer.Builder<>(this, imgs)
                .setStartPosition(position)
                .setFormatter((ImageViewer.Formatter<DiagnosisImage>) diagnosisImage -> diagnosisImage.getMedicalimageurl());

        ImageOverlayView overlayView = new ImageOverlayView(this);
        builder.setOverlayView(overlayView);
        builder.setImageChangeListener(position1 -> {
            if (imgs.size() > 1) {
                overlayView.setDescription(String.format(Locale.CHINESE,"%s/%d",String.valueOf(position1+1),imgs.size()));
            }
        });
        builder.show();
    }

    private NineGridImageViewAdapter<DiagnosisImage> mAdapter = new NineGridImageViewAdapter<DiagnosisImage>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, DiagnosisImage photo) {
            Picasso.get().load(photo.getMedicalimageurlmini())
                    .centerCrop()
                    .resizeDimen(R.dimen.diagnosis_photo_size,R.dimen.diagnosis_photo_size)
                    .into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<DiagnosisImage> photoList) {
            showBigPicture(photoList, index);
        }
    };

    @Override
    public void onRequestSuccess(PhoneOrderDetail phoneOrderDetail) {

        if (phoneOrderDetail == null)
            return;
        this.phoneOrderDetail = phoneOrderDetail;

        if (!TextUtils.isEmpty(phoneOrderDetail.getPicheadurl()))
        ImageLoader.with(mActivity)
                .load(phoneOrderDetail
                        .getPicheadurl()).placeholder(R.drawable.img_doctor)
                .into(imgHeader);

        tvName.setText(phoneOrderDetail.getPatientname());
        tvPrice.setText(String.valueOf(phoneOrderDetail.getAmount()));
        tvGender.setText(phoneOrderDetail.getGender().equals("1")?"男":"女");
        tvAge.setText(String.valueOf(phoneOrderDetail.getAge()));

        if (phoneOrderDetail.getStatus().equals("0")) {
            tvState.setText("待处理");
        } else if (phoneOrderDetail.getStatus().equals("1")) {
            tvState.setText("已完成");
        } else if (phoneOrderDetail.getStatus().equals("2")) {
            tvState.setText("已取消");
        }


        nineGridImageView.setImagesData(phoneOrderDetail.getImages());

        tvTime.setText("提交时间    "+phoneOrderDetail.getOrderdate());

        tvPatientDetai.setText(phoneOrderDetail.getMedicalrecord());

        imgVip.setVisibility(phoneOrderDetail.getIsvip()? View.VISIBLE:View.GONE);

    }

    @Override
    public void onRequestVirtualNumberSuccess(PhoneNumberDetail phoneNumberDetail) {
        QMUIDialog.CustomDialogBuilder builder = new QMUIDialog.CustomDialogBuilder(mActivity);

        builder.setLayout(R.layout.layout_dail_dialog);

        builder.addAction("取消", (dialog, index) -> {
            dialog.dismiss();
            presenter.cancelCall();
        });

        QMUIDialog qmuiDialog = builder.addAction("确认并呼叫",((dialog, index) -> {
            dialog.dismiss();
            call(phoneNumberDetail.getCellphone());
        })).create();

        ((TextView) qmuiDialog.findViewById(R.id.tv_phone)).setText(phoneNumberDetail.getDoctorcellphone());

        qmuiDialog.show();
    }

    @Override
    public void onCancelCallSuccess() {

    }


    @OnClick(R.id.btn_complete)
    void onBtnCompleteClick() {
        presenter.processOrder(getIntent().getStringExtra("orderId"));
    }

    private void call(String phoneNumber) {


        Dexter.withActivity(mActivity)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        SystemUtil.call(mActivity,phoneNumber);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        ToastUtil.show(mActivity, "拨打电话权限被拒绝");
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        new QMUIDialog.MessageDialogBuilder(mActivity)
                                .setMessage("检测到没有运行APP需要的必要权限，请到权限管理页面授予相应权限，否则APP无法正常使用")
                                .addAction("取消", (dialog, index) -> {
                                    dialog.dismiss();
                                    finish();
                                })
                                .addAction("确定", (dialog, index) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }).show();
                    }
                }).withErrorListener(error -> ToastUtil.show(mActivity,"error:"+error.name())).check();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cancelCall();
    }
}
