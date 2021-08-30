package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;

// TODO: Auto-generated Javadoc
/**
 * A WorkEstimate.
 */
@Entity
@Table(name = "work_estimate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
public class WorkEstimate extends EProcModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The work estimate number. */

	@Column(name = "work_estimate_number", updatable = false, nullable = false, unique = true)
	private String workEstimateNumber;

	/** The status. */

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private WorkEstimateStatus status;

	/** The dept id. */

	@Column(name = "dept_id", nullable = false)
	private Long deptId;

	/** The location id. */

	@Column(name = "location_id", nullable = false)
	private Long locationId;

	/** The file number. */

	@Column(name = "file_number", nullable = false)
	private String fileNumber;

	/** The name. */

	@Column(name = "name", nullable = false)
	private String name;

	/** The description. */
	@Column(name = "description")
	private String description;

	/** The estimate type id. */

	@Column(name = "estimate_type_id", nullable = false)
	private Long estimateTypeId;

	/** The work type id. */

	@Column(name = "work_type_id", nullable = false)
	private Long workTypeId;

	/** The work category id. */

	@Column(name = "work_category_id", nullable = false)
	private Long workCategoryId;

	/** The work category attribute. */
	@Column(name = "work_category_attribute")
	private Long workCategoryAttribute;

	/** The work category attribute value. */
	@Column(name = "work_category_attribute_value", precision = 21, scale = 4)
	private BigDecimal workCategoryAttributeValue;

	/** The admin sanction accorded yn. */
	@Column(name = "admin_sanction_accorded_yn")
	private Boolean adminSanctionAccordedYn;

	/** The tech sanction accorded yn. */
	@Column(name = "tech_sanction_accorded_yn")
	private Boolean techSanctionAccordedYn;

	/** The line estimate total. */
	@Column(name = "line_estimate_total", precision = 21, scale = 4)
	private BigDecimal lineEstimateTotal;

	/** The estimate total. */
	@Column(name = "estimate_total", precision = 21, scale = 4)
	private BigDecimal estimateTotal;

	/** The group lumpsum total. */
	@Column(name = "group_lumpsum_total", precision = 21, scale = 4)
	private BigDecimal groupLumpsumTotal;

	/** The group overhead total. */
	@Column(name = "group_overhead_total", precision = 21, scale = 4)
	private BigDecimal groupOverheadTotal;

	/** The normal ovehead total. */
	@Column(name = "normal_overhead_total", precision = 21, scale = 4)
	private BigDecimal normalOveheadTotal;

	/** The addl ovehead total. */
	@Column(name = "addl_overhead_total", precision = 21, scale = 4)
	private BigDecimal addlOveheadTotal;

	/** The admin sanction ref number. */
	@Column(name = "admin_sanction_ref_number")
	private String adminSanctionRefNumber;

	/** The admin sanction ref date. */
	@Column(name = "admin_sanction_ref_date")
	private ZonedDateTime adminSanctionRefDate;

	/** The admin sanctioned date. */
	@Column(name = "admin_sanctioned_date")
	private ZonedDateTime adminSanctionedDate;

	/** The tech sanction ref number. */
	@Column(name = "tech_sanction_ref_number")
	private String techSanctionRefNumber;

	/** The tech sanctioned date. */
	@Column(name = "tech_sanctioned_date")
	private ZonedDateTime techSanctionedDate;

	/** The approved by. */
	@Column(name = "approved_by")
	private String approvedBy;

	/** The approved ts. */
	@Column(name = "approved_ts")
	private ZonedDateTime approvedTs;

	/** The hkrdb funded yn. */
	@Column(name = "hkrdb_funded_yn")
	private Boolean hkrdbFundedYn;

	/** The scheme id. */
	@Column(name = "scheme_id")
	private Long schemeId;

	/** The approved budget yn. */

	@Column(name = "approved_budget_yn", nullable = false)
	private Boolean approvedBudgetYn;

	/** The grant allocated amount. */
	@Column(name = "grant_allocated_amount", precision = 21, scale = 4)
	private BigDecimal grantAllocatedAmount;

	/** The document reference. */
	@Column(name = "document_reference")
	private String documentReference;

	/** The provisional amount. */
	@Column(name = "provisional_amount", precision = 21, scale = 4)
	private BigDecimal provisionalAmount;

	/** The head of account. */
	@Column(name = "head_of_account")
	private String headOfAccount;

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
	 * @return the work estimate
	 */
	public WorkEstimate id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Gets the work estimate number.
	 *
	 * @return the work estimate number
	 */
	public String getWorkEstimateNumber() {
		return this.workEstimateNumber;
	}

	/**
	 * Work estimate number.
	 *
	 * @param workEstimateNumber the work estimate number
	 * @return the work estimate
	 */
	public WorkEstimate workEstimateNumber(String workEstimateNumber) {
		this.workEstimateNumber = workEstimateNumber;
		return this;
	}

	/**
	 * Sets the work estimate number.
	 *
	 * @param workEstimateNumber the new work estimate number
	 */
	public void setWorkEstimateNumber(String workEstimateNumber) {
		this.workEstimateNumber = workEstimateNumber;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public WorkEstimateStatus getStatus() {
		return this.status;
	}

	/**
	 * Status.
	 *
	 * @param status the status
	 * @return the work estimate
	 */
	public WorkEstimate status(WorkEstimateStatus status) {
		this.status = status;
		return this;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(WorkEstimateStatus status) {
		this.status = status;
	}

	/**
	 * Gets the dept id.
	 *
	 * @return the dept id
	 */
	public Long getDeptId() {
		return this.deptId;
	}

	/**
	 * Dept id.
	 *
	 * @param deptId the dept id
	 * @return the work estimate
	 */
	public WorkEstimate deptId(Long deptId) {
		this.deptId = deptId;
		return this;
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
	 * Gets the location id.
	 *
	 * @return the location id
	 */
	public Long getLocationId() {
		return this.locationId;
	}

	/**
	 * Location id.
	 *
	 * @param locationId the location id
	 * @return the work estimate
	 */
	public WorkEstimate locationId(Long locationId) {
		this.locationId = locationId;
		return this;
	}

	/**
	 * Sets the location id.
	 *
	 * @param locationId the new location id
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	/**
	 * Gets the file number.
	 *
	 * @return the file number
	 */
	public String getFileNumber() {
		return this.fileNumber;
	}

	/**
	 * File number.
	 *
	 * @param fileNumber the file number
	 * @return the work estimate
	 */
	public WorkEstimate fileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
		return this;
	}

	/**
	 * Sets the file number.
	 *
	 * @param fileNumber the new file number
	 */
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Name.
	 *
	 * @param name the name
	 * @return the work estimate
	 */
	public WorkEstimate name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the work estimate
	 */
	public WorkEstimate description(String description) {
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
	 * Gets the estimate type id.
	 *
	 * @return the estimate type id
	 */
	public Long getEstimateTypeId() {
		return this.estimateTypeId;
	}

	/**
	 * Estimate type id.
	 *
	 * @param estimateTypeId the estimate type id
	 * @return the work estimate
	 */
	public WorkEstimate estimateTypeId(Long estimateTypeId) {
		this.estimateTypeId = estimateTypeId;
		return this;
	}

	/**
	 * Sets the estimate type id.
	 *
	 * @param estimateTypeId the new estimate type id
	 */
	public void setEstimateTypeId(Long estimateTypeId) {
		this.estimateTypeId = estimateTypeId;
	}

	/**
	 * Gets the wotk type id.
	 *
	 * @return the wotk type id
	 */
	public Long getWorkTypeId() {
		return this.workTypeId;
	}

	/**
	 * Wotk type id.
	 *
	 * @param workTypeId the work type id
	 * @return the work estimate
	 */
	public WorkEstimate workTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
		return this;
	}

	/**
	 * Sets the wotk type id.
	 *
	 * @param workTypeId the new work type id
	 */
	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}

	/**
	 * Gets the work category id.
	 *
	 * @return the work category id
	 */
	public Long getWorkCategoryId() {
		return this.workCategoryId;
	}

	/**
	 * Work category id.
	 *
	 * @param workCategoryId the work category id
	 * @return the work estimate
	 */
	public WorkEstimate workCategoryId(Long workCategoryId) {
		this.workCategoryId = workCategoryId;
		return this;
	}

	/**
	 * Sets the work category id.
	 *
	 * @param workCategoryId the new work category id
	 */
	public void setWorkCategoryId(Long workCategoryId) {
		this.workCategoryId = workCategoryId;
	}

	/**
	 * Gets the work category attribute.
	 *
	 * @return the work category attribute
	 */
	public Long getWorkCategoryAttribute() {
		return this.workCategoryAttribute;
	}

	/**
	 * Work category attribute.
	 *
	 * @param workCategoryAttribute the work category attribute
	 * @return the work estimate
	 */
	public WorkEstimate workCategoryAttribute(Long workCategoryAttribute) {
		this.workCategoryAttribute = workCategoryAttribute;
		return this;
	}

	/**
	 * Sets the work category attribute.
	 *
	 * @param workCategoryAttribute the new work category attribute
	 */
	public void setWorkCategoryAttribute(Long workCategoryAttribute) {
		this.workCategoryAttribute = workCategoryAttribute;
	}

	/**
	 * Gets the work category attribute value.
	 *
	 * @return the work category attribute value
	 */
	public BigDecimal getWorkCategoryAttributeValue() {
		return this.workCategoryAttributeValue;
	}

	/**
	 * Work category attribute value.
	 *
	 * @param workCategoryAttributeValue the work category attribute value
	 * @return the work estimate
	 */
	public WorkEstimate workCategoryAttributeValue(BigDecimal workCategoryAttributeValue) {
		this.workCategoryAttributeValue = workCategoryAttributeValue;
		return this;
	}

	/**
	 * Sets the work category attribute value.
	 *
	 * @param workCategoryAttributeValue the new work category attribute value
	 */
	public void setWorkCategoryAttributeValue(BigDecimal workCategoryAttributeValue) {
		this.workCategoryAttributeValue = workCategoryAttributeValue;
	}

	/**
	 * Gets the admin sanction accorded yn.
	 *
	 * @return the admin sanction accorded yn
	 */
	public Boolean getAdminSanctionAccordedYn() {
		return this.adminSanctionAccordedYn;
	}

	/**
	 * Admin sanction accorded yn.
	 *
	 * @param adminSanctionAccordedYn the admin sanction accorded yn
	 * @return the work estimate
	 */
	public WorkEstimate adminSanctionAccordedYn(Boolean adminSanctionAccordedYn) {
		this.adminSanctionAccordedYn = adminSanctionAccordedYn;
		return this;
	}

	/**
	 * Sets the admin sanction accorded yn.
	 *
	 * @param adminSanctionAccordedYn the new admin sanction accorded yn
	 */
	public void setAdminSanctionAccordedYn(Boolean adminSanctionAccordedYn) {
		this.adminSanctionAccordedYn = adminSanctionAccordedYn;
	}

	/**
	 * Gets the tech sanction accorded yn.
	 *
	 * @return the tech sanction accorded yn
	 */
	public Boolean getTechSanctionAccordedYn() {
		return this.techSanctionAccordedYn;
	}

	/**
	 * Tech sanction accorded yn.
	 *
	 * @param techSanctionAccordedYn the tech sanction accorded yn
	 * @return the work estimate
	 */
	public WorkEstimate techSanctionAccordedYn(Boolean techSanctionAccordedYn) {
		this.techSanctionAccordedYn = techSanctionAccordedYn;
		return this;
	}

	/**
	 * Sets the tech sanction accorded yn.
	 *
	 * @param techSanctionAccordedYn the new tech sanction accorded yn
	 */
	public void setTechSanctionAccordedYn(Boolean techSanctionAccordedYn) {
		this.techSanctionAccordedYn = techSanctionAccordedYn;
	}

	/**
	 * Gets the line estimate total.
	 *
	 * @return the line estimate total
	 */
	public BigDecimal getLineEstimateTotal() {
		return this.lineEstimateTotal;
	}

	/**
	 * Line estimate total.
	 *
	 * @param lineEstimateTotal the line estimate total
	 * @return the work estimate
	 */
	public WorkEstimate lineEstimateTotal(BigDecimal lineEstimateTotal) {
		this.lineEstimateTotal = lineEstimateTotal;
		return this;
	}

	/**
	 * Sets the line estimate total.
	 *
	 * @param lineEstimateTotal the new line estimate total
	 */
	public void setLineEstimateTotal(BigDecimal lineEstimateTotal) {
		this.lineEstimateTotal = lineEstimateTotal;
	}

	/**
	 * Gets the estimate total.
	 *
	 * @return the estimate total
	 */
	public BigDecimal getEstimateTotal() {
		return this.estimateTotal;
	}

	/**
	 * Estimate total.
	 *
	 * @param estimateTotal the estimate total
	 * @return the work estimate
	 */
	public WorkEstimate estimateTotal(BigDecimal estimateTotal) {
		this.estimateTotal = estimateTotal;
		return this;
	}

	/**
	 * Sets the estimate total.
	 *
	 * @param estimateTotal the new estimate total
	 */
	public void setEstimateTotal(BigDecimal estimateTotal) {
		this.estimateTotal = estimateTotal;
	}

	/**
	 * Gets the lumpsum total.
	 *
	 * @return the lumpsum total
	 */
	public BigDecimal getGroupLumpsumTotal() {
		return this.groupLumpsumTotal;
	}

	/**
	 * Lumpsum total.
	 *
	 * @param groupLumpsumTotal the group lumpsum total
	 * @return the work estimate
	 */
	public WorkEstimate groupLumpsumTotal(BigDecimal groupLumpsumTotal) {
		this.groupLumpsumTotal = groupLumpsumTotal;
		return this;
	}

	/**
	 * Sets the lumpsum total.
	 *
	 * @param groupLumpsumTotal the new group lumpsum total
	 */
	public void setGroupLumpsumTotal(BigDecimal groupLumpsumTotal) {
		this.groupLumpsumTotal = groupLumpsumTotal;
	}

	/**
	 * Gets the group overhead total.
	 *
	 * @return the group overhead total
	 */
	public BigDecimal getGroupOverheadTotal() {
		return this.groupOverheadTotal;
	}

	/**
	 * Group overhead total.
	 *
	 * @param groupOverheadTotal the group overhead total
	 * @return the work estimate
	 */
	public WorkEstimate groupOverheadTotal(BigDecimal groupOverheadTotal) {
		this.groupOverheadTotal = groupOverheadTotal;
		return this;
	}

	/**
	 * Sets the group overhead total.
	 *
	 * @param groupOverheadTotal the new group overhead total
	 */
	public void setGroupOverheadTotal(BigDecimal groupOverheadTotal) {
		this.groupOverheadTotal = groupOverheadTotal;
	}

	/**
	 * Gets the admin sanction ref number.
	 *
	 * @return the admin sanction ref number
	 */
	public String getAdminSanctionRefNumber() {
		return this.adminSanctionRefNumber;
	}

	/**
	 * Admin sanction ref number.
	 *
	 * @param adminSanctionRefNumber the admin sanction ref number
	 * @return the work estimate
	 */
	public WorkEstimate adminSanctionRefNumber(String adminSanctionRefNumber) {
		this.adminSanctionRefNumber = adminSanctionRefNumber;
		return this;
	}

	/**
	 * Sets the admin sanction ref number.
	 *
	 * @param adminSanctionRefNumber the new admin sanction ref number
	 */
	public void setAdminSanctionRefNumber(String adminSanctionRefNumber) {
		this.adminSanctionRefNumber = adminSanctionRefNumber;
	}

	/**
	 * Gets the admin sanction ref date.
	 *
	 * @return the admin sanction ref date
	 */
	public ZonedDateTime getAdminSanctionRefDate() {
		return this.adminSanctionRefDate;
	}

	/**
	 * Admin sanction ref date.
	 *
	 * @param adminSanctionRefDate the admin sanction ref date
	 * @return the work estimate
	 */
	public WorkEstimate adminSanctionRefDate(ZonedDateTime adminSanctionRefDate) {
		this.adminSanctionRefDate = adminSanctionRefDate;
		return this;
	}

	/**
	 * Sets the admin sanction ref date.
	 *
	 * @param adminSanctionRefDate the new admin sanction ref date
	 */
	public void setAdminSanctionRefDate(ZonedDateTime adminSanctionRefDate) {
		this.adminSanctionRefDate = adminSanctionRefDate;
	}

	/**
	 * Gets the admin sanctioned date.
	 *
	 * @return the admin sanctioned date
	 */
	public ZonedDateTime getAdminSanctionedDate() {
		return this.adminSanctionedDate;
	}

	/**
	 * Admin sanctioned date.
	 *
	 * @param adminSanctionedDate the admin sanctioned date
	 * @return the work estimate
	 */
	public WorkEstimate adminSanctionedDate(ZonedDateTime adminSanctionedDate) {
		this.adminSanctionedDate = adminSanctionedDate;
		return this;
	}

	/**
	 * Sets the admin sanctioned date.
	 *
	 * @param adminSanctionedDate the new admin sanctioned date
	 */
	public void setAdminSanctionedDate(ZonedDateTime adminSanctionedDate) {
		this.adminSanctionedDate = adminSanctionedDate;
	}

	/**
	 * Gets the tech sanction ref number.
	 *
	 * @return the tech sanction ref number
	 */
	public String getTechSanctionRefNumber() {
		return this.techSanctionRefNumber;
	}

	/**
	 * Tech sanction ref number.
	 *
	 * @param techSanctionRefNumber the tech sanction ref number
	 * @return the work estimate
	 */
	public WorkEstimate techSanctionRefNumber(String techSanctionRefNumber) {
		this.techSanctionRefNumber = techSanctionRefNumber;
		return this;
	}

	/**
	 * Sets the tech sanction ref number.
	 *
	 * @param techSanctionRefNumber the new tech sanction ref number
	 */
	public void setTechSanctionRefNumber(String techSanctionRefNumber) {
		this.techSanctionRefNumber = techSanctionRefNumber;
	}

	/**
	 * Gets the tech sanctioned date.
	 *
	 * @return the tech sanctioned date
	 */
	public ZonedDateTime getTechSanctionedDate() {
		return this.techSanctionedDate;
	}

	/**
	 * Tech sanctioned date.
	 *
	 * @param techSanctionedDate the tech sanctioned date
	 * @return the work estimate
	 */
	public WorkEstimate techSanctionedDate(ZonedDateTime techSanctionedDate) {
		this.techSanctionedDate = techSanctionedDate;
		return this;
	}

	/**
	 * Sets the tech sanctioned date.
	 *
	 * @param techSanctionedDate the new tech sanctioned date
	 */
	public void setTechSanctionedDate(ZonedDateTime techSanctionedDate) {
		this.techSanctionedDate = techSanctionedDate;
	}

	/**
	 * Gets the approved by.
	 *
	 * @return the approved by
	 */
	public String getApprovedBy() {
		return this.approvedBy;
	}

	/**
	 * Approved by.
	 *
	 * @param approvedBy the approved by
	 * @return the work estimate
	 */
	public WorkEstimate approvedBy(String approvedBy) {
		this.approvedBy = approvedBy;
		return this;
	}

	/**
	 * Sets the approved by.
	 *
	 * @param approvedBy the new approved by
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * Gets the approved ts.
	 *
	 * @return the approved ts
	 */
	public ZonedDateTime getApprovedTs() {
		return this.approvedTs;
	}

	/**
	 * Approved ts.
	 *
	 * @param approvedTs the approved ts
	 * @return the work estimate
	 */
	public WorkEstimate approvedTs(ZonedDateTime approvedTs) {
		this.approvedTs = approvedTs;
		return this;
	}

	/**
	 * Sets the approved ts.
	 *
	 * @param approvedTs the new approved ts
	 */
	public void setApprovedTs(ZonedDateTime approvedTs) {
		this.approvedTs = approvedTs;
	}

	/**
	 * Gets the hkrdb funded yn.
	 *
	 * @return the hkrdb funded yn
	 */
	public Boolean getHkrdbFundedYn() {
		return this.hkrdbFundedYn;
	}

	/**
	 * Hkrdb funded yn.
	 *
	 * @param hkrdbFundedYn the hkrdb funded yn
	 * @return the work estimate
	 */
	public WorkEstimate hkrdbFundedYn(Boolean hkrdbFundedYn) {
		this.hkrdbFundedYn = hkrdbFundedYn;
		return this;
	}

	/**
	 * Sets the hkrdb funded yn.
	 *
	 * @param hkrdbFundedYn the new hkrdb funded yn
	 */
	public void setHkrdbFundedYn(Boolean hkrdbFundedYn) {
		this.hkrdbFundedYn = hkrdbFundedYn;
	}

	/**
	 * Gets the scheme id.
	 *
	 * @return the scheme id
	 */
	public Long getSchemeId() {
		return this.schemeId;
	}

	/**
	 * Scheme id.
	 *
	 * @param schemeId the scheme id
	 * @return the work estimate
	 */
	public WorkEstimate schemeId(Long schemeId) {
		this.schemeId = schemeId;
		return this;
	}

	/**
	 * Sets the scheme id.
	 *
	 * @param schemeId the new scheme id
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * Gets the approved budget yn.
	 *
	 * @return the approved budget yn
	 */
	public Boolean getApprovedBudgetYn() {
		return this.approvedBudgetYn;
	}

	/**
	 * Approved budget yn.
	 *
	 * @param approvedBudgetYn the approved budget yn
	 * @return the work estimate
	 */
	public WorkEstimate approvedBudgetYn(Boolean approvedBudgetYn) {
		this.approvedBudgetYn = approvedBudgetYn;
		return this;
	}

	/**
	 * Sets the approved budget yn.
	 *
	 * @param approvedBudgetYn the new approved budget yn
	 */
	public void setApprovedBudgetYn(Boolean approvedBudgetYn) {
		this.approvedBudgetYn = approvedBudgetYn;
	}

	/**
	 * Gets the grant allocated amount.
	 *
	 * @return the grant allocated amount
	 */
	public BigDecimal getGrantAllocatedAmount() {
		return this.grantAllocatedAmount;
	}

	/**
	 * Grant allocated amount.
	 *
	 * @param grantAllocatedAmount the grant allocated amount
	 * @return the work estimate
	 */
	public WorkEstimate grantAllocatedAmount(BigDecimal grantAllocatedAmount) {
		this.grantAllocatedAmount = grantAllocatedAmount;
		return this;
	}

	/**
	 * Sets the grant allocated amount.
	 *
	 * @param grantAllocatedAmount the new grant allocated amount
	 */
	public void setGrantAllocatedAmount(BigDecimal grantAllocatedAmount) {
		this.grantAllocatedAmount = grantAllocatedAmount;
	}

	/**
	 * Gets the document reference.
	 *
	 * @return the document reference
	 */
	public String getDocumentReference() {
		return this.documentReference;
	}

	/**
	 * Document reference.
	 *
	 * @param documentReference the document reference
	 * @return the work estimate
	 */
	public WorkEstimate documentReference(String documentReference) {
		this.documentReference = documentReference;
		return this;
	}

	/**
	 * Sets the document reference.
	 *
	 * @param documentReference the new document reference
	 */
	public void setDocumentReference(String documentReference) {
		this.documentReference = documentReference;
	}

	/**
	 * Gets the provisional amount.
	 *
	 * @return the provisional amount
	 */
	public BigDecimal getProvisionalAmount() {
		return this.provisionalAmount;
	}

	/**
	 * Provisional amount.
	 *
	 * @param provisionalAmount the provisional amount
	 * @return the work estimate
	 */
	public WorkEstimate provisionalAmount(BigDecimal provisionalAmount) {
		this.provisionalAmount = provisionalAmount;
		return this;
	}

	/**
	 * Sets the provisional amount.
	 *
	 * @param provisionalAmount the new provisional amount
	 */
	public void setProvisionalAmount(BigDecimal provisionalAmount) {
		this.provisionalAmount = provisionalAmount;
	}

	/**
	 * Gets the head of account.
	 *
	 * @return the head of account
	 */
	public String getHeadOfAccount() {
		return this.headOfAccount;
	}

	/**
	 * Head of account.
	 *
	 * @param headOfAccount the head of account
	 * @return the work estimate
	 */
	public WorkEstimate headOfAccount(String headOfAccount) {
		this.headOfAccount = headOfAccount;
		return this;
	}

	/**
	 * Sets the head of account.
	 *
	 * @param headOfAccount the new head of account
	 */
	public void setHeadOfAccount(String headOfAccount) {
		this.headOfAccount = headOfAccount;
	}

	/**
	 * Gets the normal ovehead total.
	 *
	 * @return the normal ovehead total
	 */
	public BigDecimal getNormalOveheadTotal() {
		return normalOveheadTotal;
	}

	/**
	 * Sets the normal ovehead total.
	 *
	 * @param normalOveheadTotal the new normal ovehead total
	 */
	public void setNormalOveheadTotal(BigDecimal normalOveheadTotal) {
		this.normalOveheadTotal = normalOveheadTotal;
	}

	/**
	 * Gets the addl ovehead total.
	 *
	 * @return the addl ovehead total
	 */
	public BigDecimal getAddlOveheadTotal() {
		return addlOveheadTotal;
	}

	/**
	 * Sets the addl ovehead total.
	 *
	 * @param addlOveheadTotal the new addl ovehead total
	 */
	public void setAddlOveheadTotal(BigDecimal addlOveheadTotal) {
		this.addlOveheadTotal = addlOveheadTotal;
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
		if (!(o instanceof WorkEstimate)) {
			return false;
		}
		return id != null && id.equals(((WorkEstimate) o).id);
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
		return "WorkEstimate{" + "id=" + getId() + ", workEstimateNumber='" + getWorkEstimateNumber() + "'"
				+ ", status='" + getStatus() + "'" + ", deptId=" + getDeptId() + ", locationId=" + getLocationId()
				+ ", fileNumber='" + getFileNumber() + "'" + ", name='" + getName() + "'" + ", description='"
				+ getDescription() + "'" + ", estimateTypeId=" + getEstimateTypeId() + ", wotkTypeId=" + getWorkTypeId()
				+ ", workCategoryId=" + getWorkCategoryId() + ", workCategoryAttribute=" + getWorkCategoryAttribute()
				+ ", workCategoryAttributeValue=" + getWorkCategoryAttributeValue() + ", adminSanctionAccordedYn='"
				+ getAdminSanctionAccordedYn() + "'" + ", techSanctionAccordedYn='" + getTechSanctionAccordedYn() + "'"
				+ ", lineEstimateTotal=" + getLineEstimateTotal() + ", estimateTotal=" + getEstimateTotal()
				+ ", groupLumpsumTotal=" + getGroupLumpsumTotal() + ", addlOverheadTotal=" + ", groupOverheadTotal="
				+ getGroupOverheadTotal() + ", adminSanctionRefNumber='" + getAdminSanctionRefNumber() + "'"
				+ ", adminSanctionRefDate='" + getAdminSanctionRefDate() + "'" + ", adminSanctionedDate='"
				+ getAdminSanctionedDate() + "'" + ", techSanctionRefNumber='" + getTechSanctionRefNumber() + "'"
				+ ", techSanctionedDate='" + getTechSanctionedDate() + "'" + ", approvedBy='" + getApprovedBy() + "'"
				+ ", approvedTs='" + getApprovedTs() + "'" + ", hkrdbFundedYn='" + getHkrdbFundedYn() + "'"
				+ ", schemeId=" + getSchemeId() + ", approvedBudgetYn='" + getApprovedBudgetYn() + "'"
				+ ", grantAllocatedAmount=" + getGrantAllocatedAmount() + ", documentReference='"
				+ getDocumentReference() + "'" + ", provisionalAmount=" + getProvisionalAmount() + ", headOfAccount='"
				+ getHeadOfAccount() + "'" + "}";
	}
}
