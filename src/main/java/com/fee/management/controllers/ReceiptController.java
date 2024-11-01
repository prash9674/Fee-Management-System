package com.fee.management.controllers;

import com.fee.management.models.Receipt;
import com.fee.management.services.ReceiptService;
import com.fee.management.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService, UserService userService) {
        this.receiptService = receiptService;
    }

    @Operation(
            summary = "Fetch receipt details",
            description = "Fetch receipt details by providing the order ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Receipt found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Receipt not found")
            }
    )
    @GetMapping("/{orderId}")
    public ResponseEntity<Receipt> getReceiptByOrderId(@PathVariable String orderId) {
        Optional<Receipt> receipt = receiptService.getReceiptByOrderId(orderId);
        return receipt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
