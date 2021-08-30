package com.dxc.eproc.estimate.service.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dxc.eproc.client.sor.dto.AreaWeightageMasterDTO;

public class RateAnalysisDetailsDTO {

	private String itemCode;

	private String itemDescription;
	
	private String workType;

	private String circleName;

	private List<WorkEstimateMarketRateDTO> prevailingRatesList;

	private BigDecimal prevailingRatesTotal;

	private boolean areaWeightageReq;

	private List<AreaWeightageMasterDTO> areaWeightageList;

	private BigDecimal areaWeightageValue;

	private boolean leadChargesReq;

	private BigDecimal initialLead;

	private List<WorkEstimateLeadChargesDTO> leadChargesList;

	private BigDecimal leadChargesTotal;

	private boolean liftChargesReq;

	private List<WorkEstimateLiftChargesDTO> liftChargesList;

	private BigDecimal liftChargesTotal;

	private boolean loadingUnloadingChargesReq;

	private List<WorkEstimateLoadUnloadChargesDTO> loadUnloadChargesList;

	private BigDecimal loadUnloadChargesTotal;

	private boolean royaltyChargesReq;

	private List<WorkEstimateRoyaltyChargesDTO> royaltyChargesList;

	private BigDecimal royaltyChargesTotal;

	private boolean contractorProfitReq;

	private BigDecimal contractorProfitForDiffInMaterial;

	private boolean overheadChargesReq;

	private BigDecimal overheadAmount;

	private boolean taxReq;

	private BigDecimal taxAmount;

	private boolean localityAllowanceReq;

	private BigDecimal totalLocalityAllowance;

	private boolean employeeCostReq;

	private BigDecimal totalEmployeeCost;

	private boolean contingenciesReq;

	private BigDecimal totalContingencies;

	private boolean transportationCostReq;

	private BigDecimal totalTransportationCost;

	private boolean serviceTaxReq;

	private BigDecimal totalServiceTax;

	private boolean providentFundReq;

	private BigDecimal totalProvidentFundCharges;

	private boolean esiChargesReq;

	private BigDecimal totalEsiCharges;

	private boolean idcChargesReq;

	private BigDecimal totalIdcCharges;

	private boolean watchAndWardReq;

	private BigDecimal totalWatchAndWardCost;

	private boolean insuranceCostReq;

	private BigDecimal totalInsuranceCost;

	private boolean statutoryChargesReq;

	private boolean compensationCostReq;

	private boolean labourChargesReq;

	private boolean additionalChargesReq;

	private WorkEstimateAdditionalChargesDTO additionalCharges;

	private boolean addlLiftChargesReq;

	private List<WorkEstimateOtherAddnLiftChargesDTO> addnLiftChargesList;

	private BigDecimal addnLiftChargesTotal;

	private boolean otherChargesReq;

	private List<WorkEstimateOtherAddnLiftChargesDTO> addnOtherChargesList;

	private BigDecimal addnOtherChargesTotal;

	private BigDecimal grandTotal;

	private WorkEstimateRateAnalysisDTO workEstimateRateAnalysis;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public List<WorkEstimateMarketRateDTO> getPrevailingRatesList() {
		return prevailingRatesList;
	}

	public void setPrevailingRatesList(List<WorkEstimateMarketRateDTO> prevailingRatesList) {
		this.prevailingRatesList = prevailingRatesList;
	}

	public BigDecimal getPrevailingRatesTotal() {
		return prevailingRatesTotal;
	}

	public void setPrevailingRatesTotal(BigDecimal prevailingRatesTotal) {
		this.prevailingRatesTotal = prevailingRatesTotal;
	}

	public boolean getAreaWeightageReq() {
		return areaWeightageReq;
	}

	public void setAreaWeightageReq(boolean areaWeightageReq) {
		this.areaWeightageReq = areaWeightageReq;
	}

	public List<AreaWeightageMasterDTO> getAreaWeightageList() {
		return areaWeightageList;
	}

	public void setAreaWeightageList(List<AreaWeightageMasterDTO> areaWeightageList) {
		this.areaWeightageList = areaWeightageList;
	}

