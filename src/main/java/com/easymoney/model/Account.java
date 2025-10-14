package com.easymoney.model;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String iban;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Account(
            Currency currency,
            User     user,
            String   iban
    ) {
        this.currency = currency;
        this.user     = user;
        this.iban     = iban;
    }

    public long     getId      () { return id;       }
    public String   getIban    () { return iban;     }
    public Currency getCurrency() { return currency; }
    public User     getUser    () { return user;     }

    public void setCurrency(Currency currency) { this.currency = currency; }
}