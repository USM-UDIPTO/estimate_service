package com.dxc.eproc.estimate.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.EstimateServiceConstants;
import com.dxc.eproc.estimate.enumeration.RaChargeType;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
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
import com.dxc.eproc.estimate.service.dto.RateAnalysisDetailsDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.CustomClientInputValidators;
import com.dxc.eproc.exceptionhandling.CustomMethodArgumentNotValidException;
import com.dxc.eproc.exceptionhandling.CustomValidatorConstants;
import com.dxc.eproc.exceptionhandling.CustomValidatorVM;
import com.dxc.eproc.exceptionhandling.FieldErrorVM;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.Utility;

@RestController
@Transactional
@RequestMapping("/v1/api")
public class RateAnalysisController_V2 {

	private final Logger log = LoggerFactory.getLogger(RateAnalysisController_V2.class);

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";

	private static final String MATERIAL_MASTER_ID = "materialMasterId";

	private static final String DIFFERENCE = "difference";

	private static final String PREVAILING_MARKET_RATE = "prevailingMarketRate";

	private static final String CAT_WORK_SOR_ITEM_ID = "catWorkSorItemId";

	private static final String INITIAL_LEAD_REQUIRED_YN = "initialLeadRequiredYn";

	private static final String SELECTED_LOAD_CHARGES = "selectedLoadCharges";

	private static final String LOADING_CHARGES = "loadingCharges";

	private static final String SELECTED_UNLOAD_CHARGES = "selectedUnloadCharges";

	private static final String UNLOADING_CHARGES = "unloadingCharges";

	private static final String SR_ROYALTY_CHARGES = "srRoyaltyCharges";

	private static final String PREVAILING_ROYALTY_CHARGES = "prevailingRoyaltyCharges";

	private static final String DENSITY_FACTOR = "densityFactor";

	private static final String LIFT_DISTANCE = "liftDistance";

	private static final String LIFT_CHARGES = "liftCharges";

	private static final String QUANTITY = "quantity";

	private static final String BASIC_RATE = "basicRate";

	private static final String NET_RATE = "netRate";

	private static final String RA_COMPLETED_YN = "raCompletedYn";

	private static final String NOTES_MASTER_ID = "notesMasterId";

	private static final String SELECTED = "selected";

	private static final String ADDN_CHARGES = "addnCharges";

	private static final String TYPE = "type";

	private static final String ADDITIONAL_CHARGES_DESC = "additionalChargesDesc";

	private static final String ADDITIONAL_CHARGES_RATE = "additionalChargesRate";

	private static final String LEAD_IN_M = "leadInM";

	private static final String LEAD_IN_KM = "leadInKm";

	private static final String CONTRACTOR_PROFIT_PERCENTAGE = "contractorProfitPercentage";

	private static final String OVERHEAD_PERCENTAGE = "overheadPercentage";

	private static final String TAX_PERCENTAGE = "taxPercentage";

	private static final String LOCALITY_ALLOWANCE = "localityAllowance";

	private static final String EMPLOYEES_COST = "employeesCost";

	private static final String CONTINGENCIES = "contingencies";

	private static final String TRANSPORTATION_COST = "transportationCost";

	private static final String SERVICE_TAX = "serviceTax";

	private static final String PROVIDENT_FUND_CHARGES = "providentFundCharges";

	private static final String ESI_CHARGES = "esiCharges";

	private static final String IDC_CHARGES = "idcCharges";

	private static final String WATCH_AND_WARD_COST = "watchAndWardCost";

	private static final String INSURANCE_COST = "insuranceCost";

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	@Autowired
	private WorkEstimateRateAnalysisService workEstimateRateAnalysisService;

