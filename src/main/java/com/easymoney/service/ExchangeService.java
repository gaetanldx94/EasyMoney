package com.easymoney.service;

import com.easymoney.model.Account;
import com.easymoney.model.Currency;
import com.easymoney.model.Exchange;
import com.easymoney.repository.ExchangeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;

    public ExchangeService(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public Exchange createExchange(Account account, Currency from, Currency to, BigDecimal rate, BigDecimal amount) {
        if (from == to) {
            throw new IllegalArgumentException("Cannot exchange same currency");
        }

        BigDecimal convertedAmount = amount.multiply(rate);

        Exchange exchange = new Exchange(from, to, rate, account, convertedAmount);

        return exchangeRepository.save(exchange);
    }

    public List<Exchange> getExchangesByAccount(Account account) {
        return exchangeRepository.findByAccount(account);
    }

    public BigDecimal convertAmount(BigDecimal amount, Currency from, Currency to, BigDecimal rate) {
        if (from == to) return amount;
        return amount.multiply(rate);
    }
}
