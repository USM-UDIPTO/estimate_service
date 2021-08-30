package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LineEstimate.
 */
@Entity
@Table(name = "line_estimate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LineEstimate extends EProcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "work_estimate_id", nullable = false)
    private Long workEstimateId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "approx_rate", precision = 21, scale = 2, nullable = false)
    private BigDecimal approxRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LineEstimate id(Long id) {
        this.id = id;
        return this;
    }

    public Long getWorkEstimateId() {
        return this.workEstimateId;
    }

    public LineEstimate workEstimateId(Long workEstimateId) {
        this.workEstimateId = workEstimateId;
        return this;
    }

    public void setWorkEstimateId(Long workEstimateId) {
        this.workEstimateId = workEstimateId;
    }

    public String getName() {
        return this.name;
    }

    public LineEstimate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getApproxRate() {
        return this.approxRate;
    }

    public LineEstimate approxRate(BigDecimal approxRate) {
        this.approxRate = approxRate;
        return this;
    }

    public void setApproxRate(BigDecimal approxRate) {
        this.approxRate = approxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineEstimate)) {
            return false;
        }
        return id != null && id.equals(((LineEstimate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LineEstimate{" +
            "id=" + getId() +
            ", workEstimateId=" + getWorkEstimateId() +
            ", name='" + getName() + "'" +
            ", approxRate=" + getApproxRate() +
            "}";
    }
}
