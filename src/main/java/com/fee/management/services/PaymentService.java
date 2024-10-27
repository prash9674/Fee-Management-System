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
        String orderId = "REC-" + System.currentTimeMillis();
        List<Payment> existingPayments = paymentRepository.findByStudentId(studentId);

        Payment payment;
        String message;

        if (!existingPayments.isEmpty()) {
            payment = existingPayments.get(0);
            double newAmountPaid = payment.getAmountPaid() + amount;
            payment.setAmountPaid(newAmountPaid);

            if (newAmountPaid >= totalFee) {
                payment.setStatus("PAID");
                message = "Course fee paid in full.";
            } else {
                payment.setStatus("PARTIAL");
                message = "Partial payment processed. Remaining balance: " + (totalFee - newAmountPaid);
            }

            payment.setTimestamp(LocalDateTime.now());
            payment.setOrderId(orderId); // Ensure to update orderId if necessary
        } else {
            payment = new Payment();
            payment.setStudentId(studentId);
            payment.setTotalFee(totalFee);
            payment.setAmountPaid(amount);
            payment.setTimestamp(LocalDateTime.now());

            if (amount >= totalFee) {
                payment.setStatus("PAID");
                message = "Course fee paid in full.";
            } else {
                payment.setStatus("PARTIAL");
                message = "Partial payment processed. Remaining balance: " + (totalFee - amount);
            }

            payment.setOrderId(orderId);
        }

        // Set the message in the payment object
        payment.setMessage(message); // Set the message here

        Receipt receipt = new Receipt();
        receipt.setStudentId(payment.getStudentId());
        receipt.setOrderId(payment.getOrderId());
        receipt.setDate(LocalDateTime.now());
        receipt.setStatus(payment.getStatus());
        receipt.setTotalFee(payment.getTotalFee());
        receipt.setAmountPaid(payment.getAmountPaid());

        receiptRepository.save(receipt);

        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByStudentId(String studentId) {
        return paymentRepository.findByStudentId(studentId);
    }


}
