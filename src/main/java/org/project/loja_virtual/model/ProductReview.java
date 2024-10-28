package org.project.loja_virtual.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT_REVIEW")
@SequenceGenerator(name = "SEQ_PRODUCT_REVIEW", sequenceName = "SEQ_PRODUCT_REVIEW", allocationSize = 1, initialValue = 1)
public class ProductReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_REVIEW")
    private Long id;

    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_PRODUCT_REVIEW_PERSON"))
    private Person person;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_PRODUCT_REVIEW_PRODUCT"))
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
        ProductReview that = (ProductReview) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
