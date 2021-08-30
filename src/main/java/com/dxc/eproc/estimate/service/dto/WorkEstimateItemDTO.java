package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkEstimateItem} entity.
 */
public class WorkEstimateItemDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The sub estimate id. */
	private Long subEstimateId;

	/** The cat work sor item id. */
	private Long catWorkSorItemId;

	/** The work category id. */
	private Long categoryId;

	/** The category name. */
	private String categoryName;

	/** The entry order. */
	private Integer entryOrder;

	/** The item code. */
	private String itemCode;

	/** The description. */
	private String description;

	/** The uom id. */
	private Long uomId;

	/** The uom name. */
	private String uomName;

	/** The base rate. */
	private BigDecimal baseRate;

	/** The final rate. */
	private BigDecimal finalRate;

	/** The quantity. */
	private BigDecimal quantity;

	/** The floor number. */
	private Integer floorNumber;

	/** The labour rate. */
	private BigDecimal labourRate;

	/** The lbd performed yn. */
	private Boolean lbdPerformedYn;

	/** The ra performed yn. */
	private Boolean raPerformedYn;

	/** The floor yn. */
	private boolean floorYn;

	/** The net amount. */
	private BigDecimal netAmount;

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
	 * Gets the cat work sor item id.
	 *
	 * @return the cat work sor item id
	 */
	public Long getCatWorkSorItemId() {
		return catWorkSorItemId;
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
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the category id.
	 *
	 * @param categoryId the new category id
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Gets the entry order.
	 *
	 * @return the entry order
	 */
	public Integer getEntryOrder() {
		return entryOrder;
	}

	/**
	 * Sets the entry order.
	 *
	 * @param entryOrder the new entry order
	 */
	public void setEntryOrder(Integer entryOrder) {
		this.entryOrder = entryOrder;
	}

	/**
	 * Gets the item code.
	 *
	 * @return the item code
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * Sets the item code.
	 *
	 * @param itemCode the new item code
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the uom id.
	 *
	 * @return the uom id
	 */
	public Long getUomId() {
		return uomId;
	}

	/**
	 * Sets the uom id.
	 *
	 * @param uomId the new uom id
	 */
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}

	/**
	 * Gets the base rate.
	 *
	 * @return the base rate
	 */
	public BigDecimal getBaseRate() {
		return baseRate;
	}

	/**
	 * Sets the base rate.
	 *
	 * @param baseRate the new base rate
	 */
	public void setBaseRate(BigDecimal baseRate) {
		this.baseRate = baseRate;
	}

	/**
	 * Gets the final rate.
	 *
	 * @return the final rate
	 */
	public BigDecimal getFinalRate() {
		return finalRate;
	}

	/**
	 * Sets the final rate.
	 *
	 * @param finalRate the new final rate
	 */
	public void setFinalRate(BigDecimal finalRate) {
		this.finalRate = finalRate;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the floor number.
	 *
	 * @return the floor number
	 */
	public Integer getFloorNumber() {
		return floorNumber;
	}

	/**
	 * Sets the floor number.
	 *
	 * @param floorNumber the new floor number
	 */
	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	/**
	 * Gets the labour rate.
	 *
	 * @return the labour rate
	 */
	public BigDecimal getLabourRate() {
		return labourRate;
	}

	/**
	 * Sets the labour rate.
	 *
	 * @param labourRate the new labour rate
	 */
	public void setLabourRate(BigDecimal labourRate) {
		this.labourRate = labourRate;
	}

	/**
	 * Gets the lbd performed yn.
	 *
	 * @return the lbd performed yn
	 */
	public Boolean getLbdPerformedYn() {
		return lbdPerformedYn;
	}

	/**
	 * Sets the lbd performed yn.
	 *
	 * @param lbdPerformedYn the new lbd performed yn
	 */
	public void setLbdPerformedYn(Boolean lbdPerformedYn) {
		this.lbdPerformedYn = lbdPerformedYn;
	}

	/**
	 * Gets the ra performed yn.
	 *
	 * @return the ra performed yn
	 */
	public Boolean getRaPerformedYn() {
		return raPerformedYn;
	}

	/**
	 * Sets the ra performed yn.
	 *
	 * @param raPerformedYn the new ra performed yn
	 */
	public void setRaPerformedYn(Boolean raPerformedYn) {
		this.raPerformedYn = raPerformedYn;
	}

	/**
	 * Checks if is floor yn.
	 *
	 * @return true, if is floor yn
	 */
	public boolean isFloorYn() {
		return floorYn;
	}

	/**
	 * Sets the floor yn.
	 *
	 * @param floorYn the new floor yn
	 */
	public void setFloorYn(boolean floorYn) {
		this.floorYn = floorYn;
	}

	/**
	 * Gets the uom name.
	 *
	 * @return the uom name
	 */
	public String getUomName() {
		return uomName;
	}

	/**
	 * Sets the uom name.
	 *
	 * @param uomName the new uom name
	 */
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	/**
	 * Gets the category name.
	 *
	 * @return the category name
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Sets the category name.
	 *
	 * @param categoryName the new category name
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * Gets the net amount.
	 *
	 * @return the net amount
	 */
	public BigDecimal getNetAmount() {
		netAmount = BigDecimal.ZERO;
		if (quantity == null) {
			return netAmount = BigDecimal.ZERO;
		}
		if (finalRate == null) {
			if (baseRate == null) {
				return netAmount = BigDecimal.ZERO;
			} else {
				return netAmount = quantity.multiply(baseRate);
			}
		}
		netAmount = quantity.multiply(finalRate);
		return netAmount;
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
		if (!(o instanceof WorkEstimateItemDTO)) {
			return false;
		}

		WorkEstimateItemDTO workEstimateItemDTO = (WorkEstimateItemDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateItemDTO.id);
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
		return "WorkEstimateItemDTO{" + "id=" + getId() + ", subEstimateId=" + getSubEstimateId()
				+ ", catWorkSorItemId=" + getCatWorkSorItemId() + ", categoryId=" + getCategoryId() + ", entryOrder="
				+ getEntryOrder() + ", itemCode='" + getItemCode() + "'" + ", description='" + getDescription() + "'"
				+ ", uomId=" + getUomId() + ", baseRate=" + getBaseRate() + ", finalRate=" + getFinalRate()
				+ ", quantity=" + getQuantity() + ", floorNumber=" + getFloorNumber() + ", labourRate="
				+ getLabourRate() + ", lbdPerformedYn='" + getLbdPerformedYn() + "'" + ", raPerformedYn='"
				+ getRaPerformedYn() + "'" + "}";
	}
}
