package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkCategory} entity.
 */
public class WorkCategoryDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The category name. */
	@NotNull
	private String categoryName;

	/** The category code. */
	@NotNull
	private String categoryCode;

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
	 * Gets the category name.
	 *
	 * @return the category name
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Sets the category name.
	 *
	 * @param categoryName the new category name
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * Gets the category code.
	 *
	 * @return the category code
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * Sets the category code.
	 *
	 * @param categoryCode the new category code
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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
		if (!(o instanceof WorkCategoryDTO)) {
			return false;
		}

		WorkCategoryDTO workCategoryDTO = (WorkCategoryDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workCategoryDTO.id);
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
		return "WorkCategoryDTO{" + "id=" + getId() + ", categoryName='" + getCategoryName() + "'" + ", categoryCode='"
				+ getCategoryCode() + "'" + ", activeYn='" + getActiveYn() + "'" + "}";
	}
}
