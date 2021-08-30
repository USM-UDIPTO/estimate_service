package com.dxc.eproc.estimate.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * A WorkLocation.
 */
@Entity
@Table(name = "work_location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
public class WorkLocation extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The sub estimate id. */
	@NotNull
	@Column(name = "sub_estimate_id", nullable = false)
	private Long subEstimateId;

	/** The country id. */
	@Column(name = "country_id")
	private Long countryId;

	/** The country name. */
	@Column(name = "country_name")
	private String countryName;

	/** The state id. */
	@Column(name = "state_id")
	private Long stateId;

	/** The state name. */
	@Column(name = "state_name")
	private String stateName;

	/** The district id. */
	@Column(name = "district_id")
	private Long districtId;

	/** The district name. */
	@Column(name = "district_name")
	private String districtName;

	/** The taluq id. */
	@Column(name = "taluq_id")
	private Long taluqId;

	/** The taluq name. */
	@Column(name = "taluq_name")
	private String taluqName;

	/** The loksabha cont id. */
	@Column(name = "loksabha_cont_id")
	private Long loksabhaContId;

	/** The loksabha cont name. */
	@Column(name = "loksabha_cont_name")
	private String loksabhaContName;

	/** The assembly cont id. */
	@Column(name = "assembly_cont_id")
	private Long assemblyContId;

	/** The assembly cont name. */
	@Column(name = "assembly_cont_name")
	private String assemblyContName;

	/** The location description. */
	@Column(name = "location_description")
	private String locationDescription;

	/** The latitude degrees. */
	@Column(name = "latitude_degrees")
	private Integer latitudeDegrees;

	/** The latitude minutes. */
	@Column(name = "latitude_minutes")
	private Integer latitudeMinutes;

	/** The latitude seconds. */
	@Column(name = "latitude_seconds")
	private Integer latitudeSeconds;

	/** The longitude degrees. */
	@Column(name = "longitude_degrees")
	private Integer longitudeDegrees;

	/** The longitude minutes. */
	@Column(name = "longitude_minutes")
	private Integer longitudeMinutes;

	/** The longitude seconds. */
	@Column(name = "longitude_seconds")
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
	 * Id.
	 *
	 * @param id the id
	 * @return the work location
	 */
	public WorkLocation id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the sub estimate id.
	 *
	 * @return the sub estimate id
	 */
	public Long getSubEstimateId() {
		return this.subEstimateId;
	}

	/**
	 * Sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the work location
	 */
	public WorkLocation subEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
		return this;
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
		return this.districtId;
	}

	/**
	 * District id.
	 *
	 * @param districtId the district id
	 * @return the work location
	 */
	public WorkLocation districtId(Long districtId) {
		this.districtId = districtId;
		return this;
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
		return this.taluqId;
	}

	/**
	 * Taluq id.
	 *
	 * @param taluqId the taluq id
	 * @return the work location
	 */
	public WorkLocation taluqId(Long taluqId) {
		this.taluqId = taluqId;
		return this;
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
		return this.loksabhaContId;
	}

	/**
	 * Loksabha cont id.
	 *
	 * @param loksabhaContId the loksabha cont id
	 * @return the work location
	 */
	public WorkLocation loksabhaContId(Long loksabhaContId) {
		this.loksabhaContId = loksabhaContId;
		return this;
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
		return this.assemblyContId;
	}

	/**
	 * Assembly cont id.
	 *
	 * @param assemblyContId the assembly cont id
	 * @return the work location
	 */
	public WorkLocation assemblyContId(Long assemblyContId) {
		this.assemblyContId = assemblyContId;
		return this;
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
		return this.locationDescription;
	}

	/**
	 * Location description.
	 *
	 * @param locationDescription the location description
	 * @return the work location
	 */
	public WorkLocation locationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
		return this;
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
		return this.latitudeDegrees;
	}

	/**
	 * Latitude degrees.
	 *
	 * @param latitudeDegrees the latitude degrees
	 * @return the work location
	 */
	public WorkLocation latitudeDegrees(Integer latitudeDegrees) {
		this.latitudeDegrees = latitudeDegrees;
		return this;
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
		return this.latitudeMinutes;
	}

	/**
	 * Latitude minutes.
	 *
	 * @param latitudeMinutes the latitude minutes
	 * @return the work location
	 */
	public WorkLocation latitudeMinutes(Integer latitudeMinutes) {
		this.latitudeMinutes = latitudeMinutes;
		return this;
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
		return this.latitudeSeconds;
	}

	/**
	 * Latitude seconds.
	 *
	 * @param latitudeSeconds the latitude seconds
	 * @return the work location
	 */
	public WorkLocation latitudeSeconds(Integer latitudeSeconds) {
		this.latitudeSeconds = latitudeSeconds;
		return this;
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
		return this.longitudeDegrees;
	}

	/**
	 * Longitude degrees.
	 *
	 * @param longitudeDegrees the longitude degrees
	 * @return the work location
	 */
	public WorkLocation longitudeDegrees(Integer longitudeDegrees) {
		this.longitudeDegrees = longitudeDegrees;
		return this;
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
		return this.longitudeMinutes;
	}

	/**
	 * Longitude minutes.
	 *
	 * @param longitudeMinutes the longitude minutes
	 * @return the work location
	 */
	public WorkLocation longitudeMinutes(Integer longitudeMinutes) {
		this.longitudeMinutes = longitudeMinutes;
		return this;
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
		return this.longitudeSeconds;
	}

	/**
	 * Longitude seconds.
	 *
	 * @param longitudeSeconds the longitude seconds
	 * @return the work location
	 */
	public WorkLocation longitudeSeconds(Integer longitudeSeconds) {
		this.longitudeSeconds = longitudeSeconds;
		return this;
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
	 * Country id.
	 *
	 * @param countryId the country id
	 * @return the work location
	 */
	public WorkLocation countryId(Long countryId) {
		this.countryId = countryId;
		return this;
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
	 * State id.
	 *
	 * @param stateId the state id
	 * @return the work location
	 */
	public WorkLocation stateId(Long stateId) {
		this.stateId = stateId;
		return this;
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
	 * Country name.
	 *
	 * @param countryName the country name
	 * @return the work location
	 */
	public WorkLocation countryName(String countryName) {
		this.countryName = countryName;
		return this;
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
	 * State name.
	 *
	 * @param stateName the state name
	 * @return the work location
	 */
	public WorkLocation stateName(String stateName) {
		this.stateName = stateName;
		return this;
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
	 * District name.
	 *
	 * @param districtName the district name
	 * @return the work location
	 */
	public WorkLocation districtName(String districtName) {
		this.districtName = districtName;
		return this;
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
	 * Taluq name.
	 *
	 * @param taluqName the taluq name
	 * @return the work location
	 */
	public WorkLocation taluqName(String taluqName) {
		this.taluqName = taluqName;
		return this;
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
	 * Loksabha cont name.
	 *
	 * @param loksabhaContName the loksabha cont name
	 * @return the work location
	 */
	public WorkLocation loksabhaContName(String loksabhaContName) {
		this.loksabhaContName = loksabhaContName;
		return this;
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
	 * Assembly cont name.
	 *
	 * @param assemblyContName the assembly cont name
	 * @return the work location
	 */
	public WorkLocation assemblyContName(String assemblyContName) {
		this.assemblyContName = assemblyContName;
		return this;
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
		if (!(o instanceof WorkLocation)) {
			return false;
		}
		return id != null && id.equals(((WorkLocation) o).id);
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
		return "WorkLocation{" + "id=" + getId() + ", subEstimateId=" + getSubEstimateId() + ", districtId="
				+ getDistrictId() + ", taluqId=" + getTaluqId() + ", loksabhaContId=" + getLoksabhaContId()
				+ ", assemblyContId=" + getAssemblyContId() + ", locationDescription='" + getLocationDescription() + "'"
				+ ", latitudeDegrees=" + getLatitudeDegrees() + ", latitudeMinutes=" + getLatitudeMinutes()
				+ ", latitudeSeconds=" + getLatitudeSeconds() + ", longitudeDegrees=" + getLongitudeDegrees()
				+ ", longitudeMinutes=" + getLongitudeMinutes() + ", longitudeSeconds=" + getLongitudeSeconds() + "}";
	}
}
