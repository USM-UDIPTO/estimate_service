package com.dxc.eproc.estimate.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.cheffo.jeplite.JEP;
import org.cheffo.jeplite.util.DoubleStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.eproc.client.sor.api.AreaWeightageMasterControllerApiClient;
import com.dxc.eproc.client.sor.api.FinancialYearsControllerApiClient;
import com.dxc.eproc.client.sor.api.MaterialMasterControllerApiClient;
import com.dxc.eproc.client.sor.api.SorCategoryControllerApiClient;
import com.dxc.eproc.client.sor.api.WorkItemControllerApiClient;
import com.dxc.eproc.client.sor.dto.AreaWeightageMasterDTO;
import com.dxc.eproc.client.sor.dto.FinancialYearsDTO;
import com.dxc.eproc.client.sor.dto.MaterialChargesDTO;
import com.dxc.eproc.client.sor.dto.MaterialLeadDetailsDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterDetailsDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterLoadUnloadDetailsDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterRoyaltyChargesDetailsDTO;
import com.dxc.eproc.client.sor.dto.NotesMasterDTO;
import com.dxc.eproc.client.sor.dto.SorCategoryDTO;
import com.dxc.eproc.client.sor.dto.WorkItemDTO;
import com.dxc.eproc.client.user.api.DepartmentControllerApiClient;
import com.dxc.eproc.client.user.dto.DepartmentDTO;
import com.dxc.eproc.client.user.dto.ResponseDTODepartmentDTO;
import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.enumeration.RaChargeType;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.enumeration.WorkType;
import com.dxc.eproc.estimate.service.RaConfigurationService;
import com.dxc.eproc.estimate.service.RaFormulaService;
import com.dxc.eproc.estimate.service.RaParametersService;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateAdditionalChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateLeadChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateLiftChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateLoadUnloadChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateMarketRateService;
import com.dxc.eproc.estimate.service.WorkEstimateOtherAddnLiftChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateRateAnalysisService;
import com.dxc.eproc.estimate.service.WorkEstimateRoyaltyChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkTypeService;
import com.dxc.eproc.estimate.service.dto.RaConfigurationDTO;
import com.dxc.eproc.estimate.service.dto.RaFormulaDTO;
import com.dxc.eproc.estimate.service.dto.RaParametersDTO;
import com.dxc.eproc.estimate.service.dto.RateAnalysisDetailsDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateAdditionalChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLeadChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLiftChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLoadUnloadChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateMarketRateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOtherAddnLiftChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRoyaltyChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.InterServiceCommunicationException;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.Utility;

import feign.FeignException.FeignClientException;

@RestController
@Transactional
@RequestMapping("/v1/api")
public class RateAnalysisController {

