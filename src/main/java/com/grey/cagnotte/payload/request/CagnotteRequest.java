package com.grey.cagnotte.payload.request;


import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class CagnotteRequest {
    private String label;
    private String reference;
    private String organizer;
    private String concerns;
    private LocalDateTime dateDue;
    private double goalAmount;
    private double collectedAmount;
    private double participationAmount;
    private String personalizedMessage;
    private String image;
    private String eventLocation;
    private String url;
    private String state_slug;
    private long category_id;
}
