package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkEstimateRoyaltyCharges}
 * entity.
 */
public class WorkEstimateRoyaltyChargesDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate id. */
	private Long workEstimateId;

	/** The sub estimate id. */
	private Long subEstimateId;

	/** The work estimate item id. */
	private Long workEstimateItemId;

	/** The cat work sor item id. */
	@NotNull
	private Long catWorkSorItemId;

	/** The material master id. */
	@NotNull
	private Long materialMasterId;

	/** The sr royalty charges. */
	@NotNull
	private BigDecimal srRoyaltyCharges;

	/** The prevailing royalty charges. */
	@NotNull
	@DecimalMin(value = "0")
	@DecimalMax(value = "100000000")
	private BigDecimal prevailingRoyaltyCharges;

	/** The density factor. */
	@NotNull
	private BigDecimal densityFactor;

	/** The difference. */
	private BigDecimal difference;

	// Transient
	private String materialName;
	private BigDecimal quantity;
	private Long uomId;
	private String uomName;
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
	 * Gets the sr royalty charges.
	 *
	 * @return the sr royalty charges
	 */
	public BigDecimal getSrRoyaltyCharges() {
		return srRoyaltyCharges;
	}

	/**
	 * Sets the sr royalty charges.
	 *
	 * @param srRoyaltyCharges the new sr royalty charges
	 */
	public void setSrRoyaltyCharges(BigDecimal srRoyaltyCharges) {
		this.srRoyaltyCharges = srRoyaltyCharges;
	}

	/**
	 * Gets the prevailing royalty charges.
	 *
	 * @return the prevailing royalty charges
	 */
	public BigDecimal getPrevailingRoyaltyCharges() {
		return prevailingRoyaltyCharges;
	}

	/**
	 * Sets the prevailing royalty charges.
	 *
	 * @param prevailingRoyaltyCharges the new prevailing royalty charges
	 */
	public void setPrevailingRoyaltyCharges(BigDecimal prevailingRoyaltyCharges) {
		this.prevailingRoyaltyCharges = prevailingRoyaltyCharges;
	}

	/**
	 * Gets the density factor.
	 *
	 * @return the density factor
	 */
	public BigDecimal getDensityFactor() {
		return densityFactor;
	}

	/**
	 * Sets the density factor.
	 *
	 * @param densityFactor the new density factor
	 */
	public void setDensityFactor(BigDecimal densityFactor) {
		this.densityFactor = densityFactor;
	}

	/**
	 * Gets the difference.
	 *
	 * @return the difference
	 */
	public BigDecimal getDifference() {
		return difference;
	}

	/**
	 * Sets the difference.
	 *
	 * @param difference the new difference
	 */
	public void setDifference(BigDecimal difference) {
		this.difference = difference;
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
		if (!(o instanceof WorkEstimateRoyaltyChargesDTO)) {
			return false;
		}

		WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = (WorkEstimateRoyaltyChargesDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateRoyaltyChargesDTO.id);
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
		return "WorkEstimateRoyaltyChargesDTO{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId()
				+ ", workEstimateItemId=" + getWorkEstimateItemId() + ", catWorkSorItemId=" + getCatWorkSorItemId()
				+ ", materialMasterId=" + getMaterialMasterId() + ", subEstimateId=" + getSubEstimateId()
				+ ", srRoyaltyCharges=" + getSrRoyaltyCharges() + ", prevailingRoyaltyCharges="
				+ getPrevailingRoyaltyCharges() + ", densityFactor=" + getDensityFactor() + ", difference="
				+ getDifference() + "}";
	}
}
