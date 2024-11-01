package com.fee.management.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fee.management.models.FeeDetails;
import com.fee.management.models.Payment;
import com.fee.management.models.User;
import com.fee.management.repositories.UserRepository;
import com.fee.management.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById() {
        String userId = "U101";
        User user = new User();
        user.setUserId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setCourseName("Mathematics");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(userId);
        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getUserId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_NotFound() {
        String userId = "U101";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(userId);
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUserId("U101");
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setCourseName("Mathematics");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals(user.getUserId(), createdUser.getUserId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetFeeDetailsByUserId_UserNotFound() {
        String userId = "U101";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<FeeDetails> feeDetails = userService.getFeeDetailsByUserId(userId);
        assertFalse(feeDetails.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetFeeDetailsByUserId_NoPayments() {
        String userId = "U101";
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(paymentService.getPaymentsByStudentId(userId)).thenReturn(Collections.emptyList());

        Optional<FeeDetails> feeDetails = userService.getFeeDetailsByUserId(userId);
        assertTrue(feeDetails.isPresent());
        assertEquals(0, feeDetails.get().getTotalFee());
        assertEquals(0, feeDetails.get().getAmountPaid());
        assertEquals("UNPAID", feeDetails.get().getStatus());
        verify(userRepository, times(1)).findById(userId);
        verify(paymentService, times(1)).getPaymentsByStudentId(userId);
    }

    @Test
    public void testGetFeeDetailsByUserId_WithPayments() {
        String userId = "U101";
        User user = new User();
        user.setUserId(userId);

        Payment payment = new Payment();
        payment.setTotalFee(2000.0);
        payment.setAmountPaid(1500.0);
        payment.setStatus("PARTIAL");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(paymentService.getPaymentsByStudentId(userId)).thenReturn(Collections.singletonList(payment));

        Optional<FeeDetails> feeDetails = userService.getFeeDetailsByUserId(userId);
        assertTrue(feeDetails.isPresent());
        assertEquals(2000.0, feeDetails.get().getTotalFee());
        assertEquals(1500.0, feeDetails.get().getAmountPaid());
        assertEquals("PARTIAL", feeDetails.get().getStatus());
        verify(userRepository, times(1)).findById(userId);
        verify(paymentService, times(1)).getPaymentsByStudentId(userId);
    }
}
