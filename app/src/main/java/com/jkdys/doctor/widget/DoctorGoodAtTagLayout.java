package com.jkdys.doctor.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jkdys.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorGoodAtTagLayout extends FrameLayout {

    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.tv_tag)
    DoctorGoodAtTag doctorGoodAtTag;

    ImgCloseClickListener imgCloseClickListener;

    public DoctorGoodAtTagLayout(@NonNull Context context) {
        this(context, null);
    }

    public DoctorGoodAtTagLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DoctorGoodAtTagLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_good_at_tag, this);

        ButterKnife.bind(this);
    }

    public void setImgCloseClickListener(ImgCloseClickListener imgCloseClickListener) {
        this.imgCloseClickListener = imgCloseClickListener;
    }

    @OnClick(R.id.img_close)
    void onImgCloseClick() {
        ((ViewGroup)getParent()).removeView(this);
        if (imgCloseClickListener != null) {
            imgCloseClickListener.onClick(this);
        }
    }

    void setImgCloseVisable(boolean visable) {
        imgClose.setVisibility(visable? View.VISIBLE:View.GONE);
    }

    public void setText(String tag) {
        doctorGoodAtTag.setText(tag);
        setTag(tag);
    }

    public interface ImgCloseClickListener {
        void onClick(View view);
    }
}
