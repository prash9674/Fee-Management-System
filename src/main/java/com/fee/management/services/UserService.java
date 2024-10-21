package com.fee.management.services;

import org.springframework.stereotype.Service;

import com.fee.management.models.User;

@Service
public class UserService { //Mock Service to create users

    public User getUserDetails(String userId) {
        // Mocking user details based on userId
        return switch (userId) {
            case "12345" -> new User("12345", "Prashant Kumar", "student", "prashant@example.com", "9765432145", true);
            case "admin" -> new User("admin", "Administrator", "admin", "admin@example.com", "8765432156", true);
            default -> null;
        }; // Return null if user not found
    }
}
