package com.dxc.eproc.estimate.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.EstimateServiceConstants;
import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupOverheadService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.AggregateOverheadsDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.CustomClientInputValidators;
import com.dxc.eproc.exceptionhandling.CustomMethodArgumentNotValidException;
import com.dxc.eproc.exceptionhandling.CustomValidatorConstants;
import com.dxc.eproc.exceptionhandling.CustomValidatorVM;
import com.dxc.eproc.exceptionhandling.FieldErrorVM;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead}.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class WorkSubEstimateGroupOverheadController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workSubEstimateGroupOverhead";

	/** The Constant ENTERED_VALUE. */
	private static final String ENTERED_VALUE = "enteredValue";

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupOverheadController.class);

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The work estimate repository. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The work sub estimate group service. */
	@Autowired
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	/** The work sub estimate group overhead service. */
	@Autowired
	private WorkSubEstimateGroupOverheadService workSubEstimateGroupOverheadService;

	/** The framework component. */
	@Autowired
	private FrameworkComponent frameworkComponent;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/**
	 * {@code POST  /work-sub-estimate-group-overheads} : Create a new
	 * workSubEstimateGroupOverhead.
	 *
	 * @param workSubEstimateGroupOverheadDTO the workSubEstimateGroupOverheadDTO to
	 *                                        create.
	 * @param workEstimateId                  the work estimate id
	 * @param groupId                         the group id
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workSubEstimateGroupOverheadDTO, or with status
	 *         {@code 400 (Bad Request)} if the workSubEstimateGroupOverhead has
	 *         already an ID.
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead")
	public ResponseEntity<AggregateOverheadsDTO> createWorkSubEstimateGroupOverhead(
			@Valid @RequestBody WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId) throws Exception {
		log.debug("REST request to save WorkSubEstimateGroupOverhead : {}", workSubEstimateGroupOverheadDTO);

		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();

		String errorM = "workSubEstimateGroupOverheadDTO.createWorkSubEstimateGroupOverhead.";
		workSubEstimateGroupOverheadDTO.setId(null);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		fieldErrorVMList.addAll(validateWorkEstimateGroupOverhead(workSubEstimateGroupOverheadDTO, null));

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorVMList);
		}

		workSubEstimateGroupOverheadDTO.setWorkSubEstimateGroupId(groupId);

		// Code formation
		int overheadDTOCount = 65 + workSubEstimateGroupOverheadService.countByWorkSubEstimateGroupId(groupId);
		if (overheadDTOCount <= 90) {
			char code = (char) overheadDTOCount;
			workSubEstimateGroupOverheadDTO.setCode(String.valueOf(code));
		} else {
			workSubEstimateGroupOverheadDTO.setCode("A" + (overheadDTOCount - 90));
		}

		// Calculating overhead and construct
		if (workSubEstimateGroupOverheadDTO.getValueFixedYn().equals(false)) {
			if (CollectionUtils.isEmpty(workSubEstimateGroupOverheadDTO.getSelectedOverheads())) {
				throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "noOverheadSelected"),
						ENTITY_NAME, "noOverheadSelected");
			}
			List<WorkSubEstimateGroupOverheadDTO> overheadDTOList = workSubEstimateGroupOverheadService
					.findByWorkSubEstimateGroupIdAndIdIn(groupId,
							workSubEstimateGroupOverheadDTO.getSelectedOverheads());

			if (overheadDTOList.stream()
					.count() != new LinkedHashSet<Long>(workSubEstimateGroupOverheadDTO.getSelectedOverheads()).stream()
							.count()) {
				throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "invalidOverheads"),
						ENTITY_NAME, "invalidOverheads");
			}
			workSubEstimateGroupOverheadService.calculatePercentageOverhead(workSubEstimateGroupOverheadDTO,
					overheadDTOList, groupId);
			workSubEstimateGroupOverheadDTO.setSelectedOverheads(
					new ArrayList<>(new LinkedHashSet<Long>(workSubEstimateGroupOverheadDTO.getSelectedOverheads())));
		} else {
			workSubEstimateGroupOverheadDTO.setOverheadValue(workSubEstimateGroupOverheadDTO.getEnteredValue());
			workSubEstimateGroupOverheadDTO.setConstruct("(Fixed)");
		}
		workSubEstimateGroupOverheadDTO.setValueType(ValueType.ADDED);
		List<Long> selectedOverheads = workSubEstimateGroupOverheadDTO.getSelectedOverheads();
		workSubEstimateGroupOverheadDTO = workSubEstimateGroupOverheadService.save(workSubEstimateGroupOverheadDTO);

		if (workEstimateDTO.getGroupOverheadTotal() == null) {
			workEstimateDTO.setGroupOverheadTotal(new BigDecimal(0));
		}
		workSubEstimateGroupOverheadService.recalculateAndUpdateWRTOverhead(workEstimateId, groupId);

		workSubEstimateGroupOverheadDTO.setSelectedOverheads(selectedOverheads);

		AggregateOverheadsDTO aggregateDTO = calculateOverHeadTotals(groupId);
		Page<WorkSubEstimateGroupOverheadDTO> overheadDTOPage = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupId(groupId, null);
		aggregateDTO.setOverheads(overheadDTOPage.getContent());
		return ResponseEntity
				.created(new URI("/api/work-sub-estimate-group-overheads/" + workSubEstimateGroupOverheadDTO.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						workSubEstimateGroupOverheadDTO.getId().toString()))
				.body(aggregateDTO);
	}

	/**
	 * Calculate over head totals.
	 *
	 * @param groupId the group id
	 * @return the aggregate overheads DTO
	 */
	private AggregateOverheadsDTO calculateOverHeadTotals(Long groupId) {
		BigDecimal addedOverheadsValue = workSubEstimateGroupOverheadService
				.sumAddedOverheadValueByGroupIdAndFinalYnTrue(groupId);
		if (addedOverheadsValue == null) {
			addedOverheadsValue = BigDecimal.ZERO;
		}

		BigDecimal otherOverheadValue = workSubEstimateGroupOverheadService.sumOtherOverheadValueByGroupId(groupId);

		BigDecimal totalCostOfWork = addedOverheadsValue.add(otherOverheadValue);
		AggregateOverheadsDTO aggregateDTO = new AggregateOverheadsDTO();

		aggregateDTO.setTotalCostOfWork(totalCostOfWork);
		aggregateDTO.setTotalOverheadsValue(addedOverheadsValue);
		return aggregateDTO;
	}

	/**
	 * Validate work estimate group overhead.
	 *
	 * @param workSubEstimateGroupOverheadDTO the work sub estimate group overhead
	 *                                        DTO
	 * @param index                           the index
	 * @return the list
	 */
	private List<FieldErrorVM> validateWorkEstimateGroupOverhead(
			WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO, String index) {
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

		if (workSubEstimateGroupOverheadDTO.getValueFixedYn().equals(true)) {
			customValidatorVMList.add(new CustomValidatorVM(ENTERED_VALUE,
					workSubEstimateGroupOverheadDTO.getEnteredValue(), Arrays.asList(CustomValidatorConstants.PATTERN),
					workSubEstimateGroupOverheadDTO.getClass().getName(),
					EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, null,
					frameworkComponent.resolveI18n("workSubEstimateGroupOverhead.enteredValue.pattern")));
		} else if (workSubEstimateGroupOverheadDTO.getValueFixedYn().equals(false)) {
			customValidatorVMList.add(new CustomValidatorVM(ENTERED_VALUE,
					workSubEstimateGroupOverheadDTO.getEnteredValue(), Arrays.asList(CustomValidatorConstants.PATTERN),
					workSubEstimateGroupOverheadDTO.getClass().getName(), EstimateServiceConstants.PERCENTAGE_PATTERN,
					null, frameworkComponent.resolveI18n("workSubEstimateGroupOverhead.enteredValue.pattern")));
		}
		return customClientInputValidators.checkValidations(customValidatorVMList);
	}

	/**
	 * {@code PATCH  /work-sub-estimate-group-overheads/:id} : Partial updates given
	 * fields of an existing workSubEstimateGroupOverhead, field will ignore if it
	 * is null.
	 *
	 * @param workSubEstimateGroupOverheadDTO the workSubEstimateGroupOverheadDTO to
	 *                                        update.
	 * @param workEstimateId                  the work estimate id
	 * @param groupId                         the group id
	 * @param id                              the id of the
	 *                                        workSubEstimateGroupOverheadDTO to
	 *                                        save.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workSubEstimateGroupOverheadDTO, or with status
	 *         {@code 400 (Bad Request)} if the workSubEstimateGroupOverheadDTO is
	 *         not valid, or with status {@code 404 (Not Found)} if the
	 *         workSubEstimateGroupOverheadDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the
	 *         workSubEstimateGroupOverheadDTO couldn't be updated.
	 * @throws Exception the exception
	 */
	@PutMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}")
	public ResponseEntity<AggregateOverheadsDTO> updateWorkSubEstimateGroupOverhead(
			@Valid @RequestBody WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to update WorkSubEstimateGroupOverhead : {}, {}", id, workSubEstimateGroupOverheadDTO);
		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();

		String errorM = "workSubEstimateGroupOverheadDTO.updateWorkSubEstimateGroupOverhead.";
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		fieldErrorVMList.addAll(validateWorkEstimateGroupOverhead(workSubEstimateGroupOverheadDTO, null));

		if (Utility.isValidCollection(fieldErrorVMList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorVMList);
		}

		WorkSubEstimateGroupOverheadDTO dbOverheadDTO = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndIdAndValueType(groupId, id, ValueType.ADDED).orElseThrow(
						() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidOverheadId"),
								ENTITY_NAME));

		// check if its code is already in construct of another
		workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(groupId, ValueType.ADDED)
				.forEach(overhead -> {
					if (overhead.getConstruct().contains(dbOverheadDTO.getCode())) {
						throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "overheadLinked"),
								ENTITY_NAME, "overheadLinked");
					}
				});

		WorkSubEstimateGroupOverheadDTO finalDTO = new WorkSubEstimateGroupOverheadDTO();
		finalDTO.setId(id);
		finalDTO.setFinalYn(workSubEstimateGroupOverheadDTO.getFinalYn());
		// Calculating overhead and construct
		if (workSubEstimateGroupOverheadDTO.getValueFixedYn().equals(false)) {
			if (CollectionUtils.isEmpty(workSubEstimateGroupOverheadDTO.getSelectedOverheads())) {
				throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "noOverheadSelected"),
						ENTITY_NAME, "noOverheadSelected");
			}
			List<WorkSubEstimateGroupOverheadDTO> overheadDTOList = workSubEstimateGroupOverheadService
					.findByWorkSubEstimateGroupIdAndIdIn(groupId,
							workSubEstimateGroupOverheadDTO.getSelectedOverheads())
					.stream().filter(overheadDTO -> overheadDTO.getId() != id).collect(Collectors.toList());

			if (overheadDTOList.stream()
					.count() != new LinkedHashSet<Long>(workSubEstimateGroupOverheadDTO.getSelectedOverheads()).stream()
							.count()) {
				throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "invalidOverheads"),
						ENTITY_NAME, "invalidOverheads");
			}
			finalDTO.setSelectedOverheads(
					new ArrayList<>(new LinkedHashSet<Long>(workSubEstimateGroupOverheadDTO.getSelectedOverheads())));
			finalDTO.setEnteredValue(workSubEstimateGroupOverheadDTO.getEnteredValue());
			finalDTO.setValueFixedYn(false);
			workSubEstimateGroupOverheadService.calculatePercentageOverhead(finalDTO, overheadDTOList, groupId);
		} else {
			finalDTO.setOverheadValue(workSubEstimateGroupOverheadDTO.getEnteredValue());
			finalDTO.setConstruct("(Fixed)");
			finalDTO.setValueFixedYn(true);
		}

		List<Long> selectedOverheads = finalDTO.getSelectedOverheads();

		finalDTO = workSubEstimateGroupOverheadService.partialUpdate(finalDTO).get();

		workSubEstimateGroupOverheadService.recalculateAndUpdateWRTOverhead(workEstimateId, groupId);

		finalDTO.setSelectedOverheads(selectedOverheads);

		AggregateOverheadsDTO aggregateDTO = calculateOverHeadTotals(groupId);
		Page<WorkSubEstimateGroupOverheadDTO> overheadDTOPage = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupId(groupId, null);
		aggregateDTO.setOverheads(overheadDTOPage.getContent());

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, finalDTO.getId().toString()))
				.body(aggregateDTO);
	}

	/**
	 * {@code GET  /work-sub-estimate-group-overheads} : get all the
	 * workSubEstimateGroupOverheads.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 * @param pageable       the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workSubEstimateGroupOverheads in body.
	 * @throws Exception the exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead")
	public ResponseEntity<AggregateOverheadsDTO> getAllWorkSubEstimateGroupOverheads(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId, Pageable pageable) throws Exception {
		log.debug("REST request to get a page of WorkSubEstimateGroupOverheads");

		String errorM = "workSubEstimateGroupOverheadDTO.getAllWorkSubEstimateGroupOverheads.";
		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"), ENTITY_NAME));

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		Page<WorkSubEstimateGroupOverheadDTO> overheadDTOPage = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupId(groupId, pageable);

		if (overheadDTOPage.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "noOverheadsPresent"),
					ENTITY_NAME);
		}

		AggregateOverheadsDTO aggregateDTO = calculateOverHeadTotals(groupId);
		aggregateDTO.setOverheads(overheadDTOPage.getContent());

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), overheadDTOPage);
		return ResponseEntity.ok().headers(headers).body(aggregateDTO);
	}

	/**
	 * {@code GET  /work-sub-estimate-group-overheads/:id} : get the "id"
	 * workSubEstimateGroupOverhead.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 * @param id             the id of the workSubEstimateGroupOverheadDTO to
	 *                       retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workSubEstimateGroupOverheadDTO, or with status
	 *         {@code 404 (Not Found)}.
	 * @throws Exception the exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}")
	public ResponseEntity<WorkSubEstimateGroupOverheadDTO> getWorkSubEstimateGroupOverhead(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to get WorkSubEstimateGroupOverhead : {}", id);

		String errorM = "workSubEstimateGroupOverheadDTO.getWorkSubEstimateGroupOverhead.";
		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"), ENTITY_NAME));

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndId(groupId, id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(errorM + "invalidOverheadId"), ENTITY_NAME));
		return ResponseUtil.wrapOrNotFound(Optional.of(workSubEstimateGroupOverheadDTO));
	}

	/**
	 * {@code DELETE  /work-sub-estimate-group-overheads/:id} : delete the "id"
	 * workSubEstimateGroupOverhead.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 * @param id             the id of the workSubEstimateGroupOverheadDTO to
	 *                       delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 * @throws Exception the exception
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{groupId}/overhead/{id}")
	public ResponseEntity<AggregateOverheadsDTO> deleteWorkSubEstimateGroupOverhead(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "groupId", required = true) Long groupId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to delete WorkSubEstimateGroupOverhead : {}", id);

		String errorM = "workSubEstimateGroupOverheadDTO.deleteWorkSubEstimateGroupOverhead.";
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, groupId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		WorkSubEstimateGroupOverheadDTO dbOverheadDTO = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndIdAndValueType(groupId, id, ValueType.ADDED).orElseThrow(
						() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidOverheadId"),
								ENTITY_NAME));

		// check if its code is already in construct of another
		workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(groupId, ValueType.ADDED)
				.forEach(overhead -> {
					if (overhead.getConstruct().contains(dbOverheadDTO.getCode())) {
						throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "overheadLinked"),
								ENTITY_NAME, "overheadLinked");
					}
				});

		workSubEstimateGroupOverheadService.delete(id);

		workSubEstimateGroupOverheadService.recalculateAndUpdateWRTOverhead(workEstimateId, groupId);

		AggregateOverheadsDTO aggregateDTO = calculateOverHeadTotals(groupId);
		Page<WorkSubEstimateGroupOverheadDTO> overheadDTOPage = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupId(groupId, null);
		aggregateDTO.setOverheads(overheadDTOPage.getContent());

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.body(aggregateDTO);
	}
}
