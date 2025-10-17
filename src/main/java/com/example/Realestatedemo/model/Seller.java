package com.example.Realestatedemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




@Document(collection = "sellers")
public class Seller {

    @Id
   
    private String id;

    private String propertyName;
    private String propertyType; // Rent or Sale
    private String squareFeet;
    private Double rate;
    private String place;
    private String landOwner;
    private String contact;
    private String imageUrl;
//(columnDefinition = "TEXT")  // Cloudinary URL
    private String description; // Optional property description
    private String status; // Pending, Approved, Rejected
    private String city;
    private String state;
    private String postalCode;
    private String propertyCategory; // e.g., Apartment, House, Land
    private String amenities; // e.g., Pool, Parking, Gym
    private String furnished; // Yes/No
    private String builtYear; // Year of construction

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }

    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }

    public String getSquareFeet() { return squareFeet; }
    public void setSquareFeet(String squareFeet) { this.squareFeet = squareFeet; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getLandOwner() { return landOwner; }
    public void setLandOwner(String landOwner) { this.landOwner = landOwner; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getPropertyCategory() { return propertyCategory; }
    public void setPropertyCategory(String propertyCategory) { this.propertyCategory = propertyCategory; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public String getFurnished() { return furnished; }
    public void setFurnished(String furnished) { this.furnished = furnished; }

    public String getBuiltYear() { return builtYear; }
    public void setBuiltYear(String builtYear) { this.builtYear = builtYear; }
}
