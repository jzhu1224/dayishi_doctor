package com.jkdys.doctor.data.model;

public class DoctorDetailData {
    /**
     * {
     "doctorpicheadurl": "……", //医生头像URL
     "doctorname": "郝总",
     "hospitalname": "上海第二人民医院",
     "facultyname": "神经内科",
     "titlename": "医师",
     "goodat": "", //擅长疾病
     "personalprofile": "" ,//个人简介
     "isfriend": true //是否是好友，true：是，false：不是
     }

     */

    private String doctorpicheadurl;
    private String doctorname;
    private String facultyname;
    private String titlename;
    private String goodat;
    private String personalprofile;
    private boolean isfriend;
    private String hxid;
    private String doctorid;

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

    public String getGoodat() {
        return goodat;
    }

    public void setGoodat(String goodat) {
        this.goodat = goodat;
    }

    public String getPersonalprofile() {
        return personalprofile;
    }

    public void setPersonalprofile(String personalprofile) {
        this.personalprofile = personalprofile;
    }

    public boolean isIsfriend() {
        return isfriend;
    }

    public void setIsfriend(boolean isfriend) {
        this.isfriend = isfriend;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }
}
