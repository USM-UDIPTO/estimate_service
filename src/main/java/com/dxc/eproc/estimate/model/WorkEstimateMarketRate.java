package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEstimateMarketRate.
 */
@Entity
@Table(name = "work_estimate_market_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateMarketRate extends EProcModel implements Serializable {

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

    /** The material master id. */
    @NotNull
    @Column(name = "material_master_id", nullable = false)
    private Long materialMasterId;

    /** The difference. */
    @NotNull
    @Column(name = "difference", precision = 10, scale = 4, nullable = false)
    private BigDecimal difference;

    /** The prevailing market rate. */
    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    @Column(name = "prevailing_market_rate", precision = 10, scale = 4, nullable = false)
    private BigDecimal prevailingMarketRate;

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
     * @return the work estimate market rate
     */
    public WorkEstimateMarketRate id(Long id) {
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
     * @return the work estimate market rate
     */
    public WorkEstimateMarketRate workEstimateId(Long workEstimateId) {
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
     * @return the work estimate market rate
     */
    public WorkEstimateMarketRate subEstimateId(Long subEstimateId) {
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
     * @return the work estimate market rate
     */
    public WorkEstimateMarketRate workEstimateItemId(Long workEstimateItemId) {
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
     * @return the work estimate market rate
     */
    public WorkEstimateMarketRate materialMasterId(Long materialMasterId) {
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
     * @return the work estimate market rate
     */
    public WorkEstimateMarketRate difference(BigDecimal difference) {
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
     * Gets the prevailing market rate.
     *
     * @return the prevailing market rate
     */
    public BigDecimal getPrevailingMarketRate() {
        return this.prevailingMarketRate;
    }

    /**
     * Prevailing market rate.
     *
     * @param prevailingMarketRate the prevailing market rate
     * @return the work estimate market rate
     */
    public WorkEstimateMarketRate prevailingMarketRate(BigDecimal prevailingMarketRate) {
        this.prevailingMarketRate = prevailingMarketRate;
        return this;
    }

    /**
     * Sets the prevailing market rate.
     *
     * @param prevailingMarketRate the new prevailing market rate
     */
    public void setPrevailingMarketRate(BigDecimal prevailingMarketRate) {
        this.prevailingMarketRate = prevailingMarketRate;
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
        if (!(o instanceof WorkEstimateMarketRate)) {
            return false;
        }
        return id != null && id.equals(((WorkEstimateMarketRate) o).id);
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
        return "WorkEstimateMarketRate{" +
            "id=" + getId() +
            ", workEstimateId=" + getWorkEstimateId() +
            ", subEstimateId=" + getSubEstimateId() +
            ", workEstimateItemId=" + getWorkEstimateItemId() +
            ", materialMasterId=" + getMaterialMasterId() +
            ", difference=" + getDifference() +
            ", prevailingMarketRate=" + getPrevailingMarketRate() +
            "}";
    }
}
