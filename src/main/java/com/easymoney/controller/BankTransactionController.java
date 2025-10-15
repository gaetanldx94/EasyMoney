package com.easymoney.controller;

import com.easymoney.model.Account;
import com.easymoney.model.BankTransaction;
import com.easymoney.service.AccountService;
import com.easymoney.service.BankTransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class BankTransactionController {

    private final BankTransactionService transactionService;
    private final AccountService accountService;

    public BankTransactionController(BankTransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @PostMapping("/deposit")
    public BankTransaction deposit(@RequestParam Long accountId,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam String description) {
        Account account = accountService.getAccountById(accountId);
        return transactionService.deposit(account, amount, description);
    }

    @PostMapping("/withdraw")
    public BankTransaction withdraw(@RequestParam Long accountId,
                                    @RequestParam BigDecimal amount,
                                    @RequestParam String description) {
        Account account = accountService.getAccountById(accountId);
        return transactionService.withdraw(account, amount, description);
    }

    @PostMapping("/transfer")
    public BankTransaction transfer(@RequestParam Long fromAccountId,
                                    @RequestParam Long toAccountId,
                                    @RequestParam BigDecimal amount,
                                    @RequestParam String description) {
        Account from = accountService.getAccountById(fromAccountId);
        Account to = accountService.getAccountById(toAccountId);
        return transactionService.transfer(from, to, amount, description);
    }

    @GetMapping("/account/{accountId}/balance")
    public BigDecimal getBalance(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return transactionService.getAccountBalance(account);
    }
}
