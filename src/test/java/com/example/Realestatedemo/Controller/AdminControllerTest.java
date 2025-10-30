package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.repository.FinalEstateRepository;
import com.example.Realestatedemo.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private FinalEstateRepository finalEstateRepository;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test: Dashboard Home
    @Test
    void testDashboardHome() {
        when(sellerRepository.count()).thenReturn(5L);
        when(finalEstateRepository.count()).thenReturn(3L);

        String viewName = adminController.dashboardHome(model);

        assertEquals("admin_dashboard", viewName);
        verify(model).addAttribute("totalSellers", 5L);
        verify(model).addAttribute("totalApproved", 3L);
        verify(model).addAttribute("content", "admin_dashboard_home");
    }

    // ✅ Test: Sellers Page
    @Test
    void testSellersPage() {
        List<Seller> sellers = List.of(new Seller());
        when(sellerRepository.findAll()).thenReturn(sellers);

        String viewName = adminController.sellersPage(model);

        assertEquals("admin_sellers", viewName);
        verify(model).addAttribute("sellers", sellers);
        verify(model).addAttribute("content", "admin_sellers");
    }

    // ✅ Test: Approved Page
    @Test
    void testApprovedPage() {
        List<FinalEstate> estates = List.of(new FinalEstate());
        when(finalEstateRepository.findAll()).thenReturn(estates);

        String viewName = adminController.approvedPage(model);

        assertEquals("admin_approved", viewName);
        verify(model).addAttribute("approved", estates);
        verify(model).addAttribute("content", "admin_approved");
    }

    // ✅ Test: Approve Seller
    @Test
    void testApproveSeller() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setPropertyName("Dream Villa");
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        String viewName = adminController.approveSeller(1L, model);

        assertEquals("redirect:/admin/dashboard/sellers", viewName);
        verify(finalEstateRepository).save(any(FinalEstate.class));
        verify(sellerRepository).deleteById(1L);
    }

    // ✅ Test: Approve Seller Not Found
    @Test
    void testApproveSeller_NotFound() {
        when(sellerRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> adminController.approveSeller(999L, model));

        assertEquals("Seller not found", exception.getMessage());
    }

    // ✅ Test: Delete Seller
    @Test
    void testDeleteSeller() {
        List<FinalEstate> estates = List.of(new FinalEstate());
        when(finalEstateRepository.findAll()).thenReturn(estates);

        String viewName = adminController.deleteSeller(5L, model);

        assertEquals("redirect:/admin/dashboard/sellers", viewName);
        verify(sellerRepository).deleteById(5L);
        verify(model).addAttribute("approvedList", estates);
    }

    // ✅ Test: Delete Approved
    @Test
    void testDeleteApproved() {
        String viewName = adminController.deleteApproved(7L);

        assertEquals("redirect:/admin/dashboard/approved", viewName);
        verify(finalEstateRepository).deleteById(7L);
    }
}
