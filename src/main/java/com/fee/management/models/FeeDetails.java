package com.fee.management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedetails")
public class FeeDetails {
    @Id
    private String id;
    private String studentId;
    private double amount;
    private String paymentMethod;
    private String term;
    private String status = "PENDING";
    private String receiptId;
    private String createdAt;

    // No-argument constructor
    public FeeDetails() {
    }

    //Constructor
    public FeeDetails(String id, String studentId, double amount, String paymentMethod, String term, String status,
            String receiptId, String createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.term = term;
        this.status = status;
        this.receiptId = receiptId;
        this.createdAt = createdAt;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getReceiptId() {
        return receiptId;
    }
    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
