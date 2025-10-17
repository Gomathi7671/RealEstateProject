package com.example.Realestatedemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.*;


@Document(collection = "users_realestate") // MongoDB collection name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users_Realestate {

    @Id
   
    private String id;

    private String name;
    private String email;
    private String password;

  //  @Enumerated(EnumType.STRING)
    private Role role; // SELLER, CUSTOMER, ADMIN
}
