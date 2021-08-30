package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEstimateLeadCharges.
 */
@Entity
@Table(name = "work_estimate_lead_charges")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateLeadCharges extends EProcModel implements Serializable {

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

	@Size(max = 50)
	@Column(name = "quarry", length = 50)
	private String quarry;

	@DecimalMin(value = "0")
	@DecimalMax(value = "10000")
	@Column(name = "lead_in_m", precision = 6, scale = 2)
	private BigDecimal leadInM;

	@DecimalMin(value = "0")
	@DecimalMax(value = "10000")
	@Column(name = "lead_in_km", precision = 6, scale = 2)
	private BigDecimal leadInKm;

	@Column(name = "lead_charges", precision = 10, scale = 2)
	private BigDecimal leadCharges;

	@NotNull
	@Column(name = "initial_lead_required_yn", nullable = false)
	private Boolean initialLeadRequiredYn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkEstimateLeadCharges id(Long id) {
		this.id = id;
		return this;
	}

	public Long getWorkEstimateId() {
		return this.workEstimateId;
	}

	public WorkEstimateLeadCharges workEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
		return this;
	}

	public void setWorkEstimateId(Long workEstimateId) {
		this.workEstimateId = workEstimateId;
	}

	public Long getWorkEstimateItemId() {
		return workEstimateItemId;
	}

	public WorkEstimateLeadCharges workEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
		return this;
	}

	public void setWorkEstimateItemId(Long workEstimateItemId) {
		this.workEstimateItemId = workEstimateItemId;
	}

	public Long getCatWorkSorItemId() {
		return this.catWorkSorItemId;
	}

	public WorkEstimateLeadCharges catWorkSorItemId(Long catWorkSorItemId) {
		this.catWorkSorItemId = catWorkSorItemId;
		return this;
	}

	public void setCatWorkSorItemId(Long catWorkSorItemId) {
		this.catWorkSorItemId = catWorkSorItemId;
	}

	public Long getMaterialMasterId() {
		return this.materialMasterId;
	}

	public WorkEstimateLeadCharges materialMasterId(Long materialMasterId) {
		this.materialMasterId = materialMasterId;
		return this;
	}

	public void setMaterialMasterId(Long materialMasterId) {
		this.materialMasterId = materialMasterId;
	}

	public Long getSubEstimateId() {
		return this.subEstimateId;
	}

	public WorkEstimateLeadCharges subEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
		return this;
	}

	public void setSubEstimateId(Long subEstimateId) {
		this.subEstimateId = subEstimateId;
	}

	public String getQuarry() {
		return this.quarry;
	}

	public WorkEstimateLeadCharges quarry(String quarry) {
		this.quarry = quarry;
		return this;
	}

	public void setQuarry(String quarry) {
		this.quarry = quarry;
	}

	public BigDecimal getLeadInM() {
		return this.leadInM;
	}

	public WorkEstimateLeadCharges leadInM(BigDecimal leadInM) {
		this.leadInM = leadInM;
		return this;
	}

	public void setLeadInM(BigDecimal leadInM) {
		this.leadInM = leadInM;
	}

	public BigDecimal getLeadInKm() {
		return this.leadInKm;
	}

	public WorkEstimateLeadCharges leadInKm(BigDecimal leadInKm) {
		this.leadInKm = leadInKm;
		return this;
	}

	public void setLeadInKm(BigDecimal leadInKm) {
		this.leadInKm = leadInKm;
	}

	public Boolean getInitialLeadRequiredYn() {
		return this.initialLeadRequiredYn;
	}

	public BigDecimal getLeadCharges() {
		return this.leadCharges;
	}

	public WorkEstimateLeadCharges leadCharges(BigDecimal leadCharges) {
		this.leadCharges = leadCharges;
		return this;
	}

	public void setLeadCharges(BigDecimal leadCharges) {
		this.leadCharges = leadCharges;
	}

	public WorkEstimateLeadCharges initialLeadRequiredYn(Boolean initialLeadRequiredYn) {
		this.initialLeadRequiredYn = initialLeadRequiredYn;
		return this;
	}

	public void setInitialLeadRequiredYn(Boolean initialLeadRequiredYn) {
		this.initialLeadRequiredYn = initialLeadRequiredYn;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WorkEstimateLeadCharges)) {
			return false;
		}
		return id != null && id.equals(((WorkEstimateLeadCharges) o).id);
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
		return "WorkEstimateLeadCharges{" + "id=" + getId() + ", workEstimateId=" + getWorkEstimateId()
				+ ", workEstimateItemId=" + getWorkEstimateItemId() + ", catWorkSorItemId=" + getCatWorkSorItemId()
				+ ", materialMasterId=" + getMaterialMasterId() + ", subEstimateId=" + getSubEstimateId() + ", quarry='"
				+ getQuarry() + "'" + ", leadInM=" + getLeadInM() + ", leadInKm=" + getLeadInKm() + ", leadCharges="
				+ getLeadCharges() + ", initialLeadRequiredYn='" + getInitialLeadRequiredYn() + "'" + "}";
	}
}
