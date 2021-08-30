package com.dxc.eproc.estimate.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.cheffo.jeplite.JEP;
import org.cheffo.jeplite.ParseException;
import org.cheffo.jeplite.util.DoubleStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.EstimateServiceConstants;
import com.dxc.eproc.estimate.enumeration.LBDOperation;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.response.dto.WorkEstimateItemLBDResponseDTO;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateItemLBDService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.CustomClientInputValidators;
import com.dxc.eproc.exceptionhandling.CustomMethodArgumentNotValidException;
import com.dxc.eproc.exceptionhandling.CustomValidatorConstants;
import com.dxc.eproc.exceptionhandling.CustomValidatorVM;
import com.dxc.eproc.exceptionhandling.FieldErrorVM;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimateItemLBD}.
 */
@RestController
@Transactional
@RequestMapping("/v1/api")
public class WorkEstimateItemLBDController {

	/** The log. */
	private final static Logger log = LoggerFactory.getLogger(WorkEstimateItemLBDController.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workEstimateItemLBD";

	/** The Constant LBD_PARTICULARS. */
	private static final String LBD_PARTICULARS = "lbdParticulars";

	/** The Constant CALCULATED_YN. */
	private static final String CALCULATED_YN = "calculatedYn";

	/** The Constant QUANTITY. */
	private static final String LBD_QUANTITY = "lbdQuantity";

	/** The Constant LBD_LENGTH_FORMULA. */
	private static final String LBD_LENGTH_FORMULA = "lbdLengthFormula";

	/** The Constant LBD_BREADTH_FORMULA. */
	private static final String LBD_BREADTH_FORMULA = "lbdBreadthFormula";

	/** The Constant LBD_DEPTH_FORMULA. */
	private static final String LBD_DEPTH_FORMULA = "lbdDepthFormula";

	/** The Constant LBD_NOS. */
	private static final String LBD_NOS = "lbdNos";

	/** The Constant LBD_LENGTH. */
	private static final String LBD_LENGTH = "lbdLength";

	/** The Constant LBD_BREADTH. */
	private static final String LBD_BREADTH = "lbdBreadth";

	/** The Constant LBD_DEPTH. */
	private static final String LBD_DEPTH = "lbdDepth";

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";

	/** The framework component. */
	@Autowired
	private FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The work estimate item LBD service. */
	@Autowired
	private WorkEstimateItemLBDService workEstimateItemLBDService;

	/** The frame work component. */
	@Autowired
	private FrameworkComponent frameWorkComponent;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The work estimate item service. */
	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/**
	 * {@code POST  /work-estimate-item-lbds} : Create a new workEstimateItemLBD.
	 *
	 * @param workEstimateItemLBDDTO the workEstimateItemLBDDTO to create.
	 * @param workEstimateId         the work estimate id
	 * @param subEstimateId          the sub estimate id
	 * @param itemId                 the item id
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateItemLBDDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateItemLBD has already an
	 *         ID.
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd")
	public ResponseEntity<WorkEstimateItemLBDResponseDTO> createWorkEstimateItemLBD(
			@Valid @RequestBody WorkEstimateItemLBDDTO workEstimateItemLBDDTO,
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId) throws Exception {
		log.debug("REST request to save WorkEstimateItemLBD : {}", workEstimateItemLBDDTO);
		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();
		workEstimateItemLBDDTO.setId(null);
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalid WorkEstimateId"));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"workEstimateDto");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.subEstimate.invalidSubEstimateId") + "-" + subEstimateId,
						"invalid SubEstimateId"));
		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateDTO.getId(), itemId).orElseThrow(
						() -> new RecordNotFoundException(
								frameWorkComponent.resolveI18n(
										"validation.workEstimateItemLBD.SORItem.invalidSORItemId") + "-" + itemId,
								"invalid SORItemId"));

		workEstimateItemLBDDTO.setWorkEstimateItemId(workEstimateItemDTO.getId());

		fieldErrorDTOList.addAll(validateWorkEstimateItemLBD(workEstimateItemLBDDTO, null));
		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}

		validateItemLBD(workEstimateItemLBDDTO);
		BigDecimal itemLBDsTotal = workEstimateItemLBDService.getItemLBDsTotal(workEstimateItemDTO.getId());

		compareTotalLBDWithActualLBDAndFinalRate(itemLBDsTotal, workEstimateItemLBDDTO.getLbdTotal(),
				workEstimateItemDTO.getFinalRate(), workEstimateItemLBDDTO.getAdditionDeduction());
		workEstimateItemLBDDTO = workEstimateItemLBDService.save(workEstimateItemLBDDTO);
		workEstimateItemLBDService.recalculateAndUpdateItemWRTLBD(workEstimateDTO.getId(), subEstimateDTO.getId(),
				workEstimateItemDTO.getId());
		return ResponseEntity.created(new URI("/api/work-estimate-item-lbds/" + workEstimateItemLBDDTO.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						workEstimateItemLBDDTO.getId().toString()))
				.body(getLBDDetails(workEstimateId, subEstimateId, itemId));
	}

	/**
	 * Validate work estimate item LBD.
	 *
	 * @param workEstimateItemLBDDTO the work estimate item LBDDTO
	 * @param index                  the index
	 * @return the list
	 */
	private List<FieldErrorVM> validateWorkEstimateItemLBD(WorkEstimateItemLBDDTO workEstimateItemLBDDTO,
			String index) {
		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

//Particulars check
		// pattern check
		customValidatorVMList.add(new CustomValidatorVM(LBD_PARTICULARS, workEstimateItemLBDDTO.getLbdParticulars(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.PATTERN),
				workEstimateItemLBDDTO.getClass().getName(), EstimateServiceConstants.LBD_PARTICULARS_PATTERN, index,
				frameworkComponent.resolveI18n("workEstimateItemLBD.lbdParticulars.pattern")));

//Addition-Deduction check
		customValidatorVMList.add(new CustomValidatorVM(CALCULATED_YN, workEstimateItemLBDDTO.getCalculatedYn(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemLBDDTO.getClass().getName(), index));

		if (workEstimateItemLBDDTO.getCalculatedYn().equals(true)) {
			boolean isBreadth = true;
			boolean isDepth = true;
			boolean isLength = true;
			// Breadth
			if (workEstimateItemLBDDTO.getLbdBredth() != null || workEstimateItemLBDDTO.getLbdBredthFormula() != null) {
				if (workEstimateItemLBDDTO.getLbdBredthFormula() != null) {
					try {
						double calculatedBredth = calculateFromFormula(workEstimateItemLBDDTO.getLbdBredthFormula());
						if (calculatedBredth <= 0.0) {
							fieldErrorVMList.add(new FieldErrorVM(workEstimateItemLBDDTO.getClass().getName(),
									LBD_BREADTH_FORMULA, "",
									frameworkComponent.resolveI18n("workEstimateItemLBD.lbdBreadthFormula.zero")));
						}
						workEstimateItemLBDDTO.setLbdBredth(BigDecimal.valueOf(calculatedBredth));
					} catch (Exception e) {
						fieldErrorVMList.add(new FieldErrorVM(workEstimateItemLBDDTO.getClass().getName(),
								LBD_BREADTH_FORMULA, "",
								frameworkComponent.resolveI18n("workEstimateItemLBD.lbdBreadthFormula.inValid")));
					}
				} else {
					customValidatorVMList.add(new CustomValidatorVM(LBD_BREADTH, workEstimateItemLBDDTO.getLbdBredth(),
							Arrays.asList(CustomValidatorConstants.PATTERN),
							workEstimateItemLBDDTO.getClass().getName(),
							EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, index,
							frameworkComponent.resolveI18n("workEstimateItemLBD.lbdBreadth.pattern")));
				}
			} else {
				isBreadth = false;
			}
			// Depth
			if (workEstimateItemLBDDTO.getLbdDepth() != null || workEstimateItemLBDDTO.getLbdDepthFormula() != null) {
				if (workEstimateItemLBDDTO.getLbdDepthFormula() != null) {
					try {
						double calculatedDepth = calculateFromFormula(workEstimateItemLBDDTO.getLbdDepthFormula());
						if (calculatedDepth <= 0.0) {
							fieldErrorVMList.add(new FieldErrorVM(workEstimateItemLBDDTO.getClass().getName(),
									LBD_DEPTH_FORMULA, "",
									frameworkComponent.resolveI18n("workEstimateItemLBD.lbdDepthFormula.zero")));
						}
						workEstimateItemLBDDTO.setLbdDepth(BigDecimal.valueOf(calculatedDepth));
					} catch (Exception e) {
						fieldErrorVMList.add(
								new FieldErrorVM(workEstimateItemLBDDTO.getClass().getName(), LBD_DEPTH_FORMULA, "",
										frameworkComponent.resolveI18n("workEstimateItemLBD.lbdDepthFormula.inValid")));
					}
				} else {
					customValidatorVMList.add(new CustomValidatorVM(LBD_DEPTH, workEstimateItemLBDDTO.getLbdDepth(),
							Arrays.asList(CustomValidatorConstants.PATTERN),
							workEstimateItemLBDDTO.getClass().getName(),
							EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, index,
							frameworkComponent.resolveI18n("workEstimateItemLBD.lbdDepth.pattern")));
				}
			} else {
				isDepth = false;
			}
			// Length
			if (workEstimateItemLBDDTO.getLbdLength() != null || workEstimateItemLBDDTO.getLbdLengthFormula() != null) {
				if (workEstimateItemLBDDTO.getLbdLengthFormula() != null) {
					try {
						double calculatedLength = calculateFromFormula(workEstimateItemLBDDTO.getLbdLengthFormula());
						if (calculatedLength <= 0.0) {
							fieldErrorVMList.add(new FieldErrorVM(workEstimateItemLBDDTO.getClass().getName(),
									LBD_LENGTH_FORMULA, "",
									frameworkComponent.resolveI18n("workEstimateItemLBD.lbdLengthFormula.zero")));
						}
						workEstimateItemLBDDTO.setLbdLength(BigDecimal.valueOf(calculatedLength));
					} catch (Exception e) {
						fieldErrorVMList.add(new FieldErrorVM(workEstimateItemLBDDTO.getClass().getName(),
								LBD_LENGTH_FORMULA, "",
								frameworkComponent.resolveI18n("workEstimateItemLBD.lbdLengthFormula.inValid")));
					}
				} else {
					customValidatorVMList.add(new CustomValidatorVM(LBD_LENGTH, workEstimateItemLBDDTO.getLbdLength(),
							Arrays.asList(CustomValidatorConstants.PATTERN),
							workEstimateItemLBDDTO.getClass().getName(),
							EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, index,
							frameworkComponent.resolveI18n("workEstimateItemLBD.lbdLength.pattern")));
				}
			} else {
				isLength = false;
			}

			if (!isLength && !isBreadth && !isDepth) {
				fieldErrorVMList.add(new FieldErrorVM(workEstimateItemLBDDTO.getClass().getName(), "LBD_CALCULATED", "",
						frameworkComponent.resolveI18n("workEstimateItemLBD.lbdCalculated.null")));
			}
//Quantity check
		} else if (workEstimateItemLBDDTO.getCalculatedYn().equals(false)) {
			customValidatorVMList.add(new CustomValidatorVM(LBD_QUANTITY, workEstimateItemLBDDTO.getLbdQuantity(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.PATTERN),
					workEstimateItemLBDDTO.getClass().getName(), EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN,
					index, frameworkComponent.resolveI18n("workEstimateItemLBD.lbdQuantity.pattern")));

		}
//Nos check
		customValidatorVMList.add(new CustomValidatorVM(LBD_NOS, workEstimateItemLBDDTO.getLbdNos(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.PATTERN),
				workEstimateItemLBDDTO.getClass().getName(), EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, index,
				frameworkComponent.resolveI18n("workEstimateItemLBD.lbdNos.pattern")));

		List<FieldErrorVM> fieldErrorDTOList = customClientInputValidators.checkValidations(customValidatorVMList);
		fieldErrorVMList.addAll(fieldErrorDTOList);

		return fieldErrorVMList;
	}

	/**
	 * Calculate from formula.
	 *
	 * @param formula the formula
	 * @return the double
	 * @throws Exception the exception
	 */
	public static double calculateFromFormula(String formula) throws Exception {
		double answer = 0.0;
		if (formula != null && !formula.trim().isEmpty()) {
			JEP jep = new JEP();
			jep.parseExpression(formula.trim());
			jep.addStandardConstants();
			jep.addStandardFunctions();
			DoubleStack stack = new DoubleStack();
			try {
				answer = jep.getValue(stack);
			} catch (ParseException e) {
				log.error(e.getMessage());
				throw new Exception(e.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		/* validation for NaN or infinite cause by division by zero */
		if (Double.isNaN(answer) || Double.isInfinite(answer)) {
			log.error("Can't divide the value with zero.");
			return 0.0;
		}
		return answer;
	}

	/**
	 * Calculate LBD true.
	 *
	 * @param workEstimateItemLBDDTO the work estimate item LBDDTO
	 */
	private void validateItemLBD(WorkEstimateItemLBDDTO workEstimateItemLBDDTO) {

		if (workEstimateItemLBDDTO.getCalculatedYn().equals(false)) {

			workEstimateItemLBDDTO
					.setLbdTotal((workEstimateItemLBDDTO.getLbdNos().multiply(workEstimateItemLBDDTO.getLbdQuantity()))
							.setScale(4, RoundingMode.HALF_EVEN));

			workEstimateItemLBDDTO.setLbdLength(null);
			workEstimateItemLBDDTO.setLbdLengthFormula(null);
			workEstimateItemLBDDTO.setLbdBredth(null);
			workEstimateItemLBDDTO.setLbdBredthFormula(null);
			workEstimateItemLBDDTO.setLbdDepth(null);
			workEstimateItemLBDDTO.setLbdDepthFormula(null);
		} else {
			BigDecimal total = new BigDecimal(1);
			if (workEstimateItemLBDDTO.getLbdLength() != null) {
				total = total.multiply(workEstimateItemLBDDTO.getLbdLength().setScale(4, RoundingMode.HALF_UP))
						.setScale(4, RoundingMode.HALF_UP);
			} else {
				workEstimateItemLBDDTO.setLbdLength(BigDecimal.ZERO);
			}
			if (workEstimateItemLBDDTO.getLbdBredth() != null) {
				total = total.multiply(workEstimateItemLBDDTO.getLbdBredth().setScale(4, RoundingMode.HALF_UP))
						.setScale(4, RoundingMode.HALF_UP);
			} else {
				workEstimateItemLBDDTO.setLbdBredth(BigDecimal.ZERO);
			}
			if (workEstimateItemLBDDTO.getLbdDepth() != null) {
				total = total.multiply(workEstimateItemLBDDTO.getLbdDepth().setScale(4, RoundingMode.HALF_UP))
						.setScale(4, RoundingMode.HALF_UP);
			} else {
				workEstimateItemLBDDTO.setLbdDepth(BigDecimal.ZERO);
			}
			workEstimateItemLBDDTO.setLbdQuantity(total);
			workEstimateItemLBDDTO
					.setLbdTotal(total.multiply(workEstimateItemLBDDTO.getLbdNos().setScale(4, RoundingMode.HALF_UP))
							.setScale(4, RoundingMode.HALF_UP));
		}
	}

	/**
	 * Gets the LBD details.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param itemId         the item id
	 * @return the LBD details
	 */
	private WorkEstimateItemLBDResponseDTO getLBDDetails(Long workEstimateId, Long subEstimateId, Long itemId) {
		WorkEstimateItemLBDResponseDTO workEstimateItemLBDResponseDTO = new WorkEstimateItemLBDResponseDTO();
		workEstimateItemLBDResponseDTO.setWorkEstimateId(workEstimateId);
		workEstimateItemLBDResponseDTO.setSubEstimateId(subEstimateId);
		workEstimateItemLBDResponseDTO.setWorkEstimateItemId(workEstimateId);
		workEstimateItemLBDResponseDTO.setLbdTotal(workEstimateItemLBDService.getItemLBDsTotal(itemId));
		workEstimateItemLBDResponseDTO.setLbds(workEstimateItemLBDService.findAllByWorkEstimateItemId(itemId));

		return workEstimateItemLBDResponseDTO;
	}

	/**
	 * Compare total LBD with actual LBD and final rate.
	 *
	 * @param totalLBD     the total LBD
	 * @param actualLBD    the actual LBD
	 * @param finalRate    the final rate
	 * @param lBDOperation the l BD operation
	 */
	private void compareTotalLBDWithActualLBDAndFinalRate(BigDecimal totalLBD, BigDecimal actualLBD,
			BigDecimal finalRate, LBDOperation lBDOperation) {
		BigDecimal finalLBD = null;
		if (LBDOperation.DEDUCTION.equals(lBDOperation)) {
			if (!((totalLBD.subtract(actualLBD)).compareTo(BigDecimal.ZERO) > 0)) {
				throw new BadRequestAlertException(
						frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.LBDTotal.invalid"), ENTITY_NAME,
						"invalid LBDTotal");
			}
			finalLBD = totalLBD.subtract(actualLBD);
		} else {
			finalLBD = totalLBD.add(actualLBD);
		}

		// TODO - cache eproc_config
		if (finalLBD.compareTo(BigDecimal.valueOf(999999999999.9999)) > 0) {
			Object[] args = { totalLBD };
			throw new BadRequestAlertException(
					frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.LBDTotal.exceedsQuantity", args),
					ENTITY_NAME, "invalid LBDTotal");
		}
		// TODO - cache eproc_config
		if (finalRate != null) {
			if (finalLBD.multiply(finalRate).compareTo(BigDecimal.valueOf(999999999999.9999)) > 0) {
				Object[] args = { finalLBD, finalRate };
				throw new BadRequestAlertException(
						frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.LBDTotal.exceedsValue", args),
						ENTITY_NAME, "invalid LBDTotal");
			}
		}
	}

	/**
	 * {@code PUT  /work-estimate-item-lbds/:id} : Updates an existing
	 * workEstimateItemLBD.
	 *
	 * @param workEstimateItemLBDDTO the workEstimateItemLBDDTO to update.
	 * @param workEstimateId         the work estimate id
	 * @param subEstimateId          the sub estimate id
	 * @param itemId                 the item id
	 * @param id                     the id of the workEstimateItemLBDDTO to save.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateItemLBDDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateItemLBDDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateItemLBDDTO couldn't be updated.
	 * @throws Exception the exception
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}")
	public ResponseEntity<WorkEstimateItemLBDResponseDTO> updateWorkEstimateItemLBD(
			@Valid @RequestBody WorkEstimateItemLBDDTO workEstimateItemLBDDTO,
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id) throws Exception {
		log.debug("REST request to update WorkEstimateItemLBD : {}, {}", id, workEstimateItemLBDDTO);
		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalidWorkEstimateId"));
		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"workEstimateDto");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.subEstimate.invalidSubEstimateId") + "-" + subEstimateId,
						"invalid subEstimateId"));

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateId, itemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.subEstimateId.itemId.invalid")
								+ "-" + subEstimateId,
						itemId + "invalid ItemId"));

		WorkEstimateItemLBDDTO workEstimateItemLBDDBDTO = workEstimateItemLBDService
				.findByWorkEstimateItemIdAndId(workEstimateItemDTO.getId(), id).orElseThrow(
						() -> new RecordNotFoundException(
								frameWorkComponent.resolveI18n(
										"validation.workEstimateItemLBD.SORItem.invalidSORItemId") + "-" + id,
								"invalid ItemId"));

		workEstimateItemLBDDTO.setId(workEstimateItemLBDDBDTO.getId());
		workEstimateItemLBDDTO.setWorkEstimateItemId(workEstimateItemDTO.getId());

		fieldErrorDTOList.addAll(validateWorkEstimateItemLBD(workEstimateItemLBDDTO, null));
		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}

		validateItemLBD(workEstimateItemLBDDTO);
		BigDecimal itemLBDsTotal = workEstimateItemLBDService.getItemLBDsTotal(workEstimateItemDTO.getId());

		compareTotalLBDWithActualLBDAndFinalRate(itemLBDsTotal, workEstimateItemLBDDTO.getLbdTotal(),
				workEstimateItemDTO.getFinalRate(), workEstimateItemLBDDTO.getAdditionDeduction());
		workEstimateItemLBDDTO = workEstimateItemLBDService.save(workEstimateItemLBDDTO);
		workEstimateItemLBDService.recalculateAndUpdateItemWRTLBD(workEstimateDTO.getId(), subEstimateDTO.getId(),
				workEstimateItemDTO.getId());
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
						workEstimateItemLBDDTO.getId().toString()))
				.body(getLBDDetails(workEstimateId, subEstimateId, itemId));
	}

	/**
	 * {@code GET  /work-estimate-item-lbds} : get all the workEstimateItemLBDS.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param itemId         the item id
	 * @param pageable       the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateItemLBDS in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbds")
	public ResponseEntity<WorkEstimateItemLBDResponseDTO> getAllWorkEstimateItemLBDS(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, Pageable pageable) {
		log.debug("REST request to get a page of WorkEstimateItemLBDS");

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalid WorkEstimateId"));
		SubEstimateDTO subEstimateDTO = subEstimateService
				.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.subEstimate.invalidSubEstimateId") + "-" + subEstimateId,
						"invalid SubEstimateId"));

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateDTO.getId(), itemId).orElseThrow(
						() -> new RecordNotFoundException(
								frameWorkComponent.resolveI18n(
										"validation.workEstimateItemLBD.workItem.invalidworkItemId") + "-" + itemId,
								"invalid ItemId"));

		List<WorkEstimateItemLBDDTO> workEstimateItemLBDDTOList = workEstimateItemLBDService
				.findAllByWorkEstimateItemId(itemId);

		WorkEstimateItemLBDResponseDTO workEstimateItemLBDResponseDTO = new WorkEstimateItemLBDResponseDTO();
		workEstimateItemLBDResponseDTO.setWorkEstimateId(workEstimateId);
		workEstimateItemLBDResponseDTO.setSubEstimateId(subEstimateId);
		workEstimateItemLBDResponseDTO.setWorkEstimateItemId(workEstimateItemDTO.getId());
		workEstimateItemLBDResponseDTO
				.setLbdTotal(workEstimateItemLBDService.getItemLBDsTotal(workEstimateItemDTO.getId()));
		workEstimateItemLBDResponseDTO.setLbds(workEstimateItemLBDDTOList);
		return ResponseEntity.ok().body(workEstimateItemLBDResponseDTO);
	}

	/**
	 * {@code GET  /work-estimate-item-lbds/:id} : get the "id" workEstimateItemLBD.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param itemId         the item id
	 * @param id             the id of the workEstimateItemLBDDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateItemLBDDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}")
	public ResponseEntity<WorkEstimateItemLBDDTO> getWorkEstimateItemLBD(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id) {
		log.debug("REST request to get WorkEstimateItemLBD : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalid WorkEstimateId"));
		SubEstimateDTO subEstimateDTO = subEstimateService
				.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.subEstimate.invalidSubEstimateId") + "-" + subEstimateId,
						"invalid subEstimate"));

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateDTO.getId(), itemId).orElseThrow(
						() -> new RecordNotFoundException(
								frameWorkComponent.resolveI18n(
										"validation.workEstimateItemLBD.SORItem.invalidSORItemId") + "-" + itemId,
								"invalid  itemId"));
		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDService
				.findByWorkEstimateItemIdAndId(workEstimateItemDTO.getId(), id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(
								"validation.workEstimateItemLBD.SORItem.invalidEstimateItemLBDId") + "-" + id,
						"invalid id"));

		return ResponseUtil.wrapOrNotFound(Optional.of(workEstimateItemLBDDTO));
	}

	/**
	 * {@code DELETE  /work-estimate-item-lbds/:id} : delete the "id"
	 * workEstimateItemLBD.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param itemId         the item id
	 * @param id             the id of the workEstimateItemLBDDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/lbd/{id}")
	public ResponseEntity<WorkEstimateItemLBDResponseDTO> deleteWorkEstimateItemLBD(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id) {
		log.debug("REST request to delete WorkEstimateItemLBD : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalid WorkEstimateId"));
		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"workEstimateDto");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimateId.subEstimateId.invalid") + "-" + subEstimateId,
						"invalid subEstimateId"));

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
						() -> new RecordNotFoundException(
								frameWorkComponent.resolveI18n(
										"validation.workEstimateItemLBD.SORItem.invalidSORItemId") + "-" + itemId,
								"invalidSORItemId"));

		WorkEstimateItemLBDDTO workEstimateItemLBDDTO = workEstimateItemLBDService
				.findByWorkEstimateItemIdAndId(workEstimateItemDTO.getId(), id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(
								"validation.workEstimateItemLBD.SORItem.invalidEstimateItemLBDId") + "-" + id,
						"invalid id"));

		BigDecimal itemLBDsTotal = workEstimateItemLBDService
				.getItemLBDsTotal(workEstimateItemLBDDTO.getWorkEstimateItemId());
		if (workEstimateItemLBDDTO.getAdditionDeduction().equals(LBDOperation.ADDITION)) {
			if (workEstimateItemLBDService.findByWorkEstimateItemId(itemId, null).stream().count() > 1) {
				if (!((itemLBDsTotal.subtract(workEstimateItemLBDDTO.getLbdTotal())).compareTo(BigDecimal.ZERO) > 0)) {
					throw new BadRequestAlertException(
							frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.LBDTotal.invalid"),
							ENTITY_NAME, "invalid LBDTotal");
				}
			}
		}

		workEstimateItemLBDService.delete(id);
		workEstimateItemLBDService.recalculateAndUpdateItemWRTLBD(workEstimateDTO.getId(), subEstimateDTO.getId(),
				itemId);
		WorkEstimateItemLBDResponseDTO workEstimateItemLBDResponseDTO = new WorkEstimateItemLBDResponseDTO();
		workEstimateItemLBDResponseDTO.setWorkEstimateId(workEstimateId);
		workEstimateItemLBDResponseDTO.setSubEstimateId(subEstimateId);
		workEstimateItemLBDResponseDTO.setWorkEstimateItemId(workEstimateItemDTO.getId());
		workEstimateItemLBDResponseDTO
				.setLbdTotal(workEstimateItemLBDService.getItemLBDsTotal(workEstimateItemDTO.getId()));
		workEstimateItemLBDResponseDTO.setLbds(workEstimateItemLBDService.findAllByWorkEstimateItemId(itemId));
		return ResponseEntity.ok().body(workEstimateItemLBDResponseDTO);
	}

	/**
	 * Search item within sub estimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param itemId         the item id
	 * @param itemCode       the item code
	 * @return the response entity
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/item/{itemId}/search")
	public ResponseEntity<List<WorkEstimateItemDTO>> searchItemWithinSubEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "itemId", required = true) Long itemId,
			@RequestParam(name = "item-code", required = false) String itemCode) {

		log.debug("REST request to searchItemWithInSubEstimate : {}", workEstimateId, subEstimateId, itemCode);
		// Find By WorkEstimateId

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalid WorkEstimateId"));

		// find By workEstimateId and SubEstimateId.
		SubEstimateDTO subEstimateDTO = subEstimateService
				.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.subEstimate.invalidSubEstimateId") + "-" + subEstimateId,
						"invalid SubEstimateId"));
		List<WorkEstimateItemDTO> workEstimateItemDTOList = null;
		if (Utility.isValidString(itemCode)) {
			workEstimateItemDTOList = workEstimateItemService.findBySubEstimateIdAndItemCode(subEstimateDTO.getId(),
					itemCode);
		} else {
			List<WorkEstimateItemDTO> allWorkEstimateItemList = workEstimateItemService.findAllItems(subEstimateId);
			workEstimateItemDTOList = allWorkEstimateItemList.stream()
					.filter(workEstimateItem -> workEstimateItem.getId() != itemId).collect(Collectors.toList());
		}
		if (workEstimateItemDTOList.isEmpty()) {
			throw new RecordNotFoundException(
					frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.copySearch.noItems"), "invalid id");
		}

		return ResponseEntity.ok().body(workEstimateItemDTOList);

	}

	/**
	 * Copy item LBD within sub estimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param workItemId     the work item id
	 * @param sourceItemId   the source item id
	 * @return the response entity
	 * @throws URISyntaxException the URI syntax exception
	 */
	@GetMapping("work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/copy-lbds")
	public ResponseEntity<WorkEstimateItemLBDResponseDTO> copyItemLBDWithinSubEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "itemId", required = true) Long workItemId,
			@RequestParam(value = "sourceItemId", required = true) Long sourceItemId) throws URISyntaxException {

		log.debug("REST request to copyItemLBDWithinSubEstimate : {}", workEstimateId, subEstimateId, workItemId);

		// Find By WorkEstimateId

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalid WorkEstimateId"));

		if (workEstimateDTO.getStatus() != WorkEstimateStatus.DRAFT
				&& workEstimateDTO.getStatus() != WorkEstimateStatus.INITIAL) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"custom");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.subEstimate.invalidSubEstimateId") + "-" + subEstimateId,
						"invalid SubEstimateId"));

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateDTO.getId(), workItemId).orElseThrow(
						() -> new RecordNotFoundException(
								frameWorkComponent.resolveI18n(
										"validation.workEstimateItemLBD.workItem.invalidworkItemId") + "-" + workItemId,
								"invalid Item Id"));

