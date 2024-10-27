package com.fee.management.controllers;

import com.fee.management.models.CatalogItem;
import com.fee.management.models.FeeDetails;
import com.fee.management.models.User;
import com.fee.management.services.CatalogService;
import com.fee.management.services.PaymentService;
import com.fee.management.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fee.management.models.Payment;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            description = "Collect Student fees by studentId , amount ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Fee Collected", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @PostMapping("/collectfees")
    public ResponseEntity<Payment> processPayment(
            @RequestParam String userId,
            @RequestParam double amount) {
        Optional <Double> totalfee = Optional.of(0.00);
        Optional<User> user = userService.getUserById(userId);
        List<CatalogItem> catalogItem = catalogService.getAllCatalogItems();
        if(user.isPresent()){
            if(!catalogItem.isEmpty() && catalogItem.size()>0){
                String courseName = user.get().getCourseName();
                 totalfee = catalogItem.stream()
                        .filter(item -> item.getCourseName().equalsIgnoreCase(courseName))
                        .map(CatalogItem::getFee)
                        .findFirst();
            }
        }
        Payment payment = paymentService.processPayment(userId, amount,totalfee.get());
        return ResponseEntity.ok(payment);
    }

    @Operation(
            summary = "Fetch fee payment",
            description = "Fetch fee payment by studentId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fee Payment found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Fee Payment not found")
            }
    )
    @GetMapping("/{studentId}")
    public ResponseEntity<List<Payment>> getPaymentsByStudentId(@PathVariable String studentId) {
        List<Payment> payments = paymentService.getPaymentsByStudentId(studentId);
        return ResponseEntity.ok(payments);
    }
}
