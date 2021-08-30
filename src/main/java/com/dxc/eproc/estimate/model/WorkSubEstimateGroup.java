package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * A WorkSubEstimateGroup.
 */
@Entity
@Table(name = "work_sub_estimate_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkSubEstimateGroup extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The work estimate id. */
	@Column(name = "work_estimate_id", nullable = false)
	private Long workEstimateId;

	/** The description. */
	@Column(name = "description", nullable = false)
	private String description;

	/** The overhead total. */
	@Column(name = "overhead_total", precision = 21, scale = 4)
	private BigDecimal overheadTotal;

	/** The lumpsum total. */
	@Column(name = "lumpsum_total", precision = 21, scale = 4)
	private BigDecimal lumpsumTotal;

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
	 * @return the work sub estimate group
	 */
	public WorkSubEstimateGroup id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the work estimate id.
	 *
	 * @return the work estimate id
	 */
	public Long getWorkEstimateId() {
		return this.workEstimateId;
	}

	/**
	 * Work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the work sub estimate group
	 */
	public WorkSubEstimateGroup workEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
		return this;
	}

	/**
	 * Sets the work estimate id.
	 *
	 * @param workEstimateId the new work estimate id
	 */
	public void setWorkEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
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
	 * @return the work sub estimate group
	 */
	public WorkSubEstimateGroup description(String description) {
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
	 * Gets the overhead total.
	 *
	 * @return the overhead total
	 */
	public BigDecimal getOverheadTotal() {
		return this.overheadTotal;
	}

	/**
	 * Overhead total.
	 *
	 * @param overheadTotal the overhead total
	 * @return the work sub estimate group
	 */
	public WorkSubEstimateGroup overheadTotal(BigDecimal overheadTotal) {
		this.overheadTotal = overheadTotal;
		return this;
	}

	/**
	 * Sets the overhead total.
	 *
	 * @param overheadTotal the new overhead total
	 */
	public void setOverheadTotal(BigDecimal overheadTotal) {
		this.overheadTotal = overheadTotal;
	}

	/**
	 * Gets the lumpsum total.
	 *
	 * @return the lumpsum total
	 */
	public BigDecimal getLumpsumTotal() {
		return this.lumpsumTotal;
	}

	/**
	 * Lumpsum total.
	 *
	 * @param lumpsumTotal the lumpsum total
	 * @return the work sub estimate group
	 */
	public WorkSubEstimateGroup lumpsumTotal(BigDecimal lumpsumTotal) {
		this.lumpsumTotal = lumpsumTotal;
		return this;
	}

	/**
	 * Sets the lumpsum total.
	 *
	 * @param lumpsumTotal the new lumpsum total
	 */
	public void setLumpsumTotal(BigDecimal lumpsumTotal) {
		this.lumpsumTotal = lumpsumTotal;
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
		if (!(o instanceof WorkSubEstimateGroup)) {
			return false;
		}
		return id != null && id.equals(((WorkSubEstimateGroup) o).id);
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
		return "WorkSubEstimateGroup{" + "id=" + getId() + ", description='" + getDescription() + "'"
				+ ", overheadTotal=" + getOverheadTotal() + ", lumpsumTotal=" + getLumpsumTotal() + "}";
	}
}
