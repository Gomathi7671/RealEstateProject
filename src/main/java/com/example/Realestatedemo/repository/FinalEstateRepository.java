package com.example.Realestatedemo.repository;

import com.example.Realestatedemo.model.FinalEstate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalEstateRepository extends MongoRepository<FinalEstate, String> {

    @Query("{ '$and': [ " +
           "{ $or: [ { 'city': { $regex: ?0, $options: 'i' } }, { '?0': null } ] }, " +
           "{ $or: [ { 'propertyType': ?1 }, { '?1': null } ] }, " +
           "{ $or: [ { 'propertyCategory': ?2 }, { '?2': null } ] }, " +
           "{ $or: [ { 'rate': { $gte: ?3 } }, { '?3': null } ] }, " +
           "{ $or: [ { 'rate': { $lte: ?4 } }, { '?4': null } ] } " +
           "] }")
    List<FinalEstate> searchProperties(String city, String propertyType, String propertyCategory, Double minRate, Double maxRate);
}
