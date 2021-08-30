package com.dxc.eproc.estimate.response.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateSearchResponseDTO.
 */
public class WorkEstimateSearchResponseDTO implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The file number. */
	private String fileNumber;

	/** The work estimate number. */
	private String workEstimateNumber;

	/** The name. */
	private String name;

	/** The estimate total. */
	private BigDecimal estimateTotal;

	/** The status. */
	private WorkEstimateStatus status;

	/** The created by. */
	private String createdBy;

	/** The created ts. */
	private Instant createdTs;

	/**
	 * Gets the file number.
	 *
	 * @return the file number
	 */
	public String getFileNumber() {
		return fileNumber;
	}

	/**
	 * Sets the file number.
	 *
	 * @param fileNumber the new file number
	 */
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	/**
	 * Gets the work estimate number.
	 *
	 * @return the work estimate number
	 */
	public String getWorkEstimateNumber() {
		return workEstimateNumber;
	}

	/**
	 * Sets the work estimate number.
	 *
	 * @param workEstimateNumber the new work estimate number
	 */
	public void setWorkEstimateNumber(String workEstimateNumber) {
		this.workEstimateNumber = workEstimateNumber;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public WorkEstimateStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(WorkEstimateStatus status) {
		this.status = status;
	}

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
	 * Gets the created ts.
	 *
	 * @return the created ts
	 */
	public Instant getCreatedTs() {
		return createdTs;
	}

	/**
	 * Sets the created ts.
	 *
	 * @param createdTs the new created ts
	 */
	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

}
