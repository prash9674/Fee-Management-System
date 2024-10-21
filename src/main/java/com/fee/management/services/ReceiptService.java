package com.fee.management.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fee.management.models.FeeDetails;
import com.fee.management.models.Receipt;
import com.fee.management.repositories.ReceiptRepository;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;

    public Receipt createReceipt(FeeDetails payment) {
        Receipt receipt = new Receipt(payment.getReceiptId(), payment.getStudentId(), payment.getAmount(),LocalDate.now().toString());
        return receiptRepository.save(receipt);
    }

    public Receipt getReceiptByOrderId(String orderId) {
        return receiptRepository.findByOrderId(orderId);
    }

}
