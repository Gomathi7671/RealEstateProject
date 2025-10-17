package com.example.Realestatedemo.repository;

import com.example.Realestatedemo.model.Users_Realestate;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Realestaterepository extends  MongoRepository<Users_Realestate, String> {
    Optional<Users_Realestate> findByEmail(String email);
    Optional<Users_Realestate> findByName(String password); // optional if you use name anywhere
}
