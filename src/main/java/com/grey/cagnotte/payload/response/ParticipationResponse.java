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
    private double amount;
    private LocalDateTime dateParticipation;
    private String participantName;
    private String customMessage;
    private boolean isAnonymous;
    private boolean showAmount;
}
