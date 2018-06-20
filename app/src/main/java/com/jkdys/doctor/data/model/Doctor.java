package com.jkdys.doctor.data.model;

public class Doctor {
    private String did;
    private String name;
    private String certificatetype;
    private String certificateno;
    private Boolean verifystatus;
    private String cellphoneno;
    private String birthdate;
    private String gender;
    private String areaid;
    private Integer doctorNo;
    private Integer telfee;
    private Integer outpatientfee;
    private Integer promotefee;

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

    public Boolean getVerifystatus() {
        return verifystatus;
    }

    public void setVerifystatus(Boolean verifystatus) {
        this.verifystatus = verifystatus;
    }

    public String getCellphoneno() {
        return cellphoneno;
    }

    public void setCellphoneno(String cellphoneno) {
        this.cellphoneno = cellphoneno;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public Integer getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(Integer doctorNo) {
        this.doctorNo = doctorNo;
    }

    public Integer getTelfee() {
        return telfee;
    }

    public void setTelfee(Integer telfee) {
        this.telfee = telfee;
    }

    public Integer getOutpatientfee() {
        return outpatientfee;
    }

    public void setOutpatientfee(Integer outpatientfee) {
        this.outpatientfee = outpatientfee;
    }

    public Integer getPromotefee() {
        return promotefee;
    }

    public void setPromotefee(Integer promotefee) {
        this.promotefee = promotefee;
    }

    public Integer getUndrawnamount() {
        return undrawnamount;
    }

    public void setUndrawnamount(Integer undrawnamount) {
        this.undrawnamount = undrawnamount;
    }

    public Integer getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Integer totalamount) {
        this.totalamount = totalamount;
    }

    public Integer getTeltimes() {
        return teltimes;
    }

    public void setTeltimes(Integer teltimes) {
        this.teltimes = teltimes;
    }

    public Integer getPromotetimes() {
        return promotetimes;
    }

    public void setPromotetimes(Integer promotetimes) {
        this.promotetimes = promotetimes;
    }

    public Integer getTotaldiagnosistimes() {
        return totaldiagnosistimes;
    }

    public void setTotaldiagnosistimes(Integer totaldiagnosistimes) {
        this.totaldiagnosistimes = totaldiagnosistimes;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Integer undrawnamount;
    private Integer totalamount;
    private Integer teltimes;
    private Integer promotetimes;
    private Integer totaldiagnosistimes;
    private Integer level;
    private String status;

}