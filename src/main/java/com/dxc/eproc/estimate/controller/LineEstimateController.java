package com.dxc.eproc.estimate.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.EstimateServiceConstants;
import com.dxc.eproc.estimate.service.LineEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateOverheadService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.AggregateLineEstimateDTO;
import com.dxc.eproc.estimate.service.dto.LineEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.CustomClientInputValidators;
import com.dxc.eproc.exceptionhandling.CustomMethodArgumentNotValidException;
import com.dxc.eproc.exceptionhandling.CustomValidatorConstants;
import com.dxc.eproc.exceptionhandling.CustomValidatorVM;
import com.dxc.eproc.exceptionhandling.FieldErrorVM;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.response.TextResponse;
import com.dxc.eproc.utils.PaginationUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.domain.LineEstimate}.
 */
@RestController
@RequestMapping("/v1/api")
public class LineEstimateController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(LineEstimateController.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "lineEstimate";

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The line estimate service. */
	@Autowired
	private LineEstimateService lineEstimateService;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The Constant ESTIMATE_BASE. */
	private static final String ESTIMATE_BASE = "workEstimate";

	/** The Constant LINE_ESTIMATE_BASE. */
	private static final String LINE_ESTIMATE_BASE = "lineEstimate";

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";

	/** The Constant APPROX_RATE. */
	private static final String APPROX_RATE = "approxRate";

	/** The frame work component. */
	@Autowired
	private FrameworkComponent frameWorkComponent;

	/** The work estimate overhead service. */
	@Autowired
	private WorkEstimateOverheadService workEstimateOverheadService;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/**
	 * {@code POST  /line-estimates} : Create a new lineEstimate.
	 *
	 * @param lineEstimateDTO the lineEstimateDTO to create.
	 * @param workEstimateId  the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new lineEstimateDTO, or with status
	 *         {@code 400 (Bad Request)} if the lineEstimate has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/line-estimate")
	public ResponseEntity<AggregateLineEstimateDTO> createLineEstimate(
			@Valid @RequestBody LineEstimateDTO lineEstimateDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws URISyntaxException {
		log.debug("REST request to save LineEstimate : {}", lineEstimateDTO);

		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (workEstimateDTO.getApprovedBudgetYn() != false) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		fieldErrorVMList.addAll(validateLineEstimate(lineEstimateDTO));

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameWorkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorVMList);
		}

		lineEstimateDTO.setId(null);
		lineEstimateDTO.setWorkEstimateId(workEstimateId);
		LineEstimateDTO result = lineEstimateService.save(lineEstimateDTO);

		AggregateLineEstimateDTO aggregateDTO = calculateLineEstimateTotals(workEstimateId);

		return ResponseEntity
				.created(
						new URI("/v1/api/work-estimate/" + workEstimateDTO.getId() + "/line-estimate" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						result.getId().toString()))
				.body(aggregateDTO);
	}

	/**
	 * Validate line estimate.
	 *
	 * @param linestimateDTO the linestimate DTO
	 * @return the list
	 */
	private List<FieldErrorVM> validateLineEstimate(LineEstimateDTO linestimateDTO) {
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();
		customValidatorVMList.add(new CustomValidatorVM(APPROX_RATE, linestimateDTO.getApproxRate(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.PATTERN),
				linestimateDTO.getClass().getName(), EstimateServiceConstants.OVERHEAD_APPROX_RATE, null,
				frameWorkComponent.resolveI18n("lineEstimate.approxRate.Pattern")));
		return customClientInputValidators.checkValidations(customValidatorVMList);
	}

	/**
	 * Calculate line estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the aggregate line estimate DTO
	 */
	private AggregateLineEstimateDTO calculateLineEstimateTotals(Long workEstimateId) {
		AggregateLineEstimateDTO aggregateDTO = new AggregateLineEstimateDTO();
		BigDecimal approxMateValue = lineEstimateService.sumApproximateValueByLineEstimateId(workEstimateId);

		aggregateDTO.setEstimatedCostValue(approxMateValue);

		List<LineEstimateDTO> lineEstimates = lineEstimateService.findAllByWorkEstimateId(workEstimateId);
		aggregateDTO.setLineEstimates(lineEstimates);

		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setId(workEstimateId);
		workEstimateDTO.setLineEstimateTotal(aggregateDTO.getEstimatedCostValue());
		workEstimateService.partialUpdate(workEstimateDTO);

		workEstimateOverheadService.calculateWorkEstimateNormalOverheadTotals(workEstimateId);
		workEstimateOverheadService.calculateWorkEstimateAdditionalOverheadTotals(workEstimateId);

		return aggregateDTO;
	}

	/**
	 * {@code PUT  /line-estimates/:id} : Updates an existing lineEstimate.
	 *
	 * @param id              the id of the lineEstimateDTO to save.
	 * @param lineEstimateDTO the lineEstimateDTO to update.
	 * @param workEstimateId  the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated lineEstimateDTO, or with status {@code 400 (Bad Request)}
	 *         if the lineEstimateDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the lineEstimateDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/line-estimate/{id}")
	public ResponseEntity<AggregateLineEstimateDTO> updateLineEstimate(
			@PathVariable(value = "id", required = true) Long id, @Valid @RequestBody LineEstimateDTO lineEstimateDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws URISyntaxException {
		log.debug("REST request to update LineEstimate : {}, {}", id, lineEstimateDTO);

		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (workEstimateDTO.getApprovedBudgetYn() != false) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		lineEstimateService.findByWorkEstimateIdAndId(workEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(LINE_ESTIMATE_BASE + ".recordNotFound") + " - " + id,
						ENTITY_NAME));

		fieldErrorVMList.addAll(validateLineEstimate(lineEstimateDTO));

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameWorkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorVMList);
		}

		lineEstimateDTO.setId(id);
		lineEstimateDTO.setWorkEstimateId(workEstimateId);
		lineEstimateService.save(lineEstimateDTO);

		AggregateLineEstimateDTO aggregateDTO = calculateLineEstimateTotals(workEstimateId);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				lineEstimateDTO.getId().toString())).body(aggregateDTO);
	}

	/**
	 * {@code PATCH  /line-estimates/:id} : Partial updates given fields of an
	 * existing lineEstimate, field will ignore if it is null.
	 *
	 * @param id              the id of the lineEstimateDTO to save.
	 * @param lineEstimateDTO the lineEstimateDTO to update.
	 * @param workEstimateId  the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated lineEstimateDTO, or with status {@code 400 (Bad Request)}
	 *         if the lineEstimateDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the lineEstimateDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the lineEstimateDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/work-estimate/{workEstimateId}/line-estimate/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<AggregateLineEstimateDTO> partialUpdateLineEstimate(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody LineEstimateDTO lineEstimateDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws URISyntaxException {
		log.debug("REST request to partial update LineEstimate partially : {}, {}", id, lineEstimateDTO);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (workEstimateDTO.getApprovedBudgetYn() != false) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}
		if (lineEstimateDTO.getId() == null) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(LINE_ESTIMATE_BASE + ".idNull"),
					ENTITY_NAME, "idNull");
		}
		lineEstimateDTO.setId(id);
		lineEstimateDTO.setWorkEstimateId(workEstimateId);
		lineEstimateService.partialUpdate(lineEstimateDTO);

		AggregateLineEstimateDTO aggregateDTO = calculateLineEstimateTotals(workEstimateId);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				lineEstimateDTO.getId().toString())).body(aggregateDTO);

	}

	/**
	 * {@code GET  /line-estimates} : get all the lineEstimates.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of lineEstimates in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/line-estimates")
	public ResponseEntity<AggregateLineEstimateDTO> getAllLineEstimates(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId, Pageable pageable) {
		log.debug("REST request to get a page of LineEstimates");

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		Page<LineEstimateDTO> lineEstimatesDTOList = lineEstimateService
				.getAllLineEstimatesByWorkEstimateId(workEstimateId, pageable);

		if (lineEstimatesDTOList.isEmpty()) {
			throw new RecordNotFoundException(frameWorkComponent.resolveI18n(LINE_ESTIMATE_BASE + ".noRecordsFound"),
					ENTITY_NAME);
		}
		AggregateLineEstimateDTO aggregateDTO = calculateLineEstimateTotals(workEstimateId);

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), lineEstimatesDTOList);
		return ResponseEntity.ok().headers(headers).body(aggregateDTO);

	}

	/**
	 * {@code GET  /line-estimates/:id} : get the "id" lineEstimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id of the lineEstimateDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the lineEstimateDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/line-estimate/{id}")
	public ResponseEntity<LineEstimateDTO> getLineEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to get LineEstimate : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		LineEstimateDTO lineEstimateDTO = lineEstimateService.findByWorkEstimateIdAndId(workEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(LINE_ESTIMATE_BASE + ".recordNotFound") + " - " + id,
						ENTITY_NAME));

		return ResponseEntity.ok().body(lineEstimateDTO);
	}

	/**
	 * {@code DELETE  /line-estimates/:id} : delete the "id" lineEstimate.
	 *
	 * @param id             the id of the lineEstimateDTO to delete.
	 * @param workEstimateId the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/line-estimate/{id}")
	public ResponseEntity<AggregateLineEstimateDTO> deleteLineEstimate(
			@PathVariable(value = "id", required = true) Long id,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) {
		log.debug("REST request to delete LineEstimate : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		LineEstimateDTO lineEstimateDTO = lineEstimateService.findByWorkEstimateIdAndId(workEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(LINE_ESTIMATE_BASE + ".recordNotFound") + " - " + id,
						ENTITY_NAME));
		lineEstimateService.delete(lineEstimateDTO.getId());

		AggregateLineEstimateDTO aggregateDTO = calculateLineEstimateTotals(workEstimateId);

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.body(aggregateDTO);
	}

	/**
	 * Validaet line estimates.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the response entity
	 */
	@GetMapping("/work-estimate/{workEstimateId}/validate-line-estimates")
	public ResponseEntity<TextResponse> validaetLineEstimates(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) {
		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		List<LineEstimateDTO> lineEstimateDTOList = lineEstimateService.findAllByWorkEstimateId(workEstimateId);
		if (!Utility.isValidCollection(lineEstimateDTOList)) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(LINE_ESTIMATE_BASE + ".atleastOne"),
					ENTITY_NAME, "valid");
		}

		return ResponseEntity.ok()
				.body(new TextResponse(frameWorkComponent.resolveI18n(LINE_ESTIMATE_BASE + ".valid")));
	}
}
