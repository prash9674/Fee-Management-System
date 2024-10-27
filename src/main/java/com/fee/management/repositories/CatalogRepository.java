package com.fee.management.repositories;

import com.fee.management.models.CatalogItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends MongoRepository<CatalogItem, String> {
}
