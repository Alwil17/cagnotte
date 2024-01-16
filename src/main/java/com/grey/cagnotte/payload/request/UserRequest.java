package com.grey.cagnotte.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String nom;
    private String prenoms;
    private String email;
    private String tel1;
    private String tel2;
    private String adresse;
    private String password;
    private String type;
}