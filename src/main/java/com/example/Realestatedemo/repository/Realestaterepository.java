package com.example.Realestatedemo.repository;

import com.example.Realestatedemo.model.Users_Realestate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface Realestaterepository extends JpaRepository<Users_Realestate,Integer> {
    Optional<Users_Realestate> findByEmail(String email);
    Optional<Users_Realestate> findByName(String password); // optional if you use name anywhere
}
