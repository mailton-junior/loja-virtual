package org.project.loja_virtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TRACKING_STATUS")
@SequenceGenerator(name = "SEQ_TRACKING_STATUS", sequenceName = "SEQ_TRACKING_STATUS", allocationSize = 1, initialValue = 1)
public class TrackingStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRACKING_STATUS")
    private Long id;

    private String distributionCenter;

    private String city;

    private String state;

    private String status;

    @ManyToOne
    @JoinColumn(name = "SALES_AND_BUY_ONLINE_STORE_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_TRACKING_STATUS_SALES_AND_BUY_ONLINE_STORE"))
    private SalesAndBuyOnlineStore salesAndBuyOnlineStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(String distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TrackingStatus that = (TrackingStatus) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
