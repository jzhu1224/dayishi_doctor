package com.jkdys.doctor.ui.consult.diagnosis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.jkdys.doctor.R;
import com.jkdys.doctor.ui.MvpActivity;
import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class DiagnosisOnPhoneActivity extends MvpActivity<DiagnosisOnPhoneView, DiagnosisOnPhonePresenter> {


    @Inject
    DiagnosisOnPhonePresenter presenter;

    @BindView(R.id.nine_grid_img)
    NineGridImageView<String> nineGridImageView;

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

        List<String> imgs = new ArrayList<>();
        imgs.add("http://seopic.699pic.com/photo/00026/7248.jpg_wh1200.jpg");
        imgs.add("http://img.1ppt.com/uploads/allimg/1605/4_160521171105_1.jpg");
        imgs.add("http://himg2.huanqiu.com/attachment2010/2017/1205/08/51/20171205085117810.jpg");
        imgs.add("http://himg2.huanqiu.com/attachment2010/2012/1115/20121115025806447.jpg");
        imgs.add("http://www.sinaimg.cn/dy/slidenews/5_img/2013_26/453_27781_114389.jpg");

        nineGridImageView.setImagesData(imgs);
    }

    @Override
    protected int getLayout() {
        return R.layout.diagnosis_online;
    }

    private void showBigPicture(List<String> imgs,int position) {
        new ImageViewer.Builder<>(this, imgs)
                .setStartPosition(position)
                .show();
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
}
