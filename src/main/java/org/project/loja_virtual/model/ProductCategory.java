package org.project.loja_virtual.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PRODUCT_CATEGORY")
@SequenceGenerator(name = "SEQ_PRODUCT_CATEGORY", sequenceName = "SEQ_PRODUCT_CATEGORY", allocationSize = 1, initialValue = 1)
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_CATEGORY")
    private Long id;

    @Column(nullable = false)
    private String nameDescription;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "ENTERPRISE_ID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_ENTERPRISE_ID"))
    private Person enterprise;

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

    public Person getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Person enterprise) {
        this.enterprise = enterprise;
    }
}
