package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;

public class WorkEstimateSearchDTO implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The work estimate number. */

	private String workEstimateNumber;

	/** The status. */
	@NotNull
	private List<WorkEstimateStatus> workEstimateStatusList;

	/** The file number. */

	private String fileNumber;

	/** The name. */

	private String name;

	public String getWorkEstimateNumber() {
		return workEstimateNumber;
	}

	public void setWorkEstimateNumber(String workEstimateNumber) {
		this.workEstimateNumber = workEstimateNumber;
	}

	public List<WorkEstimateStatus> getWorkEstimateStatusList() {
		return workEstimateStatusList;
	}

	public void setWorkEstimateStatusList(List<WorkEstimateStatus> workEstimateStatusList) {
		this.workEstimateStatusList = workEstimateStatusList;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
