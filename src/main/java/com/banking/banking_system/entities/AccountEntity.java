package com.banking.banking_system.entities;

import com.banking.banking_system.entities.enums.AccountStatusEnum;
import com.banking.banking_system.entities.enums.AccountTypeEnum;
import com.banking.banking_system.entities.enums.CurrencyEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "account_number", length = 50, nullable = false, unique = true)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "legal_person_id", nullable = false)
    private LegalPersonEntity legalPerson;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branch;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status",  nullable = false)
    private AccountStatusEnum accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type",  nullable = false    )
    private AccountTypeEnum accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", length = 3, nullable = false)
    private CurrencyEnum currency;

    protected AccountEntity() {
    }

    public AccountEntity(String accountNumber, LegalPersonEntity legalPerson, BranchEntity branch, AccountStatusEnum accountStatus, AccountTypeEnum accountType, CurrencyEnum currency) {
        this.accountNumber = accountNumber;
        this.legalPerson = legalPerson;
        this.branch = branch;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
        this.currency = currency;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public LegalPersonEntity getLegalPerson() {
        return legalPerson;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public AccountStatusEnum getAccountStatus() {
        return accountStatus;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }
}
