package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateCategoryDTO.
 */
public class WorkEstimateCategoryDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The sub estimate id. */
	private Long subEstimateId;

	/** The category code. */
	private String categoryCode;

	/** The category name. */
	private String categoryName;

	/** The parent id. */
	private Long parentId;

	/** The reference id. */
	private Long referenceId;

	/** The item yn. */
	private Boolean itemYn;

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
	 * Gets the parent id.
	 *
	 * @return the parent id
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent id.
	 *
	 * @param parentId the new parent id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the reference id.
	 *
	 * @return the reference id
	 */
	public Long getReferenceId() {
		return referenceId;
	}

	/**
	 * Sets the reference id.
	 *
	 * @param referenceId the new reference id
	 */
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * Gets the item yn.
	 *
	 * @return the item yn
	 */
	public Boolean getItemYn() {
		return itemYn;
	}

	/**
	 * Sets the item yn.
	 *
	 * @param itemYn the new item yn
	 */
	public void setItemYn(Boolean itemYn) {
		this.itemYn = itemYn;
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
		if (!(o instanceof WorkEstimateCategoryDTO)) {
			return false;
		}

		WorkEstimateCategoryDTO workCategoryDTO = (WorkEstimateCategoryDTO) o;
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
	@Override
	public String toString() {
		return "WorkEstimateCategoryDTO [id=" + id + ", subEstimateId=" + subEstimateId + ", categoryCode="
				+ categoryCode + ", categoryName=" + categoryName + ", parentId=" + parentId + ", referenceId="
				+ referenceId + ", itemYn=" + itemYn + "]";
	}

}
