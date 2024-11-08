package org.project.loja_virtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT_IMAGE")
@SequenceGenerator(name = "SEQ_PRODUCT_IMAGE", sequenceName = "SEQ_PRODUCT_IMAGE", allocationSize = 1, initialValue = 1)
public class ProductImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_IMAGE")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String originalImage;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String smallImage;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_PRODUCT_IMAGE_PRODUCT"))
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProductImage that = (ProductImage) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
