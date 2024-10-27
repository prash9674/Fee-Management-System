package com.fee.management.repositories;

import com.fee.management.models.Receipt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepository extends MongoRepository<Receipt, String> {
    Optional<Receipt> findByOrderId(String orderId);
}
