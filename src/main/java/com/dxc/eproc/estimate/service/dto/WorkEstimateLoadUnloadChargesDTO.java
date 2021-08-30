package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the
 * {@link com.dxc.eproc.estimate.model.WorkEstimateLoadUnloadCharges} entity.
 */
public class WorkEstimateLoadUnloadChargesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long workEstimateId;

	private Long workEstimateItemId;

	@NotNull
	private Long catWorkSorItemId;

	@NotNull
	private Long materialMasterId;

	private Long subEstimateId;

	@NotNull
	private Boolean selectedLoadCharges;

	@NotNull
	private BigDecimal loadingCharges;

	@NotNull
	private Boolean selectedUnloadCharges;

	@NotNull
	private BigDecimal unloadingCharges;

	// Transient
	private String materialName;
	private BigDecimal quantity;
	private BigDecimal total;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkEstimateId() {
		return workEstimateId;
	}

	public void setWorkEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
	}

	public Long getWorkEstimateItemId() {
		return workEstimateItemId;
	}

	public void setWorkEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
	}

	public Long getCatWorkSorItemId() {
		return catWorkSorItemId;
	}

	public void setCatWorkSorItemId(Long catWorkSorItemId) {
		this.catWorkSorItemId = catWorkSorItemId;
	}

	public Long getMaterialMasterId() {
		return materialMasterId;
	}

	public void setMaterialMasterId(Long materialMasterId) {
		this.materialMasterId = materialMasterId;
	}

	public Long getSubEstimateId() {
		return subEstimateId;
	}

	public void setSubEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
	}

	public Boolean getSelectedLoadCharges() {
		return selectedLoadCharges;
	}

	public void setSelectedLoadCharges(Boolean selectedLoadCharges) {
		this.selectedLoadCharges = selectedLoadCharges;
	}

	public BigDecimal getLoadingCharges() {
		return loadingCharges;
	}

	public void setLoadingCharges(BigDecimal loadingCharges) {
		this.loadingCharges = loadingCharges;
	}

	public Boolean getSelectedUnloadCharges() {
		return selectedUnloadCharges;
	}

	public void setSelectedUnloadCharges(Boolean selectedUnloadCharges) {
		this.selectedUnloadCharges = selectedUnloadCharges;
	}

	public BigDecimal getUnloadingCharges() {
		return unloadingCharges;
	}

	public void setUnloadingCharges(BigDecimal unloadingCharges) {
		this.unloadingCharges = unloadingCharges;
	}

	// Transient
	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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
		if (!(o instanceof WorkEstimateLoadUnloadChargesDTO)) {
			return false;
		}

		WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = (WorkEstimateLoadUnloadChargesDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateLoadUnloadChargesDTO.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "WorkEstimateLoadUnloadChargesDTO{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId()
				+ ", workEstimateItemId=" + getWorkEstimateItemId() + ", catWorkSorItemId=" + getCatWorkSorItemId()
				+ ", materialMasterId=" + getMaterialMasterId() + ", loadUnloadRateMasterId=" + ", subEstimateId="
				+ getSubEstimateId() + ", selectedLoadCharges='" + getSelectedLoadCharges() + "'" + ", loadingCharges="
				+ getLoadingCharges() + ", selectedUnloadCharges='" + getSelectedUnloadCharges() + "'"
				+ ", unloadingCharges=" + getUnloadingCharges() + "}";
	}
}
