package com.fee.management.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fee.management.models.FeeDetails;

public interface FeeDetailsRepository extends MongoRepository<FeeDetails, String>{

}
