package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the
 * {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum} entity.
 */
public class WorkSubEstimateGroupLumpsumDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work sub estimate group id. */
	private Long workSubEstimateGroupId;

	/** The name. */
	@NotBlank(message = "{workSubEstimateGroupLumpsum.name.notBlank}")
	@Pattern(regexp = "^(?! )[\\'+\\\\\\/&,.A-Za-z0-9 \\s-]*(?<! )$", message = "{workSubEstimateGroupLumpsum.name.pattern}")
	private String name;

	/** The approx rate. */
	@NotNull(message = "{workSubEstimateGroupLumpsum.approxRate.notNull}")
	private BigDecimal approxRate;

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
	 * Gets the work sub estimate group id.
	 *
	 * @return the work sub estimate group id
	 */
	public Long getWorkSubEstimateGroupId() {
		return workSubEstimateGroupId;
	}

	/**
	 * Sets the work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the new work sub estimate group id
	 */
	public void setWorkSubEstimateGroupId(Long workSubEstimateGroupId) {
		this.workSubEstimateGroupId = workSubEstimateGroupId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the approx rate.
	 *
	 * @return the approx rate
	 */
	public BigDecimal getApproxRate() {
		return approxRate;
	}

	/**
	 * Sets the approx rate.
	 *
	 * @param approxRate the new approx rate
	 */
	public void setApproxRate(BigDecimal approxRate) {
		this.approxRate = approxRate;
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
		if (!(o instanceof WorkSubEstimateGroupLumpsumDTO)) {
			return false;
		}

		WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO = (WorkSubEstimateGroupLumpsumDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workSubEstimateGroupLumpsumDTO.id);
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
		return "WorkSubEstimateGroupLumpsumDTO{" + "id=" + getId() + ", workSubEstimateGroupId="
				+ getWorkSubEstimateGroupId() + ", name='" + getName() + "'" + ", approxRate=" + getApproxRate() + "}";
	}
}
