package com.grey.cagnotte.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Categorie {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id ;
  @Max(100)
  private String libelle ;
  @Max(100)
  private String slug ;
  @Max(100)
  private String icone ;
  @Column(columnDefinition = "BOOLEAN DEFAULT false")
  private boolean allow_concerne ;
  @Column(columnDefinition = "BOOLEAN DEFAULT false")
  private boolean allow_message ;
  @Column(columnDefinition = "BOOLEAN DEFAULT false")
  private boolean allow_image ;
  @Column(columnDefinition = "BOOLEAN DEFAULT false")
  private boolean allow_lieu ;
  @Column(columnDefinition = "BOOLEAN DEFAULT false")
  private boolean allow_url ;


}
