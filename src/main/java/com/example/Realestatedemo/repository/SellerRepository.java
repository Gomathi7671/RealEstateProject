package com.example.Realestatedemo.repository;

import com.example.Realestatedemo.model.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends  MongoRepository<Seller, String> {
}
