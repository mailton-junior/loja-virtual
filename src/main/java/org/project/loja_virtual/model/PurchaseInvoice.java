package org.project.loja_virtual.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "PURCHASE_INVOICE")
@SequenceGenerator(name = "SEQ_PURCHASE_INVOICE", sequenceName = "SEQ_PURCHASE_INVOICE", allocationSize = 1, initialValue = 1)
public class PurchaseInvoice {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PURCHASE_INVOICE")
    private Long id;

    private String invoiceNumber;

    private String invoiceSeries;

    private String Description;

    private BigDecimal totalValue;

    private BigDecimal discountValue;

    private BigDecimal ICMSValue;

    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "PERSON_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_PURCHASE_INVOICE_PERSON"))
    private Person person;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_PAYABLE_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_PURCHASE_INVOICE_ACCOUNT_PAYABLE"))
    private AccountPayable accountPayable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceSeries() {
        return invoiceSeries;
    }

    public void setInvoiceSeries(String invoiceSeries) {
        this.invoiceSeries = invoiceSeries;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public BigDecimal getICMSValue() {
        return ICMSValue;
    }

    public void setICMSValue(BigDecimal ICMSValue) {
        this.ICMSValue = ICMSValue;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public AccountPayable getAccountPayable() {
        return accountPayable;
    }

    public void setAccountPayable(AccountPayable accountPayable) {
        this.accountPayable = accountPayable;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PurchaseInvoice that = (PurchaseInvoice) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
