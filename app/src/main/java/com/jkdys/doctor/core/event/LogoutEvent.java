package com.jkdys.doctor.core.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LogoutEvent {

    public static final int TOKEN_EXPIRED = 0x1;

    @LogoutReason
    public int logoutReason;

    public LogoutEvent(@LogoutReason int reason) {
        this.logoutReason = reason;
    }



    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TOKEN_EXPIRED})
    public @interface LogoutReason {}
}
