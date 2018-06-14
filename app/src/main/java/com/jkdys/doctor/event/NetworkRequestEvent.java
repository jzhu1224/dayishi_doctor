package com.jkdys.doctor.event;

import android.support.annotation.IntDef;

public class NetworkRequestEvent {
    /**
     * 定义几个static final 的常量
     */
    public static final int TOAST = 0;
    public static final int DIALOG = 1;
    public static final int HIDING_LOADING = 2;

    public int type;
    public String msg;

    public NetworkRequestEvent(@Type int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    @IntDef({TOAST,DIALOG,HIDING_LOADING})
    public @interface Type {

    }
}
