package com.easymoney.model;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String iban;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Account(
            String currency,
            User   user
    ) {
        this.currency = currency;
        this.user     = user;
    }

    public long   getId      () { return id;       }
    public String getIban    () { return iban;     }
    public String getCurrency() { return currency; }
    public User   getUser    () { return user;     }

    public void setCurrency(String currency) { this.currency = currency; }
}