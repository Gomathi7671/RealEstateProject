package com.example.Realestatedemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "final_estate")
public class FinalEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String propertyName;

    @NotBlank
    private String propertyType;

    private String squareFeet;

    @NotNull
    private Double rate;

    private String place;
    private String city;
    private String state;
    private String postalCode;
    private String propertyCategory;
    private String amenities;
    private String furnished;
    private String builtYear;
    private String description;
    private String landOwner;
    private String contact;
    private String imageUrl;

    // ----------------- Mapping to Seller -----------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    // Getters & Setters
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

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLandOwner() { return landOwner; }
    public void setLandOwner(String landOwner) { this.landOwner = landOwner; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }
}
