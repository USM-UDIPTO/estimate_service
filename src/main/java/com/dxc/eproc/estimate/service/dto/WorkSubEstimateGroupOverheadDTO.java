package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.dxc.eproc.estimate.enumeration.ValueType;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the
 * {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead} entity.
 */
public class WorkSubEstimateGroupOverheadDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work sub estimate group id. */
	private Long workSubEstimateGroupId;

	/** The description. */
	@NotBlank(message = "{workSubEstimateGroupOverhead.description.notBlank}")
	@Size(max = 255, message = "{workSubEstimateGroupOverhead.description.size}")
	@Pattern(regexp = "^(?! )['+\\\\/&,.A-Za-z0-9 s-]*(?<! )$", message = "{workSubEstimateGroupOverhead.description.pattern}")
	private String description;

	/** The code. */
	private String code;

	/** The value type. */
	private ValueType valueType;

	/** The entered value. */
	@NotNull(message = "{workSubEstimateGroupOverhead.enteredValue.notNull}")
	private BigDecimal enteredValue;

	/** The value fixed yn. */
	@NotNull(message = "{workSubEstimateGroupOverhead.valueFixedYn.notNull}")
	private Boolean valueFixedYn;

	/** The construct. */
	private String construct;

	/** The overhead value. */
	private BigDecimal overheadValue;

	/** The final yn. */
	@NotNull(message = "{workSubEstimateGroupOverhead.finalYn.notNull}")
	private Boolean finalYn;

	/** The selected overheads. */
	private List<Long> selectedOverheads;

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
	 * Gets the work sub estimate group id.
	 *
	 * @return the work sub estimate group id
	 */
	public Long getWorkSubEstimateGroupId() {
		return workSubEstimateGroupId;
	}

	/**
	 * Sets the work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the new work sub estimate group id
	 */
	public void setWorkSubEstimateGroupId(Long workSubEstimateGroupId) {
		this.workSubEstimateGroupId = workSubEstimateGroupId;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the value type.
	 *
	 * @return the value type
	 */
	public ValueType getValueType() {
		return valueType;
	}

	/**
	 * Sets the value type.
	 *
	 * @param valueType the new value type
	 */
	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	/**
	 * Gets the entered value.
	 *
	 * @return the entered value
	 */
	public BigDecimal getEnteredValue() {
		return enteredValue;
	}

	/**
	 * Sets the entered value.
	 *
	 * @param enteredValue the new entered value
	 */
	public void setEnteredValue(BigDecimal enteredValue) {
		this.enteredValue = enteredValue;
	}

	/**
	 * Gets the value fixed yn.
	 *
	 * @return the value fixed yn
	 */
	public Boolean getValueFixedYn() {
		return valueFixedYn;
	}

	/**
	 * Sets the value fixed yn.
	 *
	 * @param valueFixedYn the new value fixed yn
	 */
	public void setValueFixedYn(Boolean valueFixedYn) {
		this.valueFixedYn = valueFixedYn;
	}

	/**
	 * Gets the construct.
	 *
	 * @return the construct
	 */
	public String getConstruct() {
		return construct;
	}

	/**
	 * Sets the construct.
	 *
	 * @param construct the new construct
	 */
	public void setConstruct(String construct) {
		this.construct = construct;
	}

	/**
	 * Gets the overhead value.
	 *
	 * @return the overhead value
	 */
	public BigDecimal getOverheadValue() {
		return overheadValue;
	}

	/**
	 * Sets the overhead value.
	 *
	 * @param overheadValue the new overhead value
	 */
	public void setOverheadValue(BigDecimal overheadValue) {
		this.overheadValue = overheadValue;
	}

	/**
	 * Gets the final yn.
	 *
	 * @return the final yn
	 */
	public Boolean getFinalYn() {
		return finalYn;
	}

	/**
	 * Sets the final yn.
	 *
	 * @param finalYn the new final yn
	 */
	public void setFinalYn(Boolean finalYn) {
		this.finalYn = finalYn;
	}

	/**
	 * Gets the selected overheads.
	 *
	 * @return the selected overheads
	 */
	public List<Long> getSelectedOverheads() {
		return selectedOverheads;
	}

	/**
	 * Sets the selected overheads.
	 *
	 * @param selectedOverheads the new selected overheads
	 */
	public void setSelectedOverheads(List<Long> selectedOverheads) {
		this.selectedOverheads = selectedOverheads;
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
		if (!(o instanceof WorkSubEstimateGroupOverheadDTO)) {
			return false;
		}

		WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO = (WorkSubEstimateGroupOverheadDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workSubEstimateGroupOverheadDTO.id);
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
		return "WorkSubEstimateGroupOverheadDTO{" + "id=" + getId() + ", workSubEstimateGroupId="
				+ getWorkSubEstimateGroupId() + ", description='" + getDescription() + "'" + ", code='" + getCode()
				+ "'" + ", valueType='" + getValueType() + "'" + ", enteredValue=" + getEnteredValue()
				+ ", valueFixedYn='" + getValueFixedYn() + "'" + ", construct='" + getConstruct() + "'"
				+ ", overheadValue=" + getOverheadValue() + ", finalYn='" + getFinalYn() + "'" + "}";
	}
}
