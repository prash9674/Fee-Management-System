package com.fee.management.services;

import com.fee.management.models.CatalogItem;
import com.fee.management.repositories.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<CatalogItem> getAllCatalogItems() {
        return catalogRepository.findAll();
    }
}
