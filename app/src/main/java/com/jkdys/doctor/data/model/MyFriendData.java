package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyFriendData implements Parcelable {
    /**
     *  "did": "90e505a5-a964-4da2-9953-2d0b857510f3", //好友医生ID
     "name": "祝江", //医生姓名
     "picheadurl": null, //医生头像URL
     "hospitalname": "上海华山医院", //医院
     "facultyname": "中医理疗科", //科室
     "titlename": "主任医师" ,//职称
     "hxid": "90e505a5a9644da299532d0b857510f3"//环信ID
     */

    private String did;
    private String name;
    private String picheadurl;
    private String hospitalname;
    private String facultyname;
    private String titlename;
    private String hxid;

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

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
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
        dest.writeString(this.hxid);
    }

    public MyFriendData() {
    }

    protected MyFriendData(Parcel in) {
        this.did = in.readString();
        this.name = in.readString();
        this.picheadurl = in.readString();
        this.hospitalname = in.readString();
        this.facultyname = in.readString();
        this.titlename = in.readString();
        this.hxid = in.readString();
    }

    public static final Parcelable.Creator<MyFriendData> CREATOR = new Parcelable.Creator<MyFriendData>() {
        @Override
        public MyFriendData createFromParcel(Parcel source) {
            return new MyFriendData(source);
        }

        @Override
        public MyFriendData[] newArray(int size) {
            return new MyFriendData[size];
        }
    };
}
