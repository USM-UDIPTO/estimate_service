package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dxc.eproc.estimate.enumeration.RaChargeType;

/**
 * A WorkEstimateOtherAddnLiftCharges.
 */
@Entity
@Table(name = "work_estimate_other_addn_lift_charges")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateOtherAddnLiftCharges extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The work estimate item id. */
	@NotNull
	@Column(name = "work_estimate_item_id", nullable = false)
	private Long workEstimateItemId;

	/** The notes master id. */
	@NotNull
	@Column(name = "notes_master_id", nullable = false)
	private Long notesMasterId;

	/** The selected. */
	@NotNull
	@Column(name = "selected", nullable = false)
	private Boolean selected;

	/** The addn charges. */
	@NotNull
	@Column(name = "addn_charges", precision = 10, scale = 2, nullable = false)
	private BigDecimal addnCharges;

	@NotNull
	@Column(name = "type", nullable = false)
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
	 * Id.
	 *
	 * @param id the id
	 * @return the work estimate other addn lift charges
	 */
	public WorkEstimateOtherAddnLiftCharges id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the work estimate item id.
	 *
	 * @return the work estimate item id
	 */
	public Long getWorkEstimateItemId() {
		return this.workEstimateItemId;
	}

	/**
	 * Work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @return the work estimate other addn lift charges
	 */
	public WorkEstimateOtherAddnLiftCharges workEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
		return this;
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
		return this.notesMasterId;
	}

	/**
	 * Notes master id.
	 *
	 * @param notesMasterId the notes master id
	 * @return the work estimate other addn lift charges
	 */
	public WorkEstimateOtherAddnLiftCharges notesMasterId(Long notesMasterId) {
		this.notesMasterId = notesMasterId;
		return this;
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
		return this.selected;
	}

	/**
	 * Selected.
	 *
	 * @param selected the selected
	 * @return the work estimate other addn lift charges
	 */
	public WorkEstimateOtherAddnLiftCharges selected(Boolean selected) {
		this.selected = selected;
		return this;
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
		return this.addnCharges;
	}

	/**
	 * Addn charges.
	 *
	 * @param addnCharges the addn charges
	 * @return the work estimate other addn lift charges
	 */
	public WorkEstimateOtherAddnLiftCharges addnCharges(BigDecimal addnCharges) {
		this.addnCharges = addnCharges;
		return this;
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

	public WorkEstimateOtherAddnLiftCharges type(RaChargeType type) {
		this.type = type;
		return this;
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
		if (!(o instanceof WorkEstimateOtherAddnLiftCharges)) {
			return false;
		}
		return id != null && id.equals(((WorkEstimateOtherAddnLiftCharges) o).id);
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
		return "WorkEstimateOtherAddnLiftCharges{" + "id=" + getId() + ", workEstimateItemId=" + getWorkEstimateItemId()
				+ ", notesMasterId=" + getNotesMasterId() + ", selected='" + getSelected() + "'" + ", addnCharges="
				+ getAddnCharges() + "'" + ", type=" + getType() + "}";
	}
}
