package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkEstimateMarketRate} entity.
 */
public class WorkEstimateMarketRateDTO implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

    /** The work estimate id. */
    private Long workEstimateId;

    /** The sub estimate id. */
    private Long subEstimateId;
    
    /** The work estimate item id. */
    private Long workEstimateItemId;

    /** The material master id. */
    @NotNull
    private Long materialMasterId;

    /** The difference. */
    @NotNull
    private BigDecimal difference;

    /** The prevailing market rate. */
    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    private BigDecimal prevailingMarketRate;

    //Transient
    private String materialName;
    private BigDecimal quantity;
    private BigDecimal rate;
    private Long uomId;
    private String uomName;
    private BigDecimal total;

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
     * Gets the material master id.
     *
     * @return the material master id
     */
    public Long getMaterialMasterId() {
        return materialMasterId;
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
        return difference;
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
        return prevailingMarketRate;
    }

    /**
     * Sets the prevailing market rate.
     *
     * @param prevailingMarketRate the new prevailing market rate
     */
    public void setPrevailingMarketRate(BigDecimal prevailingMarketRate) {
        this.prevailingMarketRate = prevailingMarketRate;
    }

    //Transient
    public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Long getUomId() {
		return uomId;
	}

	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
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
        if (!(o instanceof WorkEstimateMarketRateDTO)) {
            return false;
        }

        WorkEstimateMarketRateDTO workEstimateMarketRateDTO = (WorkEstimateMarketRateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workEstimateMarketRateDTO.id);
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
        return "WorkEstimateMarketRateDTO{" +
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
