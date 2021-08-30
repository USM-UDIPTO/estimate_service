package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * A EstimateType.
 */
@Entity
@Table(name = "estimate_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EstimateType extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The estimate type value. */
	@NotNull
	@Column(name = "estimate_type_value", nullable = false)
	private String estimateTypeValue;

	/** The value type. */
	@NotNull
	@Column(name = "value_type", nullable = false)
	private String valueType;

	/** The active yn. */
	@NotNull
	@Column(name = "active_yn", nullable = false)
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
	 * Id.
	 *
	 * @param id the id
	 * @return the estimate type
	 */
	public EstimateType id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the estimate type value.
	 *
	 * @return the estimate type value
	 */
	public String getEstimateTypeValue() {
		return this.estimateTypeValue;
	}

	/**
	 * Estimate type value.
	 *
	 * @param estimateTypeValue the estimate type value
	 * @return the estimate type
	 */
	public EstimateType estimateTypeValue(String estimateTypeValue) {
		this.estimateTypeValue = estimateTypeValue;
		return this;
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
		return this.valueType;
	}

	/**
	 * Value type.
	 *
	 * @param valueType the value type
	 * @return the estimate type
	 */
	public EstimateType valueType(String valueType) {
		this.valueType = valueType;
		return this;
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
		return this.activeYn;
	}

	/**
	 * Active yn.
	 *
	 * @param activeYn the active yn
	 * @return the estimate type
	 */
	public EstimateType activeYn(Boolean activeYn) {
		this.activeYn = activeYn;
		return this;
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
		if (!(o instanceof EstimateType)) {
			return false;
		}
		return id != null && id.equals(((EstimateType) o).id);
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
		return "EstimateType{" + "id=" + getId() + ", estimateTypeValue='" + getEstimateTypeValue() + "'"
				+ ", valueType='" + getValueType() + "'" + ", activeYn='" + getActiveYn() + "'" + "}";
	}
}
