package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * A SubEstimate.
 */
@Entity
@Table(name = "sub_estimate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
public class SubEstimate extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The work estimate id. */
	@NotNull
	@Column(name = "work_estimate_id", nullable = false)
	private Long workEstimateId;

	/** The sor wor category id. */
	@Column(name = "sor_wor_category_id")
	private Long sorWorCategoryId;

	/** The sub estimate name. */
	@NotNull
	@Column(name = "sub_estimate_name", nullable = false)
	private String subEstimateName;

	/** The estimate total. */
	@Column(name = "estimate_total", precision = 21, scale = 4)
	private BigDecimal estimateTotal;

	/** The area weightage id. */
	@Column(name = "area_weightage_id")
	private Long areaWeightageId;

	/** The area weightage circle. */
	@Column(name = "area_weightage_circle")
	private Long areaWeightageCircle;

	/** The area weightage description. */
	@Column(name = "area_weightage_description")
	private String areaWeightageDescription;

	/** The completed yn. */
	@Column(name = "completed_yn")
	private Boolean completedYn;

	/** The work sub estimate group id. */
	@Column(name = "work_sub_estimate_group_id")
	private Long workSubEstimateGroupId;

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
	 * @return the sub estimate
	 */
	public SubEstimate workEstimateId(Long workEstimateId) {
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
	 * Id.
	 *
	 * @param id the id
	 * @return the sub estimate
	 */
	public SubEstimate id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the sor wor category id.
	 *
	 * @return the sor wor category id
	 */
	public Long getSorWorCategoryId() {
		return this.sorWorCategoryId;
	}

	/**
	 * Sor wor category id.
	 *
	 * @param sorWorCategoryId the sor wor category id
	 * @return the sub estimate
	 */
	public SubEstimate sorWorCategoryId(Long sorWorCategoryId) {
		this.sorWorCategoryId = sorWorCategoryId;
		return this;
	}

	/**
	 * Sets the sor wor category id.
	 *
	 * @param sorWorCategoryId the new sor wor category id
	 */
	public void setSorWorCategoryId(Long sorWorCategoryId) {
		this.sorWorCategoryId = sorWorCategoryId;
	}

	/**
	 * Gets the sub estimate name.
	 *
	 * @return the sub estimate name
	 */
	public String getSubEstimateName() {
		return this.subEstimateName;
	}

	/**
	 * Sub estimate name.
	 *
	 * @param subEstimateName the sub estimate name
	 * @return the sub estimate
	 */
	public SubEstimate subEstimateName(String subEstimateName) {
		this.subEstimateName = subEstimateName;
		return this;
	}

	/**
	 * Sets the sub estimate name.
	 *
	 * @param subEstimateName the new sub estimate name
	 */
	public void setSubEstimateName(String subEstimateName) {
		this.subEstimateName = subEstimateName;
	}

	/**
	 * Gets the estimate total.
	 *
	 * @return the estimate total
	 */
	public BigDecimal getEstimateTotal() {
		return this.estimateTotal;
	}

	/**
	 * Estimate total.
	 *
	 * @param estimateTotal the estimate total
	 * @return the sub estimate
	 */
	public SubEstimate estimateTotal(BigDecimal estimateTotal) {
		this.estimateTotal = estimateTotal;
		return this;
	}

	/**
	 * Sets the estimate total.
	 *
	 * @param estimateTotal the new estimate total
	 */
	public void setEstimateTotal(BigDecimal estimateTotal) {
		this.estimateTotal = estimateTotal;
	}

	/**
	 * Gets the area weightage id.
	 *
	 * @return the area weightage id
	 */
	public Long getAreaWeightageId() {
		return this.areaWeightageId;
	}

	/**
	 * Area weightage id.
	 *
	 * @param areaWeightageId the area weightage id
	 * @return the sub estimate
	 */
	public SubEstimate areaWeightageId(Long areaWeightageId) {
		this.areaWeightageId = areaWeightageId;
		return this;
	}

	/**
	 * Sets the area weightage id.
	 *
	 * @param areaWeightageId the new area weightage id
	 */
	public void setAreaWeightageId(Long areaWeightageId) {
		this.areaWeightageId = areaWeightageId;
	}

	/**
	 * Gets the area weightage circle.
	 *
	 * @return the area weightage circle
	 */
	public Long getAreaWeightageCircle() {
		return this.areaWeightageCircle;
	}

	/**
	 * Area weightage circle.
	 *
	 * @param areaWeightageCircle the area weightage circle
	 * @return the sub estimate
	 */
	public SubEstimate areaWeightageCircle(Long areaWeightageCircle) {
		this.areaWeightageCircle = areaWeightageCircle;
		return this;
	}

	/**
	 * Sets the area weightage circle.
	 *
	 * @param areaWeightageCircle the new area weightage circle
	 */
	public void setAreaWeightageCircle(Long areaWeightageCircle) {
		this.areaWeightageCircle = areaWeightageCircle;
	}

	/**
	 * Gets the area weightage description.
	 *
	 * @return the area weightage description
	 */
	public String getAreaWeightageDescription() {
		return this.areaWeightageDescription;
	}

	/**
	 * Area weightage description.
	 *
	 * @param areaWeightageDescription the area weightage description
	 * @return the sub estimate
	 */
	public SubEstimate areaWeightageDescription(String areaWeightageDescription) {
		this.areaWeightageDescription = areaWeightageDescription;
		return this;
	}

	/**
	 * Sets the area weightage description.
	 *
	 * @param areaWeightageDescription the new area weightage description
	 */
	public void setAreaWeightageDescription(String areaWeightageDescription) {
		this.areaWeightageDescription = areaWeightageDescription;
	}

	/**
	 * Gets the completed yn.
	 *
	 * @return the completed yn
	 */
	public Boolean getCompletedYn() {
		return this.completedYn;
	}

	/**
	 * Completed yn.
	 *
	 * @param completedYn the completed yn
	 * @return the sub estimate
	 */
	public SubEstimate completedYn(Boolean completedYn) {
		this.completedYn = completedYn;
		return this;
	}

	/**
	 * Sets the completed yn.
	 *
	 * @param completedYn the new completed yn
	 */
	public void setCompletedYn(Boolean completedYn) {
		this.completedYn = completedYn;
	}

	/**
	 * Gets the work sub estimate group id.
	 *
	 * @return the work sub estimate group id
	 */
	public Long getWorkSubEstimateGroupId() {
		return workSubEstimateGroupId;
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
		if (!(o instanceof SubEstimate)) {
			return false;
		}
		return id != null && id.equals(((SubEstimate) o).id);
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
		return "SubEstimate{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId() + ", sorWorCategoryId="
				+ getSorWorCategoryId() + ", subEstimateName='" + getSubEstimateName() + "'" + ", estimateTotal="
				+ getEstimateTotal() + ", areaWeightageId=" + getAreaWeightageId() + ", areaWeightageCircle="
				+ getAreaWeightageCircle() + ", areaWeightageDescription='" + getAreaWeightageDescription() + "'"
				+ ", completedYn='" + getCompletedYn() + "'" + "}";
	}
}
