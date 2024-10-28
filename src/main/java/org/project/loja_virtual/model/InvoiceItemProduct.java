package org.project.loja_virtual.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "INVOICE_ITEM_PRODUCT")
@SequenceGenerator(name = "SEQ_INVOICE_ITEM_PRODUCT", sequenceName = "SEQ_INVOICE_ITEM_PRODUCT", allocationSize = 1, initialValue = 1)
public class InvoiceItemProduct {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INVOICE_ITEM_PRODUCT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PURCHASE_INVOICE_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_INVOICE_ITEM_PRODUCT_PURCHASE_INVOICE"))
    private PurchaseInvoice purchaseInvoice;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_INVOICE_ITEM_PRODUCT_PRODUCT"))
    private Product product;

    @Column(nullable = false)
    private Double quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchaseInvoice getPurchaseInvoice() {
        return purchaseInvoice;
    }

    public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
        this.purchaseInvoice = purchaseInvoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        InvoiceItemProduct that = (InvoiceItemProduct) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
