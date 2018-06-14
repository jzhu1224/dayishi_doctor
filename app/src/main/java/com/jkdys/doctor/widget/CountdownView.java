package com.jkdys.doctor.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.jkdys.doctor.R;

/**
 * Created by yanxin on 17/4/3.
 */

public class CountdownView extends AppCompatButton implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    private CountDownListener countDownListener;

    public CountdownView(Context context) {
        super(context);
        init(context);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setInit(true);
    }

    @Override
    protected final void onDetachedFromWindow() {
        cancel();
        super.onDetachedFromWindow();
    }

    /**
     * 设置初始样式
     */
    public final void setInit(boolean first) {
        setCommonText(first);
        setClickable(true);
        setOnClickListener(this);
    }

    public void start() {
        startWork();
    }

    /**
     * 设置工作中的样式
     */
    private final void startWork() {
        setClickable(false);
        cancel();
        setWorkingText(60);
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setWorkingText((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                setInit(false);
                if (countDownListener != null) {
                    countDownListener.finish();
                }
            }
        };
        countDownTimer.start();
    }

    public final void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public final void onClick(View v) {
        if (countDownListener != null) {
            boolean before = countDownListener.before();
            if (!before) return;
        }
        startWork();
        if (countDownListener != null) {
            countDownListener.start();
        }
    }

    public void setCommonText(boolean isFirst) {
        setText("获取验证码");
        setTextSize(12);
        setBackgroundResource(R.drawable.corner_757575_ffffff);
        setTextColor(getResources().getColor(R.color.color_757575));
    }

    public void setWorkingText(int leftTime) {
        setText(leftTime + "秒重新获取");
        setTextSize(12);
        setBackgroundResource(R.drawable.corner_bdbdbd_ffffff);
        setTextColor(getResources().getColor(R.color.color_BDBDBD));
    }

    public final void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }

    public interface CountDownListener {
        //点击了按钮 false:不进行倒计时
        boolean before();

        //开始倒计时
        void start();

        //结束倒计时
        void finish();
    }
}