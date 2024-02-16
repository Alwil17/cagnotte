package com.grey.cagnotte.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
public class Participation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private long cagnotte_id;
    private double montant;
    private LocalDateTime date_participation;
    private String nom_participant;
    private String message_personnalise;
    private boolean is_anonyme;
    private boolean show_montant;
}