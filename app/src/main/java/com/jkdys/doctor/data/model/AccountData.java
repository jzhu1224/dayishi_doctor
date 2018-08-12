package com.jkdys.doctor.data.model;

public class AccountData {
    private double telfee;
    private double outpatientfee;
    private double promotefee;
    private double undrawnamount;
    private double totalamount;
    private boolean bindornot;

    public double getTelfee() {
        return telfee;
    }

    public void setTelfee(double telfee) {
        this.telfee = telfee;
    }

    public double getOutpatientfee() {
        return outpatientfee;
    }

    public void setOutpatientfee(double outpatientfee) {
        this.outpatientfee = outpatientfee;
    }

    public double getPromotefee() {
        return promotefee;
    }

    public void setPromotefee(double promotefee) {
        this.promotefee = promotefee;
    }

    public double getUndrawnamount() {
        return undrawnamount;
    }

    public void setUndrawnamount(double undrawnamount) {
        this.undrawnamount = undrawnamount;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public boolean isBindornot() {
        return bindornot;
    }

    public void setBindornot(boolean bindornot) {
        this.bindornot = bindornot;
    }
}
