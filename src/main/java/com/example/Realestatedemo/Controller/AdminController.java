package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.exception.ResourceNotFoundException;
import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.repository.SellerRepository;
import com.example.Realestatedemo.repository.FinalEstateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private FinalEstateRepository finalEstateRepository;

    // Dashboard Home
    @GetMapping("/dashboard")
    public String dashboardHome(Model model) {
        model.addAttribute("totalSellers", sellerRepository.count());
        model.addAttribute("totalApproved", finalEstateRepository.count());
        return "admin_dashboard";
    }

    // Show Sellers
    @GetMapping("/dashboard/sellers")
    public String sellersPage(Model model) {
        List<Seller> sellers = sellerRepository.findAll();
        model.addAttribute("sellers", sellers);
        return "admin_sellers";
    }

    // Show Approved Properties
    @GetMapping("/dashboard/approved")
    public String approvedPage(Model model) {
        List<FinalEstate> approved = finalEstateRepository.findAll();
        model.addAttribute("approved", approved);
        return "admin_approved";
    }

    // Approve Seller → move to FinalEstate
    @GetMapping("/approve/{id}")
    public String approveSeller(@PathVariable Long id, Model model) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + id));

        FinalEstate finalEstate = new FinalEstate();
        finalEstate.setPropertyName(seller.getPropertyName());
        finalEstate.setPropertyType(seller.getPropertyType());
        finalEstate.setSquareFeet(seller.getSquareFeet());
        finalEstate.setRate(seller.getRate());
        finalEstate.setPlace(seller.getPlace());
        finalEstate.setCity(seller.getCity());
        finalEstate.setState(seller.getState());
        finalEstate.setPostalCode(seller.getPostalCode());
        finalEstate.setPropertyCategory(seller.getPropertyCategory());
        finalEstate.setAmenities(seller.getAmenities());
        finalEstate.setFurnished(seller.getFurnished());
        finalEstate.setBuiltYear(seller.getBuiltYear());
        finalEstate.setDescription(seller.getDescription());
        finalEstate.setLandOwner(seller.getLandOwner());
        finalEstate.setContact(seller.getContact());
        finalEstate.setImageUrl(seller.getImageUrl());

        finalEstateRepository.save(finalEstate);
        sellerRepository.deleteById(id);

        model.addAttribute("approvedList", finalEstateRepository.findAll());
        return "redirect:/admin/dashboard/sellers";
    }

    // Delete Seller
    @GetMapping("/delete/{id}")
    public String deleteSeller(@PathVariable Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Seller not found with ID: " + id);
        }
        sellerRepository.deleteById(id);
        return "redirect:/admin/dashboard/sellers";
    }

    // Delete Approved Property
    @GetMapping("/delete-approved/{id}")
    public String deleteApproved(@PathVariable Long id) {
        if (!finalEstateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Approved property not found with ID: " + id);
        }
        finalEstateRepository.deleteById(id);
        return "redirect:/admin/dashboard/approved";
    }
}
