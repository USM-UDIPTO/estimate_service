package com.dxc.eproc.estimate.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.EstimateServiceConstants;
import com.dxc.eproc.estimate.config.SeriesGenerator;
import com.dxc.eproc.estimate.enumeration.WorkEstimateSearch;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.response.dto.WorkEstimateSearchResponseDTO;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkCategoryService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateSearchDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.CustomClientInputValidators;
import com.dxc.eproc.exceptionhandling.CustomMethodArgumentNotValidException;
import com.dxc.eproc.exceptionhandling.CustomValidatorConstants;
import com.dxc.eproc.exceptionhandling.CustomValidatorVM;
import com.dxc.eproc.exceptionhandling.FieldErrorVM;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.response.TextResponse;
import com.dxc.eproc.utils.DateUtil;
import com.dxc.eproc.utils.PaginationUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimate}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workEstimate";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateController.class);

	/** The work estimate service. */
	private final WorkEstimateService workEstimateService;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The work category service. */
	@Autowired
	private WorkCategoryService workCategoryService;

	/**
	 * Instantiates a new work estimate controller.
	 *
	 * @param workEstimateService the work estimate service
	 * @param frameworkComponent  the framework component
	 */
	public WorkEstimateController(WorkEstimateService workEstimateService, FrameworkComponent frameworkComponent) {
		this.workEstimateService = workEstimateService;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST  /work-estimates} : Create a new workEstimate.
	 *
	 * @param workEstimateDTO the workEstimateDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimate has already an ID.
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate")
	@Transactional
	public ResponseEntity<WorkEstimateDTO> createWorkEstimate(@Valid @RequestBody WorkEstimateDTO workEstimateDTO)
			throws Exception {
		log.debug("REST request to save WorkEstimate : {}", workEstimateDTO);
		if (workEstimateDTO.getId() != null) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("workEstimate.idAlreadyExists"),
					ENTITY_NAME, "idexists");
		}

		// validator
		validateWorkEsitmate(workEstimateDTO);

		workEstimateDTO.setStatus(WorkEstimateStatus.DRAFT);
		WorkCategoryDTO workCategoryDTO = workCategoryService.findOne(workEstimateDTO.getWorkCategoryId())
				.orElseThrow(() -> new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"),
						ENTITY_NAME, "idnotfound"));
		workEstimateDTO.setWorkEstimateNumber(
				getWorkIndentNumber(workEstimateDTO.getDeptCode(), workCategoryDTO.getCategoryCode()));
		WorkEstimateDTO workEstimateResult = workEstimateService.save(workEstimateDTO);
		return ResponseEntity.created(new URI("/v1/api/work-estimates/" + workEstimateResult.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						workEstimateResult.getId().toString()))
				.body(workEstimateResult);
	}

	/**
	 * Gets the work indent number.
	 *
	 * @param deptCode     the dept code
	 * @param categoryCode the category code
	 * @return the work indent number
	 */
	public String getWorkIndentNumber(String deptCode, String categoryCode) {

		StringBuilder series = new StringBuilder();
		series.append(deptCode).append("/").append(DateUtil.getFinancialYear(new Date())).append("/")
				.append(EstimateServiceConstants.WORK_INDENT_SERIES);

		StringBuilder tenderNumber = new StringBuilder();
		tenderNumber.append(deptCode).append("/").append(DateUtil.getFinancialYear(new Date())).append("/")
				.append(categoryCode).append("/").append(EstimateServiceConstants.WORK_INDENT_SERIES);

		return tenderNumber + SeriesGenerator.getIndentSeries(entityManager, series.toString());
	}

	/**
	 * {@code PUT  /work-estimates/:id} : Updates an existing workEstimate.
	 *
	 * @param id              the id of the workEstimateDTO to save.
	 * @param workEstimateDTO the workEstimateDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateDTO, or with status {@code 400 (Bad Request)}
	 *         if the workEstimateDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the workEstimateDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{id}")
	public ResponseEntity<WorkEstimateDTO> updateWorkEstimate(
			@PathVariable(value = "id", required = true) final Long id,
			@Valid @RequestBody WorkEstimateDTO workEstimateDTO) throws URISyntaxException {
		log.debug("REST request to update WorkEstimate : {}, {}", id, workEstimateDTO);

		WorkEstimateDTO workEstimateDBDTO = workEstimateService.findOne(id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("workEstimate.notFound") + " - " + id,
						ENTITY_NAME));

		if (!(workEstimateDBDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDBDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		// validator
		workEstimateDTO.setId(id);
		validateWorkEsitmate(workEstimateDTO);
		
		WorkEstimateDTO workEstimateResult = workEstimateService.save(workEstimateDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workEstimateDTO.getId().toString())).body(workEstimateResult);
	}

	/**
	 * Partial update work estimate.
	 *
	 * @param id              the id
	 * @param workEstimateDTO the work estimate DTO
	 * @return the response entity
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PatchMapping(value = "/work-estimate/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<WorkEstimateDTO> partialUpdateWorkEstimate(
			@PathVariable(value = "id", required = true) final Long id,
			@NotNull @RequestBody WorkEstimateDTO workEstimateDTO) throws URISyntaxException {
		log.debug("REST request to partial update WorkEstimate partially : {}, {}", id, workEstimateDTO);

		WorkEstimateDTO workEstimateDBDTO = workEstimateService.findOne(id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n("workEstimate.notFound") + " - " + id,
						ENTITY_NAME));

		if (!(workEstimateDBDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDBDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}
		workEstimateDTO.setId(id);
		Optional<WorkEstimateDTO> result = workEstimateService.partialUpdate(workEstimateDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, workEstimateDTO.getId().toString()));

	}

	/**
	 * {@code GET  /work-estimates/:id} : get the "id" workEstimate.
	 *
	 * @param id the id of the workEstimateDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{id}")
	public ResponseEntity<WorkEstimateDTO> getWorkEstimate(@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to get WorkEstimate : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(id).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n("workEstimate.notFound") + " - " + id, ENTITY_NAME));

		return ResponseEntity.ok().body(workEstimateDTO);

	}

	/**
	 * Validate sub estimates.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping("/work-estimate/{id}/validate-subestimates")
	public ResponseEntity<TextResponse> validateSubEstimates(@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to get WorkEstimate : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(id).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n("workEstimate.notFound") + " - " + id, ENTITY_NAME));

		List<SubEstimateDTO> subEstimateDTOList = subEstimateService.findAllByWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTOList.stream().forEach(subEstimateDTO -> {
			if (subEstimateDTO.getCompletedYn().equals(false)) {
				throw new BadRequestAlertException(frameworkComponent.resolveI18n("workEstimate.validateSubEstimates"),
						ENTITY_NAME, "validateSubEstimates");
			}
		});

		if (workEstimateDTO.getApprovedBudgetYn().equals(true)) {

			BigDecimal provisionalAmount = workEstimateDTO.getProvisionalAmount();

			BigDecimal maxEstimateVariencePercentage = BigDecimal.valueOf(10.00);

			BigDecimal calculatedValue = maxEstimateVariencePercentage.multiply(provisionalAmount)
					.divide(BigDecimal.valueOf(100));

			if (workEstimateDTO.getEcv().compareTo(provisionalAmount.add(calculatedValue)) == 1) {
				throw new BadRequestAlertException(
						frameworkComponent.resolveI18n("workEstimate.exceedSanctionedAmount"), ENTITY_NAME,
						"validateSubEstimates");
			}
		}

		return ResponseEntity.ok()
				.body(new TextResponse(frameworkComponent.resolveI18n("workEstimate.subEstimatesValidated")));

	}

	/**
	 * Complete work estimate.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@GetMapping("/work-estimate/{id}/complete-workestimate")
	public ResponseEntity<WorkEstimateDTO> completeWorkEstimate(@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to complete WorkEstimate : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(id).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n("workEstimate.notFound") + " - " + id, ENTITY_NAME));

		List<SubEstimateDTO> subEstimateDTOList = subEstimateService.findAllByWorkEstimateId(workEstimateDTO.getId());
		subEstimateDTOList.stream().forEach(subEstimateDTO -> {
			if (subEstimateDTO.getCompletedYn().equals(false)) {
				throw new BadRequestAlertException(frameworkComponent.resolveI18n("workEstimate.validateSubEstimates"),
						ENTITY_NAME, "validateSubEstimates");
			}
		});

		WorkEstimateDTO workEstimatePartialDTO = new WorkEstimateDTO();
		workEstimatePartialDTO.setId(workEstimateDTO.getId());
		if (workEstimateDTO.getStatus() != null) {

			if (workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)) {
				if (workEstimateDTO.getApprovedBudgetYn().equals(true)) {
					workEstimatePartialDTO.setStatus(WorkEstimateStatus.INITIAL_DETAIL_ESTIMATE);
				} else {
					workEstimatePartialDTO.setStatus(WorkEstimateStatus.INITIAL);
				}
			} else if (workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT_DETAIL_ESTIMATE)) {
				workEstimatePartialDTO.setStatus(WorkEstimateStatus.INITIAL_DETAIL_ESTIMATE);
			}
		}
		workEstimateService.partialUpdate(workEstimateDTO);

		return ResponseEntity.ok().body(workEstimateDTO);

	}

	/**
	 * Validate work esitmate.
	 *
	 * @param workEstimate the work estimate
	 */
	private void validateWorkEsitmate(WorkEstimateDTO workEstimate) {
		// Custom Validation
		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

		if (workEstimate.getWorkCategoryAttribute() != null) {
			// workCategoryAttributeValue
			customValidatorVMList.add(
					new CustomValidatorVM("workCategoryAttributeValue", workEstimate.getWorkCategoryAttributeValue(),
							Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimate.getClass().getName()));

			if (workEstimate.getWorkCategoryAttributeValue() != null) {
				customValidatorVMList.add(new CustomValidatorVM("workCategoryAttributeValue",
						workEstimate.getWorkCategoryAttributeValue(), Arrays.asList(CustomValidatorConstants.PATTERN),
						workEstimate.getClass().getName(), EstimateServiceConstants.EPROC_DECIMAL_PATTERN, null,
						frameworkComponent.resolveI18n("workEstimate.workCategoryAttributeValue.pattern")));
			}
		}
		if (workEstimate.getApprovedBudgetYn().equals(true)) {
			// budgetReferenceId
			customValidatorVMList.add(new CustomValidatorVM("documentReference", workEstimate.getDocumentReference(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimate.getClass().getName()));
			// provisionalAmount
			customValidatorVMList.add(new CustomValidatorVM("provisionalAmount", workEstimate.getProvisionalAmount(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimate.getClass().getName()));
			if (workEstimate.getProvisionalAmount() != null) {
				customValidatorVMList.add(new CustomValidatorVM("provisionalAmount",
						workEstimate.getProvisionalAmount(), Arrays.asList(CustomValidatorConstants.PATTERN),
						workEstimate.getClass().getName(), EstimateServiceConstants.EPROC_DECIMAL_PATTERN, null,
						frameworkComponent.resolveI18n("workEstimate.provisionalAmount.pattern")));
			}
			// headOfAccount
			customValidatorVMList.add(new CustomValidatorVM("headOfAccount", workEstimate.getHeadOfAccount(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimate.getClass().getName()));
		}
		if (workEstimate.getHkrdbFundedYn().equals(true)) {
			// schemeId
			customValidatorVMList.add(new CustomValidatorVM("schemeId", workEstimate.getSchemeId(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimate.getClass().getName()));

		}

		if (workEstimate.getId() == null) {
			// DeptIdAndfileNUmber
			if (Utility.isValidString(workEstimate.getFileNumber())) {
				workEstimateService.findByDeptIdAndFileNumber(workEstimate.getDeptId(), workEstimate.getFileNumber())
						.ifPresent(s -> {
							fieldErrorVMList.add(new FieldErrorVM(ENTITY_NAME, "fileNumber", "fileNumberAlreadyExists",
									frameworkComponent.resolveI18n("workEstimate.fileNumberAlreadyExists")));
						});
			}

			// schemeId
			if (Utility.isValidLong(workEstimate.getSchemeId())) {
				workEstimateService.findBySchemeId(workEstimate.getSchemeId()).ifPresent(s -> {
					fieldErrorVMList.add(new FieldErrorVM(ENTITY_NAME, "schemeId", "schemeIdAlreadyExists",
							frameworkComponent.resolveI18n("workEstimate.schemeIdAlreadyExists")));

				});
			}
		}

		if (Utility.isValidLong(workEstimate.getId())) {
			// DeptIdAndfileNUmberAndIdNot
			if (Utility.isValidString(workEstimate.getFileNumber())) {
				workEstimateService.findByDeptIdAndFileNumberAndIdNot(workEstimate.getDeptId(),
						workEstimate.getFileNumber(), workEstimate.getId()).ifPresent(s -> {
							fieldErrorVMList.add(new FieldErrorVM(ENTITY_NAME, "fileNumber", "fileNumberAlreadyExists",
									frameworkComponent.resolveI18n("workEstimate.fileNumberAlreadyExists")));
						});
			}
			// schemeIdAndIdNot
			if (Utility.isValidLong(workEstimate.getSchemeId())) {
				workEstimateService.findBySchemeIdAndIdNot(workEstimate.getSchemeId(), workEstimate.getId())
						.ifPresent(s -> {
							fieldErrorVMList.add(new FieldErrorVM(ENTITY_NAME, "schemeId", "schemeIdAlreadyExists",
									frameworkComponent.resolveI18n("workEstimate.schemeIdAlreadyExists")));

						});
			}
		}

		// grantAllocatedAmount
		if (workEstimate.getGrantAllocatedAmount() != null) {
			customValidatorVMList.add(new CustomValidatorVM("grantAllocatedAmount",
					workEstimate.getGrantAllocatedAmount(), Arrays.asList(CustomValidatorConstants.PATTERN),
					workEstimate.getClass().getName(), EstimateServiceConstants.EPROC_DECIMAL_PATTERN, null,
					frameworkComponent.resolveI18n("workEstimate.grantAllocatedAmount.pattern")));
		}

		// description
		if (workEstimate.getDescription() != null) {
			customValidatorVMList.add(new CustomValidatorVM("description", workEstimate.getDescription(),
					Arrays.asList(CustomValidatorConstants.PATTERN), workEstimate.getClass().getName(),
					EstimateServiceConstants.SCOPE_OF_WORK, null,
					frameworkComponent.resolveI18n("workEstimate.description.Pattern")));
		}

		List<FieldErrorVM> fieldErrorDTOList = customClientInputValidators.checkValidations(customValidatorVMList);

		fieldErrorVMList.addAll(fieldErrorDTOList);

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n("fieldError.customMethodArgumentNotValidException"),
					fieldErrorVMList);
		}
	}

	/**
	 * Search work estimate.
	 *
	 * @param workEstimateSearch    the work estimate search
	 * @param createdBy             the created by
	 * @param pageIndex             the page index
	 * @param pageSize              the page size
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/search-work-estimate")
	public ResponseEntity<List<WorkEstimateSearchResponseDTO>> searchWorkEstimate(
			@RequestParam(value = "workEstimateSearch", required = true) WorkEstimateSearch workEstimateSearch,
			@RequestParam(value = "createdBy", required = true) String createdBy,
			@RequestParam(name = "page", defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", defaultValue = "5") int pageSize,
			@RequestBody WorkEstimateSearchDTO workEstimateSearchDTO) throws Exception {
		log.debug("REST request to get a page of searchWorkEstimateByQueryDSL");
		Page<WorkEstimateSearchResponseDTO> workEstimateSearchResponseDTOPage = workEstimateService
				.searchWorkEstimateByQueryDSL(PageRequest.of(pageIndex, pageSize), workEstimateSearchDTO, createdBy,
						workEstimateSearch);

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				ServletUriComponentsBuilder.fromCurrentRequest(), workEstimateSearchResponseDTOPage);

		return ResponseEntity.ok().headers(headers).body(workEstimateSearchResponseDTOPage.getContent());

	}

	/**
	 * Search work estimate for copy estimate.
	 *
	 * @param deptId                the dept id
	 * @param createdBy             the created by
	 * @param pageIndex             the page index
	 * @param pageSize              the page size
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/search-work-estimate-for-copy-estimate")
	public ResponseEntity<List<WorkEstimateSearchResponseDTO>> searchWorkEstimateForCopyEstimate(
			@RequestParam(value = "deptId", required = true) Long deptId,
			@RequestParam(value = "createdBy", required = true) String createdBy,
			@RequestParam(name = "page", defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", defaultValue = "5") int pageSize,
			@RequestBody WorkEstimateSearchDTO workEstimateSearchDTO) throws Exception {
		log.debug("REST request to get a page of searchWorkEstimateForCopyEstimate");
		Page<WorkEstimateSearchResponseDTO> workEstimateSearchResponseDTOPage = workEstimateService
				.searchWorkEstimateForCopyEstimate(PageRequest.of(pageIndex, pageSize), workEstimateSearchDTO, deptId,
						createdBy);

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				ServletUriComponentsBuilder.fromCurrentRequest(), workEstimateSearchResponseDTOPage);

		return ResponseEntity.ok().headers(headers).body(workEstimateSearchResponseDTOPage.getContent());

	}
}
