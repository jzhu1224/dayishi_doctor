package com.jkdys.doctor.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.jkdys.doctor.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class MyToolbar extends FrameLayout {
    public MyToolbar(@NonNull Context context) {
        this(context, null);
    }

    public MyToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_my_toolbar,this);


        setBackgroundColor(getResources().getColor(R.color.color_white));
        if (Build.VERSION.SDK_INT >= 14) {
            setPadding(0, QMUIStatusBarHelper.getStatusbarHeight(context),0,0);
        }
    }
}
