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
import com.dxc.eproc.estimate.service.WorkEstimateRoyaltyChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRoyaltyChargesDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimateRoyaltyCharges}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateRoyaltyChargesController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateRoyaltyChargesController.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workEstimateRoyaltyCharges";

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;
	
	/** The work estimate royalty charges service. */
	@Autowired
	private WorkEstimateRoyaltyChargesService workEstimateRoyaltyChargesService;

    /** The work estimate service. */
    @Autowired
    private WorkEstimateService workEstimateService;
    
    /** The sub estimate service. */
    @Autowired
    private SubEstimateService subEstimateService;
    
    /** The work estimate item service. */
    @Autowired
    private WorkEstimateItemService workEstimateItemService;
    
	/** The framework component. */
	@Autowired
	private FrameworkComponent frameworkComponent;

	/**
	 * {@code POST  /work-estimate-royalty-charges} : Create a new
	 * workEstimateRoyaltyCharges.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId the sub estimate id
	 * @param itemId the item id
	 * @param workEstimateRoyaltyChargesDTO the workEstimateRoyaltyChargesDTO to
	 *                                      create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workEstimateRoyaltyChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateRoyaltyCharges has
	 *         already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-royalty-charges")
	public ResponseEntity<WorkEstimateRoyaltyChargesDTO> createWorkEstimateRoyaltyCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
		@Valid @RequestBody WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO) throws URISyntaxException {
		log.debug("REST request to save WorkEstimateRoyaltyCharges : {}", workEstimateRoyaltyChargesDTO);
		workEstimateRoyaltyChargesDTO.setId(null);
		
        WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
        		.orElseThrow(() -> new RecordNotFoundException(
        				frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId, "WorkEstimate"));
        
		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(
					frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME, "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId, "SubEstimate"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + itemId, "WorkEstimateItem"));

		workEstimateRoyaltyChargesDTO.setWorkEstimateId(workEstimateId);
		workEstimateRoyaltyChargesDTO.setSubEstimateId(subEstimateId);
		workEstimateRoyaltyChargesDTO.setWorkEstimateItemId(itemId);
		WorkEstimateRoyaltyChargesDTO result = workEstimateRoyaltyChargesService.save(workEstimateRoyaltyChargesDTO);
		
		return ResponseEntity.created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/" + subEstimateId + "/work-estimate-item/" +
						itemId + "/work-estimate-royalty-charges/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-estimate-royalty-charges/:id} : Updates an existing
	 * workEstimateRoyaltyCharges.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId the sub estimate id
	 * @param itemId the item id
	 * @param id                            the id of the
	 *                                      workEstimateRoyaltyChargesDTO to save.
	 * @param workEstimateRoyaltyChargesDTO the workEstimateRoyaltyChargesDTO to
	 *                                      update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workEstimateRoyaltyChargesDTO, or with status
	 *         {@code 400 (Bad Request)} if the workEstimateRoyaltyChargesDTO is not
	 *         valid, or with status {@code 500 (Internal Server Error)} if the
	 *         workEstimateRoyaltyChargesDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-royalty-charges/{id}")
	public ResponseEntity<WorkEstimateRoyaltyChargesDTO> updateWorkEstimateRoyaltyCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
		@PathVariable(value = "id", required = false) final Long id,
		@Valid @RequestBody WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO) throws URISyntaxException {
		log.debug("REST request to update WorkEstimateRoyaltyCharges : {}, {}", id, workEstimateRoyaltyChargesDTO);
		
        WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
        		.orElseThrow(() -> new RecordNotFoundException(
        				frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId, "WorkEstimate"));
        
		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(
					frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME, "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId, "SubEstimate"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + itemId, "WorkEstimateItem"));
		
		workEstimateRoyaltyChargesService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() ->  new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateRoyaltyCharges.recordNotFound") + " - " + id, ENTITY_NAME));

		workEstimateRoyaltyChargesDTO.setWorkEstimateId(workEstimateId);
		workEstimateRoyaltyChargesDTO.setWorkEstimateItemId(itemId);
		workEstimateRoyaltyChargesDTO.setId(id);
		WorkEstimateRoyaltyChargesDTO result = workEstimateRoyaltyChargesService.save(workEstimateRoyaltyChargesDTO);
		
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workEstimateRoyaltyChargesDTO.getId().toString())).body(result);
	}
	
	/**
	 * {@code GET  /work-estimate-royalty-charges} : get all the
	 * workEstimateRoyaltyCharges.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId the sub estimate id
	 * @param itemId the item id
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workEstimateRoyaltyCharges in body.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-royalty-charges")
	public ResponseEntity<List<WorkEstimateRoyaltyChargesDTO>> getAllWorkEstimateRoyaltyCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId, Pageable pageable) {
		log.debug("REST request to get a page of WorkEstimateRoyaltyCharges");
		
        WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
        		.orElseThrow(() -> new RecordNotFoundException(
        				frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId, "WorkEstimate"));
        
		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(
					frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME, "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId, "SubEstimate"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + itemId, "WorkEstimateItem"));

		Page<WorkEstimateRoyaltyChargesDTO> page = workEstimateRoyaltyChargesService.findAllByWorkEstimateItemId(itemId, pageable);
		if(page.isEmpty()) {
			throw new RecordNotFoundException(
					frameworkComponent.resolveI18n("workEstimateRoyaltyCharges.noRecords") + " - " + itemId, ENTITY_NAME);
		}
		
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /work-estimate-royalty-charges/:id} : get the "id"
	 * workEstimateRoyaltyCharges.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId the sub estimate id
	 * @param itemId the item id
	 * @param id the id of the workEstimateRoyaltyChargesDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workEstimateRoyaltyChargesDTO, or with status
	 *         {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-royalty-charges/{id}")
	public ResponseEntity<WorkEstimateRoyaltyChargesDTO> getWorkEstimateRoyaltyCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
		@PathVariable Long id) {
		log.debug("REST request to get WorkEstimateRoyaltyCharges : {}", id);
		
        WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
        		.orElseThrow(() -> new RecordNotFoundException(
        				frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId, "WorkEstimate"));
        
		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(
					frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME, "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId, "SubEstimate"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + itemId, "WorkEstimateItem"));

		WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO = workEstimateRoyaltyChargesService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() ->  new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateRoyaltyCharges.recordNotFound") + " - " + id, ENTITY_NAME));

		return ResponseEntity.ok(workEstimateRoyaltyChargesDTO);
	}

	/**
	 * {@code DELETE  /work-estimate-royalty-charges/:id} : delete the "id"
	 * workEstimateRoyaltyCharges.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId the sub estimate id
	 * @param itemId the item id
	 * @param id the id of the workEstimateRoyaltyChargesDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-royalty-charges/{id}")
	public ResponseEntity<Void> deleteWorkEstimateRoyaltyCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
		@PathVariable Long id) {
		log.debug("REST request to delete WorkEstimateRoyaltyCharges : {}", id);
		
        WorkEstimateDTO workEstimateDTO = workEstimateService.findOne(workEstimateId)
        		.orElseThrow(() -> new RecordNotFoundException(
        				frameworkComponent.resolveI18n("workEstimate.recordNotFound") + " - " + workEstimateId, "WorkEstimate"));
        
		if (!workEstimateDTO.getStatus().equals(WorkEstimateStatus.DRAFT)
				&& !workEstimateDTO.getStatus().equals(WorkEstimateStatus.INITIAL)) {
			throw new BadRequestAlertException(
					frameworkComponent.resolveI18n("authorization.permision"), ENTITY_NAME, "permision");
		}

		subEstimateService.findByWorkEstimateIdAndId(workEstimateId, subEstimateId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("subEstimate.recordNotFound") + " - " + subEstimateId, "SubEstimate"));

		workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateItem.recordNotFound") + " - " + itemId, "WorkEstimateItem"));

		workEstimateRoyaltyChargesService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() ->  new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateRoyaltyCharges.recordNotFound") + " - " + id, ENTITY_NAME));

		workEstimateRoyaltyChargesService.delete(id);
		
		return ResponseEntity.noContent().headers(HeaderUtil
				.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
	}
}
