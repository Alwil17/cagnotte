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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @Max(value = 150)
    public String libelle;
    @Max(value = 50)
    public String slug;
}
