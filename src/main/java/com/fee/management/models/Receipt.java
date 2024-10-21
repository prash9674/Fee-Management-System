package com.fee.management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "receipts")
public class Receipt {
     @Id
    private String id;
    private String orderId;
    private String studentId;
    private double amount;
    private String status = "PAID";
    private String receiptDate;

    // No-argument constructor
    public Receipt() {
    }

    //Constructor
    public Receipt(String id,String orderId, String studentId, double amount, String status, String receiptDate) {
        this.id = id;
        this.orderId = orderId;
        this.studentId = studentId;
        this.amount = amount;
        this.status = status;
        this.receiptDate = receiptDate;
    }

    public Receipt(String orderId, String studentId, double amount,String receiptDate) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.amount = amount;
        this.receiptDate = receiptDate;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getReceiptDate() {
        return receiptDate;
    }
    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

}
