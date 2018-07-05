package com.jkdys.doctor.data.model;

public class AccountData {
    public int telfee;
    public int outpatientfee;
    public int promotefee;
    public int undrawnamount;
    public int totalamount;
    public boolean bindornot;

    public int getTelfee() {
        return telfee;
    }

    public void setTelfee(int telfee) {
        this.telfee = telfee;
    }

    public int getOutpatientfee() {
        return outpatientfee;
    }

    public void setOutpatientfee(int outpatientfee) {
        this.outpatientfee = outpatientfee;
    }

    public int getPromotefee() {
        return promotefee;
    }

    public void setPromotefee(int promotefee) {
        this.promotefee = promotefee;
    }

    public int getUndrawnamount() {
        return undrawnamount;
    }

    public void setUndrawnamount(int undrawnamount) {
        this.undrawnamount = undrawnamount;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }

    public boolean isBindornot() {
        return bindornot;
    }

    public void setBindornot(boolean bindornot) {
        this.bindornot = bindornot;
    }
}
