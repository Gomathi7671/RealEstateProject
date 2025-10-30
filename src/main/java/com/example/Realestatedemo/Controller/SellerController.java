package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.exception.FileUploadException;
import com.example.Realestatedemo.exception.PropertyNotFoundException;
import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.repository.SellerRepository;
import com.example.Realestatedemo.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    // ----------------- Seller Dashboard -----------------
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("properties", sellerRepository.findAll());
        return "seller_dashboard";
    }

    // ----------------- Show Upload Form -----------------
    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("seller", new Seller());
        return "seller_form";
    }

    // ----------------- Save Property -----------------
    @PostMapping("/save")
    public String saveProperty(@ModelAttribute Seller seller,
                               @RequestParam("image") MultipartFile file,
                               Model model) {

        try {
            if (file.isEmpty()) {
                throw new FileUploadException("Please select an image to upload.");
            }

            Map uploadResult = cloudinaryService.uploadFile(file);
            seller.setImageUrl((String) uploadResult.get("secure_url"));

            sellerRepository.save(seller);

            return "redirect:/seller/dashboard";

        } catch (FileUploadException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "seller_form";
        } catch (Exception e) {
            throw new FileUploadException("Error saving property: " + e.getMessage());
        }
    }

    // ----------------- Delete Property -----------------
    @GetMapping("/delete/{id}")
    public String deleteProperty(@PathVariable Long id) {
        Seller property = sellerRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Property with ID " + id + " not found."));
        sellerRepository.delete(property);
        return "redirect:/seller/dashboard";
    }
}
