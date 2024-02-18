package com.grey.cagnotte.payload.request;


import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class CagnotteRequest {

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

    public String getLibelle() {
        return libelle;
    }

    public String getSlug() {
        return slug;
    }

    public String getReference() {
        return reference;
    }

    public String getOrganisateur() {
        return organisateur;
    }

    public String getConcerne() {
        return concerne;
    }

    public LocalDateTime getDate_creation() {
        return date_creation;
    }

    public LocalDateTime getDate_echeance() {
        return date_echeance;
    }

    public double getMontant_objectif() {
        return montant_objectif;
    }

    public double getMontant_collecte() {
        return montant_collecte;
    }

    public int getType_participation() {
        return type_participation;
    }

    public double getMontant_participation() {
        return montant_participation;
    }

    public String getMessage_personnalise() {
        return message_personnalise;
    }

    public String getImage() {
        return image;
    }

    public LocalDateTime getLieu_evenement() {
        return lieu_evenement;
    }

    public String getUrl() {
        return url;
    }
}
