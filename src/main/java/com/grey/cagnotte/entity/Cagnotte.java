package com.grey.cagnotte.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cagnotte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String libelle;
    private String slug;
    private String reference;
    private String organisateur;
    private String concerne;
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    @Column(name = "date_echeance")
    private LocalDateTime dateEcheance;
    @Column(name = "montant_objectif")
    private double montantObjectif = 0;
    @Column(name = "montantCollecte")
    private double montantCollecte = 0;
    @Column(name = "montant_participation")
    private double montantParticipation;
    @Column(name = "message_personnalise")
    private String messagePersonnalise;
    private String image;
    @Column(name = "lieu_evenement")
    private String lieuEvenement;
    private String url;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "etat_id")
    private Etat etat;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "cagnotte")
    @JsonIgnore
    private List<Participation> participations;

}
