package com.fee.management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "receipts")
public class Receipt {
    @Id
    private String orderId;
    private String studentId;
    private double amountPaid;
    private double totalFee;
    private LocalDateTime date;
    private String status; // "PAID", "PARTIAL"

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

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Receipt(String orderId, String studentId, double amountPaid, double totalFee, LocalDateTime date, String status) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.amountPaid = amountPaid;
        this.totalFee = totalFee;
        this.date = date;
        this.status = status;
    }



    public Receipt() {

    }

}