	@Autowired
	private WorkEstimateMarketRateService workEstimateMarketRateService;

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
	private FrameworkComponent frameworkComponent;

	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/rate-analysis/save")
	public ResponseEntity<RateAnalysisDetailsDTO> saveRateAnalysis(@PathVariable("workEstimateId") Long workEstimateId,
			@PathVariable("subEstimateId") Long subEstimateId, @PathVariable("itemId") Long itemId,
			@Valid @RequestBody RateAnalysisDetailsDTO raDetailsDTO) throws Exception {
		log.info("REST request to save WorkEstimate : {}", raDetailsDTO);

		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();

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

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("workEstimateItem.recordNotFound"),
						"WorkEstimateItem"));

		// check RA completed or not
		WorkEstimateRateAnalysisDTO weRateAnalysisDTO = raDetailsDTO.getWorkEstimateRateAnalysis();
		if (weRateAnalysisDTO != null && !weRateAnalysisDTO.getRaCompletedYn()) {
			throw new BadRequestAlertException("If the final rate is correct, please select the I agree check box",
					"workEstimateRateAnalysis", "invalidRA");
		}

		// check final rate is greater than or equal to zero or not
		// if not send bad request 'Final rate can't be negative.'
		if (weRateAnalysisDTO.getNetRate() != null && weRateAnalysisDTO.getNetRate().compareTo(BigDecimal.ZERO) < 0) {
			throw new BadRequestAlertException("Final rate can't be negative.", "workEstimateRateAnalysis",
					"invalidFinalRate");
		}

		fieldErrorDTOList.addAll(validateRateAnalysisDetails(raDetailsDTO));
		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}

		// saving prevailing market rates
		if (Utility.isValidCollection(raDetailsDTO.getPrevailingRatesList())) {
			raDetailsDTO.getPrevailingRatesList().stream()
					.filter(pmr -> pmr.getDifference().compareTo(BigDecimal.ZERO) != 0).forEach(marketRate -> {
						if (marketRate.getId() != null) {
							workEstimateMarketRateService.findByWorkEstimateItemIdAndId(itemId, marketRate.getId())
									.orElseThrow(() -> new RecordNotFoundException(
											frameworkComponent.resolveI18n("workEstimateMarketRate.recordNotFound"),
											"WorkEstimateMarketRate"));
						}
						try {
							workEstimateMarketRateService.save(marketRate);
						} catch (Exception ex) {
							log.info("prevailing market rates: exception occured " + ex.getMessage());
							throw new BadRequestAlertException("Error while saving!!", "WorkEstimateMarketRate",
									"InvalidPrevailingRate");
						}
					});
		}

		// saving lead charges
		if (Utility.isValidCollection(raDetailsDTO.getLeadChargesList())) {
			raDetailsDTO.getLeadChargesList().stream().filter(lc -> lc.getLeadCharges().compareTo(BigDecimal.ZERO) > 0)
					.forEach(leadCharge -> {
						if (!StringUtils.hasText(leadCharge.getQuarry())) {
							throw new BadRequestAlertException("Quarry cannot be empty", "WorkEstimateLeadCharges",
									"invalidQuarry");
						}
						if (leadCharge.getId() != null) {
							workEstimateLeadChargesService.findByWorkEstimateItemIdAndId(itemId, leadCharge.getId())
									.orElseThrow(() -> new RecordNotFoundException(
											frameworkComponent.resolveI18n("workEstimateLeadCharges.invalidId"),
											"WorkEstimateLeadCharges"));
						}
						try {
							workEstimateLeadChargesService.save(leadCharge);
						} catch (Exception ex) {
							log.info("lead charges: exception occured " + ex.getMessage());
							throw new BadRequestAlertException("Error while saving!!", "WorkEstimateLeadCharges",
									"InvalidLeadCharges");
						}
					});
		}

		// saving loading and unloading charges
		if (Utility.isValidCollection(raDetailsDTO.getLoadUnloadChargesList())) {
			raDetailsDTO.getLoadUnloadChargesList().stream()
					.filter(lu -> lu.getSelectedLoadCharges() || lu.getSelectedUnloadCharges())
					.forEach(loadUnloadCharge -> {
						if (loadUnloadCharge.getId() != null) {
							workEstimateLoadUnloadChargesService
									.findByWorkEstimateItemIdAndId(itemId, loadUnloadCharge.getId())
									.orElseThrow(() -> new RecordNotFoundException(
											frameworkComponent.resolveI18n("workEstimateLoadUnloadCharges.invalidId"),
											"WorkEstimateLoadUnloadCharges"));
						}
						try {
							workEstimateLoadUnloadChargesService.save(loadUnloadCharge);
						} catch (Exception ex) {
							log.info("loading and unloading charges: exception occured " + ex.getMessage());
							throw new BadRequestAlertException("Error while saving!!", "WorkEstimateLoadUnloadCharges",
									"InvalidLodadUnloadCharges");
						}
					});
		}

		// saving royalty charges
		if (Utility.isValidCollection(raDetailsDTO.getRoyaltyChargesList())) {
			raDetailsDTO.getRoyaltyChargesList().stream()
					.filter(rc -> rc.getDifference().compareTo(BigDecimal.ZERO) != 0).forEach(royaltyCharge -> {
						if (royaltyCharge.getId() != null) {
							workEstimateRoyaltyChargesService
									.findByWorkEstimateItemIdAndId(itemId, royaltyCharge.getId())
									.orElseThrow(() -> new RecordNotFoundException(
											frameworkComponent.resolveI18n("workEstimateRoyaltyCharges.invalidId"),
											"WorkEstimateRoyaltyCharges"));
						}
						try {
							workEstimateRoyaltyChargesService.save(royaltyCharge);
						} catch (Exception ex) {
							log.info("royalty charges: exception occured " + ex.getMessage());
							throw new BadRequestAlertException("Error while saving!!", "WorkEstimateRoyaltyCharges",
									"InvalidRoyaltyCharges");
						}
					});
		}

		// saving lift charges
		if (Utility.isValidCollection(raDetailsDTO.getLiftChargesList())) {
			raDetailsDTO.getLiftChargesList().stream().filter(lc -> lc.getLiftCharges().compareTo(BigDecimal.ZERO) > 0)
					.forEach(liftCharge -> {
						if (liftCharge.getId() != null) {
							workEstimateLiftChargesService.findByWorkEstimateItemIdAndId(itemId, liftCharge.getId())
									.orElseThrow(() -> new RecordNotFoundException(
											frameworkComponent.resolveI18n("workEstimateLiftCharges.invalidId"),
											"WorkEstimateLiftCharges"));
						}
						try {
							workEstimateLiftChargesService.save(liftCharge);
						} catch (Exception ex) {
							log.info("lift charges: exception occured " + ex.getMessage());
							throw new BadRequestAlertException("Error while saving!!", "WorkEstimateLiftCharges",
									"InvalidLiftCharges");
						}
					});
		}

		// saving work estimate rate analysis
		if (weRateAnalysisDTO != null) {
			if (weRateAnalysisDTO.getId() != null) {
				workEstimateRateAnalysisService.findByWorkEstimateItemIdAndId(itemId, weRateAnalysisDTO.getId())
						.orElseThrow(() -> new RecordNotFoundException(
								frameworkComponent.resolveI18n("workEstimateRateAnalysis.invalidId"),
								"WorkEstimateRateAnalysis"));
			}
			try {
				workEstimateRateAnalysisService.save(weRateAnalysisDTO);
			} catch (Exception ex) {
				log.info("work estimate rate analysis: exception occured " + ex.getMessage());
				throw new BadRequestAlertException("Error while saving!!", "WorkEstimateRateAnalysis",
						"InvalidRateAnalysis");
			}
		}

		// saving additional lift charges
		if (Utility.isValidCollection(raDetailsDTO.getAddnLiftChargesList())) {
			raDetailsDTO.getAddnLiftChargesList().stream().filter(a -> a.getSelected() != null)
					.filter(a -> a.getSelected()).forEach(weAddnLiftCharges -> {
						if (weAddnLiftCharges.getId() != null) {
							workEstimateOtherAddnLiftChargesService
									.findByWorkEstimateItemIdAndId(itemId, weAddnLiftCharges.getId())
									.orElseThrow(() -> new RecordNotFoundException(
											frameworkComponent
													.resolveI18n("workEstimateOtherAddnLiftCharges.invalidId"),
											"WorkEstimateOtherAddnLiftCharges"));
						}
						try {
							workEstimateOtherAddnLiftChargesService.save(weAddnLiftCharges);
						} catch (Exception ex) {
							log.info("additional lift charges: exception occured " + ex.getMessage());
							throw new BadRequestAlertException("Error while saving!!",
									"WorkEstimateOtherAddnLiftCharges", "InvalidOtherAddLiftCharges");
						}
					});
		}

		// saving other charges
		if (Utility.isValidCollection(raDetailsDTO.getAddnOtherChargesList())) {
			raDetailsDTO.getAddnOtherChargesList().stream().filter(a -> a.getSelected() != null)
					.filter(a -> a.getSelected()).forEach(weAddnOtherCharges -> {
						if (weAddnOtherCharges.getId() != null) {
							workEstimateOtherAddnLiftChargesService
									.findByWorkEstimateItemIdAndId(itemId, weAddnOtherCharges.getId())
									.orElseThrow(() -> new RecordNotFoundException(
											frameworkComponent
													.resolveI18n("workEstimateOtherAddnLiftCharges.invalidId"),
											"WorkEstimateOtherAddnLiftCharges"));
						}
						try {
							workEstimateOtherAddnLiftChargesService.save(weAddnOtherCharges);
						} catch (Exception ex) {
							log.info("other charges: exception occured " + ex.getMessage());
							throw new BadRequestAlertException("Error while saving!!",
									"workEstimateOtherAddnLiftCharges", "InvalidOtherCharges");
						}
					});
		}

		// saving additional charges
		if (raDetailsDTO.getAdditionalChargesReq() && raDetailsDTO.getAdditionalCharges() != null) {
			if (raDetailsDTO.getAdditionalCharges().getAdditionalChargesRate().compareTo(BigDecimal.ZERO) > 0) {
				if (raDetailsDTO.getAdditionalCharges().getId() != null) {
					workEstimateAdditionalChargesService
							.findByWorkEstimateItemIdAndId(itemId, raDetailsDTO.getAdditionalCharges().getId())
							.orElseThrow(() -> new RecordNotFoundException(
									frameworkComponent.resolveI18n("workEstimateAdditionalCharges.invalidId"),
									"WorkEstimateAdditionalCharges"));
				}
				try {
					workEstimateAdditionalChargesService.save(raDetailsDTO.getAdditionalCharges());
				} catch (Exception ex) {
					log.info("additional charges: exception occured " + ex.getMessage());
					throw new BadRequestAlertException("Error while saving!!", "WorkEstimateAdditionalCharges",
							"InvalidAdditionalCharges");
				}
			}
		}

		return ResponseEntity.created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/"
				+ subEstimateId + "/work-estimate-item/" + itemId + "/rate-analysis/save")).body(raDetailsDTO);
	}

	private List<FieldErrorVM> validateRateAnalysisDetails(RateAnalysisDetailsDTO raDetailsDTO) {
		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

		// validate prevailing market rates
		if (Utility.isValidCollection(raDetailsDTO.getPrevailingRatesList())) {
			raDetailsDTO.getPrevailingRatesList().stream()
					.filter(pmr -> pmr.getDifference().compareTo(BigDecimal.ZERO) != 0).forEach(marketRates -> {
						customValidatorVMList.add(new CustomValidatorVM(MATERIAL_MASTER_ID,
								marketRates.getMaterialMasterId(), Arrays.asList(CustomValidatorConstants.NOT_NULL),
								marketRates.getClass().getName()));

						customValidatorVMList.add(new CustomValidatorVM(DIFFERENCE, marketRates.getDifference(),
								Arrays.asList(CustomValidatorConstants.NOT_NULL), marketRates.getClass().getName()));

						customValidatorVMList.add(new CustomValidatorVM(PREVAILING_MARKET_RATE,
								marketRates.getPrevailingMarketRate(), Arrays.asList(CustomValidatorConstants.NOT_NULL),
								marketRates.getClass().getName()));

						if (marketRates.getPrevailingMarketRate() != null) {
							customValidatorVMList.add(new CustomValidatorVM(PREVAILING_MARKET_RATE,
									marketRates.getPrevailingMarketRate(),
									Arrays.asList(CustomValidatorConstants.PATTERN), marketRates.getClass().getName(),
									EstimateServiceConstants.RA_PREVAILING_RATE_MIN_DECIMAL_PATTERN,
									"invalidPrevailingRate",
									frameworkComponent.resolveI18n("rateAnalysis.prevailingRate.pattern")));
						}
					});
		}

		// validate lead charges
		if (raDetailsDTO.getLeadChargesReq()) {
			if (Utility.isValidCollection(raDetailsDTO.getLeadChargesList())) {
				raDetailsDTO.getLeadChargesList().stream()
						.filter(lc -> lc.getLeadCharges().compareTo(BigDecimal.ZERO) > 0).forEach(leadCharges -> {
							customValidatorVMList.add(new CustomValidatorVM(CAT_WORK_SOR_ITEM_ID,
									leadCharges.getCatWorkSorItemId(), Arrays.asList(CustomValidatorConstants.NOT_NULL),
									leadCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(MATERIAL_MASTER_ID,
									leadCharges.getMaterialMasterId(), Arrays.asList(CustomValidatorConstants.NOT_NULL),
									leadCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(INITIAL_LEAD_REQUIRED_YN,
									leadCharges.getInitialLeadRequiredYn(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									leadCharges.getClass().getName()));

							if (leadCharges.getLeadInM() != null) {
								customValidatorVMList.add(new CustomValidatorVM(LEAD_IN_M, leadCharges.getLeadInM(),
										Arrays.asList(CustomValidatorConstants.PATTERN),
										leadCharges.getClass().getName(),
										EstimateServiceConstants.RA_LEAD_MIN_DECIMAL_PATTERN, "invalidLeadInM",
										"rateAnalysis.lead.pattern"));
								// TODO to add in messages
								// frameworkComponent.resolveI18n("rateAnalysis.lead.pattern")));
							}

							if (leadCharges.getLeadInKm() != null) {
								customValidatorVMList.add(new CustomValidatorVM(LEAD_IN_KM, leadCharges.getLeadInKm(),
										Arrays.asList(CustomValidatorConstants.PATTERN),
										leadCharges.getClass().getName(),
										EstimateServiceConstants.RA_LEAD_MIN_DECIMAL_PATTERN, "invalidLeadInKm",
										"rateAnalysis.lead.pattern"));
								// TODO to add in messages
								// frameworkComponent.resolveI18n("rateAnalysis.lead.pattern")));
							}
						});
			}
		}

		// validate loading and unloading charges
		if (raDetailsDTO.getLoadingUnloadingChargesReq()) {
			if (Utility.isValidCollection(raDetailsDTO.getLoadUnloadChargesList())) {
				raDetailsDTO.getLoadUnloadChargesList().stream()
						.filter(lu -> lu.getSelectedLoadCharges() || lu.getSelectedUnloadCharges())
						.forEach(loadUnloadCharges -> {
							customValidatorVMList.add(
									new CustomValidatorVM(CAT_WORK_SOR_ITEM_ID, loadUnloadCharges.getCatWorkSorItemId(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											loadUnloadCharges.getClass().getName()));

							customValidatorVMList.add(
									new CustomValidatorVM(MATERIAL_MASTER_ID, loadUnloadCharges.getMaterialMasterId(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											loadUnloadCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(SELECTED_LOAD_CHARGES,
									loadUnloadCharges.getSelectedLoadCharges(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									loadUnloadCharges.getClass().getName()));

							customValidatorVMList
									.add(new CustomValidatorVM(LOADING_CHARGES, loadUnloadCharges.getLoadingCharges(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											loadUnloadCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(SELECTED_UNLOAD_CHARGES,
									loadUnloadCharges.getSelectedUnloadCharges(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									loadUnloadCharges.getClass().getName()));

							customValidatorVMList.add(
									new CustomValidatorVM(UNLOADING_CHARGES, loadUnloadCharges.getUnloadingCharges(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											loadUnloadCharges.getClass().getName()));
						});
			}
		}

		// validate royalty charges
		if (raDetailsDTO.getRoyaltyChargesReq()) {
			if (Utility.isValidCollection(raDetailsDTO.getRoyaltyChargesList())) {
				raDetailsDTO.getRoyaltyChargesList().stream()
						.filter(rc -> rc.getDifference().compareTo(BigDecimal.ZERO) != 0).forEach(royaltyCharges -> {
							customValidatorVMList.add(
									new CustomValidatorVM(CAT_WORK_SOR_ITEM_ID, royaltyCharges.getCatWorkSorItemId(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											royaltyCharges.getClass().getName()));

							customValidatorVMList
									.add(new CustomValidatorVM(MATERIAL_MASTER_ID, royaltyCharges.getMaterialMasterId(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											royaltyCharges.getClass().getName()));

							customValidatorVMList
									.add(new CustomValidatorVM(SR_ROYALTY_CHARGES, royaltyCharges.getSrRoyaltyCharges(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											royaltyCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(PREVAILING_ROYALTY_CHARGES,
									royaltyCharges.getPrevailingRoyaltyCharges(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									royaltyCharges.getClass().getName()));

							if (royaltyCharges.getPrevailingRoyaltyCharges() != null) {
								customValidatorVMList.add(new CustomValidatorVM(PREVAILING_ROYALTY_CHARGES,
										royaltyCharges.getPrevailingRoyaltyCharges(),
										Arrays.asList(CustomValidatorConstants.PATTERN),
										royaltyCharges.getClass().getName(),
										EstimateServiceConstants.RA_ROYALTY_MIN_DECIMAL_PATTERN,
										"invalidRoyaltyCharges",
										frameworkComponent.resolveI18n("rateAnalysis.royalty.pattern")));
							}

							customValidatorVMList.add(new CustomValidatorVM(DENSITY_FACTOR,
									royaltyCharges.getDensityFactor(), Arrays.asList(CustomValidatorConstants.NOT_NULL),
									royaltyCharges.getClass().getName()));
						});
			}
		}

		// validate lift charges
		if (raDetailsDTO.getLiftChargesReq()) {
			if (Utility.isValidCollection(raDetailsDTO.getLiftChargesList())) {
				raDetailsDTO.getLiftChargesList().stream()
						.filter(lc -> lc.getLiftCharges().compareTo(BigDecimal.ZERO) > 0).forEach(liftCharges -> {
							customValidatorVMList.add(new CustomValidatorVM(MATERIAL_MASTER_ID,
									liftCharges.getMaterialMasterId(), Arrays.asList(CustomValidatorConstants.NOT_NULL),
									liftCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(LIFT_DISTANCE,
									liftCharges.getLiftDistance(), Arrays.asList(CustomValidatorConstants.NOT_NULL),
									liftCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(LIFT_CHARGES, liftCharges.getLiftCharges(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									liftCharges.getClass().getName()));

							if (liftCharges.getLiftCharges() != null) {
								customValidatorVMList.add(new CustomValidatorVM(LIFT_CHARGES,
										liftCharges.getLiftCharges(), Arrays.asList(CustomValidatorConstants.PATTERN),
										liftCharges.getClass().getName(),
										EstimateServiceConstants.RA_LIFT_MIN_DECIMAL_PATTERN, "invalidLiftCharges",
										frameworkComponent.resolveI18n("rateAnalysis.lift.pattern")));
							}

							customValidatorVMList.add(new CustomValidatorVM(QUANTITY, liftCharges.getQuantity(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									liftCharges.getClass().getName()));
						});
			}
		}

		// validate work estimate rate analysis
		WorkEstimateRateAnalysisDTO weRateAnalysisDTO = raDetailsDTO.getWorkEstimateRateAnalysis();
		if (weRateAnalysisDTO != null) {
			customValidatorVMList.add(new CustomValidatorVM(BASIC_RATE, weRateAnalysisDTO.getBasicRate(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL), weRateAnalysisDTO.getClass().getName()));

			customValidatorVMList.add(new CustomValidatorVM(NET_RATE, weRateAnalysisDTO.getNetRate(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL), weRateAnalysisDTO.getClass().getName()));

			if (raDetailsDTO.getContingenciesReq()
					&& weRateAnalysisDTO.getContractorProfitPercentage().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList.add(new CustomValidatorVM(CONTRACTOR_PROFIT_PERCENTAGE,
						weRateAnalysisDTO.getContractorProfitPercentage(),
						Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
						EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidContractProfit",
						frameworkComponent.resolveI18n("rateAnalysis.contractorProfit.pattern")));
			}

			if (raDetailsDTO.getOverheadChargesReq()
					&& weRateAnalysisDTO.getOverheadPercentage().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList
						.add(new CustomValidatorVM(OVERHEAD_PERCENTAGE, weRateAnalysisDTO.getOverheadPercentage(),
								Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
								EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidOverheadPercentage",
								frameworkComponent.resolveI18n("rateAnalysis.overheadPercentage.pattern")));
			}

			if (raDetailsDTO.getTaxReq() && weRateAnalysisDTO.getTaxPercentage().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList.add(new CustomValidatorVM(TAX_PERCENTAGE, weRateAnalysisDTO.getTaxPercentage(),
						Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
						EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidTaxPercentage",
						frameworkComponent.resolveI18n("rateAnalysis.taxPercentage.pattern")));
			}

			if (raDetailsDTO.getLocalityAllowanceReq()
					&& weRateAnalysisDTO.getLocalityAllowance().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList
						.add(new CustomValidatorVM(LOCALITY_ALLOWANCE, weRateAnalysisDTO.getLocalityAllowance(),
								Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
								EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidLocalityAllowance",
								frameworkComponent.resolveI18n("rateAnalysis.localityAllowance.pattern")));
			}

			if (raDetailsDTO.getEmployeeCostReq()
					&& weRateAnalysisDTO.getEmployeesCost().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList.add(new CustomValidatorVM(EMPLOYEES_COST, weRateAnalysisDTO.getEmployeesCost(),
						Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
						EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidEmployeesCost",
						"rateAnalysis.employeesCost.pattern"));
				// TODO to add in messages properties
//				frameworkComponent.resolveI18n("rateAnalysis.employeesCost.pattern")
			}

			if (raDetailsDTO.getContingenciesReq()
					&& weRateAnalysisDTO.getContingencies().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList.add(new CustomValidatorVM(CONTINGENCIES, weRateAnalysisDTO.getContingencies(),
						Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
						EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidContingencies",
						frameworkComponent.resolveI18n("rateAnalysis.contingencies.pattern")));
			}

			if (raDetailsDTO.getTransportationCostReq()
					&& weRateAnalysisDTO.getTransportationCost().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList
						.add(new CustomValidatorVM(TRANSPORTATION_COST, weRateAnalysisDTO.getTransportationCost(),
								Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
								EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidTransportationCost",
								frameworkComponent.resolveI18n("rateAnalysis.transportationCost.pattern")));
			}

			if (raDetailsDTO.getServiceTaxReq() && weRateAnalysisDTO.getServiceTax().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList.add(new CustomValidatorVM(SERVICE_TAX, weRateAnalysisDTO.getServiceTax(),
						Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
						EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidServiceTax",
						frameworkComponent.resolveI18n("rateAnalysis.serviceTax.pattern")));
			}

			if (raDetailsDTO.getProvidentFundReq()
					&& weRateAnalysisDTO.getProvidentFundCharges().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList
						.add(new CustomValidatorVM(PROVIDENT_FUND_CHARGES, weRateAnalysisDTO.getProvidentFundCharges(),
								Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
								EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidProvidentFundCharges",
								frameworkComponent.resolveI18n("rateAnalysis.providentFundCharges.pattern")));
			}

			if (raDetailsDTO.getEsiChargesReq() && weRateAnalysisDTO.getEsiCharges().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList.add(new CustomValidatorVM(ESI_CHARGES, weRateAnalysisDTO.getEsiCharges(),
						Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
						EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidEsiCharges",
						frameworkComponent.resolveI18n("rateAnalysis.esiCharges.pattern")));
			}

			if (raDetailsDTO.getIdcChargesReq() && weRateAnalysisDTO.getIdcCharges().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList.add(new CustomValidatorVM(IDC_CHARGES, weRateAnalysisDTO.getIdcCharges(),
						Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
						EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidIdcCharges",
						frameworkComponent.resolveI18n("rateAnalysis.idcCharges.pattern")));
			}

			if (raDetailsDTO.getWatchAndWardReq()
					&& weRateAnalysisDTO.getWatchAndWardCost().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList
						.add(new CustomValidatorVM(WATCH_AND_WARD_COST, weRateAnalysisDTO.getWatchAndWardCost(),
								Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
								EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidWatchAndWardCost",
								frameworkComponent.resolveI18n("rateAnalysis.watchAndWardCost.pattern")));
			}

			if (raDetailsDTO.getInsuranceCostReq()
					&& weRateAnalysisDTO.getInsuranceCost().compareTo(BigDecimal.ZERO) > 0) {
				customValidatorVMList.add(new CustomValidatorVM(INSURANCE_COST, weRateAnalysisDTO.getInsuranceCost(),
						Arrays.asList(CustomValidatorConstants.PATTERN), weRateAnalysisDTO.getClass().getName(),
						EstimateServiceConstants.RA_MIN_DECIMAL_PATTERN, "invalidInsuranceCost",
						frameworkComponent.resolveI18n("rateAnalysis.insuranceCost.pattern")));
			}

			// TODO statuatory and compensation validation

			customValidatorVMList.add(new CustomValidatorVM(RA_COMPLETED_YN, weRateAnalysisDTO.getRaCompletedYn(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL), weRateAnalysisDTO.getClass().getName()));
		}

		// validate additional lift charges
		if (raDetailsDTO.getAddlLiftChargesReq()) {
			if (Utility.isValidCollection(raDetailsDTO.getAddnLiftChargesList())) {
				raDetailsDTO.getAddnLiftChargesList().stream().filter(a -> a.getSelected() != null)
						.filter(a -> a.getSelected()).filter(a -> a.getType() == RaChargeType.LIFT_CHARGES)
						.forEach(weAddnLiftCharges -> {
							customValidatorVMList
									.add(new CustomValidatorVM(NOTES_MASTER_ID, weAddnLiftCharges.getNotesMasterId(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											weAddnLiftCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(SELECTED, weAddnLiftCharges.getSelected(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									weAddnLiftCharges.getClass().getName()));

							customValidatorVMList
									.add(new CustomValidatorVM(ADDN_CHARGES, weAddnLiftCharges.getAddnCharges(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											weAddnLiftCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(TYPE, weAddnLiftCharges.getType(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									weAddnLiftCharges.getClass().getName()));
						});
			}
		}

		// validate other charges
		if (raDetailsDTO.getOtherChargesReq()) {
			if (Utility.isValidCollection(raDetailsDTO.getAddnOtherChargesList())) {
				raDetailsDTO.getAddnOtherChargesList().stream().filter(a -> a.getSelected() != null)
						.filter(a -> a.getSelected()).filter(a -> a.getType() == RaChargeType.OTHER_CHARGES)
						.forEach(weAddnOtherCharges -> {
							customValidatorVMList
									.add(new CustomValidatorVM(NOTES_MASTER_ID, weAddnOtherCharges.getNotesMasterId(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											weAddnOtherCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(SELECTED, weAddnOtherCharges.getSelected(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									weAddnOtherCharges.getClass().getName()));

							customValidatorVMList
									.add(new CustomValidatorVM(ADDN_CHARGES, weAddnOtherCharges.getAddnCharges(),
											Arrays.asList(CustomValidatorConstants.NOT_NULL),
											weAddnOtherCharges.getClass().getName()));

							customValidatorVMList.add(new CustomValidatorVM(TYPE, weAddnOtherCharges.getType(),
									Arrays.asList(CustomValidatorConstants.NOT_NULL),
									weAddnOtherCharges.getClass().getName()));
						});
			}
		}

		// validate additional charges
		if (raDetailsDTO.getAdditionalChargesReq()
				&& raDetailsDTO.getAdditionalCharges().getAdditionalChargesRate().compareTo(BigDecimal.ZERO) > 0) {
			customValidatorVMList.add(new CustomValidatorVM(ADDITIONAL_CHARGES_DESC,
					raDetailsDTO.getAdditionalCharges().getAdditionalChargesDesc(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL),
					raDetailsDTO.getAdditionalCharges().getClass().getName()));

			customValidatorVMList.add(new CustomValidatorVM(ADDITIONAL_CHARGES_RATE,
					raDetailsDTO.getAdditionalCharges().getAdditionalChargesRate(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL),
					raDetailsDTO.getAdditionalCharges().getClass().getName()));
		}

		List<FieldErrorVM> fieldErrorDTOList = customClientInputValidators.checkValidations(customValidatorVMList);
		fieldErrorVMList.addAll(fieldErrorDTOList);

		return fieldErrorVMList;
	}
}
