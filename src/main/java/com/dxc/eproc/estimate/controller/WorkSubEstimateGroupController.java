package com.dxc.eproc.estimate.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

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
import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupLumpsumService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupOverheadService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.response.TextResponse;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroup}.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class WorkSubEstimateGroupController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workSubEstimateGroup";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupController.class);

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The work estimate repository. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The work sub estimate group overhead service. */
	@Autowired
	private WorkSubEstimateGroupOverheadService workSubEstimateGroupOverheadService;

	/** The work sub estimate group lumpsum service. */
	@Autowired
	private WorkSubEstimateGroupLumpsumService workSubEstimateGroupLumpsumService;

	/** The work sub estimate group service. */
	@Autowired
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	/** The framework component. */
	@Autowired
	private FrameworkComponent frameworkComponent;

	/**
	 * {@code POST  /work-sub-estimate-groups} : Create a new workSubEstimateGroup.
	 *
	 * @param workSubEstimateGroupDTO the workSubEstimateGroupDTO to create.
	 * @param workEstimateId          the work estimate id
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workSubEstimateGroupDTO, or with status
	 *         {@code 400 (Bad Request)} if the workSubEstimateGroup has already an
	 *         ID.
	 * @throws Exception the exception
	 */
	@PostMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group")
	public ResponseEntity<WorkSubEstimateGroupDTO> createWorkSubEstimateGroup(
			@Valid @RequestBody WorkSubEstimateGroupDTO workSubEstimateGroupDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws Exception {
		log.debug("REST request to save WorkSubEstimateGroup : {}", workSubEstimateGroupDTO);

		String errorM = "workSubEstimateGroupDTO.createWorkSubEstimateGroup.";
		workSubEstimateGroupDTO.setId(null);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		if (CollectionUtils.isEmpty(workSubEstimateGroupDTO.getSubEstimateIds())) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "noSubEstimatesSelected"),
					ENTITY_NAME, "noSubEstimatesSelected");
		}

		// get all at a time
		if (subEstimateService.findByWorkEstimateIdAndIds(workEstimateId, workSubEstimateGroupDTO.getSubEstimateIds())
				.stream().filter(s -> s.getWorkSubEstimateGroupId() == null)
				.count() != new LinkedHashSet<Long>(workSubEstimateGroupDTO.getSubEstimateIds()).stream().count()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidSubEstimateId"),
					ENTITY_NAME);
		}

		BigDecimal sum = subEstimateService.sumEstimateTotalByWorkEstimateIdAndIds(workEstimateId,
				workSubEstimateGroupDTO.getSubEstimateIds());
		WorkSubEstimateGroupOverheadDTO overheadDTO = new WorkSubEstimateGroupOverheadDTO();
		overheadDTO.setOverheadValue(sum);
		overheadDTO.setCode("A");
		overheadDTO.setDescription("Base Estimate Items");
		overheadDTO.setValueType(ValueType.OTHERS);
		overheadDTO.setValueFixedYn(true);
		overheadDTO.setFinalYn(false);

		workSubEstimateGroupDTO.setLumpsumTotal(new BigDecimal(0));
		workSubEstimateGroupDTO.setOverheadTotal(new BigDecimal(0));
		workSubEstimateGroupDTO.setWorkEstimateId(workEstimateId);
		List<Long> subEstimateIds = new ArrayList<>(
				new LinkedHashSet<Long>(workSubEstimateGroupDTO.getSubEstimateIds()));
		workSubEstimateGroupDTO = workSubEstimateGroupService.save(workSubEstimateGroupDTO);
		overheadDTO.setWorkSubEstimateGroupId(workSubEstimateGroupDTO.getId());
		workSubEstimateGroupOverheadService.save(overheadDTO);
		Long workSubEstimateGroupId = workSubEstimateGroupDTO.getId();
		workSubEstimateGroupDTO.setSubEstimateIds(subEstimateIds);
		workSubEstimateGroupDTO.getSubEstimateIds().forEach(id -> {
			SubEstimateDTO subEstimateDTO = new SubEstimateDTO();
			subEstimateDTO.setId(id);
			subEstimateDTO.setWorkSubEstimateGroupId(workSubEstimateGroupId);
			subEstimateService.partialUpdate(subEstimateDTO);
		});
		workSubEstimateGroupService.setSubEstimateDetailsInGroup(workEstimateId, workSubEstimateGroupDTO);
		return ResponseEntity.created(new URI("/api/work-sub-estimate-groups/" + workSubEstimateGroupDTO.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						workSubEstimateGroupDTO.getId().toString()))
				.body(workSubEstimateGroupDTO);
	}

	/**
	 * {@code PATCH  /work-sub-estimate-groups/:id} : Partial updates given fields
	 * of an existing workSubEstimateGroup, field will ignore if it is null.
	 *
	 * @param workSubEstimateGroupDTO the workSubEstimateGroupDTO to update.
	 * @param workEstimateId          the work estimate id
	 * @param id                      the id of the workSubEstimateGroupDTO to save.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workSubEstimateGroupDTO, or with status
	 *         {@code 400 (Bad Request)} if the workSubEstimateGroupDTO is not
	 *         valid, or with status {@code 404 (Not Found)} if the
	 *         workSubEstimateGroupDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the workSubEstimateGroupDTO
	 *         couldn't be updated.
	 * @throws Exception the exception
	 */
	@PutMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}")
	public ResponseEntity<WorkSubEstimateGroupDTO> updateWorkSubEstimateGroup(
			@Valid @RequestBody WorkSubEstimateGroupDTO workSubEstimateGroupDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to update WorkSubEstimateGroup : {}, {}", id, workSubEstimateGroupDTO);

		String errorM = "workSubEstimateGroupDTO.getWorkSubEstimateGroup.";

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		WorkSubEstimateGroupDTO workSubEstimateGroupDBDTO = workSubEstimateGroupService
				.findByWorkEstimateIdAndId(workEstimateId, id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(errorM + "invalidGroupId"), ENTITY_NAME));

		WorkSubEstimateGroupDTO finalGroupDTO = new WorkSubEstimateGroupDTO();
		finalGroupDTO.setId(id);
		finalGroupDTO.setDescription(workSubEstimateGroupDTO.getDescription());
		finalGroupDTO = workSubEstimateGroupService.partialUpdate(finalGroupDTO).get();

		List<Long> subEstimateIds = new ArrayList<>(
				new LinkedHashSet<Long>(workSubEstimateGroupDTO.getSubEstimateIds()));
		workSubEstimateGroupDTO.setSubEstimateIds(subEstimateIds);
		workSubEstimateGroupDTO.getSubEstimateIds().forEach(subEstimateId -> {
			SubEstimateDTO subEstimateDTO = new SubEstimateDTO();
			subEstimateDTO.setId(subEstimateId);
			subEstimateDTO.setWorkSubEstimateGroupId(workSubEstimateGroupDBDTO.getId());
			subEstimateService.partialUpdate(subEstimateDTO);
		});

		workSubEstimateGroupService.recalculateGroup(workSubEstimateGroupDTO.getSubEstimateIds(), finalGroupDTO);

		workSubEstimateGroupService.setSubEstimateDetailsInGroup(workEstimateId, finalGroupDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				finalGroupDTO.getId().toString())).body(finalGroupDTO);
	}

	/**
	 * {@code GET  /work-sub-estimate-groups} : get all the workSubEstimateGroups.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workSubEstimateGroups in body.
	 * @throws Exception the exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/work-sub-estimate-groups")
	public ResponseEntity<List<WorkSubEstimateGroupDTO>> getAllWorkSubEstimateGroups(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId, Pageable pageable)
			throws Exception {
		log.debug("REST request to get a page of WorkSubEstimateGroups");

		String errorM = "workSubEstimateGroupDTO.getAllWorkSubEstimateGroup.";

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"), ENTITY_NAME));

		Page<WorkSubEstimateGroupDTO> groupPage = workSubEstimateGroupService.findByWorkEstimateId(workEstimateId,
				pageable);
		if (groupPage.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "noGroupsPresent"), ENTITY_NAME);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), groupPage);
		groupPage.forEach(workSubEstimateGroupDTO -> {
			workSubEstimateGroupService.setSubEstimateDetailsInGroup(workEstimateId, workSubEstimateGroupDTO);
		});
		return ResponseEntity.ok().headers(headers).body(groupPage.getContent());
	}

	/**
	 * {@code GET  /work-sub-estimate-groups/:id} : get the "id"
	 * workSubEstimateGroup.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id of the workSubEstimateGroupDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workSubEstimateGroupDTO, or with status {@code 404 (Not Found)}.
	 * @throws Exception the exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}")
	public ResponseEntity<WorkSubEstimateGroupDTO> getWorkSubEstimateGroup(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to get WorkSubEstimateGroup : {}", id);

		String errorM = "workSubEstimateGroupDTO.getWorkSubEstimateGroup.";
		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"), ENTITY_NAME));

		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = workSubEstimateGroupService
				.findByWorkEstimateIdAndId(workEstimateId, id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(errorM + "invalidGroupId"), ENTITY_NAME));

		workSubEstimateGroupService.setSubEstimateDetailsInGroup(workEstimateId, workSubEstimateGroupDTO);
		return ResponseUtil.wrapOrNotFound(Optional.of(workSubEstimateGroupDTO));
	}

	/**
	 * {@code DELETE  /work-sub-estimate-groups/:id} : delete the "id"
	 * workSubEstimateGroup.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id of the workSubEstimateGroupDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 * @throws Exception the exception
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}")
	public ResponseEntity<Void> deleteWorkSubEstimateGroup(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("REST request to delete WorkSubEstimateGroup : {},{}", workEstimateId, id);

		String errorM = "workSubEstimateGroupDTO.deleteWorkSubEstimateGroup.";

		WorkEstimateDTO dbWorkEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		if (!(dbWorkEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| dbWorkEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		WorkSubEstimateGroupDTO dbGroupDTO = workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(errorM + "invalidGroupId"), ENTITY_NAME));

		workSubEstimateGroupService.deleteGroup(dbGroupDTO);

		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * Verify group overhead or lumpsum.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/work-sub-estimate-group/{id}/verify")
	public ResponseEntity<TextResponse> verifyGroupOverheadOrLumpsum(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "id", required = true) Long id) throws Exception {

		String errorM = "workSubEstimateGroupDTO.verifyGroupOverheadOrLumpsum.";
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		workSubEstimateGroupService.findByWorkEstimateIdAndId(workEstimateId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidGroupId"),
						ENTITY_NAME));

		if (workSubEstimateGroupLumpsumService.findByWorkSubEstimateGroupId(id, null).isEmpty()
				&& workSubEstimateGroupOverheadService.findByWorkSubEstimateGroupIdAndValueType(id, ValueType.ADDED)
						.isEmpty()) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "noGroupContents"), ENTITY_NAME,
					"noGroupContents");
		}

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

		return ResponseEntity
				.ok(new TextResponse("WorkSubEstimeGroup contains atleast one added Overhead or Lumpsum!!"));

	}

	/**
	 * Verify group overhead or lumpsum by work estimate.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/verify-work-sub-estimate-group")
	public ResponseEntity<TextResponse> verifyGroupOverheadOrLumpsumByWorkEstimate(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId) throws Exception {

		String errorM = "workSubEstimateGroupDTO.verifyGroupOverheadOrLumpsum.";
		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(errorM + "invalidWorkEstimateId"),
						ENTITY_NAME));

		Page<WorkSubEstimateGroupDTO> workEstimateGroupPage = workSubEstimateGroupService
				.findByWorkEstimateId(workEstimateId, null);

		workEstimateGroupPage.getContent().stream().forEach(workEstimateGroup -> {
			if (workSubEstimateGroupLumpsumService.findByWorkSubEstimateGroupId(workEstimateGroup.getId(), null)
					.isEmpty()
					&& workSubEstimateGroupOverheadService
							.findByWorkSubEstimateGroupIdAndValueType(workEstimateGroup.getId(), ValueType.ADDED)
							.isEmpty()) {
				throw new BadRequestAlertException(frameworkComponent.resolveI18n(errorM + "noGroupContents"),
						ENTITY_NAME, "noGroupContents");
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

		return ResponseEntity
				.ok(new TextResponse("WorkSubEstimeGroup contains atleast one added Overhead or Lumpsum!!"));

	}
}
