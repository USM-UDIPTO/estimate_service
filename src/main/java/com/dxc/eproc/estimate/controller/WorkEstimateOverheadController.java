package com.dxc.eproc.estimate.controller;

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

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.EstimateServiceConstants;
import com.dxc.eproc.estimate.enumeration.OverHeadType;
import com.dxc.eproc.estimate.service.LineEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateOverheadService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.AggregateWorkEstimateOverheadDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOverheadDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.CustomClientInputValidators;
import com.dxc.eproc.exceptionhandling.CustomMethodArgumentNotValidException;
import com.dxc.eproc.exceptionhandling.CustomValidatorConstants;
import com.dxc.eproc.exceptionhandling.CustomValidatorVM;
import com.dxc.eproc.exceptionhandling.FieldErrorVM;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.domain.WorkEstimateOverhead}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateOverheadController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateOverheadController.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workEstimateOverhead";

	/** The Constant ESTIMATE_BASE. */
	private static final String ESTIMATE_BASE = "workEstimate";

	/** The Constant ESTIMATE_BASE. */
	private static final String WORKESTIMATEOVERHEAD_ESTIMATE_BASE = "workEstimateOverhead";

	/** The Constant OVERHEAD_VALUE. */
	private static final String OVERHEAD_VALUE = "overHeadValue";

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The work estimate overhead service. */
	@Autowired
	private WorkEstimateOverheadService workEstimateOverheadService;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The frame work component. */
	@Autowired
	private FrameworkComponent frameWorkComponent;

	/** The line estimate service. */
	@Autowired
	private LineEstimateService lineEstimateService;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/**
	 * Instantiates a new work estimate overhead controller.
	 */
	public WorkEstimateOverheadController() {
	}

	/**
	 * {@code POST  /work-estimate-overheads} : Create a new workEstimateOverhead.
	 *
	 * @param workEstimateOverheadDTO the workEstimateOverheadDTO to create.
	 * @param workEstimateId          the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateOverheadDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateOverhead has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/work-estimate-overhead")
	public ResponseEntity<AggregateWorkEstimateOverheadDTO> createWorkEstimateOverhead(
			@Valid @RequestBody WorkEstimateOverheadDTO workEstimateOverheadDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws URISyntaxException {
		log.debug("REST request to save WorkEstimateOverhead : {}", workEstimateOverheadDTO);

		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));
		if (workEstimateDTO.getApprovedBudgetYn() != false) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}
		if (workEstimateOverheadDTO.getId() != null) {
			throw new BadRequestAlertException(
					frameWorkComponent.resolveI18n(WORKESTIMATEOVERHEAD_ESTIMATE_BASE + ".idAlreadyExists"),
					ENTITY_NAME, "idAlreadyExists");
		}

		fieldErrorVMList.addAll(validateWorkEstimateOverhead(workEstimateOverheadDTO));

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameWorkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorVMList);
		}

		workEstimateOverheadDTO.setWorkEstimateId(workEstimateId);
		WorkEstimateOverheadDTO result = workEstimateOverheadService.save(workEstimateOverheadDTO);

		AggregateWorkEstimateOverheadDTO aggregateDTO = getAggregateWorkEstimateOverheadDTO(workEstimateId);

		List<WorkEstimateOverheadDTO> workEstimateOverheadList = null;
		if (OverHeadType.NORMAL.equals(result.getOverHeadType())) {
			workEstimateOverheadList = new ArrayList<WorkEstimateOverheadDTO>();
			workEstimateOverheadList.add(result);
			aggregateDTO.setNormalWorkEstimateOverheadList(workEstimateOverheadList);
		} else if (OverHeadType.ADDITIONAL.equals(result.getOverHeadType())) {
			workEstimateOverheadList = new ArrayList<WorkEstimateOverheadDTO>();
			workEstimateOverheadList.add(result);
			aggregateDTO.setAdditionalWorkEstimateOverheadList(workEstimateOverheadList);
		}
		return ResponseEntity
				.created(new URI(
						"/v1/api/work-estimate" + workEstimateDTO.getId() + "/work-estimate-overhead" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						result.getId().toString()))
				.body(aggregateDTO);
	}

	/**
	 * Validate work estimate overhead.
	 *
	 * @param workEstimateOverheadDTO the work estimate overhead DTO
	 * @return the list
	 */
	private List<FieldErrorVM> validateWorkEstimateOverhead(WorkEstimateOverheadDTO workEstimateOverheadDTO) {
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();
		if (OverHeadType.NORMAL.equals(workEstimateOverheadDTO.getOverHeadType())) {
			customValidatorVMList.add(new CustomValidatorVM(OVERHEAD_VALUE, workEstimateOverheadDTO.getOverHeadValue(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.PATTERN),
					workEstimateOverheadDTO.getClass().getName(), EstimateServiceConstants.OVERHEAD_APPROX_RATE, null,
					frameWorkComponent.resolveI18n("workEstimateOverhead.overHeadValue.NotNull")));
		} else if (OverHeadType.ADDITIONAL.equals(workEstimateOverheadDTO.getOverHeadType())) {
			customValidatorVMList.add(new CustomValidatorVM(OVERHEAD_VALUE, workEstimateOverheadDTO.getOverHeadValue(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.PATTERN),
					workEstimateOverheadDTO.getClass().getName(), EstimateServiceConstants.OVERHEAD_APPROX_RATE, null,
					frameWorkComponent.resolveI18n("workEstimateOverhead.overHeadValue.NotNull")));
		}
		return customClientInputValidators.checkValidations(customValidatorVMList);
	}

	/**
	 * Gets the aggregate work estimate overhead DTO.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the aggregate work estimate overhead DTO
	 */
	private AggregateWorkEstimateOverheadDTO getAggregateWorkEstimateOverheadDTO(Long workEstimateId) {
		AggregateWorkEstimateOverheadDTO aggregateDTO = new AggregateWorkEstimateOverheadDTO();
		aggregateDTO.setLineEstimateTotal(lineEstimateService.sumApproximateValueByLineEstimateId(workEstimateId));
		aggregateDTO.setNormalOverheadTotal(
				workEstimateOverheadService.calculateWorkEstimateNormalOverheadTotals(workEstimateId));
		// aggregateDTO.setNormalWorkEstimateOverheadList(workEstimateOverheadService
		// .findAllByWorkEstimateIdAndOverHeadType(workEstimateId,
		// OverHeadType.NORMAL));

		aggregateDTO.setAdditionalOverheadTotal(
				workEstimateOverheadService.calculateWorkEstimateAdditionalOverheadTotals(workEstimateId));
		// aggregateDTO.setAdditionalWorkEstimateOverheadList(workEstimateOverheadService
		// .findAllByWorkEstimateIdAndOverHeadType(workEstimateId,
		// OverHeadType.ADDITIONAL));
		return aggregateDTO;
	}

	/**
	 * {@code PUT  /work-estimate-overheads/:id} : Updates an existing
	 * workEstimateOverhead.
	 *
	 * @param id                      the id of the workEstimateOverheadDTO to save.
	 * @param workEstimateOverheadDTO the workEstimateOverheadDTO to update.
	 * @param workEstimateId          the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateOverheadDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateOverheadDTO is not
	 *         valid, or with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateOverheadDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/work-estimate-overhead/{id}")
	public ResponseEntity<AggregateWorkEstimateOverheadDTO> updateWorkEstimateOverhead(
			@PathVariable(value = "id", required = false) Long id,
			@Valid @RequestBody WorkEstimateOverheadDTO workEstimateOverheadDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws URISyntaxException {
		log.debug("REST request to update WorkEstimateOverhead : {}, {}", id, workEstimateOverheadDTO);

		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));
		if (workEstimateDTO.getApprovedBudgetYn() != false) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}
		workEstimateOverheadService.findByWorkEstimateIdAndId(workEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(WORKESTIMATEOVERHEAD_ESTIMATE_BASE + ".recordNotFound") + " - "
								+ id,
						ENTITY_NAME));

		fieldErrorVMList.addAll(validateWorkEstimateOverhead(workEstimateOverheadDTO));

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameWorkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorVMList);
		}

		workEstimateOverheadDTO.setId(id);
		workEstimateOverheadDTO.setWorkEstimateId(workEstimateId);
		WorkEstimateOverheadDTO result = workEstimateOverheadService.save(workEstimateOverheadDTO);

		AggregateWorkEstimateOverheadDTO aggregateDTO = getAggregateWorkEstimateOverheadDTO(workEstimateId);

		List<WorkEstimateOverheadDTO> workEstimateOverheadList = null;
		if (OverHeadType.NORMAL.equals(result.getOverHeadType())) {
			workEstimateOverheadList = new ArrayList<WorkEstimateOverheadDTO>();
			workEstimateOverheadList.add(result);
			aggregateDTO.setNormalWorkEstimateOverheadList(workEstimateOverheadList);
		} else if (OverHeadType.ADDITIONAL.equals(result.getOverHeadType())) {
			workEstimateOverheadList = new ArrayList<WorkEstimateOverheadDTO>();
			workEstimateOverheadList.add(result);
			aggregateDTO.setAdditionalWorkEstimateOverheadList(workEstimateOverheadList);
		}

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workEstimateOverheadDTO.getId().toString())).body(aggregateDTO);
	}

	/**
	 * {@code PATCH  /work-estimate-overheads/:id} : Partial updates given fields of
	 * an existing workEstimateOverhead, field will ignore if it is null.
	 *
	 * @param id                      the id of the workEstimateOverheadDTO to save.
	 * @param workEstimateOverheadDTO the workEstimateOverheadDTO to update.
	 * @param workEstimateId          the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateOverheadDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateOverheadDTO is not
	 *         valid, or with status {@code 404 (Not Found)} if the
	 *         workEstimateOverheadDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the workEstimateOverheadDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/work-estimate/{workEstimateId}/work-estimate-overhead/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<AggregateWorkEstimateOverheadDTO> partialUpdateWorkEstimateOverhead(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody WorkEstimateOverheadDTO workEstimateOverheadDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws URISyntaxException {
		log.debug("REST request to partial update WorkEstimateOverhead partially : {}, {}", id,
				workEstimateOverheadDTO);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));
		if (workEstimateDTO.getApprovedBudgetYn() != false) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}
		workEstimateOverheadService.findByWorkEstimateIdAndId(workEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(WORKESTIMATEOVERHEAD_ESTIMATE_BASE + ".recordNotFound") + " - "
								+ id,
						ENTITY_NAME));
		workEstimateOverheadDTO.setId(id);
		workEstimateOverheadDTO.setWorkEstimateId(workEstimateId);
		workEstimateOverheadService.partialUpdate(workEstimateOverheadDTO);

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
						workEstimateOverheadDTO.getId().toString()))
				.body(getAggregateWorkEstimateOverheadDTO(workEstimateId));
	}

	/**
	 * {@code GET  /work-estimate-overheads} : get all the workEstimateOverheads.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateOverheads in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/work-estimate-overheads")
	public ResponseEntity<AggregateWorkEstimateOverheadDTO> getAllWorkEstimateOverheads(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) {
		log.debug("REST request to get a page of WorkEstimateOverheads");

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		AggregateWorkEstimateOverheadDTO aggregateDTO = getAggregateWorkEstimateOverheadDTO(workEstimateDTO.getId());

		aggregateDTO.setNormalWorkEstimateOverheadList(workEstimateOverheadService
				.findAllByWorkEstimateIdAndOverHeadType(workEstimateDTO.getId(), OverHeadType.NORMAL));

		aggregateDTO.setAdditionalWorkEstimateOverheadList(workEstimateOverheadService
				.findAllByWorkEstimateIdAndOverHeadType(workEstimateDTO.getId(), OverHeadType.ADDITIONAL));

		return ResponseEntity.ok().body(aggregateDTO);
	}

	/**
	 * {@code GET  /work-estimate-overheads/:id} : get the "id"
	 * workEstimateOverhead.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id of the workEstimateOverheadDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateOverheadDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/work-estimate-overhead/{id}")
	public ResponseEntity<WorkEstimateOverheadDTO> getWorkEstimateOverhead(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId, @PathVariable Long id) {
		log.debug("REST request to get WorkEstimateOverhead : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadService
				.findByWorkEstimateIdAndId(workEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(WORKESTIMATEOVERHEAD_ESTIMATE_BASE + ".recordNotFound") + " - "
								+ id,
						ENTITY_NAME));

		return ResponseEntity.ok().body(workEstimateOverheadDTO);
	}

	/**
	 * {@code DELETE  /work-estimate-overheads/:id} : delete the "id"
	 * workEstimateOverhead.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id of the workEstimateOverheadDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/work-estimate-overhead/{id}")
	public ResponseEntity<AggregateWorkEstimateOverheadDTO> deleteWorkEstimateOverhead(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId, @PathVariable Long id) {
		log.debug("REST request to delete WorkEstimateOverhead : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		WorkEstimateOverheadDTO workEstimateOverheadDTO = workEstimateOverheadService
				.findByWorkEstimateIdAndId(workEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(WORKESTIMATEOVERHEAD_ESTIMATE_BASE + ".recordNotFound") + " - "
								+ id,
						ENTITY_NAME));

		workEstimateOverheadService.delete(workEstimateOverheadDTO.getId());

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.body(getAggregateWorkEstimateOverheadDTO(workEstimateId));
	}
}
