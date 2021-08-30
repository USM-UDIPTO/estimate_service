package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * A WorkType.
 */
@Entity
@Table(name = "work_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkType extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The work type name. */
	@NotNull
	@Column(name = "work_type_name", nullable = false)
	private String workTypeName;

	/** The work type value. */
	@NotNull
	@Column(name = "work_type_value", nullable = false)
	private String workTypeValue;

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
	 * @return the work type
	 */
	public WorkType id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the work type name.
	 *
	 * @return the work type name
	 */
	public String getWorkTypeName() {
		return this.workTypeName;
	}

	/**
	 * Work type name.
	 *
	 * @param workTypeName the work type name
	 * @return the work type
	 */
	public WorkType workTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
		return this;
	}

	/**
	 * Sets the work type name.
	 *
	 * @param workTypeName the new work type name
	 */
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	/**
	 * Gets the work type value.
	 *
	 * @return the work type value
	 */
	public String getWorkTypeValue() {
		return this.workTypeValue;
	}

	/**
	 * Work type value.
	 *
	 * @param workTypeValue the work type value
	 * @return the work type
	 */
	public WorkType workTypeValue(String workTypeValue) {
		this.workTypeValue = workTypeValue;
		return this;
	}

	/**
	 * Sets the work type value.
	 *
	 * @param workTypeValue the new work type value
	 */
	public void setWorkTypeValue(String workTypeValue) {
		this.workTypeValue = workTypeValue;
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
	 * @return the work type
	 */
	public WorkType valueType(String valueType) {
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
	 * @return the work type
	 */
	public WorkType activeYn(Boolean activeYn) {
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
		if (!(o instanceof WorkType)) {
			return false;
		}
		return id != null && id.equals(((WorkType) o).id);
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
		return "WorkType{" + "id=" + getId() + ", workTypeName='" + getWorkTypeName() + "'" + ", workTypeValue='"
				+ getWorkTypeValue() + "'" + ", valueType='" + getValueType() + "'" + ", activeYn='" + getActiveYn()
				+ "'" + "}";
	}
}
