package com.grey.cagnotte.payload.response;

import com.grey.cagnotte.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CagnotteResponse {
    private long id;
    private String label;
    private String slug;
    private String reference;
    private String organizer;
    private String concerns;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDue;
    private double goalAmount;
    private double collectedAmount;
    private double participationAmount;
    private String personalizedMessage;
    private String image;
    private String eventLocation;
    private String url;
    private UserResponse user;
    private CategoryResponse category;
    private State state;
    private List<ParticipationResponse> participations;
}
