package com.easymoney.controller;

import com.easymoney.model.Account;
import com.easymoney.model.type.Currency;
import com.easymoney.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(
            AccountService accountService
    ) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/user/{uuid}")
    public List<Account> getAccountByUser(@PathVariable String uuid) {
        return accountService.getAccountsByUser(uuid);
    }

    @PostMapping
    public Account createAccount(@RequestParam String userUuid, @RequestParam Currency currency) {
        return accountService.createAccount(userUuid, currency);
    }
}
