package com.fee.management.controllers;

import com.fee.management.models.CatalogItem;
import com.fee.management.models.User;
import com.fee.management.services.CatalogService;
import com.fee.management.services.PaymentService;
import com.fee.management.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fee.management.models.Payment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fee/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;
    private final CatalogService catalogService;

    public PaymentController(PaymentService paymentService, UserService userService, CatalogService catalogService) {
        this.paymentService = paymentService;
        this.userService = userService;
        this.catalogService = catalogService;
    }

    @Operation(
            summary = "Collect Student Fees",
            description = "Collect Student fees by userId, courseId, and amount",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Fee Collected", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @PostMapping("/collectfees")
    public ResponseEntity<Payment> processPayment(
            @RequestParam String userId,
            @RequestParam double amount) {

        // Retrieve user and catalog item information
        Optional<User> user = userService.getUserById(userId);
        String courseName = user.get().getCourseName();
        Optional<CatalogItem> catalogItem = catalogService.getCatalogItemByCourseName(courseName);

        // Ensure user and course exist
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // User not found
        }
        if (catalogItem.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Course not found
        }

        // Get the total fee for the specified course
        double totalFee = catalogItem.get().getFee();

        // Process payment and return response
        Payment payment = paymentService.processPayment(userId, courseName, amount);
        return ResponseEntity.ok(payment);
    }

    @Operation(
            summary = "Fetch Pending Fee Balance",
            description = "Fetch the pending fee balance for a student by userId and courseId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pending fee balance found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Student or course not found")
            }
    )
    @GetMapping("/pendingfees")
    public ResponseEntity<?> getPendingFees(
            @RequestParam String userId) {

        // Validate that user and course exist
        Optional<User> user = userService.getUserById(userId);
        String courseName = user.get().getCourseName();
        Optional<CatalogItem> catalogItem = catalogService.getCatalogItemByCourseName(courseName);

        if (user.isEmpty() || catalogItem.isEmpty()) {
            return ResponseEntity.status(404).body("Student or course not found.");
        }

        // Retrieve pending fee balance from PaymentService
        double totalFee = catalogItem.get().getFee();
        double pendingBalance = paymentService.getPendingFeeBalance(userId, courseName, totalFee);

        // Prepare response data
        return ResponseEntity.ok(
                Map.of(
                        "courseName", catalogItem.get().getCourseName(),
                        "totalFee", totalFee,
                        "pendingBalance", pendingBalance
                )
        );
    }
}