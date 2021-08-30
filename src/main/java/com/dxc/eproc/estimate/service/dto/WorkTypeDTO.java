package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkType} entity.
 */
public class WorkTypeDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work type name. */
	@NotNull
	private String workTypeName;

	/** The work type value. */
	@NotNull
	private String workTypeValue;

	/** The value type. */
	@NotNull
	private String valueType;

	/** The active yn. */
	@NotNull
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
	 * Gets the work type name.
	 *
	 * @return the work type name
	 */
	public String getWorkTypeName() {
		return workTypeName;
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
		return workTypeValue;
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
		if (!(o instanceof WorkTypeDTO)) {
			return false;
		}

		WorkTypeDTO workTypeDTO = (WorkTypeDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workTypeDTO.id);
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
		return "WorkTypeDTO{" + "id=" + getId() + ", workTypeName='" + getWorkTypeName() + "'" + ", workTypeValue='"
				+ getWorkTypeValue() + "'" + ", valueType='" + getValueType() + "'" + ", activeYn='" + getActiveYn()
				+ "'" + "}";
	}
}
