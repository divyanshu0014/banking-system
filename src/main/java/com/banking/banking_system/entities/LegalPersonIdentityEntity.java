package com.banking.banking_system.entities;

import com.banking.banking_system.entities.enums.VerificationStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "legal_person_identity")
public class LegalPersonIdentityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "legal_person_identity_id")
    private Long legalPersonIdentityId;

    @Column(name = "identity_type", nullable = false)
    private String identityType;

    @Column(name = "identity_number", unique = true, nullable = false)
    private String identityNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private VerificationStatusEnum verificationStatus;

    protected LegalPersonIdentityEntity() {
    }

    public LegalPersonIdentityEntity(String identityType, String identityNumber, VerificationStatusEnum verificationStatus) {
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.verificationStatus = verificationStatus;
    }

    public Long getLegalPersonIdentityId() {
        return legalPersonIdentityId;
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
