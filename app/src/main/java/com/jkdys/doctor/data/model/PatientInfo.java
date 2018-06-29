package com.jkdys.doctor.data.model;

/**
 * 患者信息
 */
public class PatientInfo {

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

}