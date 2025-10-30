package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.exception.UserAlreadyExistsException;
import com.example.Realestatedemo.exception.UserNotFoundException;
import com.example.Realestatedemo.model.Role;
import com.example.Realestatedemo.model.Users_Realestate;
import com.example.Realestatedemo.repository.Realestaterepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private Realestaterepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---------------------- LOGIN TESTS ----------------------
    @Test
    void testLoginSuccessSeller() {
        Users_Realestate user = new Users_Realestate();
        user.setEmail("seller@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.SELLER);

        when(userRepository.findByEmail("seller@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);

        String result = userController.login("seller@example.com", "rawPassword", model);
        assertEquals("redirect:/seller/dashboard", result);
    }

    @Test
    void testLoginInvalidPassword() {
        Users_Realestate user = new Users_Realestate();
        user.setEmail("user@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        String result = userController.login("user@example.com", "wrongPassword", model);
        assertEquals("login", result);
        verify(model).addAttribute("error", "Invalid password");
    }

    @Test
    void testLoginUserNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        try {
            userController.login("nonexistent@example.com", "anyPassword", model);
        } catch (UserNotFoundException ex) {
            assertEquals("Email not found", ex.getMessage());
        }
    }

    // ---------------------- REGISTER TESTS ----------------------
    @Test
    void testRegisterSuccess() {
        Users_Realestate user = new Users_Realestate();
        user.setEmail("newuser@example.com");
        user.setPassword("password");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        String result = userController.register(user, bindingResult, model);

        verify(userRepository).save(user);
        assertEquals(Role.CUSTOMER, user.getRole());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals("login", result);
        verify(model).addAttribute("success", "Registration successful! Please login.");
    }

    @Test
    void testRegisterEmailAlreadyExists() {
        Users_Realestate user = new Users_Realestate();
        user.setEmail("existing@example.com");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));

        try {
            userController.register(user, bindingResult, model);
        } catch (UserAlreadyExistsException ex) {
            assertEquals("Email already exists!", ex.getMessage());
        }
    }

    @Test
    void testRegisterWithValidationErrors() {
        Users_Realestate user = new Users_Realestate();

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = userController.register(user, bindingResult, model);
        assertEquals("register", result);
    }

    // ---------------------- LOGOUT TEST ----------------------
    @Test
    void testLogout() {
        String result = userController.logoutPage(model);
        assertEquals("login", result);
        verify(model).addAttribute("message", "You have been logged out successfully.");
    }

    // ---------------------- HOME TEST ----------------------
    @Test
    void testHome() {
        String result = userController.home();
        assertEquals("index", result);
    }

    // ---------------------- EXCEPTION HANDLER TESTS ----------------------
    @Test
    void testHandleUserNotFoundException() {
        UserNotFoundException ex = new UserNotFoundException("Email not found");
        String result = userController.handleUserNotFound(ex, model);
        assertEquals("login", result);
        verify(model).addAttribute("error", "Email not found");
    }

    @Test
    void testHandleUserAlreadyExistsException() {
        UserAlreadyExistsException ex = new UserAlreadyExistsException("Email already exists!");
        String result = userController.handleUserAlreadyExists(ex, model);
        assertEquals("register", result);
        verify(model).addAttribute("error", "Email already exists!");
    }
}
