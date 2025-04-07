package com.grey.cagnotte.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Max(value = 100)
    private String label;
    private String slug;


    @OneToMany(mappedBy = "state")
    @JsonIgnore
    private List<Cagnotte> cagnottes;
}
