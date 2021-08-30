package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dxc.eproc.estimate.domain.WorkEstimateAdditionalCharges} entity.
 */
public class WorkEstimateAdditionalChargesDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long workEstimateItemId;

    @NotNull
    private String additionalChargesDesc;

    @NotNull
    private BigDecimal additionalChargesRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkEstimateItemId() {
        return workEstimateItemId;
    }

    public void setWorkEstimateItemId(Long workEstimateItemId) {
        this.workEstimateItemId = workEstimateItemId;
    }

    public String getAdditionalChargesDesc() {
        return additionalChargesDesc;
    }

    public void setAdditionalChargesDesc(String additionalChargesDesc) {
        this.additionalChargesDesc = additionalChargesDesc;
    }

    public BigDecimal getAdditionalChargesRate() {
        return additionalChargesRate;
    }

    public void setAdditionalChargesRate(BigDecimal additionalChargesRate) {
        this.additionalChargesRate = additionalChargesRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkEstimateAdditionalChargesDTO)) {
            return false;
        }

        WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = (WorkEstimateAdditionalChargesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workEstimateAdditionalChargesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEstimateAdditionalChargesDTO{" +
            "id=" + getId() +
            ", workEstimateItemId=" + getWorkEstimateItemId() +
            ", additionalChargesDesc='" + getAdditionalChargesDesc() + "'" +
            ", additionalChargesRate=" + getAdditionalChargesRate() +
            "}";
    }
}
