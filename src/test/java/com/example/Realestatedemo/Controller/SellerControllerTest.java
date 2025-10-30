package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.exception.FileUploadException;
import com.example.Realestatedemo.exception.PropertyNotFoundException;
import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.repository.SellerRepository;
import com.example.Realestatedemo.service.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SellerControllerTest {

    @InjectMocks
    private SellerController sellerController;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private Model model;

    private Seller seller;
    private MockMultipartFile mockFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        seller = new Seller();
        seller.setId(1L);
        seller.setPropertyName("Test Property");

        mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "dummy".getBytes());
    }

    // -------------------- Dashboard --------------------
    @Test
    void testDashboard() {
        List<Seller> sellers = Arrays.asList(seller);
        when(sellerRepository.findAll()).thenReturn(sellers);

        String view = sellerController.dashboard(model);

        assertEquals("seller_dashboard", view);
        verify(model, times(1)).addAttribute("properties", sellers);
    }

    // -------------------- Show Upload Form --------------------
    @Test
    void testShowUploadForm() {
        String view = sellerController.showUploadForm(model);
        assertEquals("seller_form", view);
        verify(model, times(1)).addAttribute(eq("seller"), any(Seller.class));
    }

    // -------------------- Save Property Success --------------------
    @Test
    void testSavePropertySuccess() throws Exception {
        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put("secure_url", "https://cloudinary.com/test.jpg");

        when(cloudinaryService.uploadFile(mockFile)).thenReturn(uploadResult);

        String view = sellerController.saveProperty(seller, mockFile, model);

        assertEquals("redirect:/seller/dashboard", view);
        assertEquals("https://cloudinary.com/test.jpg", seller.getImageUrl());
        verify(sellerRepository, times(1)).save(seller);
    }

    // -------------------- Save Property with Empty File --------------------
    @Test
    void testSavePropertyEmptyFile() {
        MockMultipartFile emptyFile = new MockMultipartFile("image", new byte[0]);

        String view = sellerController.saveProperty(seller, emptyFile, model);

        assertEquals("seller_form", view);
        verify(model, times(1)).addAttribute(eq("errorMessage"), eq("Please select an image to upload."));
        verifyNoInteractions(sellerRepository);
    }

    // -------------------- Save Property Exception --------------------
    @Test
    void testSavePropertyException() throws Exception {
        when(cloudinaryService.uploadFile(mockFile)).thenThrow(new RuntimeException("Cloudinary down"));

        FileUploadException exception = assertThrows(FileUploadException.class, () -> {
            sellerController.saveProperty(seller, mockFile, model);
        });

        assertTrue(exception.getMessage().contains("Error saving property"));
    }

    // -------------------- Delete Property Success --------------------
    @Test
    void testDeletePropertySuccess() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        String view = sellerController.deleteProperty(1L);

        assertEquals("redirect:/seller/dashboard", view);
        verify(sellerRepository, times(1)).delete(seller);
    }

    // -------------------- Delete Property Not Found --------------------
    @Test
    void testDeletePropertyNotFound() {
        when(sellerRepository.findById(2L)).thenReturn(Optional.empty());

        PropertyNotFoundException exception = assertThrows(PropertyNotFoundException.class, () -> {
            sellerController.deleteProperty(2L);
        });

        assertTrue(exception.getMessage().contains("Property with ID 2 not found."));
    }
}
