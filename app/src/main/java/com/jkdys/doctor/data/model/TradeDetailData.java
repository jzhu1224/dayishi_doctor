package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TradeDetailData implements Parcelable {
    /**
     *   "status": "3",
     "statusname": "提现失败",
     "money": 600,
     "moneyshow": "-600.00",
     "accountname": "郑医生",
     "bankname": "中国银行",
     "bankaccount": "6214********5124",
     "cellphone": "18818333565",
     "tradetime": "2017-09-24 22:57",
     "tradeserialnumber": "20180523192827642987000778217828",
     "traderemark": null


     "money": 560, //交易金额
     "moneyshow": "+560.00", //显示金额
     "tradetime": "2018-07-26 18:00", //交易时间
     "orderid": "20180709163548460977000485878222", //订单号
     "ordertype": "1", //收入类型
     "ordertypename": "电话诊断收入" //收入类型


     */

    private String status;
    private String statusname;
    private String money;
    private String moneyshow;
    private String accountname;
    private String bankname;
    private String bankaccount;
    private String cellphone;
    private String tradetime;
    private String tradeserialnumber;
    private String traderemark;
    private String orderid;
    private String ordertype;
    private String ordertypename;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyshow() {
        return moneyshow;
    }

    public void setMoneyshow(String moneyshow) {
        this.moneyshow = moneyshow;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTradetime() {
        return tradetime;
    }

    public void setTradetime(String tradetime) {
        this.tradetime = tradetime;
    }

    public String getTradeserialnumber() {
        return tradeserialnumber;
    }

    public void setTradeserialnumber(String tradeserialnumber) {
        this.tradeserialnumber = tradeserialnumber;
    }

    public String getTraderemark() {
        return traderemark;
    }

    public void setTraderemark(String traderemark) {
        this.traderemark = traderemark;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrdertypename() {
        return ordertypename;
    }

    public void setOrdertypename(String ordertypename) {
        this.ordertypename = ordertypename;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.statusname);
        dest.writeValue(this.money);
        dest.writeString(this.moneyshow);
        dest.writeString(this.accountname);
        dest.writeString(this.bankname);
        dest.writeString(this.bankaccount);
        dest.writeString(this.cellphone);
        dest.writeString(this.tradetime);
        dest.writeString(this.tradeserialnumber);
        dest.writeString(this.traderemark);
        dest.writeString(this.orderid);
        dest.writeString(this.ordertype);
        dest.writeString(this.ordertypename);
    }

    public TradeDetailData() {
    }

    protected TradeDetailData(Parcel in) {
        this.status = in.readString();
        this.statusname = in.readString();
        this.money = in.readString();
        this.moneyshow = in.readString();
        this.accountname = in.readString();
        this.bankname = in.readString();
        this.bankaccount = in.readString();
        this.cellphone = in.readString();
        this.tradetime = in.readString();
        this.tradeserialnumber = in.readString();
        this.traderemark = in.readString();
        this.orderid = in.readString();
        this.ordertype = in.readString();
        this.ordertypename = in.readString();
    }

    public static final Parcelable.Creator<TradeDetailData> CREATOR = new Parcelable.Creator<TradeDetailData>() {
        @Override
        public TradeDetailData createFromParcel(Parcel source) {
            return new TradeDetailData(source);
        }

        @Override
        public TradeDetailData[] newArray(int size) {
            return new TradeDetailData[size];
        }
    };
}
