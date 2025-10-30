package com.example.Realestatedemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Property name is required")
    private String propertyName;

    @NotBlank(message = "Property type is required")
    private String propertyType; // Rent or Sale

    @NotBlank(message = "Square feet is required")
    private String squareFeet;

    @NotNull(message = "Rate is required")
    private Double rate;

    @NotBlank(message = "Place is required")
    private String place;

    @NotBlank(message = "Land owner is required")
    private String landOwner;

    @NotBlank(message = "Contact is required")
    private String contact;

    private String imageUrl; // Cloudinary URL

    @Column(columnDefinition = "TEXT")
    private String description; // Optional

    private String status = "Pending"; // Default status

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "Property category is required")
    private String propertyCategory; // Apartment, House, Land

    private String amenities; // Pool, Parking, Gym

    private String furnished; // Yes/No

    private String builtYear; // Year of construction

    // ===== Getters and Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
