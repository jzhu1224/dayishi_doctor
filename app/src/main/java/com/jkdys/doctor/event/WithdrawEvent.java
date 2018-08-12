package com.jkdys.doctor.event;

public class WithdrawEvent {
    private String code;
    public WithdrawEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}