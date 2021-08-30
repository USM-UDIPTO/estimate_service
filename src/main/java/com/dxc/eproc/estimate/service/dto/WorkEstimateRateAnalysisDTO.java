package com.dxc.eproc.estimate.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dxc.eproc.estimate.model.WorkEstimateRateAnalysis} entity.
 */
public class WorkEstimateRateAnalysisDTO implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

    /** The work estimate id. */
    private Long workEstimateId;

    /** The work estimate item id. */
    private Long workEstimateItemId;
    
    /** The area weightage master id. */
    private Long areaWeightageMasterId;

    /** The area weightage circle id. */
    private Long areaWeightageCircleId;

    /** The area weightage percentage. */
    private BigDecimal areaWeightagePercentage;

    /** The sor financial year. */
    private String sorFinancialYear;

    /** The basic rate. */
    @NotNull
    private BigDecimal basicRate;

    /** The net rate. */
    @NotNull
    private BigDecimal netRate;

    /** The floor no. */
    private Long floorNo;

    /** The contractor profit percentage. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal contractorProfitPercentage;

    /** The overhead percentage. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal overheadPercentage;

    /** The tax percentage. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal taxPercentage;

    /** The locality allowance. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal localityAllowance;

    /** The employees cost. */
    private BigDecimal employeesCost;

    /** The contingencies. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal contingencies;

    /** The transportation cost. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal transportationCost;

    /** The service tax. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal serviceTax;

    /** The provident fund charges. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal providentFundCharges;

    /** The esi charges. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal esiCharges;

    /** The idc charges. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal idcCharges;

    /** The watch and ward cost. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal watchAndWardCost;

    /** The insurance cost. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal insuranceCost;

    /** The statutory charges. */
    private BigDecimal statutoryCharges;

    /** The compensation cost. */
    private BigDecimal compensationCost;

    /** The ra completed yn. */
    @NotNull
    private Boolean raCompletedYn;

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
     * Gets the work estimate id.
     *
     * @return the work estimate id
     */
    public Long getWorkEstimateId() {
        return workEstimateId;
    }

    /**
     * Sets the work estimate id.
     *
     * @param workEstimateId the new work estimate id
     */
    public void setWorkEstimateId(Long workEstimateId) {
        this.workEstimateId = workEstimateId;
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
     * Gets the area weightage master id.
     *
     * @return the area weightage master id
     */
    public Long getAreaWeightageMasterId() {
        return areaWeightageMasterId;
    }

    /**
     * Sets the area weightage master id.
     *
     * @param areaWeightageMasterId the new area weightage master id
     */
    public void setAreaWeightageMasterId(Long areaWeightageMasterId) {
        this.areaWeightageMasterId = areaWeightageMasterId;
    }

    /**
     * Gets the area weightage circle id.
     *
     * @return the area weightage circle id
     */
    public Long getAreaWeightageCircleId() {
        return areaWeightageCircleId;
    }

    /**
     * Sets the area weightage circle id.
     *
     * @param areaWeightageCircleId the new area weightage circle id
     */
    public void setAreaWeightageCircleId(Long areaWeightageCircleId) {
        this.areaWeightageCircleId = areaWeightageCircleId;
    }

    /**
     * Gets the area weightage percentage.
     *
     * @return the area weightage percentage
     */
    public BigDecimal getAreaWeightagePercentage() {
        return areaWeightagePercentage;
    }

    /**
     * Sets the area weightage percentage.
     *
     * @param areaWeightagePercentage the new area weightage percentage
     */
    public void setAreaWeightagePercentage(BigDecimal areaWeightagePercentage) {
        this.areaWeightagePercentage = areaWeightagePercentage;
    }

    /**
     * Gets the sor financial year.
     *
     * @return the sor financial year
     */
    public String getSorFinancialYear() {
        return sorFinancialYear;
    }

    /**
     * Sets the sor financial year.
     *
     * @param sorFinancialYear the new sor financial year
     */
    public void setSorFinancialYear(String sorFinancialYear) {
        this.sorFinancialYear = sorFinancialYear;
    }

    /**
     * Gets the basic rate.
     *
     * @return the basic rate
     */
    public BigDecimal getBasicRate() {
        return basicRate;
    }

    /**
     * Sets the basic rate.
     *
     * @param basicRate the new basic rate
     */
    public void setBasicRate(BigDecimal basicRate) {
        this.basicRate = basicRate;
    }

    /**
     * Gets the net rate.
     *
     * @return the net rate
     */
    public BigDecimal getNetRate() {
        return netRate;
    }

    /**
     * Sets the net rate.
     *
     * @param netRate the new net rate
     */
    public void setNetRate(BigDecimal netRate) {
        this.netRate = netRate;
    }

    /**
     * Gets the floor no.
     *
     * @return the floor no
     */
    public Long getFloorNo() {
        return floorNo;
    }

    /**
     * Sets the floor no.
     *
     * @param floorNo the new floor no
     */
    public void setFloorNo(Long floorNo) {
        this.floorNo = floorNo;
    }

    /**
     * Gets the contractor profit percentage.
     *
     * @return the contractor profit percentage
     */
    public BigDecimal getContractorProfitPercentage() {
        return contractorProfitPercentage;
    }

    /**
     * Sets the contractor profit percentage.
     *
     * @param contractorProfitPercentage the new contractor profit percentage
     */
    public void setContractorProfitPercentage(BigDecimal contractorProfitPercentage) {
        this.contractorProfitPercentage = contractorProfitPercentage;
    }

    /**
     * Gets the overhead percentage.
     *
     * @return the overhead percentage
     */
    public BigDecimal getOverheadPercentage() {
        return overheadPercentage;
    }

    /**
     * Sets the overhead percentage.
     *
     * @param overheadPercentage the new overhead percentage
     */
    public void setOverheadPercentage(BigDecimal overheadPercentage) {
        this.overheadPercentage = overheadPercentage;
    }

    /**
     * Gets the tax percentage.
     *
     * @return the tax percentage
     */
    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    /**
     * Sets the tax percentage.
     *
     * @param taxPercentage the new tax percentage
     */
    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    /**
     * Gets the locality allowance.
     *
     * @return the locality allowance
     */
    public BigDecimal getLocalityAllowance() {
        return localityAllowance;
    }

    /**
     * Sets the locality allowance.
     *
     * @param localityAllowance the new locality allowance
     */
    public void setLocalityAllowance(BigDecimal localityAllowance) {
        this.localityAllowance = localityAllowance;
    }

    /**
     * Gets the employees cost.
     *
     * @return the employees cost
     */
    public BigDecimal getEmployeesCost() {
        return employeesCost;
    }

    /**
     * Sets the employees cost.
     *
     * @param employeesCost the new employees cost
     */
    public void setEmployeesCost(BigDecimal employeesCost) {
        this.employeesCost = employeesCost;
    }

    /**
     * Gets the contingencies.
     *
     * @return the contingencies
     */
    public BigDecimal getContingencies() {
        return contingencies;
    }

    /**
     * Sets the contingencies.
     *
     * @param contingencies the new contingencies
     */
    public void setContingencies(BigDecimal contingencies) {
        this.contingencies = contingencies;
    }

    /**
     * Gets the transportation cost.
     *
     * @return the transportation cost
     */
    public BigDecimal getTransportationCost() {
        return transportationCost;
    }

    /**
     * Sets the transportation cost.
     *
     * @param transportationCost the new transportation cost
     */
    public void setTransportationCost(BigDecimal transportationCost) {
        this.transportationCost = transportationCost;
    }

    /**
     * Gets the service tax.
     *
     * @return the service tax
     */
    public BigDecimal getServiceTax() {
        return serviceTax;
    }

    /**
     * Sets the service tax.
     *
     * @param serviceTax the new service tax
     */
    public void setServiceTax(BigDecimal serviceTax) {
        this.serviceTax = serviceTax;
    }

    /**
     * Gets the provident fund charges.
     *
     * @return the provident fund charges
     */
    public BigDecimal getProvidentFundCharges() {
        return providentFundCharges;
    }

    /**
     * Sets the provident fund charges.
     *
     * @param providentFundCharges the new provident fund charges
     */
    public void setProvidentFundCharges(BigDecimal providentFundCharges) {
        this.providentFundCharges = providentFundCharges;
    }

    /**
     * Gets the esi charges.
     *
     * @return the esi charges
     */
    public BigDecimal getEsiCharges() {
        return esiCharges;
    }

    /**
     * Sets the esi charges.
     *
     * @param esiCharges the new esi charges
     */
    public void setEsiCharges(BigDecimal esiCharges) {
        this.esiCharges = esiCharges;
    }

    /**
     * Gets the idc charges.
     *
     * @return the idc charges
     */
    public BigDecimal getIdcCharges() {
        return idcCharges;
    }

    /**
     * Sets the idc charges.
     *
     * @param idcCharges the new idc charges
     */
    public void setIdcCharges(BigDecimal idcCharges) {
        this.idcCharges = idcCharges;
    }

    /**
     * Gets the watch and ward cost.
     *
     * @return the watch and ward cost
     */
    public BigDecimal getWatchAndWardCost() {
        return watchAndWardCost;
    }

    /**
     * Sets the watch and ward cost.
     *
     * @param watchAndWardCost the new watch and ward cost
     */
    public void setWatchAndWardCost(BigDecimal watchAndWardCost) {
        this.watchAndWardCost = watchAndWardCost;
    }

    /**
     * Gets the insurance cost.
     *
     * @return the insurance cost
     */
    public BigDecimal getInsuranceCost() {
        return insuranceCost;
    }

    /**
     * Sets the insurance cost.
     *
     * @param insuranceCost the new insurance cost
     */
    public void setInsuranceCost(BigDecimal insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    /**
     * Gets the statutory charges.
     *
     * @return the statutory charges
     */
    public BigDecimal getStatutoryCharges() {
        return statutoryCharges;
    }

    /**
     * Sets the statutory charges.
     *
     * @param statutoryCharges the new statutory charges
     */
    public void setStatutoryCharges(BigDecimal statutoryCharges) {
        this.statutoryCharges = statutoryCharges;
    }

    /**
     * Gets the compensation cost.
     *
     * @return the compensation cost
     */
    public BigDecimal getCompensationCost() {
        return compensationCost;
    }

    /**
     * Sets the compensation cost.
     *
     * @param compensationCost the new compensation cost
     */
    public void setCompensationCost(BigDecimal compensationCost) {
        this.compensationCost = compensationCost;
    }

    /**
     * Gets the ra completed yn.
     *
     * @return the ra completed yn
     */
    public Boolean getRaCompletedYn() {
        return raCompletedYn;
    }

    /**
     * Sets the ra completed yn.
     *
     * @param raCompletedYn the new ra completed yn
     */
    public void setRaCompletedYn(Boolean raCompletedYn) {
        this.raCompletedYn = raCompletedYn;
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
        if (!(o instanceof WorkEstimateRateAnalysisDTO)) {
            return false;
        }

        WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = (WorkEstimateRateAnalysisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workEstimateRateAnalysisDTO.id);
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
        return "WorkEstimateRateAnalysisDTO{" +
            "id=" + getId() +
            ", workEstimateId=" + getWorkEstimateId() +
            ", workEstimateItemId=" + getWorkEstimateItemId() +
            ", areaWeightageMasterId=" + getAreaWeightageMasterId() +
            ", areaWeightageCircleId=" + getAreaWeightageCircleId() +
            ", areaWeightagePercentage=" + getAreaWeightagePercentage() +
            ", sorFinancialYear='" + getSorFinancialYear() + "'" +
            ", basicRate=" + getBasicRate() +
            ", netRate=" + getNetRate() +
            ", floorNo=" + getFloorNo() +
            ", contractorProfitPercentage=" + getContractorProfitPercentage() +
            ", overheadPercentage=" + getOverheadPercentage() +
            ", taxPercentage=" + getTaxPercentage() +
            ", localityAllowance=" + getLocalityAllowance() +
            ", employeesCost=" + getEmployeesCost() +
            ", contingencies=" + getContingencies() +
            ", transportationCost=" + getTransportationCost() +
            ", serviceTax=" + getServiceTax() +
            ", providentFundCharges=" + getProvidentFundCharges() +
            ", esiCharges=" + getEsiCharges() +
            ", idcCharges=" + getIdcCharges() +
            ", watchAndWardCost=" + getWatchAndWardCost() +
            ", insuranceCost=" + getInsuranceCost() +
            ", statutoryCharges=" + getStatutoryCharges() +
            ", compensationCost=" + getCompensationCost() +
            ", raCompletedYn='" + getRaCompletedYn() + "'" +
            "}";
    }
}
