package com.banking.banking_system.entities;

import com.banking.banking_system.dto.Address;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @OneToOne
    @JoinColumn(name = "legal_person_id", nullable = false, unique = true)
    private LegalPersonEntity legalPerson;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    protected AddressEntity() {
    }

    public AddressEntity(LegalPersonEntity legalPerson, String street, String city, String state, String postalCode, String country, boolean isPrimary) {
        this.legalPerson = legalPerson;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.isPrimary = isPrimary;
    }

    public Long getAddressId() {
        return addressId;
    }

    public LegalPersonEntity getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(LegalPersonEntity legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
