package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * A WorkEstimateItem.
 */
@Entity
@Table(name = "work_estimate_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
public class WorkEstimateItem extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The sub estimate id. */
	@NotNull
	@Column(name = "sub_estimate_id", nullable = false)
	private Long subEstimateId;

	/** The cat work sor item id. */
	@Column(name = "cat_work_sor_item_id")
	private Long catWorkSorItemId;

	/** The category id. */
	@Column(name = "category_id")
	private Long categoryId;

	/** The category name. */
	@Column(name = "category_name")
	private String categoryName;

	/** The entry order. */
	@Column(name = "entry_order")
	private Integer entryOrder;

	/** The item code. */
	@NotNull
	@Column(name = "item_code", nullable = false)
	private String itemCode;

	/** The description. */
	@NotNull
	@Column(name = "description", nullable = false)
	private String description;

	/** The uom id. */
	@NotNull
	@Column(name = "uom_id", nullable = false)
	private Long uomId;

	/** The uom name. */
	@NotNull
	@Column(name = "uom_name")
	private String uomName;

	/** The base rate. */
	@NotNull
	@Column(name = "base_rate", precision = 21, scale = 4, nullable = false)
	private BigDecimal baseRate;

	/** The final rate. */
	@Column(name = "final_rate", precision = 21, scale = 4)
	private BigDecimal finalRate;

	/** The quantity. */
	@Column(name = "quantity", precision = 21, scale = 4)
	private BigDecimal quantity;

	/** The floor number. */
	@Column(name = "floor_number")
	private Integer floorNumber;

	/** The labour rate. */
	@Column(name = "labour_rate", precision = 21, scale = 4)
	private BigDecimal labourRate;

	/** The lbd performed yn. */
	@Column(name = "lbd_performed_yn")
	private Boolean lbdPerformedYn;

	/** The ra performed yn. */
	@Column(name = "ra_performed_yn")
	private Boolean raPerformedYn;

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
		return this.subEstimateId;
	}

	/**
	 * Sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the work estimate item
	 */
	public WorkEstimateItem subEstimateId(Long subEstimateId) {
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
	 * Id.
	 *
	 * @param id the id
	 * @return the work estimate item
	 */
	public WorkEstimateItem id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the cat work sor item id.
	 *
	 * @return the cat work sor item id
	 */
	public Long getCatWorkSorItemId() {
		return this.catWorkSorItemId;
	}

	/**
	 * Cat work sor item id.
	 *
	 * @param catWorkSorItemId the cat work sor item id
	 * @return the work estimate item
	 */
	public WorkEstimateItem catWorkSorItemId(Long catWorkSorItemId) {
		this.catWorkSorItemId = catWorkSorItemId;
		return this;
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
		return this.categoryId;
	}

	/**
	 * Category id.
	 *
	 * @param categoryId the category id
	 * @return the work estimate item
	 */
	public WorkEstimateItem categoryId(Long categoryId) {
		this.categoryId = categoryId;
		return this;
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
		return this.entryOrder;
	}

	/**
	 * Entry order.
	 *
	 * @param entryOrder the entry order
	 * @return the work estimate item
	 */
	public WorkEstimateItem entryOrder(Integer entryOrder) {
		this.entryOrder = entryOrder;
		return this;
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
		return this.itemCode;
	}

	/**
	 * Item code.
	 *
	 * @param itemCode the item code
	 * @return the work estimate item
	 */
	public WorkEstimateItem itemCode(String itemCode) {
		this.itemCode = itemCode;
		return this;
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
		return this.description;
	}

	/**
	 * Description.
	 *
	 * @param description the description
	 * @return the work estimate item
	 */
	public WorkEstimateItem description(String description) {
		this.description = description;
		return this;
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
		return this.uomId;
	}

	/**
	 * Uom id.
	 *
	 * @param uomId the uom id
	 * @return the work estimate item
	 */
	public WorkEstimateItem uomId(Long uomId) {
		this.uomId = uomId;
		return this;
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
		return this.baseRate;
	}

	/**
	 * Base rate.
	 *
	 * @param baseRate the base rate
	 * @return the work estimate item
	 */
	public WorkEstimateItem baseRate(BigDecimal baseRate) {
		this.baseRate = baseRate;
		return this;
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
		return this.finalRate;
	}

	/**
	 * Final rate.
	 *
	 * @param finalRate the final rate
	 * @return the work estimate item
	 */
	public WorkEstimateItem finalRate(BigDecimal finalRate) {
		this.finalRate = finalRate;
		return this;
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
		return this.quantity;
	}

	/**
	 * Quantity.
	 *
	 * @param quantity the quantity
	 * @return the work estimate item
	 */
	public WorkEstimateItem quantity(BigDecimal quantity) {
		this.quantity = quantity;
		return this;
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
		return this.floorNumber;
	}

	/**
	 * Floor number.
	 *
	 * @param floorNumber the floor number
	 * @return the work estimate item
	 */
	public WorkEstimateItem floorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
		return this;
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
		return this.labourRate;
	}

	/**
	 * Labour rate.
	 *
	 * @param labourRate the labour rate
	 * @return the work estimate item
	 */
	public WorkEstimateItem labourRate(BigDecimal labourRate) {
		this.labourRate = labourRate;
		return this;
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
		return this.lbdPerformedYn;
	}

	/**
	 * Lbd performed yn.
	 *
	 * @param lbdPerformedYn the lbd performed yn
	 * @return the work estimate item
	 */
	public WorkEstimateItem lbdPerformedYn(Boolean lbdPerformedYn) {
		this.lbdPerformedYn = lbdPerformedYn;
		return this;
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
		return this.raPerformedYn;
	}

	/**
	 * Ra performed yn.
	 *
	 * @param raPerformedYn the ra performed yn
	 * @return the work estimate item
	 */
	public WorkEstimateItem raPerformedYn(Boolean raPerformedYn) {
		this.raPerformedYn = raPerformedYn;
		return this;
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
	 * @param uomName
	 * @return
	 */
	public WorkEstimateItem uomName(String uomName) {
		this.uomName = uomName;
		return this;
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
	
	public WorkEstimateItem categoryName(String categoryName) {
		this.categoryName = categoryName;
		return this;
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
		if (!(o instanceof WorkEstimateItem)) {
			return false;
		}
		return id != null && id.equals(((WorkEstimateItem) o).id);
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
		return "WorkEstimateItem{" + "id=" + getId() + ", subEstimateId=" + getSubEstimateId() + ", catWorkSorItemId="
				+ getCatWorkSorItemId() + ", categoryId=" + getCategoryId() + ", entryOrder=" + getEntryOrder()
				+ ", itemCode='" + getItemCode() + "'" + ", description='" + getDescription() + "'" + ", uomId="
				+ getUomId() + ", baseRate=" + getBaseRate() + ", finalRate=" + getFinalRate() + ", quantity="
				+ getQuantity() + ", floorNumber=" + getFloorNumber() + ", labourRate=" + getLabourRate()
				+ ", lbdPerformedYn='" + getLbdPerformedYn() + "'" + ", raPerformedYn='" + getRaPerformedYn() + "'"
				+ "}";
	}
}
