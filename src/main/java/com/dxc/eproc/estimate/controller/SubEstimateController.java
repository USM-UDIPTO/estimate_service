package com.dxc.eproc.estimate.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.response.dto.WorkEstimateResponseDTO;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateCategoryService;
import com.dxc.eproc.estimate.service.WorkEstimateItemLBDService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkLocationService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.CopyDetailSubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.SubEstimateAggregateDTO;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateCategoryDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;
import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;
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
 * {@link com.dxc.eproc.estimate.model.SubEstimate}.
 */
@RestController
@RequestMapping("/v1/api")
public class SubEstimateController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "subEstimate";

	/** The Constant PERMISSION. */
	private static final String PERMISSION = "authorization.permision";

	/** The Constant ESTIMATE_BASE. */
	private static final String ESTIMATE_BASE = "estimate";

	/** The Constant SUB_ESTIMATE_BASE. */
	private static final String SUB_ESTIMATE_BASE = "subEstimate";

	/** The Constant QUANTITY. */
	private static final String QUANTITY = "quantity";

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SubEstimateController.class);

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The frame work component. */
	@Autowired
	private FrameworkComponent frameWorkComponent;

	/** The work location service. */
	@Autowired
	private WorkLocationService workLocationService;

	/** The work estimate item service. */
	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	/** The work sub estimate group service. */
	@Autowired
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	/** The framework component. */
	@Autowired
	private FrameworkComponent frameworkComponent;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/** The work estimate item LBD service. */
	@Autowired
	private WorkEstimateItemLBDService workEstimateItemLBDService;

	/** The work estimate category service. */
	@Autowired
	private WorkEstimateCategoryService workEstimateCategoryService;

	/**
	 * Instantiates a new sub estimate controller.
	 */
	public SubEstimateController() {
	}

	/**
	 * {@code POST  /sub-estimates} : Create a new subEstimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateDTO the subEstimateDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new subEstimateDTO, or with status {@code 400 (Bad Request)}
	 *         if the subEstimate has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate")
	public ResponseEntity<SubEstimateDTO> createSubEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@Valid @RequestBody SubEstimateDTO subEstimateDTO) throws URISyntaxException {
		log.debug("REST request to save SubEstimate");
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}
		subEstimateDTO.setId(null);
		subEstimateDTO.setWorkEstimateId(workEstimateId);
		subEstimateDTO.setCompletedYn(false);
		SubEstimateDTO result = subEstimateService.save(subEstimateDTO);
		return ResponseEntity
				.created(new URI(
						"/v1/api/work-estimate/" + workEstimateDTO.getId() + "/sub-estimates/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						result.getId().toString()))
				.body(result);
	}

	/**
	 * Creates the sub estimate aggregate.
	 *
	 * @param subEstimateAggregateDTO the sub estimate aggregate DTO
	 * @param workEstimateId          the work estimate id
	 * @return the response entity
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate-aggregate")
	public ResponseEntity<SubEstimateAggregateDTO> createSubEstimateAggregate(
			@Valid @RequestBody SubEstimateAggregateDTO subEstimateAggregateDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws URISyntaxException {
		log.debug("REST request to Aggregate SusEstimate : {}", subEstimateAggregateDTO);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}
		SubEstimateDTO subEstimateDTO = subEstimateAggregateDTO.getSubEstimate();
		if (subEstimateDTO.getId() != null) {
			SubEstimateDTO subEstimateDTODB = subEstimateService
					.findByWorkEstimateIdAndId(workEstimateId, subEstimateDTO.getId())
					.orElseThrow(() -> new RecordNotFoundException(
							frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

			if (Utility.isValidCollection(subEstimateAggregateDTO.getDeletedWorkLocationIds())) {
				List<WorkLocationDTO> workLocationList = workLocationService.findBySubEstimateIdAndIdIn(
						subEstimateDTODB.getId(), subEstimateAggregateDTO.getDeletedWorkLocationIds());

				if (workLocationList.stream()
						.count() != new LinkedHashSet<Long>(subEstimateAggregateDTO.getDeletedWorkLocationIds())
								.stream().count()) {
					throw new BadRequestAlertException(
							frameWorkComponent.resolveI18n("subestimate.worklocation.deleteIds"), ENTITY_NAME,
							"invalidDeleteIds");
				}
			}

			List<Long> workLocationWithIds = subEstimateAggregateDTO.getWorkLocations().stream()
					.filter(overheadDTO -> overheadDTO.getId() != null).map(WorkLocationDTO::getId)
					.collect(Collectors.toList());

			if (Utility.isValidCollection(workLocationWithIds)) {
				List<WorkLocationDTO> workLocationWithIdsList = workLocationService
						.findBySubEstimateIdAndIdIn(subEstimateDTODB.getId(), workLocationWithIds);

				if (workLocationWithIdsList.stream().count() != new LinkedHashSet<Long>(workLocationWithIds).stream()
						.count()) {
					throw new BadRequestAlertException(
							frameWorkComponent.resolveI18n("subestimate.worklocation.invalid"), ENTITY_NAME,
							"invalidWorkLocations");
				}
			}

			if (Utility.isValidCollection(subEstimateAggregateDTO.getDeletedWorkLocationIds())) {

				List<Long> mappedIds = workLocationWithIds.stream().filter(withId -> subEstimateAggregateDTO
						.getDeletedWorkLocationIds().stream().anyMatch(delId -> delId.equals(withId)))
						.collect(Collectors.toList());

				if (Utility.isValidCollection(mappedIds)) {
					throw new BadRequestAlertException("subestimate.worklocation.confilct", ENTITY_NAME, "conflictIds");
				}
				subEstimateAggregateDTO.getDeletedWorkLocationIds().stream().forEach(id -> {
					workLocationService.delete(id);
				});
			}
		}

		subEstimateDTO.setWorkEstimateId(workEstimateId);
		subEstimateDTO.setCompletedYn(false);
		subEstimateDTO = subEstimateService.save(subEstimateDTO);

		Long subEstimateId = subEstimateDTO.getId();
		List<WorkLocationDTO> workLocationDTOList = new ArrayList<>();
		subEstimateAggregateDTO.getWorkLocations().stream().forEach(workLocationDTO -> {
			workLocationDTO.setSubEstimateId(subEstimateId);
			workLocationDTOList.add(workLocationService.save(workLocationDTO));
		});

		subEstimateAggregateDTO.setSubEstimate(subEstimateDTO);
		subEstimateAggregateDTO.setWorkLocations(workLocationService.getAllWorkLocationsBySubEstimateId(subEstimateId));
		subEstimateAggregateDTO.setDeletedWorkLocationIds(null);
		return ResponseEntity.created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate-aggregate"))
				.body(subEstimateAggregateDTO);
	}

	/**
	 * Gets the sub estimate aggregate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the sub estimate aggregate
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate-aggregate/{id}")
	public ResponseEntity<SubEstimateAggregateDTO> getSubEstimateAggregate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to get SubEstimate Aggregate : {}", id);
		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, id).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));
		SubEstimateAggregateDTO subEstimateAggregateDTO = new SubEstimateAggregateDTO();
		subEstimateAggregateDTO.setSubEstimate(subEstimateDTO);
		subEstimateAggregateDTO
				.setWorkLocations(workLocationService.getAllWorkLocationsBySubEstimateId(subEstimateDTO.getId()));
		subEstimateAggregateDTO.setDeletedWorkLocationIds(null);

		return ResponseEntity.ok().body(subEstimateAggregateDTO);
	}

	/**
	 * {@code PUT  /sub-estimates/:id} : Updates an existing subEstimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id of the subEstimateDTO to save.
	 * @param subEstimateDTO the subEstimateDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated subEstimateDTO, or with status {@code 400 (Bad Request)}
	 *         if the subEstimateDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the subEstimateDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{id}")
	public ResponseEntity<SubEstimateDTO> updateSubEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) final Long id,
			@Valid @RequestBody SubEstimateDTO subEstimateDTO) throws URISyntaxException {
		log.debug("REST request to update SubEstimate : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, id).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));
		SubEstimateDTO subEstimateDTOPartial = new SubEstimateDTO();
		subEstimateDTOPartial.setId(id);
		subEstimateDTOPartial.setSubEstimateName(subEstimateDTO.getSubEstimateName());
		subEstimateDTOPartial.setCompletedYn(false);
		SubEstimateDTO result = subEstimateService.partialUpdate(subEstimateDTOPartial).get();
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				subEstimateDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /sub-estimates} : get all the subEstimates.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of subEstimates in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimates-with-pagination")
	public ResponseEntity<WorkEstimateResponseDTO> getAllSubEstimates(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
		log.debug("REST request to get a page of SubEstimates");
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		Page<SubEstimateDTO> page = subEstimateService.findAllByWorkEstimateId(workEstimateId, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		WorkEstimateResponseDTO workEstimateResponseDTO = workEstimateService
				.getEstimateTotals(workEstimateDTO.getId());
		workEstimateResponseDTO.setSubEstimates(page.getContent());
		return ResponseEntity.ok().headers(headers).body(workEstimateResponseDTO);
	}

	/**
	 * Gets the all sub estimates.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the all sub estimates
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate")
	public ResponseEntity<WorkEstimateResponseDTO> getAllSubEstimates(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) {
		log.debug("REST request to get a page of SubEstimates");
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		List<SubEstimateDTO> subEstimateDTOList = subEstimateService.findAllByWorkEstimateId(workEstimateId);
		WorkEstimateResponseDTO workEstimateResponseDTO = workEstimateService
				.getEstimateTotals(workEstimateDTO.getId());
		workEstimateResponseDTO.setSubEstimates(subEstimateDTOList);
		return ResponseEntity.ok().body(workEstimateResponseDTO);
	}

	/**
	 * {@code GET  /sub-estimates/:id} : get the "id" subEstimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id of the subEstimateDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the subEstimateDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{id}")
	public ResponseEntity<SubEstimateDTO> getSubEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to get SubEstimate : {}", id);
		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, id).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		return ResponseEntity.ok().body(subEstimateDTO);
	}

	/**
	 * {@code DELETE  /sub-estimates/:id} : delete the "id" subEstimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id of the subEstimateDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{id}")
	public ResponseEntity<Void> deleteSubEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to delete SubEstimate : {}", id);
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, id).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		// TODO - Delete Dependents Datasets

		List<WorkEstimateItemDTO> workEstimateItemSORList = workEstimateItemService
				.findAllSORItems(subEstimateDTO.getId());

		workEstimateItemSORList.stream().forEach(workEstimateItemDTO -> {
			// TODO - Delete RA
			// Delete LBD
			if (workEstimateItemDTO.getLbdPerformedYn().equals(true)) {
				List<WorkEstimateItemLBDDTO> workEstimateItemLBDDTOList = workEstimateItemLBDService
						.findAllByWorkEstimateItemId(workEstimateItemDTO.getId());
				workEstimateItemLBDDTOList.stream().forEach(workEstimateItemLBD -> {
					workEstimateItemLBDService.delete(workEstimateItemLBD.getId());
				});
			}
			workEstimateItemService.delete(workEstimateItemDTO.getId());
		});

		List<WorkEstimateItemDTO> workEstimateItemNonSORList = workEstimateItemService
				.findAllNonSORItems(subEstimateDTO.getId());

		workEstimateItemNonSORList.stream().forEach(workEstimateItemDTO -> {
			// Delete LBD
			if (workEstimateItemDTO.getLbdPerformedYn().equals(true)) {
				List<WorkEstimateItemLBDDTO> workEstimateItemLBDDTOList = workEstimateItemLBDService
						.findAllByWorkEstimateItemId(workEstimateItemDTO.getId());
				workEstimateItemLBDDTOList.stream().forEach(workEstimateItemLBD -> {
					workEstimateItemLBDService.delete(workEstimateItemLBD.getId());
				});
			}
			workEstimateItemService.delete(workEstimateItemDTO.getId());
		});

		subEstimateService.delete(id);

		// TODO - Calculate Overheads
		if (Utility.isValidLong(subEstimateDTO.getWorkSubEstimateGroupId())) {
			workSubEstimateGroupService.recalculateGroupWRTSubEstimate(workEstimateId);
		}

		workEstimateService.calculateEstimateTotals(workEstimateId);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * Complete sub estimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the response entity
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{id}/complete")
	public ResponseEntity<TextResponse> completeSubEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, id).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		List<WorkEstimateItemDTO> workEstimateItemSORList = workEstimateItemService
				.findAllSORItems(subEstimateDTO.getId());

		List<WorkEstimateItemDTO> workEstimateItemNonSORList = workEstimateItemService
				.findAllNonSORItems(subEstimateDTO.getId());

		if (workEstimateItemSORList.stream().count() == 0 && workEstimateItemNonSORList.stream().count() == 0) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("subestimate.notCompleted"), ENTITY_NAME,
					"notCompleted");
		}

		workEstimateItemSORList.stream().forEach(workEstimateItemDTO -> {
			Object[] args = { workEstimateItemDTO.getItemCode() };
			customValidatorVMList.add(new CustomValidatorVM(QUANTITY, workEstimateItemDTO.getQuantity(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(),
					String.valueOf(workEstimateItemDTO.getId())));
			if (workEstimateItemDTO.getQuantity() != null) {
				customValidatorVMList.add(new CustomValidatorVM(QUANTITY, workEstimateItemDTO.getQuantity(),
						Arrays.asList(CustomValidatorConstants.PATTERN), workEstimateItemDTO.getClass().getName(),
						EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, null,
						frameworkComponent.resolveI18n("workEstimateItem.quantityPattern", args)));
			}
		});

		List<FieldErrorVM> customFieldErrorDTOList = customClientInputValidators
				.checkValidations(customValidatorVMList);
		fieldErrorDTOList.addAll(customFieldErrorDTOList);

		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}

		SubEstimateDTO subEstimateCompleteDTO = new SubEstimateDTO();
		subEstimateCompleteDTO.setId(subEstimateDTO.getId());
		subEstimateCompleteDTO.setCompletedYn(true);
		subEstimateService.partialUpdate(subEstimateCompleteDTO);

		// TODO - Calculate SubEstimate Total
		subEstimateService.calculateSubEstimateTotals(workEstimateId, subEstimateDTO.getId());
		// TODO - Calculate Overheads
		if (Utility.isValidLong(subEstimateDTO.getWorkSubEstimateGroupId())) {
			workSubEstimateGroupService.recalculateGroupWRTSubEstimate(workEstimateId);
		}
		// TODO - Calculate Estimate Total
		workEstimateService.calculateEstimateTotals(workEstimateId);
		Object[] args = { subEstimateDTO.getId() };
		return ResponseEntity.ok()
				.body(new TextResponse(frameWorkComponent.resolveI18n("subestimate.completed", args)));
	}

	/**
	 * Copy detail sub estimate.
	 *
	 * @param workEstimateId            the work estimate id
	 * @param subEstimateId             the sub estimate id
	 * @param copyWorkEstimateId        the copy work estimate id
	 * @param copyDetailSubEstimateDTOs the copy detail sub estimate DT os
	 * @return the response entity
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/copy-detail-sub-estimate")
	public ResponseEntity<TextResponse> copyDetailSubEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@RequestParam(value = "copyWorkEstimateId", required = true) Long copyWorkEstimateId,
			@RequestBody List<CopyDetailSubEstimateDTO> copyDetailSubEstimateDTOs) {

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"), ENTITY_NAME));

		List<Long> copySubEstimateIds = copyDetailSubEstimateDTOs.stream()
				.filter(copyDetailSubEstimateDTO -> copyDetailSubEstimateDTO.getSubEstimateId() != null)
				.map(CopyDetailSubEstimateDTO::getSubEstimateId).collect(Collectors.toList());

		List<SubEstimateDTO> copyDetailSubEstimates = subEstimateService
				.findAllByWorkEstimateIdAndIdInAndCompletedYn(copyWorkEstimateId, copySubEstimateIds);

		if (copyDetailSubEstimates.stream().count() != new LinkedHashSet<Long>(copySubEstimateIds).stream().count()) {
			throw new BadRequestAlertException(
					frameworkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".invalidSubEstimatesForCopy"), ENTITY_NAME,
					"invalidOverheads");
		}

		WorkEstimateCategoryDTO currentParentWorkEstimateCategory = workEstimateCategoryService
				.findOneBySubEstimateIdAndParentIdIsNull(subEstimateId);

		Map<Long, Long> subEstimatesReferenceIds = new HashMap<>();
		Map<Long, String> referenceIdsSORs = new HashMap<>();
		copyDetailSubEstimates.stream().forEach(copySubEstimateDTO -> {
			WorkEstimateCategoryDTO copyParentWorkEstimateCategory = workEstimateCategoryService
					.findOneBySubEstimateIdAndParentIdIsNull(copySubEstimateDTO.getId());
			if (currentParentWorkEstimateCategory != null && copyParentWorkEstimateCategory != null
					&& !currentParentWorkEstimateCategory.getReferenceId()
							.equals(copyParentWorkEstimateCategory.getReferenceId())) {
				Object[] args = { currentParentWorkEstimateCategory.getCategoryName() };
				throw new BadRequestAlertException(frameworkComponent.resolveI18n("workEstimateItem.selectSor", args),
						ENTITY_NAME, "selectSor");
			}
			if (copyParentWorkEstimateCategory != null) {
				subEstimatesReferenceIds.put(copyParentWorkEstimateCategory.getId(),
						copyParentWorkEstimateCategory.getReferenceId());
				referenceIdsSORs.put(copyParentWorkEstimateCategory.getReferenceId(),
						copyParentWorkEstimateCategory.getCategoryName());
			}
		});
		Set<Long> uniqueValues = new HashSet<Long>(subEstimatesReferenceIds.values());
		if (uniqueValues != null && uniqueValues.size() > 1) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".differentSOR"),
					ENTITY_NAME, "differentSOR");
		}

		WorkEstimateCategoryDTO parentWorkEstimateCategory = workEstimateCategoryService
				.findOneBySubEstimateIdAndParentIdIsNull(subEstimateId);
		if (parentWorkEstimateCategory == null && subEstimatesReferenceIds.size() == 1) {
			Iterator<Entry<Long, Long>> iterator = subEstimatesReferenceIds.entrySet().iterator();
			Entry<Long, Long> next = null;
			while (iterator.hasNext()) {
				next = iterator.next();
			}
			Optional<WorkEstimateCategoryDTO> workEstimateCategoryDTOOptional = workEstimateCategoryService
					.findOne(next.getKey());
			WorkEstimateCategoryDTO workEstimateCategoryDTO;
			if (workEstimateCategoryDTOOptional.isPresent()) {
				workEstimateCategoryDTO = workEstimateCategoryDTOOptional.get();
				WorkEstimateCategoryDTO workEstimateCategorySORDTO = new WorkEstimateCategoryDTO();
				workEstimateCategorySORDTO.setSubEstimateId(subEstimateId);
				workEstimateCategorySORDTO.setCategoryName(workEstimateCategoryDTO.getCategoryName());
				workEstimateCategorySORDTO.setParentId(workEstimateCategoryDTO.getParentId());
				workEstimateCategorySORDTO.setReferenceId(workEstimateCategoryDTO.getReferenceId());
				parentWorkEstimateCategory = workEstimateCategoryService.save(workEstimateCategorySORDTO);
			}
		}

		Long parentCategoryId = parentWorkEstimateCategory != null ? parentWorkEstimateCategory.getId() : null;

		Integer maxEntrySOR = workEstimateItemService.findMaxSOREntryOrderBySubEstimateId(subEstimateDTO.getId());
		Integer maxEntryNonSOR = workEstimateItemService.findMaxSOREntryOrderBySubEstimateId(subEstimateDTO.getId());
		AtomicInteger maxEntryOrderSOR = new AtomicInteger(maxEntrySOR != null ? maxEntrySOR : 0);
		AtomicInteger maxEntryOrderNonSOR = new AtomicInteger(maxEntryNonSOR != null ? maxEntryNonSOR : 0);
		copyDetailSubEstimateDTOs.stream().forEach(copyDetailSubEstimateDTO -> {
			Optional<SubEstimateDTO> copySubEstimateDTOOptional = subEstimateService
					.findOne(copyDetailSubEstimateDTO.getSubEstimateId());
			SubEstimateDTO copySubEstimateDTO = copySubEstimateDTOOptional.get();
			// SOR
			List<WorkEstimateItemDTO> workEstimateSORItemDTOList = workEstimateItemService
					.findAllSORItems(copySubEstimateDTO.getId());
			workEstimateSORItemDTOList.stream().forEach(workEstimateSORItemDTO -> {
				List<WorkEstimateCategoryDTO> workEstimateCategoryDTOList = workEstimateCategoryService
						.findAllBySubEstimateIdAndReferenceIdAndParentIdIsNotNull(subEstimateId,
								workEstimateSORItemDTO.getCategoryId());
				if (!Utility.isValidCollection(workEstimateCategoryDTOList)) {
					WorkEstimateCategoryDTO workEstimateCategorySORDTO = new WorkEstimateCategoryDTO();
					workEstimateCategorySORDTO.setSubEstimateId(subEstimateId);
					workEstimateCategorySORDTO.setCategoryName(workEstimateSORItemDTO.getCategoryName());
					workEstimateCategorySORDTO.setParentId(parentCategoryId);
					workEstimateCategorySORDTO.setReferenceId(workEstimateSORItemDTO.getCategoryId());
					workEstimateCategoryService.save(workEstimateCategorySORDTO);
				}

				WorkEstimateItemDTO workEstimateItemDTO = new WorkEstimateItemDTO();
				workEstimateItemDTO.setSubEstimateId(subEstimateId);
				workEstimateItemDTO.setEntryOrder(maxEntryOrderSOR.getAndIncrement());
				workEstimateItemDTO.setBaseRate(workEstimateSORItemDTO.getBaseRate());
				workEstimateItemDTO.setCategoryId(workEstimateSORItemDTO.getCategoryId());
				workEstimateItemDTO.setCategoryName(workEstimateSORItemDTO.getCategoryName());
				workEstimateItemDTO.setCatWorkSorItemId(workEstimateSORItemDTO.getCatWorkSorItemId());
				workEstimateItemDTO.setDescription(workEstimateSORItemDTO.getDescription());
				workEstimateItemDTO.setFloorNumber(workEstimateSORItemDTO.getFloorNumber());
				workEstimateItemDTO.setFloorYn(workEstimateSORItemDTO.isFloorYn());
				workEstimateItemDTO.setItemCode(workEstimateSORItemDTO.getItemCode());
				workEstimateItemDTO.setLabourRate(workEstimateSORItemDTO.getLabourRate());

				workEstimateItemDTO.setUomId(workEstimateSORItemDTO.getUomId());
				workEstimateItemDTO.setUomName(workEstimateSORItemDTO.getUomName());
				if (copyDetailSubEstimateDTO.isCopyLBD() && workEstimateSORItemDTO.getLbdPerformedYn().equals(true)) {
					copyLBD(workEstimateItemDTO.getId(), workEstimateSORItemDTO.getId());
					workEstimateItemDTO.setLbdPerformedYn(workEstimateSORItemDTO.getLbdPerformedYn());
				}
				workEstimateItemDTO.setQuantity(workEstimateSORItemDTO.getQuantity());

				if (copyDetailSubEstimateDTO.isCopyRA() && workEstimateSORItemDTO.getRaPerformedYn().equals(true)) {
					copyRA(workEstimateItemDTO.getId(), workEstimateSORItemDTO.getId());
					workEstimateItemDTO.setRaPerformedYn(workEstimateSORItemDTO.getRaPerformedYn());
				}
				workEstimateItemDTO.setFinalRate(workEstimateSORItemDTO.getFinalRate());
				workEstimateItemDTO = workEstimateItemService.save(workEstimateItemDTO);

			});
			// Non SOR
			List<WorkEstimateItemDTO> workEstimateNonSORItemDTOList = workEstimateItemService
					.findAllNonSORItems(copySubEstimateDTO.getId());
			workEstimateNonSORItemDTOList.stream().forEach(workEstimateNonSORItemDTO -> {
				WorkEstimateItemDTO workEstimateItemDTO = new WorkEstimateItemDTO();
				workEstimateItemDTO.setSubEstimateId(subEstimateId);
				workEstimateItemDTO.setEntryOrder(maxEntryOrderNonSOR.getAndIncrement());
				workEstimateItemDTO.setBaseRate(workEstimateNonSORItemDTO.getBaseRate());
				workEstimateItemDTO.setCategoryId(null);
				workEstimateItemDTO.setCategoryName(null);
				workEstimateItemDTO.setCatWorkSorItemId(null);
				workEstimateItemDTO.setDescription(workEstimateNonSORItemDTO.getDescription());
				workEstimateItemDTO.setFinalRate(workEstimateNonSORItemDTO.getFinalRate());
				workEstimateItemDTO.setItemCode(workEstimateNonSORItemDTO.getItemCode());
				workEstimateItemDTO.setLbdPerformedYn(workEstimateNonSORItemDTO.getLbdPerformedYn());
				if (copyDetailSubEstimateDTO.isCopyLBD()
						&& workEstimateNonSORItemDTO.getLbdPerformedYn().equals(true)) {
					copyLBD(workEstimateItemDTO.getId(), workEstimateNonSORItemDTO.getId());
					workEstimateItemDTO.setLbdPerformedYn(workEstimateNonSORItemDTO.getLbdPerformedYn());
				}
				workEstimateItemDTO.setQuantity(workEstimateNonSORItemDTO.getQuantity());

				workEstimateItemDTO.setUomId(workEstimateNonSORItemDTO.getUomId());
				workEstimateItemDTO.setUomName(workEstimateNonSORItemDTO.getUomName());
				workEstimateItemService.save(workEstimateItemDTO);
			});
		});

		return ResponseEntity.ok()
				.body(new TextResponse(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".copied")));
	}

	/**
	 * Copy LBD.
	 *
	 * @param sourceWorkEstimateItemId the source work estimate item id
	 * @param copyWorkEstimateItemId   the copy work estimate item id
	 */
	private void copyLBD(Long sourceWorkEstimateItemId, Long copyWorkEstimateItemId) {
		List<WorkEstimateItemLBDDTO> workEstimateItemLBDDTOList = workEstimateItemLBDService
				.findAllByWorkEstimateItemId(copyWorkEstimateItemId);
		workEstimateItemLBDDTOList.stream().forEach(workEstimateItemLBDDTOItr -> {
			WorkEstimateItemLBDDTO workEstimateItemLBDDTO = new WorkEstimateItemLBDDTO();
			workEstimateItemLBDDTO.setAdditionDeduction(workEstimateItemLBDDTOItr.getAdditionDeduction());
			workEstimateItemLBDDTO.setCalculatedYn(workEstimateItemLBDDTOItr.getCalculatedYn());
			workEstimateItemLBDDTO.setLbdBredth(workEstimateItemLBDDTOItr.getLbdBredth());
			workEstimateItemLBDDTO.setLbdBredthFormula(workEstimateItemLBDDTOItr.getLbdBredthFormula());
			workEstimateItemLBDDTO.setLbdDepth(workEstimateItemLBDDTOItr.getLbdDepth());
			workEstimateItemLBDDTO.setLbdDepthFormula(workEstimateItemLBDDTOItr.getLbdDepthFormula());
			workEstimateItemLBDDTO.setLbdLength(workEstimateItemLBDDTOItr.getLbdLength());
			workEstimateItemLBDDTO.setLbdLengthFormula(workEstimateItemLBDDTOItr.getLbdLengthFormula());
			workEstimateItemLBDDTO.setLbdNos(workEstimateItemLBDDTOItr.getLbdNos());
			workEstimateItemLBDDTO.setLbdParticulars(workEstimateItemLBDDTOItr.getLbdParticulars());
			workEstimateItemLBDDTO.setLbdQuantity(workEstimateItemLBDDTOItr.getLbdQuantity());
			workEstimateItemLBDDTO.setLbdTotal(workEstimateItemLBDDTOItr.getLbdTotal());
			workEstimateItemLBDDTO.setWorkEstimateItemId(sourceWorkEstimateItemId);
		});
	}

	/**
	 * Copy RA.
	 *
	 * @param sourceWorkEstimateItemId the source work estimate item id
	 * @param copyWorkEstimateItemId   the copy work estimate item id
	 */
	private void copyRA(Long sourceWorkEstimateItemId, Long copyWorkEstimateItemId) {

	}
}
