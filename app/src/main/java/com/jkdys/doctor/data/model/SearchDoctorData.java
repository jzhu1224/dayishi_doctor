package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchDoctorData implements Parcelable {
    private String did;
    private String name;
    private String picheadurl;
    private String hospitalname;
    private String facultyname;
    private String titlename;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicheadurl() {
        return picheadurl;
    }

    public void setPicheadurl(String picheadurl) {
        this.picheadurl = picheadurl;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(String facultyname) {
        this.facultyname = facultyname;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.did);
        dest.writeString(this.name);
        dest.writeString(this.picheadurl);
        dest.writeString(this.hospitalname);
        dest.writeString(this.facultyname);
        dest.writeString(this.titlename);
    }

    public SearchDoctorData() {
    }

    protected SearchDoctorData(Parcel in) {
        this.did = in.readString();
        this.name = in.readString();
        this.picheadurl = in.readString();
        this.hospitalname = in.readString();
        this.facultyname = in.readString();
        this.titlename = in.readString();
    }

    public static final Creator<SearchDoctorData> CREATOR = new Creator<SearchDoctorData>() {
        @Override
        public SearchDoctorData createFromParcel(Parcel source) {
            return new SearchDoctorData(source);
        }

        @Override
        public SearchDoctorData[] newArray(int size) {
            return new SearchDoctorData[size];
        }
    };
}
