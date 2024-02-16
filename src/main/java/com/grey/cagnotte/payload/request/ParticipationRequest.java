package com.grey.cagnotte.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipationRequest {
    private long cagnotte_id;
    private double montant;
    private LocalDateTime date_participation;
    private String nom_participant;
    private String message_personnalise;
    private boolean is_anonyme;
    private boolean show_montant;
}