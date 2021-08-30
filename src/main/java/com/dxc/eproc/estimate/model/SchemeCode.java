package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dxc.eproc.estimate.enumeration.SchemeType;

// TODO: Auto-generated Javadoc
/**
 * A SchemeCode.
 */
@Entity
@Table(name = "scheme_code")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SchemeCode extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The location id. */
	@NotNull
	@Column(name = "location_id", nullable = false)
	private Long locationId;

	/** The schemetype. */
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "scheme_type", nullable = false)
	private SchemeType schemeType;

	/** The scheme code. */
	@NotNull
	@Column(name = "scheme_code", nullable = false)
	private String schemeCode;

	/** The scheme status. */
	@Column(name = "scheme_status")
	private String schemeStatus;

	/** The work name. */
	@Column(name = "work_name")
	private String workName;

	/** The work val. */
	@Column(name = "work_val")
	private String workVal;

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
	 * @return the scheme code
	 */
	public SchemeCode id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the location id.
	 *
	 * @return the location id
	 */
	public Long getLocationId() {
		return this.locationId;
	}

	/**
	 * Location id.
	 *
	 * @param locationId the location id
	 * @return the scheme code
	 */
	public SchemeCode locationId(Long locationId) {
		this.locationId = locationId;
		return this;
	}

	/**
	 * Sets the location id.
	 *
	 * @param locationId the new location id
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	/**
	 * Gets the schemetype.
	 *
	 * @return the schemetype
	 */
	public SchemeType getSchemeType() {
		return this.schemeType;
	}

	/**
	 * Schemetype.
	 *
	 * @param schemeType the scheme type
	 * @return the scheme code
	 */
	public SchemeCode schemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
		return this;
	}

	/**
	 * Sets the schemetype.
	 *
	 * @param schemeType the new scheme type
	 */
	public void setSchemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
	}

	/**
	 * Gets the scheme code.
	 *
	 * @return the scheme code
	 */
	public String getSchemeCode() {
		return this.schemeCode;
	}

	/**
	 * Scheme code.
	 *
	 * @param schemeCode the scheme code
	 * @return the scheme code
	 */
	public SchemeCode schemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
		return this;
	}

	/**
	 * Sets the scheme code.
	 *
	 * @param schemeCode the new scheme code
	 */
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	/**
	 * Gets the scheme status.
	 *
	 * @return the scheme status
	 */
	public String getSchemeStatus() {
		return this.schemeStatus;
	}

	/**
	 * Scheme status.
	 *
	 * @param schemeStatus the scheme status
	 * @return the scheme code
	 */
	public SchemeCode schemeStatus(String schemeStatus) {
		this.schemeStatus = schemeStatus;
		return this;
	}

	/**
	 * Sets the scheme status.
	 *
	 * @param schemeStatus the new scheme status
	 */
	public void setSchemeStatus(String schemeStatus) {
		this.schemeStatus = schemeStatus;
	}

	/**
	 * Gets the work name.
	 *
	 * @return the work name
	 */
	public String getWorkName() {
		return this.workName;
	}

	/**
	 * Work name.
	 *
	 * @param workName the work name
	 * @return the scheme code
	 */
	public SchemeCode workName(String workName) {
		this.workName = workName;
		return this;
	}

	/**
	 * Sets the work name.
	 *
	 * @param workName the new work name
	 */
	public void setWorkName(String workName) {
		this.workName = workName;
	}

	/**
	 * Gets the work val.
	 *
	 * @return the work val
	 */
	public String getWorkVal() {
		return this.workVal;
	}

	/**
	 * Work val.
	 *
	 * @param workVal the work val
	 * @return the scheme code
	 */
	public SchemeCode workVal(String workVal) {
		this.workVal = workVal;
		return this;
	}

	/**
	 * Sets the work val.
	 *
	 * @param workVal the new work val
	 */
	public void setWorkVal(String workVal) {
		this.workVal = workVal;
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
	 * @return the scheme code
	 */
	public SchemeCode activeYn(Boolean activeYn) {
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
		if (!(o instanceof SchemeCode)) {
			return false;
		}
		return id != null && id.equals(((SchemeCode) o).id);
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
		return "SchemeCode{" + "id=" + getId() + ", locationId=" + getLocationId() + ", schemetype='" + getSchemeType()
				+ "'" + ", schemeCode='" + getSchemeCode() + "'" + ", schemeStatus='" + getSchemeStatus() + "'"
				+ ", workName='" + getWorkName() + "'" + ", workVal='" + getWorkVal() + "'" + ", activeYn='"
				+ getActiveYn() + "'" + "}";
	}
}
