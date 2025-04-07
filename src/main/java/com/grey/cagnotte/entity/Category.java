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
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Max(100)
  private String label;
  private String slug ;
  private String icone ;

  @Column(name = "allow_concern")
  private boolean allowConcern;
  @Column(name = "allow_message")
  private boolean allowMessage;
  @Column(name = "allow_media")
  private boolean allowMedia;
  @Column(name = "allow_location")
  private boolean allowLocation;
  @Column(name = "allow_url")
  private boolean allowUrl;

  @OneToMany(mappedBy = "category")
  @JsonIgnore
  private List<Cagnotte> cagnottes;

}
