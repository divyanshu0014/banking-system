package com.banking.banking_system.dto;

import com.banking.banking_system.entities.enums.AccountStatusEnum;
import com.banking.banking_system.entities.enums.AccountTypeEnum;
import com.banking.banking_system.entities.enums.CurrencyEnum;

import java.math.BigDecimal;

public class AccountResponse {

    private Long accountId;
    private String accountNumber;
    private String accountHolderName;
    private AccountTypeEnum accountType;
    private AccountStatusEnum accountStatus;
    private Long branchId;
    private String branchName;
    private BigDecimal currentBalance;
    private CurrencyEnum currency;
    private HolderIdentityDetails holderIdentityDetails;


    public AccountResponse() {
    }

    public AccountResponse(Long accountId, String accountNumber, String accountHolderName, AccountTypeEnum accountType, AccountStatusEnum accountStatus, Long branchId, String branchName, BigDecimal currentBalance, CurrencyEnum currency, HolderIdentityDetails holderIdentityDetails) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.branchId = branchId;
        this.branchName = branchName;
        this.currentBalance = currentBalance;
        this.currency = currency;
        this.holderIdentityDetails = holderIdentityDetails;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public AccountStatusEnum getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatusEnum accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public HolderIdentityDetails getHolderIdentityDetails() {
        return holderIdentityDetails;
    }

    public void setHolderIdentityDetails(HolderIdentityDetails holderIdentityDetails) {
        this.holderIdentityDetails = holderIdentityDetails;
    }
}
