package com.grey.cagnotte.payload.request;


import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class CagnotteRequest {
    private String libelle;
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
    private String user_email;
}
