package com.example.Realestatedemo.repository;

import com.example.Realestatedemo.model.Users_Realestate;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface Realestaterepository extends  JpaRepository<Users_Realestate,Long> {
    Optional<Users_Realestate> findByEmail(String email);
    Optional<Users_Realestate> findByName(String password); // optional if you use name anywhere
}
