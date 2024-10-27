package com.fee.management.models;

public class FeeDetails {
    private double totalFee;
    private double amountPaid;

    public FeeDetails(double totalFee, double amountPaid, double remainingBalance, String status) {

        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
        this.remainingBalance = remainingBalance;
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

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private double remainingBalance;
    private String status;
}
