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
import org.springframework.transaction.annotation.Transactional;
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
import com.dxc.eproc.estimate.service.WorkEstimateLoadUnloadChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLoadUnloadChargesDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimateLoadUnloadCharges}.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class WorkEstimateLoadUnloadChargesController {

	private final Logger log = LoggerFactory.getLogger(WorkEstimateLoadUnloadChargesController.class);

	private static final String ENTITY_NAME = "workEstimateLoadUnloadCharges";

	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	@Autowired
	private WorkEstimateLoadUnloadChargesService workEstimateLoadUnloadChargesService;

	@Autowired
	private WorkEstimateService workEstimateService;

	@Autowired
	private SubEstimateService subEstimateService;

	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	@Autowired
	private FrameworkComponent frameworkComponent;

	/**
	 * {@code POST  /work-estimate-load-unload-charges} : Create a new
	 * workEstimateLoadUnloadCharges.
	 *
	 * @param workEstimateLoadUnloadChargesDTO the workEstimateLoadUnloadChargesDTO
	 *                                         to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateLoadUnloadChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateLoadUnloadCharges has
	 *         already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-load-unload-charges")
	public ResponseEntity<WorkEstimateLoadUnloadChargesDTO> createWorkEstimateLoadUnloadCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId,
			@Valid @RequestBody WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO)
			throws URISyntaxException {
		log.debug("REST request to save WorkEstimateLoadUnloadCharges : {}", workEstimateLoadUnloadChargesDTO);
		workEstimateLoadUnloadChargesDTO.setId(null);

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

		workEstimateLoadUnloadChargesDTO.setWorkEstimateId(workEstimateId);
		workEstimateLoadUnloadChargesDTO.setSubEstimateId(subEstimateId);
		workEstimateLoadUnloadChargesDTO.setWorkEstimateItemId(workEstimateId);

		WorkEstimateLoadUnloadChargesDTO result = workEstimateLoadUnloadChargesService
				.save(workEstimateLoadUnloadChargesDTO);
		return ResponseEntity
				.created(new URI("/api/work-estimate-load-unload-charges/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-estimate-load-unload-charges/:id} : Updates an existing
	 * workEstimateLoadUnloadCharges.
	 *
	 * @param id                               the id of the
	 *                                         workEstimateLoadUnloadChargesDTO to
	 *                                         save.
	 * @param workEstimateLoadUnloadChargesDTO the workEstimateLoadUnloadChargesDTO
	 *                                         to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateLoadUnloadChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateLoadUnloadChargesDTO is
	 *         not valid, or with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateLoadUnloadChargesDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-load-unload-charges/{id}")
	public ResponseEntity<WorkEstimateLoadUnloadChargesDTO> updateWorkEstimateLoadUnloadCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id,
			@Valid @RequestBody WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO)
			throws URISyntaxException {
		log.debug("REST request to update WorkEstimateLoadUnloadCharges : {}, {}", id,
				workEstimateLoadUnloadChargesDTO);

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

		workEstimateLoadUnloadChargesService.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));

		workEstimateLoadUnloadChargesDTO.setId(id);
		workEstimateLoadUnloadChargesDTO.setWorkEstimateId(workEstimateId);
		workEstimateLoadUnloadChargesDTO.setSubEstimateId(subEstimateId);
		workEstimateLoadUnloadChargesDTO.setWorkEstimateItemId(workEstimateId);

		WorkEstimateLoadUnloadChargesDTO result = workEstimateLoadUnloadChargesService
				.save(workEstimateLoadUnloadChargesDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workEstimateLoadUnloadChargesDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /work-estimate-load-unload-charges} : get all the
	 * workEstimateLoadUnloadCharges.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateLoadUnloadCharges in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-load-unload-charges")
	public ResponseEntity<List<WorkEstimateLoadUnloadChargesDTO>> getAllWorkEstimateLoadUnloadCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, Pageable pageable) {
		log.debug("REST request to get a page of WorkEstimateLoadUnloadCharges");

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						ENTITY_NAME));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + "invalidItemId"),
						ENTITY_NAME));

		Page<WorkEstimateLoadUnloadChargesDTO> page = workEstimateLoadUnloadChargesService
				.findAllByWorkEstimateItemId(itemId, pageable);
		if (page.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".noRecords"), ENTITY_NAME);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /work-estimate-load-unload-charges/:id} : get the "id"
	 * workEstimateLoadUnloadCharges.
	 *
	 * @param id the id of the workEstimateLoadUnloadChargesDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateLoadUnloadChargesDTO, or with status
	 *         {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-load-unload-charges/{id}")
	public ResponseEntity<WorkEstimateLoadUnloadChargesDTO> getWorkEstimateLoadUnloadCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id) {
		log.debug("REST request to get WorkEstimateLoadUnloadCharges : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						"invalidSubEstimateId"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidItemId"),
						"invalidItemId"));

		WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO = workEstimateLoadUnloadChargesService
				.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));

		return ResponseEntity.ok(workEstimateLoadUnloadChargesDTO);
	}

	/**
	 * {@code DELETE  /work-estimate-load-unload-charges/:id} : delete the "id"
	 * workEstimateLoadUnloadCharges.
	 *
	 * @param id the id of the workEstimateLoadUnloadChargesDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-load-unload-charges/{id}")
	public ResponseEntity<Void> deleteWorkEstimateLoadUnloadCharges(@PathVariable("workEstimateId") Long workEstimateId,
			@PathVariable("subEstimateId") Long subEstimateId, @PathVariable("itemId") Long itemId,
			@PathVariable("id") Long id) {
		log.debug("REST request to delete WorkEstimateLoadUnloadCharges : {}", id);

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

		workEstimateLoadUnloadChargesService.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));

		workEstimateLoadUnloadChargesService.delete(id);

		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
