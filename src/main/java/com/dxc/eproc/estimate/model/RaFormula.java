package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.dxc.eproc.estimate.enumeration.WorkType;

/**
 * A RaFormula.
 */
@Entity
@Table(name = "ra_formula")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RaFormula extends EProcModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dept_id", nullable = false)
    private Long deptId;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_type")
    private WorkType workType;

    @Column(name = "formula", length = 4000)
    private String formula;

    @Column(name = "aw_formula")
    private String awFormula;

    @Column(name = "royalty_formula")
    private String royaltyFormula;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RaFormula id(Long id) {
        this.id = id;
        return this;
    }

    public Long getDeptId() {
        return this.deptId;
    }

    public RaFormula deptId(Long deptId) {
        this.deptId = deptId;
        return this;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public WorkType getWorkType() {
        return this.workType;
    }

    public RaFormula workType(WorkType workType) {
        this.workType = workType;
        return this;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public String getFormula() {
        return this.formula;
    }

    public RaFormula formula(String formula) {
        this.formula = formula;
        return this;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getAwFormula() {
        return this.awFormula;
    }

    public RaFormula awFormula(String awFormula) {
        this.awFormula = awFormula;
        return this;
    }

    public void setAwFormula(String awFormula) {
        this.awFormula = awFormula;
    }

    public String getRoyaltyFormula() {
        return this.royaltyFormula;
    }

    public RaFormula royaltyFormula(String royaltyFormula) {
        this.royaltyFormula = royaltyFormula;
        return this;
    }

    public void setRoyaltyFormula(String royaltyFormula) {
        this.royaltyFormula = royaltyFormula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaFormula)) {
            return false;
        }
        return id != null && id.equals(((RaFormula) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RaFormula{" +
            "id=" + getId() +
            ", deptId=" + getDeptId() +
            ", workType=" + getWorkType() +
            ", formula='" + getFormula() + "'" +
            ", awFormula='" + getAwFormula() + "'" +
            ", royaltyFormula='" + getRoyaltyFormula() + "'" +
            "}";
    }
}
