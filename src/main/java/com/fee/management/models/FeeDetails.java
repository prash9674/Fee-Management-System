package com.fee.management.models;

public class FeeDetails {
    private double totalFee;
    private double amountPaid;
    private String status;

    public FeeDetails(double totalFee, double amountPaid, String status) {

        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
        this.status = status;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
