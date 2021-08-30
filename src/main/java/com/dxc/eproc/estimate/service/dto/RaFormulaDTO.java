package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.util.Objects;

import com.dxc.eproc.estimate.enumeration.WorkType;

/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.RaFormula} entity.
 */
public class RaFormulaDTO implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

    /** The dept id. */
    private Long deptId;

    /** The work type. */
    private WorkType workType;

    /** The formula. */
    private String formula;

    /** The aw formula. */
    private String awFormula;

    /** The royalty formula. */
    private String royaltyFormula;

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
     * Gets the dept id.
     *
     * @return the dept id
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * Sets the dept id.
     *
     * @param deptId the new dept id
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * Gets the work type.
     *
     * @return the work type
     */
    public WorkType getWorkType() {
        return workType;
    }

    /**
     * Sets the work type.
     *
     * @param workType the new work type
     */
    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    /**
     * Gets the formula.
     *
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * Sets the formula.
     *
     * @param formula the new formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * Gets the aw formula.
     *
     * @return the aw formula
     */
    public String getAwFormula() {
        return awFormula;
    }

    /**
     * Sets the aw formula.
     *
     * @param awFormula the new aw formula
     */
    public void setAwFormula(String awFormula) {
        this.awFormula = awFormula;
    }

    /**
     * Gets the royalty formula.
     *
     * @return the royalty formula
     */
    public String getRoyaltyFormula() {
        return royaltyFormula;
    }

    /**
     * Sets the royalty formula.
     *
     * @param royaltyFormula the new royalty formula
     */
    public void setRoyaltyFormula(String royaltyFormula) {
        this.royaltyFormula = royaltyFormula;
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
        if (!(o instanceof RaFormulaDTO)) {
            return false;
        }

        RaFormulaDTO raFormulaDTO = (RaFormulaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, raFormulaDTO.id);
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
        return "RaFormulaDTO{" +
            "id=" + getId() +
            ", deptId=" + getDeptId() +
            ", workType=" + getWorkType() +
            ", formula='" + getFormula() + "'" +
            ", awFormula='" + getAwFormula() + "'" +
            ", royaltyFormula='" + getRoyaltyFormula() + "'" +
            "}";
    }
}
