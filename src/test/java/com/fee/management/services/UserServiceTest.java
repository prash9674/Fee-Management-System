package com.fee.management.services;

import com.fee.management.models.FeeDetails;
import com.fee.management.models.Payment;
import com.fee.management.models.User;
import com.fee.management.repositories.UserRepository;
import com.fee.management.services.PaymentService;
import com.fee.management.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById_UserExists() {
        // Arrange: Prepare a user
        User user = new User("1", "Test User","test@gmail.com","CourseA",1234567890L);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act: Call the method under test
        Optional<User> foundUser = userService.getUserById("1");

        // Assert: Verify the result
        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getName());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        // Arrange: Mock userRepository to return an empty Optional
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act: Call the method under test
        Optional<User> foundUser = userService.getUserById("1");

        // Assert: Verify the result
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    public void testCreateUser() {
        // Arrange: Prepare a user to save
        User user = new User("1", "Test User","test@gmail.com","CourseA",1234567890L);
        when(userRepository.save(user)).thenReturn(user);

        // Act: Call the method under test
        User createdUser = userService.createUser(user);

        // Assert: Verify the result
        assertNotNull(createdUser);
        assertEquals("Test User", createdUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetFeeDetailsByUserId_UserExistsWithPayments() {
        // Arrange: Prepare a user and payment details
        User user = new User("1", "Test User","test@gmail.com","CourseA",1234567890L);
        Payment payment = new Payment("1", "REC-1730033114686", "abc", 50.0, 100,"PARTIAL", LocalDateTime.now());
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(paymentService.getPaymentsByStudentId("1")).thenReturn(Arrays.asList(payment));

        // Act: Call the method under test
        Optional<FeeDetails> feeDetails = userService.getFeeDetailsByUserId("1");

        // Assert: Verify the result
        assertTrue(feeDetails.isPresent());
        assertEquals(100.0, feeDetails.get().getTotalFee());
        assertEquals(50.0, feeDetails.get().getAmountPaid());
        assertEquals(50.0, feeDetails.get().getRemainingBalance());
        assertEquals("PARTIAL", feeDetails.get().getStatus());
        verify(userRepository, times(1)).findById("1");
        verify(paymentService, times(1)).getPaymentsByStudentId("1");
    }

    @Test
    public void testGetFeeDetailsByUserId_UserExistsWithNoPayments() {
        // Arrange: Prepare a user with no payments
        User user = new User("1", "Test User","test@gmail.com","CourseA",1234567890L);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(paymentService.getPaymentsByStudentId("1")).thenReturn(Arrays.asList());

        // Act: Call the method under test
        Optional<FeeDetails> feeDetails = userService.getFeeDetailsByUserId("1");

        // Assert: Verify the result
        assertTrue(feeDetails.isPresent());
        assertEquals(0, feeDetails.get().getTotalFee());
        assertEquals(0, feeDetails.get().getAmountPaid());
        assertEquals(0, feeDetails.get().getRemainingBalance());
        assertEquals("UNPAID", feeDetails.get().getStatus());
        verify(userRepository, times(1)).findById("1");
        verify(paymentService, times(1)).getPaymentsByStudentId("1");
    }

    @Test
    public void testGetFeeDetailsByUserId_UserDoesNotExist() {
        // Arrange: Mock userRepository to return an empty Optional
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act: Call the method under test
        Optional<FeeDetails> feeDetails = userService.getFeeDetailsByUserId("1");

        // Assert: Verify the result
        assertFalse(feeDetails.isPresent());
        verify(userRepository, times(1)).findById("1");
        verify(paymentService, never()).getPaymentsByStudentId("1"); // Should not call paymentService if user doesn't exist
    }
}
