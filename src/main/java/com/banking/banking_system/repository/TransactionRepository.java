package com.banking.banking_system.repository;

import com.banking.banking_system.entities.BankTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<BankTransactionEntity, Long> {
}
