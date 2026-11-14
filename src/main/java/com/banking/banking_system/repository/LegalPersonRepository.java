package com.banking.banking_system.repository;

import com.banking.banking_system.entities.LegalPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalPersonRepository extends JpaRepository<LegalPersonEntity, Long> {
}
