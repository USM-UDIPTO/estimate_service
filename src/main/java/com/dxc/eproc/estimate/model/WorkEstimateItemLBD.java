package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.dxc.eproc.estimate.enumeration.LBDOperation;

// TODO: Auto-generated Javadoc
/**
 * A WorkEstimateItemLBD.
 */
@Entity
@Table(name = "work_estimate_item_lbd")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateItemLBD extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The work estimate item id. */
	@Column(name = "work_estimate_item_id", nullable = false)
	private Long workEstimateItemId;

	/** The lbd particulars. */
	@Column(name = "lbd_particulars")
	private String lbdParticulars;

	/** The lbd nos. */
	@NotNull
	@Column(name = "lbd_nos", precision = 21, scale = 4, nullable = false)
	private BigDecimal lbdNos;

	/** The lbd length. */
	@Column(name = "lbd_length", precision = 21, scale = 4)
	private BigDecimal lbdLength;

	/** The lbd length formula. */
	@Column(name = "lbd_length_formula")
	private String lbdLengthFormula;

	/** The lbd bredth. */
	@Column(name = "lbd_bredth", precision = 21, scale = 4)
	private BigDecimal lbdBredth;

	/** The lbd bredth formula. */
	@Column(name = "lbd_bredth_formula")
	private String lbdBredthFormula;

	/** The lbd depth. */
	@Column(name = "lbd_depth", precision = 21, scale = 4)
	private BigDecimal lbdDepth;

	/** The lbd depth formula. */
	@Column(name = "lbd_depth_formula")
	private String lbdDepthFormula;

	/** The lbd quantity. */
	@NotNull
	@Column(name = "lbd_quantity", precision = 21, scale = 4, nullable = false)
	private BigDecimal lbdQuantity;

	/** The lbd total. */
	@Column(name = "lbd_total", precision = 21, scale = 4)
	private BigDecimal lbdTotal;

	/** The addition deduction. */
	@Enumerated(EnumType.STRING)
	@Column(name = "addition_deduction")
	private LBDOperation additionDeduction;

	/** The calculated yn. */
	@Column(name = "calculated_yn")
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
	 * Id.
	 *
	 * @param id the id
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD id(Long id) {
		this.id = id;
		return this;
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
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD workEstimateItemId(Long workEstimateItemId) {
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
	 * Gets the lbd particulars.
	 *
	 * @return the lbd particulars
	 */
	public String getLbdParticulars() {
		return this.lbdParticulars;
	}

	/**
	 * Lbd particulars.
	 *
	 * @param lbdParticulars the lbd particulars
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdParticulars(String lbdParticulars) {
		this.lbdParticulars = lbdParticulars;
		return this;
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
		return this.lbdNos;
	}

	/**
	 * Lbd nos.
	 *
	 * @param lbdNos the lbd nos
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdNos(BigDecimal lbdNos) {
		this.lbdNos = lbdNos;
		return this;
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
		return this.lbdLength;
	}

	/**
	 * Lbd length.
	 *
	 * @param lbdLength the lbd length
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdLength(BigDecimal lbdLength) {
		this.lbdLength = lbdLength;
		return this;
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
		return this.lbdLengthFormula;
	}

	/**
	 * Lbd length formula.
	 *
	 * @param lbdLengthFormula the lbd length formula
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdLengthFormula(String lbdLengthFormula) {
		this.lbdLengthFormula = lbdLengthFormula;
		return this;
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
		return this.lbdBredth;
	}

	/**
	 * Lbd bredth.
	 *
	 * @param lbdBredth the lbd bredth
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdBredth(BigDecimal lbdBredth) {
		this.lbdBredth = lbdBredth;
		return this;
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
		return this.lbdBredthFormula;
	}

	/**
	 * Lbd bredth formula.
	 *
	 * @param lbdBredthFormula the lbd bredth formula
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdBredthFormula(String lbdBredthFormula) {
		this.lbdBredthFormula = lbdBredthFormula;
		return this;
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
		return this.lbdDepth;
	}

	/**
	 * Lbd depth.
	 *
	 * @param lbdDepth the lbd depth
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdDepth(BigDecimal lbdDepth) {
		this.lbdDepth = lbdDepth;
		return this;
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
		return this.lbdDepthFormula;
	}

	/**
	 * Lbd depth formula.
	 *
	 * @param lbdDepthFormula the lbd depth formula
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdDepthFormula(String lbdDepthFormula) {
		this.lbdDepthFormula = lbdDepthFormula;
		return this;
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
		return this.lbdQuantity;
	}

	/**
	 * Lbd quantity.
	 *
	 * @param lbdQuantity the lbd quantity
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdQuantity(BigDecimal lbdQuantity) {
		this.lbdQuantity = lbdQuantity;
		return this;
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
		return this.lbdTotal;
	}

	/**
	 * Lbd total.
	 *
	 * @param lbdTotal the lbd total
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD lbdTotal(BigDecimal lbdTotal) {
		this.lbdTotal = lbdTotal;
		return this;
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
		return this.additionDeduction;
	}

	/**
	 * Addition deduction.
	 *
	 * @param additionDeduction the addition deduction
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD additionDeduction(LBDOperation additionDeduction) {
		this.additionDeduction = additionDeduction;
		return this;
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
		return this.calculatedYn;
	}

	/**
	 * Calculated yn.
	 *
	 * @param calculatedYn the calculated yn
	 * @return the work estimate item LBD
	 */
	public WorkEstimateItemLBD calculatedYn(Boolean calculatedYn) {
		this.calculatedYn = calculatedYn;
		return this;
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
		if (!(o instanceof WorkEstimateItemLBD)) {
			return false;
		}
		return id != null && id.equals(((WorkEstimateItemLBD) o).id);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		// see
		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
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
		return "WorkEstimateItemLBD{" + "id=" + getId() + ", lbdParticulars='" + getLbdParticulars() + "'" + ", lbdNos="
				+ getLbdNos() + ", lbdLength=" + getLbdLength() + ", lbdLengthFormula='" + getLbdLengthFormula() + "'"
				+ ", lbdBredth=" + getLbdBredth() + ", lbdBredthFormula='" + getLbdBredthFormula() + "'" + ", lbdDepth="
				+ getLbdDepth() + ", lbdDepthFormula='" + getLbdDepthFormula() + "'" + ", lbdQuantity="
				+ getLbdQuantity() + ", lbdTotal=" + getLbdTotal() + ", additionDeduction='" + getAdditionDeduction()
				+ "'" + ", calculatedYn='" + getCalculatedYn() + "'" + "}";
	}
}
