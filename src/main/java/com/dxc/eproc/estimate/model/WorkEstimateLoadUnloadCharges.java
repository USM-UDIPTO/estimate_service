package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEstimateLoadUnloadCharges.
 */
@Entity
@Table(name = "work_estimate_load_unload_charges")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateLoadUnloadCharges extends EProcModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "work_estimate_id", nullable = false)
	private Long workEstimateId;

	@NotNull
	@Column(name = "work_estimate_item_id", nullable = false)
	private Long workEstimateItemId;

	@NotNull
	@Column(name = "cat_work_sor_item_id", nullable = false)
	private Long catWorkSorItemId;

	@NotNull
	@Column(name = "material_master_id", nullable = false)
	private Long materialMasterId;

	@NotNull
	@Column(name = "sub_estimate_id", nullable = false)
	private Long subEstimateId;

	@NotNull
	@Column(name = "selected_load_charges", nullable = false)
	private Boolean selectedLoadCharges;

	@NotNull
	@Column(name = "loading_charges", precision = 8, scale = 4, nullable = false)
	private BigDecimal loadingCharges;

	@NotNull
	@Column(name = "selected_unload_charges", nullable = false)
	private Boolean selectedUnloadCharges;

	@NotNull
	@Column(name = "unloading_charges", precision = 8, scale = 4, nullable = false)
	private BigDecimal unloadingCharges;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkEstimateLoadUnloadCharges id(Long id) {
		this.id = id;
		return this;
	}

	public Long getWorkEstimateId() {
		return this.workEstimateId;
	}

	public WorkEstimateLoadUnloadCharges workEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
		return this;
	}

	public void setWorkEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
	}

	public Long getWorkEstimateItemId() {
		return workEstimateItemId;
	}

	public WorkEstimateLoadUnloadCharges workEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
		return this;
	}

	public void setWorkEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
	}

	public Long getCatWorkSorItemId() {
		return this.catWorkSorItemId;
	}

	public WorkEstimateLoadUnloadCharges catWorkSorItemId(Long catWorkSorItemId) {
		this.catWorkSorItemId = catWorkSorItemId;
		return this;
	}

	public void setCatWorkSorItemId(Long catWorkSorItemId) {
		this.catWorkSorItemId = catWorkSorItemId;
	}

	public Long getMaterialMasterId() {
		return this.materialMasterId;
	}

	public WorkEstimateLoadUnloadCharges materialMasterId(Long materialMasterId) {
		this.materialMasterId = materialMasterId;
		return this;
	}

	public void setMaterialMasterId(Long materialMasterId) {
		this.materialMasterId = materialMasterId;
	}

	public Long getSubEstimateId() {
		return this.subEstimateId;
	}

	public WorkEstimateLoadUnloadCharges subEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
		return this;
	}

	public void setSubEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
	}

	public Boolean getSelectedLoadCharges() {
		return this.selectedLoadCharges;
	}

	public WorkEstimateLoadUnloadCharges selectedLoadCharges(Boolean selectedLoadCharges) {
		this.selectedLoadCharges = selectedLoadCharges;
		return this;
	}

	public void setSelectedLoadCharges(Boolean selectedLoadCharges) {
		this.selectedLoadCharges = selectedLoadCharges;
	}

	public BigDecimal getLoadingCharges() {
		return this.loadingCharges;
	}

	public WorkEstimateLoadUnloadCharges loadingCharges(BigDecimal loadingCharges) {
		this.loadingCharges = loadingCharges;
		return this;
	}

	public void setLoadingCharges(BigDecimal loadingCharges) {
		this.loadingCharges = loadingCharges;
	}

	public Boolean getSelectedUnloadCharges() {
		return this.selectedUnloadCharges;
	}

	public WorkEstimateLoadUnloadCharges selectedUnloadCharges(Boolean selectedUnloadCharges) {
		this.selectedUnloadCharges = selectedUnloadCharges;
		return this;
	}

	public void setSelectedUnloadCharges(Boolean selectedUnloadCharges) {
		this.selectedUnloadCharges = selectedUnloadCharges;
	}

	public BigDecimal getUnloadingCharges() {
		return this.unloadingCharges;
	}

	public WorkEstimateLoadUnloadCharges unloadingCharges(BigDecimal unloadingCharges) {
		this.unloadingCharges = unloadingCharges;
		return this;
	}

	public void setUnloadingCharges(BigDecimal unloadingCharges) {
		this.unloadingCharges = unloadingCharges;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WorkEstimateLoadUnloadCharges)) {
			return false;
		}
		return id != null && id.equals(((WorkEstimateLoadUnloadCharges) o).id);
	}

	@Override
	public int hashCode() {
		// see
		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "WorkEstimateLoadUnloadCharges{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId()
				+ ", workEstimateItemId=" + getWorkEstimateItemId() + ", catWorkSorItemId=" + getCatWorkSorItemId()
				+ ", materialMasterId=" + getMaterialMasterId() + ", subEstimateId=" + getSubEstimateId()
				+ ", selectedLoadCharges='" + getSelectedLoadCharges() + "'" + ", loadingCharges=" + getLoadingCharges()
				+ ", selectedUnloadCharges='" + getSelectedUnloadCharges() + "'" + ", unloadingCharges="
				+ getUnloadingCharges() + "}";
	}
}
