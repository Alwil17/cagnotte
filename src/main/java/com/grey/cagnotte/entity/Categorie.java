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

  @Column(name = "allow_concerne")
  private boolean allowConcerne;
  @Column(name = "allow_message")
  private boolean allowMessage;
  @Column(name = "allow_media")
  private boolean allowMedia;
  @Column(name = "allow_lieu")
  private boolean allowLieu;
  @Column(name = "allow_url")
  private boolean allowUrl;

  @OneToMany(mappedBy = "categorie")
  @JsonIgnore
  private List<Cagnotte> cagnottes;

}
