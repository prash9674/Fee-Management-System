package com.fee.management.repositories;


import com.fee.management.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // MongoRepository provides built-in methods for saving, deleting, and finding by ID.
    // Additional query methods can be defined here if needed in the future.
}