	public BigDecimal getAreaWeightageValue() {
		return areaWeightageValue;
	}

	public void setAreaWeightageValue(BigDecimal areaWeightageValue) {
		this.areaWeightageValue = areaWeightageValue;
	}

	public boolean getLeadChargesReq() {
		return leadChargesReq;
	}

	public void setLeadChargesReq(boolean leadChargesReq) {
		this.leadChargesReq = leadChargesReq;
	}

	public BigDecimal getInitialLead() {
		return initialLead;
	}

	public void setInitialLead(BigDecimal initialLead) {
		this.initialLead = initialLead;
	}

	public List<WorkEstimateLeadChargesDTO> getLeadChargesList() {
		return leadChargesList;
	}

	public void setLeadChargesList(List<WorkEstimateLeadChargesDTO> leadChargesList) {
		this.leadChargesList = leadChargesList;
	}

	public BigDecimal getLeadChargesTotal() {
		return leadChargesTotal;
	}

	public void setLeadChargesTotal(BigDecimal leadChargesTotal) {
		this.leadChargesTotal = leadChargesTotal;
	}

	public boolean getLiftChargesReq() {
		return liftChargesReq;
	}

	public void setLiftChargesReq(boolean liftChargesReq) {
		this.liftChargesReq = liftChargesReq;
	}

	public List<WorkEstimateLiftChargesDTO> getLiftChargesList() {
		return liftChargesList;
	}

	public void setLiftChargesList(List<WorkEstimateLiftChargesDTO> liftChargesList) {
		this.liftChargesList = liftChargesList;
	}

	public BigDecimal getLiftChargesTotal() {
		return liftChargesTotal;
	}

	public void setLiftChargesTotal(BigDecimal liftChargesTotal) {
		this.liftChargesTotal = liftChargesTotal;
	}

	public boolean getLoadingUnloadingChargesReq() {
		return loadingUnloadingChargesReq;
	}

	public void setLoadingUnloadingChargesReq(boolean loadingUnloadingChargesReq) {
		this.loadingUnloadingChargesReq = loadingUnloadingChargesReq;
	}

	public List<WorkEstimateLoadUnloadChargesDTO> getLoadUnloadChargesList() {
		return loadUnloadChargesList;
	}

	public void setLoadUnloadChargesList(List<WorkEstimateLoadUnloadChargesDTO> loadUnloadChargesList) {
		this.loadUnloadChargesList = loadUnloadChargesList;
	}

	public BigDecimal getLoadUnloadChargesTotal() {
		return loadUnloadChargesTotal;
	}

	public void setLoadUnloadChargesTotal(BigDecimal loadUnloadChargesTotal) {
		this.loadUnloadChargesTotal = loadUnloadChargesTotal;
	}

	public boolean getRoyaltyChargesReq() {
		return royaltyChargesReq;
	}

	public void setRoyaltyChargesReq(boolean royaltyChargesReq) {
		this.royaltyChargesReq = royaltyChargesReq;
	}

	public List<WorkEstimateRoyaltyChargesDTO> getRoyaltyChargesList() {
		return royaltyChargesList;
	}

	public void setRoyaltyChargesList(List<WorkEstimateRoyaltyChargesDTO> royaltyChargesList) {
		this.royaltyChargesList = royaltyChargesList;
	}

	public BigDecimal getRoyaltyChargesTotal() {
		return royaltyChargesTotal;
	}

	public void setRoyaltyChargesTotal(BigDecimal royaltyChargesTotal) {
		this.royaltyChargesTotal = royaltyChargesTotal;
	}

	public boolean getContractorProfitReq() {
		return contractorProfitReq;
	}

	public void setContractorProfitReq(boolean contractorProfitReq) {
		this.contractorProfitReq = contractorProfitReq;
	}

	public BigDecimal getContractorProfitForDiffInMaterial() {
		return contractorProfitForDiffInMaterial;
	}

	public void setContractorProfitForDiffInMaterial(BigDecimal contractorProfitForDiffInMaterial) {
		this.contractorProfitForDiffInMaterial = contractorProfitForDiffInMaterial;
	}

