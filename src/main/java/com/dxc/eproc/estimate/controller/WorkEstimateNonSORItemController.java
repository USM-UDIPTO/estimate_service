package com.dxc.eproc.estimate.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.EstimateServiceConstants;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.response.dto.SubEstimateResponseDTO;
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
import com.dxc.eproc.utils.PaginationUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimateItem}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateNonSORItemController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workEstimateItem";

	/** The Constant QUANTITY. */
	private static final String QUANTITY = "quantity";

	/** The Constant BASE_RATE. */
	private static final String BASE_RATE = "baseRate";

	/** The Constant ITEM_CODE. */
	private static final String ITEM_CODE = "itemCode";

	/** The Constant UOM_ID. */
	private static final String UOM_ID = "uomId";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "description";

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";

	/** The Constant ITEM_CODE_INDEX. */
	private static final int ITEM_CODE_INDEX = 0;

	/** The Constant DESCRIPTION_INDEX. */
	private static final int DESCRIPTION_INDEX = 1;

	/** The Constant QUANTITY_INDEX. */
	private static final int QUANTITY_INDEX = 3;

	/** The Constant RATE_INDEX. */
	private static final int RATE_INDEX = 4;

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateNonSORItemController.class);

	/** The work estimate service. */
	private final WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	private final SubEstimateService subEstimateService;

	/** The work estimate item service. */
	private final WorkEstimateItemService workEstimateItemService;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/** The work estimate item LBD service. */
	@Autowired
	private WorkEstimateItemLBDService workEstimateItemLBDService;

	/**
	 * Instantiates a new work estimate item controller.
	 *
	 * @param workEstimateService     the work estimate service
	 * @param subEstimateService      the sub estimate service
	 * @param workEstimateItemService the work estimate item service
	 * @param frameworkComponent      the framework component
	 */
	public WorkEstimateNonSORItemController(WorkEstimateService workEstimateService,
			SubEstimateService subEstimateService, WorkEstimateItemService workEstimateItemService,
			FrameworkComponent frameworkComponent) {
		this.workEstimateService = workEstimateService;
		this.subEstimateService = subEstimateService;
		this.workEstimateItemService = workEstimateItemService;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items} :
	 * Create a new workEstimateItem.
	 *
	 * @param workEstimateId      the work estimate id
	 * @param subEstimateId       the sub estimate id
	 * @param workEstimateItemDTO the workEstimateItemDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateItemDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateItem has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items")
	public ResponseEntity<SubEstimateResponseDTO> createWorkEstimateNonSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@Valid @RequestBody WorkEstimateItemDTO workEstimateItemDTO) throws URISyntaxException {
		log.debug("REST request to save WorkEstimateItem : {}", workEstimateItemDTO);

		SubEstimateResponseDTO workEstimateNonSORItemResponseDTO = new SubEstimateResponseDTO();

		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();

		WorkEstimateDTO workEstimateDTO = null;
		SubEstimateDTO subEstimateDTO;

		workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		if (workEstimateDTO.getStatus() != WorkEstimateStatus.DRAFT
				&& workEstimateDTO.getStatus() != WorkEstimateStatus.INITIAL) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"),
					"WorkEstimate", "InvalidStatus");
		}

		subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		fieldErrorDTOList.addAll(validateWorkEstimateNonSORItem(workEstimateItemDTO, null));
		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}
		workEstimateItemDTO.setSubEstimateId(subEstimateDTO.getId());
		workEstimateItemDTO.setCategoryId(null);
		workEstimateItemDTO.setCatWorkSorItemId(null);
		workEstimateItemDTO.setLbdPerformedYn(false);
		workEstimateItemDTO.setRaPerformedYn(false);
		workEstimateItemDTO = workEstimateItemService.save(workEstimateItemDTO);

		workEstimateNonSORItemResponseDTO = subEstimateService.calculateSubEstimateTotals(workEstimateId,
				subEstimateId);

		workEstimateNonSORItemResponseDTO.setItems(workEstimateItemService.findAllNonSORItems(subEstimateId));

		return ResponseEntity
				.created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/" + subEstimateId
						+ "/non-sor-items"))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						"number of items inserted: " + 1))
				.body(workEstimateNonSORItemResponseDTO);
	}

	/**
	 * Validate work estimate non SOR item.
	 *
	 * @param workEstimateItemDTO the work estimate item DTO
	 * @param index               the index
	 * @return the list
	 */
	private List<FieldErrorVM> validateWorkEstimateNonSORItem(WorkEstimateItemDTO workEstimateItemDTO, String index) {
		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

		customValidatorVMList.add(new CustomValidatorVM(BASE_RATE, workEstimateItemDTO.getBaseRate(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		if (workEstimateItemDTO.getBaseRate() != null) {
			customValidatorVMList.add(new CustomValidatorVM(BASE_RATE, workEstimateItemDTO.getBaseRate(),
					Arrays.asList(CustomValidatorConstants.PATTERN), workEstimateItemDTO.getClass().getName(),
					EstimateServiceConstants.BASE_RATE_PATTERN, index,
					frameworkComponent.resolveI18n("workEstimateItem.baseRatePattern")));
		}

		customValidatorVMList.add(new CustomValidatorVM(QUANTITY, workEstimateItemDTO.getQuantity(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		if (workEstimateItemDTO.getBaseRate() != null) {
			customValidatorVMList.add(new CustomValidatorVM(QUANTITY, workEstimateItemDTO.getQuantity(),
					Arrays.asList(CustomValidatorConstants.PATTERN), workEstimateItemDTO.getClass().getName(),
					EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, index,
					frameworkComponent.resolveI18n("workEstimateItem.QuantityPattern")));
		}

		customValidatorVMList.add(new CustomValidatorVM(ITEM_CODE, workEstimateItemDTO.getItemCode(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		if (workEstimateItemDTO.getItemCode() != null) {
			customValidatorVMList.add(new CustomValidatorVM(ITEM_CODE, workEstimateItemDTO.getItemCode(),
					Arrays.asList(CustomValidatorConstants.PATTERN), workEstimateItemDTO.getClass().getName(),
					EstimateServiceConstants.SR_ITEM_CODE, index,
					frameworkComponent.resolveI18n("workEstimateItem.itemCodePattern")));
		}

		customValidatorVMList.add(new CustomValidatorVM(UOM_ID, workEstimateItemDTO.getUomId(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		customValidatorVMList.add(new CustomValidatorVM(DESCRIPTION, workEstimateItemDTO.getDescription(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		if (workEstimateItemDTO.getDescription() != null) {
			customValidatorVMList.add(new CustomValidatorVM(DESCRIPTION, workEstimateItemDTO.getDescription(),
					Arrays.asList(CustomValidatorConstants.PATTERN), workEstimateItemDTO.getClass().getName(),
					EstimateServiceConstants.WORKESTIMATE_ITEM_DESCRIPTION, index,
					frameworkComponent.resolveI18n("workEstimateItem.descriptionPattern")));
		}

		List<FieldErrorVM> fieldErrorDTOList = customClientInputValidators.checkValidations(customValidatorVMList);
		fieldErrorVMList.addAll(fieldErrorDTOList);

		return fieldErrorVMList;
	}

	/**
	 * {@code PUT
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items/:id}
	 * : Updates an existing workEstimateItem.
	 *
	 * @param workEstimateId      the work estimate id
	 * @param subEstimateId       the sub estimate id
	 * @param id                  the id of the workEstimateItemDTO to save.
	 * @param workEstimateItemDTO the workEstimateItemDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateItemDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateItemDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateItemDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items/{id}")
	public ResponseEntity<SubEstimateResponseDTO> updateWorkEstimateNonSORItem(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) final Long id,
			@Valid @RequestBody WorkEstimateItemDTO workEstimateItemDTO) throws URISyntaxException {
		log.debug("REST request to update WorkEstimateItem : {}, {}", id, workEstimateItemDTO);

		WorkEstimateDTO workEstimateDTO = null;
		SubEstimateDTO subEstimateDTO;
		WorkEstimateItemDTO workEstimateItemDTODB;

		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();

		SubEstimateResponseDTO workEstimateNonSORItemResponseDTO = new SubEstimateResponseDTO();

		workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		if (workEstimateDTO.getStatus() != WorkEstimateStatus.DRAFT
				&& workEstimateDTO.getStatus() != WorkEstimateStatus.INITIAL) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"),
					"WorkEstimate", "InvalidStatus");
		}

		subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		workEstimateItemDTODB = workEstimateItemService.findBySubEstimateIdAndId(subEstimateDTO.getId(), id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + id, ENTITY_NAME));

		fieldErrorDTOList.addAll(validateWorkEstimateNonSORItem(workEstimateItemDTO, null));
		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}
		workEstimateItemDTO.setId(workEstimateItemDTODB.getId());
		workEstimateItemDTO.setSubEstimateId(subEstimateDTO.getId());
		workEstimateItemDTO.setCategoryId(null);
		workEstimateItemDTO.setCatWorkSorItemId(null);
		workEstimateItemService.partialUpdate(workEstimateItemDTO);

		workEstimateNonSORItemResponseDTO = subEstimateService.calculateSubEstimateTotals(workEstimateId,
				subEstimateId);
		workEstimateNonSORItemResponseDTO.setItems(workEstimateItemService.findAllNonSORItems(subEstimateId));

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workEstimateItemDTO.getId().toString())).body(workEstimateNonSORItemResponseDTO);
	}

	/**
	 * {@code PATCH
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items/:id}
	 * : Partial updates given fields of an existing workEstimateItem, field will
	 * ignore if it is null.
	 *
	 * @param workEstimateId      the work estimate id
	 * @param subEstimateId       the sub estimate id
	 * @param id                  the id of the workEstimateItemDTO to save.
	 * @param workEstimateItemDTO the workEstimateItemDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateItemDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateItemDTO is not valid, or
	 *         with status {@code 404 (Not Found)} if the workEstimateItemDTO is not
	 *         found, or with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateItemDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<WorkEstimateItemDTO> partialUpdateWorkEstimateNonSORItem(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) final Long id,
			@NotNull @RequestBody WorkEstimateItemDTO workEstimateItemDTO) throws URISyntaxException {
		log.debug("REST request to partial update WorkEstimateItem partially : {}, {}", id, workEstimateItemDTO);

		WorkEstimateDTO workEstimateDTO = null;
		SubEstimateDTO subEstimateDTO;
		WorkEstimateItemDTO workEstimateItemDTODB;

		workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		if (workEstimateDTO.getStatus() != WorkEstimateStatus.DRAFT
				&& workEstimateDTO.getStatus() != WorkEstimateStatus.INITIAL) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"),
					"WorkEstimate", "InvalidStatus");
		}

		subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		workEstimateItemDTODB = workEstimateItemService.findBySubEstimateIdAndId(subEstimateDTO.getId(), id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + id, ENTITY_NAME));

		workEstimateItemDTO.setId(workEstimateItemDTODB.getId());
		Optional<WorkEstimateItemDTO> result = workEstimateItemService.partialUpdate(workEstimateItemDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, workEstimateItemDTO.getId().toString()));
	}

	/**
	 * {@code GET
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items} :
	 * get all the workEstimateItems.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param pageable       the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateItems in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items-with-pagination")
	public ResponseEntity<SubEstimateResponseDTO> getAllWorkEstimateNonSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId, Pageable pageable) {
		log.debug("REST request to get a page of WorkEstimateItems");

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		SubEstimateDTO subEstimateDTO = subEstimateService
				.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		Page<WorkEstimateItemDTO> page = workEstimateItemService.findAllNonSORItems(subEstimateDTO.getId(), pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		SubEstimateResponseDTO workEstimateSORItemResponseDTO = subEstimateService
				.getSubEstimateTotals(workEstimateDTO.getId(), subEstimateDTO.getId());
		workEstimateSORItemResponseDTO.setItems(page.getContent());
		return ResponseEntity.ok().headers(headers).body(workEstimateSORItemResponseDTO);
	}

	/**
	 * Gets the all work estimate non SOR items.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @return the all work estimate non SOR items
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items")
	public ResponseEntity<SubEstimateResponseDTO> getAllWorkEstimateNonSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId) {
		log.debug("REST request to get a page of WorkEstimateItems");

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		SubEstimateDTO subEstimateDTO = subEstimateService
				.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		List<WorkEstimateItemDTO> workEstimateItemDTOList = workEstimateItemService
				.findAllNonSORItems(subEstimateDTO.getId());
		SubEstimateResponseDTO workEstimateSORItemResponseDTO = subEstimateService
				.getSubEstimateTotals(workEstimateDTO.getId(), subEstimateDTO.getId());
		workEstimateSORItemResponseDTO.setItems(workEstimateItemDTOList);
		return ResponseEntity.ok().body(workEstimateSORItemResponseDTO);
	}

	/**
	 * {@code GET
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items/:id}
	 * : get the "id" workEstimateItem.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param id             the id of the workEstimateItemDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateItemDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items/{id}")
	public ResponseEntity<WorkEstimateItemDTO> getWorkEstimateNonSORItem(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to get WorkEstimateItem : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		SubEstimateDTO subEstimateDTO = subEstimateService
				.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		Optional<WorkEstimateItemDTO> workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateDTO.getId(), id);
		return ResponseUtil.wrapOrNotFound(workEstimateItemDTO);
	}

	/**
	 * {@code DELETE
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items/:id}
	 * : delete the "id" workEstimateItem.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param id             the id of the workEstimateItemDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/non-sor-items/{id}")
	public ResponseEntity<SubEstimateResponseDTO> deleteWorkEstimateNonSORItem(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to delete WorkEstimateItem : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		SubEstimateDTO subEstimateDTO = subEstimateService
				.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		WorkEstimateItemDTO workEstimateItemDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateDTO.getId(), id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + id, ENTITY_NAME));

		// Delete LBD
		if (workEstimateItemDTO.getLbdPerformedYn().equals(true)) {
			List<WorkEstimateItemLBDDTO> workEstimateItemLBDDTOList = workEstimateItemLBDService
					.findAllByWorkEstimateItemId(workEstimateItemDTO.getId());
			workEstimateItemLBDDTOList.stream().forEach(workEstimateItemLBD -> {
				workEstimateItemLBDService.delete(workEstimateItemLBD.getId());
				workEstimateItemLBDService.recalculateAndUpdateItemWRTLBD(workEstimateId, subEstimateId,
						workEstimateItemDTO.getId());
			});
		}
		// TODO - WorkEstimateItem Category
		workEstimateItemService.delete(workEstimateItemDTO.getId());
		subEstimateService.calculateSubEstimateTotals(workEstimateId, subEstimateId);
		List<WorkEstimateItemDTO> workEstimateItemDTOList = workEstimateItemService
				.findAllNonSORItems(subEstimateDTO.getId());
		SubEstimateResponseDTO workEstimateSORItemResponseDTO = subEstimateService
				.getSubEstimateTotals(workEstimateDTO.getId(), subEstimateDTO.getId());
		workEstimateSORItemResponseDTO.setItems(workEstimateItemDTOList);
		return ResponseEntity.ok().body(workEstimateSORItemResponseDTO);
	}

	/**
	 * Upload non sor items.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param file           the file
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/upload-non-sor-items")
	public ResponseEntity<SubEstimateResponseDTO> uploadNonSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@RequestParam(name = "file", required = true) MultipartFile file) throws Exception {
		log.debug("REST request to upload non sor items : {}", workEstimateId, subEstimateId);
		SubEstimateResponseDTO subEstimateResponseDTO = new SubEstimateResponseDTO();

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		if (workEstimateDTO.getStatus() != WorkEstimateStatus.DRAFT
				&& workEstimateDTO.getStatus() != WorkEstimateStatus.INITIAL) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"),
					"WorkEstimate", "InvalidStatus");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		HSSFWorkbook myWorkBook = new HSSFWorkbook(file.getInputStream());
		List<WorkEstimateItemDTO> workEstimateItemDTOs = new ArrayList<>();
		List<FieldErrorVM> fieldErrorsInNonSORItems = new ArrayList<>();

		final Integer TOTALCOLUMNS = 5;

		for (int index = 0; index < myWorkBook.getNumberOfSheets(); index++) {
			for (Row row : myWorkBook.getSheetAt(index)) {
				if (isValidRow(row, TOTALCOLUMNS)) {
					if (row.getRowNum() > 0) {
						WorkEstimateItemDTO workEstimateItemDTO = getWorkEstimateDTO(row);
						workEstimateItemDTO.setSubEstimateId(subEstimateDTO.getId());
						workEstimateItemDTO.setCategoryId(null);
						workEstimateItemDTO.setCatWorkSorItemId(null);
						// TODO: need to work on uom_id
						workEstimateItemDTO.setUomId(1L);
						workEstimateItemDTO.setUomName("KG");
						workEstimateItemDTO.setLbdPerformedYn(false);
						workEstimateItemDTO.setRaPerformedYn(false);

						fieldErrorsInNonSORItems.addAll(
								validateWorkEstimateNonSORItem(workEstimateItemDTO, "RowNum-" + row.getRowNum()));
						if (CollectionUtils.isEmpty(fieldErrorsInNonSORItems)) {
							workEstimateItemDTOs.add(workEstimateItemDTO);
						}
					}
				} else {
					throw new BadRequestAlertException(
							frameworkComponent
									.resolveI18n("validation.workEstimateItem.uploadNonSORItem.invalidTemplate"),
							ENTITY_NAME, "incorrectTemplate");
				}
			}
		}
		if (!CollectionUtils.isEmpty(fieldErrorsInNonSORItems)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION),
					fieldErrorsInNonSORItems);
		}

		if (workEstimateItemDTOs.isEmpty()) {
			throw new RecordNotFoundException(
					frameworkComponent.resolveI18n("validation.workEstimateItem.uploadNonSORItem.notPrepared") + " - "
							+ workEstimateItemDTOs,
					"NotPrepared");
		}

		BigDecimal excelNonSORTotal = BigDecimal.ZERO;
		for (WorkEstimateItemDTO workEstimateItemDTO : workEstimateItemDTOs) {
			excelNonSORTotal = excelNonSORTotal
					.add(workEstimateItemDTO.getBaseRate().multiply(workEstimateItemDTO.getQuantity()));
		}
		subEstimateResponseDTO.setSubEstimateNonSORTotal(excelNonSORTotal);
		subEstimateResponseDTO.setWorkEstimateId(workEstimateId);
		subEstimateResponseDTO.setSubEstimateId(subEstimateId);
		subEstimateResponseDTO.setItems(workEstimateItemDTOs);

		return ResponseEntity.created(new URI(
				"/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/" + subEstimateId + "/upload-non-sor-items"))
				.body(subEstimateResponseDTO);

	}

	/**
	 * Save uploaded non-sor-items.
	 *
	 * @param workEstimateId       the work estimate id
	 * @param subEstimateId        the sub estimate id
	 * @param workEstimateItemDTOs the work estimate item dtos
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/save-uploaded-non-sor-items")
	public ResponseEntity<SubEstimateResponseDTO> saveUploadedNonSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@RequestBody @Valid List<WorkEstimateItemDTO> workEstimateItemDTOs) throws Exception {
		log.debug("REST request to Save Uploaded NonSORItems : {} {} {}", workEstimateId, subEstimateId);
		SubEstimateResponseDTO workEstimateNonSORItemResponseDTO = new SubEstimateResponseDTO();

		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		if (workEstimateDTO.getStatus() != WorkEstimateStatus.DRAFT
				&& workEstimateDTO.getStatus() != WorkEstimateStatus.INITIAL) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"),
					"WorkEstimate", "InvalidStatus");
		}

		SubEstimateDTO subEstimateDTO = subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		if (workEstimateItemDTOs.size() == 0) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("workEstimateItem.emptyItems"),
					ENTITY_NAME, "NoInputs");
		}

		AtomicInteger index = new AtomicInteger();
		workEstimateItemDTOs.stream().forEach(workEstimateItemDTO -> {
			fieldErrorDTOList.addAll(
					validateWorkEstimateNonSORItem(workEstimateItemDTO, String.valueOf(index.incrementAndGet())));
		});

		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}

		Integer maxEntry = workEstimateItemService.findMaxSOREntryOrderBySubEstimateId(subEstimateDTO.getId());
		AtomicInteger maxEntryOrder = new AtomicInteger(maxEntry != null ? maxEntry : 0);

		workEstimateItemDTOs = workEstimateItemDTOs.stream().map(workEstimateItemDTO -> {
			workEstimateItemDTO.setSubEstimateId(subEstimateDTO.getId());
			workEstimateItemDTO.setCategoryId(null);
			workEstimateItemDTO.setCatWorkSorItemId(null);
			workEstimateItemDTO.setLbdPerformedYn(false);
			workEstimateItemDTO.setRaPerformedYn(false);

			workEstimateItemDTO.setEntryOrder(maxEntryOrder.incrementAndGet());
			workEstimateItemDTO = workEstimateItemService.save(workEstimateItemDTO);

			return workEstimateItemDTO;
		}).collect(Collectors.toList());
		List<WorkEstimateItemDTO> workEstimateItemDTOList = workEstimateItemService
				.findAllNonSORItems(subEstimateDTO.getId());
		workEstimateNonSORItemResponseDTO = subEstimateService.calculateSubEstimateTotals(workEstimateId,
				subEstimateId);
		workEstimateNonSORItemResponseDTO.setItems(workEstimateItemDTOList);
		return ResponseEntity.created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/"
				+ subEstimateId + "/save-uploaded-non-sor-items")).body(workEstimateNonSORItemResponseDTO);
	}

	/**
	 * Download object.
	 *
	 * @param template the template
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping("/work-estimate/download-non-sor-template")
	public ResponseEntity<ByteArrayResource> downloadObject(
			@Value("classpath:/templates/NonSOR_Template_Download.xls") Resource template) throws IOException {
		byte[] data = org.springframework.util.FileCopyUtils.copyToByteArray(template.getFile());

		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + "NonSOR_Template.xls" + "\"").body(resource);

	}

	/**
	 * Checks if is valid row.
	 *
	 * @param currentRow   the current row
	 * @param totalColumns the total columns
	 * @return true, if is valid row
	 */
	private boolean isValidRow(Row currentRow, Integer totalColumns) {
		int lastColumn = totalColumns - 1;

		if (currentRow == null) {
			return false;
		}
		if (currentRow.getLastCellNum() < lastColumn) {
			return false;
		}

		/* check validation for each header element */
		if (isFirstRow(currentRow)) {
			for (int nextColumn = 0; nextColumn < totalColumns; nextColumn++) {
				if (!isValidTextHeader(currentRow, nextColumn)) {
					return false;
				}
				if (!isValidRequiredHeader(currentRow, nextColumn)) {
					return false;
				}
			}
		} else {
			/* check validation for each input data */
			for (int nextColumn = 0; nextColumn < totalColumns; nextColumn++) {
				if (!isValidInputData(currentRow, nextColumn)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Checks if is first row.
	 *
	 * @param currentRow the current row
	 * @return true, if is first row
	 */
	private boolean isFirstRow(Row currentRow) {
		return currentRow.getRowNum() == 0;
	}

	/**
	 * Checks if is valid text header.
	 *
	 * @param currentRow    the current row
	 * @param currentColumn the current column
	 * @return true, if is valid text header
	 */
	private boolean isValidTextHeader(Row currentRow, Integer currentColumn) {
		boolean isValidText = false;
		if (currentRow.getCell(currentColumn) == null) {
			isValidText = false;
		} else if (StringUtils.hasText(currentRow.getCell(currentColumn).toString())) {
			isValidText = true;
		}
		return isValidText;
	}

	/**
	 * Checks if is valid required header.
	 *
	 * @param currentRow    the current row
	 * @param currentColumn the current column
	 * @return true, if is valid required header
	 */
	private boolean isValidRequiredHeader(Row currentRow, Integer currentColumn) {
		boolean status = false;
		String headerText = currentRow.getCell(currentColumn).toString().toLowerCase();
		switch (headerText) {
		case "item_code":
			status = true;
			break;
		case "description":
			status = true;
			break;
		case "uom_name":
			status = true;
			break;
		case "quantity":
			status = true;
			break;
		case "rate":
			status = true;
			break;
		default:
			status = false;
		}

		return status;
	}

	/**
	 * Checks if is valid input data.
	 *
	 * @param currentRow    the current row
	 * @param currentColumn the current column
	 * @return true, if is valid input data
	 */
	private boolean isValidInputData(Row currentRow, int currentColumn) {
		if (currentRow.getCell(currentColumn) == null) {
			return false;
		}
		if (currentColumn == 0 || currentColumn == 1 || currentColumn == 2) {
			if (currentRow.getCell(currentColumn).getCellType() != Cell.CELL_TYPE_STRING) {
				return false;
			}
		} else if (currentColumn == 3 || currentColumn == 4) {
			if (currentRow.getCell(currentColumn).getCellType() != Cell.CELL_TYPE_NUMERIC) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Gets the work estimate DTO.
	 *
	 * @param row the row
	 * @return the work estimate DTO
	 */
	private WorkEstimateItemDTO getWorkEstimateDTO(Row row) {
		WorkEstimateItemDTO workEstimateItemDTO = new WorkEstimateItemDTO();
		workEstimateItemDTO.setItemCode(row.getCell(ITEM_CODE_INDEX).toString());
		workEstimateItemDTO.setDescription(row.getCell(DESCRIPTION_INDEX).toString());
		// TODO: uom_id and uom_name
		workEstimateItemDTO.setQuantity(new BigDecimal(row.getCell(QUANTITY_INDEX).toString()));
		workEstimateItemDTO.setBaseRate(new BigDecimal(row.getCell(RATE_INDEX).toString()));

		return workEstimateItemDTO;
	}
}
