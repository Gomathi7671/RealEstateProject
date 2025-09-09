package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.model.Users_Realestate;
import com.example.Realestatedemo.model.Role;
import com.example.Realestatedemo.repository.Realestaterepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    @Autowired
    private Realestaterepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //  Show login page
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This must be your login.html template
    }

    

    //  Show register page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new Users_Realestate());
        return "register"; // register.html template
    }

    //  Handle registration
    @PostMapping("/register")
    public String register(Users_Realestate user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role (you can change this as needed)
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER); // Default role
        }

        userRepository.save(user);
        model.addAttribute("success", "Registration successful! Please login.");
        return "login";
    }

    //  Home page (optional)
    @GetMapping("/")
    public String home() {
        return "index"; // index.html
    }
}
