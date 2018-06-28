package com.jkdys.doctor.data.model;

public class OrderInfo {
    private String orderid;
    private String picheadurl;
    private String patientid;
    private String patientname;
    private String ordertype;
    private String paytype;
    private String paytypename;
    private String amount;
    private String orderdate;
    private boolean isvip;
    private String status;

    public String getOrderid() {
return orderid;
}

    public void setOrderid(String orderid) {
this.orderid = orderid;
}

    public String getPatientid() {
return patientid;
}

    public void setPatientid(String patientid) {
this.patientid = patientid;
}

    public String getPatientname() {
return patientname;
}

    public void setPatientname(String patientname) {
this.patientname = patientname;
}

    public String getOrdertype() {
return ordertype;
}

    public void setOrdertype(String ordertype) {
this.ordertype = ordertype;
}

    public String getPaytype() {
return paytype;
}

    public void setPaytype(String paytype) {
this.paytype = paytype;
}

    public String getAmount() {
return amount;
}

    public void setAmount(String amount) {
this.amount = amount;
}

    public String getOrderdate() {
return orderdate;
}

    public void setOrderdate(String orderdate) {
this.orderdate = orderdate;
}

    public String getStatus() {
return status;
}

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicheadurl() {
        return picheadurl;
    }

    public void setPicheadurl(String picheadurl) {
        this.picheadurl = picheadurl;
    }

    public String getPaytypename() {
        return paytypename;
    }

    public void setPaytypename(String paytypename) {
        this.paytypename = paytypename;
    }

    public boolean isIsvip() {
        return isvip;
    }

    public void setIsvip(boolean isvip) {
        this.isvip = isvip;
    }
}