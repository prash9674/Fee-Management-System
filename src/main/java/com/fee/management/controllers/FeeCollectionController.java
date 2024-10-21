package com.fee.management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fee.management.models.FeeDetails;
import com.fee.management.services.FeeDetailsService;

@RestController
@RequestMapping("/api/v1/fees")
public class FeeCollectionController {
    
    @Autowired
    private FeeDetailsService feeDetailsService;

    @Operation(summary = "Collect fee payment", description = "Collect the fee for a student and return a receipt ID")
    @ApiResponse(responseCode = "201", description = "Payment collected successfully")
    @PostMapping("/pay")
    public ResponseEntity<FeeDetails> collectFee(@RequestBody FeeDetails paymentData) {
        try {
            FeeDetails payment = feeDetailsService.collectFee(paymentData);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

}
