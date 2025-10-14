package com.easymoney.repository;

import com.easymoney.model.Account;
import com.easymoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository  extends JpaRepository<Account, Long> {
    boolean existsByIban(String iban);
    List<Account> findByUser(User user);
}
