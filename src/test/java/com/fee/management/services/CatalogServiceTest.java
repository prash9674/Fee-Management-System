package com.fee.management.services;

import com.fee.management.models.CatalogItem;
import com.fee.management.repositories.CatalogRepository;
import com.fee.management.services.CatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class CatalogServiceTest {
    @Mock
    private CatalogRepository catalogRepository;

    @InjectMocks
    private CatalogService catalogService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCatalogItems() {
        // Arrange: Prepare some sample catalog items
        CatalogItem item1 = new CatalogItem("1", "CourseA",1000.0);
        CatalogItem item2 = new CatalogItem("2", "CourseB",1200.0);
        List<CatalogItem> expectedItems = Arrays.asList(item1, item2);

        // Mock the behavior of catalogRepository
        when(catalogRepository.findAll()).thenReturn(expectedItems);

        // Act: Call the method under test
        List<CatalogItem> actualItems = catalogService.getAllCatalogItems();

        // Assert: Verify the result
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);

        // Verify that findAll was called exactly once
        verify(catalogRepository, times(1)).findAll();
    }
}
