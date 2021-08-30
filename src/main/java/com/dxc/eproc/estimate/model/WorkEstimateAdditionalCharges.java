package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEstimateAdditionalCharges.
 */
@Entity
@Table(name = "work_estimate_additional_charges")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateAdditionalCharges extends EProcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "work_estimate_item_id", nullable = false)
    private Long workEstimateItemId;

    @NotNull
    @Column(name = "additional_charges_desc", nullable = false)
    private String additionalChargesDesc;

    @NotNull
    @Column(name = "additional_charges_rate", precision = 18, scale = 4, nullable = false)
    private BigDecimal additionalChargesRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkEstimateAdditionalCharges id(Long id) {
        this.id = id;
        return this;
    }

    public Long getWorkEstimateItemId() {
        return this.workEstimateItemId;
    }

    public WorkEstimateAdditionalCharges workEstimateItemId(Long workEstimateItemId) {
        this.workEstimateItemId = workEstimateItemId;
        return this;
    }

    public void setWorkEstimateItemId(Long workEstimateItemId) {
        this.workEstimateItemId = workEstimateItemId;
    }

    public String getAdditionalChargesDesc() {
        return this.additionalChargesDesc;
    }

    public WorkEstimateAdditionalCharges additionalChargesDesc(String additionalChargesDesc) {
        this.additionalChargesDesc = additionalChargesDesc;
        return this;
    }

    public void setAdditionalChargesDesc(String additionalChargesDesc) {
        this.additionalChargesDesc = additionalChargesDesc;
    }

    public BigDecimal getAdditionalChargesRate() {
        return this.additionalChargesRate;
    }

    public WorkEstimateAdditionalCharges additionalChargesRate(BigDecimal additionalChargesRate) {
        this.additionalChargesRate = additionalChargesRate;
        return this;
    }

    public void setAdditionalChargesRate(BigDecimal additionalChargesRate) {
        this.additionalChargesRate = additionalChargesRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkEstimateAdditionalCharges)) {
            return false;
        }
        return id != null && id.equals(((WorkEstimateAdditionalCharges) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEstimateAdditionalCharges{" +
            "id=" + getId() +
            ", workEstimateItemId=" + getWorkEstimateItemId() +
            ", additionalChargesDesc='" + getAdditionalChargesDesc() + "'" +
            ", additionalChargesRate=" + getAdditionalChargesRate() +
            "}";
    }
}
