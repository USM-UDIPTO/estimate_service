package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.SubEstimate} entity.
 */
public class SubEstimateDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate id. */
	private Long workEstimateId;

	/** The sor wor category id. */
	private Long sorWorCategoryId;

	/** The sub estimate name. */
	@NotNull
	@Size(min = 1, max = 512, message = "{subEstimate.subEstimateName.size}")
	@Pattern(regexp = "^(?! )[+,.\\\\/&A-Za-z0-9 \\s-]*(?<! )$", message = "{subEstimate.subEstimateName.pattern}")
	private String subEstimateName;

	/** The estimate total. */
	private BigDecimal estimateTotal;

	/** The area weightage id. */
	private Long areaWeightageId;

	/** The area weightage circle. */
	private Long areaWeightageCircle;

	/** The area weightage description. */
	private String areaWeightageDescription;

	/** The completed yn. */
	private Boolean completedYn;

	/** The work sub estimate group id. */
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
		return workEstimateId;
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
	 * Gets the sor wor category id.
	 *
	 * @return the sor wor category id
	 */
	public Long getSorWorCategoryId() {
		return sorWorCategoryId;
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
		return subEstimateName;
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
		return estimateTotal;
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
		return areaWeightageId;
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
		return areaWeightageCircle;
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
		return areaWeightageDescription;
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
		return completedYn;
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
		if (!(o instanceof SubEstimateDTO)) {
			return false;
		}

		SubEstimateDTO subEstimateDTO = (SubEstimateDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, subEstimateDTO.id);
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
		return "SubEstimateDTO{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId() + ", sorWorCategoryId="
				+ getSorWorCategoryId() + ", subEstimateName='" + getSubEstimateName() + "'" + ", estimateTotal="
				+ getEstimateTotal() + ", areaWeightageId=" + getAreaWeightageId() + ", areaWeightageCircle="
				+ getAreaWeightageCircle() + ", areaWeightageDescription='" + getAreaWeightageDescription() + "'"
				+ ", completedYn='" + getCompletedYn() + "'" + "}";
	}
}
