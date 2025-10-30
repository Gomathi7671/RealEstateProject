package com.example.Realestatedemo.Controller;

import com.example.Realestatedemo.model.Role;
import com.example.Realestatedemo.model.Users_Realestate;
import com.example.Realestatedemo.repository.Realestaterepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private Realestaterepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test: login page
    @Test
    void testLoginPage() {
        String viewName = userController.loginPage();
        assertEquals("login", viewName);
    }

    // ✅ Test: register page
    @Test
    void testRegisterPage() {
        String viewName = userController.registerPage(model);

        assertEquals("register", viewName);
        verify(model).addAttribute(eq("user"), any(Users_Realestate.class));
    }

    // ✅ Test: successful registration
    @Test
    void testRegister_Success() {
        Users_Realestate user = new Users_Realestate();
        user.setEmail("test@example.com");
        user.setPassword("plain123");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plain123")).thenReturn("encoded123");

        String viewName = userController.register(user, model);

        assertEquals("login", viewName);
        assertEquals("encoded123", user.getPassword());
        assertEquals(Role.CUSTOMER, user.getRole()); // Default role
        verify(userRepository).save(user);
        verify(model).addAttribute("success", "Registration successful! Please login.");
    }

    // ✅ Test: registration fails when email already exists
    @Test
    void testRegister_EmailExists() {
        Users_Realestate user = new Users_Realestate();
        user.setEmail("existing@example.com");

        when(userRepository.findByEmail("existing@example.com"))
                .thenReturn(Optional.of(new Users_Realestate()));

        String viewName = userController.register(user, model);

        assertEquals("register", viewName);
        verify(model).addAttribute("error", "Email already exists!");
        verify(userRepository, never()).save(any());
    }

    // ✅ Test: home page
    @Test
    void testHomePage() {
        String viewName = userController.home();
        assertEquals("index", viewName);
    }
}
