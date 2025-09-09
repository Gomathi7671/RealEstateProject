package com.example.Realestatedemo.Controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.repository.FinalEstateRepository;
import com.example.Realestatedemo.repository.SellerRepository;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false) // disable Spring Security
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerRepository sellerRepository;

    @MockBean
    private FinalEstateRepository finalEstateRepository;

    // Dashboard Home
    @Test
    void testDashboardHome() throws Exception {
        when(sellerRepository.count()).thenReturn(5L);
        when(finalEstateRepository.count()).thenReturn(3L);

        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_dashboard"))
                .andExpect(model().attribute("totalSellers", 5L))
                .andExpect(model().attribute("totalApproved", 3L))
                .andExpect(model().attribute("content", "admin_dashboard_home"));
    }

    //  Sellers Page
    @Test
    void testSellersPage() throws Exception {
        when(sellerRepository.findAll()).thenReturn(Arrays.asList(new Seller()));

        mockMvc.perform(get("/admin/dashboard/sellers"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_sellers"))
                .andExpect(model().attributeExists("sellers"))
                .andExpect(model().attribute("content", "admin_sellers"));
    }

    //  Approved Page
    @Test
    void testApprovedPage() throws Exception {
        when(finalEstateRepository.findAll()).thenReturn(Arrays.asList(new FinalEstate()));

        mockMvc.perform(get("/admin/dashboard/approved"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_approved"))
                .andExpect(model().attributeExists("approved"))
                .andExpect(model().attribute("content", "admin_approved"));
    }

    //  Approve Seller
    @Test
    void testApproveSeller() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setPropertyName("Test Property");
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        mockMvc.perform(get("/admin/approve/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard/sellers"));

        verify(finalEstateRepository).save(any(FinalEstate.class));
        verify(sellerRepository).deleteById(1L);
    }

    //  Delete Seller
    @Test
    void testDeleteSeller() throws Exception {
        mockMvc.perform(get("/admin/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard/sellers"));

        verify(sellerRepository).deleteById(2L);
    }

    //  Delete Approved
    @Test
    void testDeleteApproved() throws Exception {
        mockMvc.perform(get("/admin/delete-approved/3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard/approved"));

        verify(finalEstateRepository).deleteById(3L);
    }
}
