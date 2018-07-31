package com.jkdys.doctor.data.model;

public class PhoneNumberDetail {
    /**
     * "doctorcellphone": "18913223885",//医生手机号码
     "cellphone": "17186408025"//虚拟电话号码，十分钟之内拨打有效
     */
    private String doctorcellphone;
    private String cellphone;

    public String getDoctorcellphone() {
        return doctorcellphone;
    }

    public void setDoctorcellphone(String doctorcellphone) {
        this.doctorcellphone = doctorcellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
