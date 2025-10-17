//  package com.example.Realestatedemo.Controller;

// import com.example.Realestatedemo.model.Seller;
// import com.example.Realestatedemo.repository.SellerRepository;
// import com.example.Realestatedemo.service.CloudinaryService;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.test.web.servlet.MockMvc;

// import java.util.HashMap;
// import java.util.Map;

// import static org.mockito.ArgumentMatchers.any;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(SellerController.class)
// @AutoConfigureMockMvc(addFilters = false) // disable Spring Security
// class SellerControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private SellerRepository sellerRepository;

//     @MockBean
//     private CloudinaryService cloudinaryService;

//     // 🔹 Test GET /seller/upload
//     @Test
//     void testShowUploadForm() throws Exception {
//         mockMvc.perform(get("/seller/upload"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("seller_form"))
//                 .andExpect(model().attributeExists("seller"));
//     }

//     // 🔹 Test POST /seller/save with file
//     @Test
//     void testSavePropertyWithFile() throws Exception {
//         MockMultipartFile mockFile =
//                 new MockMultipartFile("image", "test.jpg", "image/jpeg", "fake-image-data".getBytes());

//         Map<String, Object> uploadResult = new HashMap<>();
//         uploadResult.put("secure_url", "http://cloudinary.com/test.jpg");

//         Mockito.when(cloudinaryService.uploadFile(any())).thenReturn(uploadResult);

//         mockMvc.perform(multipart("/seller/save")
//                         .file(mockFile)
//                         .param("name", "Test Seller")
//                         .param("place", "Chennai"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("success"));

//         Mockito.verify(cloudinaryService).uploadFile(any());
//         Mockito.verify(sellerRepository).save(any(Seller.class));
//     }

//     // 🔹 Test POST /seller/save without file
//     @Test
//     void testSavePropertyWithoutFile() throws Exception {
//         MockMultipartFile emptyFile =
//                 new MockMultipartFile("image", "", "image/jpeg", new byte[0]);

//         mockMvc.perform(multipart("/seller/save")
//                         .file(emptyFile)
//                         .param("name", "Test Seller")
//                         .param("place", "Mumbai"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("success"));

//         Mockito.verify(sellerRepository).save(any(Seller.class));
//         Mockito.verify(cloudinaryService, Mockito.never()).uploadFile(any());
//     }
// }
 