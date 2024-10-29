package org.project.loja_virtual.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "SALES_AND_BUY_ONLINE_STORE")
@SequenceGenerator(name = "SEQ_SALES_AND_BUY_ONLINE_STORE", sequenceName = "SEQ_SALES_AND_BUY_ONLINE_STORE", allocationSize = 1, initialValue = 1)
public class SalesAndBuyOnlineStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALES_AND_BUY_ONLINE_STORE")
    private Long id;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "PERSON_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALES_AND_BUY_ONLINE_STORE_PERSON"))
    private Person person;

    @ManyToOne
    @JoinColumn(name = "DELIVERY_ADDRESS_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALES_AND_BUY_ONLINE_STORE_DELIVERY_ADDRESS"))
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "BILLING_ADDRESS_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALES_AND_BUY_ONLINE_STORE_BILLING_ADDRESS"))
    private Address billingAddress;

    @Column(nullable = false)
    private BigDecimal totalValue;

    private BigDecimal discountValue;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_FORM_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALES_AND_BUY_ONLINE_STORE_PAYMENT_FORM"))
    private PaymentForm paymentForm;

    @OneToOne
    @JoinColumn(name = "SALES_INVOICE_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALES_AND_BUY_ONLINE_STORE_SALES_INVOICE"))
    private SalesInvoice salesInvoice;

    @ManyToOne
    @JoinColumn(name = "DISCOUNT_COUPON_ID",
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALES_AND_BUY_ONLINE_STORE_DISCOUNT_COUPON"))
    private DiscountCoupon discountCoupon;

    @Column(nullable = false)
    private BigDecimal shippingValue;

    @Column(nullable = false)
    private Integer shippingDays;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date salesDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
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

    public PaymentForm getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(PaymentForm paymentForm) {
        this.paymentForm = paymentForm;
    }

    public SalesInvoice getSalesInvoice() {
        return salesInvoice;
    }

    public void setSalesInvoice(SalesInvoice salesInvoice) {
        this.salesInvoice = salesInvoice;
    }

    public DiscountCoupon getDiscountCoupon() {
        return discountCoupon;
    }

    public void setDiscountCoupon(DiscountCoupon discountCoupon) {
        this.discountCoupon = discountCoupon;
    }

    public BigDecimal getShippingValue() {
        return shippingValue;
    }

    public void setShippingValue(BigDecimal shippingValue) {
        this.shippingValue = shippingValue;
    }

    public Integer getShippingDays() {
        return shippingDays;
    }

    public void setShippingDays(Integer shippingDays) {
        this.shippingDays = shippingDays;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SalesAndBuyOnlineStore that = (SalesAndBuyOnlineStore) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
