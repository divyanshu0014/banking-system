package com.banking.banking_system.repository;

import com.banking.banking_system.entities.BalanceEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;import org.springframework.data.repository.query.Param;import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {

    Optional<BalanceEntity> findById(Long accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BalanceEntity b WHERE b.id = :id")
    Optional<BalanceEntity> findByIdForUpdate(@Param("id") Long id);
}
