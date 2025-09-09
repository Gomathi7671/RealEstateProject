package com.example.Realestatedemo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users_realestate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users_Realestate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // SELLER, CUSTOMER, ADMIN
}
