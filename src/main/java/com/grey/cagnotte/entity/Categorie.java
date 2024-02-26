package com.grey.cagnotte.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.util.List;

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
  private String slug ;
  private String icone ;
  private boolean allow_concerne = false;
  private boolean allow_message = false;
  private boolean allow_image = false;
  private boolean allow_lieu = false;
  private boolean allow_url = false;

  @OneToMany(mappedBy = "categorie")
  @JsonIgnore
  private List<Cagnotte> cagnottes;

}
