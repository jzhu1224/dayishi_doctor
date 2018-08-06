package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TradeRecord implements Parcelable {
    //            "type": "2", //交易类型
    //            "typename": "取现",  //交易类型名称
    //            "money": 600, //金额
    //            "showmoney": "-600.00", //显示金额
    //            "date": "2018-07-18 12:08", //交易日期
    //            "status": "1", //状态。0是待处理，1是处理中，2是已完成，3是失败
    //            "statusname": "处理中" //状态

    private String rid;
    private String type;
    private String typename;
    private String money;
    private String showmoney;
    private String date;
    private String status;
    private String statusname;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getShowmoney() {
        return showmoney;
    }

    public void setShowmoney(String showmoney) {
        this.showmoney = showmoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getRid() {
        return rid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rid);
        dest.writeString(this.type);
        dest.writeString(this.typename);
        dest.writeString(this.money);
        dest.writeString(this.showmoney);
        dest.writeString(this.date);
        dest.writeString(this.status);
        dest.writeString(this.statusname);
    }

    public TradeRecord() {
    }

    protected TradeRecord(Parcel in) {
        this.rid = in.readString();
        this.type = in.readString();
        this.typename = in.readString();
        this.money = in.readString();
        this.showmoney = in.readString();
        this.date = in.readString();
        this.status = in.readString();
        this.statusname = in.readString();
    }

    public static final Creator<TradeRecord> CREATOR = new Creator<TradeRecord>() {
        @Override
        public TradeRecord createFromParcel(Parcel source) {
            return new TradeRecord(source);
        }

        @Override
        public TradeRecord[] newArray(int size) {
            return new TradeRecord[size];
        }
    };
}
