package com.fee.management.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fee.management.models.FeeDetails;
import com.fee.management.models.User;
import com.fee.management.repositories.FeeDetailsRepository;

@Service
public class FeeDetailsService {
    @Autowired
    private FeeDetailsRepository feeDetailsRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ReceiptService receiptService;

    public FeeDetails collectFee(FeeDetails paymentData) {
        // Verify student details
        User student = userService.getUserDetails(paymentData.getStudentId());
        if (student == null || !"student".equals(student.getRole())) {
            throw new RuntimeException("Invalid student ID");
        }

        // Create Payment
        paymentData.setStatus("PENDING");
        paymentData.setReceiptId("REC" + System.currentTimeMillis());
        feeDetailsRepository.save(paymentData);

        // Simulate payment completion
        paymentData.setStatus("PAID");
        feeDetailsRepository.save(paymentData);

        // Create receipt after successful payment
        receiptService.createReceipt(paymentData);

        return paymentData;
    }

}
