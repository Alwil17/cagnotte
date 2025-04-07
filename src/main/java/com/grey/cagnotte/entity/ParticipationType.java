package com.grey.cagnotte.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "participation_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Cagnotte> cagnottes;
}

