package com.jkdys.doctor.data.model;

import java.util.List;

public class PhoneOrderDetail {

    private String doctorpicheadurl;
    private String doctorname;
    private String hospitalname;
    private String facultyname;
    private String titlename;
    private String paytype;
    private String paytypename;
    private Double amount;
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
    private Boolean isvip;
    private String medicalrecord;

    private List<DiagnosisImage> images;

    public String getDoctorpicheadurl() {
        return doctorpicheadurl;
    }

    public void setDoctorpicheadurl(String doctorpicheadurl) {
        this.doctorpicheadurl = doctorpicheadurl;
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

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPaytypename() {
        return paytypename;
    }

    public void setPaytypename(String paytypename) {
        this.paytypename = paytypename;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    public Boolean getIsvip() {
        return isvip;
    }

    public void setIsvip(Boolean isvip) {
        this.isvip = isvip;
    }

    public String getMedicalrecord() {
        return medicalrecord;
    }

    public void setMedicalrecord(String medicalrecord) {
        this.medicalrecord = medicalrecord;
    }


    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public List<DiagnosisImage> getImages() {
        return images;
    }

    public void setImages(List<DiagnosisImage> images) {
        this.images = images;
    }
}