package com.fee.management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fee.management.models.Receipt;
import com.fee.management.services.ReceiptService;


@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @Operation(
            summary = "Fetch receipt details",
            description = "Fetch receipt details by providing the order ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Receipt found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Receipt not found")
            }
    )
    @GetMapping("/{orderId}")
    public ResponseEntity<Receipt> fetchReceiptDetails(@PathVariable String orderId) {
        Receipt receipt = receiptService.getReceiptByOrderId(orderId);
        if (receipt == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receipt);
    }
}
