package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.exception.PropertyNotFoundException;
import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.repository.FinalEstateRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuyerControllerTest {

    @InjectMocks
    private BuyerController buyerController;

    @Mock
    private FinalEstateRepository finalEstateRepository;

    @Mock
    private Model model;

    private FinalEstate property1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        property1 = new FinalEstate();
        property1.setId(1L);
        property1.setPropertyName("Test Property");
        property1.setCity("Test City");
    }

    // -------------------- Get All Properties --------------------
    @Test
    void testGetAllProperties() {
        List<FinalEstate> properties = Arrays.asList(property1);
        when(finalEstateRepository.findAll()).thenReturn(properties);

        String view = buyerController.getAllProperties(model);

        assertEquals("buyer", view);
        verify(model, times(1)).addAttribute("properties", properties);
    }

    // -------------------- View Property --------------------
    @Test
    void testViewPropertySuccess() {
        when(finalEstateRepository.findById(1L)).thenReturn(Optional.of(property1));

        String view = buyerController.viewProperty(1L, model);

        assertEquals("buyer_view", view);
        verify(model, times(1)).addAttribute("property", property1);
    }

    @Test
    void testViewPropertyNotFound() {
        when(finalEstateRepository.findById(2L)).thenReturn(Optional.empty());

        PropertyNotFoundException thrown = assertThrows(PropertyNotFoundException.class, () -> {
            buyerController.viewProperty(2L, model);
        });

        assertTrue(thrown.getMessage().contains("Property not found with ID: 2"));
    }

    // -------------------- Search Properties --------------------
    @Test
    void testSearchPropertiesSuccess() {
        List<FinalEstate> properties = Arrays.asList(property1);
        when(finalEstateRepository.searchProperties(
                eq("Test City"),
                eq("Rent"),
                eq("Apartment"),
                eq(500.0),
                eq(1000.0)
        )).thenReturn(properties);

        String view = buyerController.searchProperties("Test City", "Rent", "Apartment", 500.0, 1000.0, model);

        assertEquals("buyer", view);
        verify(model, times(1)).addAttribute("properties", properties);
    }

    @Test
    void testSearchPropertiesNoResults() {
        when(finalEstateRepository.searchProperties(
                anyString(),
                anyString(),
                anyString(),
                any(),
                any()
        )).thenReturn(Collections.emptyList());

        PropertyNotFoundException thrown = assertThrows(PropertyNotFoundException.class, () -> {
            buyerController.searchProperties("City", "Sale", "House", 100.0, 200.0, model);
        });

        assertTrue(thrown.getMessage().contains("No properties found matching your criteria."));
    }
}
