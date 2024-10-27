package com.fee.management.services;

import com.fee.management.models.Receipt;
import com.fee.management.repositories.ReceiptRepository;
import com.fee.management.services.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ReceiptServiceTest {
    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReceiptByOrderId_ReceiptExists() {
        // Arrange: Prepare a sample receipt
        String orderId = "REC-1730033114686";
        Receipt receipt = new Receipt(orderId,"abc",4000.0,4000.0, LocalDateTime.now(),"PAID");
        when(receiptRepository.findByOrderId(orderId)).thenReturn(Optional.of(receipt));

        // Act: Call the method under test
        Optional<Receipt> foundReceipt = receiptService.getReceiptByOrderId(orderId);

        // Assert: Verify the result
        assertTrue(foundReceipt.isPresent());
        assertEquals(orderId, foundReceipt.get().getOrderId());
        verify(receiptRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    public void testGetReceiptByOrderId_ReceiptDoesNotExist() {
        // Arrange: Mock the receiptRepository to return an empty Optional
        String orderId = "12345";
        when(receiptRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        // Act: Call the method under test
        Optional<Receipt> foundReceipt = receiptService.getReceiptByOrderId(orderId);

        // Assert: Verify the result
        assertFalse(foundReceipt.isPresent());
        verify(receiptRepository, times(1)).findByOrderId(orderId);
    }
}