	public boolean getOverheadChargesReq() {
		return overheadChargesReq;
	}

	public void setOverheadChargesReq(boolean overheadChargesReq) {
		this.overheadChargesReq = overheadChargesReq;
	}

	public BigDecimal getOverheadAmount() {
		return overheadAmount;
	}

	public void setOverheadAmount(BigDecimal overheadAmount) {
		this.overheadAmount = overheadAmount;
	}

	public boolean getTaxReq() {
		return taxReq;
	}

	public void setTaxReq(boolean taxReq) {
		this.taxReq = taxReq;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public boolean getLocalityAllowanceReq() {
		return localityAllowanceReq;
	}

	public void setLocalityAllowanceReq(boolean localityAllowanceReq) {
		this.localityAllowanceReq = localityAllowanceReq;
	}

	public BigDecimal getTotalLocalityAllowance() {
		return totalLocalityAllowance;
	}

	public void setTotalLocalityAllowance(BigDecimal totalLocalityAllowance) {
		this.totalLocalityAllowance = totalLocalityAllowance;
	}

	public boolean getEmployeeCostReq() {
		return employeeCostReq;
	}

	public void setEmployeeCostReq(boolean employeeCostReq) {
		this.employeeCostReq = employeeCostReq;
	}

	public BigDecimal getTotalEmployeeCost() {
		return totalEmployeeCost;
	}

	public void setTotalEmployeeCost(BigDecimal totalEmployeeCost) {
		this.totalEmployeeCost = totalEmployeeCost;
	}

	public boolean getContingenciesReq() {
		return contingenciesReq;
	}

	public void setContingenciesReq(boolean contingenciesReq) {
		this.contingenciesReq = contingenciesReq;
	}

	public BigDecimal getTotalContingencies() {
		return totalContingencies;
	}

	public void setTotalContingencies(BigDecimal totalContingencies) {
		this.totalContingencies = totalContingencies;
	}

	public boolean getTransportationCostReq() {
		return transportationCostReq;
	}

	public void setTransportationCostReq(boolean transportationCostReq) {
		this.transportationCostReq = transportationCostReq;
	}

	public BigDecimal getTotalTransportationCost() {
		return totalTransportationCost;
	}

	public void setTotalTransportationCost(BigDecimal totalTransportationCost) {
		this.totalTransportationCost = totalTransportationCost;
	}

	public boolean getServiceTaxReq() {
		return serviceTaxReq;
	}

	public void setServiceTaxReq(boolean serviceTaxReq) {
		this.serviceTaxReq = serviceTaxReq;
	}

	public BigDecimal getTotalServiceTax() {
		return totalServiceTax;
	}

	public void setTotalServiceTax(BigDecimal totalServiceTax) {
		this.totalServiceTax = totalServiceTax;
	}

	public boolean getProvidentFundReq() {
		return providentFundReq;
	}

	public void setProvidentFundReq(boolean providentFundReq) {
		this.providentFundReq = providentFundReq;
	}

	public BigDecimal getTotalProvidentFundCharges() {
		return totalProvidentFundCharges;
	}

	public void setTotalProvidentFundCharges(BigDecimal totalProvidentFundCharges) {
		this.totalProvidentFundCharges = totalProvidentFundCharges;
	}

	public boolean getEsiChargesReq() {
		return esiChargesReq;
	}

	public void setEsiChargesReq(boolean esiChargesReq) {
		this.esiChargesReq = esiChargesReq;
	}

	public BigDecimal getTotalEsiCharges() {
		return totalEsiCharges;
	}

	public void setTotalEsiCharges(BigDecimal totalEsiCharges) {
		this.totalEsiCharges = totalEsiCharges;
	}

	public boolean getIdcChargesReq() {
		return idcChargesReq;
	}

	public void setIdcChargesReq(boolean idcChargesReq) {
		this.idcChargesReq = idcChargesReq;
	}

	public BigDecimal getTotalIdcCharges() {
		return totalIdcCharges;
	}

	public void setTotalIdcCharges(BigDecimal totalIdcCharges) {
		this.totalIdcCharges = totalIdcCharges;
	}

	public boolean getWatchAndWardReq() {
		return watchAndWardReq;
	}

	public void setWatchAndWardReq(boolean watchAndWardReq) {
		this.watchAndWardReq = watchAndWardReq;
	}

	public BigDecimal getTotalWatchAndWardCost() {
		return totalWatchAndWardCost;
	}

	public void setTotalWatchAndWardCost(BigDecimal totalWatchAndWardCost) {
		this.totalWatchAndWardCost = totalWatchAndWardCost;
	}

	public boolean getInsuranceCostReq() {
		return insuranceCostReq;
	}

	public void setInsuranceCostReq(boolean insuranceCostReq) {
		this.insuranceCostReq = insuranceCostReq;
	}

	public BigDecimal getTotalInsuranceCost() {
		return totalInsuranceCost;
	}

	public void setTotalInsuranceCost(BigDecimal totalInsuranceCost) {
		this.totalInsuranceCost = totalInsuranceCost;
	}

	public boolean getStatutoryChargesReq() {
		return statutoryChargesReq;
	}

	public void setStatutoryChargesReq(boolean statutoryChargesReq) {
		this.statutoryChargesReq = statutoryChargesReq;
	}

	public boolean getCompensationCostReq() {
		return compensationCostReq;
	}

	public void setCompensationCostReq(boolean compensationCostReq) {
		this.compensationCostReq = compensationCostReq;
	}

	public boolean getLabourChargesReq() {
		return labourChargesReq;
	}

	public void setLabourChargesReq(boolean labourChargesReq) {
		this.labourChargesReq = labourChargesReq;
	}

	public boolean getAdditionalChargesReq() {
		return additionalChargesReq;
	}

	public void setAdditionalChargesReq(boolean additionalChargesReq) {
		this.additionalChargesReq = additionalChargesReq;
	}

	public WorkEstimateAdditionalChargesDTO getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(WorkEstimateAdditionalChargesDTO additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public boolean getAddlLiftChargesReq() {
		return addlLiftChargesReq;
	}

	public void setAddlLiftChargesReq(boolean addlLiftChargesReq) {
		this.addlLiftChargesReq = addlLiftChargesReq;
	}

	public List<WorkEstimateOtherAddnLiftChargesDTO> getAddnLiftChargesList() {
		return addnLiftChargesList;
	}

	public void setAddnLiftChargesList(List<WorkEstimateOtherAddnLiftChargesDTO> addnLiftChargesList) {
		this.addnLiftChargesList = addnLiftChargesList;
	}

	public BigDecimal getAddnLiftChargesTotal() {
		return addnLiftChargesTotal;
	}

	public void setAddnLiftChargesTotal(BigDecimal addnLiftChargesTotal) {
		this.addnLiftChargesTotal = addnLiftChargesTotal;
	}

	public boolean getOtherChargesReq() {
		return otherChargesReq;
	}

	public void setOtherChargesReq(boolean otherChargesReq) {
		this.otherChargesReq = otherChargesReq;
	}

	public List<WorkEstimateOtherAddnLiftChargesDTO> getAddnOtherChargesList() {
		return addnOtherChargesList;
	}

	public void setAddnOtherChargesList(List<WorkEstimateOtherAddnLiftChargesDTO> addnOtherChargesList) {
		this.addnOtherChargesList = addnOtherChargesList;
	}

	public BigDecimal getAddnOtherChargesTotal() {
		return addnOtherChargesTotal;
	}

	public void setAddnOtherChargesTotal(BigDecimal addnOtherChargesTotal) {
		this.addnOtherChargesTotal = addnOtherChargesTotal;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public WorkEstimateRateAnalysisDTO getWorkEstimateRateAnalysis() {
		return workEstimateRateAnalysis;
	}

	public void setWorkEstimateRateAnalysis(WorkEstimateRateAnalysisDTO workEstimateRateAnalysis) {
		this.workEstimateRateAnalysis = workEstimateRateAnalysis;
	}

}
