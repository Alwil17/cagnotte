package com.grey.cagnotte.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Max(value = 50)
    private String nom;
    @Max(value = 100)
    private String prenoms;
    @Column(nullable = false, unique = true)
    private String email;
    @Max(value = 20)
    private String tel1;
    @Max(value = 20)
    private String tel2;
    private String adresse;
    private String password;
    private String type;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Cagnotte> cagnottes;
}