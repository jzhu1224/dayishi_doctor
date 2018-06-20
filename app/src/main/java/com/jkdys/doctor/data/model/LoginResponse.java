package com.jkdys.doctor.data.model;

public class LoginResponse {
    private String token;
    private Doctor doctor;
    private Doctorauthstatus doctorauthstatus;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctorauthstatus getDoctorauthstatus() {
        return doctorauthstatus;
    }

    public void setDoctorauthstatus(Doctorauthstatus doctorauthstatus) {
        this.doctorauthstatus = doctorauthstatus;
    }
}
