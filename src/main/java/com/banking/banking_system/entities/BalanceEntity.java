package com.banking.banking_system.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance")
public class BalanceEntity {

    @Id
    @Column(name = "account_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private AccountEntity account;

    @Column(name = "account_balance", precision = 19, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected BalanceEntity() {
    }

    public BalanceEntity(AccountEntity account, BigDecimal balance) {
        this.account = account;
        this.balance = balance;
    }

    @PrePersist
    protected void onCreate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public AccountEntity getAccount() {
        return account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
