package com.example.Realestatedemo.repository;

import com.example.Realestatedemo.model.FinalEstate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalEstateRepository extends JpaRepository<FinalEstate, Long> {

    @Query("SELECT f FROM FinalEstate f " +
           "WHERE (:city IS NULL OR LOWER(f.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "AND (:propertyType IS NULL OR f.propertyType = :propertyType) " +
           "AND (:propertyCategory IS NULL OR f.propertyCategory = :propertyCategory) " +
           "AND (:minRate IS NULL OR f.rate >= :minRate) " +
           "AND (:maxRate IS NULL OR f.rate <= :maxRate)")
    List<FinalEstate> searchProperties(
            String city,
            String propertyType,
            String propertyCategory,
            Double minRate,
            Double maxRate
    );
}
