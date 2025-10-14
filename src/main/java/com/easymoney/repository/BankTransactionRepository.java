package com.easymoney.repository;

import com.easymoney.model.Account;
import com.easymoney.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    List<BankTransaction> findByAccount(Account account);
}
