package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.dxc.eproc.estimate.enumeration.SchemeType;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.SchemeCode} entity.
 */
public class SchemeCodeDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The location id. */
	@NotNull
	private Long locationId;

	/** The schemetype. */
	@NotNull
	private SchemeType schemeType;

	/** The scheme code. */
	@NotNull
	private String schemeCode;

	/** The scheme status. */
	private String schemeStatus;

	/** The work name. */
	private String workName;

	/** The work val. */
	private String workVal;

	/** The source name. */
	private String sourceName;

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
	 * Gets the location id.
	 *
	 * @return the location id
	 */
	public Long getLocationId() {
		return locationId;
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
		return schemeType;
	}

	/**
	 * Sets the scheme type.
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
		return schemeCode;
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
		return schemeStatus;
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
		return workName;
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
		return workVal;
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
	 * Gets the source name.
	 *
	 * @return the source name
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * Sets the source name.
	 *
	 * @param sourceName the new source name
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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
		if (!(o instanceof SchemeCodeDTO)) {
			return false;
		}

		SchemeCodeDTO schemeCodeDTO = (SchemeCodeDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, schemeCodeDTO.id);
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
		return "SchemeCodeDTO{" + "id=" + getId() + ", locationId=" + getLocationId() + ", schemetype='"
				+ getSchemeType() + "'" + ", schemeCode='" + getSchemeCode() + "'" + ", schemeStatus='"
				+ getSchemeStatus() + "'" + ", workName='" + getWorkName() + "'" + ", workVal='" + getWorkVal() + "'"
				+ ", sourceName='" + getSourceName() + "'" + ", activeYn='" + getActiveYn() + "'" + "}";
	}
}
