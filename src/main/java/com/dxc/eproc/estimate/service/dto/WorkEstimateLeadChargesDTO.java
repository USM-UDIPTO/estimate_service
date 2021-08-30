package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkEstimateLeadCharges}
 * entity.
 */
public class WorkEstimateLeadChargesDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate id. */
	private Long workEstimateId;

	/** The work estimate item id. */
	private Long workEstimateItemId;

	/** The cat work sor item id. */
	@NotNull
	private Long catWorkSorItemId;

	/** The material master id. */
	@NotNull
	private Long materialMasterId;

	/** The sub estimate id. */
	private Long subEstimateId;

	/** The quarry. */
	@Size(max = 50)
	private String quarry;

	/** The lead in M. */
	@DecimalMin(value = "0")
	@DecimalMax(value = "10000")
	private BigDecimal leadInM;

	/** The lead in km. */
	@DecimalMin(value = "0")
	@DecimalMax(value = "10000")
	private BigDecimal leadInKm;

	/** The lead charges. */
	private BigDecimal leadCharges;

	/** The initial lead required yn. */
	@NotNull
	private boolean initialLeadRequiredYn;

	// Transient
	private String materialName;
	private BigDecimal quantity;
	private BigDecimal total;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the work estimate id.
	 *
	 * @return the work estimate id
	 */
	public Long getWorkEstimateId() {
		return workEstimateId;
	}

	/**
	 * Sets the work estimate id.
	 *
	 * @param workEstimateId the new work estimate id
	 */
	public void setWorkEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
	}

	/**
	 * Gets the work estimate item id.
	 *
	 * @return the work estimate item id
	 */
	public Long getWorkEstimateItemId() {
		return workEstimateItemId;
	}

	/**
	 * Sets the work estimate item id.
	 *
	 * @param workEstimateItemId the new work estimate item id
	 */
	public void setWorkEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
	}

	/**
	 * Gets the cat work sor item id.
	 *
	 * @return the cat work sor item id
	 */
	public Long getCatWorkSorItemId() {
		return catWorkSorItemId;
	}

	/**
	 * Sets the cat work sor item id.
	 *
	 * @param catWorkSorItemId the new cat work sor item id
	 */
	public void setCatWorkSorItemId(Long catWorkSorItemId) {
		this.catWorkSorItemId = catWorkSorItemId;
	}

	/**
	 * Gets the material master id.
	 *
	 * @return the material master id
	 */
	public Long getMaterialMasterId() {
		return materialMasterId;
	}

	/**
	 * Sets the material master id.
	 *
	 * @param materialMasterId the new material master id
	 */
	public void setMaterialMasterId(Long materialMasterId) {
		this.materialMasterId = materialMasterId;
	}

	/**
	 * Gets the sub estimate id.
	 *
	 * @return the sub estimate id
	 */
	public Long getSubEstimateId() {
		return subEstimateId;
	}

	/**
	 * Sets the sub estimate id.
	 *
	 * @param subEstimateId the new sub estimate id
	 */
	public void setSubEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
	}

	/**
	 * Gets the quarry.
	 *
	 * @return the quarry
	 */
	public String getQuarry() {
		return quarry;
	}

	/**
	 * Sets the quarry.
	 *
	 * @param quarry the new quarry
	 */
	public void setQuarry(String quarry) {
		this.quarry = quarry;
	}

	/**
	 * Gets the lead in M.
	 *
	 * @return the lead in M
	 */
	public BigDecimal getLeadInM() {
		return leadInM;
	}

	/**
	 * Sets the lead in M.
	 *
	 * @param leadInM the new lead in M
	 */
	public void setLeadInM(BigDecimal leadInM) {
		this.leadInM = leadInM;
	}

	/**
	 * Gets the lead in km.
	 *
	 * @return the lead in km
	 */
	public BigDecimal getLeadInKm() {
		return leadInKm;
	}

	/**
	 * Sets the lead in km.
	 *
	 * @param leadInKm the new lead in km
	 */
	public void setLeadInKm(BigDecimal leadInKm) {
		this.leadInKm = leadInKm;
	}

	/**
	 * Gets the lead charges.
	 *
	 * @return the lead charges
	 */
	public BigDecimal getLeadCharges() {
		return leadCharges;
	}

	/**
	 * Sets the lead charges.
	 *
	 * @param leadCharges the new lead charges
	 */
	public void setLeadCharges(BigDecimal leadCharges) {
		this.leadCharges = leadCharges;
	}

	/**
	 * Gets the initial lead required yn.
	 *
	 * @return the initial lead required yn
	 */
	public boolean getInitialLeadRequiredYn() {
		return initialLeadRequiredYn;
	}

	/**
	 * Sets the initial lead required yn.
	 *
	 * @param initialLeadRequiredYn the new initial lead required yn
	 */
	public void setInitialLeadRequiredYn(boolean initialLeadRequiredYn) {
		this.initialLeadRequiredYn = initialLeadRequiredYn;
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

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WorkEstimateLeadChargesDTO)) {
			return false;
		}

		WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = (WorkEstimateLeadChargesDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateLeadChargesDTO.id);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	// prettier-ignore
	@Override
	public String toString() {
		return "WorkEstimateLeadChargesDTO{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId()
				+ ", workEstimateItemId=" + getWorkEstimateItemId() + ", catWorkSorItemId=" + getCatWorkSorItemId()
				+ ", materialMasterId=" + getMaterialMasterId() + ", subEstimateId=" + getSubEstimateId() + ", quarry='"
				+ getQuarry() + "'" + ", leadInM=" + getLeadInM() + ", leadInKm=" + getLeadInKm() + ", leadCharges="
				+ getLeadCharges() + ", initialLeadRequiredYn='" + getInitialLeadRequiredYn() + "'" + "}";
	}
}
