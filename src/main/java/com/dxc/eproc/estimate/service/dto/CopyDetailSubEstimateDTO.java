package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;

public class CopyDetailSubEstimateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long subEstimateId;

	private boolean copyLBD;

	private boolean copyRA;

	public Long getSubEstimateId() {
		return subEstimateId;
	}

	public void setSubEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
	}

	public boolean isCopyLBD() {
		return copyLBD;
	}

	public void setCopyLBD(boolean copyLBD) {
		this.copyLBD = copyLBD;
	}

	public boolean isCopyRA() {
		return copyRA;
	}

	public void setCopyRA(boolean copyRA) {
		this.copyRA = copyRA;
	}

}
