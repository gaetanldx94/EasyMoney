package com.easymoney.model;

import com.easymoney.model.type.Currency;
import com.easymoney.model.type.TransactionStatus;
import com.easymoney.model.type.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String description;
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.SUCCESS;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public BankTransaction() {}

    public BankTransaction(
            BigDecimal      amount,
            Currency        currency,
            TransactionType type,
            String          description,
            Account         account
    ) {
        this.amount      = amount;
        this.currency    = currency;
        this.type        = type;
        this.description = description;
        this.account     = account;
    }

    public long              getId         () { return id;          }
    public BigDecimal        getAmount     () { return amount;      }
    public Currency          getCurrency   () { return currency;    }
    public String            getDescription() { return description; }
    public TransactionType   getType       () { return type;        }
    public LocalDateTime     getDate       () { return date;        }
    public Account           getAccount    () { return account;     }
    public TransactionStatus getStatus     () { return status;      }

    public void setStatus(TransactionStatus status) { this.status = status; }
}