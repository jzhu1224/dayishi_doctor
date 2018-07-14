package com.jkdys.doctor.data.model;

public class ProcessFace2FaceOrder {
    /**
     *  "orderid": "20180705140610637167000174456456", //订单号
     "handletype": "2", // 1是同意，2是完成，3是延期，4是取消
     "regtime": "2018-07-09 18:32", //门诊时间
     "regplace": "昆城广场" ,//门诊地点
     "medicalcode": "" //就诊码，如果是完成，则必须要就诊码
     */

    private String orderid;
    private String handletype;
    private String regtime;
    private String regplace;
    private String medicalcode;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getHandletype() {
        return handletype;
    }

    public void setHandletype(String handletype) {
        this.handletype = handletype;
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getRegplace() {
        return regplace;
    }

    public void setRegplace(String regplace) {
        this.regplace = regplace;
    }

    public String getMedicalcode() {
        return medicalcode;
    }

    public void setMedicalcode(String medicalcode) {
        this.medicalcode = medicalcode;
    }
}
