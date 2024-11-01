package com.fee.management.services;

import com.fee.management.models.CatalogItem;
import com.fee.management.repositories.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<CatalogItem> getAllCatalogItems() {
        return catalogRepository.findAll();
    }

    public Optional<CatalogItem> getCatalogItemByCourseName(String courseName) {
        return catalogRepository.findByCourseName(courseName);
    }
}
