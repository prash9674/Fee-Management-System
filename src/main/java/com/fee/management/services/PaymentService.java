package com.fee.management.services;

import com.fee.management.models.Payment;
import com.fee.management.models.Receipt;
import com.fee.management.repositories.PaymentRepository;
import com.fee.management.repositories.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReceiptRepository receiptRepository;
    public PaymentService(PaymentRepository paymentRepository, ReceiptRepository receiptRepository) {
        this.paymentRepository = paymentRepository;
        this.receiptRepository = receiptRepository;
    }

    public Payment processPayment(String studentId, double amount, double totalFee) {
        Payment payment = paymentRepository.findByStudentId(studentId)
                .stream()
                .findFirst()
                .orElse(new Payment());

        payment.setStudentId(studentId);
        payment.setTotalFee(totalFee);

        double newAmountPaid = payment.getAmountPaid() + amount;
        payment.setAmountPaid(newAmountPaid);
        payment.setTimestamp(LocalDateTime.now());

        // Determine payment status based on the balance
        if (newAmountPaid >= totalFee) {
            payment.setStatus("PAID");
        } else {
            payment.setStatus("PARTIAL");
        }
        //generate Receipt
        payment.setOrderId("REC-" + System.currentTimeMillis());

        //Populate receipts collection
        Receipt r = new Receipt();
        r.setStudentId(payment.getStudentId());
        r.setOrderId(payment.getOrderId());
        r.setDate(LocalDateTime.now());
        r.setStatus(payment.getStatus());
        r.setTotalFee(payment.getTotalFee());
        r.getAmountPaid(payment.getAmountPaid());
        receiptRepository.save(r);
        //populate payments collection
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByStudentId(String studentId) {
        return paymentRepository.findByStudentId(studentId);
    }


}
