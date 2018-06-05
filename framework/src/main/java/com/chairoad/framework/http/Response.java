package com.chairoad.framework.http;

public class Response {

    private int code;

    private String errorMsg;

    private long timestamp;

    private int total;

    public Response() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 请求是否成功
     * @return
     */
    public boolean isSuccess() {
        return code == 200;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isEmpty() {
        return false;
    }
}
