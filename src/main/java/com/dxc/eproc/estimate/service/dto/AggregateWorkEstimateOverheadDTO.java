package com.dxc.eproc.estimate.service.dto;

import java.math.BigDecimal;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AggregateWorkEstimateOverheadDTO.
 */
public class AggregateWorkEstimateOverheadDTO {

	/** The line estimate total. */
	private BigDecimal lineEstimateTotal;

	/** The normal overhead total. */
	private BigDecimal normalOverheadTotal;

	/** The additional overhead total. */
	private BigDecimal additionalOverheadTotal;

	/** The wor kestimate overheads. */
	private List<WorkEstimateOverheadDTO> normalWorkEstimateOverheadList;

	/** The additional work estimate overhead list. */
	private List<WorkEstimateOverheadDTO> additionalWorkEstimateOverheadList;

	/**
	 * Gets the normal overhead total.
	 *
	 * @return the normalOverheadTotal
	 */
	public BigDecimal getNormalOverheadTotal() {
		return normalOverheadTotal;
	}

	/**
	 * Sets the normal overhead total.
	 *
	 * @param normalOverheadTotal the normalOverheadTotal to set
	 */
	public void setNormalOverheadTotal(BigDecimal normalOverheadTotal) {
		this.normalOverheadTotal = normalOverheadTotal;
	}

	/**
	 * Gets the additional overhead total.
	 *
	 * @return the additionalOverheadTotal
	 */
	public BigDecimal getAdditionalOverheadTotal() {
		return additionalOverheadTotal;
	}

	/**
	 * Sets the additional overhead total.
	 *
	 * @param additionalOverheadTotal the additionalOverheadTotal to set
	 */
	public void setAdditionalOverheadTotal(BigDecimal additionalOverheadTotal) {
		this.additionalOverheadTotal = additionalOverheadTotal;
	}

	/**
	 * Gets the normal work estimate overhead list.
	 *
	 * @return the normal work estimate overhead list
	 */
	public List<WorkEstimateOverheadDTO> getNormalWorkEstimateOverheadList() {
		return normalWorkEstimateOverheadList;
	}

	/**
	 * Sets the normal work estimate overhead list.
	 *
	 * @param normalWorkEstimateOverheadList the new normal work estimate overhead
	 *                                       list
	 */
	public void setNormalWorkEstimateOverheadList(List<WorkEstimateOverheadDTO> normalWorkEstimateOverheadList) {
		this.normalWorkEstimateOverheadList = normalWorkEstimateOverheadList;
	}

	/**
	 * Gets the additional work estimate overhead list.
	 *
	 * @return the additional work estimate overhead list
	 */
	public List<WorkEstimateOverheadDTO> getAdditionalWorkEstimateOverheadList() {
		return additionalWorkEstimateOverheadList;
	}

	/**
	 * Sets the additional work estimate overhead list.
	 *
	 * @param additionalWorkEstimateOverheadList the new additional work estimate
	 *                                           overhead list
	 */
	public void setAdditionalWorkEstimateOverheadList(
			List<WorkEstimateOverheadDTO> additionalWorkEstimateOverheadList) {
		this.additionalWorkEstimateOverheadList = additionalWorkEstimateOverheadList;
	}

	/**
	 * Gets the line estimate total.
	 *
	 * @return the line estimate total
	 */
	public BigDecimal getLineEstimateTotal() {
		return lineEstimateTotal;
	}

	/**
	 * Sets the line estimate total.
	 *
	 * @param lineEstimateTotal the new line estimate total
	 */
	public void setLineEstimateTotal(BigDecimal lineEstimateTotal) {
		this.lineEstimateTotal = lineEstimateTotal;
	}

}
