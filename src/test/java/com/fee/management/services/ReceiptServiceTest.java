package com.fee.management.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.fee.management.models.Receipt;
import com.fee.management.repositories.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

public class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReceiptByOrderId_Found() {
        String orderId = "ORD-123";
        Receipt receipt = new Receipt();
        receipt.setOrderId(orderId);
        receipt.setStudentId("U101");
        receipt.setAmountPaid(1500.0);
        receipt.setTotalFee(2000.0);
        receipt.setDate(LocalDateTime.now());
        receipt.setStatus("PAID");

        when(receiptRepository.findByOrderId(orderId)).thenReturn(Optional.of(receipt));

        Optional<Receipt> foundReceipt = receiptService.getReceiptByOrderId(orderId);

        assertTrue(foundReceipt.isPresent());
        assertEquals(orderId, foundReceipt.get().getOrderId());
        assertEquals("U101", foundReceipt.get().getStudentId());
        assertEquals(1500.0, foundReceipt.get().getAmountPaid());
        assertEquals(2000.0, foundReceipt.get().getTotalFee());
        assertEquals("PAID", foundReceipt.get().getStatus());

        verify(receiptRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    public void testGetReceiptByOrderId_NotFound() {
        String orderId = "ORD-999";

        when(receiptRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        Optional<Receipt> foundReceipt = receiptService.getReceiptByOrderId(orderId);

        assertTrue(foundReceipt.isEmpty());
        verify(receiptRepository, times(1)).findByOrderId(orderId);
    }
}
