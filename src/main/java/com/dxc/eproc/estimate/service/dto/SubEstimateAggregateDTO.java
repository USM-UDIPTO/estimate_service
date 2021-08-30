package com.dxc.eproc.estimate.service.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

// TODO: Auto-generated Javadoc
/**
 * The Class SubEstimateAggregateDTO.
 */
public class SubEstimateAggregateDTO {

	/** The sub estimate. */
	@NotNull
	private SubEstimateDTO subEstimate;

	/** The work locations. */
	// @NotEmpty
	private List<WorkLocationDTO> workLocations;

	/** The deleted work location ids. */
	private List<Long> deletedWorkLocationIds;

	/**
	 * Gets the sub estimate.
	 *
	 * @return the sub estimate
	 */
	public SubEstimateDTO getSubEstimate() {
		return subEstimate;
	}

	/**
	 * Sets the sub estimate.
	 *
	 * @param subEstimate the new sub estimate
	 */
	public void setSubEstimate(SubEstimateDTO subEstimate) {
		this.subEstimate = subEstimate;
	}

	/**
	 * Gets the work locations.
	 *
	 * @return the work locations
	 */
	public List<WorkLocationDTO> getWorkLocations() {
		return workLocations;
	}

	/**
	 * Sets the work locations.
	 *
	 * @param workLocations the new work locations
	 */
	public void setWorkLocations(List<WorkLocationDTO> workLocations) {
		this.workLocations = workLocations;
	}

	/**
	 * Gets the deleted work location ids.
	 *
	 * @return the deleted work location ids
	 */
	public List<Long> getDeletedWorkLocationIds() {
		return deletedWorkLocationIds;
	}

	/**
	 * Sets the deleted work location ids.
	 *
	 * @param deletedWorkLocationIds the new deleted work location ids
	 */
	public void setDeletedWorkLocationIds(List<Long> deletedWorkLocationIds) {
		this.deletedWorkLocationIds = deletedWorkLocationIds;
	}

}
