package com.fee.management.services;

import com.fee.management.models.FeeDetails;
import com.fee.management.models.Payment;
import com.fee.management.models.User;

import com.fee.management.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public UserService(UserRepository userRepository, PaymentService paymentService) {
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<FeeDetails> getFeeDetailsByUserId(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        List<Payment> payments = paymentService.getPaymentsByStudentId(userId);

        if (payments.isEmpty()) {
            return Optional.of(new FeeDetails(0, 0, "UNPAID"));
        }

        Payment payment = payments.get(0);
        double totalFee = payment.getTotalFee();
        double amountPaid = payment.getAmountPaid();
        String status = payment.getStatus();

        return Optional.of(new FeeDetails(totalFee, amountPaid, status));
    }
}
