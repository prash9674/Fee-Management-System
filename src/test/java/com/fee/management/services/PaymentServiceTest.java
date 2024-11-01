package com.fee.management.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fee.management.models.CatalogItem;
import com.fee.management.models.Payment;
import com.fee.management.models.Receipt;
import com.fee.management.repositories.CatalogRepository;
import com.fee.management.repositories.PaymentRepository;
import com.fee.management.repositories.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private CatalogRepository catalogRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPayment_FullyPaid() {
        String studentId = "U101";
        String courseName = "Mathematics";
        double paymentAmount = 2000.0;

        CatalogItem course = new CatalogItem("C101", courseName, 2000.0);
        when(catalogRepository.findByCourseName(courseName)).thenReturn(Optional.of(course));
        when(paymentRepository.findByStudentIdAndOrderId(studentId, "ORD-U101MATHEMATICS"))
                .thenReturn(Collections.emptyList());
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        when(receiptRepository.findByOrderId("ORD-U101MATHEMATICS")).thenReturn(Optional.empty());

        Payment savedPayment = paymentService.processPayment(studentId, courseName, paymentAmount);

        assertNotNull(savedPayment);
        assertEquals(studentId, savedPayment.getStudentId());
        assertEquals(paymentAmount, savedPayment.getAmountPaid());
        assertEquals("PAID", savedPayment.getStatus());
        assertEquals("Course fee paid in full.", savedPayment.getMessage());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    public void testProcessPayment_PartialPayment() {
        String studentId = "U101";
        String courseName = "Mathematics";
        double paymentAmount = 1000.0;

        CatalogItem course = new CatalogItem("C101", courseName, 2000.0);
        when(catalogRepository.findByCourseName(courseName)).thenReturn(Optional.of(course));
        when(paymentRepository.findByStudentIdAndOrderId(studentId, "ORD-U101MATHEMATICS"))
                .thenReturn(Collections.emptyList());
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        when(receiptRepository.findByOrderId("ORD-U101MATHEMATICS")).thenReturn(Optional.empty());

        Payment savedPayment = paymentService.processPayment(studentId, courseName, paymentAmount);

        assertNotNull(savedPayment);
        assertEquals(studentId, savedPayment.getStudentId());
        assertEquals(paymentAmount, savedPayment.getAmountPaid());
        assertEquals("PARTIAL", savedPayment.getStatus());
        assertEquals("Partial payment processed. Remaining balance: 1000.0", savedPayment.getMessage());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    public void testGetPendingFeeBalance() {
        String studentId = "U101";
        double totalFee = 2000.0;

        Payment payment1 = new Payment();
        payment1.setAmountPaid(1000.0);
        Payment payment2 = new Payment();
        payment2.setAmountPaid(500.0);
        when(paymentRepository.findByStudentId(studentId)).thenReturn(List.of(payment1, payment2));

        double pendingBalance = paymentService.getPendingFeeBalance(studentId, "Mathematics", totalFee);

        assertEquals(500.0, pendingBalance);
        verify(paymentRepository, times(1)).findByStudentId(studentId);
    }

    @Test
    public void testGetPaymentsByStudentId() {
        String studentId = "U101";
        Payment payment = new Payment();
        payment.setStudentId(studentId);
        payment.setAmountPaid(1500.0);

        when(paymentRepository.findByStudentId(studentId)).thenReturn(List.of(payment));

        List<Payment> payments = paymentService.getPaymentsByStudentId(studentId);

        assertEquals(1, payments.size());
        assertEquals(studentId, payments.get(0).getStudentId());
        assertEquals(1500.0, payments.get(0).getAmountPaid());
        verify(paymentRepository, times(1)).findByStudentId(studentId);
    }
}
