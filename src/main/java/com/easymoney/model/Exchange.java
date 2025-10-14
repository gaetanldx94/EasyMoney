package com.easymoney.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Currency      fromCurrency;

    @Enumerated(EnumType.STRING)
    private Currency      toCurrency;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal convertedAmount;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private BigDecimal    rate;
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Exchange(
            Currency   fromCurrency,
            Currency   toCurrency,
            BigDecimal rate
    ) {
        this.fromCurrency = fromCurrency;
        this.toCurrency   = toCurrency;
        this.rate         = rate;
    }

    public Currency getFromCurrency  () { return fromCurrency; }
    public Currency getToCurrency    () { return toCurrency;   }
    public BigDecimal getRate        () { return rate;         }
    public Long getId                () { return id;           }
    public LocalDateTime getUpdatedAt() { return updatedAt;    }
}