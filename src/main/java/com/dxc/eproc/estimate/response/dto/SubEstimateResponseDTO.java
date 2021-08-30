package com.dxc.eproc.estimate.response.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class SubEstimateResponseDTO.
 */
public class SubEstimateResponseDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The work estimate id. */
	private Long workEstimateId;

	/** The sub estimate id. */
	private Long subEstimateId;

	/** The work estimate total. */
	private BigDecimal workEstimateTotal;

	/** The sub estimate total. */
	private BigDecimal subEstimateTotal;

	/** The sub estimate SOR total. */
	private BigDecimal subEstimateSORTotal;

	/** The sub estimate non SOR total. */
	private BigDecimal subEstimateNonSORTotal;

	/** The items. */
	private List<WorkEstimateItemDTO> items;

	/**
	 * Gets the work estimate total.
	 *
	 * @return the work estimate total
	 */
	public BigDecimal getWorkEstimateTotal() {
		return workEstimateTotal;
	}

	/**
	 * Sets the work estimate total.
	 *
	 * @param workEstimateTotal the new work estimate total
	 */
	public void setWorkEstimateTotal(BigDecimal workEstimateTotal) {
		this.workEstimateTotal = workEstimateTotal;
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
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<WorkEstimateItemDTO> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(List<WorkEstimateItemDTO> items) {
		this.items = items;
	}

	/**
	 * Gets the sub estimate SOR total.
	 *
	 * @return the sub estimate SOR total
	 */
	public BigDecimal getSubEstimateSORTotal() {
		return subEstimateSORTotal;
	}

	/**
	 * Sets the sub estimate SOR total.
	 *
	 * @param subEstimateSORTotal the new sub estimate SOR total
	 */
	public void setSubEstimateSORTotal(BigDecimal subEstimateSORTotal) {
		this.subEstimateSORTotal = subEstimateSORTotal;
	}

	/**
	 * Gets the sub estimate non SOR total.
	 *
	 * @return the sub estimate non SOR total
	 */
	public BigDecimal getSubEstimateNonSORTotal() {
		return subEstimateNonSORTotal;
	}

	/**
	 * Sets the sub estimate non SOR total.
	 *
	 * @param subEstimateNonSORTotal the new sub estimate non SOR total
	 */
	public void setSubEstimateNonSORTotal(BigDecimal subEstimateNonSORTotal) {
		this.subEstimateNonSORTotal = subEstimateNonSORTotal;
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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "WorkEstimateItemResponseDTO [workEstimateId=" + workEstimateId + ", subEstimateId=" + subEstimateId
				+ ", workEstimateTotal=" + workEstimateTotal + ", subEstimateTotal=" + subEstimateTotal
				+ ", subEstimateSORTotal=" + subEstimateSORTotal + ", subEstimateNonSORTotal=" + subEstimateNonSORTotal
				+ ", items=" + items + "]";
	}

}
