package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.model.FinalEstate;
import com.example.Realestatedemo.repository.FinalEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BuyerController {

    @Autowired
    private FinalEstateRepository finalEstateRepository;

    // Show all properties
    @GetMapping("/buyer/home")
    public String getAllProperties(Model model) {
        List<FinalEstate> properties = finalEstateRepository.findAll();
        model.addAttribute("properties", properties);
        return "buyer";
    }

    // Show property details
    @GetMapping("/buyer/view/{id}")
    public String viewProperty(@PathVariable Long id, Model model) {
        FinalEstate property = finalEstateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found: " + id));
        model.addAttribute("property", property);
        return "buyer_view";
    }

    // Search properties
   @GetMapping("/buyer/search")
public String searchProperties(
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Double minRate,
        @RequestParam(required = false) Double maxRate,
        Model model) {

    List<FinalEstate> properties = finalEstateRepository.searchProperties(
            (city == null || city.trim().isEmpty()) ? null : city,
            (type == null || type.trim().isEmpty()) ? null : type,
            (category == null || category.trim().isEmpty()) ? null : category,
            minRate,
            maxRate
    );

    model.addAttribute("properties", properties);
    return "buyer";
}
}