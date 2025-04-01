package com.grey.cagnotte.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorieRequest {
    private String libelle;
    private String icone;
    private boolean allow_concerne;
    private boolean allow_message;
    private boolean allow_media;
    private boolean allow_lieu;
    private boolean allow_url;

}
