package com.easymoney.service;

import com.easymoney.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IbanService {

    @Autowired
    private AccountRepository accountRepository;

    public String generateIban() {
        String iban;
        do {
            iban = "FR" +
                    (int)(Math.random() * 90 + 10) +
                    (int)(Math.random() * 900 + 100) +
                    (long)(Math.random() * 1_000_000_000L);
        } while (accountRepository.existsByIban(iban));

        return iban;
    }
}