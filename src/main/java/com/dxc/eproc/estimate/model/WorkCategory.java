package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * A WorkCategory.
 */
@Entity
@Table(name = "work_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkCategory extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The category name. */
	@NotNull
	@Column(name = "category_name", nullable = false)
	private String categoryName;

	/** The category code. */
	@NotNull
	@Column(name = "category_code", nullable = false)
	private String categoryCode;

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
	 * @return the work category
	 */
	public WorkCategory id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the category name.
	 *
	 * @return the category name
	 */
	public String getCategoryName() {
		return this.categoryName;
	}

	/**
	 * Category name.
	 *
	 * @param categoryName the category name
	 * @return the work category
	 */
	public WorkCategory categoryName(String categoryName) {
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
	 * Gets the category code.
	 *
	 * @return the category code
	 */
	public String getCategoryCode() {
		return this.categoryCode;
	}

	/**
	 * Category code.
	 *
	 * @param categoryCode the category code
	 * @return the work category
	 */
	public WorkCategory categoryCode(String categoryCode) {
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
	 * @return the work category
	 */
	public WorkCategory activeYn(Boolean activeYn) {
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
		if (!(o instanceof WorkCategory)) {
			return false;
		}
		return id != null && id.equals(((WorkCategory) o).id);
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
		return "WorkCategory{" + "id=" + getId() + ", categoryName='" + getCategoryName() + "'" + ", categoryCode='"
				+ getCategoryCode() + "'" + ", activeYn='" + getActiveYn() + "'" + "}";
	}
}
