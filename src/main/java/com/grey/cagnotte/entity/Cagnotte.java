package com.grey.cagnotte.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cagnotte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String libelle;
    private String slug;
    private String reference;
    private String organisateur;
    private String concerne;
    private LocalDateTime date_creation;
    private LocalDateTime date_echeance;
    private double montant_objectif;
    private double montant_collecte;
    private int type_participation;
    private double montant_participation;
    private String message_personnalise;
    private String image;
    private LocalDateTime lieu_evenement;
    private String url;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private long categorie_id;
    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;


}
