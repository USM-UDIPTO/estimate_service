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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEstimateRoyaltyCharges.
 */
@Entity
@Table(name = "work_estimate_royalty_charges")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateRoyaltyCharges extends EProcModel implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The work estimate id. */
    @NotNull
    @Column(name = "work_estimate_id", nullable = false)
    private Long workEstimateId;
    
    /** The sub estimate id. */
    @NotNull
    @Column(name = "sub_estimate_id", nullable = false)
    private Long subEstimateId;
    
    /** The work estimate item id. */
    @NotNull
    @Column(name = "work_estimate_item_id", nullable = false)
    private Long workEstimateItemId;

    /** The cat work sor item id. */
    @NotNull
    @Column(name = "cat_work_sor_item_id", nullable = false)
    private Long catWorkSorItemId;

    /** The material master id. */
    @NotNull
    @Column(name = "material_master_id", nullable = false)
    private Long materialMasterId;

    /** The sr royalty charges. */
    @NotNull
    @Column(name = "sr_royalty_charges", precision = 10, scale = 2, nullable = false)
    private BigDecimal srRoyaltyCharges;

    /** The prevailing royalty charges. */
    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    @Column(name = "prevailing_royalty_charges", precision = 10, scale = 2, nullable = false)
    private BigDecimal prevailingRoyaltyCharges;

    /** The density factor. */
    @NotNull
    @Column(name = "density_factor", precision = 10, scale = 2, nullable = false)
    private BigDecimal densityFactor;

    /** The difference. */
    @Column(name = "difference", precision = 10, scale = 2)
    private BigDecimal difference;

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
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Gets the work estimate id.
     *
     * @return the work estimate id
     */
    public Long getWorkEstimateId() {
        return this.workEstimateId;
    }

    /**
     * Work estimate id.
     *
     * @param workEstimateId the work estimate id
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges workEstimateId(Long workEstimateId) {
        this.workEstimateId = workEstimateId;
        return this;
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
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges workEstimateItemId(Long workEstimateItemId) {
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
     * Gets the cat work sor item id.
     *
     * @return the cat work sor item id
     */
    public Long getCatWorkSorItemId() {
        return this.catWorkSorItemId;
    }

    /**
     * Cat work sor item id.
     *
     * @param catWorkSorItemId the cat work sor item id
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges catWorkSorItemId(Long catWorkSorItemId) {
        this.catWorkSorItemId = catWorkSorItemId;
        return this;
    }

    /**
     * Sets the cat work sor item id.
     *
     * @param catWorkSorItemId the new cat work sor item id
     */
    public void setCatWorkSorItemId(Long catWorkSorItemId) {
        this.catWorkSorItemId = catWorkSorItemId;
    }

    /**
     * Gets the material master id.
     *
     * @return the material master id
     */
    public Long getMaterialMasterId() {
        return this.materialMasterId;
    }

    /**
     * Material master id.
     *
     * @param materialMasterId the material master id
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges materialMasterId(Long materialMasterId) {
        this.materialMasterId = materialMasterId;
        return this;
    }

    /**
     * Sets the material master id.
     *
     * @param materialMasterId the new material master id
     */
    public void setMaterialMasterId(Long materialMasterId) {
        this.materialMasterId = materialMasterId;
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
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges subEstimateId(Long subEstimateId) {
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
     * Gets the sr royalty charges.
     *
     * @return the sr royalty charges
     */
    public BigDecimal getSrRoyaltyCharges() {
        return this.srRoyaltyCharges;
    }

    /**
     * Sr royalty charges.
     *
     * @param srRoyaltyCharges the sr royalty charges
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges srRoyaltyCharges(BigDecimal srRoyaltyCharges) {
        this.srRoyaltyCharges = srRoyaltyCharges;
        return this;
    }

    /**
     * Sets the sr royalty charges.
     *
     * @param srRoyaltyCharges the new sr royalty charges
     */
    public void setSrRoyaltyCharges(BigDecimal srRoyaltyCharges) {
        this.srRoyaltyCharges = srRoyaltyCharges;
    }

    /**
     * Gets the prevailing royalty charges.
     *
     * @return the prevailing royalty charges
     */
    public BigDecimal getPrevailingRoyaltyCharges() {
        return this.prevailingRoyaltyCharges;
    }

    /**
     * Prevailing royalty charges.
     *
     * @param prevailingRoyaltyCharges the prevailing royalty charges
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges prevailingRoyaltyCharges(BigDecimal prevailingRoyaltyCharges) {
        this.prevailingRoyaltyCharges = prevailingRoyaltyCharges;
        return this;
    }

    /**
     * Sets the prevailing royalty charges.
     *
     * @param prevailingRoyaltyCharges the new prevailing royalty charges
     */
    public void setPrevailingRoyaltyCharges(BigDecimal prevailingRoyaltyCharges) {
        this.prevailingRoyaltyCharges = prevailingRoyaltyCharges;
    }

    /**
     * Gets the density factor.
     *
     * @return the density factor
     */
    public BigDecimal getDensityFactor() {
        return this.densityFactor;
    }

    /**
     * Density factor.
     *
     * @param densityFactor the density factor
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges densityFactor(BigDecimal densityFactor) {
        this.densityFactor = densityFactor;
        return this;
    }

    /**
     * Sets the density factor.
     *
     * @param densityFactor the new density factor
     */
    public void setDensityFactor(BigDecimal densityFactor) {
        this.densityFactor = densityFactor;
    }

    /**
     * Gets the difference.
     *
     * @return the difference
     */
    public BigDecimal getDifference() {
        return this.difference;
    }

    /**
     * Difference.
     *
     * @param difference the difference
     * @return the work estimate royalty charges
     */
    public WorkEstimateRoyaltyCharges difference(BigDecimal difference) {
        this.difference = difference;
        return this;
    }

    /**
     * Sets the difference.
     *
     * @param difference the new difference
     */
    public void setDifference(BigDecimal difference) {
        this.difference = difference;
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
        if (!(o instanceof WorkEstimateRoyaltyCharges)) {
            return false;
        }
        return id != null && id.equals(((WorkEstimateRoyaltyCharges) o).id);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
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
        return "WorkEstimateRoyaltyCharges{" +
            "id=" + getId() +
            ", workEstimateId=" + getWorkEstimateId() +
            ", workEstimateItemId=" + getWorkEstimateItemId() +
            ", catWorkSorItemId=" + getCatWorkSorItemId() +
            ", materialMasterId=" + getMaterialMasterId() +
            ", subEstimateId=" + getSubEstimateId() +
            ", srRoyaltyCharges=" + getSrRoyaltyCharges() +
            ", prevailingRoyaltyCharges=" + getPrevailingRoyaltyCharges() +
            ", densityFactor=" + getDensityFactor() +
            ", difference=" + getDifference() +
            "}";
    }
}
