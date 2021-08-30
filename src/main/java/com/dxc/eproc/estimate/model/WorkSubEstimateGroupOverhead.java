package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dxc.eproc.estimate.enumeration.ValueType;

// TODO: Auto-generated Javadoc
/**
 * A WorkSubEstimateGroupOverhead.
 */
@Entity
@Table(name = "work_sub_estimate_group_overhead")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkSubEstimateGroupOverhead extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The work sub estimate group id. */
	@Column(name = "work_sub_estimate_group_id", nullable = false)
	private Long workSubEstimateGroupId;

	/** The description. */
	@Column(name = "description", nullable = false)
	private String description;

	/** The code. */
	@Column(name = "code", nullable = false)
	private String code;

	/** The value type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "value_type", nullable = false)
	private ValueType valueType;

	/** The entered value. */
	@Column(name = "entered_value", precision = 21, scale = 4)
	private BigDecimal enteredValue;

	/** The value fixed yn. */
	@Column(name = "value_fixed_yn", nullable = false)
	private Boolean valueFixedYn;

	/** The construct. */
	@Column(name = "construct")
	private String construct;

	/** The overhead value. */
	@Column(name = "overhead_value", precision = 21, scale = 4, nullable = false)
	private BigDecimal overheadValue;

	/** The final yn. */
	@Column(name = "final_yn", nullable = false)
	private Boolean finalYn;

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
	 * Id.
	 *
	 * @param id the id
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the work sub estimate group id.
	 *
	 * @return the work sub estimate group id
	 */
	public Long getWorkSubEstimateGroupId() {
		return this.workSubEstimateGroupId;
	}

	/**
	 * Work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead workSubEstimateGroupId(Long workSubEstimateGroupId) {
		this.workSubEstimateGroupId = workSubEstimateGroupId;
		return this;
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
		return this.description;
	}

	/**
	 * Description.
	 *
	 * @param description the description
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead description(String description) {
		this.description = description;
		return this;
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
		return this.code;
	}

	/**
	 * Code.
	 *
	 * @param code the code
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead code(String code) {
		this.code = code;
		return this;
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
		return this.valueType;
	}

	/**
	 * Value type.
	 *
	 * @param valueType the value type
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead valueType(ValueType valueType) {
		this.valueType = valueType;
		return this;
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
		return this.enteredValue;
	}

	/**
	 * Entered value.
	 *
	 * @param enteredValue the entered value
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead enteredValue(BigDecimal enteredValue) {
		this.enteredValue = enteredValue;
		return this;
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
		return this.valueFixedYn;
	}

	/**
	 * Value fixed yn.
	 *
	 * @param valueFixedYn the value fixed yn
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead valueFixedYn(Boolean valueFixedYn) {
		this.valueFixedYn = valueFixedYn;
		return this;
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
		return this.construct;
	}

	/**
	 * Construct.
	 *
	 * @param construct the construct
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead construct(String construct) {
		this.construct = construct;
		return this;
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
		return this.overheadValue;
	}

	/**
	 * Overhead value.
	 *
	 * @param overheadValue the overhead value
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead overheadValue(BigDecimal overheadValue) {
		this.overheadValue = overheadValue;
		return this;
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
		return this.finalYn;
	}

	/**
	 * Final yn.
	 *
	 * @param finalYn the final yn
	 * @return the work sub estimate group overhead
	 */
	public WorkSubEstimateGroupOverhead finalYn(Boolean finalYn) {
		this.finalYn = finalYn;
		return this;
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
		if (!(o instanceof WorkSubEstimateGroupOverhead)) {
			return false;
		}
		return id != null && id.equals(((WorkSubEstimateGroupOverhead) o).id);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		// see
		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	// prettier-ignore
	@Override
	public String toString() {
		return "WorkSubEstimateGroupOverhead{" + "id=" + getId() + ", description='" + getDescription() + "'"
				+ ", code='" + getCode() + "'" + ", valueType='" + getValueType() + "'" + ", enteredValue="
				+ getEnteredValue() + ", valueFixedYn='" + getValueFixedYn() + "'" + ", construct='" + getConstruct()
				+ "'" + ", overheadValue=" + getOverheadValue() + ", finalYn='" + getFinalYn() + "'" + "}";
	}
}
