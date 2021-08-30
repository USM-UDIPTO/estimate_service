package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkEstimate} entity.
 */
public class WorkEstimateDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The work estimate number. */
	private String workEstimateNumber;

	/** The status. */
	private WorkEstimateStatus status;

	/** The dept id. */
	@NotNull(message = "{workEstimate.deptId.NotNull}")
	@Min(value = 1, message = "{workEstimate.deptId.NotValid}")
	private Long deptId;

	/** The dept code. */
	@NotNull(message = "{workEstimate.deptCode.NotNull}")
	private String deptCode;

	/** The location id. */
	@NotNull(message = "{workEstimate.locationId.NotNull}")
	@Min(value = 1, message = "{workEstimate.locationId.NotValid}")
	private Long locationId;

	/** The file number. */
	@NotBlank(message = "{workEstimate.fileNumber.NotBlank}")
	@Size(max = 255, message = "{workEstimate.fileNumber.Size}")
	@Pattern(regexp = "^(?! )[A-Za-z0-9 \\s-/]*(?<! )$", message = "{workEstimate.fileNumber.Pattern}")
	private String fileNumber;

	/** The name. */
	@NotBlank(message = "{workEstimate.name.NotBlank}")
	@Size(max = 512, message = "{workEstimate.name.Size}")
	@Pattern(regexp = "^(?! )[A-Za-z0-9 \\s-+,.\\\\&/]*(?<! )$", message = "{workEstimate.name.Pattern}")
	private String name;

	/** The description. */
	private String description;

	/** The estimate type id. */
	@NotNull(message = "{workEstimate.estimateTypeId.NotNull}")
	@Min(value = 1, message = "{workEstimate.estimateTypeId.NotValid}")
	private Long estimateTypeId;

	/** The wotk type id. */
	@NotNull(message = "{workEstimate.wotkTypeId.NotNull}")
	@Min(value = 1, message = "{workEstimate.wotkTypeId.NotValid}")
	private Long workTypeId;

	/** The work category id. */
	@NotNull(message = "{workEstimate.workCategoryId.NotNull}")
	@Min(value = 1, message = "{workEstimate.workCategoryId.NotValid}")
	private Long workCategoryId;

	/** The work category code. */
	//@NotNull(message = "{workEstimate.workCategoryCode.NotNull}")
	private String workCategoryCode;

	/** The work category attribute. */
	private Long workCategoryAttribute;

	/** The work category attribute value. */
	private BigDecimal workCategoryAttributeValue;

	/** The admin sanction accorded yn. */
	private Boolean adminSanctionAccordedYn;

	/** The tech sanction accorded yn. */
	private Boolean techSanctionAccordedYn;

	/** The line estimate total. */
	private BigDecimal lineEstimateTotal;

	/** The estimate total. */
	private BigDecimal estimateTotal;

	/** The lumpsum total. */
	private BigDecimal groupLumpsumTotal;

	/** The group overhead total. */
	private BigDecimal groupOverheadTotal;

	/** The admin sanction ref number. */
	private String adminSanctionRefNumber;

	/** The admin sanction ref date. */
	private ZonedDateTime adminSanctionRefDate;

	/** The admin sanctioned date. */
	private ZonedDateTime adminSanctionedDate;

	/** The tech sanction ref number. */
	private String techSanctionRefNumber;

	/** The tech sanctioned date. */
	private ZonedDateTime techSanctionedDate;

	/** The approved by. */
	private String approvedBy;

	/** The approved ts. */
	private ZonedDateTime approvedTs;

	/** The hkrdb funded yn. */
	private Boolean hkrdbFundedYn;

	/** The scheme id. */
	private Long schemeId;

	/** The approved budget yn. */
	@NotNull(message = "{workEstimate.approvedBudgetYn.NotNull}")
	private Boolean approvedBudgetYn;

	/** The grant allocated amount. */
	private BigDecimal grantAllocatedAmount;

	/** The document reference. */
	private String documentReference;

	/** The provisional amount. */
	private BigDecimal provisionalAmount;

	/** The head of account. */
	private String headOfAccount;

	/** The ecv. */
	private BigDecimal ecv;
	
	/** The normal ovehead total. */
	private BigDecimal normalOveheadTotal;
	
	/** The addl ovehead total. */
	private BigDecimal addlOveheadTotal;

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
	 * Gets the work estimate number.
	 *
	 * @return the work estimate number
	 */
	public String getWorkEstimateNumber() {
		return workEstimateNumber;
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
		return status;
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
	 * Gets the location id.
	 *
	 * @return the location id
	 */
	public Long getLocationId() {
		return locationId;
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
		return fileNumber;
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
		return name;
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
	 * Gets the estimate type id.
	 *
	 * @return the estimate type id
	 */
	public Long getEstimateTypeId() {
		return estimateTypeId;
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
		return workTypeId;
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
		return workCategoryId;
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
		return workCategoryAttribute;
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
		return workCategoryAttributeValue;
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
		return adminSanctionAccordedYn;
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
		return techSanctionAccordedYn;
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
		return lineEstimateTotal;
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
		return estimateTotal;
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
	 * Gets the group lumpsum total.
	 *
	 * @return the group lumpsum total
	 */
	public BigDecimal getGroupLumpsumTotal() {
		return groupLumpsumTotal;
	}

	/**
	 * Sets the group lumpsum total.
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
		return groupOverheadTotal;
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
		return adminSanctionRefNumber;
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
		return adminSanctionRefDate;
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
		return adminSanctionedDate;
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
		return techSanctionRefNumber;
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
		return techSanctionedDate;
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
		return approvedBy;
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
		return approvedTs;
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
		return hkrdbFundedYn;
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
		return schemeId;
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
		return approvedBudgetYn;
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
		return grantAllocatedAmount;
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
		return documentReference;
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
		return provisionalAmount;
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
		return headOfAccount;
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
	 * Gets the dept code.
	 *
	 * @return the dept code
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * Sets the dept code.
	 *
	 * @param deptCode the new dept code
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * Gets the work category code.
	 *
	 * @return the work category code
	 */
	public String getWorkCategoryCode() {
		return workCategoryCode;
	}

	/**
	 * Sets the work category code.
	 *
	 * @param workCategoryCode the new work category code
	 */
	public void setWorkCategoryCode(String workCategoryCode) {
		this.workCategoryCode = workCategoryCode;
	}

	/**
	 * Gets the ecv.
	 *
	 * @return the ecv
	 */
	public BigDecimal getEcv() {
		ecv = BigDecimal.ZERO;
		if (estimateTotal != null) {
			ecv = ecv.add(estimateTotal);
		}
		if (groupLumpsumTotal != null) {
			ecv = ecv.add(groupLumpsumTotal);
		}
		if (groupOverheadTotal != null) {
			ecv = ecv.add(groupOverheadTotal);
		}
		return ecv;
	}

	/**
	 * @return the normalOveheadTotal
	 */
	public BigDecimal getNormalOveheadTotal() {
		return normalOveheadTotal;
	}

	/**
	 * @param normalOveheadTotal the normalOveheadTotal to set
	 */
	public void setNormalOveheadTotal(BigDecimal normalOveheadTotal) {
		this.normalOveheadTotal = normalOveheadTotal;
	}

	/**
	 * @return the addlOveheadTotal
	 */
	public BigDecimal getAddlOveheadTotal() {
		return addlOveheadTotal;
	}

	/**
	 * @param addlOveheadTotal the addlOveheadTotal to set
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
		if (!(o instanceof WorkEstimateDTO)) {
			return false;
		}

		WorkEstimateDTO workEstimateDTO = (WorkEstimateDTO) o;
		if (this.id == null) {
			return false;
		}
		return Objects.equals(this.id, workEstimateDTO.id);
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
		return "WorkEstimateDTO{" + "id=" + getId() + ", workEstimateNumber='" + getWorkEstimateNumber() + "'"
				+ ", status='" + getStatus() + "'" + ", deptId=" + getDeptId() + ", locationId=" + getLocationId()
				+ ", fileNumber='" + getFileNumber() + "'" + ", name='" + getName() + "'" + ", description='"
				+ getDescription() + "'" + ", estimateTypeId=" + getEstimateTypeId() + ", wotkTypeId=" + getWorkTypeId()
				+ ", workCategoryId=" + getWorkCategoryId() + ", workCategoryAttribute=" + getWorkCategoryAttribute()
				+ ", workCategoryAttributeValue=" + getWorkCategoryAttributeValue() + ", adminSanctionAccordedYn='"
				+ getAdminSanctionAccordedYn() + "'" + ", techSanctionAccordedYn='" + getTechSanctionAccordedYn() + "'"
				+ ", lineEstimateTotal=" + getLineEstimateTotal() + ", estimateTotal=" + getEstimateTotal()
				+ ", lumpsumTotal=" + getGroupLumpsumTotal() + ", groupOverheadTotal=" + getGroupOverheadTotal()
				+ ", adminSanctionRefNumber='" + getAdminSanctionRefNumber() + "'" + ", adminSanctionRefDate='"
				+ getAdminSanctionRefDate() + "'" + ", adminSanctionedDate='" + getAdminSanctionedDate() + "'"
				+ ", techSanctionRefNumber='" + getTechSanctionRefNumber() + "'" + ", techSanctionedDate='"
				+ getTechSanctionedDate() + "'" + ", approvedBy='" + getApprovedBy() + "'" + ", approvedTs='"
				+ getApprovedTs() + "'" + ", hkrdbFundedYn='" + getHkrdbFundedYn() + "'" + ", schemeId=" + getSchemeId()
				+ ", normalOveheadTotal='" + getNormalOveheadTotal() + "'" + ", addlOveheadTotal=" + getAddlOveheadTotal()
				+ ", approvedBudgetYn='" + getApprovedBudgetYn() + "'" + ", grantAllocatedAmount="
				+ getGrantAllocatedAmount() + ", documentReference='" + getDocumentReference() + "'"
				+ ", provisionalAmount=" + getProvisionalAmount() + ", headOfAccount='" + getHeadOfAccount() + "'"
				+ "}";
	}
}
