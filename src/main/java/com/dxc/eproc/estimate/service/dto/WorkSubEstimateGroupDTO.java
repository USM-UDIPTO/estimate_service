package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroup}
 * entity.
 */
public class WorkSubEstimateGroupDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate id. */
	private Long workEstimateId;

	/** The description. */
	@NotBlank(message = "{workSubEstimateGroup.description.notBlank}")
	@Size(max = 255, message = "{workSubEstimateGroup.description.size}")
	private String description;

	/** The overhead total. */
	private BigDecimal overheadTotal;

	/** The lumpsum total. */
	private BigDecimal lumpsumTotal;

	/** The sub estimate ids. */
	private List<Long> subEstimateIds;

	/** The sub estimates. */
	private List<SubEstimateDTO> subEstimates;

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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
		return overheadTotal;
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
		return lumpsumTotal;
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
	 * Gets the sub estimate ids.
	 *
	 * @return the sub estimate ids
	 */
	public List<Long> getSubEstimateIds() {
		return subEstimateIds;
	}

	/**
	 * Sets the sub estimate ids.
	 *
	 * @param subEstimateIds the new sub estimate ids
	 */
	public void setSubEstimateIds(List<Long> subEstimateIds) {
		this.subEstimateIds = subEstimateIds;
	}

	/**
	 * Gets the sub estimates.
	 *
	 * @return the sub estimates
	 */
	public List<SubEstimateDTO> getSubEstimates() {
		return subEstimates;
	}

	/**
	 * Sets the sub estimates.
	 *
	 * @param subEstimates the new sub estimates
	 */
	public void setSubEstimates(List<SubEstimateDTO> subEstimates) {
		this.subEstimates = subEstimates;
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
		if (!(o instanceof WorkSubEstimateGroupDTO)) {
			return false;
		}

		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = (WorkSubEstimateGroupDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workSubEstimateGroupDTO.id);
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
		return "WorkSubEstimateGroupDTO{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId()
				+ ", description='" + getDescription() + "'" + ", overheadTotal=" + getOverheadTotal()
				+ ", lumpsumTotal=" + getLumpsumTotal() + "}";
	}
}
