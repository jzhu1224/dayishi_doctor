package com.jkdys.doctor.ui.consult.diagnosis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.image.ImageLoader;
import com.jkdys.doctor.data.model.PhoneOrderDetail;
import com.jkdys.doctor.ui.MvpActivity;
import com.jkdys.doctor.ui.common.img.views.ImageOverlayView;
import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import butterknife.BindView;

public class DiagnosisOnPhoneActivity extends MvpActivity<DiagnosisOnPhoneView, DiagnosisOnPhonePresenter> implements DiagnosisOnPhoneView{


    @Inject
    DiagnosisOnPhonePresenter presenter;

    @BindView(R.id.nine_grid_img)
    NineGridImageView<String> nineGridImageView;

    @BindView(R.id.img_avatar)
    ImageView imgHeader;

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

    private void showBigPicture(List<String> imgs,int position) {

        ImageViewer.Builder builder = new ImageViewer.Builder<>(this, imgs)
                .setStartPosition(position);

        ImageOverlayView overlayView = new ImageOverlayView(this);
        builder.setOverlayView(overlayView);
        builder.setImageChangeListener(position1 -> {
            if (imgs.size() > 1) {
                overlayView.setDescription(String.format(Locale.CHINESE,"%s/%d",String.valueOf(position1+1),imgs.size()));
            }
        });
        builder.show();
    }

    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String photo) {
            Picasso.get().load(photo)
                    .centerCrop()
                    .resizeDimen(R.dimen.diagnosis_photo_size,R.dimen.diagnosis_photo_size)
                    .into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> photoList) {
            showBigPicture(photoList, index);
        }
    };

    @Override
    public void onRequestSuccess(PhoneOrderDetail phoneOrderDetail) {
        ImageLoader.with(mActivity)
                .load(phoneOrderDetail
                        .getPicheadurl()).placeholder(R.drawable.img_doctor)
                .into(imgHeader);

        tvName.setText(phoneOrderDetail.getPatientname());
        tvPrice.setText(String.valueOf(phoneOrderDetail.getAmount()));
        tvGender.setText(phoneOrderDetail.getGender().equals("1")?"男":"女");
        tvAge.setText(String.valueOf(phoneOrderDetail.getAge()));


        if (!TextUtils.isEmpty(phoneOrderDetail.getMedicalimageurl())) {
            String[] sImgs = phoneOrderDetail.getMedicalimageurl().split(",");
            if (sImgs.length > 0) {
                List<String> imgs = Arrays.asList(sImgs);
                nineGridImageView.setImagesData(imgs);
            }
        }

        tvTime.setText("提交时间    "+phoneOrderDetail.getOrderdate());

        tvPatientDetai.setText(phoneOrderDetail.getMedicalrecord());

        imgVip.setVisibility(phoneOrderDetail.getIsvip()? View.VISIBLE:View.GONE);

    }
}
