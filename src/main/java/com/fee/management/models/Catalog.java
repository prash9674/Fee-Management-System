package com.fee.management.models;

public class Catalog {

    private String id;
    private String planName;
    private double amount;
    private String validForTerm;

    //Constructor
    public Catalog(String id, String planName, double amount, String validForTerm) {
        this.id = id;
        this.planName = planName;
        this.amount = amount;
        this.validForTerm = validForTerm;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPlanName() {
        return planName;
    }
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getValidForTerm() {
        return validForTerm;
    }
    public void setValidForTerm(String validForTerm) {
        this.validForTerm = validForTerm;
    }

}
