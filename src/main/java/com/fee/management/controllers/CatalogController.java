package com.fee.management.controllers;

import com.fee.management.models.CatalogItem;
import com.fee.management.services.CatalogService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }
    @Operation(
            summary = "Fetch catalog items",
            description = "Fetch all courseNames and fee details from Catalog",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Catalog items found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Catalog items not found")
            }
    )
    @GetMapping
    public ResponseEntity<List<CatalogItem>> getAllCatalogItems() {
        return ResponseEntity.ok(catalogService.getAllCatalogItems());
    }
}
