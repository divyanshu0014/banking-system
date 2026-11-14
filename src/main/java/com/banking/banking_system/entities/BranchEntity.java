package com.banking.banking_system.entities;

import com.banking.banking_system.entities.enums.BranchStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "branch")
public class BranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch_status", nullable = false)
    private BranchStatusEnum branchStatus;

    protected BranchEntity() {
    }

    public BranchEntity(String branchName, BranchStatusEnum branchStatus) {
        this.branchName = branchName;
        this.branchStatus = branchStatus;
    }

    public Long getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public BranchStatusEnum getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(BranchStatusEnum branchStatus) {
        this.branchStatus = branchStatus;
    }
}
