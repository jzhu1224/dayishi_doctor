package com.chairoad.framework.exception;

/**
 * Created by lili on 16/12/1.
 */

public class BusinessException extends RuntimeException {

    public BusinessException(String detailMessage) {
        super(detailMessage);
    }

    public BusinessException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
