package com.dxc.eproc.estimate.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.response.dto.SubEstimateResponseDTO;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateCategoryService;
import com.dxc.eproc.estimate.service.WorkEstimateItemLBDService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateCategoryDTO;
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
import com.dxc.eproc.response.TextResponse;
import com.dxc.eproc.utils.PaginationUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimateItem}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateSORItemController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workEstimateItem";

	/** The Constant WORK_ESTIMATE_ID. */
	private static final String WORK_ESTIMATE_ID = "id";

	/** The Constant ENTRY_ORDER. */
	private static final String ENTRY_ORDER = "entryOrder";

	/** The Constant WORK_ESTIMATE_ITEM_ID. */
	private static final String WORK_ESTIMATE_ITEM_ID = "id";

	/** The Constant CAT_WORK_SOR_ITEM_ID. */
	private static final String CAT_WORK_SOR_ITEM_ID = "catWorkSorItemId";

	/** The Constant QUANTITY. */
	private static final String QUANTITY = "quantity";

	/** The Constant CATEGORY_ID. */
	private static final String CATEGORY_ID = "categoryId";

	/** The Constant CATEGORY_NAME. */
	private static final String CATEGORY_NAME = "categoryName";

	/** The Constant BASE_RATE. */
	private static final String BASE_RATE = "baseRate";

	/** The Constant ITEM_CODE. */
	private static final String ITEM_CODE = "itemCode";

	/** The Constant UOM_ID. */
	private static final String UOM_ID = "uomId";

	/** The Constant UOM_NAME. */
	private static final String UOM_NAME = "uomName";

	/** The Constant FLOOR_NUMBER. */
	private static final String FLOOR_NUMBER = "floorNumber";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "description";

	/** The Constant CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION. */
	private static final String CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "fieldError.customMethodArgumentNotValidException";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateSORItemController.class);

	/** The work estimate service. */
	private final WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	private final SubEstimateService subEstimateService;

	/** The work estimate item service. */
	private final WorkEstimateItemService workEstimateItemService;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The custom client input validators. */
	@Autowired
	private CustomClientInputValidators customClientInputValidators;

	/** The work estimate item LBD service. */
	@Autowired
	private WorkEstimateItemLBDService workEstimateItemLBDService;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The work estimate category service. */
	@Autowired
	private WorkEstimateCategoryService workEstimateCategoryService;

	/**
	 * Instantiates a new work estimate item controller.
	 *
	 * @param workEstimateService     the work estimate service
	 * @param subEstimateService      the sub estimate service
	 * @param workEstimateItemService the work estimate item service
	 * @param frameworkComponent      the framework component
	 */
	public WorkEstimateSORItemController(WorkEstimateService workEstimateService, SubEstimateService subEstimateService,
			WorkEstimateItemService workEstimateItemService, FrameworkComponent frameworkComponent) {
		this.workEstimateService = workEstimateService;
		this.subEstimateService = subEstimateService;
		this.workEstimateItemService = workEstimateItemService;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items} :
	 * Create a new workEstimateItem.
	 *
	 * @param workEstimateId       the work estimate id
	 * @param subEstimateId        the sub estimate id
	 * @param sorId                the sor id
	 * @param sorName              the sor name
	 * @param workEstimateItemDTOs the work estimate item DT os
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateItemDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateItem has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items")
	public ResponseEntity<SubEstimateResponseDTO> createWorkEstimateSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@RequestParam(value = "sorId", required = true) Long sorId,
			@RequestParam(value = "sorName", required = true) String sorName,
			@Valid @RequestBody List<WorkEstimateItemDTO> workEstimateItemDTOs) throws URISyntaxException {
		log.debug("REST request to save SOR WorkEstimateItem : {}", workEstimateItemDTOs.toString());

		SubEstimateResponseDTO workEstimateSORItemResponseDTO = new SubEstimateResponseDTO();

		List<WorkEstimateItemDTO> workEstimateItemList = new ArrayList<>();
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

		if (workEstimateItemDTOs.size() == 0) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("workEstimateItem.emptyItems"),
					ENTITY_NAME, "NoInputs");
		}

		AtomicInteger index = new AtomicInteger();
		workEstimateItemDTOs.stream().forEach(workEstimateItemDTO -> {
			fieldErrorDTOList
					.addAll(validateWorkEstimateSORItem(workEstimateItemDTO, String.valueOf(index.incrementAndGet())));
		});

		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}

		List<WorkEstimateItemDTO> existingWorkEstimateItems = workEstimateItemService.findAllSORItems(subEstimateId);

		if (!Utility.isValidCollection(existingWorkEstimateItems)) {
			if (!Utility.isValidLong(sorId) || !Utility.isValidString(sorName)) {
				throw new BadRequestAlertException(frameworkComponent.resolveI18n("workEstimateItem.sor"), ENTITY_NAME,
						"sors");
			}
		}

		WorkEstimateCategoryDTO workEstimateCategoryDTO = workEstimateCategoryService
				.findOneBySubEstimateIdAndReferenceIdNotAndParentIdIsNull(subEstimateId, sorId);
		if (workEstimateCategoryDTO != null) {
			Object[] args = { workEstimateCategoryDTO.getCategoryName() };
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("workEstimateItem.selectSor", args),
					ENTITY_NAME, "selectSor");
		}

		if (!Utility.isValidCollection(existingWorkEstimateItems)) {
			WorkEstimateCategoryDTO workEstimateCategorySORDTO = new WorkEstimateCategoryDTO();
			workEstimateCategorySORDTO.setSubEstimateId(subEstimateId);
			workEstimateCategorySORDTO.setCategoryName(sorName);
			workEstimateCategorySORDTO.setParentId(null);
			workEstimateCategorySORDTO.setReferenceId(sorId);
			workEstimateCategoryService.save(workEstimateCategorySORDTO);
		}

		WorkEstimateCategoryDTO parentWorkEstimateCategory = workEstimateCategoryService
				.findOneBySubEstimateIdAndParentIdIsNull(subEstimateId);

		Integer maxEntry = workEstimateItemService.findMaxSOREntryOrderBySubEstimateId(subEstimateDTO.getId());
		AtomicInteger maxEntryOrder = new AtomicInteger(maxEntry != null ? maxEntry : 0);
		workEstimateItemDTOs.stream().forEach(workEstimateItemDTO -> {
			List<WorkEstimateCategoryDTO> workEstimateCategoryDTOList = workEstimateCategoryService
					.findAllBySubEstimateIdAndReferenceIdAndParentIdIsNotNull(subEstimateId,
							workEstimateItemDTO.getCategoryId());
			if (!Utility.isValidCollection(workEstimateCategoryDTOList)) {
				WorkEstimateCategoryDTO workEstimateCategorySORDTO = new WorkEstimateCategoryDTO();
				workEstimateCategorySORDTO.setSubEstimateId(subEstimateId);
				workEstimateCategorySORDTO.setCategoryName(workEstimateItemDTO.getCategoryName());
				workEstimateCategorySORDTO.setParentId(parentWorkEstimateCategory.getId());
				workEstimateCategorySORDTO.setReferenceId(workEstimateItemDTO.getCategoryId());
				workEstimateCategoryService.save(workEstimateCategorySORDTO);
			}

			if (!workEstimateItemDTO.isFloorYn()) {
				workEstimateItemDTO.setSubEstimateId(subEstimateDTO.getId());
				workEstimateItemDTO.setQuantity(null);
				workEstimateItemDTO.setFinalRate(workEstimateItemDTO.getBaseRate());
				workEstimateItemDTO.setLbdPerformedYn(false);
				workEstimateItemDTO.setRaPerformedYn(false);
				workEstimateItemDTO.setFloorNumber(null);

				workEstimateItemDTO.setEntryOrder(maxEntryOrder.incrementAndGet());
				workEstimateItemService.save(workEstimateItemDTO);
			} else {
				int floorSize = 0;
				int initialFloor = 0;
				floorSize = workEstimateItemDTO.getFloorNumber();
				Integer maxFloorInteger = workEstimateItemService.findMaxFloorBySubEstimateIdAndCatWorkSorItemId(
						subEstimateDTO.getId(), workEstimateItemDTO.getCatWorkSorItemId());
				int maxFloor = maxFloorInteger != null ? maxFloorInteger : 0;
				floorSize = floorSize + maxFloor + 1;
				initialFloor = maxFloor + 1;

				int floorNumber;
				for (floorNumber = initialFloor; floorNumber < floorSize; floorNumber++) {
					WorkEstimateItemDTO workEstimateItemFloorDTO = new WorkEstimateItemDTO();
					workEstimateItemFloorDTO.setCategoryId(workEstimateItemDTO.getCategoryId());
					workEstimateItemFloorDTO.setCatWorkSorItemId(workEstimateItemDTO.getCatWorkSorItemId());
					workEstimateItemFloorDTO.setItemCode(workEstimateItemDTO.getItemCode());
					workEstimateItemFloorDTO.setDescription(workEstimateItemDTO.getDescription());
					workEstimateItemFloorDTO.setFloorNumber(floorNumber);
					workEstimateItemFloorDTO.setUomId(workEstimateItemDTO.getUomId());
					workEstimateItemFloorDTO.setUomName(workEstimateItemDTO.getUomName());
					workEstimateItemFloorDTO.setBaseRate(workEstimateItemDTO.getBaseRate());
					workEstimateItemFloorDTO.setLabourRate(workEstimateItemDTO.getLabourRate());
					workEstimateItemFloorDTO.setEntryOrder(maxEntryOrder.incrementAndGet());
					workEstimateItemFloorDTO.setFinalRate(workEstimateItemDTO.getBaseRate());
					workEstimateItemFloorDTO.setLbdPerformedYn(false);
					workEstimateItemFloorDTO.setRaPerformedYn(false);
					workEstimateItemFloorDTO.setSubEstimateId(subEstimateId);
					workEstimateItemService.save(workEstimateItemFloorDTO);
				}

			}
		});
		workEstimateSORItemResponseDTO = subEstimateService.getSubEstimateTotals(workEstimateId, subEstimateId);
		workEstimateSORItemResponseDTO.setItems(workEstimateItemService.findAllSORItems(subEstimateId));

		return ResponseEntity
				.created(new URI(
						"/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/" + subEstimateId + "/sor-items"))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						"number of items inserted: " + workEstimateItemList.size()))
				.body(workEstimateSORItemResponseDTO);
	}

	/**
	 * Adds the work estimate category.
	 *
	 * @param subEstimateId             the sub estimate id
	 * @param workEstimateItemDTO       the work estimate item DTO
	 * @param existingWorkEstimateItems the existing work estimate items
	 */
	public void addWorkEstimateCategory(Long subEstimateId, WorkEstimateItemDTO workEstimateItemDTO,
			List<WorkEstimateItemDTO> existingWorkEstimateItems) {
		workEstimateItemDTO.getCategoryId();

		existingWorkEstimateItems.stream().forEach(existingWorkEstimateItem -> {

		});
	}

	/**
	 * Validate work estimate SOR item.
	 *
	 * @param workEstimateItemDTO the work estimate item DTO
	 * @param index               the index
	 * @return the list
	 */
	private List<FieldErrorVM> validateWorkEstimateSORItem(WorkEstimateItemDTO workEstimateItemDTO, String index) {
		List<FieldErrorVM> fieldErrorVMList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

		if (workEstimateItemDTO.getId() != null) {
			fieldErrorVMList.add(new FieldErrorVM(workEstimateItemDTO.getClass().getName(), WORK_ESTIMATE_ID, index,
					frameworkComponent.resolveI18n("workEstimateItem.idExists")));
		}
		customValidatorVMList.add(new CustomValidatorVM(CAT_WORK_SOR_ITEM_ID, workEstimateItemDTO.getCatWorkSorItemId(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.IS_VALID_NUMBER),
				workEstimateItemDTO.getClass().getName(), index));

		customValidatorVMList.add(new CustomValidatorVM(CATEGORY_ID, workEstimateItemDTO.getCategoryId(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.IS_VALID_NUMBER),
				workEstimateItemDTO.getClass().getName(), index));

		customValidatorVMList.add(new CustomValidatorVM(CATEGORY_NAME, workEstimateItemDTO.getCategoryName(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		customValidatorVMList.add(new CustomValidatorVM(BASE_RATE, workEstimateItemDTO.getBaseRate(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		if (workEstimateItemDTO.getBaseRate() != null) {
			customValidatorVMList.add(new CustomValidatorVM(BASE_RATE, workEstimateItemDTO.getBaseRate(),
					Arrays.asList(CustomValidatorConstants.PATTERN), workEstimateItemDTO.getClass().getName(),
					EstimateServiceConstants.BASE_RATE_PATTERN, index,
					frameworkComponent.resolveI18n("workEstimateItem.baseRatePattern")));
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
				Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.IS_VALID_NUMBER),
				workEstimateItemDTO.getClass().getName(), index));

		customValidatorVMList.add(new CustomValidatorVM(UOM_NAME, workEstimateItemDTO.getUomName(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		customValidatorVMList.add(new CustomValidatorVM(DESCRIPTION, workEstimateItemDTO.getDescription(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL), workEstimateItemDTO.getClass().getName(), index));

		if (workEstimateItemDTO.isFloorYn()) {
			customValidatorVMList.add(new CustomValidatorVM(FLOOR_NUMBER, workEstimateItemDTO.getFloorNumber(),
					Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.IS_VALID_NUMBER),
					workEstimateItemDTO.getClass().getName(), index));
		}

		List<FieldErrorVM> fieldErrorDTOList = customClientInputValidators.checkValidations(customValidatorVMList);
		fieldErrorVMList.addAll(fieldErrorDTOList);

		return fieldErrorVMList;
	}

	/**
	 * {@code PUT
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/:id} :
	 * Updates an existing workEstimateItem.
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
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/{id}")
	public ResponseEntity<WorkEstimateItemDTO> updateWorkEstimateSORItem(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) final Long id,
			@NotNull @RequestBody WorkEstimateItemDTO workEstimateItemDTO) throws URISyntaxException {
		log.debug("REST request to update SOR WorkEstimateItem : {}, {}", id, workEstimateItemDTO);

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
		WorkEstimateItemDTO result = workEstimateItemService.save(workEstimateItemDTO);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * Continue work estimate SOR items.
	 *
	 * @param workEstimateId       the work estimate id
	 * @param subEstimateId        the sub estimate id
	 * @param workEstimateItemDTOs the work estimate item DT os
	 * @return the response entity
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items-continue")
	public ResponseEntity<TextResponse> continueWorkEstimateSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@Valid @RequestBody List<WorkEstimateItemDTO> workEstimateItemDTOs) throws URISyntaxException {
		log.debug("REST request to continue SOR WorkEstimateItem : {}", workEstimateItemDTOs.toString());
		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

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

		String textResponse = null;
		if (Utility.isValidCollection(workEstimateItemDTOs)) {

			workEstimateItemDTOs.stream().forEach(workEstimateItemDTO -> {
				if (!Utility.isValidLong(workEstimateItemDTO.getId())) {
					fieldErrorDTOList.add(new FieldErrorVM(workEstimateItemDTO.getClass().getName(),
							WORK_ESTIMATE_ITEM_ID, String.valueOf(workEstimateItemDTO.getCatWorkSorItemId()),
							frameworkComponent.resolveI18n("workEstimateItem.idNotNull")));
				}
			});

			workEstimateItemDTOs.stream().forEach(workEstimateItemDTO -> {
				if (!Utility.isValidInteger(workEstimateItemDTO.getEntryOrder())) {
					Object[] args = { workEstimateItemDTO.getId() };
					fieldErrorDTOList.add(new FieldErrorVM(workEstimateItemDTO.getClass().getName(), ENTRY_ORDER,
							String.valueOf(workEstimateItemDTO.getId()),
							frameworkComponent.resolveI18n("workEstimateItem.entryOrder.notNull", args)));
				}
			});

			List<Long> workEstimateItemWithIds = workEstimateItemDTOs.stream()
					.filter(workEstimateItemDTO -> workEstimateItemDTO.getId() != null).map(WorkEstimateItemDTO::getId)
					.collect(Collectors.toList());

			List<WorkEstimateItemDTO> workEstimateItemsDBDTO = workEstimateItemService
					.findAllSORItems(subEstimateDTO.getId());

			workEstimateItemsDBDTO.stream().forEach(workEstimateItemDTO -> {
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

			if (workEstimateItemsDBDTO.stream().count() != new LinkedHashSet<Long>(workEstimateItemWithIds).stream()
					.count()) {
				throw new BadRequestAlertException(
						frameworkComponent.resolveI18n("workEstimateItem.invalidSorEstimateItems"), ENTITY_NAME,
						"invalidSorEstimateItems");
			}

			workEstimateItemsDBDTO.stream().forEach(workEstimateItemDBDTO -> {
				workEstimateItemDTOs.stream().forEach(workEstimateItemDTO -> {
					if (workEstimateItemDTO.getId().equals(workEstimateItemDBDTO.getId())
							&& !workEstimateItemDTO.getEntryOrder().equals(workEstimateItemDBDTO.getEntryOrder())) {
						WorkEstimateItemDTO workEstimateItemPartialDTO = new WorkEstimateItemDTO();
						workEstimateItemPartialDTO.setId(workEstimateItemDBDTO.getId());
						workEstimateItemPartialDTO.setEntryOrder(workEstimateItemDTO.getEntryOrder());
						workEstimateItemService.partialUpdate(workEstimateItemDTO);
					}
				});
			});

			textResponse = "All SOR Items Validated";
		} else {
			textResponse = "No SOR Items Saved";
		}

		return ResponseEntity.ok().body(new TextResponse(textResponse));
	}

	/**
	 * Modify quantity work estimate SOR items.
	 *
	 * @param workEstimateId      the work estimate id
	 * @param subEstimateId       the sub estimate id
	 * @param id                  the id
	 * @param workEstimateItemDTO the work estimate item DTO
	 * @return the response entity
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/{id}/modify-quantity")
	public ResponseEntity<SubEstimateResponseDTO> modifyQuantityWorkEstimateSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) Long id, @RequestBody WorkEstimateItemDTO workEstimateItemDTO)
			throws URISyntaxException {
		log.debug("REST request to Modify Quantity SOR WorkEstimateItem : {} - {}", workEstimateItemDTO.getQuantity(),
				workEstimateItemDTO.getQuantity());
		List<FieldErrorVM> fieldErrorDTOList = new ArrayList<>();
		List<CustomValidatorVM> customValidatorVMList = new ArrayList<>();

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

		WorkEstimateItemDTO workEstimateItemDBDTO = workEstimateItemService
				.findBySubEstimateIdAndId(subEstimateDTO.getId(), id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + id,
						"WorkEstimateItem"));

		Object[] args = { workEstimateItemDBDTO.getItemCode() };
		customValidatorVMList.add(new CustomValidatorVM(QUANTITY, workEstimateItemDTO.getQuantity(),
				Arrays.asList(CustomValidatorConstants.NOT_NULL, CustomValidatorConstants.PATTERN),
				workEstimateItemDTO.getClass().getName(), EstimateServiceConstants.EPROC_MIN_DECIMAL_PATTERN, null,
				frameworkComponent.resolveI18n("workEstimateItem.quantityPattern", args)));

		fieldErrorDTOList = customClientInputValidators.checkValidations(customValidatorVMList);

		if (Utility.isValidCollection(fieldErrorDTOList)) {
			throw new CustomMethodArgumentNotValidException(
					frameworkComponent.resolveI18n(CUSTOM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION), fieldErrorDTOList);
		}

		WorkEstimateItemDTO workEstimateItemPartialDTO = new WorkEstimateItemDTO();
		workEstimateItemPartialDTO.setId(workEstimateItemDBDTO.getId());
		workEstimateItemPartialDTO.setQuantity(workEstimateItemDTO.getQuantity());
		workEstimateItemDBDTO = workEstimateItemService.partialUpdate(workEstimateItemPartialDTO).get();

		SubEstimateResponseDTO workEstimateSORItemResponseDTO = subEstimateService
				.calculateSubEstimateTotals(workEstimateId, subEstimateId);
		workEstimateSORItemResponseDTO.setItems(Arrays.asList(workEstimateItemDBDTO));
		return ResponseEntity.ok().body(workEstimateSORItemResponseDTO);
	}

	/**
	 * {@code PATCH
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/:id} :
	 * Partial updates given fields of an existing workEstimateItem, field will
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
	@PatchMapping(value = "/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<WorkEstimateItemDTO> partialUpdateWorkEstimateSORItem(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) final Long id,
			@RequestBody WorkEstimateItemDTO workEstimateItemDTO) throws URISyntaxException {
		log.debug("REST request to partial update SOR WorkEstimateItem partially : {}, {}", id, workEstimateItemDTO);

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
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items} : get
	 * all the workEstimateItems.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param pageable       the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateItems in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items-with-pagination")
	public ResponseEntity<SubEstimateResponseDTO> getAllWorkEstimateSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
		log.debug("REST request to get a page of SOR WorkEstimateItems - WorkEstimateId : {} - SubEstimateId : {}",
				workEstimateId, subEstimateId);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId,
						"WorkEstimate"));

		SubEstimateDTO subEstimateDTO = subEstimateService
				.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId,
						"SubEstimate"));

		Page<WorkEstimateItemDTO> page = workEstimateItemService.findAllSORItems(subEstimateDTO.getId(), pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		SubEstimateResponseDTO workEstimateSORItemResponseDTO = subEstimateService
				.getSubEstimateTotals(workEstimateDTO.getId(), subEstimateDTO.getId());
		workEstimateSORItemResponseDTO.setItems(page.getContent());
		return ResponseEntity.ok().headers(headers).body(workEstimateSORItemResponseDTO);
	}

	/**
	 * Gets the all work estimate SOR items.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @return the all work estimate SOR items
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items")
	public ResponseEntity<SubEstimateResponseDTO> getAllWorkEstimateSORItems(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId) {
		log.debug("REST request to get All of SOR WorkEstimateItems - WorkEstimateId : {} - SubEstimateId : {}",
				workEstimateId, subEstimateId);

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
				.findAllSORItems(subEstimateDTO.getId());

		SubEstimateResponseDTO workEstimateSORItemResponseDTO = subEstimateService
				.getSubEstimateTotals(workEstimateDTO.getId(), subEstimateDTO.getId());
		workEstimateSORItemResponseDTO.setItems(workEstimateItemDTOList);
		return ResponseEntity.ok().body(workEstimateSORItemResponseDTO);
	}

	/**
	 * {@code GET
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/:id} :
	 * get the "id" workEstimateItem.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param id             the id of the workEstimateItemDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateItemDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/{id}")
	public ResponseEntity<WorkEstimateItemDTO> getWorkEstimateSORItem(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to get SOR WorkEstimateItem : {}", id);

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
	 * /work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/:id} :
	 * delete the "id" workEstimateItem.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param id             the id of the workEstimateItemDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/sor-items/{id}")
	public ResponseEntity<SubEstimateResponseDTO> deleteWorkEstimateSORItem(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to delete SOR WorkEstimateItem : {}", id);

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

		// TOTO - Delete RA

		// Delete WorkEstimateItem Category
		List<WorkEstimateItemDTO> categoryWorkEstimateItemDTOList = workEstimateItemService
				.findAllBySubEstimateIdAndCategoryIdAndCatWorkSorItemIdIsNotNull(workEstimateItemDTO.getSubEstimateId(),
						workEstimateItemDTO.getCategoryId());
		if (categoryWorkEstimateItemDTOList.size() == 1) {
			workEstimateCategoryService.delete(categoryWorkEstimateItemDTOList.get(0).getId());
		}

		workEstimateItemService.delete(workEstimateItemDTO.getId());
		subEstimateService.calculateSubEstimateTotals(workEstimateId, subEstimateId);

		List<WorkEstimateItemDTO> workEstimateItemDTOList = workEstimateItemService
				.findAllSORItems(subEstimateDTO.getId());

		SubEstimateResponseDTO workEstimateSORItemResponseDTO = subEstimateService
				.getSubEstimateTotals(workEstimateDTO.getId(), subEstimateDTO.getId());
		workEstimateSORItemResponseDTO.setItems(workEstimateItemDTOList);
		return ResponseEntity.ok().body(workEstimateSORItemResponseDTO);
	}
}
