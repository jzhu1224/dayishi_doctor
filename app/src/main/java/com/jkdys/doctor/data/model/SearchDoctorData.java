package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchDoctorData implements Parcelable {
    private String name;
    private String id;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.info);
    }

    public SearchDoctorData() {
    }

    protected SearchDoctorData(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.info = in.readString();
    }

    public static final Parcelable.Creator<SearchDoctorData> CREATOR = new Parcelable.Creator<SearchDoctorData>() {
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
