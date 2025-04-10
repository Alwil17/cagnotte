package com.grey.cagnotte.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipationRequest {
    private double amount;
    private long cagnotte_id;
    private long type_id;
    private LocalDateTime dateParticipation;
    private String participantName;
    private String customMessage;
    private boolean isAnonymous;
    private boolean showAmount;
}