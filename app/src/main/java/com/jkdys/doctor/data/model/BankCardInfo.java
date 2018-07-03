package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BankCardInfo implements Parcelable {


    private String bankname;
    private String bankaccount;
    private String bankid;

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

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankname);
        dest.writeString(this.bankaccount);
        dest.writeString(this.bankid);
    }

    public BankCardInfo() {
    }

    protected BankCardInfo(Parcel in) {
        this.bankname = in.readString();
        this.bankaccount = in.readString();
        this.bankid = in.readString();
    }

    public static final Parcelable.Creator<BankCardInfo> CREATOR = new Parcelable.Creator<BankCardInfo>() {
        @Override
        public BankCardInfo createFromParcel(Parcel source) {
            return new BankCardInfo(source);
        }

        @Override
        public BankCardInfo[] newArray(int size) {
            return new BankCardInfo[size];
        }
    };
}