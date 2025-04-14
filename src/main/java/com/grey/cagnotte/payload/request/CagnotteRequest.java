package com.grey.cagnotte.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class CagnotteRequest {
    private String label;
    private String concerns;
    private String description;
    private LocalDateTime dateDue;
    private double goalAmount;
    private double collectedAmount;
    private double participationAmount;
    private String image;
    private String eventLocation;
    private boolean isPublic;
    @NotBlank
    private String category_slug;
}
