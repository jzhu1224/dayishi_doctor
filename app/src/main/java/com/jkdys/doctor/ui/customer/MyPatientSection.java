package com.jkdys.doctor.ui.customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.jkdys.doctor.data.model.PatientInfo;

public class MyPatientSection extends SectionEntity<PatientInfo> implements Parcelable{

    public MyPatientSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MyPatientSection(PatientInfo patientInfo) {
        super(patientInfo);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isHeader ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.t, flags);
        dest.writeString(this.header);
    }

    public MyPatientSection(Parcel in) {
        super(null);
        this.isHeader = in.readByte() != 0;
        this.t = in.readParcelable(PatientInfo.class.getClassLoader());
        this.header = in.readString();
    }

    public static final Creator<MyPatientSection> CREATOR = new Creator<MyPatientSection>() {
        @Override
        public MyPatientSection createFromParcel(Parcel source) {
            return new MyPatientSection(source);
        }

        @Override
        public MyPatientSection[] newArray(int size) {
            return new MyPatientSection[size];
        }
    };
}
