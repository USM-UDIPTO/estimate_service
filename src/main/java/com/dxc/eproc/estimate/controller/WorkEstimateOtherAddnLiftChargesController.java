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
import com.dxc.eproc.estimate.service.WorkEstimateOtherAddnLiftChargesService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOtherAddnLiftChargesDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing {@link com.dxc.eproc.estimate.model.WorkEstimateOtherAddnLiftCharges}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateOtherAddnLiftChargesController {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(WorkEstimateOtherAddnLiftChargesController.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "workEstimateOtherAddnLiftCharges";

    /** The application name. */
    @Value("${eprocurement.clientApp.name}")
    private String applicationName;

    /** The work estimate other addn lift charges service. */
    @Autowired
    private WorkEstimateOtherAddnLiftChargesService workEstimateOtherAddnLiftChargesService;
    
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
     * {@code POST  /work-estimate-other-addn-lift-charges} : Create a new workEstimateOtherAddnLiftCharges.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param workEstimateOtherAddnLiftChargesDTO the workEstimateOtherAddnLiftChargesDTO to create.
     * @return the {@link ResponseEntity} with status
     * 			   {@code 201 (Created)} and with body the new workEstimateOtherAddnLiftChargesDTO, or with status 
     * 			   {@code 400 (Bad Request)} if the workEstimateOtherAddnLiftCharges has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-other-additional-lift-charges")
    public ResponseEntity<WorkEstimateOtherAddnLiftChargesDTO> createWorkEstimateOtherAddnLiftCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
        @Valid @RequestBody WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO) throws URISyntaxException {
        log.debug("REST request to save WorkEstimateOtherAddnLiftCharges : {}", workEstimateOtherAddnLiftChargesDTO);
        workEstimateOtherAddnLiftChargesDTO.setId(null);
        
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

		workEstimateOtherAddnLiftChargesDTO.setWorkEstimateItemId(itemId);
        WorkEstimateOtherAddnLiftChargesDTO result = workEstimateOtherAddnLiftChargesService
        		.save(workEstimateOtherAddnLiftChargesDTO);
        return ResponseEntity
            .created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/" + subEstimateId  + "/work-estimate-item/" + 
            		itemId + "/work-estimate-other-addn-lift-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-estimate-other-addn-lift-charges/:id} : Updates an existing workEstimateOtherAddnLiftCharges.
     *
     * @param id the id of the workEstimateOtherAddnLiftChargesDTO to save.
     * @param workEstimateOtherAddnLiftChargesDTO the workEstimateOtherAddnLiftChargesDTO to update.
     * @return the {@link ResponseEntity} with status 
     * 			   {@code 200 (OK)} and with body the updated workEstimateOtherAddnLiftChargesDTO, or with status 
     *			   {@code 400 (Bad Request)} if the workEstimateOtherAddnLiftChargesDTO is not valid,or with status
     * 			   {@code 500 (Internal Server Error)} if the workEstimateOtherAddnLiftChargesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-other-additional-lift-charges/{id}")
    public ResponseEntity<WorkEstimateOtherAddnLiftChargesDTO> updateWorkEstimateOtherAddnLiftCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO) throws URISyntaxException {
        log.debug("REST request to update WorkEstimateOtherAddnLiftCharges : {}, {}", id, workEstimateOtherAddnLiftChargesDTO);
        
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
		
		workEstimateOtherAddnLiftChargesService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateOtherAddnLiftCharges.recordNotFound") + " - " + id, ENTITY_NAME));

		workEstimateOtherAddnLiftChargesDTO.setWorkEstimateItemId(itemId);
		workEstimateOtherAddnLiftChargesDTO.setId(id);
		
        WorkEstimateOtherAddnLiftChargesDTO result = workEstimateOtherAddnLiftChargesService
        		.save(workEstimateOtherAddnLiftChargesDTO);
        return ResponseEntity.ok().headers(HeaderUtil
        		.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEstimateOtherAddnLiftChargesDTO.getId().toString())).body(result);
    }

    /**
     * {@code GET  /work-estimate-other-addn-lift-charges} : get all the workEstimateOtherAddnLiftCharges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and 
     * 		   the list of workEstimateOtherAddnLiftCharges in body.
     */
    @GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-other-additional-lift-charges")
    public ResponseEntity<List<WorkEstimateOtherAddnLiftChargesDTO>> getAllWorkEstimateOtherAddnLiftCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId, Pageable pageable) {
        log.debug("REST request to get a page of WorkEstimateOtherAddnLiftCharges");
        
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

        Page<WorkEstimateOtherAddnLiftChargesDTO> page = workEstimateOtherAddnLiftChargesService
        		.findAllByWorkEstimateItemId(itemId, pageable);
        if(page.isEmpty()) {
        	throw new RecordNotFoundException(frameworkComponent
        			.resolveI18n("workEstimateOtherAddnLiftCharges.noRecords") + " - " + itemId, ENTITY_NAME);
        }
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-estimate-other-addn-lift-charges/:id} : get the "id" workEstimateOtherAddnLiftCharges.
     *
     * @param id the id of the workEstimateOtherAddnLiftChargesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body 
     * 		   the workEstimateOtherAddnLiftChargesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-other-additional-lift-charges/{id}")
    public ResponseEntity<WorkEstimateOtherAddnLiftChargesDTO> getWorkEstimateOtherAddnLiftCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
		@PathVariable Long id) {
        log.debug("REST request to get WorkEstimateOtherAddnLiftCharges : {}", id);
        
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

        WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO = 
        		workEstimateOtherAddnLiftChargesService.findByWorkEstimateItemIdAndId(itemId, id)
        			.orElseThrow(() -> new RecordNotFoundException(frameworkComponent
        					.resolveI18n("workEstimateOtherAddnLiftCharges.recordNotFound") + " - " + id, ENTITY_NAME));
        
        return ResponseEntity.ok(workEstimateOtherAddnLiftChargesDTO);
    }

    /**
     * {@code DELETE  /work-estimate-other-addn-lift-charges/:id} : delete the "id" workEstimateOtherAddnLiftCharges.
     *
     * @param id the id of the workEstimateOtherAddnLiftChargesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-other-additional-lift-charges/{id}")
    public ResponseEntity<Void> deleteWorkEstimateOtherAddnLiftCharges(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
    	@PathVariable Long id) {
        log.debug("REST request to delete WorkEstimateOtherAddnLiftCharges : {}", id);
        
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

		workEstimateOtherAddnLiftChargesService.findByWorkEstimateItemIdAndId(itemId, id)
			.orElseThrow(() -> new RecordNotFoundException(frameworkComponent
					.resolveI18n("workEstimateOtherAddnLiftCharges.recordNotFound") + " - " + id, ENTITY_NAME));

        workEstimateOtherAddnLiftChargesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
