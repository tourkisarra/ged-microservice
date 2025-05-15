package com.example.ged_microservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
public class User {

    @Id
    private String username; // Peut être aussi un email

    private String fullName;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
