package com.easymoney.model;

import com.easymoney.service.UserService;
import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String iban;

    private long balance;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Account(
            String currency,
            User user
    ) {
        this.currency = currency;
        this.user     = user;
    }

    public long   getId      () { return id;       }
    public String getIban    () { return iban;     }
    public long   getBalance () { return balance;  }
    public String getCurrency() { return currency; }
    public User   getUser    () { return user;     }

    public void setBalance (long balance)    { this.balance = balance;   }
    public void setCurrency(String currency) { this.currency = currency; }
}