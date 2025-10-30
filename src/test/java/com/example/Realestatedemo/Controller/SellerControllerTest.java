package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.repository.SellerRepository;
import com.example.Realestatedemo.service.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SellerControllerTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private Model model;

    @InjectMocks
    private SellerController sellerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test: showUploadForm()
    @Test
    void testShowUploadForm() {
        String viewName = sellerController.showUploadForm(model);

        assertEquals("seller_form", viewName);
        verify(model).addAttribute(eq("seller"), any(Seller.class));
    }

    // ✅ Test: saveProperty() — with image upload success
    @Test
    void testSaveProperty_WithImage() throws Exception {
        Seller seller = new Seller();
        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "house.jpg",
                "image/jpeg",
                "fake-image".getBytes()
        );

        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("secure_url", "https://cloudinary.com/image/house.jpg");

        when(cloudinaryService.uploadFile(mockFile)).thenReturn(uploadResult);

        String viewName = sellerController.saveProperty(seller, mockFile, model);

        assertEquals("success", viewName);
        assertEquals("https://cloudinary.com/image/house.jpg", seller.getImageUrl());
        verify(sellerRepository).save(seller);
        verify(cloudinaryService).uploadFile(mockFile);
    }

    // ✅ Test: saveProperty() — with empty file
    @Test
    void testSaveProperty_WithEmptyFile() throws Exception {
        Seller seller = new Seller();
        MockMultipartFile emptyFile = new MockMultipartFile("image", new byte[0]); // empty file

        String viewName = sellerController.saveProperty(seller, emptyFile, model);

        assertEquals("success", viewName);
        verify(sellerRepository).save(seller);
        verify(cloudinaryService, never()).uploadFile(any());
    }

    // ✅ Test: saveProperty() — with exception
    @Test
    void testSaveProperty_WithException() throws Exception {
        Seller seller = new Seller();
        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "house.jpg",
                "image/jpeg",
                "fake-image".getBytes()
        );

        when(cloudinaryService.uploadFile(mockFile)).thenThrow(new RuntimeException("Cloudinary error"));

        String viewName = sellerController.saveProperty(seller, mockFile, model);

        assertEquals("error", viewName);
        verify(model).addAttribute(eq("errorMessage"), contains("Error saving property"));
    }
}
