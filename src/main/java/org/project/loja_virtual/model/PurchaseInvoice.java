package org.project.loja_virtual.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

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
}
