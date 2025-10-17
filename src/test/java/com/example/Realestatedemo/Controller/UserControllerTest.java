// package com.example.Realestatedemo.Controller;

// import com.example.Realestatedemo.model.Users_Realestate;
// import com.example.Realestatedemo.repository.Realestaterepository;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.web.servlet.MockMvc;

// import java.util.Optional;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// // @WebMvcTest(UserController.class)
// // @AutoConfigureMockMvc(addFilters = false)
// class UserControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private Realestaterepository userRepository;

//     @MockBean
//     private PasswordEncoder passwordEncoder;

//     @Test
//     void testLoginPage() throws Exception {
//         mockMvc.perform(get("/login"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("login"));
//     }

//     @Test
//     void testRegisterPage() throws Exception {
//         mockMvc.perform(get("/register"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("register"))
//                 .andExpect(model().attributeExists("user"));
//     }

//     @Test
//     void testRegister_NewUser_Success() throws Exception {
//         Users_Realestate user = new Users_Realestate();
//         user.setEmail("test@example.com");
//         user.setPassword("plainpass");

//         // Mock repository and encoder
//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
//         when(passwordEncoder.encode("plainpass")).thenReturn("encodedpass");
//         when(userRepository.save(any(Users_Realestate.class))).thenReturn(user);

//         mockMvc.perform(post("/register")
//                         .param("email", "test@example.com")
//                         .param("password", "plainpass"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("login"))
//                 .andExpect(model().attributeExists("success"));
//     }

//     @Test
//     void testRegister_EmailAlreadyExists() throws Exception {
//         Users_Realestate existingUser = new Users_Realestate();
//         existingUser.setEmail("duplicate@example.com");

//         when(userRepository.findByEmail("duplicate@example.com")).thenReturn(Optional.of(existingUser));

//         mockMvc.perform(post("/register")
//                         .param("email", "duplicate@example.com")
//                         .param("password", "secret"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("register"))
//                 .andExpect(model().attributeExists("error"));
//     }

//     @Test
//     void testHomePage() throws Exception {
//         mockMvc.perform(get("/"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("index"));
//     }
// }
