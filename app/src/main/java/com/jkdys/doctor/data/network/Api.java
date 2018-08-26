package com.jkdys.doctor.data.network;

public interface Api {
    String DOCTOR_DOMAIN = "http://www.jkdys.com:8080";
    //法律声明url：/Doctor/LegalDeclarationMVC/LegalDeclarationIndex
    //服务协议url：/Doctor/ServiceAgreementMVC/ServiceAgreementIndex
    String STATEMENTS = DOCTOR_DOMAIN + "/Doctor/LegalDeclarationMVC/LegalDeclarationIndex";
    String SERVICE_CONTRACT = DOCTOR_DOMAIN + "/Doctor/ServiceAgreementMVC/ServiceAgreementIndex";
}
