package com.banking.banking_system.dto;

import com.banking.banking_system.entities.enums.AccountTypeEnum;
import com.banking.banking_system.entities.enums.CurrencyEnum;

import java.time.LocalDate;

public class CreateAccountRequest {

    private String legalPersonName;
    private LocalDate dateOfBirth;
    private String identityType;
    private String identityNumber;
    private String phoneNumber;
    private Address address;
    private Long branchId;
    private CurrencyEnum currency;
    private AccountTypeEnum accountType;

    protected CreateAccountRequest() {
    }

    public CreateAccountRequest(String legalPersonName, LocalDate dateOfBirth, String identityType, String identityNumber, String phoneNumber, Address address, Long branchId, CurrencyEnum currency, AccountTypeEnum accountType) {
        this.legalPersonName = legalPersonName;
        this.dateOfBirth = dateOfBirth;
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.branchId = branchId;
        this.currency = currency;
        this.accountType = accountType;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }
}
