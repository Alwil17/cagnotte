package com.grey.cagnotte.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorieResponse {
    private long id ;
    private String libelle ;
    private String slug ;
    private String icone ;
    private boolean allow_concerne ;
    private boolean allow_message ;
    private boolean allow_media ;
    private boolean allow_lieu ;
    private boolean allow_url ;
}
