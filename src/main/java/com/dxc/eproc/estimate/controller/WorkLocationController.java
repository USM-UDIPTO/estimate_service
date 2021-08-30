package com.dxc.eproc.estimate.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkLocationService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkLocation}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkLocationController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workLocation";

	/** The Constant PERMISSION. */
	private static final String PERMISSION = "authorization.permision";

	/** The Constant ESTIMATE_BASE. */
	private static final String ESTIMATE_BASE = "estimate";

	/** The Constant SUB_ESTIMATE_BASE. */
	private static final String SUB_ESTIMATE_BASE = "subEstimate";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkLocationController.class);

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The work location service. */
	@Autowired
	private WorkLocationService workLocationService;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The frame work component. */
	@Autowired
	private FrameworkComponent frameWorkComponent;

	/**
	 * Instantiates a new work location controller.
	 */
	public WorkLocationController() {
	}

	/**
	 * {@code POST  /work-locations} : Create a new workLocation.
	 *
	 * @param workLocationDTO the workLocationDTO to create.
	 * @param workEstimateId  the work estimate id
	 * @param subEstimateId   the sub estimate id
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workLocationDTO, or with status
	 *         {@code 400 (Bad Request)} if the workLocation has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-location")
	public ResponseEntity<WorkLocationDTO> createWorkLocation(@RequestBody WorkLocationDTO workLocationDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId) throws URISyntaxException {
		log.debug("REST request to save WorkLocation : {}", workLocationDTO);

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
		workLocationDTO.setSubEstimateId(subEstimateDTO.getId());
		WorkLocationDTO result = workLocationService.save(workLocationDTO);

		return ResponseEntity
				.created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/" + subEstimateId
						+ "/work-locations/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-locations/:id} : Updates an existing workLocation.
	 *
	 * @param id              the id of the workLocationDTO to save.
	 * @param workLocationDTO the workLocationDTO to update.
	 * @param workEstimateId  the work estimate id
	 * @param subEstimateId   the sub estimate id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workLocationDTO, or with status {@code 400 (Bad Request)}
	 *         if the workLocationDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the workLocationDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-location/{id}")
	public ResponseEntity<WorkLocationDTO> updateWorkLocation(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody WorkLocationDTO workLocationDTO,
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId)
			throws URISyntaxException {
		log.debug("REST request to update WorkLocation : {}, {}", id, workLocationDTO);

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
		workLocationDTO.setSubEstimateId(subEstimateDTO.getId());
		WorkLocationDTO result = workLocationService.save(workLocationDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workLocationDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /work-locations/:id} : Partial updates given fields of an
	 * existing workLocation, field will ignore if it is null.
	 *
	 * @param id              the id of the workLocationDTO to save.
	 * @param workLocationDTO the workLocationDTO to update.
	 * @param workEstimateId  the work estimate id
	 * @param subEstimateId   the sub estimate id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workLocationDTO, or with status {@code 400 (Bad Request)}
	 *         if the workLocationDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the workLocationDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the workLocationDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-location/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<WorkLocationDTO> partialUpdateWorkLocation(
			@PathVariable(value = "id", required = true) final Long id, @RequestBody WorkLocationDTO workLocationDTO,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId) throws URISyntaxException {
		log.debug("REST request to partial update WorkLocation partially : {}, {}", id, workLocationDTO);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		Optional<WorkLocationDTO> result = workLocationService.partialUpdate(workLocationDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, workLocationDTO.getId().toString()));
	}

	/**
	 * {@code GET  /work-locations} : get all the workLocations.
	 *
	 * @param pageable       the pagination information.
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workLocations in body.
	 * @throws Exception the exception
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-location")
	public ResponseEntity<List<WorkLocationDTO>> getAllWorkLocations(
			@PageableDefault(page = 0, size = 10) Pageable pageable,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId) throws Exception {
		log.debug("REST request to get a page of WorkLocations");

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		Page<WorkLocationDTO> workLocationDTOList = workLocationService
				.getAllWorkLocationsBySubEstimateId(subEstimateId, pageable);
		if (workLocationDTOList.isEmpty()) {
			throw new RecordNotFoundException(frameWorkComponent.resolveI18n("workLocation.recordNotFound"),
					ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), workLocationDTOList);
		return ResponseEntity.ok().headers(headers).body(workLocationDTOList.getContent());
	}

	/**
	 * {@code GET  /work-locations/:id} : get the "id" workLocation.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @param id             the id of the workLocationDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workLocationDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-location/{id}")
	public ResponseEntity<WorkLocationDTO> getWorkLocation(
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
			@PathVariable(value = "id", required = true) Long id) {
		log.debug("REST request to get WorkLocation : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateDTO.getId(), subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		WorkLocationDTO workLocationDTO = workLocationService.findBySubEstimateIdAndId(subEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n("workLocation.recordNotFound") + " - " + id, ENTITY_NAME));

		return ResponseEntity.ok().body(workLocationDTO);
	}

	/**
	 * {@code DELETE  /work-locations/:id} : delete the "id" workLocation.
	 *
	 * @param id             the id of the workLocationDTO to delete.
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-location/{id}")
	public ResponseEntity<Void> deleteWorkLocation(@PathVariable(value = "id", required = true) Long id,
			@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
			@PathVariable(value = "subEstimateId", required = true) Long subEstimateId) {
		log.debug("REST request to delete WorkLocation : {}", id);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		if (!(workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				|| workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL))) {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n(PERMISSION), ENTITY_NAME, "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameWorkComponent.resolveI18n(SUB_ESTIMATE_BASE + ".recordNotFound"),
						ENTITY_NAME));

		WorkLocationDTO workLocationDTO = workLocationService.findBySubEstimateIdAndId(subEstimateId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameWorkComponent.resolveI18n("workLocation.recordNotFound") + " - " + id, ENTITY_NAME));

		workLocationService.delete(workLocationDTO.getId());

		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
