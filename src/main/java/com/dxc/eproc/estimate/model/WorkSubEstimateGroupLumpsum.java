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
 * A WorkSubEstimateGroupLumpsum.
 */
@Entity
@Table(name = "work_sub_estimate_group_lumpsum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkSubEstimateGroupLumpsum extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The work sub estimate group id. */
	@Column(name = "work_sub_estimate_group_id", nullable = false)
	private Long workSubEstimateGroupId;

	/** The name. */
	@Column(name = "name", nullable = false)
	private String name;

	/** The approx rate. */
	@Column(name = "approx_rate", precision = 21, scale = 4, nullable = false)
	private BigDecimal approxRate;

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
	 * @return the work sub estimate group lumpsum
	 */
	public WorkSubEstimateGroupLumpsum id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the work sub estimate group id.
	 *
	 * @return the work sub estimate group id
	 */
	public Long getWorkSubEstimateGroupId() {
		return this.workSubEstimateGroupId;
	}

	/**
	 * Work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the work sub estimate group lumpsum
	 */
	public WorkSubEstimateGroupLumpsum workSubEstimateGroupId(Long workSubEstimateGroupId) {
		this.workSubEstimateGroupId = workSubEstimateGroupId;
		return this;
	}

	/**
	 * Sets the work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the new work sub estimate group id
	 */
	public void setWorkSubEstimateGroupId(Long workSubEstimateGroupId) {
		this.workSubEstimateGroupId = workSubEstimateGroupId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Name.
	 *
	 * @param name the name
	 * @return the work sub estimate group lumpsum
	 */
	public WorkSubEstimateGroupLumpsum name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the approx rate.
	 *
	 * @return the approx rate
	 */
	public BigDecimal getApproxRate() {
		return this.approxRate;
	}

	/**
	 * Approx rate.
	 *
	 * @param approxRate the approx rate
	 * @return the work sub estimate group lumpsum
	 */
	public WorkSubEstimateGroupLumpsum approxRate(BigDecimal approxRate) {
		this.approxRate = approxRate;
		return this;
	}

	/**
	 * Sets the approx rate.
	 *
	 * @param approxRate the new approx rate
	 */
	public void setApproxRate(BigDecimal approxRate) {
		this.approxRate = approxRate;
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
		if (!(o instanceof WorkSubEstimateGroupLumpsum)) {
			return false;
		}
		return id != null && id.equals(((WorkSubEstimateGroupLumpsum) o).id);
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
		return "WorkSubEstimateGroupLumpsum{" + "id=" + getId() + ", name='" + getName() + "'" + ", approxRate="
				+ getApproxRate() + "}";
	}
}
