package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.DeptEstimateType} entity.
 */
public class DeptEstimateTypeDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The dept id. */
	private Long deptId;

	/** The flow type. */
	@NotNull
	private String flowType;

	/** The active yn. */
	private Boolean activeYn;

	/** The estimate type id. */
	@NotNull
	private Long estimateTypeId;

	/** The estimate type value. */
	private String estimateTypeValue;

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
	 * Gets the dept id.
	 *
	 * @return the dept id
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * Sets the dept id.
	 *
	 * @param deptId the new dept id
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * Gets the flow type.
	 *
	 * @return the flow type
	 */
	public String getFlowType() {
		return flowType;
	}

	/**
	 * Sets the flow type.
	 *
	 * @param flowType the new flow type
	 */
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	/**
	 * Gets the active yn.
	 *
	 * @return the active yn
	 */
	public Boolean getActiveYn() {
		return activeYn;
	}

	/**
	 * Sets the active yn.
	 *
	 * @param activeYn the new active yn
	 */
	public void setActiveYn(Boolean activeYn) {
		this.activeYn = activeYn;
	}

	/**
	 * Gets the estimate type id.
	 *
	 * @return the estimate type id
	 */
	public Long getEstimateTypeId() {
		return estimateTypeId;
	}

	/**
	 * Sets the estimate type id.
	 *
	 * @param estimateTypeId the new estimate type id
	 */
	public void setEstimateTypeId(Long estimateTypeId) {
		this.estimateTypeId = estimateTypeId;
	}

	/**
	 * Gets the estimate type value.
	 *
	 * @return the estimate type value
	 */
	public String getEstimateTypeValue() {
		return estimateTypeValue;
	}

	/**
	 * Sets the estimate type value.
	 *
	 * @param estimateTypeValue the new estimate type value
	 */
	public void setEstimateTypeValue(String estimateTypeValue) {
		this.estimateTypeValue = estimateTypeValue;
	}

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof DeptEstimateTypeDTO)) {
			return false;
		}

		DeptEstimateTypeDTO deptEstimateTypeDTO = (DeptEstimateTypeDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, deptEstimateTypeDTO.id);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	// prettier-ignore
	@Override
	public String toString() {
		return "DeptEstimateTypeDTO{" + "id=" + getId() + ", deptId=" + getDeptId() + ", estimateTypeId="
				+ getEstimateTypeId() + ", flowType='" + getFlowType() + "'" + ", activeYn='" + getActiveYn() + "'"
				+ "}";
	}
}
