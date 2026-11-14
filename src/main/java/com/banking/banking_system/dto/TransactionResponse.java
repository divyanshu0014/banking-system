package com.banking.banking_system.dto;

import com.banking.banking_system.entities.enums.TransactionStatusEnum;

import java.math.BigDecimal;

public class TransactionResponse {
    private Long transactionId;
    private Long accountId;
    private TransactionStatusEnum transactionStatus;
    private BigDecimal currentBalance;

    public TransactionResponse() {
    }

    public TransactionResponse(Long transactionId, Long accountId, TransactionStatusEnum transactionStatus, BigDecimal currentBalance) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionStatus = transactionStatus;
        this.currentBalance = currentBalance;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public TransactionStatusEnum getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusEnum transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
