package org.project.loja_virtual.model;

import jakarta.persistence.*;
import org.project.loja_virtual.enums.AddressType;

import java.util.Objects;

@Entity
@Table(name = "ADDRESS")
@SequenceGenerator(name = "SEQ_ADDRESS", sequenceName = "SEQ_ADDRESS", allocationSize = 1, initialValue = 1)
public class Address {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS")
    private Long id;

    private String street;

    private String number;

    private String cep;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ADDRESS_PERSON"))
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Address address = (Address) object;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
