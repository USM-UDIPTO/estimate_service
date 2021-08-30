package com.dxc.eproc.estimate.model;

import com.dxc.eproc.estimate.enumeration.OverHeadType;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEstimateOverhead.
 */
@Entity
@Table(name = "work_estimate_overhead")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateOverhead extends EProcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "work_estimate_id", nullable = false)
    private Long workEstimateId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "over_head_type", nullable = false)
    private OverHeadType overHeadType;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "over_head_value", precision = 21, scale = 2, nullable = false)
    private BigDecimal overHeadValue;

    @NotNull
    @Column(name = "fixed_yn", nullable = false)
    private Boolean fixedYn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkEstimateOverhead id(Long id) {
        this.id = id;
        return this;
    }

    public Long getWorkEstimateId() {
        return this.workEstimateId;
    }

    public WorkEstimateOverhead workEstimateId(Long workEstimateId) {
        this.workEstimateId = workEstimateId;
        return this;
    }

    public void setWorkEstimateId(Long workEstimateId) {
        this.workEstimateId = workEstimateId;
    }

    public OverHeadType getOverHeadType() {
        return this.overHeadType;
    }

    public WorkEstimateOverhead overHeadType(OverHeadType overHeadType) {
        this.overHeadType = overHeadType;
        return this;
    }

    public void setOverHeadType(OverHeadType overHeadType) {
        this.overHeadType = overHeadType;
    }

    public String getName() {
        return this.name;
    }

    public WorkEstimateOverhead name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOverHeadValue() {
        return this.overHeadValue;
    }

    public WorkEstimateOverhead overHeadValue(BigDecimal overHeadValue) {
        this.overHeadValue = overHeadValue;
        return this;
    }

    public void setOverHeadValue(BigDecimal overHeadValue) {
        this.overHeadValue = overHeadValue;
    }

    public Boolean getFixedYn() {
        return this.fixedYn;
    }

    public WorkEstimateOverhead fixedYn(Boolean fixedYn) {
        this.fixedYn = fixedYn;
        return this;
    }

    public void setFixedYn(Boolean fixedYn) {
        this.fixedYn = fixedYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkEstimateOverhead)) {
            return false;
        }
        return id != null && id.equals(((WorkEstimateOverhead) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkEstimateOverhead{" +
            "id=" + getId() +
            ", workEstimateId=" + getWorkEstimateId() +
            ", overHeadType='" + getOverHeadType() + "'" +
            ", name='" + getName() + "'" +
            ", overHeadValue=" + getOverHeadValue() +
            ", fixedYn='" + getFixedYn() + "'" +
            "}";
    }
}
