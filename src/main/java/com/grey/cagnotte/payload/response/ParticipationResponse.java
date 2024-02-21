package com.grey.cagnotte.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationResponse {
    private long id;
    private long cagnotte_id;
    private double montant;
    private LocalDateTime date_participation;
    private String nom_participant;
    private String message_personnalise;
    private boolean is_anonyme;
    private boolean show_montant;
}
