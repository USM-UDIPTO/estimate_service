package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.DeptHeadOfAccount} entity.
 */
public class DeptHeadOfAccountDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The dept id. */
	@NotNull
	private Long deptId;

	/** The head of account. */
	@NotNull
	private String headOfAccount;

	/** The hoa description. */
	private String hoaDescription;

	/** The active yn. */
	@NotNull
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
	 * Gets the head of account.
	 *
	 * @return the head of account
	 */
	public String getHeadOfAccount() {
		return headOfAccount;
	}

	/**
	 * Sets the head of account.
	 *
	 * @param headOfAccount the new head of account
	 */
	public void setHeadOfAccount(String headOfAccount) {
		this.headOfAccount = headOfAccount;
	}

	/**
	 * Gets the hoa description.
	 *
	 * @return the hoa description
	 */
	public String getHoaDescription() {
		return hoaDescription;
	}

	/**
	 * Sets the hoa description.
	 *
	 * @param hoaDescription the new hoa description
	 */
	public void setHoaDescription(String hoaDescription) {
		this.hoaDescription = hoaDescription;
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
		if (!(o instanceof DeptHeadOfAccountDTO)) {
			return false;
		}

		DeptHeadOfAccountDTO deptHeadOfAccountDTO = (DeptHeadOfAccountDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, deptHeadOfAccountDTO.id);
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
		return "DeptHeadOfAccountDTO{" + "id=" + getId() + ", deptId=" + getDeptId() + ", headOfAccount='"
				+ getHeadOfAccount() + "'" + ", hoaDescription='" + getHoaDescription() + "'" + ", activeYn='"
				+ getActiveYn() + "'" + "}";
	}
}
