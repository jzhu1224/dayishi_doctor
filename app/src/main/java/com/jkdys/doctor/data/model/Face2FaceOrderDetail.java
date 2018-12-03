package com.jkdys.doctor.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Face2FaceOrderDetail {

    private String doctorpicheadurl;
    private String doctorname;
    private Object hospitalname;
    private Object facultyname;
    private Object titlename;
    private Object paytype;
    private String paytypename;
    private String amount;
    private String orderdate;
    private String status;
    private Boolean isself;
    private String picheadurl;
    private String patientid;
    private String patientname;
    private String certificatetype;
    private String certificateno;
    private String cellphone;
    private String gender;
    private String age;
    private Boolean candelay;
    private String regplace;
    private String regtime;
    private String regstatus;
    private List<DelayRecord> delayrecord;
    private String historyregplace;

    public String getDoctorpicheadurl() {
        return doctorpicheadurl;
    }

    public void setDoctorpicheadurl(String doctorpicheadurl) {
        this.doctorpicheadurl = doctorpicheadurl;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public Object getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(Object hospitalname) {
        this.hospitalname = hospitalname;
    }

    public Object getFacultyname() {
        return facultyname;
    }

    public void setFacultyname(Object facultyname) {
        this.facultyname = facultyname;
    }

    public Object getTitlename() {
        return titlename;
    }

    public void setTitlename(Object titlename) {
        this.titlename = titlename;
    }

    public Object getPaytype() {
        return paytype;
    }

    public void setPaytype(Object paytype) {
        this.paytype = paytype;
    }

    public String getPaytypename() {
        return paytypename;
    }

    public void setPaytypename(String paytypename) {
        this.paytypename = paytypename;
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

    public Boolean getIsself() {
        return isself;
    }

    public void setIsself(Boolean isself) {
        this.isself = isself;
    }

    public String getPicheadurl() {
        return picheadurl;
    }

    public void setPicheadurl(String picheadurl) {
        this.picheadurl = picheadurl;
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

    public Boolean getCandelay() {
        return candelay;
    }

    public void setCandelay(Boolean candelay) {
        this.candelay = candelay;
    }

    public String getRegplace() {
        return regplace;
    }

    public void setRegplace(String regplace) {
        this.regplace = regplace;
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getRegstatus() {
        return regstatus;
    }

    public void setRegstatus(String regstatus) {
        this.regstatus = regstatus;
    }

    public List<DelayRecord> getDelayrecord() {
        return delayrecord;
    }

    public void setDelayrecord(List<DelayRecord> delayrecord) {
        this.delayrecord = delayrecord;
    }

    public String getHistoryregplace() {
        return historyregplace;
    }

    public void setHistoryregplace(String historyregplace) {
        this.historyregplace = historyregplace;
    }

    public static class DelayRecord implements Parcelable {

        private String regplace;
        private String regtime;
        private String modifytime;

        public String getRegplace() {
            return regplace;
        }

        public void setRegplace(String regplace) {
            this.regplace = regplace;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getModifytime() {
            return modifytime;
        }

        public void setModifytime(String modifytime) {
            this.modifytime = modifytime;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.regplace);
            dest.writeString(this.regtime);
            dest.writeString(this.modifytime);
        }

        public DelayRecord() {
        }

        protected DelayRecord(Parcel in) {
            this.regplace = in.readString();
            this.regtime = in.readString();
            this.modifytime = in.readString();
        }

        public static final Parcelable.Creator<DelayRecord> CREATOR = new Parcelable.Creator<DelayRecord>() {
            @Override
            public DelayRecord createFromParcel(Parcel source) {
                return new DelayRecord(source);
            }

            @Override
            public DelayRecord[] newArray(int size) {
                return new DelayRecord[size];
            }
        };
    }
}