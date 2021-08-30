package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

import com.dxc.eproc.estimate.enumeration.LBDOperation;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkEstimateItemLBD}
 * entity.
 */
public class WorkEstimateItemLBDDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate item id. */
	private Long workEstimateItemId;

	/** The lbd particulars. */
	@NotBlank(message = "{workEstimateItemLBD.lbdParticulars.notBlank}")
	@Pattern(regexp = "^(?! )[A-Za-z0-9 \\s-+\\\\\\/&',.]*(?<! )$", message = "{workEstimateItemLBD.lbdParticulars.pattern}")
	private String lbdParticulars;

	/** The lbd nos. */
	@NotNull(message = "{workEstimateItemLBD.lbdNos.notNull}")
	private BigDecimal lbdNos;

	/** The lbd length. */
	private BigDecimal lbdLength;

	/** The lbd length formula. */
	private String lbdLengthFormula;

	/** The lbd bredth. */
	private BigDecimal lbdBredth;

	/** The lbd bredth formula. */
	private String lbdBredthFormula;

	/** The lbd depth. */
	private BigDecimal lbdDepth;

	/** The lbd depth formula. */
	private String lbdDepthFormula;

	/** The lbd quantity. */
	private BigDecimal lbdQuantity;

	/** The lbd total. */
	private BigDecimal lbdTotal;

	/** The addition deduction. */
	@NotNull(message = "{workEstimateItemLBD.additionDeduction.notNull}")
	private LBDOperation additionDeduction;

	/** The calculated yn. */
	@NotNull(message = "{workEstimateItemLBD.calculatedYn.notNull}")
	private Boolean calculatedYn;

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
	 * Gets the lbd particulars.
	 *
	 * @return the lbd particulars
	 */
	public String getLbdParticulars() {
		return lbdParticulars;
	}

	/**
	 * Sets the lbd particulars.
	 *
	 * @param lbdParticulars the new lbd particulars
	 */
	public void setLbdParticulars(String lbdParticulars) {
		this.lbdParticulars = lbdParticulars;
	}

	/**
	 * Gets the lbd nos.
	 *
	 * @return the lbd nos
	 */
	public BigDecimal getLbdNos() {
		return lbdNos;
	}

	/**
	 * Sets the lbd nos.
	 *
	 * @param lbdNos the new lbd nos
	 */
	public void setLbdNos(BigDecimal lbdNos) {
		this.lbdNos = lbdNos;
	}

	/**
	 * Gets the lbd length.
	 *
	 * @return the lbd length
	 */
	public BigDecimal getLbdLength() {
		return lbdLength;
	}

	/**
	 * Sets the lbd length.
	 *
	 * @param lbdLength the new lbd length
	 */
	public void setLbdLength(BigDecimal lbdLength) {
		this.lbdLength = lbdLength;
	}

	/**
	 * Gets the lbd length formula.
	 *
	 * @return the lbd length formula
	 */
	public String getLbdLengthFormula() {
		return lbdLengthFormula;
	}

	/**
	 * Sets the lbd length formula.
	 *
	 * @param lbdLengthFormula the new lbd length formula
	 */
	public void setLbdLengthFormula(String lbdLengthFormula) {
		this.lbdLengthFormula = lbdLengthFormula;
	}

	/**
	 * Gets the lbd bredth.
	 *
	 * @return the lbd bredth
	 */
	public BigDecimal getLbdBredth() {
		return lbdBredth;
	}

	/**
	 * Sets the lbd bredth.
	 *
	 * @param lbdBredth the new lbd bredth
	 */
	public void setLbdBredth(BigDecimal lbdBredth) {
		this.lbdBredth = lbdBredth;
	}

	/**
	 * Gets the lbd bredth formula.
	 *
	 * @return the lbd bredth formula
	 */
	public String getLbdBredthFormula() {
		return lbdBredthFormula;
	}

	/**
	 * Sets the lbd bredth formula.
	 *
	 * @param lbdBredthFormula the new lbd bredth formula
	 */
	public void setLbdBredthFormula(String lbdBredthFormula) {
		this.lbdBredthFormula = lbdBredthFormula;
	}

	/**
	 * Gets the lbd depth.
	 *
	 * @return the lbd depth
	 */
	public BigDecimal getLbdDepth() {
		return lbdDepth;
	}

	/**
	 * Sets the lbd depth.
	 *
	 * @param lbdDepth the new lbd depth
	 */
	public void setLbdDepth(BigDecimal lbdDepth) {
		this.lbdDepth = lbdDepth;
	}

	/**
	 * Gets the lbd depth formula.
	 *
	 * @return the lbd depth formula
	 */
	public String getLbdDepthFormula() {
		return lbdDepthFormula;
	}

	/**
	 * Sets the lbd depth formula.
	 *
	 * @param lbdDepthFormula the new lbd depth formula
	 */
	public void setLbdDepthFormula(String lbdDepthFormula) {
		this.lbdDepthFormula = lbdDepthFormula;
	}

	/**
	 * Gets the lbd quantity.
	 *
	 * @return the lbd quantity
	 */
	public BigDecimal getLbdQuantity() {
		return lbdQuantity;
	}

	/**
	 * Sets the lbd quantity.
	 *
	 * @param lbdQuantity the new lbd quantity
	 */
	public void setLbdQuantity(BigDecimal lbdQuantity) {
		this.lbdQuantity = lbdQuantity;
	}

	/**
	 * Gets the lbd total.
	 *
	 * @return the lbd total
	 */
	public BigDecimal getLbdTotal() {
		return lbdTotal;
	}

	/**
	 * Sets the lbd total.
	 *
	 * @param lbdTotal the new lbd total
	 */
	public void setLbdTotal(BigDecimal lbdTotal) {
		this.lbdTotal = lbdTotal;
	}

	/**
	 * Gets the addition deduction.
	 *
	 * @return the addition deduction
	 */
	public LBDOperation getAdditionDeduction() {
		return additionDeduction;
	}

	/**
	 * Sets the addition deduction.
	 *
	 * @param additionDeduction the new addition deduction
	 */
	public void setAdditionDeduction(LBDOperation additionDeduction) {
		this.additionDeduction = additionDeduction;
	}

	/**
	 * Gets the calculated yn.
	 *
	 * @return the calculated yn
	 */
	public Boolean getCalculatedYn() {
		return calculatedYn;
	}

	/**
	 * Sets the calculated yn.
	 *
	 * @param calculatedYn the new calculated yn
	 */
	public void setCalculatedYn(Boolean calculatedYn) {
		this.calculatedYn = calculatedYn;
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
		if (!(o instanceof WorkEstimateItemLBDDTO)) {
			return false;
		}

		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = (WorkEstimateItemLBDDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateItemLBDDTO.id);
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
		return "WorkEstimateItemLBDDTO{" + "id=" + getId() + ", workEstimateItemId=" + getWorkEstimateItemId()
				+ ", lbdParticulars='" + getLbdParticulars() + "'" + ", lbdNos=" + getLbdNos() + ", lbdLength="
				+ getLbdLength() + ", lbdLengthFormula='" + getLbdLengthFormula() + "'" + ", lbdBredth="
				+ getLbdBredth() + ", lbdBredthFormula='" + getLbdBredthFormula() + "'" + ", lbdDepth=" + getLbdDepth()
				+ ", lbdDepthFormula='" + getLbdDepthFormula() + "'" + ", lbdQuantity=" + getLbdQuantity()
				+ ", lbdTotal=" + getLbdTotal() + ", additionDeduction='" + getAdditionDeduction() + "'"
				+ ", calculatedYn='" + getCalculatedYn() + "'" + "}";
	}
}
