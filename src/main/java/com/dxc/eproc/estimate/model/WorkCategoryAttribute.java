package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * A WorkCategoryAttribute.
 */
@Entity
@Table(name = "work_category_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkCategoryAttribute extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The attribute name. */
	@NotNull
	@Column(name = "attribute_name", nullable = false)
	private String attributeName;

	/** The active yn. */
	@NotNull
	@Column(name = "active_yn", nullable = false)
	private Boolean activeYn;

	/** The work category id. */
	@NotNull
	@Column(name = "work_category_id", nullable = false)
	private Long workCategoryId;

	/** The work type id. */
	@NotNull
	@Column(name = "work_type_id", nullable = false)
	private Long workTypeId;

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
	 * @return the work category attribute
	 */
	public WorkCategoryAttribute id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the attribute name.
	 *
	 * @return the attribute name
	 */
	public String getAttributeName() {
		return this.attributeName;
	}

	/**
	 * Attribute name.
	 *
	 * @param attributeName the attribute name
	 * @return the work category attribute
	 */
	public WorkCategoryAttribute attributeName(String attributeName) {
		this.attributeName = attributeName;
		return this;
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
	 * Gets the work category id.
	 *
	 * @return the work category id
	 */
	public Long getWorkCategoryId() {
		return this.workCategoryId;
	}

	/**
	 * Work category id.
	 *
	 * @param workCategoryId the work category id
	 * @return the work category attribute
	 */
	public WorkCategoryAttribute workCategoryId(Long workCategoryId) {
		this.workCategoryId = workCategoryId;
		return this;
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
		return this.workTypeId;
	}

	/**
	 * Work type id.
	 *
	 * @param workTypeId the work type id
	 * @return the work category attribute
	 */
	public WorkCategoryAttribute workTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
		return this;
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
	 * @return the work category attribute
	 */
	public WorkCategoryAttribute activeYn(Boolean activeYn) {
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
		if (!(o instanceof WorkCategoryAttribute)) {
			return false;
		}
		return id != null && id.equals(((WorkCategoryAttribute) o).id);
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
		return "WorkCategoryAttribute{" + "id=" + getId() + ", attributeName='" + getAttributeName() + "'"
				+ ", activeYn='" + getActiveYn() + "'" + "}";
	}
}
