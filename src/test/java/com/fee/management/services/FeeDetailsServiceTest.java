package com.fee.management.services;

import com.fee.management.models.FeeDetails;
import com.fee.management.models.User;
import com.fee.management.repositories.FeeDetailsRepository;
import com.fee.management.services.FeeDetailsService;
import com.fee.management.services.ReceiptService;
import com.fee.management.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeeDetailsServiceTest {

    @Mock
    private FeeDetailsRepository feeDetailsRepository;

    @Mock
    private UserService userService;

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private FeeDetailsService feeDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testCollectFee_SuccessfulPayment() {
        // Arrange
        FeeDetails paymentData = new FeeDetails();
        paymentData.setStudentId("12345");
        paymentData.setAmount(1000.0);

        User mockStudent = new User("12345", "Prashant Kumar", "student", "prashant@example.com", "9765432145", true);

        // Mock userService to return a valid student
        when(userService.getUserDetails("12345")).thenReturn(mockStudent);
        when(feeDetailsRepository.save(any(FeeDetails.class))).thenReturn(paymentData);
        // For non-void methods, we can just use 'when()' instead of 'doNothing()'
        when(receiptService.createReceipt(any(FeeDetails.class))).thenReturn(null);  // Adjust this to the method signature if needed

        // Act
        FeeDetails result = feeDetailsService.collectFee(paymentData);

        // Assert
        assertNotNull(result);
        assertEquals("PAID", result.getStatus());
        verify(userService, times(1)).getUserDetails("12345");
        verify(feeDetailsRepository, times(2)).save(paymentData);
        verify(receiptService, times(1)).createReceipt(paymentData);
    }

    @Test
    void testCollectFee_InvalidStudentRole() {
        // Arrange
        FeeDetails paymentData = new FeeDetails();
        paymentData.setStudentId("admin");

        User mockStudent = new User("admin", "Administrator", "admin", "admin@example.com", "8765432156", true);

        // Mock userService to return an admin user (not a student)
        when(userService.getUserDetails("admin")).thenReturn(mockStudent);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            feeDetailsService.collectFee(paymentData);
        });

        assertEquals("Invalid student ID", exception.getMessage());
        verify(userService, times(1)).getUserDetails("admin");
        verify(feeDetailsRepository, never()).save(any());
        verify(receiptService, never()).createReceipt(any());
    }

    @Test
    void testCollectFee_SaveFails() {
        // Arrange
        FeeDetails paymentData = new FeeDetails();
        paymentData.setStudentId("12345");
        paymentData.setAmount(1000.0);

        User mockStudent = new User("12345", "Prashant Kumar", "student", "prashant@example.com", "9765432145", true);

        // Mock userService to return a valid student
        when(userService.getUserDetails("12345")).thenReturn(mockStudent);
        when(feeDetailsRepository.save(any(FeeDetails.class))).thenThrow(new RuntimeException("Save failed"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            feeDetailsService.collectFee(paymentData);
        });

        assertEquals("Save failed", exception.getMessage());
        verify(userService, times(1)).getUserDetails("12345");
        verify(feeDetailsRepository, times(1)).save(any());
        verify(receiptService, never()).createReceipt(any());
    }

    @Test
    void testCollectFee_ReceiptServiceFailure() {
        // Arrange
        FeeDetails paymentData = new FeeDetails();
        paymentData.setStudentId("12345");
        paymentData.setAmount(1000.0);

        User mockStudent = new User("12345", "Prashant Kumar", "student", "prashant@example.com", "9765432145", true);

        // Mock userService to return a valid student
        when(userService.getUserDetails("12345")).thenReturn(mockStudent);
        when(feeDetailsRepository.save(any(FeeDetails.class))).thenReturn(paymentData);
        // Mock receiptService to throw exception
        when(receiptService.createReceipt(any(FeeDetails.class))).thenThrow(new RuntimeException("Receipt creation failed"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            feeDetailsService.collectFee(paymentData);
        });

        assertEquals("Receipt creation failed", exception.getMessage());
        verify(userService, times(1)).getUserDetails("12345");
        verify(feeDetailsRepository, times(2)).save(paymentData);
        verify(receiptService, times(1)).createReceipt(paymentData);
    }
}
