package com.fee.management.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fee.management.models.Catalog;

@Service
public class CatalogService {//Mock Service to create catalogs
    public List<Catalog> getCatalog() {
        // Mocking catalog data
        Catalog plan1 = new Catalog("1", "Spring 2024 Plan", 500.00, "Spring 2024");
        Catalog plan2 = new Catalog("2", "Fall 2024 Plan", 600.00, "Fall 2024");
        Catalog plan3 = new Catalog("3", "Summer 2024 Plan", 450.00, "Summer 2024");

        return Arrays.asList(plan1, plan2, plan3);
    }

}
