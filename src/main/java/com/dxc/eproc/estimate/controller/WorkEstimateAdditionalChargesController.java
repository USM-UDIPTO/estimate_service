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
import com.dxc.eproc.estimate.service.WorkEstimateAdditionalChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateAdditionalChargesDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.domain.WorkEstimateAdditionalCharges}.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class WorkEstimateAdditionalChargesController {

	private final Logger log = LoggerFactory.getLogger(WorkEstimateAdditionalChargesController.class);

	private static final String ENTITY_NAME = "workEstimateAdditionalCharges";

	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	@Autowired
	private WorkEstimateAdditionalChargesService workEstimateAdditionalChargesService;

	@Autowired
	private WorkEstimateService workEstimateService;

	@Autowired
	private SubEstimateService subEstimateService;

	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	@Autowired
	private FrameworkComponent frameworkComponent;

	/**
	 * {@code POST  /work-estimate-additional-charges} : Create a new
	 * workEstimateAdditionalCharges.
	 *
	 * @param workEstimateAdditionalChargesDTO the workEstimateAdditionalChargesDTO
	 *                                         to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateAdditionalChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateAdditionalCharges has
	 *         already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-additional-charges")
	public ResponseEntity<WorkEstimateAdditionalChargesDTO> createWorkEstimateAdditionalCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId,
			@Valid @RequestBody WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO)
			throws URISyntaxException {
		log.debug("REST request to save WorkEstimateAdditionalCharges : {}", workEstimateAdditionalChargesDTO);
		workEstimateAdditionalChargesDTO.setId(null);

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

		workEstimateAdditionalChargesDTO.setWorkEstimateItemId(itemId);

		WorkEstimateAdditionalChargesDTO result = workEstimateAdditionalChargesService
				.save(workEstimateAdditionalChargesDTO);
		return ResponseEntity
				.created(new URI("/api/work-estimate-additional-charges/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-estimate-additional-charges/:id} : Updates an existing
	 * workEstimateAdditionalCharges.
	 *
	 * @param id                               the id of the
	 *                                         workEstimateAdditionalChargesDTO to
	 *                                         save.
	 * @param workEstimateAdditionalChargesDTO the workEstimateAdditionalChargesDTO
	 *                                         to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateAdditionalChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateAdditionalChargesDTO is
	 *         not valid, or with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateAdditionalChargesDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-additional-charges/{id}")
	public ResponseEntity<WorkEstimateAdditionalChargesDTO> updateWorkEstimateAdditionalCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id,
			@Valid @RequestBody WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO)
			throws URISyntaxException {
		log.debug("REST request to update WorkEstimateAdditionalCharges : {}, {}", id,
				workEstimateAdditionalChargesDTO);

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

		workEstimateAdditionalChargesService.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));

		workEstimateAdditionalChargesDTO.setId(id);
		workEstimateAdditionalChargesDTO.setWorkEstimateItemId(itemId);

		WorkEstimateAdditionalChargesDTO result = workEstimateAdditionalChargesService
				.save(workEstimateAdditionalChargesDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workEstimateAdditionalChargesDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /work-estimate-additional-charges} : get all the
	 * workEstimateAdditionalCharges.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateAdditionalCharges in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-additional-charges")
	public ResponseEntity<List<WorkEstimateAdditionalChargesDTO>> getAllWorkEstimateAdditionalCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, Pageable pageable) {
		log.debug("REST request to get a page of WorkEstimateAdditionalCharges");

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						ENTITY_NAME));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + "invalidItemId"),
						ENTITY_NAME));

		Page<WorkEstimateAdditionalChargesDTO> page = workEstimateAdditionalChargesService
				.findAllByWorkEstimateItemId(itemId, pageable);
		if (page.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".noRecords"), ENTITY_NAME);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /work-estimate-additional-charges/:id} : get the "id"
	 * workEstimateAdditionalCharges.
	 *
	 * @param id the id of the workEstimateAdditionalChargesDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateAdditionalChargesDTO, or with status
	 *         {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-additional-charges/{id}")
	public ResponseEntity<WorkEstimateAdditionalChargesDTO> getWorkEstimateAdditionalCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id) {
		log.debug("REST request to get WorkEstimateAdditionalCharges : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						ENTITY_NAME));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + "invalidItemId"),
						ENTITY_NAME));

		WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO = workEstimateAdditionalChargesService
				.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));

		return ResponseEntity.ok(workEstimateAdditionalChargesDTO);
	}

	/**
	 * {@code DELETE  /work-estimate-additional-charges/:id} : delete the "id"
	 * workEstimateAdditionalCharges.
	 *
	 * @param id the id of the workEstimateAdditionalChargesDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-additional-charges/{id}")
	public ResponseEntity<Void> deleteWorkEstimateAdditionalCharges(@PathVariable("workEstimateId") Long workEstimateId,
			@PathVariable("subEstimateId") Long subEstimateId, @PathVariable("itemId") Long itemId,
			@PathVariable("id") Long id) {
		log.debug("REST request to delete WorkEstimateAdditionalCharges : {}", id);

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

		workEstimateAdditionalChargesService.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));

		workEstimateAdditionalChargesService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
