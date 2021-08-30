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
import com.dxc.eproc.estimate.service.WorkEstimateLeadChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLeadChargesDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing
 * {@link com.dxc.eproc.rateanalysis.domain.WorkEstimateLeadCharges}.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class WorkEstimateLeadChargesController {

	private final Logger log = LoggerFactory.getLogger(WorkEstimateLeadChargesController.class);

	private static final String ENTITY_NAME = "workEstimateLeadCharges";

	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	@Autowired
	private WorkEstimateLeadChargesService workEstimateLeadChargesService;

	@Autowired
	private WorkEstimateService workEstimateService;

	@Autowired
	private SubEstimateService subEstimateService;

	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	@Autowired
	private FrameworkComponent frameworkComponent;

	/**
	 * {@code POST  /work-estimate-lead-charges} : Create a new
	 * workEstimateLeadCharges.
	 *
	 * @param workEstimateLeadChargesDTO the workEstimateLeadChargesDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateLeadChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateLeadCharges has already
	 *         an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lead-charges")
	public ResponseEntity<WorkEstimateLeadChargesDTO> createWorkEstimateLeadCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId,
			@Valid @RequestBody WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO) throws URISyntaxException {
		log.debug("REST request to save WorkEstimateLeadCharges : {}", workEstimateLeadChargesDTO);
		workEstimateLeadChargesDTO.setId(null);

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

		workEstimateLeadChargesDTO.setWorkEstimateId(workEstimateId);
		workEstimateLeadChargesDTO.setSubEstimateId(subEstimateId);
		workEstimateLeadChargesDTO.setWorkEstimateItemId(workEstimateId);

		WorkEstimateLeadChargesDTO result = workEstimateLeadChargesService.save(workEstimateLeadChargesDTO);
		return ResponseEntity
				.created(new URI("/api/work-estimate-lead-charges/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-estimate-lead-charges/:id} : Updates an existing
	 * workEstimateLeadCharges.
	 *
	 * @param id                         the id of the workEstimateLeadChargesDTO to
	 *                                   save.
	 * @param workEstimateLeadChargesDTO the workEstimateLeadChargesDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateLeadChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateLeadChargesDTO is not
	 *         valid, or with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateLeadChargesDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lead-charges/{id}")
	public ResponseEntity<WorkEstimateLeadChargesDTO> updateWorkEstimateLeadCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id,
			@Valid @RequestBody WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO) throws URISyntaxException {
		log.debug("REST request to update WorkEstimateLeadCharges : {}, {}", id, workEstimateLeadChargesDTO);

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

		workEstimateLeadChargesService.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));

		workEstimateLeadChargesDTO.setId(id);
		workEstimateLeadChargesDTO.setWorkEstimateId(workEstimateId);
		workEstimateLeadChargesDTO.setSubEstimateId(subEstimateId);
		workEstimateLeadChargesDTO.setWorkEstimateItemId(workEstimateId);

		WorkEstimateLeadChargesDTO result = workEstimateLeadChargesService.save(workEstimateLeadChargesDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workEstimateLeadChargesDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /work-estimate-lead-charges} : get all the
	 * workEstimateLeadCharges.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateLeadCharges in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lead-charges")
	public ResponseEntity<List<WorkEstimateLeadChargesDTO>> getAllWorkEstimateLeadCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, Pageable pageable) throws Exception {
		log.debug("REST request to get a page of WorkEstimateLeadCharges");

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						ENTITY_NAME));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + "invalidItemId"),
						ENTITY_NAME));

		Page<WorkEstimateLeadChargesDTO> page = workEstimateLeadChargesService.findAllByWorkEstimateItemId(itemId,
				pageable);
		if (page.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".noRecords"), ENTITY_NAME);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /work-estimate-lead-charges/:id} : get the "id"
	 * workEstimateLeadCharges.
	 *
	 * @param id the id of the workEstimateLeadChargesDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateLeadChargesDTO, or with status
	 *         {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lead-charges/{id}")
	public ResponseEntity<WorkEstimateLeadChargesDTO> getWorkEstimateLeadCharges(
			@PathVariable("workEstimateId") Long workEstimateId, @PathVariable("subEstimateId") Long subEstimateId,
			@PathVariable("itemId") Long itemId, @PathVariable("id") Long id) throws Exception {
		log.debug("REST request to get WorkEstimateLeadCharges : {}", id);

		workEstimateService.findOne(workEstimateId).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidWorkEstimateId"), ENTITY_NAME));

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidSubEstimateId"),
						"invalidSubEstimateId"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidItemId"),
						"invalidItemId"));

		WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO = workEstimateLeadChargesService
				.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));

		return ResponseEntity.ok(workEstimateLeadChargesDTO);
	}

	/**
	 * {@code DELETE  /work-estimate-lead-charges/:id} : delete the "id"
	 * workEstimateLeadCharges.
	 *
	 * @param id the id of the workEstimateLeadChargesDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-lead-charges/{id}")
	public ResponseEntity<Void> deleteWorkEstimateLeadCharges(@PathVariable("workEstimateId") Long workEstimateId,
			@PathVariable("subEstimateId") Long subEstimateId, @PathVariable("itemId") Long itemId,
			@PathVariable("id") Long id) throws Exception {
		log.debug("REST request to delete WorkEstimateLeadCharges : {}", id);

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

		workEstimateLeadChargesService.findByWorkEstimateItemIdAndId(itemId, id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));

		workEstimateLeadChargesService.delete(id);

		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