		WorkEstimateItemDTO workEstimateItemDTOSource = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateDTO.getId(), sourceItemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.workItem.invalidworkItemId")
								+ "-" + sourceItemId,
						"invalid Item Id"));

		List<WorkEstimateItemLBDDTO> workEstimateItemLBDOptList = workEstimateItemLBDService
				.findAllByWorkEstimateItemId(workEstimateItemDTOSource.getId());
		if (workEstimateItemLBDOptList.isEmpty()) {
			throw new RecordNotFoundException(
					frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.LBDDetails.NotPrepared") + " - "
							+ workEstimateItemLBDOptList,
					"workEstimateItemLBDOptList");
		}

		BigDecimal itemLBDsTotal = workEstimateItemLBDService.getItemLBDsTotal(workEstimateItemDTO.getId());

		BigDecimal actualItemLBDsTotal = workEstimateItemLBDService.getItemLBDsTotal(workEstimateItemDTOSource.getId());

		compareTotalLBDWithActualLBDAndFinalRate(itemLBDsTotal, actualItemLBDsTotal, workEstimateItemDTO.getFinalRate(),
				LBDOperation.ADDITION);
		List<WorkEstimateItemLBDDTO> destinationWorkEstimateItemLBDDTOList = new ArrayList<>();

		workEstimateItemLBDOptList.stream().forEach(workEstimateItemLBD -> {
			WorkEstimateItemLBDDTO destinationWorkEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
			destinationWorkEstimateItemLBDDTO.setAdditionDeduction(workEstimateItemLBD.getAdditionDeduction());
			destinationWorkEstimateItemLBDDTO.setWorkEstimateItemId(workEstimateItemDTO.getId());
			destinationWorkEstimateItemLBDDTO.setCalculatedYn(workEstimateItemLBD.getCalculatedYn());
			destinationWorkEstimateItemLBDDTO.setLbdBredth(workEstimateItemLBD.getLbdBredth());
			destinationWorkEstimateItemLBDDTO.setLbdBredthFormula(workEstimateItemLBD.getLbdBredthFormula());
			destinationWorkEstimateItemLBDDTO.setLbdDepth(workEstimateItemLBD.getLbdDepth());
			destinationWorkEstimateItemLBDDTO.setLbdDepthFormula(workEstimateItemLBD.getLbdDepthFormula());
			destinationWorkEstimateItemLBDDTO.setLbdLength(workEstimateItemLBD.getLbdLength());
			destinationWorkEstimateItemLBDDTO.setLbdLengthFormula(workEstimateItemLBD.getLbdLengthFormula());
			destinationWorkEstimateItemLBDDTO.setLbdNos(workEstimateItemLBD.getLbdNos());
			destinationWorkEstimateItemLBDDTO.setLbdParticulars(workEstimateItemLBD.getLbdParticulars());
			destinationWorkEstimateItemLBDDTO.setLbdQuantity(workEstimateItemLBD.getLbdQuantity());
			destinationWorkEstimateItemLBDDTO.setLbdTotal(workEstimateItemLBD.getLbdTotal());
			destinationWorkEstimateItemLBDDTO = workEstimateItemLBDService.save(destinationWorkEstimateItemLBDDTO);
			destinationWorkEstimateItemLBDDTOList.add(destinationWorkEstimateItemLBDDTO);
		});

		workEstimateItemLBDService.recalculateAndUpdateItemWRTLBD(workEstimateDTO.getId(), subEstimateDTO.getId(),
				workEstimateItemDTO.getId());
		return ResponseEntity
				.created(new URI("work-estimate/" + workEstimateDTO.getId() + "/sub-estimate/" + subEstimateDTO.getId()
						+ "/items/" + workEstimateItemDTO.getId() + "/copy-lbds"))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						workEstimateItemDTO.getId().toString()))
				.body(getLBDDetails(workEstimateId, subEstimateId, workEstimateItemDTO.getId()));
	}

	/**
	 * Upload LBD.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param itemId         the item id
	 * @param file           the file
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/upload-lbds")
	public ResponseEntity<WorkEstimateItemLBDResponseDTO> uploadLBD(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "itemId", required = true) Long itemId,
			@RequestParam(name = "file", required = true) MultipartFile file) throws Exception {
		log.debug("REST request to UploadLBD : {} {} {}", workEstimateId, subEstimateId, itemId);
		WorkEstimateItemLBDResponseDTO workEstimateItemLBDResponseDTO = new WorkEstimateItemLBDResponseDTO();

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalid WorkEstimateId"));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.subEstimate.invalidSubEstimateId") + "-" + subEstimateId,
						"invalid SubEstimateId"));

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
						() -> new RecordNotFoundException(
								frameWorkComponent.resolveI18n(
										"validation.workEstimateItemLBD.SORItem.invalidSORItemId") + "-" + itemId,
								"invalid SORItemId"));

		HSSFWorkbook myWorkBook = new HSSFWorkbook(file.getInputStream());
		List<WorkEstimateItemLBDDTO> lbdDTOList = new ArrayList<>();
		List<FieldErrorVM> fieldErrorDTOTotalList = new ArrayList<>();
		for (int i = 0; i < myWorkBook.getNumberOfSheets(); i++) {
			for (Row row : myWorkBook.getSheetAt(i)) {
				if (validateLBDRow(row, 8)) {
					if (row.getRowNum() != 0) {
						WorkEstimateItemLBDDTO lbdDTO = new WorkEstimateItemLBDDTO();
						lbdDTO.setWorkEstimateItemId(workEstimateItemDTO.getId());
						setLbdFieldsFromExcel(lbdDTO, row);
						fieldErrorDTOTotalList
								.addAll(validateWorkEstimateItemLBD(lbdDTO, String.valueOf(row.getRowNum())));
						if (CollectionUtils.isEmpty(fieldErrorDTOTotalList)) {
							lbdDTOList.add(lbdDTO);
						}
					}
				} else {
					throw new BadRequestAlertException(
							frameworkComponent
									.resolveI18n("validation.workEstimateItemLBD.uploadLBD.incorrectTemplate"),
							ENTITY_NAME, "incorrectTemplate");
				}
			}
		}
		if (!CollectionUtils.isEmpty(fieldErrorDTOTotalList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOTotalList);
		}
		if (lbdDTOList.isEmpty()) {
			throw new RecordNotFoundException(
					frameWorkComponent.resolveI18n("validation.workEstimateItemLBD.LBDDetails.NotPrepared") + " - "
							+ lbdDTOList,
					"NotPrepared");
		} else {
			BigDecimal excelLbdTotal = BigDecimal.ZERO;
			for (WorkEstimateItemLBDDTO workEstimateItemLBDDTO : lbdDTOList) {
				validateItemLBD(workEstimateItemLBDDTO);
				if (LBDOperation.DEDUCTION.equals(workEstimateItemLBDDTO.getAdditionDeduction())) {
					excelLbdTotal = excelLbdTotal.subtract(workEstimateItemLBDDTO.getLbdTotal());
				} else {
					excelLbdTotal = excelLbdTotal.add(workEstimateItemLBDDTO.getLbdTotal());
				}
			}
			workEstimateItemLBDResponseDTO.setLbds(lbdDTOList);
			workEstimateItemLBDResponseDTO.setLbdTotal(excelLbdTotal);
			workEstimateItemLBDResponseDTO.setWorkEstimateItemId(workEstimateItemDTO.getId());
			workEstimateItemLBDResponseDTO.setSubEstimateId(subEstimateId);
			workEstimateItemLBDResponseDTO.setWorkEstimateId(workEstimateId);
		}

		return ResponseEntity.ok().body(workEstimateItemLBDResponseDTO);
	}

	/**
	 * Sets the lbd fields from excel.
	 *
	 * @param lbdDTO the lbd DTO
	 * @param row    the row
	 */
	private void setLbdFieldsFromExcel(WorkEstimateItemLBDDTO lbdDTO, Row row) {
		lbdDTO.setLbdParticulars(row.getCell(0).toString().strip());
		lbdDTO.setLbdNos(new BigDecimal(row.getCell(1).toString()));
		if (row.getCell(2).toString().strip().equalsIgnoreCase("calculated")) {
			lbdDTO.setCalculatedYn(true);
			if (row.getCell(4) != null) {
				if (row.getCell(4).getCellType() != Cell.CELL_TYPE_NUMERIC) {
					lbdDTO.setLbdLengthFormula(row.getCell(4).toString().strip());
				} else {
					lbdDTO.setLbdLength(new BigDecimal(row.getCell(4).toString()));
				}
			}
			if (row.getCell(5) != null) {
				if (row.getCell(5).getCellType() != Cell.CELL_TYPE_NUMERIC) {
					lbdDTO.setLbdBredthFormula(row.getCell(5).toString().strip());
				} else {
					lbdDTO.setLbdBredth(new BigDecimal(row.getCell(5).toString()));
				}
			}
			if (row.getCell(6) != null) {
				if (row.getCell(6).getCellType() != Cell.CELL_TYPE_NUMERIC) {
					lbdDTO.setLbdDepthFormula(row.getCell(6).toString().strip());
				} else {
					lbdDTO.setLbdDepth(new BigDecimal(row.getCell(6).toString()));
				}
			}
		} else {
			lbdDTO.setCalculatedYn(false);
			lbdDTO.setLbdQuantity(new BigDecimal(row.getCell(3).toString()));
		}
		if (row.getCell(7).toString().strip().equalsIgnoreCase("addition")) {
			lbdDTO.setAdditionDeduction(LBDOperation.ADDITION);
		} else {
			lbdDTO.setAdditionDeduction(LBDOperation.DEDUCTION);
		}
	}

	/**
	 * Save uploaded LBD.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param itemId         the item id
	 * @param lbdDTOList     the lbd DTO list
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/items/{itemId}/save-uploaded-lbds")
	public ResponseEntity<List<WorkEstimateItemLBDDTO>> saveUploadedLBD(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "itemId", required = true) Long itemId,
			@RequestBody @Valid List<WorkEstimateItemLBDDTO> lbdDTOList) throws Exception {
		log.debug("REST request to Save UploadedLBD : {} {} {}", workEstimateId, subEstimateId, itemId);
		List<FieldErrorVM> fieldErrorDTOTotalList = new ArrayList<>();

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.workEstimate.invalidWorkEstimateId") + "-" + workEstimateId,
						"invalid WorkEstimateId"));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(
						"validation.workEstimateItemLBD.subEstimate.invalidSubEstimateId") + "-" + subEstimateId,
						"invalid SubEstimateId"));

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
						() -> new RecordNotFoundException(
								frameWorkComponent.resolveI18n(
										"validation.workEstimateItemLBD.SORItem.invalidSORItemId") + "-" + itemId,
								"invalid SORItemId"));
		AtomicInteger actomicInteger = new AtomicInteger(0);
		lbdDTOList.stream().forEach(lbdDTO -> {
			fieldErrorDTOTotalList
					.addAll(validateWorkEstimateItemLBD(lbdDTO, String.valueOf(actomicInteger.incrementAndGet())));
		});
		BigDecimal itemLBDsTotal = workEstimateItemLBDService.getItemLBDsTotal(workEstimateItemDTO.getId());

		BigDecimal totalLBD = BigDecimal.ZERO;
		for (WorkEstimateItemLBDDTO workEstimateItemLBDDTO : lbdDTOList) {
			if (workEstimateItemLBDDTO.getAdditionDeduction().equals(LBDOperation.ADDITION)) {
				totalLBD = totalLBD.add(workEstimateItemLBDDTO.getLbdTotal());
			} else {
				totalLBD = totalLBD.subtract(workEstimateItemLBDDTO.getLbdTotal());
			}
		}

		if (totalLBD.compareTo(BigDecimal.ZERO) > 0) {
			compareTotalLBDWithActualLBDAndFinalRate(itemLBDsTotal, totalLBD, workEstimateItemDTO.getFinalRate(),
					LBDOperation.ADDITION);
		} else {
			compareTotalLBDWithActualLBDAndFinalRate(itemLBDsTotal, totalLBD.abs(), workEstimateItemDTO.getFinalRate(),
					LBDOperation.DEDUCTION);
		}

		lbdDTOList = lbdDTOList.stream().map(lbdDTO -> {
			lbdDTO.setId(null);
			lbdDTO.setWorkEstimateItemId(itemId);
			lbdDTO = workEstimateItemLBDService.save(lbdDTO);
			return lbdDTO;
		}).collect(Collectors.toList());
		workEstimateItemLBDService.recalculateAndUpdateItemWRTLBD(workEstimateId, subEstimateId, itemId);
		return ResponseEntity.created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/"
				+ subEstimateId + "/items/" + itemId + "/save-uploaded-lbds")).body(lbdDTOList);
	}

	/**
	 * Download object.
	 *
	 * @param template the template
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping("/work-estimate/download-lbd-template")
	public ResponseEntity<ByteArrayResource> downloadObject(
			@Value("classpath:/templates/LBD_Template_Download.xls") Resource template) throws IOException {
		byte[] data = org.springframework.util.FileCopyUtils.copyToByteArray(template.getFile());

		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + "LBD_Template.xls" + "\"").body(resource);

	}

	/**
	 * Validate row.
	 *
	 * @param row the row
	 * @param i   the i
	 * @return true, if successful
	 */
	private boolean validateLBDRow(Row row, int i) {
		if (row == null) {
			return false;
		}
		if (row.getLastCellNum() < i - 1) {
			return false;
		}
		// Heading check
		if (row.getRowNum() == 0) {
			for (int j = 0; j < i; j++) {
				if (row.getCell(j) == null) {
					return false;
				}
				if (!StringUtils.hasText(row.getCell(j).toString())) {
					return false;
				}
			}
			if (!row.getCell(0).toString().strip().equalsIgnoreCase("Particulars")) {
				return false;
			}
			if (!row.getCell(1).toString().strip().equalsIgnoreCase("No")) {
				return false;
			}
			if (!row.getCell(2).toString().strip().equalsIgnoreCase("value")) {
				return false;
			}
			if (!row.getCell(3).toString().strip().equalsIgnoreCase("LBD")) {
				return false;
			}
			if (!row.getCell(4).toString().strip().equalsIgnoreCase("length")) {
				return false;
			}
			if (!row.getCell(5).toString().strip().equalsIgnoreCase("breadth")) {
				return false;
			}
			if (!row.getCell(6).toString().strip().equalsIgnoreCase("depth")) {
				return false;
			}
			if (!row.getCell(7).toString().strip().equalsIgnoreCase("addition/deduction")) {
				return false;
			}
		} else {
			// Content check
			for (int j = 0; j < 3; j++) {
				if (row.getCell(j) == null) {
					return false;
				}
				if (j == 1) {
					if (row.getCell(1).getCellType() != Cell.CELL_TYPE_NUMERIC) {
						return false;
					}
				}
				if (j == 2) {
					if (!row.getCell(2).toString().strip().equalsIgnoreCase("calculated")
							&& !row.getCell(2).toString().strip().equalsIgnoreCase("direct")) {
						return false;
					}
				}
			}

			if (row.getCell(2).toString().strip().equalsIgnoreCase("calculated")) {
				if (row.getCell(4) == null && row.getCell(5) == null && row.getCell(6) == null) {
					return false;
				}
			} else {
				if (row.getCell(3) == null) {
					return false;
				} else if (row.getCell(3).getCellType() != Cell.CELL_TYPE_NUMERIC) {
					return false;
				}
			}

			if (row.getCell(7) == null) {
				return false;
			}
			if (!row.getCell(7).toString().strip().equalsIgnoreCase("addition")
					&& !row.getCell(7).toString().strip().equalsIgnoreCase("deduction")) {
				return false;
			}
		}
		if (row.getLastCellNum() >= i) {
			for (int j = i; j <= row.getLastCellNum(); j++) {
				if (row.getCell(j) != null) {
					if (StringUtils.hasText(row.getCell(j).toString())) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
