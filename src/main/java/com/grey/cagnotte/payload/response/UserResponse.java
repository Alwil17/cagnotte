package com.grey.cagnotte.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String nom;
    private String prenoms;
    private String email;
    private String tel1;
    private String tel2;
    private String adresse;
    private String password;
    private String type;
}
