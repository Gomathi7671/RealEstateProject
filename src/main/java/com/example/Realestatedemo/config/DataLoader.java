package com.example.Realestatedemo.config;

import com.example.Realestatedemo.model.Role;
import com.example.Realestatedemo.model.Users_Realestate;
import com.example.Realestatedemo.repository.Realestaterepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final Realestaterepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(Realestaterepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

   @Override
public void run(String... args) throws Exception {
    if (userRepository.findByEmail("admin234@gmail.com").isEmpty()) {
        Users_Realestate admin = new Users_Realestate();
        admin.setName("adminator");
        admin.setEmail("admin234@gmail.com");
        admin.setPassword(passwordEncoder().encode("asdf1234asdf")); // 👈 encode
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);
        System.out.println("Admin user created!");
    } else {
        System.out.println("Admin user already exists!");
    }
}

private PasswordEncoder passwordEncoder() {
    return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
}
}