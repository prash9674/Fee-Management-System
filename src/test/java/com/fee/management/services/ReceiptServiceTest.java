package com.fee.management.services;

import com.fee.management.models.FeeDetails;
import com.fee.management.models.Receipt;
import com.fee.management.repositories.ReceiptRepository;
import com.fee.management.services.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testCreateReceipt_Success() {
        // Arrange
        FeeDetails paymentData = new FeeDetails();
        paymentData.setReceiptId("REC12345");
        paymentData.setStudentId("12345");
        paymentData.setAmount(1000.0);

        Receipt expectedReceipt = new Receipt("REC12345", "12345", 1000.0, LocalDate.now().toString());

        // Mock the behavior of receiptRepository
        when(receiptRepository.save(any(Receipt.class))).thenReturn(expectedReceipt);

        // Act
        Receipt actualReceipt = receiptService.createReceipt(paymentData);

        // Assert
        assertNotNull(actualReceipt);
        assertEquals(expectedReceipt.getOrderId(), actualReceipt.getOrderId());
        assertEquals(expectedReceipt.getStudentId(), actualReceipt.getStudentId());
        assertEquals(expectedReceipt.getAmount(), actualReceipt.getAmount());
        assertEquals(expectedReceipt.getReceiptDate(), actualReceipt.getReceiptDate());
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    void testCreateReceipt_Failure() {
        // Arrange
        FeeDetails paymentData = new FeeDetails();
        paymentData.setReceiptId("REC12345");
        paymentData.setStudentId("12345");
        paymentData.setAmount(1000.0);

        // Mock the behavior of receiptRepository to throw an exception
        when(receiptRepository.save(any(Receipt.class))).thenThrow(new RuntimeException("Failed to save receipt"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            receiptService.createReceipt(paymentData);
        });

        assertEquals("Failed to save receipt", exception.getMessage());
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    void testGetReceiptByOrderId_Success() {
        // Arrange
        String orderId = "REC12345";
        Receipt expectedReceipt = new Receipt("REC12345", "12345", 1000.0, LocalDate.now().toString());

        // Mock the behavior of receiptRepository
        when(receiptRepository.findByOrderId(orderId)).thenReturn(expectedReceipt);

        // Act
        Receipt actualReceipt = receiptService.getReceiptByOrderId(orderId);

        // Assert
        assertNotNull(actualReceipt);
        assertEquals(expectedReceipt.getOrderId(), actualReceipt.getOrderId());
        assertEquals(expectedReceipt.getStudentId(), actualReceipt.getStudentId());
        assertEquals(expectedReceipt.getAmount(), actualReceipt.getAmount());
        assertEquals(expectedReceipt.getReceiptDate(), actualReceipt.getReceiptDate());
        verify(receiptRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void testGetReceiptByOrderId_NotFound() {
        // Arrange
        String orderId = "REC12345";

        // Mock the behavior of receiptRepository to return null
        when(receiptRepository.findByOrderId(orderId)).thenReturn(null);

        // Act
        Receipt actualReceipt = receiptService.getReceiptByOrderId(orderId);

        // Assert
        assertNull(actualReceipt);
        verify(receiptRepository, times(1)).findByOrderId(orderId);
    }
}
