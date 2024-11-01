package com.fee.management.services;

import com.fee.management.models.CatalogItem;
import com.fee.management.models.Payment;
import com.fee.management.models.Receipt;
import com.fee.management.repositories.CatalogRepository;
import com.fee.management.repositories.PaymentRepository;
import com.fee.management.repositories.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ReceiptRepository receiptRepository;
    private final CatalogRepository catalogRepository;
    public PaymentService(PaymentRepository paymentRepository, ReceiptRepository receiptRepository, CatalogRepository catalogRepository) {
        this.paymentRepository = paymentRepository;
        this.receiptRepository = receiptRepository;
        this.catalogRepository = catalogRepository;
    }

    public Payment processPayment(String studentId, String courseName, double amount) {
        // Retrieve the course information from `CatalogItem` by `courseId`
        CatalogItem course = catalogRepository.findByCourseName(courseName)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with Name: " + courseName));

        double totalFee = course.getFee(); // Total fee for the course
        String orderId = "ORD-"+studentId.toUpperCase()+courseName.toUpperCase(); // Use `courseId` as the `orderId` for all payments related to this course

        // Retrieve existing payments for this student and course
        List<Payment> existingPayments = paymentRepository.findByStudentIdAndOrderId(studentId, orderId);

        // Calculate the cumulative amount paid so far for this course
        double cumulativeAmountPaid = existingPayments.stream()
                .mapToDouble(Payment::getAmountPaid)
                .sum();

        // Calculate the new cumulative amount with this payment
        double newTotalAmountPaid = cumulativeAmountPaid + amount;
        double remainingBalance = totalFee - newTotalAmountPaid;

        String status;
        String message;

        // Determine the payment status based on the total amount and remaining balance
        if (newTotalAmountPaid > totalFee) {
            status = "PAID";
            double excessAmount = newTotalAmountPaid - totalFee;
            message = "Course fee paid in full. Excess amount of " + excessAmount + " will be refunded.";
            remainingBalance = 0.0; // Fully paid, set remaining balance to 0
        } else if (newTotalAmountPaid == totalFee) {
            status = "PAID";
            message = "Course fee paid in full.";
            remainingBalance = 0.0; // Fully paid, set remaining balance to 0
        } else {
            status = "PARTIAL";
            message = "Partial payment processed. Remaining balance: " + remainingBalance;
        }

        // Create a new payment record for this transaction
        Payment payment = new Payment();
        payment.setStudentId(studentId);
        payment.setOrderId(orderId);
        payment.setAmountPaid(amount); // Only the current transaction amount
        payment.setMessage(message);
        payment.setStatus(status);
        payment.setTotalFee(totalFee);
        payment.setTimestamp(LocalDateTime.now());

        // Save the new payment document
        Payment savedPayment = paymentRepository.save(payment);

        // Update or create a receipt based on cumulative payments
        Receipt receipt = receiptRepository.findByOrderId(orderId)
                .orElse(new Receipt()); // Create new if not found

        receipt.setStudentId(studentId);
        receipt.setOrderId(orderId);
        receipt.setDate(LocalDateTime.now());
        receipt.setStatus(status);
        receipt.setTotalFee(totalFee);
        receipt.setAmountPaid(newTotalAmountPaid); // Update with cumulative amount

        receiptRepository.save(receipt);

        return savedPayment;
    }



    public double getPendingFeeBalance(String studentId, String courseName, double totalFee) {
        List<Payment> payments = paymentRepository.findByStudentId(studentId);
        double cumulativeAmountPaid = payments.stream()
                .mapToDouble(Payment::getAmountPaid)
                .sum();
        double remainingBalance = totalFee - cumulativeAmountPaid;
        return Math.max(remainingBalance, 0.0);
    }

    public List<Payment> getPaymentsByStudentId(String studentId) {
        List<Payment> payments = paymentRepository.findByStudentId(studentId);
        return payments;
    }
}
