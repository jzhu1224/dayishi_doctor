package com.jkdys.doctor.data.model;

public class AccountData {
    private String telfee;
    private String outpatientfee;
    private String promotefee;
    private String undrawnamount;
    private String totalamount;
    private boolean bindornot;

    public String getTelfee() {
        return telfee;
    }

    public void setTelfee(String telfee) {
        this.telfee = telfee;
    }

    public String getOutpatientfee() {
        return outpatientfee;
    }

    public void setOutpatientfee(String outpatientfee) {
        this.outpatientfee = outpatientfee;
    }

    public String getPromotefee() {
        return promotefee;
    }

    public void setPromotefee(String promotefee) {
        this.promotefee = promotefee;
    }

    public String getUndrawnamount() {
        return undrawnamount;
    }

    public void setUndrawnamount(String undrawnamount) {
        this.undrawnamount = undrawnamount;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public boolean isBindornot() {
        return bindornot;
    }

    public void setBindornot(boolean bindornot) {
        this.bindornot = bindornot;
    }
}
