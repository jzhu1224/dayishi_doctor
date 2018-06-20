package com.jkdys.doctor.data.model;

public class Doctorauthstatus {

    private Boolean realnameauth;
    private Boolean hospitaltowork;
    private Boolean doctorlicense;
    private Boolean doctorworkcard;
    private Boolean verifystatus;
    private Integer redirecttopage;

    public Boolean getRealnameauth() {
        return realnameauth;
    }

    public void setRealnameauth(Boolean realnameauth) {
        this.realnameauth = realnameauth;
    }

    public Boolean getHospitaltowork() {
        return hospitaltowork;
    }

    public void setHospitaltowork(Boolean hospitaltowork) {
        this.hospitaltowork = hospitaltowork;
    }

    public Boolean getDoctorlicense() {
        return doctorlicense;
    }

    public void setDoctorlicense(Boolean doctorlicense) {
        this.doctorlicense = doctorlicense;
    }

    public Boolean getDoctorworkcard() {
        return doctorworkcard;
    }

    public void setDoctorworkcard(Boolean doctorworkcard) {
        this.doctorworkcard = doctorworkcard;
    }

    public Boolean getVerifystatus() {
        return verifystatus;
    }

    public void setVerifystatus(Boolean verifystatus) {
        this.verifystatus = verifystatus;
    }

    public Integer getRedirecttopage() {
        return redirecttopage;
    }

    public void setRedirecttopage(Integer redirecttopage) {
        this.redirecttopage = redirecttopage;
    }
}