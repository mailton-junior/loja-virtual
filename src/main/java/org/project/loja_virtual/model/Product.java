package org.project.loja_virtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT")
@SequenceGenerator(name = "SEQ_PRODUCT", sequenceName = "SEQ_PRODUCT", allocationSize = 1, initialValue = 1)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT")
    private Long id;

    @Column(nullable = false)
    private String UnityType;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", length = 2000, nullable = false)
    private String description;

    // TODO - Add Nota Item Produto

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double depth;

    @Column(nullable = false)
    private BigDecimal SalePrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private Integer stockQuantityStock = 0;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "ENTERPRISE_ID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_ENTERPRISE_ID"))
    private Person enterprise;

    private Integer alertQuantityStock = 0;

    private String linkYoutube;

    private Boolean alertQuantityStockEnabled =  Boolean.FALSE;

    private Integer clickCount = 0;

    private Boolean active = Boolean.TRUE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnityType() {
        return UnityType;
    }

    public void setUnityType(String unityType) {
        UnityType = unityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public BigDecimal getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        SalePrice = salePrice;
    }

    public Integer getStockQuantityStock() {
        return stockQuantityStock;
    }

    public void setStockQuantityStock(Integer stockQuantityStock) {
        this.stockQuantityStock = stockQuantityStock;
    }

    public Integer getAlertQuantityStock() {
        return alertQuantityStock;
    }

    public void setAlertQuantityStock(Integer alertQuantityStock) {
        this.alertQuantityStock = alertQuantityStock;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public Boolean getAlertQuantityStockEnabled() {
        return alertQuantityStockEnabled;
    }

    public void setAlertQuantityStockEnabled(Boolean alertQuantityStockEnabled) {
        this.alertQuantityStockEnabled = alertQuantityStockEnabled;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        Product product = (Product) object;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
