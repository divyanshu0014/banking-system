package com.banking.banking_system.repository;

import com.banking.banking_system.entities.LegalPersonIdentityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalPersonIdentityRepository extends JpaRepository<LegalPersonIdentityEntity, Long> {
}
