package com.grey.cagnotte.payload.request;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EtatRequest {
    public String libelle;
}
