package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.dxc.eproc.estimate.enumeration.RaChargeType;

/**
 * A DTO for the
 * {@link com.dxc.eproc.estimate.model.WorkEstimateOtherAddnLiftCharges} entity.
 */
public class WorkEstimateOtherAddnLiftChargesDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate item id. */
	private Long workEstimateItemId;

	/** The notes master id. */
	@NotNull
	private Long notesMasterId;

	/** The selected. */
	@NotNull
	private Boolean selected;

	/** The addn charges. */
	@NotNull
	private BigDecimal addnCharges;

	@NotNull
	private RaChargeType type;

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
	 * Gets the work estimate item id.
	 *
	 * @return the work estimate item id
	 */
	public Long getWorkEstimateItemId() {
		return workEstimateItemId;
	}

	/**
	 * Sets the work estimate item id.
	 *
	 * @param workEstimateItemId the new work estimate item id
	 */
	public void setWorkEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
	}

	/**
	 * Gets the notes master id.
	 *
	 * @return the notes master id
	 */
	public Long getNotesMasterId() {
		return notesMasterId;
	}

	/**
	 * Sets the notes master id.
	 *
	 * @param notesMasterId the new notes master id
	 */
	public void setNotesMasterId(Long notesMasterId) {
		this.notesMasterId = notesMasterId;
	}

	/**
	 * Gets the selected.
	 *
	 * @return the selected
	 */
	public Boolean getSelected() {
		return selected;
	}

	/**
	 * Sets the selected.
	 *
	 * @param selected the new selected
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	/**
	 * Gets the addn charges.
	 *
	 * @return the addn charges
	 */
	public BigDecimal getAddnCharges() {
		return addnCharges;
	}

	/**
	 * Sets the addn charges.
	 *
	 * @param addnCharges the new addn charges
	 */
	public void setAddnCharges(BigDecimal addnCharges) {
		this.addnCharges = addnCharges;
	}

	public RaChargeType getType() {
		return type;
	}

	public void setType(RaChargeType type) {
		this.type = type;
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
		if (!(o instanceof WorkEstimateOtherAddnLiftChargesDTO)) {
			return false;
		}

		WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = (WorkEstimateOtherAddnLiftChargesDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateOtherAddnLiftChargesDTO.id);
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
		return "WorkEstimateOtherAddnLiftChargesDTO{" + "id=" + getId() + ", workEstimateItemId="
				+ getWorkEstimateItemId() + ", notesMasterId=" + getNotesMasterId() + ", selected='" + getSelected()
				+ "'" + ", addnCharges=" + getAddnCharges() + ", type=" + getType() + "}";
	}
}
