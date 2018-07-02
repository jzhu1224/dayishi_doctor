package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 患者信息
 */
public class PatientInfo implements Parcelable {

    private String pid;
    private String patientname;
    private String headurl;
    private String certificatetype;
    private String certificateno;
    private String cellphone;
    private String gender;
    private String age;

    public String getPid() {
    return pid;
    }

    public void setPid(String pid) {
    this.pid = pid;
    }

    public String getPatientname() {
    return patientname;
    }

    public void setPatientname(String patientname) {
    this.patientname = patientname;
    }

    public String getHeadurl() {
    return headurl;
    }

    public void setHeadurl(String headurl) {
    this.headurl = headurl;
    }

    public String getCertificatetype() {
    return certificatetype;
    }

    public void setCertificatetype(String certificatetype) {
    this.certificatetype = certificatetype;
    }

    public String getCertificateno() {
    return certificateno;
    }

    public void setCertificateno(String certificateno) {
    this.certificateno = certificateno;
    }

    public String getCellphone() {
    return cellphone;
    }

    public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
    }

    public String getGender() {
    return gender;
    }

    public void setGender(String gender) {
    this.gender = gender;
    }

    public String getAge() {
    return age;
    }

    public void setAge(String age) {
    this.age = age;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pid);
        dest.writeString(this.patientname);
        dest.writeString(this.headurl);
        dest.writeString(this.certificatetype);
        dest.writeString(this.certificateno);
        dest.writeString(this.cellphone);
        dest.writeString(this.gender);
        dest.writeString(this.age);
    }

    public PatientInfo() {
    }

    protected PatientInfo(Parcel in) {
        this.pid = in.readString();
        this.patientname = in.readString();
        this.headurl = in.readString();
        this.certificatetype = in.readString();
        this.certificateno = in.readString();
        this.cellphone = in.readString();
        this.gender = in.readString();
        this.age = in.readString();
    }

    public static final Parcelable.Creator<PatientInfo> CREATOR = new Parcelable.Creator<PatientInfo>() {
        @Override
        public PatientInfo createFromParcel(Parcel source) {
            return new PatientInfo(source);
        }

        @Override
        public PatientInfo[] newArray(int size) {
            return new PatientInfo[size];
        }
    };
}