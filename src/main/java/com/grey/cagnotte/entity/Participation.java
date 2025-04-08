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
public class Participation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private double amount;

    @Column(name = "date_participation", nullable = false)
    private LocalDateTime dateParticipation;

    @Column(name = "participant_name")
    private String participantName;
    @Column(name = "custom_message")
    private String customMessage;
    @Column(name = "is_anonymous")
    private boolean isAnonymous = false;
    @Column(name = "show_amount")
    private boolean showAmount = true;

    @Column(name = "participant_id")
    private long participantId;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "participation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "cagnotte_id")
    @JsonIgnore
    private Cagnotte cagnotte;
}