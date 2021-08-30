package com.dxc.eproc.estimate.response.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateItemLBDResponseDTO.
 */
public class WorkEstimateItemLBDResponseDTO {

	/** The work estimate id. */
	private Long workEstimateId;

	/** The sub estimate id. */
	private Long subEstimateId;

	/** The work estimate item id. */
	private Long workEstimateItemId;

	/** The lbd total. */
	private BigDecimal lbdTotal;

	/** The lbds. */
	private List<WorkEstimateItemLBDDTO> lbds;

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
	 * Gets the work estimate item id.
	 *
	 * @return the work estimate item id
	 */
	public Long getWorkEstimateItemId() {
		return workEstimateItemId;
	}

	/**
	 * Sets the work estimate item id.
	 *
	 * @param workEstimateItemId the new work estimate item id
	 */
	public void setWorkEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
	}

	/**
	 * Gets the lbds.
	 *
	 * @return the lbds
	 */
	public List<WorkEstimateItemLBDDTO> getLbds() {
		return lbds;
	}

	/**
	 * Sets the lbds.
	 *
	 * @param lbds the new lbds
	 */
	public void setLbds(List<WorkEstimateItemLBDDTO> lbds) {
		this.lbds = lbds;
	}

	/**
	 * Gets the lbd total.
	 *
	 * @return the lbd total
	 */
	public BigDecimal getLbdTotal() {
		return lbdTotal;
	}

	/**
	 * Sets the lbd total.
	 *
	 * @param lbdTotal the new lbd total
	 */
	public void setLbdTotal(BigDecimal lbdTotal) {
		this.lbdTotal = lbdTotal;
	}

}
