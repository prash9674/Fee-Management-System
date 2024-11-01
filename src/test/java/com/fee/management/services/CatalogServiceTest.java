package com.fee.management.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fee.management.models.CatalogItem;
import com.fee.management.repositories.CatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogServiceTest {

    @Mock
    private CatalogRepository catalogRepository;

    @InjectMocks
    private CatalogService catalogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCatalogItems() {
        CatalogItem item1 = new CatalogItem("C101", "Mathematics", 1500.0);
        CatalogItem item2 = new CatalogItem("C102", "Physics", 2000.0);

        when(catalogRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        assertEquals(2, catalogService.getAllCatalogItems().size());
        verify(catalogRepository, times(1)).findAll();
    }

    @Test
    public void testGetCatalogItemByCourseName() {
        String courseName = "Mathematics";
        CatalogItem catalogItem = new CatalogItem("C101", courseName, 1500.0);

        when(catalogRepository.findByCourseName(courseName)).thenReturn(Optional.of(catalogItem));

        Optional<CatalogItem> foundItem = catalogService.getCatalogItemByCourseName(courseName);
        assertTrue(foundItem.isPresent());
        assertEquals(catalogItem, foundItem.get());
        verify(catalogRepository, times(1)).findByCourseName(courseName);
    }

    @Test
    public void testGetCatalogItemByCourseName_NotFound() {
        String courseName = "NonExistentCourse";

        when(catalogRepository.findByCourseName(courseName)).thenReturn(Optional.empty());

        Optional<CatalogItem> foundItem = catalogService.getCatalogItemByCourseName(courseName);
        assertFalse(foundItem.isPresent());
        verify(catalogRepository, times(1)).findByCourseName(courseName);
    }
}