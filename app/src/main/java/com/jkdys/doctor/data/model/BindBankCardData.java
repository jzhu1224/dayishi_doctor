package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BindBankCardData implements Parcelable {
    private String bankname;//
    private String bankid;
    private String bankaccount;
    private String name;
    private String certificateno;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankname);
        dest.writeString(this.bankid);
        dest.writeString(this.bankaccount);
        dest.writeString(this.name);
        dest.writeString(this.certificateno);
    }

    public BindBankCardData() {
    }

    protected BindBankCardData(Parcel in) {
        this.bankname = in.readString();
        this.bankid = in.readString();
        this.bankaccount = in.readString();
        this.name = in.readString();
        this.certificateno = in.readString();
    }

    public static final Parcelable.Creator<BindBankCardData> CREATOR = new Parcelable.Creator<BindBankCardData>() {
        @Override
        public BindBankCardData createFromParcel(Parcel source) {
            return new BindBankCardData(source);
        }

        @Override
        public BindBankCardData[] newArray(int size) {
            return new BindBankCardData[size];
        }
    };
}
