package org.project.loja_virtual.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "BRAND_PRODUCT")
@SequenceGenerator(name = "SEQ_BRAND_PRODUCT", sequenceName = "SEQ_BRAND_PRODUCT", allocationSize = 1, initialValue = 1)
public class BrandProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BRAND_PRODUCT")
    private Long id;

    private String nameDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDescription() {
        return nameDescription;
    }

    public void setNameDescription(String nameDescription) {
        this.nameDescription = nameDescription;
    }
}
