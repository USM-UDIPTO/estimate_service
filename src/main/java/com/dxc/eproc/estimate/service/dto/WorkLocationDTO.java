package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkLocation} entity.
 */
public class WorkLocationDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The sub estimate id. */
	@NotNull
	private Long subEstimateId;

	/** The country id. */
	private Long countryId;

	/** The country name. */
	private String countryName;

	/** The state id. */
	private Long stateId;

	/** The state name. */
	private String stateName;

	/** The district id. */
	private Long districtId;

	/** The district name. */
	private String districtName;

	/** The taluq id. */
	private Long taluqId;

	/** The taluq name. */
	private String taluqName;

	/** The loksabha cont id. */
	private Long loksabhaContId;

	/** The loksabha cont name. */
	private String loksabhaContName;

	/** The assembly cont id. */
	private Long assemblyContId;

	/** The assembly cont name. */
	private String assemblyContName;

	/** The location description. */
	private String locationDescription;

	/** The latitude degrees. */
	private Integer latitudeDegrees;

	/** The latitude minutes. */
	private Integer latitudeMinutes;

	/** The latitude seconds. */
	private Integer latitudeSeconds;

	/** The longitude degrees. */
	private Integer longitudeDegrees;

	/** The longitude minutes. */
	private Integer longitudeMinutes;

	/** The longitude seconds. */
	private Integer longitudeSeconds;

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
	 * Gets the sub estimate id.
	 *
	 * @return the sub estimate id
	 */
	public Long getSubEstimateId() {
		return subEstimateId;
	}

	/**
	 * Sets the sub estimate id.
	 *
	 * @param subEstimateId the new sub estimate id
	 */
	public void setSubEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
	}

	/**
	 * Gets the district id.
	 *
	 * @return the district id
	 */
	public Long getDistrictId() {
		return districtId;
	}

	/**
	 * Sets the district id.
	 *
	 * @param districtId the new district id
	 */
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	/**
	 * Gets the taluq id.
	 *
	 * @return the taluq id
	 */
	public Long getTaluqId() {
		return taluqId;
	}

	/**
	 * Sets the taluq id.
	 *
	 * @param taluqId the new taluq id
	 */
	public void setTaluqId(Long taluqId) {
		this.taluqId = taluqId;
	}

	/**
	 * Gets the loksabha cont id.
	 *
	 * @return the loksabha cont id
	 */
	public Long getLoksabhaContId() {
		return loksabhaContId;
	}

	/**
	 * Sets the loksabha cont id.
	 *
	 * @param loksabhaContId the new loksabha cont id
	 */
	public void setLoksabhaContId(Long loksabhaContId) {
		this.loksabhaContId = loksabhaContId;
	}

	/**
	 * Gets the assembly cont id.
	 *
	 * @return the assembly cont id
	 */
	public Long getAssemblyContId() {
		return assemblyContId;
	}

	/**
	 * Sets the assembly cont id.
	 *
	 * @param assemblyContId the new assembly cont id
	 */
	public void setAssemblyContId(Long assemblyContId) {
		this.assemblyContId = assemblyContId;
	}

	/**
	 * Gets the location description.
	 *
	 * @return the location description
	 */
	public String getLocationDescription() {
		return locationDescription;
	}

	/**
	 * Sets the location description.
	 *
	 * @param locationDescription the new location description
	 */
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	/**
	 * Gets the latitude degrees.
	 *
	 * @return the latitude degrees
	 */
	public Integer getLatitudeDegrees() {
		return latitudeDegrees;
	}

	/**
	 * Sets the latitude degrees.
	 *
	 * @param latitudeDegrees the new latitude degrees
	 */
	public void setLatitudeDegrees(Integer latitudeDegrees) {
		this.latitudeDegrees = latitudeDegrees;
	}

	/**
	 * Gets the latitude minutes.
	 *
	 * @return the latitude minutes
	 */
	public Integer getLatitudeMinutes() {
		return latitudeMinutes;
	}

	/**
	 * Sets the latitude minutes.
	 *
	 * @param latitudeMinutes the new latitude minutes
	 */
	public void setLatitudeMinutes(Integer latitudeMinutes) {
		this.latitudeMinutes = latitudeMinutes;
	}

	/**
	 * Gets the latitude seconds.
	 *
	 * @return the latitude seconds
	 */
	public Integer getLatitudeSeconds() {
		return latitudeSeconds;
	}

	/**
	 * Sets the latitude seconds.
	 *
	 * @param latitudeSeconds the new latitude seconds
	 */
	public void setLatitudeSeconds(Integer latitudeSeconds) {
		this.latitudeSeconds = latitudeSeconds;
	}

	/**
	 * Gets the longitude degrees.
	 *
	 * @return the longitude degrees
	 */
	public Integer getLongitudeDegrees() {
		return longitudeDegrees;
	}

	/**
	 * Sets the longitude degrees.
	 *
	 * @param longitudeDegrees the new longitude degrees
	 */
	public void setLongitudeDegrees(Integer longitudeDegrees) {
		this.longitudeDegrees = longitudeDegrees;
	}

	/**
	 * Gets the longitude minutes.
	 *
	 * @return the longitude minutes
	 */
	public Integer getLongitudeMinutes() {
		return longitudeMinutes;
	}

	/**
	 * Sets the longitude minutes.
	 *
	 * @param longitudeMinutes the new longitude minutes
	 */
	public void setLongitudeMinutes(Integer longitudeMinutes) {
		this.longitudeMinutes = longitudeMinutes;
	}

	/**
	 * Gets the longitude seconds.
	 *
	 * @return the longitude seconds
	 */
	public Integer getLongitudeSeconds() {
		return longitudeSeconds;
	}

	/**
	 * Sets the longitude seconds.
	 *
	 * @param longitudeSeconds the new longitude seconds
	 */
	public void setLongitudeSeconds(Integer longitudeSeconds) {
		this.longitudeSeconds = longitudeSeconds;
	}

	/**
	 * Gets the country id.
	 *
	 * @return the country id
	 */
	public Long getCountryId() {
		return countryId;
	}

	/**
	 * Sets the country id.
	 *
	 * @param countryId the new country id
	 */
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	/**
	 * Gets the state id.
	 *
	 * @return the state id
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * Sets the state id.
	 *
	 * @param stateId the new state id
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * Gets the country name.
	 *
	 * @return the country name
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * Sets the country name.
	 *
	 * @param countryName the new country name
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Gets the state name.
	 *
	 * @return the state name
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * Sets the state name.
	 *
	 * @param stateName the new state name
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * Gets the district name.
	 *
	 * @return the district name
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * Sets the district name.
	 *
	 * @param districtName the new district name
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * Gets the taluq name.
	 *
	 * @return the taluq name
	 */
	public String getTaluqName() {
		return taluqName;
	}

	/**
	 * Sets the taluq name.
	 *
	 * @param taluqName the new taluq name
	 */
	public void setTaluqName(String taluqName) {
		this.taluqName = taluqName;
	}

	/**
	 * Gets the loksabha cont name.
	 *
	 * @return the loksabha cont name
	 */
	public String getLoksabhaContName() {
		return loksabhaContName;
	}

	/**
	 * Sets the loksabha cont name.
	 *
	 * @param loksabhaContName the new loksabha cont name
	 */
	public void setLoksabhaContName(String loksabhaContName) {
		this.loksabhaContName = loksabhaContName;
	}

	/**
	 * Gets the assembly cont name.
	 *
	 * @return the assembly cont name
	 */
	public String getAssemblyContName() {
		return assemblyContName;
	}

	/**
	 * Sets the assembly cont name.
	 *
	 * @param assemblyContName the new assembly cont name
	 */
	public void setAssemblyContName(String assemblyContName) {
		this.assemblyContName = assemblyContName;
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
		if (!(o instanceof WorkLocationDTO)) {
			return false;
		}

		WorkLocationDTO workLocationDTO = (WorkLocationDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workLocationDTO.id);
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
		return "WorkLocationDTO{" + "id=" + getId() + ", subEstimateId=" + getSubEstimateId() + ", districtId="
				+ getDistrictId() + ", taluqId=" + getTaluqId() + ", loksabhaContId=" + getLoksabhaContId()
				+ ", assemblyContId=" + getAssemblyContId() + ", locationDescription='" + getLocationDescription() + "'"
				+ ", latitudeDegrees=" + getLatitudeDegrees() + ", latitudeMinutes=" + getLatitudeMinutes()
				+ ", latitudeSeconds=" + getLatitudeSeconds() + ", longitudeDegrees=" + getLongitudeDegrees()
				+ ", longitudeMinutes=" + getLongitudeMinutes() + ", longitudeSeconds=" + getLongitudeSeconds() + "}";
	}
}
