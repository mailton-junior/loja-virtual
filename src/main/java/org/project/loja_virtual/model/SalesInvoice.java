package org.project.loja_virtual.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SALES_INVOICE")
@SequenceGenerator(name = "SEQ_SALES_INVOICE", sequenceName = "SEQ_SALES_INVOICE", allocationSize = 1, initialValue = 1)
public class SalesInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALES_INVOICE")
    private Long id;

    @Column(nullable = false)
    private String invoiceNumber;

    @Column(nullable = false)
    private String invoiceSeries;

    @Column(nullable = false)
    private String invoiceType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String xml;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pdf;

    @OneToOne
    @JoinColumn(name = "SALES_AND_BUY_ONLINE_STORE_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_SALES_INVOICE_SALES_AND_BUY_ONLINE_STORE"))
    private SalesAndBuyOnlineStore salesAndBuyOnlineStore;

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

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public SalesAndBuyOnlineStore getSalesAndBuyOnlineStore() {
        return salesAndBuyOnlineStore;
    }

    public void setSalesAndBuyOnlineStore(SalesAndBuyOnlineStore salesAndBuyOnlineStore) {
        this.salesAndBuyOnlineStore = salesAndBuyOnlineStore;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SalesInvoice that = (SalesInvoice) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
