package com.banking.banking_system.dto;

import com.banking.banking_system.entities.enums.TransactionTypeEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransactionRequest {
    @NotNull(message = "Transaction amount is required")
    @DecimalMin(value = "0.00", inclusive = false,
            message = "Transaction amount must be greater than 0")
    private BigDecimal amount;

    private String currency;

    @NotNull(message = "Transaction type is required")
    private TransactionTypeEnum transactionType;

    private Long destinationAccount;

    public  TransactionRequest() {}

    public TransactionRequest(BigDecimal amount, String currency, @NotNull TransactionTypeEnum transactionType, Long destinationAccount) {
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public Long getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Long destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
