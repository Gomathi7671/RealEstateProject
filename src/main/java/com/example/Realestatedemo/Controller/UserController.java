package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.exception.UserAlreadyExistsException;
import com.example.Realestatedemo.exception.UserNotFoundException;
import com.example.Realestatedemo.model.Users_Realestate;
import com.example.Realestatedemo.model.Role;
import com.example.Realestatedemo.repository.Realestaterepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller

public class UserController {

    @Autowired
    private Realestaterepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------------- LOGIN ----------------------
   
    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }

   
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        Users_Realestate user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Invalid password");
            return "login";
        }

        // Redirect based on role
        if (user.getRole() == Role.SELLER) {
            return "redirect:/seller/dashboard";
        } else if (user.getRole() == Role.CUSTOMER) {
            return "redirect:/buyer/home";
        } else if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Invalid role assigned");
        return "login";
    }

    // ---------------------- REGISTER ----------------------

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new Users_Realestate());
        return "register";
    }

  
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") Users_Realestate user,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists!");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        }

        userRepository.save(user);
        model.addAttribute("success", "Registration successful! Please login.");
        return "login";
    }

    // ---------------------- LOGOUT ----------------------
 
    @GetMapping("/logout-success")
    public String logoutPage(Model model) {
        model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    // ---------------------- HOME ----------------------
 
    @GetMapping("/")
    public String home() {
        return "index"; 
    }

    // ---------------------- Exception Handlers ----------------------
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "login";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(UserAlreadyExistsException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "register";
    }
}
