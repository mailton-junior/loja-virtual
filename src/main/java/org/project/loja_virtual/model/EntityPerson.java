package org.project.loja_virtual.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "ENTITY_PERSON")
@PrimaryKeyJoinColumn(name = "ID")
public class EntityPerson extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private String stateRegistration;

    private String municipalRegistration;

    @Column(nullable = false)
    private String fantasyName;

    @Column(nullable = false)
    private String companyName;

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getMunicipalRegistration() {
        return municipalRegistration;
    }

    public void setMunicipalRegistration(String municipalRegistration) {
        this.municipalRegistration = municipalRegistration;
    }

    public String getStateRegistration() {
        return stateRegistration;
    }

    public void setStateRegistration(String stateRegistration) {
        this.stateRegistration = stateRegistration;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
