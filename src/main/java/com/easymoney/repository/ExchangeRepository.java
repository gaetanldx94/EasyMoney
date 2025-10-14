package com.easymoney.repository;

import com.easymoney.model.Account;
import com.easymoney.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    Optional<Exchange> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
    List<Exchange> findByAccount(Account account);
}