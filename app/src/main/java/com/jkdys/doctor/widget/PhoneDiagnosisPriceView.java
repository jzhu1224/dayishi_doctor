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
import android.widget.TextView;

import com.jkdys.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneDiagnosisPriceView extends FrameLayout {

    @BindView(R.id.img_close)
    FrameLayout imgClose;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    ImgCloseClickListener imgCloseClickListener;

    public PhoneDiagnosisPriceView(@NonNull Context context) {
        this(context, null);
    }

    public PhoneDiagnosisPriceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PhoneDiagnosisPriceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_phone_diagnosis_view, this);

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

    public void setImgCloseVisable(boolean visable) {
        imgClose.setVisibility(visable? View.VISIBLE:View.GONE);
    }

    public void setText(String tag) {
        tvPrice.setText(tag);
        setTag(tag);
    }

    public interface ImgCloseClickListener {
        void onClick(View view);
    }
}
