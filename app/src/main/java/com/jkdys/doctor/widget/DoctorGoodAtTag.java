package com.jkdys.doctor.widget;


import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.jkdys.doctor.R;
import com.jkdys.doctor.utils.ViewUtil;

public class DoctorGoodAtTag extends AppCompatTextView {

    public DoctorGoodAtTag(Context context) {
        this(context, null);
    }

    public DoctorGoodAtTag(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DoctorGoodAtTag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int DP2 = ViewUtil.dipToPx(getContext(), 2);
        int DP4 = ViewUtil.dipToPx(getContext(), 4);

        setPadding(DP4,DP2,DP4, DP2);
        setBackgroundResource(R.drawable.shape_bg_good_at);
        setTextColor(getResources().getColor(R.color.color_ADB5C1));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    }
}
