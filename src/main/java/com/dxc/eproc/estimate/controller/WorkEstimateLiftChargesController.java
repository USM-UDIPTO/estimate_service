package com.dxc.eproc.estimate.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

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
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateLiftChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLiftChargesDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.domain.WorkEstimateLiftCharges}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateLiftChargesController {

	private final Logger log = LoggerFactory.getLogger(WorkEstimateLiftChargesController.class);

	private static final String ENTITY_NAME = "workEstimateLiftCharges";

	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	@Autowired
	private WorkEstimateLiftChargesService workEstimateLiftChargesService;

	@Autowired
	private WorkEstimateService workEstimateService;

	@Autowired
	private SubEstimateService subEstimateService;

	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	@Autowired
	private FrameworkComponent frameworkComponent;

	/**
	 * {@code POST  /work-estimate-lift-charges} : Create a new
	 * workEstimateLiftCharges.
	 *
	 * @param workEstimateLiftChargesDTO the workEstimateLiftChargesDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateLiftChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateLiftCharges has already
	 *         an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lift-charges")
	public ResponseEntity<WorkEstimateLiftChargesDTO> createWorkEstimateLiftCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId,
			@Valid @RequestBody WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO) throws URISyntaxException {
		log.debug("REST request to save WorkEstimateLiftCharges : {}", workEstimateLiftChargesDTO);
		workEstimateLiftChargesDTO.setId(null);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						ENTITY_NAME));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidItemId"),
						ENTITY_NAME));

		workEstimateLiftChargesDTO.setWorkEstimateItemId(itemId);

		WorkEstimateLiftChargesDTO result = workEstimateLiftChargesService.save(workEstimateLiftChargesDTO);
		return ResponseEntity
				.created(new URI("/api/work-estimate-lift-charges/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-estimate-lift-charges/:id} : Updates an existing
	 * workEstimateLiftCharges.
	 *
	 * @param id                         the id of the workEstimateLiftChargesDTO to
	 *                                   save.
	 * @param workEstimateLiftChargesDTO the workEstimateLiftChargesDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateLiftChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateLiftChargesDTO is not
	 *         valid, or with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateLiftChargesDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lift-charges/{id}")
	public ResponseEntity<WorkEstimateLiftChargesDTO> updateWorkEstimateLiftCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id,
			@Valid @RequestBody WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO) throws URISyntaxException {
		log.debug("REST request to update WorkEstimateLiftCharges : {}, {}", id, workEstimateLiftChargesDTO);

		WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME,
					"permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						ENTITY_NAME));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidItemId"),
						ENTITY_NAME));

		workEstimateLiftChargesService.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));

		workEstimateLiftChargesDTO.setId(id);
		workEstimateLiftChargesDTO.setWorkEstimateItemId(itemId);

		WorkEstimateLiftChargesDTO result = workEstimateLiftChargesService.save(workEstimateLiftChargesDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workEstimateLiftChargesDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /work-estimate-lift-charges} : get all the
	 * workEstimateLiftCharges.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateLiftCharges in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lift-charges")
	public ResponseEntity<List<WorkEstimateLiftChargesDTO>> getAllWorkEstimateLiftCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, Pageable pageable) {
		log.debug("REST request to get a page of WorkEstimateLiftCharges");

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						ENTITY_NAME));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + "invalidItemId"),
						ENTITY_NAME));

		Page<WorkEstimateLiftChargesDTO> page = workEstimateLiftChargesService.findAllByWorkEstimateItemId(itemId,
				pageable);
		if (page.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".noRecords"), ENTITY_NAME);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /work-estimate-lift-charges/:id} : get the "id"
	 * workEstimateLiftCharges.
	 *
	 * @param id the id of the workEstimateLiftChargesDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateLiftChargesDTO, or with status
	 *         {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lift-charges/{id}")
	public ResponseEntity<WorkEstimateLiftChargesDTO> getWorkEstimateLiftCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id) {
		log.debug("REST request to get WorkEstimateLiftCharges : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						"invalidSubEstimateId"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidItemId"),
						"invalidItemId"));

		WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO = workEstimateLiftChargesService
				.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));

		return ResponseEntity.ok(workEstimateLiftChargesDTO);
	}

	/**
	 * {@code DELETE  /work-estimate-lift-charges/:id} : delete the "id"
	 * workEstimateLiftCharges.
	 *
	 * @param id the id of the workEstimateLiftChargesDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lift-charges/{id}")
	public ResponseEntity<Void> deleteWorkEstimateLiftCharges(@PathVariable("workEstimateId") Long workEstimateId,
			@PathVariable("subEstimateId") Long subEstimateId, @PathVariable("itemId") Long itemId,
			@PathVariable("id") Long id) {
		log.debug("REST request to delete WorkEstimateLiftCharges : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						"invalidSubEstimateId"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidItemId"),
						"invalidItemId"));

		workEstimateLiftChargesService.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));

		workEstimateLiftChargesService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
