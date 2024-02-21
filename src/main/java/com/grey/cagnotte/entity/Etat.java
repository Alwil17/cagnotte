package com.grey.cagnotte.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Etat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Max(value = 100)
    private String libelle;
    private String slug;
}
