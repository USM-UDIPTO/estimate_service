package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dxc.eproc.estimate.domain.WorkEstimateLiftCharges}
 * entity.
 */
public class WorkEstimateLiftChargesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long workEstimateItemId;

	@NotNull
	private Long materialMasterId;

	@NotNull
	@DecimalMin(value = "0")
	@DecimalMax(value = "100000000")
	private BigDecimal liftDistance;

	@NotNull
	private BigDecimal liftCharges;

	@NotNull
	private BigDecimal quantity;

	// Transient
	private String materialName;
	private Long uomId;
	private String uomName;
	private BigDecimal total;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkEstimateItemId() {
		return workEstimateItemId;
	}

	public void setWorkEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
	}

	public Long getMaterialMasterId() {
		return materialMasterId;
	}

	public void setMaterialMasterId(Long materialMasterId) {
		this.materialMasterId = materialMasterId;
	}

	public BigDecimal getLiftDistance() {
		return liftDistance;
	}

	public void setLiftDistance(BigDecimal liftDistance) {
		this.liftDistance = liftDistance;
	}

	public BigDecimal getLiftCharges() {
		return liftCharges;
	}

	public void setLiftCharges(BigDecimal liftCharges) {
		this.liftCharges = liftCharges;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	// Transient
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Long getUomId() {
		return uomId;
	}

	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WorkEstimateLiftChargesDTO)) {
			return false;
		}

		WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = (WorkEstimateLiftChargesDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateLiftChargesDTO.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "WorkEstimateLiftChargesDTO{" + "id=" + getId() + ", workEstimateItemId=" + getWorkEstimateItemId()
				+ ", materialMasterId=" + getMaterialMasterId() + ", liftDistance=" + getLiftDistance()
				+ ", liftCharges=" + getLiftCharges() + ", quantity=" + getQuantity() + "}";
	}
}
