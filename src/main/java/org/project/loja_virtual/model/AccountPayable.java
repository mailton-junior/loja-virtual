package org.project.loja_virtual.model;

import org.project.loja_virtual.enums.AccountPayablesStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT_PAYABLE")
@SequenceGenerator(name = "SEQ_ACCOUNT_PAYABLE", sequenceName = "SEQ_ACCOUNT_PAYABLE", allocationSize = 1, initialValue = 1)
public class AccountPayable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACCOUNT_PAYABLE")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal totalValue;

    private BigDecimal discountValue;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountPayablesStatus accountPayablesStatus;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "supplier_person_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_PERSON"))
    private Person person;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SUPPLIER_PERSON"))
    private Person supplierPerson;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public AccountPayablesStatus getAccountPayablesStatus() {
        return accountPayablesStatus;
    }

    public void setAccountPayablesStatus(AccountPayablesStatus accountPayablesStatus) {
        this.accountPayablesStatus = accountPayablesStatus;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getSupplierPerson() {
        return supplierPerson;
    }

    public void setSupplierPerson(Person supplierPerson) {
        this.supplierPerson = supplierPerson;
    }

    public Person getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Person enterprise) {
        this.enterprise = enterprise;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AccountPayable that = (AccountPayable) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
