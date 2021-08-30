package com.dxc.eproc.estimate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WorkEstimateRateAnalysis.
 */
@Entity
@Table(name = "work_estimate_rate_analysis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkEstimateRateAnalysis extends EProcModel implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The work estimate id. */
    @NotNull
    @Column(name = "work_estimate_id", nullable = false)
    private Long workEstimateId;
    
    /** The work estimate item id. */
    @NotNull
    @Column(name = "work_estimate_item_id", nullable = false)
    private Long workEstimateItemId;

    /** The area weightage master id. */
    @Column(name = "area_weightage_master_id")
    private Long areaWeightageMasterId;

    /** The area weightage circle id. */
    @Column(name = "area_weightage_circle_id")
    private Long areaWeightageCircleId;

    /** The area weightage percentage. */
    @Column(name = "area_weightage_percentage", precision = 10, scale = 2)
    private BigDecimal areaWeightagePercentage;

    /** The sor financial year. */
    @Column(name = "sor_financial_year")
    private String sorFinancialYear;

    /** The basic rate. */
    @NotNull
    @Column(name = "basic_rate", precision = 21, scale = 4, nullable = false)
    private BigDecimal basicRate;

    /** The net rate. */
    @NotNull
    @Column(name = "net_rate", precision = 21, scale = 4, nullable = false)
    private BigDecimal netRate;

    /** The floor no. */
    @Column(name = "floor_no")
    private Long floorNo;

    /** The contractor profit percentage. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "contractor_profit_percentage", precision = 10, scale = 2)
    private BigDecimal contractorProfitPercentage;

    /** The overhead percentage. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "overhead_percentage", precision = 10, scale = 4)
    private BigDecimal overheadPercentage;

    /** The tax percentage. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "tax_percentage", precision = 10, scale = 4)
    private BigDecimal taxPercentage;

    /** The locality allowance. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "locality_allowance", precision = 10, scale = 4)
    private BigDecimal localityAllowance;

    /** The employees cost. */
    @Column(name = "employees_cost", precision = 10, scale = 4)
    private BigDecimal employeesCost;

    /** The contingencies. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "contingencies", precision = 10, scale = 4)
    private BigDecimal contingencies;

    /** The transportation cost. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "transportation_cost", precision = 10, scale = 4)
    private BigDecimal transportationCost;

    /** The service tax. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "service_tax", precision = 10, scale = 4)
    private BigDecimal serviceTax;

    /** The provident fund charges. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "provident_fund_charges", precision = 10, scale = 4)
    private BigDecimal providentFundCharges;

    /** The esi charges. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "esi_charges", precision = 10, scale = 4)
    private BigDecimal esiCharges;

    /** The idc charges. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "idc_charges", precision = 10, scale = 4)
    private BigDecimal idcCharges;

    /** The watch and ward cost. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "watch_and_ward_cost", precision = 10, scale = 4)
    private BigDecimal watchAndWardCost;

    /** The insurance cost. */
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "insurance_cost", precision = 10, scale = 4)
    private BigDecimal insuranceCost;

    /** The statutory charges. */
    @Column(name = "statutory_charges", precision = 10, scale = 4)
    private BigDecimal statutoryCharges;

    /** The compensation cost. */
    @Column(name = "compensation_cost", precision = 10, scale = 4)
    private BigDecimal compensationCost;

    /** The ra completed yn. */
    @NotNull
    @Column(name = "ra_completed_yn", nullable = false)
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
     * Id.
     *
     * @param id the id
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Gets the work estimate id.
     *
     * @return the work estimate id
     */
    public Long getWorkEstimateId() {
        return this.workEstimateId;
    }

    /**
     * Work estimate id.
     *
     * @param workEstimateId the work estimate id
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis workEstimateId(Long workEstimateId) {
        this.workEstimateId = workEstimateId;
        return this;
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
        return this.workEstimateItemId;
    }

    /**
     * Work estimate item id.
     *
     * @param workEstimateItemId the work estimate item id
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis workEstimateItemId(Long workEstimateItemId) {
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
     * Gets the area weightage master id.
     *
     * @return the area weightage master id
     */
    public Long getAreaWeightageMasterId() {
        return this.areaWeightageMasterId;
    }

    /**
     * Area weightage master id.
     *
     * @param areaWeightageMasterId the area weightage master id
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis areaWeightageMasterId(Long areaWeightageMasterId) {
        this.areaWeightageMasterId = areaWeightageMasterId;
        return this;
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
        return this.areaWeightageCircleId;
    }

    /**
     * Area weightage circle id.
     *
     * @param areaWeightageCircleId the area weightage circle id
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis areaWeightageCircleId(Long areaWeightageCircleId) {
        this.areaWeightageCircleId = areaWeightageCircleId;
        return this;
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
        return this.areaWeightagePercentage;
    }

    /**
     * Area weightage percentage.
     *
     * @param areaWeightagePercentage the area weightage percentage
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis areaWeightagePercentage(BigDecimal areaWeightagePercentage) {
        this.areaWeightagePercentage = areaWeightagePercentage;
        return this;
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
        return this.sorFinancialYear;
    }

    /**
     * Sor financial year.
     *
     * @param sorFinancialYear the sor financial year
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis sorFinancialYear(String sorFinancialYear) {
        this.sorFinancialYear = sorFinancialYear;
        return this;
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
        return this.basicRate;
    }

    /**
     * Basic rate.
     *
     * @param basicRate the basic rate
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis basicRate(BigDecimal basicRate) {
        this.basicRate = basicRate;
        return this;
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
        return this.netRate;
    }

    /**
     * Net rate.
     *
     * @param netRate the net rate
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis netRate(BigDecimal netRate) {
        this.netRate = netRate;
        return this;
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
        return this.floorNo;
    }

    /**
     * Floor no.
     *
     * @param floorNo the floor no
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis floorNo(Long floorNo) {
        this.floorNo = floorNo;
        return this;
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
        return this.contractorProfitPercentage;
    }

    /**
     * Contractor profit percentage.
     *
     * @param contractorProfitPercentage the contractor profit percentage
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis contractorProfitPercentage(BigDecimal contractorProfitPercentage) {
        this.contractorProfitPercentage = contractorProfitPercentage;
        return this;
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
        return this.overheadPercentage;
    }

    /**
     * Overhead percentage.
     *
     * @param overheadPercentage the overhead percentage
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis overheadPercentage(BigDecimal overheadPercentage) {
        this.overheadPercentage = overheadPercentage;
        return this;
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
        return this.taxPercentage;
    }

    /**
     * Tax percentage.
     *
     * @param taxPercentage the tax percentage
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis taxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
        return this;
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
        return this.localityAllowance;
    }

    /**
     * Locality allowance.
     *
     * @param localityAllowance the locality allowance
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis localityAllowance(BigDecimal localityAllowance) {
        this.localityAllowance = localityAllowance;
        return this;
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
        return this.employeesCost;
    }

    /**
     * Employees cost.
     *
     * @param employeesCost the employees cost
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis employeesCost(BigDecimal employeesCost) {
        this.employeesCost = employeesCost;
        return this;
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
        return this.contingencies;
    }

    /**
     * Contingencies.
     *
     * @param contingencies the contingencies
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis contingencies(BigDecimal contingencies) {
        this.contingencies = contingencies;
        return this;
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
        return this.transportationCost;
    }

    /**
     * Transportation cost.
     *
     * @param transportationCost the transportation cost
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis transportationCost(BigDecimal transportationCost) {
        this.transportationCost = transportationCost;
        return this;
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
        return this.serviceTax;
    }

    /**
     * Service tax.
     *
     * @param serviceTax the service tax
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis serviceTax(BigDecimal serviceTax) {
        this.serviceTax = serviceTax;
        return this;
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
        return this.providentFundCharges;
    }

    /**
     * Provident fund charges.
     *
     * @param providentFundCharges the provident fund charges
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis providentFundCharges(BigDecimal providentFundCharges) {
        this.providentFundCharges = providentFundCharges;
        return this;
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
        return this.esiCharges;
    }

    /**
     * Esi charges.
     *
     * @param esiCharges the esi charges
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis esiCharges(BigDecimal esiCharges) {
        this.esiCharges = esiCharges;
        return this;
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
        return this.idcCharges;
    }

    /**
     * Idc charges.
     *
     * @param idcCharges the idc charges
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis idcCharges(BigDecimal idcCharges) {
        this.idcCharges = idcCharges;
        return this;
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
        return this.watchAndWardCost;
    }

    /**
     * Watch and ward cost.
     *
     * @param watchAndWardCost the watch and ward cost
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis watchAndWardCost(BigDecimal watchAndWardCost) {
        this.watchAndWardCost = watchAndWardCost;
        return this;
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
        return this.insuranceCost;
    }

    /**
     * Insurance cost.
     *
     * @param insuranceCost the insurance cost
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis insuranceCost(BigDecimal insuranceCost) {
        this.insuranceCost = insuranceCost;
        return this;
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
        return this.statutoryCharges;
    }

    /**
     * Statutory charges.
     *
     * @param statutoryCharges the statutory charges
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis statutoryCharges(BigDecimal statutoryCharges) {
        this.statutoryCharges = statutoryCharges;
        return this;
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
        return this.compensationCost;
    }

    /**
     * Compensation cost.
     *
     * @param compensationCost the compensation cost
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis compensationCost(BigDecimal compensationCost) {
        this.compensationCost = compensationCost;
        return this;
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
        return this.raCompletedYn;
    }

    /**
     * Ra completed yn.
     *
     * @param raCompletedYn the ra completed yn
     * @return the work estimate rate analysis
     */
    public WorkEstimateRateAnalysis raCompletedYn(Boolean raCompletedYn) {
        this.raCompletedYn = raCompletedYn;
        return this;
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
        if (!(o instanceof WorkEstimateRateAnalysis)) {
            return false;
        }
        return id != null && id.equals(((WorkEstimateRateAnalysis) o).id);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
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
        return "WorkEstimateRateAnalysis{" +
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
