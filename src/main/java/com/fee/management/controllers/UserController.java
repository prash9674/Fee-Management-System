package com.fee.management.controllers;

import com.fee.management.models.Payment;
import com.fee.management.models.User;
import com.fee.management.services.PaymentService;
import com.fee.management.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final PaymentService paymentService;

    public UserController(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Fetch user details",
            description = "Fetch user details  by userId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "user details found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "user details not found")
            }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Fetch fee details",
            description = "Fetch fee details for user by userId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "user fee details found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "user fee details not found")
            }
    )
    @GetMapping("/{userId}/fee-details")
    public ResponseEntity<List<Payment>> getFeeDetailsByUserId(@PathVariable String userId) {
        List<Payment> payments = paymentService.getPaymentsByStudentId(userId);
        return ResponseEntity.ok(payments);
    }

    @Operation(
            summary = "Register user",
            description = "Register user by userId , name , email",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User Successfully Registered", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getUserId() == null) {
            // Generate userId based on email or other criteria if necessary
            user.setUserId(user.getEmail().split("@")[0]);
        }
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
