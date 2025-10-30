package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.repository.FinalEstateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuyerControllerTest {

    @Mock
    private FinalEstateRepository finalEstateRepository;

    @Mock
    private Model model;

    @InjectMocks
    private BuyerController buyerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test: getAllProperties()
    @Test
    void testGetAllProperties() {
        List<FinalEstate> mockList = List.of(new FinalEstate(), new FinalEstate());
        when(finalEstateRepository.findAll()).thenReturn(mockList);

        String viewName = buyerController.getAllProperties(model);

        assertEquals("buyer", viewName);
        verify(finalEstateRepository, times(1)).findAll();
        verify(model).addAttribute("properties", mockList);
    }

    // ✅ Test: viewProperty()
    @Test
    void testViewProperty() {
        FinalEstate mockEstate = new FinalEstate();
        mockEstate.setId(1L);
        mockEstate.setPropertyName("Dream Villa");

        when(finalEstateRepository.findById(1L)).thenReturn(Optional.of(mockEstate));

        String viewName = buyerController.viewProperty(1L, model);

        assertEquals("buyer_view", viewName);
        verify(model).addAttribute("property", mockEstate);
    }

    // ✅ Test: viewProperty() when property not found
    @Test
    void testViewProperty_NotFound() {
        when(finalEstateRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buyerController.viewProperty(99L, model);
        });

        assertEquals("Property not found: 99", exception.getMessage());
    }

    // ✅ Test: searchProperties() with filters
    @Test
    void testSearchProperties_WithFilters() {
        List<FinalEstate> mockList = List.of(new FinalEstate());
        when(finalEstateRepository.searchProperties("Chennai", "Apartment", "For Sale", 1000000.0, 5000000.0))
                .thenReturn(mockList);

        String viewName = buyerController.searchProperties("Chennai", "Apartment", "For Sale",
                1000000.0, 5000000.0, model);

        assertEquals("buyer", viewName);
        verify(finalEstateRepository).searchProperties("Chennai", "Apartment", "For Sale", 1000000.0, 5000000.0);
        verify(model).addAttribute("properties", mockList);
    }

    // ✅ Test: searchProperties() with null filters
    @Test
    void testSearchProperties_WithNullFilters() {
        List<FinalEstate> mockList = List.of(new FinalEstate());
        when(finalEstateRepository.searchProperties(null, null, null, null, null)).thenReturn(mockList);

        String viewName = buyerController.searchProperties("", "", "", null, null, model);

        assertEquals("buyer", viewName);
        verify(finalEstateRepository).searchProperties(null, null, null, null, null);
        verify(model).addAttribute("properties", mockList);
    }
}
