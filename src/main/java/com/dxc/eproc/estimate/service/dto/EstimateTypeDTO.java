package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.EstimateType} entity.
 */
public class EstimateTypeDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The estimate type value. */
	@NotNull
	private String estimateTypeValue;

	/** The value type. */
	@NotNull
	private String valueType;

	/** The active yn. */
	private Boolean activeYn;

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
	 * Gets the estimate type value.
	 *
	 * @return the estimate type value
	 */
	public String getEstimateTypeValue() {
		return estimateTypeValue;
	}

	/**
	 * Sets the estimate type value.
	 *
	 * @param estimateTypeValue the new estimate type value
	 */
	public void setEstimateTypeValue(String estimateTypeValue) {
		this.estimateTypeValue = estimateTypeValue;
	}

	/**
	 * Gets the value type.
	 *
	 * @return the value type
	 */
	public String getValueType() {
		return valueType;
	}

	/**
	 * Sets the value type.
	 *
	 * @param valueType the new value type
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	/**
	 * Gets the active yn.
	 *
	 * @return the active yn
	 */
	public Boolean getActiveYn() {
		return activeYn;
	}

	/**
	 * Sets the active yn.
	 *
	 * @param activeYn the new active yn
	 */
	public void setActiveYn(Boolean activeYn) {
		this.activeYn = activeYn;
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
		if (!(o instanceof EstimateTypeDTO)) {
			return false;
		}

		EstimateTypeDTO estimateTypeDTO = (EstimateTypeDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, estimateTypeDTO.id);
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
		return "EstimateTypeDTO{" + "id=" + getId() + ", estimateTypeValue='" + getEstimateTypeValue() + "'"
				+ ", valueType='" + getValueType() + "'" + ", activeYn='" + getActiveYn() + "'" + "}";
	}
}
