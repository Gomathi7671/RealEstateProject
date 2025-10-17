package com.example.Realestatedemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Realestatedemo.model.Seller;
import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.repository.SellerRepository;
import com.example.Realestatedemo.repository.FinalEstateRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private FinalEstateRepository finalEstateRepository;

    //  Dashboard Home (summary only)
    @GetMapping("/dashboard")
    public String dashboardHome(Model model) {
        long totalSellers = sellerRepository.count();
        long totalApproved = finalEstateRepository.count();

        model.addAttribute("totalSellers", totalSellers);
        model.addAttribute("totalApproved", totalApproved);
        model.addAttribute("content", "admin_dashboard_home"); // injects fragment

        return "admin_dashboard";
    }

    

    //  Show Sellers
    @GetMapping("/dashboard/sellers")
    public String sellersPage(Model model) {
        List<Seller> sellers = sellerRepository.findAll();
        model.addAttribute("sellers", sellers);
        model.addAttribute("content", "admin_sellers");
        return "admin_sellers";
    }

    //  Show Approved Properties
    @GetMapping("/dashboard/approved")
    public String approvedPage(Model model) {
        List<FinalEstate> approved = finalEstateRepository.findAll();
        model.addAttribute("approved", approved);
        model.addAttribute("content", "admin_approved");
        return "admin_approved";
    }

   //  Approve a Seller → move to FinalEstate
@GetMapping("/approve/{id}")
public String approveSeller(@PathVariable Long id, Model model) {
    Seller seller = sellerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Seller not found"));

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

    // save into final table
    finalEstateRepository.save(finalEstate);

    // delete from seller table
    sellerRepository.deleteById(id);

    //  fetch approved properties
    model.addAttribute("approvedList", finalEstateRepository.findAll());
  //  Redirect back to Sellers page
    return "redirect:/admin/dashboard/sellers";
}

//  Delete Seller
@GetMapping("/delete/{id}")
public String deleteSeller(@PathVariable Long id, Model model) {
    sellerRepository.deleteById(id);

    //  fetch updated approved list
    model.addAttribute("approvedList", finalEstateRepository.findAll());

     //  Redirect back to Sellers page
    return "redirect:/admin/dashboard/sellers";
}

@GetMapping("/delete-approved/{id}")
public String deleteApproved(@PathVariable Long id) {
    finalEstateRepository.deleteById(id);
    return "redirect:/admin/dashboard/approved";
}

}