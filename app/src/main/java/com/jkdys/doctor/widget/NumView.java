package com.jkdys.doctor.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.jkdys.doctor.R;

/**
 * Created by yanxin on 17/4/19.
 */

public class NumView extends AppCompatTextView {

    public NumView(Context context) {
        super(context);
        init();
    }

    public NumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.circle_ff0000_white);
    }

    public void setNum(int num) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        if(num < 10) {
            setTextSize(12);
        } else {
            setTextSize(10);
        }

        if(num < 99) {
            setText(String.valueOf(num));
        } else {
            setText("99+");
        }
    }

}
