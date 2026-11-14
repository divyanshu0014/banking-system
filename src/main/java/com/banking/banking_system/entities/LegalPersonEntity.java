package com.banking.banking_system.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "legal_person")
public class LegalPersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "legal_person_id")
    private Long legalPersonId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToOne()
    @JoinColumn(name = "legal_person_identity_id", nullable = false, unique = true)
    private LegalPersonIdentityEntity legalPersonIdentity;

    @OneToOne(mappedBy = "legalPerson")
    private AddressEntity address;

    @Column(name = "phone_number")
    private String phoneNumber;

    protected LegalPersonEntity() {
    }

    public LegalPersonEntity(String name, LocalDate dateOfBirth, LegalPersonIdentityEntity legalPersonIdentity, String phoneNumber) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.legalPersonIdentity = legalPersonIdentity;
        this.phoneNumber = phoneNumber;
    }

    public Long getLegalPersonId() {
        return legalPersonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LegalPersonIdentityEntity getLegalPersonIdentity() {
        return legalPersonIdentity;
    }

    public void setLegalPersonIdentity(LegalPersonIdentityEntity legalPersonIdentity) {
        this.legalPersonIdentity = legalPersonIdentity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
