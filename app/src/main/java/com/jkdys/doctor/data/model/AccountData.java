package com.jkdys.doctor.data.model;

public class AccountData {
    private float telfee;
    private float outpatientfee;
    private float promotefee;
    private float undrawnamount;
    private float totalamount;
    private boolean bindornot;

    public float getTelfee() {
        return telfee;
    }

    public void setTelfee(float telfee) {
        this.telfee = telfee;
    }

    public float getOutpatientfee() {
        return outpatientfee;
    }

    public void setOutpatientfee(float outpatientfee) {
        this.outpatientfee = outpatientfee;
    }

    public float getPromotefee() {
        return promotefee;
    }

    public void setPromotefee(float promotefee) {
        this.promotefee = promotefee;
    }

    public float getUndrawnamount() {
        return undrawnamount;
    }

    public void setUndrawnamount(float undrawnamount) {
        this.undrawnamount = undrawnamount;
    }

    public float getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(float totalamount) {
        this.totalamount = totalamount;
    }

    public boolean isBindornot() {
        return bindornot;
    }

    public void setBindornot(boolean bindornot) {
        this.bindornot = bindornot;
    }
}
