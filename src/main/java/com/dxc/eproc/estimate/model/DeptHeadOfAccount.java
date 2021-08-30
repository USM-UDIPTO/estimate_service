package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * A DeptHeadOfAccount.
 */
@Entity
@Table(name = "dept_head_of_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeptHeadOfAccount extends EProcModel implements Serializable {

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

	/** The head of account. */
	@NotNull
	@Column(name = "head_of_account", nullable = false)
	private String headOfAccount;

	/** The hoa description. */
	@Column(name = "hoa_description")
	private String hoaDescription;

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
	 * @return the dept head of account
	 */
	public DeptHeadOfAccount id(Long id) {
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
	 * @return the dept head of account
	 */
	public DeptHeadOfAccount deptId(Long deptId) {
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
	 * Gets the head of account.
	 *
	 * @return the head of account
	 */
	public String getHeadOfAccount() {
		return this.headOfAccount;
	}

	/**
	 * Head of account.
	 *
	 * @param headOfAccount the head of account
	 * @return the dept head of account
	 */
	public DeptHeadOfAccount headOfAccount(String headOfAccount) {
		this.headOfAccount = headOfAccount;
		return this;
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
		return this.hoaDescription;
	}

	/**
	 * Hoa description.
	 *
	 * @param hoaDescription the hoa description
	 * @return the dept head of account
	 */
	public DeptHeadOfAccount hoaDescription(String hoaDescription) {
		this.hoaDescription = hoaDescription;
		return this;
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
		return this.activeYn;
	}

	/**
	 * Active yn.
	 *
	 * @param activeYn the active yn
	 * @return the dept head of account
	 */
	public DeptHeadOfAccount activeYn(Boolean activeYn) {
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
		if (!(o instanceof DeptHeadOfAccount)) {
			return false;
		}
		return id != null && id.equals(((DeptHeadOfAccount) o).id);
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
		return "DeptHeadOfAccount{" + "id=" + getId() + ", deptId=" + getDeptId() + ", headOfAccount='"
				+ getHeadOfAccount() + "'" + ", hoaDescription='" + getHoaDescription() + "'" + ", activeYn='"
				+ getActiveYn() + "'" + "}";
	}
}
