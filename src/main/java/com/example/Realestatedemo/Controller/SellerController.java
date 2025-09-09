package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.repository.SellerRepository;
import com.example.Realestatedemo.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Show form
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("seller", new Seller());
        return "seller_form"; // will look for seller_form.html
    }

    // Save property
    @PostMapping("/save")
    public String saveProperty(@ModelAttribute Seller seller,
                               @RequestParam("image") MultipartFile file,
                               Model model) {
        try {
            if (!file.isEmpty()) {
                Map uploadResult = cloudinaryService.uploadFile(file);
                String imageUrl = (String) uploadResult.get("secure_url");
                seller.setImageUrl(imageUrl);
            }

            sellerRepository.save(seller);

            return "success"; // back to form after saving
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error saving property: " + e.getMessage());
            return "error"; // error.html page
        }


    }
}
