package com.easymoney.service;

import com.easymoney.model.Account;
import com.easymoney.model.BankTransaction;
import com.easymoney.model.type.TransactionStatus;
import com.easymoney.model.type.TransactionType;
import com.easymoney.repository.AccountRepository;
import com.easymoney.repository.BankTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankTransactionService {

    private final BankTransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public BankTransactionService(BankTransactionRepository transactionRepository,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public BankTransaction deposit(Account account, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        BankTransaction transaction = new BankTransaction(
                amount,
                account.getCurrency(),
                TransactionType.DEPOSIT,
                description,
                account
        );

        return transactionRepository.save(transaction);
    }

    public BankTransaction withdraw(Account account, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        BigDecimal balance = getAccountBalance(account);
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        BankTransaction transaction = new BankTransaction(
                amount,
                account.getCurrency(),
                TransactionType.WITHDRAW,
                description,
                account
        );

        return transactionRepository.save(transaction);
    }

    public BankTransaction transfer(Account from, Account to, BigDecimal amount, String description) {
        if (!from.getCurrency().equals(to.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }

        BankTransaction withdrawal = withdraw(from, amount, "Transfer to " + to.getIban() + " - " + description);
        BankTransaction deposit = deposit(to, amount, "Transfer from " + from.getIban() + " - " + description);

        return deposit;
    }

    public BigDecimal getAccountBalance(Account account) {
        List<BankTransaction> transactions = transactionRepository.findByAccount(account);

        return transactions.stream()
                .filter(t -> t.getStatus() == TransactionStatus.SUCCESS)
                .map(t -> {
                    switch (t.getType()) {
                        case DEPOSIT:
                            return t.getAmount();
                        case WITHDRAW:
                            return t.getAmount().negate();
                        case TRANSFER:
                            return t.getAccount().equals(account)
                                    ? t.getAmount()
                                    : t.getAmount().negate();
                        default:
                            return BigDecimal.ZERO;
                    }
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}