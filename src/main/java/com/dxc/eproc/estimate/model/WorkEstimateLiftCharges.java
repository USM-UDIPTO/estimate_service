package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEstimateLiftCharges.
 */
@Entity
@Table(name = "work_estimate_lift_charges")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateLiftCharges extends EProcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "work_estimate_item_id", nullable = false)
    private Long workEstimateItemId;

    @NotNull
    @Column(name = "material_master_id", nullable = false)
    private Long materialMasterId;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    @Column(name = "lift_distance", precision = 10, scale = 2, nullable = false)
    private BigDecimal liftDistance;

    @NotNull
    @Column(name = "lift_charges", precision = 10, scale = 2, nullable = false)
    private BigDecimal liftCharges;
    
    @NotNull
    @Column(name = "quantity", precision = 18, scale = 4, nullable = false)
    private BigDecimal quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkEstimateLiftCharges id(Long id) {
        this.id = id;
        return this;
    }

    public Long getWorkEstimateItemId() {
        return this.workEstimateItemId;
    }

    public WorkEstimateLiftCharges workEstimateItemId(Long workEstimateItemId) {
        this.workEstimateItemId = workEstimateItemId;
        return this;
    }

    public void setWorkEstimateItemId(Long workEstimateItemId) {
        this.workEstimateItemId = workEstimateItemId;
    }

    public Long getMaterialMasterId() {
        return this.materialMasterId;
    }

    public WorkEstimateLiftCharges materialMasterId(Long materialMasterId) {
        this.materialMasterId = materialMasterId;
        return this;
    }

    public void setMaterialMasterId(Long materialMasterId) {
        this.materialMasterId = materialMasterId;
    }

    public BigDecimal getLiftDistance() {
        return this.liftDistance;
    }

    public WorkEstimateLiftCharges liftDistance(BigDecimal liftDistance) {
        this.liftDistance = liftDistance;
        return this;
    }

    public void setLiftDistance(BigDecimal liftDistance) {
        this.liftDistance = liftDistance;
    }

    public BigDecimal getLiftCharges() {
        return this.liftCharges;
    }

    public WorkEstimateLiftCharges liftCharges(BigDecimal liftCharges) {
        this.liftCharges = liftCharges;
        return this;
    }

    public void setLiftCharges(BigDecimal liftcharges) {
        this.liftCharges = liftcharges;
    }

    public BigDecimal getQuantity() {
		return quantity;
	}

    public WorkEstimateLiftCharges quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkEstimateLiftCharges)) {
            return false;
        }
        return id != null && id.equals(((WorkEstimateLiftCharges) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEstimateLiftCharges{" +
            "id=" + getId() +
            ", workEstimateItemId=" + getWorkEstimateItemId() +
            ", materialMasterId=" + getMaterialMasterId() +
            ", liftDistance=" + getLiftDistance() +
            ", liftCharges=" + getLiftCharges() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
