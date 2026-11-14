package com.banking.banking_system.dto;

import com.banking.banking_system.entities.enums.VerificationStatusEnum;

public class HolderIdentityDetails {
    private String identityType;
    private String identityNumber;
    private VerificationStatusEnum verificationStatus;

    public HolderIdentityDetails(String identityType, String identityNumber, VerificationStatusEnum verificationStatus) {
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.verificationStatus = verificationStatus;
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

    public VerificationStatusEnum getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatusEnum verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
