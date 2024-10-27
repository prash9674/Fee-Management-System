package com.fee.management.services;

import com.fee.management.models.Receipt;
import com.fee.management.repositories.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }
    public Optional<Receipt> getReceiptByOrderId(String orderId) {
        return receiptRepository.findByOrderId(orderId);
    }
}
