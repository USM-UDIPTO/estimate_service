package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.dxc.eproc.estimate.enumeration.OverHeadType;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.domain.WorkEstimateOverhead}
 * entity.
 */
public class WorkEstimateOverheadDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate id. */
	private Long workEstimateId;

	/** The over head type. */
	@NotNull(message = "{workEstimateOverhead.overHeadType.NotNull}")
	private OverHeadType overHeadType;

	/** The name. */
	@Pattern(regexp = "^(?! )[A-Za-z0-9 \\s-+\\\\\\/&*+()',.]*(?<! )$", message = "{workEstimateOverhead.name.Pattern}")
	@NotBlank(message = "{workEstimateOverhead.name.NotBlank}")
	private String name;

	/** The over head value. */
	// @Pattern(regexp =
	// "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,6}(?:\\.\\d{1,4})?$", message =
	// "{workEstimateOverhead.overHeadValue.Pattern}")
	@NotNull(message = "{workEstimateOverhead.overHeadValue.NotNull}")
	private BigDecimal overHeadValue;

	/** The fixed yn. */
	@NotNull(message = "{workEstimateOverhead.fixedYn.NotNull}")
	private Boolean fixedYn;

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
	 * Gets the over head type.
	 *
	 * @return the over head type
	 */
	public OverHeadType getOverHeadType() {
		return overHeadType;
	}

	/**
	 * Sets the over head type.
	 *
	 * @param overHeadType the new over head type
	 */
	public void setOverHeadType(OverHeadType overHeadType) {
		this.overHeadType = overHeadType;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the over head value.
	 *
	 * @return the over head value
	 */
	public BigDecimal getOverHeadValue() {
		return overHeadValue;
	}

	/**
	 * Sets the over head value.
	 *
	 * @param overHeadValue the new over head value
	 */
	public void setOverHeadValue(BigDecimal overHeadValue) {
		this.overHeadValue = overHeadValue;
	}

	/**
	 * Gets the fixed yn.
	 *
	 * @return the fixed yn
	 */
	public Boolean getFixedYn() {
		return fixedYn;
	}

	/**
	 * Sets the fixed yn.
	 *
	 * @param fixedYn the new fixed yn
	 */
	public void setFixedYn(Boolean fixedYn) {
		this.fixedYn = fixedYn;
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
		if (!(o instanceof WorkEstimateOverheadDTO)) {
			return false;
		}

		WorkEstimateOverheadDTO workEstimateOverheadDTO = (WorkEstimateOverheadDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateOverheadDTO.id);
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
		return "WorkEstimateOverheadDTO{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId()
				+ ", overHeadType='" + getOverHeadType() + "'" + ", name='" + getName() + "'" + ", overHeadValue="
				+ getOverHeadValue() + ", fixedYn='" + getFixedYn() + "'" + "}";
	}
}
