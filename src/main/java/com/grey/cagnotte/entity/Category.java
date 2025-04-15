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
  private String slug;
  private String icon;
  private String description;

  @OneToMany(mappedBy = "category")
  @JsonIgnore
  private List<Cagnotte> cagnottes;
}
