package com.grey.cagnotte.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cagnotte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String label;
    private String slug;
    private String concerns;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    @Column(name = "date_due")
    private LocalDateTime dateDue;
    @Column(name = "goal_amount")
    private double goalAmount = 0;
    @Column(name = "collected_amount")
    private double collectedAmount = 0;
    @Column(name = "participation_amount")
    private double participationAmount;
    private String image;
    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "is_public")
    private boolean isPublic;

    private String url;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "cagnotte")
    @JsonIgnore
    private List<Participation> participations;

}
