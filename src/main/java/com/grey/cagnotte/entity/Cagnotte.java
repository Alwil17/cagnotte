package com.grey.cagnotte.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cagnotte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
    @JoinColumn(name = "utilisateur_id", insertable=false, updatable = false)
    private User user;
    private int utilisateur_id;

    @ManyToOne
    @JoinColumn(name = "categorie_id", insertable=false, updatable = false)
    private Categorie categorie;
    private int categorie_id;

    @ManyToOne
    @JoinColumn(name = "etat_id", insertable=false, updatable = false)
    private Etat etat;
    private int etat_id;


    public void setId(Integer id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setOrganisateur(String organisateur) {
        this.organisateur = organisateur;
    }

    public void setConcerne(String concerne) {
        this.concerne = concerne;
    }

    public void setDate_creation(LocalDateTime date_creation) {
        this.date_creation = date_creation;
    }

    public void setDate_echeance(LocalDateTime date_echeance) {
        this.date_echeance = date_echeance;
    }

    public void setMontant_objectif(double montant_objectif) {
        this.montant_objectif = montant_objectif;
    }

    public void setMontant_collecte(double montant_collecte) {
        this.montant_collecte = montant_collecte;
    }

    public void setType_participation(int type_participation) {
        this.type_participation = type_participation;
    }

    public void setMontant_participation(double montant_participation) {
        this.montant_participation = montant_participation;
    }

    public void setMessage_personnalise(String message_personnalise) {
        this.message_personnalise = message_personnalise;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLieu_evenement(LocalDateTime lieu_evenement) {
        this.lieu_evenement = lieu_evenement;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
