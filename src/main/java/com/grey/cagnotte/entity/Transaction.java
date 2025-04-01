package com.grey.cagnotte.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Relation Many-to-One avec Participation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_id", nullable = false)
    private Participation participation;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private String status;

    @Column(name = "external_reference")
    private String externalReference;

    @Column(nullable = false)
    private BigDecimal montant;
}
