package com.banking.banking_system.repository;

import com.banking.banking_system.entities.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, Long> {
}
