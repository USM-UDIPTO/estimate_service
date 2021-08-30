package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class AggregateOverheadsDTO.
 */
public class AggregateOverheadsDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The total overheads value. */
	private BigDecimal totalOverheadsValue;

	/** The total cost of work. */
	private BigDecimal totalCostOfWork;

	/** The overheads. */
	private List<WorkSubEstimateGroupOverheadDTO> overheads;

	/**
	 * Gets the total overheads value.
	 *
	 * @return the total overheads value
	 */
	public BigDecimal getTotalOverheadsValue() {
		return totalOverheadsValue;
	}

	/**
	 * Sets the total overheads value.
	 *
	 * @param totalOverheadsValue the new total overheads value
	 */
	public void setTotalOverheadsValue(BigDecimal totalOverheadsValue) {
		this.totalOverheadsValue = totalOverheadsValue;
	}

	/**
	 * Gets the total cost of work.
	 *
	 * @return the total cost of work
	 */
	public BigDecimal getTotalCostOfWork() {
		return totalCostOfWork;
	}

	/**
	 * Sets the total cost of work.
	 *
	 * @param totalCostOfWork the new total cost of work
	 */
	public void setTotalCostOfWork(BigDecimal totalCostOfWork) {
		this.totalCostOfWork = totalCostOfWork;
	}

	/**
	 * Gets the overheads.
	 *
	 * @return the overheads
	 */
	public List<WorkSubEstimateGroupOverheadDTO> getOverheads() {
		return overheads;
	}

	/**
	 * Sets the overheads.
	 *
	 * @param overheads the new overheads
	 */
	public void setOverheads(List<WorkSubEstimateGroupOverheadDTO> overheads) {
		this.overheads = overheads;
	}

}
