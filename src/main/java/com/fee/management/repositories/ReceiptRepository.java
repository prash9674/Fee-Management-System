package com.fee.management.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fee.management.models.Receipt;

public interface ReceiptRepository extends MongoRepository<Receipt, String> {

    Receipt findByOrderId(String orderId);
}
