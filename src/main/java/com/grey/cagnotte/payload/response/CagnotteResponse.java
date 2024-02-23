package com.grey.cagnotte.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CagnotteResponse {
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
    private UserResponse user;
    private long categorie_id;
    private long etat_id;
}
