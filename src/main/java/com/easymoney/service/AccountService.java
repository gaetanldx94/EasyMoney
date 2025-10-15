package com.easymoney.service;

import com.easymoney.model.Account;
import com.easymoney.model.type.Currency;
import com.easymoney.model.User;
import com.easymoney.repository.AccountRepository;
import com.easymoney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IbanService ibanService;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(String userUuid, Currency currency) {
        User user = userRepository.findById(userUuid).orElseThrow(() -> new IllegalArgumentException("User not found"));

        String iban = ibanService.generateIban();
        Account account = new Account(currency, user, iban);
        return accountRepository.save(account);
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<Account> getAccountsByUser(String userUuid) {
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return accountRepository.findByUser(user);
    }
}