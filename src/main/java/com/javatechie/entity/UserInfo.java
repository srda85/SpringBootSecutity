package com.javatechie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//3.0 Pour éviter de hardcoder les utilisateur, je vais créer une entité qui va les prendre en compte dans ma bdd.
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String roles;
}
