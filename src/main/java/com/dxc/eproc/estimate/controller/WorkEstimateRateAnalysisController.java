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
import com.dxc.eproc.estimate.service.WorkEstimateRateAnalysisService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing {@link com.dxc.eproc.estimate.domain.WorkEstimateRateAnalysis}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateRateAnalysisController {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(WorkEstimateRateAnalysisController.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "workEstimateRateAnalysis";

    /** The application name. */
    @Value("${eprocurement.clientApp.name}")
    private String applicationName;

    /** The work estimate rate analysis service. */
    @Autowired
    private WorkEstimateRateAnalysisService workEstimateRateAnalysisService;
    
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
     * {@code POST  /work-estimate-rate-analyses} : Create a new workEstimateRateAnalysis.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param workEstimateRateAnalysisDTO the workEstimateRateAnalysisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEstimateRateAnalysisDTO, or with status {@code 400 (Bad Request)} if the workEstimateRateAnalysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-rate-analyses")
    public ResponseEntity<WorkEstimateRateAnalysisDTO> createWorkEstimateRateAnalysis(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
        @Valid @RequestBody WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO) throws URISyntaxException {
        log.debug("REST request to save WorkEstimateRateAnalysis : {}", workEstimateRateAnalysisDTO);
        workEstimateRateAnalysisDTO.setId(null);
        
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

		workEstimateRateAnalysisDTO.setWorkEstimateId(workEstimateId);
		workEstimateRateAnalysisDTO.setWorkEstimateItemId(itemId);
        WorkEstimateRateAnalysisDTO result = workEstimateRateAnalysisService.save(workEstimateRateAnalysisDTO);
        return ResponseEntity
            .created(new URI("/v1/api//work-estimate/" + workEstimateId + "/sub-estimate/" + subEstimateId + "/work-estimate-item/" 
            		+ itemId + "/work-estimate-rate-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-estimate-rate-analyses/:id} : Updates an existing workEstimateRateAnalysis.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param id the id of the workEstimateRateAnalysisDTO to save.
     * @param workEstimateRateAnalysisDTO the workEstimateRateAnalysisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEstimateRateAnalysisDTO,
     * or with status {@code 400 (Bad Request)} if the workEstimateRateAnalysisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEstimateRateAnalysisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-rate-analyses/{id}")
    public ResponseEntity<WorkEstimateRateAnalysisDTO> updateWorkEstimateRateAnalysis(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO) throws URISyntaxException {
        log.debug("REST request to update WorkEstimateRateAnalysis : {}, {}", id, workEstimateRateAnalysisDTO);

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

		workEstimateRateAnalysisService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateRateAnalysis.recordNotFound") + " - " + id, ENTITY_NAME));
		
		workEstimateRateAnalysisDTO.setWorkEstimateId(workEstimateId);
		workEstimateRateAnalysisDTO.setWorkEstimateItemId(itemId);
		workEstimateRateAnalysisDTO.setId(id);
        WorkEstimateRateAnalysisDTO result = workEstimateRateAnalysisService.save(workEstimateRateAnalysisDTO);
        
        return ResponseEntity.ok().headers(HeaderUtil
            .createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEstimateRateAnalysisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-estimate-rate-analyses} : get all the workEstimateRateAnalyses.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEstimateRateAnalyses in body.
     */
    @GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-rate-analyses")
    public ResponseEntity<List<WorkEstimateRateAnalysisDTO>> getAllWorkEstimateRateAnalyses(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId, Pageable pageable) {
        log.debug("REST request to get a page of WorkEstimateRateAnalyses");
        
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

        Page<WorkEstimateRateAnalysisDTO> page = workEstimateRateAnalysisService.findAllByWorkEstimateItemId(itemId, pageable);
        if(page.isEmpty()) {
        	throw new RecordNotFoundException(
        			frameworkComponent.resolveI18n("workEstimateRateAnalysis.noRecords") + " - " + itemId, ENTITY_NAME);
        }
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-estimate-rate-analyses/:id} : get the "id" workEstimateRateAnalysis.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param id the id of the workEstimateRateAnalysisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEstimateRateAnalysisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-rate-analyses/{id}")
    public ResponseEntity<WorkEstimateRateAnalysisDTO> getWorkEstimateRateAnalysis(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
		@PathVariable Long id) {
        log.debug("REST request to get WorkEstimateRateAnalysis : {}", id);
        
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

		WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO = workEstimateRateAnalysisService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateRateAnalysis.recordNotFound") + " - " + id, ENTITY_NAME));

        return ResponseEntity.ok(workEstimateRateAnalysisDTO);
    }

    /**
     * {@code DELETE  /work-estimate-rate-analyses/:id} : delete the "id" workEstimateRateAnalysis.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param id the id of the workEstimateRateAnalysisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-rate-analyses/{id}")
    public ResponseEntity<Void> deleteWorkEstimateRateAnalysis(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
		@PathVariable Long id) {
        log.debug("REST request to delete WorkEstimateRateAnalysis : {}", id);
        
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

		workEstimateRateAnalysisService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateRateAnalysis.recordNotFound") + " - " + id, ENTITY_NAME));

        workEstimateRateAnalysisService.delete(id);
        
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
