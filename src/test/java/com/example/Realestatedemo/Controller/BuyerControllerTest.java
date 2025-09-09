package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.repository.FinalEstateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuyerController.class)
@AutoConfigureMockMvc(addFilters = false) // disables Spring Security filters
class BuyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinalEstateRepository finalEstateRepository;

    // 🔹 Test getAllProperties
    @Test
    void testGetAllProperties() throws Exception {
        FinalEstate property = new FinalEstate();
        property.setId(1L);
        property.setPlace("Chennai");

        Mockito.when(finalEstateRepository.findAll())
                .thenReturn(Arrays.asList(property));

        mockMvc.perform(get("/buyer/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("buyer"))
                .andExpect(model().attributeExists("properties"))
                .andExpect(model().attribute("properties", hasSize(1)));
    }

    // 🔹 Test viewProperty
    @Test
    void testViewProperty() throws Exception {
        FinalEstate property = new FinalEstate();
        property.setId(1L);
        property.setPlace("Chennai");

        Mockito.when(finalEstateRepository.findById(1L))
                .thenReturn(Optional.of(property));

        mockMvc.perform(get("/buyer/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("buyer_view"))
                .andExpect(model().attributeExists("property"))
                .andExpect(model().attribute("property", hasProperty("place", is("Chennai"))));
    }

    // 🔹 Test searchProperties
    @Test
    void testSearchProperties() throws Exception {
        FinalEstate property = new FinalEstate();
        property.setId(2L);
        property.setPlace("Bangalore");

        Mockito.when(finalEstateRepository.searchProperties("Bangalore", null, null, null, null))
                .thenReturn(Arrays.asList(property));

        mockMvc.perform(get("/buyer/search")
                        .param("city", "Bangalore"))
                .andExpect(status().isOk())
                .andExpect(view().name("buyer"))
                .andExpect(model().attributeExists("properties"))
                .andExpect(model().attribute("properties", hasSize(1)))
                .andExpect(model().attribute("properties", 
                        org.hamcrest.Matchers.contains(hasProperty("place", is("Bangalore")))));
    }
}
                