	private final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupOverheadController.class);

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	@Autowired
	private WorkTypeService workTypeService;

	@Autowired
	private WorkEstimateRateAnalysisService workEstimateRateAnalysisService;

	@Autowired
	private WorkEstimateMarketRateService workEstimateMarketRateService;

	@Autowired
	private RaConfigurationService raConfigurationService;

	@Autowired
	private RaParametersService raParametersService;

	@Autowired
	private RaFormulaService raFormulaService;

	@Autowired
	private WorkEstimateLeadChargesService workEstimateLeadChargesService;

	@Autowired
	private WorkEstimateLiftChargesService workEstimateLiftChargesService;

	@Autowired
	private WorkEstimateLoadUnloadChargesService workEstimateLoadUnloadChargesService;

	@Autowired
	private WorkEstimateRoyaltyChargesService workEstimateRoyaltyChargesService;

	@Autowired
	private WorkEstimateAdditionalChargesService workEstimateAdditionalChargesService;

	@Autowired
	private WorkEstimateOtherAddnLiftChargesService workEstimateOtherAddnLiftChargesService;

	@Autowired
	private SorCategoryControllerApiClient sorCategoryControllerApiClient;

	@Autowired
	private FinancialYearsControllerApiClient financialYearsControllerApiClient;

	@Autowired
	private AreaWeightageMasterControllerApiClient areaWeightageMasterControllerApiClient;

	@Autowired
	private MaterialMasterControllerApiClient materialMasterControllerApiClient;

	@Autowired
	private WorkItemControllerApiClient workItemControllerApiClient;

	@Autowired
	private DepartmentControllerApiClient departmentControllerApiClient;

	@Autowired
	private FrameworkComponent frameworkComponent;

	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/view")
	public ResponseEntity<?> viewRateAnalysis(@PathVariable("workEstimateId") Long workEstimateId,
			@PathVariable("subEstimateId") Long subEstimateId, @PathVariable("itemId") Long itemId) throws Exception {
		log.info("view RateAnalysis starts");

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("workEstimate.recordNotFound"),
						"WorkEstimate"));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("subEstimate.recordNotFound"),
						"SubEstimate"));

		WorkEstimateItemDTO itemDTO = workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound"), "WorkEstimateItem"));

		if (itemDTO.getLabourRate() == null) { // TODO if required or not
			itemDTO.setLabourRate(BigDecimal.ZERO);
		}

		WorkTypeDTO workTypeDTO = workTypeService.findByIdAndActiveYnTrue(workEstimateDTO.getWorkTypeId()).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("entity.notFound"), "workType"));

		WorkItemDTO workItemDTO = new WorkItemDTO();
		try {
			ResponseEntity<WorkItemDTO> workItemResponse = workItemControllerApiClient
					.getWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
			workItemDTO = workItemResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				throw new RecordNotFoundException("WorkItem not present for the given Id!!", "workItem");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}
		BigDecimal initialLead = workItemDTO.getInitialLead();

		RateAnalysisDetailsDTO raDetailsDTO = new RateAnalysisDetailsDTO();
		raDetailsDTO.setItemCode(itemDTO.getItemCode());
		raDetailsDTO.setItemDescription(itemDTO.getDescription());
		raDetailsDTO.setWorkType(workTypeDTO.getValueType());

		SorCategoryDTO sorCategoryDTO = null;
		try {
			ResponseEntity<SorCategoryDTO> sorCategoryResponse = sorCategoryControllerApiClient
					.getSorCategoryUsingGET(workItemDTO.getSorCategoryId());
			sorCategoryDTO = sorCategoryResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				throw new RecordNotFoundException("SorCategory not present for the given Id!!", "SorCategory");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}
		Long sorParentId = sorCategoryDTO.getParentId();
		Long circleId = sorCategoryDTO.getCircleId();

		DepartmentDTO circleDTO = null;
		try {
			ResponseEntity<ResponseDTODepartmentDTO> departmentResponse = departmentControllerApiClient
					.getDepartmentUsingGET(circleId.intValue());
			circleDTO = departmentResponse.getBody().getResponseObject();
			raDetailsDTO.setCircleName(circleDTO.getDeptName());
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				throw new RecordNotFoundException("Circle not present for the given Id!!", "Department");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}
		Integer circleParentId = circleDTO.getParentId();

		FinancialYearsDTO financialYearsDTO = null;
		try {
			ResponseEntity<FinancialYearsDTO> financialYearResponse = financialYearsControllerApiClient
					.getFinancialYearByCategoryUsingGET(sorCategoryDTO.getParentId());
			financialYearsDTO = financialYearResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				throw new RecordNotFoundException("FinancialYear not present for the SorCategory!!",
						"FinancialYearMaster");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}

		Optional<WorkEstimateRateAnalysisDTO> weRateAnalysisDTOOptional = workEstimateRateAnalysisService
				.findByWorkEstimateItemId(itemId);
		WorkEstimateRateAnalysisDTO weRateAnalysisDTO = new WorkEstimateRateAnalysisDTO();

		weRateAnalysisDTO.setWorkEstimateId(workEstimateId);
		weRateAnalysisDTO.setWorkEstimateItemId(itemId);
		weRateAnalysisDTO.setSorFinancialYear(financialYearsDTO.getYear());
		weRateAnalysisDTO.setBasicRate(itemDTO.getBaseRate());
		weRateAnalysisDTO.setFloorNo(itemDTO.getFloorNumber() != null ? Long.valueOf(itemDTO.getFloorNumber()) : 0L);

		// Prevailing Rates ---------->
		List<MaterialMasterDetailsDTO> materialMasterDetailsList = null;
		try {
			ResponseEntity<List<MaterialMasterDetailsDTO>> materialMasterDetailsResponse = materialMasterControllerApiClient
					.getMaterialDetailsByWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
			materialMasterDetailsList = materialMasterDetailsResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				log.error("--------------> Materials not present for the WorkItem!!");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}

		List<WorkEstimateMarketRateDTO> weMarketRateDTOList = workEstimateMarketRateService
				.findByWorkEstimateItemId(itemId);
		if (Utility.isValidCollection(materialMasterDetailsList)) {
			materialMasterDetailsList.forEach(material -> {
				long flag = 0;
				if (Utility.isValidCollection(weMarketRateDTOList)) {
					flag = weMarketRateDTOList.stream().map(marketRate -> {
						if (marketRate.getMaterialMasterId() == material.getMaterialId()) {
							return true;
						}
						return null;
					}).filter(Objects::nonNull).count();
				}
				if (flag == 0) {
					WorkEstimateMarketRateDTO weMarketRateDTO = new WorkEstimateMarketRateDTO();
					weMarketRateDTO.setMaterialMasterId(material.getMaterialId());
					weMarketRateDTO.setMaterialName(material.getMaterialName());
					weMarketRateDTO.setQuantity(material.getQuantity());
					weMarketRateDTO.setRate(material.getRate());
					weMarketRateDTO.setPrevailingMarketRate(weMarketRateDTO.getRate());
					weMarketRateDTO.setUomId(material.getUomId());
					weMarketRateDTO.setUomName(material.getUomName());
					weMarketRateDTO.setDifference(BigDecimal.ZERO);
					weMarketRateDTO.setTotal(BigDecimal.ZERO);
					weMarketRateDTO.setSubEstimateId(subEstimateId);
					weMarketRateDTO.setWorkEstimateId(workEstimateId);
					weMarketRateDTO.setWorkEstimateItemId(itemId);
					weMarketRateDTOList.add(weMarketRateDTO);
				} else {
					weMarketRateDTOList.stream().filter(m -> m.getMaterialMasterId() == material.getMaterialId())
							.forEach(marketRate -> {
								marketRate.setMaterialName(material.getMaterialName());
								marketRate.setQuantity(material.getQuantity());
								marketRate.setTotal(marketRate.getQuantity().multiply(marketRate.getDifference()));
								marketRate.setRate(material.getRate());
								marketRate.setUomId(material.getUomId());
								marketRate.setUomName(material.getUomName());
							});
				}
			});
		}
		raDetailsDTO.setPrevailingRatesList(weMarketRateDTOList);
		BigDecimal prevailingRatesTotal = weMarketRateDTOList.stream().map(m -> m.getTotal()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		raDetailsDTO.setPrevailingRatesTotal(prevailingRatesTotal);

		List<RaConfigurationDTO> raConfigDTOList = raConfigurationService.findAllByDeptId(circleId);
		if (Utility.isValidCollection(raConfigDTOList)) {
			Set<Long> raParamIds = raConfigDTOList.stream().map(c -> c.getRaParamId()).collect(Collectors.toSet());
			List<RaParametersDTO> raParams = raParametersService.findAllByIdIn(raParamIds);
			if (raParams.size() != raParamIds.size()) {
				throw new RecordNotFoundException(frameworkComponent.resolveI18n("raConfiguration.invalidId"),
						"raConfiguration");
			}
			raParams.forEach(param -> {
				log.info("Parameter name: " + param.getParamName());
				if (param.getParamName().equalsIgnoreCase("area weightage")) {
					raDetailsDTO.setAreaWeightageReq(true);
					List<AreaWeightageMasterDTO> areaWeightageMasterList = null;
					try {
						ResponseEntity<List<AreaWeightageMasterDTO>> areaWeightageMastersResponse = areaWeightageMasterControllerApiClient
								.getAreaWeightageMastersforCategoryUsingGET(sorParentId);
						areaWeightageMasterList = areaWeightageMastersResponse.getBody();
					} catch (FeignClientException e) {
						if (e.status() == 404) {
							log.error("-----------------> Area Weightage not present for the category!!");
						} else {
							throw new InterServiceCommunicationException(e.getMessage());
						}
					}
					raDetailsDTO.setAreaWeightageList(areaWeightageMasterList);
					weRateAnalysisDTO.setAreaWeightageCircleId(circleId);
					weRateAnalysisDTO.setAreaWeightageMasterId(null);
					weRateAnalysisDTO.setAreaWeightagePercentage(BigDecimal.ZERO);
					raDetailsDTO.setAreaWeightageValue(BigDecimal.ZERO);
				}

				// Lead Charges ---------------->
				if (param.getParamName().equalsIgnoreCase("material level lead rate")
						|| param.getParamName().equalsIgnoreCase("item level lead rate")) {
					raDetailsDTO.setLeadChargesReq(true);
					raDetailsDTO.setInitialLead(initialLead);
					List<WorkEstimateLeadChargesDTO> weLeadChargesDTOList = workEstimateLeadChargesService
							.findByWorkEstimateItemId(itemId);
					List<MaterialMasterDetailsDTO> leadMaterialsList = null;
					try {
						ResponseEntity<List<MaterialMasterDetailsDTO>> leadOrLiftChargesForWorkItemResponse = materialMasterControllerApiClient
								.getLeadOrLiftChargesForWorkItemUsingGET(itemDTO.getCatWorkSorItemId(),
										RaChargeType.LEAD_CHARGE.toString());
						leadMaterialsList = leadOrLiftChargesForWorkItemResponse.getBody();
					} catch (FeignClientException e) {
						if (e.status() == 404) {
							log.error("-------------> Lead Charge not present for the WorkItem!!");
						} else if (e.status() == 400) {
							throw new BadRequestAlertException("Invalid Charge Type Name!!", "ChargeTypeMaster",
									"InvalidChargeType");
						} else {
							throw new InterServiceCommunicationException(e.getMessage());
						}
					}
					if (Utility.isValidCollection(leadMaterialsList)) {
						leadMaterialsList.forEach(material -> {
							long flag = 0;
							if (!weLeadChargesDTOList.isEmpty()) {
								flag = weLeadChargesDTOList.stream().map(leadCharge -> {
									if (leadCharge.getMaterialMasterId() == material.getMaterialId()) {
										return true;
									}
									return null;
								}).filter(Objects::nonNull).count();
							}
							if (flag == 0) {
								WorkEstimateLeadChargesDTO weLeadChargesDTO = new WorkEstimateLeadChargesDTO();
								weLeadChargesDTO.setMaterialMasterId(material.getMaterialId());
								weLeadChargesDTO.setMaterialName(material.getMaterialName());
								weLeadChargesDTO.setQuantity(material.getQuantity());
								weLeadChargesDTO.setLeadCharges(BigDecimal.ZERO);
								weLeadChargesDTO.setTotal(BigDecimal.ZERO);
								weLeadChargesDTO.setCatWorkSorItemId(itemDTO.getCatWorkSorItemId());
								weLeadChargesDTO.setSubEstimateId(subEstimateId);
								weLeadChargesDTO.setWorkEstimateId(workEstimateId);
								weLeadChargesDTO.setWorkEstimateItemId(itemId);
								weLeadChargesDTOList.add(weLeadChargesDTO);
							} else {
								weLeadChargesDTOList.stream()
										.filter(l -> l.getMaterialMasterId() == material.getMaterialId())
										.forEach(leadCharge -> {
											MaterialLeadDetailsDTO leadDetails = new MaterialLeadDetailsDTO();
											leadDetails.setItemId(itemDTO.getCatWorkSorItemId());
											leadDetails.setLeadInKm(leadCharge.getLeadInKm());
											leadDetails.setLeadInMtrs(leadCharge.getLeadInM());
											leadDetails.setInitialLeadRequiredYn(leadCharge.getInitialLeadRequiredYn());
											ResponseEntity<MaterialChargesDTO> leadChargesResponse = materialMasterControllerApiClient
													.getLeadChargesForMaterialUsingPOST(material.getMaterialId(),
															leadDetails);
											MaterialChargesDTO matChargesDTO = leadChargesResponse.getBody();
											leadCharge.setMaterialName(material.getMaterialName());
											leadCharge.setQuantity(material.getQuantity());
											leadCharge.setLeadCharges(matChargesDTO.getMaterialCharges());
											leadCharge.setTotal(matChargesDTO.getMaterialCharges()
													.multiply(material.getQuantity()));
										});
							}
						});
					}
					raDetailsDTO.setLeadChargesList(weLeadChargesDTOList);
					BigDecimal leadChargesTotal = weLeadChargesDTOList.stream().map(l -> l.getTotal())
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					raDetailsDTO.setLeadChargesTotal(leadChargesTotal);
				}

				// Lift Charges ----------------->
				if (param.getParamName().equalsIgnoreCase("lift rate")) {
					raDetailsDTO.setLiftChargesReq(true);
					List<WorkEstimateLiftChargesDTO> weLiftChargesDTOList = workEstimateLiftChargesService
							.findByWorkEstimateItemId(itemId);
					List<MaterialMasterDetailsDTO> liftMaterialsList = null;
					try {
						ResponseEntity<List<MaterialMasterDetailsDTO>> leadOrLiftChargesForWorkItemResponse = materialMasterControllerApiClient
								.getLeadOrLiftChargesForWorkItemUsingGET(itemDTO.getCatWorkSorItemId(),
										RaChargeType.LIFT_CHARGE.toString());
						liftMaterialsList = leadOrLiftChargesForWorkItemResponse.getBody();
					} catch (FeignClientException e) {
						if (e.status() == 404) {
							log.error("-------------------> Lift Charge not present for the WorkItem!!");
						} else if (e.status() == 400) {
							throw new BadRequestAlertException("Invalid Charge Type Name!!", "ChargeTypeMaster",
									"InvalidChargeType");
						} else {
							throw new InterServiceCommunicationException(e.getMessage());
						}
					}
					if (Utility.isValidCollection(liftMaterialsList)) {
						liftMaterialsList.forEach(material -> {
							long flag = 0;
							if (!weLiftChargesDTOList.isEmpty()) {
								flag = weLiftChargesDTOList.stream().map(liftCharge -> {
									if (liftCharge.getMaterialMasterId() == material.getMaterialId()) {
										return true;
									}
									return null;
								}).filter(Objects::nonNull).count();
							}
							if (flag == 0) {
								WorkEstimateLiftChargesDTO liftChargeDTO = new WorkEstimateLiftChargesDTO();
								liftChargeDTO.setMaterialMasterId(material.getMaterialId());
								liftChargeDTO.setMaterialName(material.getMaterialName());
								liftChargeDTO.setUomId(material.getUomId());
								liftChargeDTO.setUomName(material.getUomName());
								liftChargeDTO.setQuantity(material.getQuantity());
								liftChargeDTO.setLiftCharges(BigDecimal.ZERO);
								liftChargeDTO.setWorkEstimateItemId(itemId);
								liftChargeDTO.setTotal(BigDecimal.ZERO);
								weLiftChargesDTOList.add(liftChargeDTO);
							} else {
								weLiftChargesDTOList.stream()
										.filter(l -> l.getMaterialMasterId() == material.getMaterialId())
										.forEach(liftChargeDTO -> {
											liftChargeDTO.setMaterialName(material.getMaterialName());
											liftChargeDTO.setUomId(material.getUomId());
											liftChargeDTO.setUomName(material.getUomName());
											liftChargeDTO.setTotal(liftChargeDTO.getQuantity()
													.multiply(liftChargeDTO.getLiftCharges()));
										});
							}
						});
					}
					raDetailsDTO.setLiftChargesList(weLiftChargesDTOList);
					BigDecimal liftChargesTotal = weLiftChargesDTOList.stream().map(l -> l.getTotal())
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					raDetailsDTO.setLiftChargesTotal(liftChargesTotal);
				}

				// Loading-Unloading Charges ----------------->
				if ((param.getParamName().equalsIgnoreCase("material level loading charges")
						|| param.getParamName().equalsIgnoreCase("item level loading charges")
						|| param.getParamName().equalsIgnoreCase("material level unloading charges")
						|| param.getParamName().equalsIgnoreCase("item level unloading charges"))
						&& !Utility.isValidCollection(raDetailsDTO.getLoadUnloadChargesList())) {
					raDetailsDTO.setLoadingUnloadingChargesReq(true);
					List<WorkEstimateLoadUnloadChargesDTO> weLoadUnloadChargesDTOList = workEstimateLoadUnloadChargesService
							.findByWorkEstimateItemId(itemId);
					List<MaterialMasterLoadUnloadDetailsDTO> materialMasterLdUdDetailsDTOList = null;
					try {
						ResponseEntity<List<MaterialMasterLoadUnloadDetailsDTO>> loadUnloadResponse = materialMasterControllerApiClient
								.getLoadUnloadforMaterialDetailsByWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
						materialMasterLdUdDetailsDTOList = loadUnloadResponse.getBody();
					} catch (FeignClientException e) {
						if (e.status() == 404) {
							log.error("------------------------> LoadUnload Charge not present for the WorkItem!!");
						} else {
							throw new InterServiceCommunicationException(e.getMessage());
						}
					}
					if (Utility.isValidCollection(materialMasterLdUdDetailsDTOList)) {
						materialMasterLdUdDetailsDTOList.forEach(ldUdDetails -> {
							long flag = 0;
							if (!weLoadUnloadChargesDTOList.isEmpty()) {
								flag = weLoadUnloadChargesDTOList.stream().map(ldUdCharges -> {
									if (ldUdCharges.getMaterialMasterId() == ldUdDetails.getMaterialId()) {
										return true;
									}
									return null;
								}).filter(Objects::nonNull).count();
							}
							if (flag == 0) {
								WorkEstimateLoadUnloadChargesDTO weLoadUnloadChargeDTO = new WorkEstimateLoadUnloadChargesDTO();
								weLoadUnloadChargeDTO.setMaterialMasterId(ldUdDetails.getMaterialId());
								weLoadUnloadChargeDTO.setMaterialName(ldUdDetails.getMaterialName());
								weLoadUnloadChargeDTO.setQuantity(ldUdDetails.getQuantity());
								weLoadUnloadChargeDTO.setLoadingCharges(ldUdDetails.getLoadingCharges());
								weLoadUnloadChargeDTO.setSelectedLoadCharges(false);
								weLoadUnloadChargeDTO.setUnloadingCharges(ldUdDetails.getUnLoadingCharges());
								weLoadUnloadChargeDTO.setSelectedUnloadCharges(false);
								weLoadUnloadChargeDTO.setTotal(BigDecimal.ZERO);
								weLoadUnloadChargeDTO.setCatWorkSorItemId(itemDTO.getCatWorkSorItemId());
								weLoadUnloadChargeDTO.setSubEstimateId(subEstimateId);
								weLoadUnloadChargeDTO.setWorkEstimateId(workEstimateId);
								weLoadUnloadChargeDTO.setWorkEstimateItemId(itemId);
								weLoadUnloadChargesDTOList.add(weLoadUnloadChargeDTO);
							} else {
								weLoadUnloadChargesDTOList.stream()
										.filter(lu -> lu.getMaterialMasterId() == ldUdDetails.getMaterialId())
										.forEach(weLdUdChargesDTO -> {
											weLdUdChargesDTO.setMaterialName(ldUdDetails.getMaterialName());
											weLdUdChargesDTO.setQuantity(ldUdDetails.getQuantity());
											BigDecimal ldUdTotalCharge = BigDecimal.ZERO;
											if (weLdUdChargesDTO.getSelectedLoadCharges()) {
												ldUdTotalCharge = ldUdTotalCharge
														.add(weLdUdChargesDTO.getLoadingCharges());
											}
											if (weLdUdChargesDTO.getSelectedUnloadCharges()) {
												ldUdTotalCharge = ldUdTotalCharge
														.add(weLdUdChargesDTO.getUnloadingCharges());
											}
											weLdUdChargesDTO
													.setTotal(ldUdTotalCharge.multiply(weLdUdChargesDTO.getQuantity()));
										});
							}
						});
					}
					raDetailsDTO.setLoadUnloadChargesList(weLoadUnloadChargesDTOList);
					BigDecimal loadUnloadChargesTotal = weLoadUnloadChargesDTOList.stream().map(l -> l.getTotal())
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					raDetailsDTO.setLoadUnloadChargesTotal(loadUnloadChargesTotal);
				}

				// Royalty Charges ---------------->
				if (param.getParamName().equalsIgnoreCase("material level royalty rate")
						|| param.getParamName().equalsIgnoreCase("item level royalty rate")) {
					raDetailsDTO.setRoyaltyChargesReq(true);
					List<WorkEstimateRoyaltyChargesDTO> weRoyaltyChargesDTOList = workEstimateRoyaltyChargesService
							.findByWorkEstimateItemId(itemId);
					List<MaterialMasterRoyaltyChargesDetailsDTO> materialMasterRoyaltyChargesDetailsList = null;
					try {
						ResponseEntity<List<MaterialMasterRoyaltyChargesDetailsDTO>> royaltyResponse = materialMasterControllerApiClient
								.getRoyaltyforMaterialDetailsByWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
						materialMasterRoyaltyChargesDetailsList = royaltyResponse.getBody();
					} catch (FeignClientException e) {
						if (e.status() == 404) {
							log.error("-------------------> RoyaltyCharge not present for the WorkItem!!");
						} else {
							throw new InterServiceCommunicationException(e.getMessage());
						}
					}
					if (Utility.isValidCollection(materialMasterRoyaltyChargesDetailsList)) {
						materialMasterRoyaltyChargesDetailsList.forEach(royaltyDetails -> {
							long flag = 0;
							if (!weRoyaltyChargesDTOList.isEmpty()) {
								flag = weRoyaltyChargesDTOList.stream().map(royaltyCharges -> {
									if (royaltyCharges.getMaterialMasterId() == royaltyDetails.getMaterialId()) {
										return true;
									}
									return null;
								}).filter(Objects::nonNull).count();
							}
							if (flag == 0) {
								WorkEstimateRoyaltyChargesDTO weRoyaltyDTO = new WorkEstimateRoyaltyChargesDTO();
								weRoyaltyDTO.setMaterialMasterId(royaltyDetails.getMaterialId());
								weRoyaltyDTO.setMaterialName(royaltyDetails.getMaterialName());
								weRoyaltyDTO.setQuantity(royaltyDetails.getQuantity());
								weRoyaltyDTO.setUomId(royaltyDetails.getUomId());
								weRoyaltyDTO.setUomName(royaltyDetails.getUomName());
								weRoyaltyDTO.setPrevailingRoyaltyCharges(royaltyDetails.getRoyaltyCharges());
								weRoyaltyDTO.setSrRoyaltyCharges(royaltyDetails.getRoyaltyCharges());
								weRoyaltyDTO.setDifference(BigDecimal.ZERO);
								// TODO to verify
								try {
									ResponseEntity<ResponseDTODepartmentDTO> departmentResponse = departmentControllerApiClient
											.getDepartmentUsingGET(circleParentId);
									if (departmentResponse.getBody().getResponseObject().getDeptCode()
											.equalsIgnoreCase("MI")) {
										if (royaltyDetails.getDensityFactor() != null) {
											weRoyaltyDTO.setDensityFactor(royaltyDetails.getDensityFactor());
										} else {
											weRoyaltyDTO.setDensityFactor(BigDecimal.ZERO);
										}
									}
								} catch (FeignClientException e) {
									if (e.status() == 404) {
										throw new RecordNotFoundException("Circle not present for the given Id!!",
												"Department");
									} else {
										throw new InterServiceCommunicationException(e.getMessage());
									}
								}
								weRoyaltyDTO.setCatWorkSorItemId(itemDTO.getCatWorkSorItemId());
								weRoyaltyDTO.setWorkEstimateId(workEstimateId);
								weRoyaltyDTO.setSubEstimateId(subEstimateId);
								weRoyaltyDTO.setWorkEstimateItemId(itemId);
								weRoyaltyDTO.setTotal(BigDecimal.ZERO);
								weRoyaltyChargesDTOList.add(weRoyaltyDTO);
							} else {
								weRoyaltyChargesDTOList.stream()
										.filter(r -> r.getMaterialMasterId() == royaltyDetails.getMaterialId())
										.forEach(weRoyaltyDTO -> {
											weRoyaltyDTO.setMaterialName(royaltyDetails.getMaterialName());
											weRoyaltyDTO.setQuantity(royaltyDetails.getQuantity());
											weRoyaltyDTO.setUomId(royaltyDetails.getUomId());
											weRoyaltyDTO.setUomName(royaltyDetails.getUomName());
										});
							}
						});
					}
					raDetailsDTO.setRoyaltyChargesList(weRoyaltyChargesDTOList);
					raDetailsDTO.setRoyaltyChargesTotal(BigDecimal.ZERO);
				}

				// Contractor profit --------------->
				if (param.getParamName().equalsIgnoreCase("contractor profit")) {
					BigDecimal contractProfit = BigDecimal.ZERO;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getContractorProfitPercentage() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getContractorProfitPercentage() != null) {
							contractProfit = weRateAnalysisDTOOptional.get().getContractorProfitPercentage();
						}
					}

					weRateAnalysisDTO.setContractorProfitPercentage(contractProfit);
					raDetailsDTO.setContractorProfitForDiffInMaterial(raDetailsDTO.getPrevailingRatesTotal()
							.multiply(contractProfit.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setContractorProfitReq(true);
				}

				// Overhead percentage --------------->
				if (param.getParamName().equalsIgnoreCase("overhead percentage")) {
					BigDecimal overhead = BigDecimal.ZERO;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getOverheadPercentage() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getOverheadPercentage() != null) {
							overhead = weRateAnalysisDTOOptional.get().getOverheadPercentage();
						}
					}
					weRateAnalysisDTO.setOverheadPercentage(overhead);
					raDetailsDTO.setOverheadAmount(
							raDetailsDTO.getPrevailingRatesTotal().multiply(overhead.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setOverheadChargesReq(true);
				}

				// tax percentage -------------------->
				if (param.getParamName().equalsIgnoreCase("tax")) {
					BigDecimal tax = BigDecimal.ZERO;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getTaxPercentage() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getTaxPercentage() != null) {
							tax = weRateAnalysisDTOOptional.get().getTaxPercentage();
						}
					}
					weRateAnalysisDTO.setTaxPercentage(tax);
					raDetailsDTO.setTaxReq(true);
					raDetailsDTO.setTaxAmount(BigDecimal.ZERO);
				}

				// locality allowance ----------------->
				if (param.getParamName().equalsIgnoreCase("locality allowance")) {
					BigDecimal locality = BigDecimal.ZERO;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getLocalityAllowance() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getLocalityAllowance() != null) {
							locality = weRateAnalysisDTOOptional.get().getLocalityAllowance();
						}
					}
					weRateAnalysisDTO.setLocalityAllowance(locality);
					raDetailsDTO.setTotalLocalityAllowance(
							itemDTO.getLabourRate().multiply(locality.divide(BigDecimal.valueOf(100))));

					raDetailsDTO.setLocalityAllowanceReq(true);
				} else {
					raDetailsDTO.setTotalLocalityAllowance(BigDecimal.ZERO);
				}

				// employee Cost ---------------------->
				if (param.getParamName().equalsIgnoreCase("employees cost")) {
					BigDecimal employeeCost = BigDecimal.valueOf(60);
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getEmployeesCost() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getEmployeesCost() != null) {
							employeeCost = weRateAnalysisDTOOptional.get().getEmployeesCost();
						}
					}
					weRateAnalysisDTO.setEmployeesCost(employeeCost);
					raDetailsDTO.setTotalEmployeeCost(
							(itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance()))
									.multiply(employeeCost.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setEmployeeCostReq(true);
				}

				// contingencies -------------------------->
				if (param.getParamName().equalsIgnoreCase("contingencies")) {
					BigDecimal contingencies = BigDecimal.valueOf(3);
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getContingencies() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getContingencies() != null) {
							contingencies = weRateAnalysisDTOOptional.get().getContingencies();
						}
					}
					weRateAnalysisDTO.setContingencies(contingencies);
					raDetailsDTO.setTotalContingencies(
							itemDTO.getBaseRate().multiply(contingencies.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setContingenciesReq(true);
				}

				// transportation cost ------------------->
				if (param.getParamName().equalsIgnoreCase("transportation cost")) {
					BigDecimal transportCost = BigDecimal.valueOf(2);
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getTransportationCost() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getTransportationCost() != null) {
							transportCost = weRateAnalysisDTOOptional.get().getTransportationCost();
						}
					}
					weRateAnalysisDTO.setTransportationCost(transportCost);
					raDetailsDTO.setTotalTransportationCost((itemDTO.getBaseRate().subtract(itemDTO.getLabourRate()))
							.multiply(transportCost.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setTransportationCostReq(true);
				}

				// service tax ------------------->
				if (param.getParamName().equalsIgnoreCase("service tax")) {
					BigDecimal serviceTax = BigDecimal.valueOf(12.36);
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getServiceTax() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getServiceTax() != null) {
							serviceTax = weRateAnalysisDTOOptional.get().getServiceTax();
						}
					}
					weRateAnalysisDTO.setServiceTax(serviceTax);
					raDetailsDTO
							.setTotalServiceTax((itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance()))
									.multiply(serviceTax.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setServiceTaxReq(true);
				}

				// provident fund charges ------------------->
				if (param.getParamName().equalsIgnoreCase("provident fund charges")) {
					BigDecimal profidentCharge = BigDecimal.valueOf(12);
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getProvidentFundCharges() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getProvidentFundCharges() != null) {
							profidentCharge = weRateAnalysisDTOOptional.get().getProvidentFundCharges();
						}
					}
					weRateAnalysisDTO.setProvidentFundCharges(profidentCharge);
					raDetailsDTO.setTotalProvidentFundCharges(
							(itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance()))
									.multiply(profidentCharge.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setProvidentFundReq(true);
				}

				// esi charges ------------------->
				if (param.getParamName().equalsIgnoreCase("esi charges")) {
					BigDecimal esiCharges = BigDecimal.ZERO;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getEsiCharges() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getEsiCharges() != null) {
							esiCharges = weRateAnalysisDTOOptional.get().getEsiCharges();
						}
					}
					weRateAnalysisDTO.setEsiCharges(esiCharges);
					raDetailsDTO
							.setTotalEsiCharges((itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance()))
									.multiply(esiCharges.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setEsiChargesReq(true);
				}

				// idc charges ------------------->
				if (param.getParamName().equalsIgnoreCase("idc charges")) {
					BigDecimal idcCharges = BigDecimal.valueOf(5);
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getIdcCharges() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getIdcCharges() != null) {
							idcCharges = weRateAnalysisDTOOptional.get().getIdcCharges();
						}
					}
					weRateAnalysisDTO.setIdcCharges(idcCharges);
					raDetailsDTO
							.setTotalIdcCharges((itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance()))
									.multiply(idcCharges.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setIdcChargesReq(true);
				}

				// watch ward cost ------------------->
				if (param.getParamName().equalsIgnoreCase("watch ward cost")) {
					BigDecimal watchWardCost = BigDecimal.ONE;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getWatchAndWardCost() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getWatchAndWardCost() != null) {
							watchWardCost = weRateAnalysisDTOOptional.get().getWatchAndWardCost();
						}
					}
					weRateAnalysisDTO.setWatchAndWardCost(watchWardCost);
					raDetailsDTO.setTotalWatchAndWardCost((itemDTO.getBaseRate().subtract(itemDTO.getLabourRate()))
							.multiply(watchWardCost.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setWatchAndWardReq(true);
				}

				// insurance cost ------------------->
				if (param.getParamName().equalsIgnoreCase("insurance cost")) {
					BigDecimal insuranceCost = BigDecimal.ONE;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getInsuranceCost() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getInsuranceCost() != null) {
							insuranceCost = weRateAnalysisDTOOptional.get().getInsuranceCost();
						}
					}
					weRateAnalysisDTO.setInsuranceCost(insuranceCost);
					raDetailsDTO.setTotalInsuranceCost((itemDTO.getBaseRate().subtract(itemDTO.getLabourRate()))
							.multiply(insuranceCost.divide(BigDecimal.valueOf(100))));
					raDetailsDTO.setInsuranceCostReq(true);
				}

				// statutory charges ------------------->
				if (param.getParamName().equalsIgnoreCase("statutory charges")) {
					BigDecimal statutory = BigDecimal.ZERO;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getStatutoryCharges() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getStatutoryCharges() != null) {
							statutory = weRateAnalysisDTOOptional.get().getStatutoryCharges();
						}
					}
					weRateAnalysisDTO.setStatutoryCharges(statutory);
					raDetailsDTO.setStatutoryChargesReq(true);
				}

				// compensation charges ------------------->
				if (param.getParamName().equalsIgnoreCase("compensation charges")) {
					BigDecimal compensation = BigDecimal.ZERO;
					if (weRateAnalysisDTOOptional.isPresent()) {
						if (weRateAnalysisDTOOptional.get().getCompensationCost() != BigDecimal.ZERO
								&& weRateAnalysisDTOOptional.get().getCompensationCost() != null) {
							compensation = weRateAnalysisDTOOptional.get().getCompensationCost();
						}
					}
					weRateAnalysisDTO.setCompensationCost(compensation);
					raDetailsDTO.setCompensationCostReq(true);
				}

				// labour charges ------------------->
				if (param.getParamName().equalsIgnoreCase("labour charges")) {
					raDetailsDTO.setLabourChargesReq(true);
				}

				// additional charges ----------------->
				if (param.getParamName().equalsIgnoreCase("additional charges")) {
					raDetailsDTO.setAdditionalChargesReq(true);
					Optional<WorkEstimateAdditionalChargesDTO> weAdditionalChargesDTOOptional = workEstimateAdditionalChargesService
							.findByWorkEstimateItemId(itemId);
					if (weAdditionalChargesDTOOptional.isPresent()) {
						raDetailsDTO.setAdditionalCharges(weAdditionalChargesDTOOptional.get());
					} else {
						WorkEstimateAdditionalChargesDTO weAdditionalChargesDTO = new WorkEstimateAdditionalChargesDTO();
						weAdditionalChargesDTO.setAdditionalChargesRate(BigDecimal.ZERO);
						weAdditionalChargesDTO.setWorkEstimateItemId(itemId);
						raDetailsDTO.setAdditionalCharges(weAdditionalChargesDTO);
					}
				}

				// additional lift rate ------------------>
				if (param.getParamName().equalsIgnoreCase("additional lift rate")) {
					raDetailsDTO.setAddlLiftChargesReq(true);
					List<WorkEstimateOtherAddnLiftChargesDTO> weAddnChargesDTOList = workEstimateOtherAddnLiftChargesService
							.findByWorkEstimateItemIdAndType(itemId, RaChargeType.LIFT_CHARGES);
					ResponseEntity<List<NotesMasterDTO>> notesMasterResponse = workItemControllerApiClient
							.getNotesMasterByItemAndChargeTypeUsingGET(itemDTO.getCatWorkSorItemId(),
									RaChargeType.LIFT_CHARGES.toString());
					List<NotesMasterDTO> notesMasterDTOList = notesMasterResponse.getBody();
					if (!Utility.isValidCollection(notesMasterDTOList)) {
						log.error("-------------------> Notes Master not present");
					}
					if (Utility.isValidCollection(notesMasterDTOList)) {
						notesMasterDTOList.forEach(noteDTO -> {
							long flag = 0;
							if (!weAddnChargesDTOList.isEmpty()) {
								flag = weAddnChargesDTOList.stream().map(addnCharge -> {
									if (addnCharge.getNotesMasterId() == noteDTO.getId()) {
										return true;
									}
									return null;
								}).filter(Objects::nonNull).count();
							}
							if (flag == 0) {
								WorkEstimateOtherAddnLiftChargesDTO weAddnChargesDTO = new WorkEstimateOtherAddnLiftChargesDTO();
								weAddnChargesDTO.setNotesMasterId(noteDTO.getId());
								weAddnChargesDTO.setAddnCharges(noteDTO.getRate());
								weAddnChargesDTO.setSelected(null);
								weAddnChargesDTO.setWorkEstimateItemId(itemId);
								weAddnChargesDTO.setType(RaChargeType.LIFT_CHARGES);
								weAddnChargesDTOList.add(weAddnChargesDTO);
							}
						});
					}
					raDetailsDTO.setAddnLiftChargesList(weAddnChargesDTOList);
					BigDecimal addnLiftChargesTotal = weAddnChargesDTOList.stream().filter(a -> a.getSelected() != null)
							.filter(a -> a.getSelected()).filter(a -> a.getType() == RaChargeType.LIFT_CHARGES)
							.map(a -> a.getAddnCharges()).reduce(BigDecimal.ZERO, BigDecimal::add);
					raDetailsDTO.setAddnLiftChargesTotal(addnLiftChargesTotal);
				}

				// other charges -------------->
				if (param.getParamName().equalsIgnoreCase("other charges")) {
					raDetailsDTO.setOtherChargesReq(true);
					List<WorkEstimateOtherAddnLiftChargesDTO> weAddnChargesDTOList = workEstimateOtherAddnLiftChargesService
							.findByWorkEstimateItemIdAndType(itemId, RaChargeType.OTHER_CHARGES);
					ResponseEntity<List<NotesMasterDTO>> notesMasterResponse = workItemControllerApiClient
							.getNotesMasterByItemAndChargeTypeUsingGET(itemDTO.getCatWorkSorItemId(),
									RaChargeType.OTHER_CHARGES.toString());
					List<NotesMasterDTO> notesMasterDTOList = notesMasterResponse.getBody();
					if (!Utility.isValidCollection(notesMasterDTOList)) {
						log.error("-------------------> Notes Master not present");
					}
					if (Utility.isValidCollection(notesMasterDTOList)) {
						notesMasterDTOList.forEach(noteDTO -> {
							long flag = 0;
							if (!weAddnChargesDTOList.isEmpty()) {
								flag = weAddnChargesDTOList.stream().map(addnCharge -> {
									if (addnCharge.getNotesMasterId() == noteDTO.getId()) {
										return true;
									}
									return null;
								}).filter(Objects::nonNull).count();
							}
							if (flag == 0) {
								WorkEstimateOtherAddnLiftChargesDTO weAddnChargesDTO = new WorkEstimateOtherAddnLiftChargesDTO();
								weAddnChargesDTO.setNotesMasterId(noteDTO.getId());
								if (noteDTO.getPercentageRate() != null) {
									weAddnChargesDTO.setAddnCharges(itemDTO.getBaseRate()
											.multiply(noteDTO.getPercentageRate().divide(BigDecimal.valueOf(100))));
								} else {
									weAddnChargesDTO.setAddnCharges(noteDTO.getRate());
								}
								weAddnChargesDTO.setSelected(null);
								weAddnChargesDTO.setWorkEstimateItemId(itemId);
								weAddnChargesDTO.setType(RaChargeType.OTHER_CHARGES);
								weAddnChargesDTOList.add(weAddnChargesDTO);
							}
						});
					}
					raDetailsDTO.setAddnOtherChargesList(weAddnChargesDTOList);
					BigDecimal addnAddnOtherChargesTotal = weAddnChargesDTOList.stream()
							.filter(a -> a.getSelected() != null).filter(a -> a.getSelected())
							.filter(a -> a.getType() == RaChargeType.OTHER_CHARGES).map(a -> a.getAddnCharges())
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					raDetailsDTO.setAddnOtherChargesTotal(addnAddnOtherChargesTotal);
				}
			});
		}
		if (weRateAnalysisDTOOptional.isPresent()) {
			raDetailsDTO.setWorkEstimateRateAnalysis(weRateAnalysisDTOOptional.get());
		} else {
			raDetailsDTO.setWorkEstimateRateAnalysis(weRateAnalysisDTO);
		}
		RateAnalysisDetailsDTO rateAnalysisFinalDTO = calculateRateAnalysis(workEstimateId, subEstimateId, itemId,
				raDetailsDTO).getBody();
		return ResponseEntity.ok(rateAnalysisFinalDTO);
	}

	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/calculate")
	public ResponseEntity<RateAnalysisDetailsDTO> calculateRateAnalysis(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @RequestBody RateAnalysisDetailsDTO raDetailsDTO) throws Exception {

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("workEstimate.recordNotFound"),
						"WorkEstimate"));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"),
					"WorkEstimate", "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("subEstimate.recordNotFound"),
						"SubEstimate"));

		WorkEstimateItemDTO itemDTO = workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound"), "WorkEstimateItem"));

		if (itemDTO.getLabourRate() == null) { // TODO if required or not
			itemDTO.setLabourRate(BigDecimal.ZERO);
		}

		WorkTypeDTO workTypeDTO = workTypeService.findByIdAndActiveYnTrue(workEstimateDTO.getWorkTypeId()).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("entity.notFound"), "workType"));
		WorkType workTypeEnum = WorkType.valueOf(workTypeDTO.getValueType());

		ResponseEntity<WorkItemDTO> workItemResponse = workItemControllerApiClient
				.getWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
		WorkItemDTO workItemDTO = workItemResponse.getBody();

		ResponseEntity<SorCategoryDTO> sorCategoryResponse = sorCategoryControllerApiClient
				.getSorCategoryUsingGET(workItemDTO.getSorCategoryId());
		SorCategoryDTO sorCategoryDTO = sorCategoryResponse.getBody();

		RaFormulaDTO raFormulaDTO = raFormulaService
				.findAllByDeptIdAndWorkType(sorCategoryDTO.getCircleId(), workTypeEnum).orElseThrow(
						() -> new RecordNotFoundException("No RaFormula present for the given details!!", "RaFormula"));

		// Calculate material rate
		BigDecimal materialRate = calculateMaterialRate(itemDTO);

		// Calculate prevailing rates
		calculatePrevailingRates(itemDTO, raDetailsDTO);

		// TODO to confirm if additional lead is required or not as its there in prod
		// raformula data

		// Calculate lead charges
		if (raDetailsDTO.getLeadChargesReq()) {
			calculateLeadCharges(itemDTO, raDetailsDTO);
		}

		// Calculate lift charges
		if (raDetailsDTO.getLiftChargesReq()) {
			calculateLiftCharges(itemDTO, raDetailsDTO);
		}

		// Calculate loading unloading charges
		if (raDetailsDTO.getLoadingUnloadingChargesReq()) {
			calculateLoadUnloadCharges(itemDTO, raDetailsDTO);
		}

		// Calculate royalty charges
		if (raDetailsDTO.getRoyaltyChargesReq()) {
			calculateRoyaltyCharges(itemDTO, raDetailsDTO, raFormulaDTO);
		}

		// Calculate addnLift charges
		if (itemDTO.getFloorNumber() != null && raDetailsDTO.getAddlLiftChargesReq()) {
			calculateAddnLiftCharges(raDetailsDTO);
		}

		// Calculate other charges
		if (raDetailsDTO.getOtherChargesReq()) {
			calculateOtherCharges(raDetailsDTO);
		}

		// Calculate contractors profit
		if (raDetailsDTO.getContractorProfitReq()) {
			raDetailsDTO.setContractorProfitForDiffInMaterial(
					raDetailsDTO.getPrevailingRatesTotal().multiply(raDetailsDTO.getWorkEstimateRateAnalysis()
							.getContractorProfitPercentage().divide(BigDecimal.valueOf(100))));
		}

		// Calculate overhead charges
		if (raDetailsDTO.getOverheadChargesReq()) {
			raDetailsDTO.setOverheadAmount(raDetailsDTO.getPrevailingRatesTotal().multiply(raDetailsDTO
					.getWorkEstimateRateAnalysis().getOverheadPercentage().divide(BigDecimal.valueOf(100))));
		}

		// Calculate locality allowance
		if (raDetailsDTO.getLocalityAllowanceReq()) {
			raDetailsDTO.setTotalLocalityAllowance(itemDTO.getLabourRate().multiply(
					raDetailsDTO.getWorkEstimateRateAnalysis().getLocalityAllowance().divide(BigDecimal.valueOf(100))));
		}

		// Calculate employee cost
		if (raDetailsDTO.getEmployeeCostReq()) {
			raDetailsDTO.setTotalEmployeeCost(
					(itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance())).multiply(raDetailsDTO
							.getWorkEstimateRateAnalysis().getEmployeesCost().divide(BigDecimal.valueOf(100))));
		}

		// Calculate contingencies
		if (raDetailsDTO.getContingenciesReq()) {
			raDetailsDTO.setTotalContingencies(itemDTO.getBaseRate().multiply(
					raDetailsDTO.getWorkEstimateRateAnalysis().getContingencies().divide(BigDecimal.valueOf(100))));
		}

		// Calculate transportation cost
		if (raDetailsDTO.getTransportationCostReq()) {
			raDetailsDTO.setTotalTransportationCost(
					(itemDTO.getBaseRate().subtract(itemDTO.getLabourRate())).multiply(raDetailsDTO
							.getWorkEstimateRateAnalysis().getTransportationCost().divide(BigDecimal.valueOf(100))));
		}

		// Calculate service tax
		if (raDetailsDTO.getServiceTaxReq()) {
			raDetailsDTO.setTotalServiceTax(
					(itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance())).multiply(raDetailsDTO
							.getWorkEstimateRateAnalysis().getServiceTax().divide(BigDecimal.valueOf(100))));
		}

		// Calculate provident fund charges
		if (raDetailsDTO.getProvidentFundReq()) {
			raDetailsDTO.setTotalProvidentFundCharges(
					(itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance())).multiply(raDetailsDTO
							.getWorkEstimateRateAnalysis().getProvidentFundCharges().divide(BigDecimal.valueOf(100))));
		}

		// Calculate esi charges
		if (raDetailsDTO.getEsiChargesReq()) {
			raDetailsDTO.setTotalEsiCharges(
					(itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance())).multiply(raDetailsDTO
							.getWorkEstimateRateAnalysis().getEsiCharges().divide(BigDecimal.valueOf(100))));
		}

		// Calculate idc charges
		if (raDetailsDTO.getIdcChargesReq()) {
			raDetailsDTO.setTotalIdcCharges(
					(itemDTO.getLabourRate().add(raDetailsDTO.getTotalLocalityAllowance())).multiply(raDetailsDTO
							.getWorkEstimateRateAnalysis().getIdcCharges().divide(BigDecimal.valueOf(100))));
		}

		// Calculate watch and ward
		if (raDetailsDTO.getWatchAndWardReq()) {
			raDetailsDTO.setTotalWatchAndWardCost((itemDTO.getBaseRate().subtract(itemDTO.getLabourRate())).multiply(
					raDetailsDTO.getWorkEstimateRateAnalysis().getWatchAndWardCost().divide(BigDecimal.valueOf(100))));
		}

		// Calculate insurance cost
		if (raDetailsDTO.getInsuranceCostReq()) {
			raDetailsDTO.setTotalInsuranceCost((itemDTO.getBaseRate().subtract(itemDTO.getLabourRate())).multiply(
					raDetailsDTO.getWorkEstimateRateAnalysis().getInsuranceCost().divide(BigDecimal.valueOf(100))));
		}

		// Calculate area weightage
		if (raDetailsDTO.getAreaWeightageReq()) {
			calculateAreaWeightage(itemDTO, raDetailsDTO, raFormulaDTO, materialRate);
		}

		// Calculate total rate
		BigDecimal totalRate = calculateTotalRate(itemDTO, raDetailsDTO, raFormulaDTO);
		raDetailsDTO.setGrandTotal(totalRate.setScale(4, RoundingMode.HALF_EVEN));

		// Calculate finalRate based on tax
		if (raDetailsDTO.getTaxReq()
				&& raDetailsDTO.getWorkEstimateRateAnalysis().getTaxPercentage() != BigDecimal.ZERO) {
			raDetailsDTO.setTaxAmount((raDetailsDTO.getGrandTotal().multiply(
					raDetailsDTO.getWorkEstimateRateAnalysis().getTaxPercentage().divide(BigDecimal.valueOf(100))))
							.setScale(4, RoundingMode.HALF_EVEN));
			raDetailsDTO.getWorkEstimateRateAnalysis()
					.setNetRate(raDetailsDTO.getGrandTotal().add(raDetailsDTO.getTaxAmount()));
		} else {
			raDetailsDTO.getWorkEstimateRateAnalysis().setNetRate(raDetailsDTO.getGrandTotal());
		}
		return ResponseEntity.ok(raDetailsDTO);
	}

	private BigDecimal calculateMaterialRate(WorkEstimateItemDTO itemDTO) {
		List<MaterialMasterDetailsDTO> materialMasterDetailsList = null;
		try {
			ResponseEntity<List<MaterialMasterDetailsDTO>> materialMasterDetailsResponse = materialMasterControllerApiClient
					.getMaterialDetailsByWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
			materialMasterDetailsList = materialMasterDetailsResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				log.error("--------------> Materials not present for the WorkItem!!");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}
		BigDecimal materialRate = materialMasterDetailsList.stream().filter(m -> m.getQuantity() != null)
				.map(m -> m.getQuantity().multiply(m.getRate())).reduce(BigDecimal.ZERO, BigDecimal::add);
		return materialRate;
	}

	private void calculatePrevailingRates(WorkEstimateItemDTO itemDTO, RateAnalysisDetailsDTO raDetailsDTO) {
		List<MaterialMasterDetailsDTO> materialMasterDetailsList = null;
		try {
			ResponseEntity<List<MaterialMasterDetailsDTO>> materialMasterDetailsResponse = materialMasterControllerApiClient
					.getMaterialDetailsByWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
			materialMasterDetailsList = materialMasterDetailsResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				log.error("--------------> Materials not present for the WorkItem!!");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}
		materialMasterDetailsList.forEach(material -> {
			raDetailsDTO.getPrevailingRatesList().stream()
					.filter(m -> m.getMaterialMasterId() == material.getMaterialId()).forEach(marketRate -> {
						marketRate.setDifference(marketRate.getPrevailingMarketRate().subtract(marketRate.getRate()));
						marketRate.setTotal(marketRate.getQuantity().multiply(marketRate.getDifference()));
					});
		});
		BigDecimal prevailingRatesTotal = raDetailsDTO.getPrevailingRatesList().stream().map(m -> m.getTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		raDetailsDTO.setPrevailingRatesTotal(prevailingRatesTotal);
	}

	private void calculateAreaWeightage(WorkEstimateItemDTO itemDTO, RateAnalysisDetailsDTO raDetailsDTO,
			RaFormulaDTO raFormulaDTO, BigDecimal materialRate) {
		BigDecimal areaWeightageValue = BigDecimal.ZERO;
		String awFormula = raFormulaDTO.getAwFormula();
		if (raDetailsDTO.getAreaWeightageReq()
				&& raDetailsDTO.getWorkEstimateRateAnalysis().getAreaWeightageMasterId() != null) {
			AreaWeightageMasterDTO areaWeightageDTO = null;
			try {
				ResponseEntity<AreaWeightageMasterDTO> areaWeightageMastersResponse = areaWeightageMasterControllerApiClient
						.getAreaWeightageMasterUsingGET(
								raDetailsDTO.getWorkEstimateRateAnalysis().getAreaWeightageMasterId());
				areaWeightageDTO = areaWeightageMastersResponse.getBody();
				raDetailsDTO.getWorkEstimateRateAnalysis().setAreaWeightageMasterId(areaWeightageDTO.getId());
				raDetailsDTO.getWorkEstimateRateAnalysis()
						.setAreaWeightagePercentage(areaWeightageDTO.getWeightageValue());
			} catch (FeignClientException e) {
				// TODO to clarify
				if (e.status() == 404) {
					raDetailsDTO.getWorkEstimateRateAnalysis().setAreaWeightagePercentage(BigDecimal.ZERO);
					throw new RecordNotFoundException("Area Weightage not found for the given id!!",
							"AreaWeightageMaster");
				} else {
					raDetailsDTO.getWorkEstimateRateAnalysis().setAreaWeightagePercentage(BigDecimal.ZERO);
					throw new InterServiceCommunicationException(e.getMessage());
				}
			}

			if (itemDTO.getLabourRate().compareTo(BigDecimal.ZERO) > 0) {
				areaWeightageValue = itemDTO.getLabourRate().multiply(areaWeightageDTO.getWeightageValue());
			} else if (StringUtils.hasText(awFormula)) {
				areaWeightageValue = calculateAreaWeightageExpression(awFormula.toLowerCase(), itemDTO.getBaseRate(),
						materialRate, raDetailsDTO);
			}
		}
		raDetailsDTO.setAreaWeightageValue(areaWeightageValue);
	}

	private BigDecimal calculateAreaWeightageExpression(String awFormula, BigDecimal basicRate, BigDecimal materialRate,
			RateAnalysisDetailsDTO raDetailsDTO) {
		if (awFormula.contains("basicrate")) {
			awFormula = awFormula.replaceAll("basicrate", basicRate != null ? basicRate.toString() : "0.0");
		}
		if (awFormula.contains("materialrate")) {
			awFormula = awFormula.replaceAll("materialrate", materialRate != null ? materialRate.toString() : "0.0");
		}
		if (awFormula.contains("differenceinmaterialcost")) {
			awFormula = awFormula.replaceAll("differenceinmaterialcost",
					raDetailsDTO.getPrevailingRatesTotal() != null ? raDetailsDTO.getPrevailingRatesTotal().toString()
							: "0.0");
		}
		// TODO to clarify if this will exist?
//		if(awFormula.contains("additionalleadcharges")) {
//			
//		}
		if (awFormula.contains("additionalliftcharges")) {
			awFormula = awFormula.replaceAll("additionalliftcharges",
					raDetailsDTO.getAddnLiftChargesTotal() != null ? raDetailsDTO.getAddnLiftChargesTotal().toString()
							: "0.0");
		}
		if (awFormula.contains("othercharges")) {
			awFormula = awFormula.replaceAll("othercharges",
					raDetailsDTO.getAddnOtherChargesTotal() != null ? raDetailsDTO.getAddnOtherChargesTotal().toString()
							: "0.0");
		}
		if (awFormula.contains("weightage")) {
			awFormula = awFormula.replaceAll("weightage",
					raDetailsDTO.getWorkEstimateRateAnalysis().getAreaWeightagePercentage() != null
							? raDetailsDTO.getWorkEstimateRateAnalysis().getAreaWeightagePercentage().toString()
							: "0.0");
		}
		BigDecimal result = getValueFromExpression(awFormula);
		return result;
	}

	private void calculateLeadCharges(WorkEstimateItemDTO itemDTO, RateAnalysisDetailsDTO raDetailsDTO) {
		List<MaterialMasterDetailsDTO> leadMaterialsList = null;
		try {
			ResponseEntity<List<MaterialMasterDetailsDTO>> leadOrLiftChargesForWorkItemResponse = materialMasterControllerApiClient
					.getLeadOrLiftChargesForWorkItemUsingGET(itemDTO.getCatWorkSorItemId(),
							RaChargeType.LEAD_CHARGE.toString());
			leadMaterialsList = leadOrLiftChargesForWorkItemResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				log.error("----------------> Lead Charge not present for the WorkItem!!");
			} else if (e.status() == 400) {
				throw new BadRequestAlertException("Invalid Charge Type Name!!", "ChargeTypeMaster",
						"InvalidChargeType");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}
		if (Utility.isValidCollection(raDetailsDTO.getLeadChargesList())) {
			leadMaterialsList.forEach(material -> {

				raDetailsDTO.getLeadChargesList().stream()
						.filter(l -> l.getMaterialMasterId() == material.getMaterialId())
						.filter(l -> l.getLeadInKm() != null || l.getLeadInM() != null).forEach(leadCharge -> {
							MaterialLeadDetailsDTO leadDetails = new MaterialLeadDetailsDTO();
							leadDetails.setItemId(itemDTO.getCatWorkSorItemId());
							leadDetails.setLeadInKm(leadCharge.getLeadInKm());
							leadDetails.setLeadInMtrs(leadCharge.getLeadInM());
							leadDetails.setInitialLeadRequiredYn(leadCharge.getInitialLeadRequiredYn());
							ResponseEntity<MaterialChargesDTO> leadChargesResponse = materialMasterControllerApiClient
									.getLeadChargesForMaterialUsingPOST(material.getMaterialId(), leadDetails);
							MaterialChargesDTO matChargesDTO = leadChargesResponse.getBody();
							leadCharge.setLeadCharges(matChargesDTO.getMaterialCharges());
							leadCharge.setTotal(matChargesDTO.getMaterialCharges().multiply(material.getQuantity()));
						});

			});

			BigDecimal leadChargesTotal = raDetailsDTO.getLeadChargesList().stream().map(l -> l.getTotal())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			raDetailsDTO.setLeadChargesTotal(leadChargesTotal);
		}
	}

	private void calculateLiftCharges(WorkEstimateItemDTO itemDTO, RateAnalysisDetailsDTO raDetailsDTO) {
		List<MaterialMasterDetailsDTO> liftMaterialsList = null;
		try {
			ResponseEntity<List<MaterialMasterDetailsDTO>> leadOrLiftChargesForWorkItemResponse = materialMasterControllerApiClient
					.getLeadOrLiftChargesForWorkItemUsingGET(itemDTO.getCatWorkSorItemId(),
							RaChargeType.LIFT_CHARGE.toString());
			liftMaterialsList = leadOrLiftChargesForWorkItemResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				log.error("------------------------> Lift Charge not present for the WorkItem!!");
			} else if (e.status() == 400) {
				throw new BadRequestAlertException("Invalid Charge Type Name!!", "ChargeTypeMaster",
						"InvalidChargeType");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}
		if (Utility.isValidCollection(raDetailsDTO.getLiftChargesList())) {
			liftMaterialsList.forEach(material -> {
				raDetailsDTO.getLiftChargesList().stream()
						.filter(l -> l.getMaterialMasterId() == material.getMaterialId())
						.filter(l -> l.getLiftDistance() != null).forEach(liftChargeDTO -> {
							ResponseEntity<MaterialChargesDTO> liftChargesResponse = materialMasterControllerApiClient
									.getLiftChargesForMaterialUsingGET(material.getMaterialId(),
											liftChargeDTO.getLiftDistance());
							MaterialChargesDTO materialChargesDTO = liftChargesResponse.getBody();
							liftChargeDTO.setLiftCharges(materialChargesDTO.getMaterialCharges());
							liftChargeDTO
									.setTotal(liftChargeDTO.getQuantity().multiply(liftChargeDTO.getLiftCharges()));
						});
			});

			BigDecimal liftChargesTotal = raDetailsDTO.getLiftChargesList().stream().map(l -> l.getTotal())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			raDetailsDTO.setLiftChargesTotal(liftChargesTotal);
		}
	}

	private void calculateLoadUnloadCharges(WorkEstimateItemDTO itemDTO, RateAnalysisDetailsDTO raDetailsDTO) {
		List<MaterialMasterLoadUnloadDetailsDTO> materialMasterLdUdDetailsDTOList = null;
		try {
			ResponseEntity<List<MaterialMasterLoadUnloadDetailsDTO>> loadUnloadResponse = materialMasterControllerApiClient
					.getLoadUnloadforMaterialDetailsByWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
			materialMasterLdUdDetailsDTOList = loadUnloadResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				log.error("--------------------------> LoadUnload Charge not present for the WorkItem!!");
			} else {
				throw new InterServiceCommunicationException(e.getMessage());
			}
		}
		if (Utility.isValidCollection(raDetailsDTO.getLoadUnloadChargesList())) {
			materialMasterLdUdDetailsDTOList.forEach(ldUdDetails -> {
				raDetailsDTO.getLoadUnloadChargesList().stream()
						.filter(lu -> lu.getMaterialMasterId() == ldUdDetails.getMaterialId())
						.filter(lu -> lu.getSelectedLoadCharges() || lu.getSelectedUnloadCharges())
						.forEach(weLdUdChargesDTO -> {
							BigDecimal ldUdTotalCharge = BigDecimal.ZERO;
							if (weLdUdChargesDTO.getSelectedLoadCharges()) {
								ldUdTotalCharge = ldUdTotalCharge.add(weLdUdChargesDTO.getLoadingCharges());
							}
							if (weLdUdChargesDTO.getSelectedUnloadCharges()) {
								ldUdTotalCharge = ldUdTotalCharge.add(weLdUdChargesDTO.getUnloadingCharges());
							}
							weLdUdChargesDTO.setTotal(ldUdTotalCharge.multiply(weLdUdChargesDTO.getQuantity()));
						});
			});

			BigDecimal loadUnloadChargesTotal = raDetailsDTO.getLoadUnloadChargesList().stream().map(l -> l.getTotal())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			raDetailsDTO.setLoadUnloadChargesTotal(loadUnloadChargesTotal);
		}
	}

	private void calculateRoyaltyCharges(WorkEstimateItemDTO itemDTO, RateAnalysisDetailsDTO raDetailsDTO,
			RaFormulaDTO raFormulaDTO) throws Exception {
		List<MaterialMasterRoyaltyChargesDetailsDTO> materialMasterRoyaltyChargesDetailsList = null;
		try {
			ResponseEntity<List<MaterialMasterRoyaltyChargesDetailsDTO>> royaltyResponse = materialMasterControllerApiClient
					.getRoyaltyforMaterialDetailsByWorkItemUsingGET(itemDTO.getCatWorkSorItemId());
			materialMasterRoyaltyChargesDetailsList = royaltyResponse.getBody();
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				log.error("getRoyaltyforMaterialDetailsByWorkItemUsingGET - NotFound - {}",
						itemDTO.getCatWorkSorItemId());
			} else {
				throw new Exception(e.getMessage());
			}
		}

		if (Utility.isValidCollection(materialMasterRoyaltyChargesDetailsList)
				&& Utility.isValidCollection(raDetailsDTO.getRoyaltyChargesList())) {
			materialMasterRoyaltyChargesDetailsList.forEach(royaltyDetails -> {

				raDetailsDTO.getRoyaltyChargesList().stream()
						.filter(r -> r.getMaterialMasterId() == royaltyDetails.getMaterialId())
						.filter(r -> r.getPrevailingRoyaltyCharges() != r.getSrRoyaltyCharges())
						.forEach(weRoyaltyDTO -> {
							weRoyaltyDTO.setDifference(weRoyaltyDTO.getPrevailingRoyaltyCharges()
									.subtract(weRoyaltyDTO.getSrRoyaltyCharges()));
							weRoyaltyDTO.setTotal(calculateRoyaltyExpression(raFormulaDTO, weRoyaltyDTO));
						});
			});
			BigDecimal royaltyChargesTotal = raDetailsDTO.getRoyaltyChargesList().stream().map(r -> r.getTotal())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			raDetailsDTO.setRoyaltyChargesTotal(royaltyChargesTotal);
		}
	}

	private BigDecimal calculateRoyaltyExpression(RaFormulaDTO raFormulaDTO,
			WorkEstimateRoyaltyChargesDTO weRoyaltyDTO) {
		String royaltyFormula = raFormulaDTO.getRoyaltyFormula().toLowerCase();
		if (royaltyFormula.contains("prevailingroyaltyrate")) {
			royaltyFormula = royaltyFormula.replaceAll("prevailingroyaltyrate",
					weRoyaltyDTO.getPrevailingRoyaltyCharges() != null
							? weRoyaltyDTO.getPrevailingRoyaltyCharges().toString()
							: "0.0");
		}
		if (royaltyFormula.contains("quantity")) {
			royaltyFormula = royaltyFormula.replaceAll("quantity",
					weRoyaltyDTO.getQuantity() != null ? weRoyaltyDTO.getQuantity().toString() : "0.0");
		}
		if (royaltyFormula.contains("densityfactor")) {
			royaltyFormula = royaltyFormula.replaceAll("densityfactor",
					weRoyaltyDTO.getDensityFactor() != null ? weRoyaltyDTO.getDensityFactor().toString() : "0.0");
		}
		if (royaltyFormula.contains("srroyaltyrate")) {
			royaltyFormula = royaltyFormula.replaceAll("srroyaltyrate",
					weRoyaltyDTO.getSrRoyaltyCharges() != null ? weRoyaltyDTO.getSrRoyaltyCharges().toString() : "0.0");
		}
		BigDecimal result = getValueFromExpression(royaltyFormula);
		return result;
	}

	private void calculateAddnLiftCharges(RateAnalysisDetailsDTO raDetailsDTO) {
		BigDecimal addnLiftChargesTotal = raDetailsDTO.getAddnLiftChargesList().stream()
				.filter(a -> a.getSelected() != null).filter(a -> a.getSelected())
				.filter(a -> a.getType() == RaChargeType.LIFT_CHARGES).map(a -> a.getAddnCharges())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		raDetailsDTO.setAddnLiftChargesTotal(addnLiftChargesTotal);
	}

	private void calculateOtherCharges(RateAnalysisDetailsDTO raDetailsDTO) {
		if (Utility.isValidCollection(raDetailsDTO.getAddnLiftChargesList())) {
			BigDecimal addnAddnOtherChargesTotal = raDetailsDTO.getAddnLiftChargesList().stream()
					.filter(a -> a.getSelected() != null).filter(a -> a.getSelected())
					.filter(a -> a.getType() == RaChargeType.OTHER_CHARGES).map(a -> a.getAddnCharges())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			raDetailsDTO.setAddnOtherChargesTotal(addnAddnOtherChargesTotal);
		}
	}

	private BigDecimal calculateTotalRate(WorkEstimateItemDTO itemDTO, RateAnalysisDetailsDTO raDetailsDTO,
			RaFormulaDTO raFormulaDTO) {
		String formula = raFormulaDTO.getFormula().toLowerCase();
		if (formula.contains("areaweightage")) {
			formula = formula.replaceAll("areaweightage",
					raDetailsDTO.getAreaWeightageValue() != null ? raDetailsDTO.getAreaWeightageValue().toString()
							: "0.0");
		}

		if (formula.contains("basicrate")) {
			formula = formula.replaceAll("basicrate",
					itemDTO.getBaseRate() != null ? itemDTO.getBaseRate().toString() : "0.0");
		}

		if (formula.contains("differenceinmaterialcost")) {
			formula = formula.replaceAll("differenceinmaterialcost",
					raDetailsDTO.getPrevailingRatesTotal() != null ? raDetailsDTO.getPrevailingRatesTotal().toString()
							: "0.0");
		}

		// TODO to clarify if required or not
//		if (formula.contains("additionalleadcharges")) {
//			formula = formula.replaceAll("additionalleadcharges", );
//		}

		if (formula.contains("additionalliftcharges")) {
			formula = formula.replaceAll("additionalliftcharges",
					raDetailsDTO.getAddnLiftChargesTotal() != null ? raDetailsDTO.getAddnLiftChargesTotal().toString()
							: "0.0");
		}

		if (formula.contains("othercharges")) {
			formula = formula.replaceAll("othercharges",
					raDetailsDTO.getAddnOtherChargesTotal() != null ? raDetailsDTO.getAddnOtherChargesTotal().toString()
							: "0.0");
		}

		if (formula.contains("leadrate")) {
			formula = formula.replaceAll("leadrate",
					raDetailsDTO.getLeadChargesTotal() != null ? raDetailsDTO.getLeadChargesTotal().toString() : "0.0");
		}

		if (formula.contains("liftrate")) {
			formula = formula.replaceAll("liftrate",
					raDetailsDTO.getLiftChargesTotal() != null ? raDetailsDTO.getLiftChargesTotal().toString() : "0.0");
		}

		if (formula.contains("loadingunloadingcharges")) {
			formula = formula.replaceAll("loadingunloadingcharges",
					raDetailsDTO.getLoadUnloadChargesTotal() != null
							? raDetailsDTO.getLoadUnloadChargesTotal().toString()
							: "0.0");
		}

		if (formula.contains("royaltycharges")) {
			formula = formula.replaceAll("royaltycharges",
					raDetailsDTO.getRoyaltyChargesTotal() != null ? raDetailsDTO.getRoyaltyChargesTotal().toString()
							: "0.0");
		}

		if (formula.contains("localityallowance")) {
			formula = formula.replaceAll("localityallowance",
					raDetailsDTO.getTotalLocalityAllowance() != null
							? raDetailsDTO.getTotalLocalityAllowance().toString()
							: "0.0");
		}

		if (formula.contains("contractorsprofit")) {
			formula = formula.replaceAll("contractorsprofit",
					raDetailsDTO.getWorkEstimateRateAnalysis() != null
							? (raDetailsDTO.getWorkEstimateRateAnalysis().getContractorProfitPercentage() != null
									? raDetailsDTO.getWorkEstimateRateAnalysis().getContractorProfitPercentage()
											.toString()
									: "0.0")
							: "0.0");
		}

		if (formula.contains("overheadcharges")) {
			formula = formula
					.replaceAll("overheadcharges",
							raDetailsDTO.getWorkEstimateRateAnalysis() != null
									? (raDetailsDTO.getWorkEstimateRateAnalysis().getOverheadPercentage() != null
											? raDetailsDTO.getWorkEstimateRateAnalysis().getOverheadPercentage()
													.toString()
											: "0.0")
									: "0.0");
		}

		if (formula.contains("employeescost")) {
			formula = formula.replaceAll("employeescost",
					raDetailsDTO.getTotalEmployeeCost() != null ? raDetailsDTO.getTotalEmployeeCost().toString()
							: "0.0");
		}

		if (formula.contains("contingencies")) {
			formula = formula.replaceAll("contingencies",
					raDetailsDTO.getTotalContingencies() != null ? raDetailsDTO.getTotalContingencies().toString()
							: "0.0");
		}

		if (formula.contains("transportationcost")) {
			formula = formula.replaceAll("transportationcost",
					raDetailsDTO.getTotalTransportationCost() != null
							? raDetailsDTO.getTotalTransportationCost().toString()
							: "0.0");
		}

		if (formula.contains("servicecost")) {
			formula = formula.replaceAll("servicecost",
					raDetailsDTO.getTotalServiceTax() != null ? raDetailsDTO.getTotalServiceTax().toString() : "0.0");
		}

		if (formula.contains("idccharges")) {
			formula = formula.replaceAll("idccharges",
					raDetailsDTO.getTotalIdcCharges() != null ? raDetailsDTO.getTotalIdcCharges().toString() : "0.0");
		}

		if (formula.contains("esicharges")) {
			formula = formula.replaceAll("esicharges",
					raDetailsDTO.getTotalEsiCharges() != null ? raDetailsDTO.getTotalEsiCharges().toString() : "0.0");
		}

		if (formula.contains("watchwardcost")) {
			formula = formula.replaceAll("watchwardcost",
					raDetailsDTO.getTotalWatchAndWardCost() != null ? raDetailsDTO.getTotalWatchAndWardCost().toString()
							: "0.0");
		}

		if (formula.contains("insurancecost")) {
			formula = formula.replaceAll("insurancecost",
					raDetailsDTO.getTotalInsuranceCost() != null ? raDetailsDTO.getTotalInsuranceCost().toString()
							: "0.0");
		}

		if (formula.contains("providentfundcharges")) {
			formula = formula.replaceAll("providentfundcharges",
					raDetailsDTO.getTotalProvidentFundCharges() != null
							? raDetailsDTO.getTotalProvidentFundCharges().toString()
							: "0.0");
		}

		if (formula.contains("statutorycost")) {
			formula = formula
					.replaceAll("statutorycost",
							raDetailsDTO.getWorkEstimateRateAnalysis() != null
									? (raDetailsDTO.getWorkEstimateRateAnalysis().getStatutoryCharges() != null
											? raDetailsDTO.getWorkEstimateRateAnalysis().getStatutoryCharges()
													.toString()
											: "0.0")
									: "0.0");
		}

		if (formula.contains("compensationcharges")) {
			formula = formula
					.replaceAll("compensationcharges",
							raDetailsDTO.getWorkEstimateRateAnalysis() != null
									? (raDetailsDTO.getWorkEstimateRateAnalysis().getCompensationCost() != null
											? raDetailsDTO.getWorkEstimateRateAnalysis().getCompensationCost()
													.toString()
											: "0.0")
									: "0.0");
		}

		if (formula.contains("additionalcharges")) {
			formula = formula.replaceAll("additionalcharges", raDetailsDTO.getAdditionalCharges() != null
					? (raDetailsDTO.getAdditionalCharges().getAdditionalChargesRate().compareTo(BigDecimal.ZERO) > 0
							? raDetailsDTO.getAdditionalCharges().getAdditionalChargesRate().toString()
							: "0.0")
					: "0.0");
		}

		BigDecimal result = getValueFromExpression(formula);
		return result;
	}

	private BigDecimal getValueFromExpression(String expression) {
		JEP jep = new JEP();
		jep.parseExpression(expression);
		jep.addStandardConstants();
		jep.addStandardFunctions();
		DoubleStack stack = new DoubleStack();
		Double value = 0.0;
		try {
			value = jep.getValue(stack);
		} catch (Exception e) {
			throw new BadRequestAlertException("Invalid formula!!", "RaFormula", "invalidExpression");
		}
		return BigDecimal.valueOf(value);
	}
}
