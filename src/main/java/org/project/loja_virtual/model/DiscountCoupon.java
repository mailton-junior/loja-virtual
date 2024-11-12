package org.project.loja_virtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "DISCOUNT_COUPON")
@SequenceGenerator(name = "SEQ_DISCOUNT_COUPON", sequenceName = "SEQ_DISCOUNT_COUPON", allocationSize = 1, initialValue = 1)
public class DiscountCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISCOUNT_COUPON")
    private Long id;

    @Column(nullable = false)
    private String codeDescription;

    private BigDecimal discountValue;

    private BigDecimal porcentageDiscount;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

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

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getPorcentageDiscount() {
        return porcentageDiscount;
    }

    public void setPorcentageDiscount(BigDecimal porcentageDiscount) {
        this.porcentageDiscount = porcentageDiscount;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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
        DiscountCoupon that = (DiscountCoupon) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
