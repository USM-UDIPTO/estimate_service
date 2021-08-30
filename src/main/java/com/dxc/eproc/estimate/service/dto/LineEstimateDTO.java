package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.domain.LineEstimate} entity.
 */
public class LineEstimateDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate id. */
	private Long workEstimateId;

	/** The name. */
	@NotBlank(message = "{lineEstimate.name.NotBlank}")
	@Size(max = 99, message = "{lineEstimate.name.Size}")
	@Pattern(regexp = "^(?! )[/A-Za-z0-9 \\s-]*(?<! )$", message = "{lineEstimate.name.Pattern}")
	private String name;

	/** The approx rate. */
	// @Pattern(regexp =
	// "^(?!0$)(?!00?\\.0$)(?!00?\\.00$)\\d{1,8}(?:\\.\\d{1,4})?$", message =
	// "{lineEstimate.approxRate.Pattern}")
	@NotNull(message = "{lineEstimate.approxRate.NotNull}")
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
	 * Gets the work estimate id.
	 *
	 * @return the work estimate id
	 */
	public Long getWorkEstimateId() {
		return workEstimateId;
	}

	/**
	 * Sets the work estimate id.
	 *
	 * @param workEstimateId the new work estimate id
	 */
	public void setWorkEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
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
		if (!(o instanceof LineEstimateDTO)) {
			return false;
		}

		LineEstimateDTO lineEstimateDTO = (LineEstimateDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, lineEstimateDTO.id);
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
		return "LineEstimateDTO{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId() + ", name='" + getName()
				+ "'" + ", approxRate=" + getApproxRate() + "}";
	}
}
