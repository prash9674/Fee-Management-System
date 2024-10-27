package com.fee.management.services;

import com.fee.management.models.Payment;
import com.fee.management.models.Receipt;
import com.fee.management.repositories.PaymentRepository;
import com.fee.management.repositories.ReceiptRepository;
import com.fee.management.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPayment_NewPayment() {
        // Arrange
        String studentId = "1";
        double amount = 100.0;
        double totalFee = 500.0;

        // Mock paymentRepository to return an empty list, simulating no prior payment
        when(paymentRepository.findByStudentId(studentId)).thenReturn(new ArrayList<>());

        // Configure save to return the Payment object passed to it
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(new Receipt());  // Mock saving Receipt as well

        // Act
        Payment result = paymentService.processPayment(studentId, amount, totalFee);

        // Assert
        assertNotNull(result);  // Ensure result is not null
        assertEquals(studentId, result.getStudentId());
        assertEquals(totalFee, result.getTotalFee());
        assertEquals(amount, result.getAmountPaid());
        assertEquals("PARTIAL", result.getStatus());  // Status should be "PARTIAL" when amount is less than totalFee
        assertNotNull(result.getOrderId());  // Ensure an orderId is generated

        // Verify save calls
        verify(paymentRepository, times(1)).save(result);
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }


    @Test
    public void testProcessPayment_ExistingPaymentPartial() {
        // Arrange
        String studentId = "1";
        double amount = 100.0;
        double totalFee = 500.0;
        Payment existingPayment = new Payment();
        existingPayment.setStudentId(studentId);
        existingPayment.setTotalFee(totalFee);
        existingPayment.setAmountPaid(200.0);

        // Mocking the find and save behavior
        when(paymentRepository.findByStudentId(studentId)).thenReturn(List.of(existingPayment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(new Receipt());  // Mock saving Receipt as well

        // Act
        Payment result = paymentService.processPayment(studentId, amount, totalFee);

        // Assert
        assertNotNull(result);
        assertEquals(studentId, result.getStudentId());
        assertEquals(totalFee, result.getTotalFee());
        assertEquals(300.0, result.getAmountPaid());  // Checks if amount is updated
        assertEquals("PARTIAL", result.getStatus());  // Checks if status is correctly updated

        // Verify the save calls
        verify(paymentRepository, times(1)).save(result);
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }


    @Test
    public void testProcessPayment_FullPayment() {
        // Arrange
        String studentId = "1";
        double amount = 300.0;
        double totalFee = 300.0;
        Payment existingPayment = new Payment();
        existingPayment.setStudentId(studentId);
        existingPayment.setTotalFee(totalFee);
        existingPayment.setAmountPaid(0.0);

        // Mocking the find and save behavior
        when(paymentRepository.findByStudentId(studentId)).thenReturn(List.of(existingPayment));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(receiptRepository.save(any(Receipt.class))).thenReturn(new Receipt());  // Mock saving Receipt as well

        // Act
        Payment result = paymentService.processPayment(studentId, amount, totalFee);

        // Assert
        assertNotNull(result);  // Ensure result is not null
        assertEquals(studentId, result.getStudentId());
        assertEquals(totalFee, result.getTotalFee());
        assertEquals(totalFee, result.getAmountPaid());  // Should match full payment amount
        assertEquals("PAID", result.getStatus());  // Status should be "PAID" when amount meets totalFee

        // Verify the save calls
        verify(paymentRepository, times(1)).save(result);
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }


    @Test
    public void testGetPaymentsByStudentId_PaymentsExist() {
        // Arrange
        String studentId = "1";
        Payment payment1 = new Payment();
        payment1.setStudentId(studentId);
        Payment payment2 = new Payment();
        payment2.setStudentId(studentId);

        when(paymentRepository.findByStudentId(studentId)).thenReturn(List.of(payment1, payment2));

        // Act
        List<Payment> result = paymentService.getPaymentsByStudentId(studentId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findByStudentId(studentId);
    }

    @Test
    public void testGetPaymentsByStudentId_NoPaymentsExist() {
        // Arrange
        String studentId = "1";
        when(paymentRepository.findByStudentId(studentId)).thenReturn(new ArrayList<>());

        // Act
        List<Payment> result = paymentService.getPaymentsByStudentId(studentId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(paymentRepository, times(1)).findByStudentId(studentId);
    }
}
