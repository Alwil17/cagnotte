package com.grey.cagnotte.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255, unique = true)
    private String title;

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;
}

