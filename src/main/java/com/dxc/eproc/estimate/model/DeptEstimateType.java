package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * A DeptEstimateType.
 */
@Entity
@Table(name = "dept_estimate_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeptEstimateType extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The dept id. */
	@NotNull
	@Column(name = "dept_id", nullable = false)
	private Long deptId;

	/** The flow type. */
	@Column(name = "flow_type")
	private String flowType;

	/** The estimate type id. */
	@NotNull
	@Column(name = "estimate_type_id", nullable = false)
	private Long estimateTypeId;

	/** The active yn. */
	@NotNull
	@Column(name = "active_yn", nullable = false)
	private Boolean activeYn;

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
	 * Id.
	 *
	 * @param id the id
	 * @return the dept estimate type
	 */
	public DeptEstimateType id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the dept id.
	 *
	 * @return the dept id
	 */
	public Long getDeptId() {
		return this.deptId;
	}

	/**
	 * Dept id.
	 *
	 * @param deptId the dept id
	 * @return the dept estimate type
	 */
	public DeptEstimateType deptId(Long deptId) {
		this.deptId = deptId;
		return this;
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
		return this.flowType;
	}

	/**
	 * Flow type.
	 *
	 * @param flowType the flow type
	 * @return the dept estimate type
	 */
	public DeptEstimateType flowType(String flowType) {
		this.flowType = flowType;
		return this;
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
	 * Gets the estimate type id.
	 *
	 * @return the estimate type id
	 */
	public Long getEstimateTypeId() {
		return this.estimateTypeId;
	}

	/**
	 * Estimate type id.
	 *
	 * @param estimateTypeId the estimate type id
	 * @return the dept estimate type
	 */
	public DeptEstimateType estimateTypeId(Long estimateTypeId) {
		this.estimateTypeId = estimateTypeId;
		return this;
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
	 * Gets the active yn.
	 *
	 * @return the active yn
	 */
	public Boolean getActiveYn() {
		return this.activeYn;
	}

	/**
	 * Active yn.
	 *
	 * @param activeYn the active yn
	 * @return the dept estimate type
	 */
	public DeptEstimateType activeYn(Boolean activeYn) {
		this.activeYn = activeYn;
		return this;
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
		if (!(o instanceof DeptEstimateType)) {
			return false;
		}
		return id != null && id.equals(((DeptEstimateType) o).id);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		// see
		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	// prettier-ignore
	@Override
	public String toString() {
		return "DeptEstimateType{" + "id=" + getId() + ", deptId=" + getDeptId() + ", flowType='" + getFlowType() + "'"
				+ ", activeYn='" + getActiveYn() + "'" + "}";
	}
}
