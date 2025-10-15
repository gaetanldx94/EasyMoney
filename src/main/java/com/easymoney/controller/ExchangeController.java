package com.easymoney.controller;

import com.easymoney.model.Account;
import com.easymoney.model.Exchange;
import com.easymoney.model.type.Currency;
import com.easymoney.service.AccountService;
import com.easymoney.service.ExchangeService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final AccountService accountService;

    public ExchangeController(ExchangeService exchangeService, AccountService accountService) {
        this.exchangeService = exchangeService;
        this.accountService = accountService;
    }

    @PostMapping
    public Exchange createExchange(@RequestParam Long accountId,
                                   @RequestParam Currency from,
                                   @RequestParam Currency to,
                                   @RequestParam BigDecimal rate,
                                   @RequestParam BigDecimal amount) {
        Account account = accountService.getAccountById(accountId);
        return exchangeService.createExchange(account, from, to, rate, amount);
    }

    @GetMapping("/account/{accountId}")
    public List<Exchange> getExchangesByAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return exchangeService.getExchangesByAccount(account);
    }
}
