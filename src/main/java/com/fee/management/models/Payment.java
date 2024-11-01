package com.fee.management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "payments")
public class Payment {
    @Id
    private String id;
    private String orderId;
    private String studentId;
    private double amountPaid;
    private String message;// Total amount paid so far

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public Payment(String id, String orderId, String studentId, double amountPaid, double totalFee, String status, LocalDateTime timestamp) {
        this.id = id;
        this.orderId = orderId;
        this.studentId = studentId;
        this.amountPaid = amountPaid;
        this.totalFee = totalFee;
        this.status = status;
        this.timestamp = timestamp;
    }

    private double totalFee;    // Total fee for the student/course
    private String status;      // "PARTIAL", "PAID"
    private LocalDateTime timestamp;

    public Payment() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
