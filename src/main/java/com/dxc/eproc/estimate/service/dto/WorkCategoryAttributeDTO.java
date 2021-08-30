package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkCategoryAttribute}
 * entity.
 */
public class WorkCategoryAttributeDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The attribute name. */
	@NotNull
	private String attributeName;

	/** The work category id. */
	@NotNull
	private Long workCategoryId;

	/** The work type id. */
	@NotNull
	private Long workTypeId;

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
	 * Gets the attribute name.
	 *
	 * @return the attribute name
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * Sets the attribute name.
	 *
	 * @param attributeName the new attribute name
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
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
	 * Gets the work category id.
	 *
	 * @return the work category id
	 */
	public Long getWorkCategoryId() {
		return workCategoryId;
	}

	/**
	 * Sets the work category id.
	 *
	 * @param workCategoryId the new work category id
	 */
	public void setWorkCategoryId(Long workCategoryId) {
		this.workCategoryId = workCategoryId;
	}

	/**
	 * Gets the work type id.
	 *
	 * @return the work type id
	 */
	public Long getWorkTypeId() {
		return workTypeId;
	}

	/**
	 * Sets the work type id.
	 *
	 * @param workTypeId the new work type id
	 */
	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
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
		if (!(o instanceof WorkCategoryAttributeDTO)) {
			return false;
		}

		WorkCategoryAttributeDTO workCategoryAttributeDTO = (WorkCategoryAttributeDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workCategoryAttributeDTO.id);
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
		return "WorkCategoryAttributeDTO{" + "id=" + getId() + ", attributeName='" + getAttributeName() + "'"
				+ ", activeYn='" + getActiveYn() + "'" + ", workCategoryId=" + getWorkCategoryId() + ", workTypeId="
				+ getWorkTypeId() + "}";
	}
}
