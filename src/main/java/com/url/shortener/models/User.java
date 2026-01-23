package com.url.shortener.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users", 
        uniqueConstraints = { 
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email") 
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;
    private String role = "ROLE_USER";
}