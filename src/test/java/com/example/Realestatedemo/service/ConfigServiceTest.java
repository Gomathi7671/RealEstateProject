package com.example.Realestatedemo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CloudinaryServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private CloudinaryService cloudinaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test file upload
    @Test
    void testUploadFile() throws IOException {
        byte[] fakeData = "image-bytes".getBytes();
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("url", "http://cloudinary.com/test.jpg");

        when(file.getBytes()).thenReturn(fakeData);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(eq(fakeData), any(Map.class))).thenReturn(expectedResponse);

        Map result = cloudinaryService.uploadFile(file);

        assertEquals("http://cloudinary.com/test.jpg", result.get("url"));
        verify(uploader).upload(eq(fakeData), argThat(map -> "auto".equals(map.get("resource_type"))));
    }

    // ✅ Test file deletion
    @Test
    void testDeleteFile() throws IOException {
        String publicId = "sample123";
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("result", "ok");

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.destroy(eq(publicId), any(Map.class))).thenReturn(expectedResponse);

        Map result = cloudinaryService.deleteFile(publicId);

        assertEquals("ok", result.get("result"));
        verify(uploader).destroy(eq(publicId), eq(ObjectUtils.emptyMap()));
    }
}
