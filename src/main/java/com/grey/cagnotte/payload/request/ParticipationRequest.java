package com.grey.cagnotte.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipationRequest {
    private double amount;
    private String cagnotte_url;
    private String cagnotte_access_token;
    private LocalDateTime dateParticipation;
    private String participantName;
    private String customMessage;
    private boolean isAnonymous;
    private boolean showAmount;
}