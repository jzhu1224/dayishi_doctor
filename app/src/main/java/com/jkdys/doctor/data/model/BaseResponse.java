package com.jkdys.doctor.data.model;

public class BaseResponse<D> {
    /**
     * {
     "code":1, ,
     "showmessage":false,// 表示是否需要以toast形式 提示message内容
     "showdialog":false,// 表示是否需要以dialog形式 提示message内容
     "msg":"返回提示信息",
     "data":null
     }
     */

    private int code;
    private boolean showmessage;
    private boolean showdialog;
    private String msg;
    private int totalpage;
    private int totalrecord;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isShowmessage() {
        return showmessage;
    }

    public void setShowmessage(boolean showmessage) {
        this.showmessage = showmessage;
    }

    public boolean isShowdialog() {
        return showdialog;
    }

    public void setShowdialog(boolean showdialog) {
        this.showdialog = showdialog;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private D data;

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalpage;
    }

    public void setTotalPage(int totalPage) {
        this.totalpage = totalPage;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getTotalrecord() {
        return totalrecord;
    }

    public void setTotalrecord(int totalrecord) {
        this.totalrecord = totalrecord;
    }
}
