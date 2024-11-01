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
        String orderId = "REC-" + System.currentTimeMillis(); // Unique orderId for each transaction
        double amountPaid = amount;
        String message;
        String status;

        // Retrieve existing payments for this student to calculate cumulative paid amount
        List<Payment> existingPayments = paymentRepository.findByStudentId(studentId);

        double cumulativeAmountPaid = existingPayments.stream()
                .mapToDouble(Payment::getAmountPaid)
                .sum();

        // Check if the course fee has already been paid in full
        if (cumulativeAmountPaid >= totalFee) {
            // Create a response indicating that the fee is already paid
            Payment response = new Payment();
            response.setStudentId(studentId);
            response.setTotalFee(totalFee);
            response.setAmountPaid(0); // No new amount paid in this case
            response.setStatus("PAID");
            response.setMessage("No remaining balance for the course as course fee is paid in full.");
            response.setTimestamp(LocalDateTime.now());
            return response;
        }

        // Calculate the new cumulative amount with this payment
        double newTotalAmountPaid = cumulativeAmountPaid + amount;
        double remainingBalance = totalFee - newTotalAmountPaid;

        if (newTotalAmountPaid >= totalFee) {
            status = "PAID";
            message = "Course fee paid in full.";
            remainingBalance = 0.0; // Set remaining balance to 0 if fully paid
        } else {
            status = "PARTIAL";
            message = "Partial payment processed. Remaining balance: " + remainingBalance;
        }

        // Create a new payment record for this transaction
        Payment payment = new Payment();
        payment.setStudentId(studentId);
        payment.setTotalFee(totalFee);
        payment.setAmountPaid(amount); // Only the amount of this transaction
        payment.setTimestamp(LocalDateTime.now());
        payment.setOrderId(orderId);
        payment.setStatus(status);
        payment.setMessage(message);

        // Save the new payment document
        Payment savedPayment = paymentRepository.save(payment);

        // Create a new receipt for each payment
        Receipt receipt = new Receipt();
        receipt.setStudentId(studentId);
        receipt.setOrderId(orderId);
        receipt.setDate(LocalDateTime.now());
        receipt.setStatus(status);
        receipt.setTotalFee(totalFee);
        receipt.setAmountPaid(amount); // Log only the current transaction amount

        receiptRepository.save(receipt);

        return savedPayment;
    }

    public List<Payment> getPaymentsByStudentId(String studentId) {
        return paymentRepository.findByStudentId(studentId);
    }


}
