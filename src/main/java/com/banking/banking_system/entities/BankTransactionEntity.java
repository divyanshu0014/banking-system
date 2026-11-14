package com.banking.banking_system.entities;

import com.banking.banking_system.entities.enums.TransactionStatusEnum;
import com.banking.banking_system.entities.enums.TransactionTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_transaction")
public class BankTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionTypeEnum transactionType;

    @Column(name = "amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatusEnum status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    private AccountEntity sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account_id", nullable = true)
    private AccountEntity targetAccount;

    protected BankTransactionEntity() {
    }

    public BankTransactionEntity(TransactionTypeEnum transactionType, BigDecimal amount, TransactionStatusEnum status, AccountEntity sourceAccount, AccountEntity targetAccount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.status = status;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public AccountEntity getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(AccountEntity sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public AccountEntity getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(AccountEntity targetAccount) {
        this.targetAccount = targetAccount;
    }
}
