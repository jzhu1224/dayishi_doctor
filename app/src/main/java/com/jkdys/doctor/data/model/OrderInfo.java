package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderInfo implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderid);
        dest.writeString(this.picheadurl);
        dest.writeString(this.patientid);
        dest.writeString(this.patientname);
        dest.writeString(this.ordertype);
        dest.writeString(this.paytype);
        dest.writeString(this.paytypename);
        dest.writeString(this.amount);
        dest.writeString(this.orderdate);
        dest.writeByte(this.isvip ? (byte) 1 : (byte) 0);
        dest.writeString(this.status);
    }

    public OrderInfo() {
    }

    protected OrderInfo(Parcel in) {
        this.orderid = in.readString();
        this.picheadurl = in.readString();
        this.patientid = in.readString();
        this.patientname = in.readString();
        this.ordertype = in.readString();
        this.paytype = in.readString();
        this.paytypename = in.readString();
        this.amount = in.readString();
        this.orderdate = in.readString();
        this.isvip = in.readByte() != 0;
        this.status = in.readString();
    }

    public static final Parcelable.Creator<OrderInfo> CREATOR = new Parcelable.Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel source) {
            return new OrderInfo(source);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}