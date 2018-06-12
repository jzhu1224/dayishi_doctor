package com.dayishiapp.doctor.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.dayishiapp.doctor.R;

/**
 * Created by yanxin on 17/4/3.
 */

public class CountdownView1 extends CountdownView{

    public CountdownView1(Context context) {
        super(context);
    }

    public CountdownView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountdownView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setCommonText(boolean isFirst) {
        setText("重新获取验证码");
        setTextSize(12);
        setBackgroundResource(R.color.color_transparent);
        setTextColor(getResources().getColor(R.color.color_3a9cff));
    }

    @Override
    public void setWorkingText(int leftTime) {
        setText(leftTime+"秒后重新获取");
        setTextSize(12);
        setBackgroundResource(R.color.color_transparent);
        setTextColor(getResources().getColor(R.color.color_757575));
    }
}