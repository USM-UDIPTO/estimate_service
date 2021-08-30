package com.dxc.eproc.estimate.response.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateResponseDTO.
 */
public class WorkEstimateResponseDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The work estimate id. */
	private Long workEstimateId;

	/** The work estimate ECV. */
	private BigDecimal workEstimateECV;

	/** The sub estimate total. */
	private BigDecimal subEstimateTotal;

	/** The lump sum total. */
	private BigDecimal lumpsumTotal;

	/** The overhead total. */
	private BigDecimal overheadTotal;

	/** The sub estimates. */
	private List<SubEstimateDTO> subEstimates;

	/**
	 * Gets the work estimate ECV.
	 *
	 * @return the work estimate ECV
	 */
	public BigDecimal getWorkEstimateECV() {
		return workEstimateECV;
	}

	/**
	 * Sets the work estimate ECV.
	 *
	 * @param workEstimateECV the new work estimate ECV
	 */
	public void setWorkEstimateECV(BigDecimal workEstimateECV) {
		this.workEstimateECV = workEstimateECV;
	}

	/**
	 * Gets the sub estimate total.
	 *
	 * @return the sub estimate total
	 */
	public BigDecimal getSubEstimateTotal() {
		return subEstimateTotal;
	}

	/**
	 * Sets the sub estimate total.
	 *
	 * @param subEstimateTotal the new sub estimate total
	 */
	public void setSubEstimateTotal(BigDecimal subEstimateTotal) {
		this.subEstimateTotal = subEstimateTotal;
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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "WorkEstimateResponseDTO [workEstimateId=" + workEstimateId + ", workEstimateECV=" + workEstimateECV
				+ ", subEstimateTotal=" + subEstimateTotal + ", lumpsumTotal=" + lumpsumTotal + ", overheadTotal="
				+ overheadTotal + ", subEstimates=" + subEstimates + "]";
	}

}
