package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.exception.ResourceNotFoundException;
import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.repository.SellerRepository;
import com.example.Realestatedemo.repository.FinalEstateRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private FinalEstateRepository finalEstateRepository;

    @Mock
    private Model model;

    private Seller seller1;
    private FinalEstate finalEstate1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        seller1 = new Seller();
        seller1.setId(1L);
        seller1.setPropertyName("Test Property");
        seller1.setCity("Test City");

        finalEstate1 = new FinalEstate();
        finalEstate1.setId(1L);
        finalEstate1.setPropertyName("Approved Property");
    }

    // -------------------- Dashboard Home --------------------
    @Test
    void testDashboardHome() {
        when(sellerRepository.count()).thenReturn(5L);
        when(finalEstateRepository.count()).thenReturn(10L);

        String view = adminController.dashboardHome(model);

        assertEquals("admin_dashboard", view);
        verify(model, times(1)).addAttribute("totalSellers", 5L);
        verify(model, times(1)).addAttribute("totalApproved", 10L);
    }

    // -------------------- Sellers Page --------------------
    @Test
    void testSellersPage() {
        when(sellerRepository.findAll()).thenReturn(Arrays.asList(seller1));

        String view = adminController.sellersPage(model);

        assertEquals("admin_sellers", view);
        verify(model, times(1)).addAttribute("sellers", Arrays.asList(seller1));
    }

    // -------------------- Approve Seller --------------------
    @Test
    void testApproveSellerSuccess() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller1));

        String view = adminController.approveSeller(1L, model);

        assertEquals("redirect:/admin/dashboard/sellers", view);
        verify(finalEstateRepository, times(1)).save(any(FinalEstate.class));
        verify(sellerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testApproveSellerNotFound() {
        when(sellerRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            adminController.approveSeller(2L, model);
        });

        assertTrue(thrown.getMessage().contains("Seller not found with ID: 2"));
    }

    // -------------------- Delete Seller --------------------
    @Test
    void testDeleteSellerSuccess() {
        when(sellerRepository.existsById(1L)).thenReturn(true);

        String view = adminController.deleteSeller(1L);

        assertEquals("redirect:/admin/dashboard/sellers", view);
        verify(sellerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSellerNotFound() {
        when(sellerRepository.existsById(2L)).thenReturn(false);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            adminController.deleteSeller(2L);
        });

        assertTrue(thrown.getMessage().contains("Seller not found with ID: 2"));
    }

    // -------------------- Delete Approved Property --------------------
    @Test
    void testDeleteApprovedSuccess() {
        when(finalEstateRepository.existsById(1L)).thenReturn(true);

        String view = adminController.deleteApproved(1L);

        assertEquals("redirect:/admin/dashboard/approved", view);
        verify(finalEstateRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteApprovedNotFound() {
        when(finalEstateRepository.existsById(2L)).thenReturn(false);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            adminController.deleteApproved(2L);
        });

        assertTrue(thrown.getMessage().contains("Approved property not found with ID: 2"));
    }
}
