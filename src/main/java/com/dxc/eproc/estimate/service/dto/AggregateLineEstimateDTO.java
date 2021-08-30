package com.dxc.eproc.estimate.service.dto;

import java.math.BigDecimal;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AggregateLineEstimateDTO.
 */
public class AggregateLineEstimateDTO {

	/** The estimated cost value. */
	private BigDecimal estimatedCostValue;

	/** The line estimates. */
	private List<LineEstimateDTO> lineEstimates;

	/**
	 * Gets the estimated cost value.
	 *
	 * @return the estimatedCostValue
	 */
	public BigDecimal getEstimatedCostValue() {
		return estimatedCostValue;
	}

	/**
	 * Sets the estimated cost value.
	 *
	 * @param estimatedCostValue the estimatedCostValue to set
	 */
	public void setEstimatedCostValue(BigDecimal estimatedCostValue) {
		this.estimatedCostValue = estimatedCostValue;
	}

	/**
	 * Gets the line estimates.
	 *
	 * @return the lineEstimates
	 */
	public List<LineEstimateDTO> getLineEstimates() {
		return lineEstimates;
	}

	/**
	 * Sets the line estimates.
	 *
	 * @param lineEstimates the lineEstimates to set
	 */
	public void setLineEstimates(List<LineEstimateDTO> lineEstimates) {
		this.lineEstimates = lineEstimates;
	}

}
