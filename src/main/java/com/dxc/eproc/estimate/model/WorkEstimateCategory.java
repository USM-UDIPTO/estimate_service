package com.dxc.eproc.estimate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateCategory.
 */
@Entity
@Table(name = "work_estimate_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
public class WorkEstimateCategory extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The sub estimate id. */
	@NotNull
	@Column(name = "sub_estimate_id", nullable = false)
	private Long subEstimateId;

	/** The category code. */
	@Column(name = "category_code")
	private String categoryCode;

	/** The category name. */
	@Column(name = "category_name")
	private String categoryName;

	/** The parent id. */
	@Column(name = "parent_id")
	private Long parentId;

	/** The reference id. */
	@Column(name = "reference_id")
	private Long referenceId;

	/** The item yn. */
	@Column(name = "item_yn")
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
	 * Id.
	 *
	 * @param id the id
	 * @return the work estimate category
	 */
	public WorkEstimateCategory id(Long id) {
		this.id = id;
		return this;
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
	 * Sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the work estimate category
	 */
	public WorkEstimateCategory subEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
		return this;
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
	 * Category code.
	 *
	 * @param categoryCode the category code
	 * @return the work estimate category
	 */
	public WorkEstimateCategory categoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
		return this;
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
	 * Category name.
	 *
	 * @param categoryName the category name
	 * @return the work estimate category
	 */
	public WorkEstimateCategory categoryName(String categoryName) {
		this.categoryName = categoryName;
		return this;
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
	 * Parent id.
	 *
	 * @param parentId the parent id
	 * @return the work estimate category
	 */
	public WorkEstimateCategory parentId(Long parentId) {
		this.parentId = parentId;
		return this;
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
	 * Reference id.
	 *
	 * @param referenceId the reference id
	 * @return the work estimate category
	 */
	public WorkEstimateCategory referenceId(Long referenceId) {
		this.referenceId = referenceId;
		return this;
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
	 * Item yn.
	 *
	 * @param itemYn the item yn
	 * @return the work estimate category
	 */
	public WorkEstimateCategory itemYn(Boolean itemYn) {
		this.itemYn = itemYn;
		return this;
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
		if (!(o instanceof WorkEstimateCategory)) {
			return false;
		}
		return id != null && id.equals(((WorkEstimateCategory) o).id);
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
	@Override
	public String toString() {
		return "WorkEstimateCategory [id=" + id + ", subEstimateId=" + subEstimateId + ", categoryCode=" + categoryCode
				+ ", categoryName=" + categoryName + ", parentId=" + parentId + ", referenceId=" + referenceId
				+ ", itemYn=" + itemYn + "]";
	}

}
