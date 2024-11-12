package org.project.loja_virtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SALE_ITEM")
@SequenceGenerator(name = "SEQ_SALE_ITEM", sequenceName = "SEQ_SALE_ITEM", allocationSize = 1, initialValue = 1)
public class SaleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALE_ITEM")
    private Long id;

    @Column(nullable = false)
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALE_ITEM_PRODUCT"))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "SALES_INVOICE_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALE_ITEM_SALES_INVOICE"))
    private SalesAndBuyOnlineStore salesAndBuyOnlineStore;

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SalesAndBuyOnlineStore getSalesAndBuyOnlineStore() {
        return salesAndBuyOnlineStore;
    }

    public void setSalesAndBuyOnlineStore(SalesAndBuyOnlineStore salesAndBuyOnlineStore) {
        this.salesAndBuyOnlineStore = salesAndBuyOnlineStore;
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
        SaleItem saleItem = (SaleItem) object;
        return Objects.equals(id, saleItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
