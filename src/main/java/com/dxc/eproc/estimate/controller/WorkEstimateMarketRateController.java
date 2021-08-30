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
import com.dxc.eproc.estimate.service.WorkEstimateMarketRateService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateMarketRateDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing {@link com.dxc.eproc.estimate.domain.WorkEstimateMarketRate}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkEstimateMarketRateController {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(WorkEstimateMarketRateController.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "workEstimateMarketRate";

    /** The application name. */
    @Value("${eprocurement.clientApp.name}")
    private String applicationName;

    /** The work estimate market rate service. */
    @Autowired
    private WorkEstimateMarketRateService workEstimateMarketRateService;

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
     * {@code POST  /work-estimate-market-rates} : Create a new workEstimateMarketRate.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param workEstimateMarketRateDTO the workEstimateMarketRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workEstimateMarketRateDTO, or with status {@code 400 (Bad Request)} if the workEstimateMarketRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-market-rates")
    public ResponseEntity<WorkEstimateMarketRateDTO> createWorkEstimateMarketRate(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
        @Valid @RequestBody WorkEstimateMarketRateDTO workEstimateMarketRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save WorkEstimateMarketRate : {}", workEstimateMarketRateDTO);
        workEstimateMarketRateDTO.setId(null);
        
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

		workEstimateMarketRateDTO.setWorkEstimateId(workEstimateId);
		workEstimateMarketRateDTO.setSubEstimateId(subEstimateId);
		workEstimateMarketRateDTO.setWorkEstimateItemId(itemId);
        WorkEstimateMarketRateDTO result = workEstimateMarketRateService.save(workEstimateMarketRateDTO);
        return ResponseEntity
            .created(new URI("/v1/api/work-estimate/" + workEstimateId + "/sub-estimate/" + subEstimateId  + "/work-estimate-item/" 
            		+ itemId + "/work-estimate-market-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-estimate-market-rates/:id} : Updates an existing workEstimateMarketRate.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param id the id of the workEstimateMarketRateDTO to save.
     * @param workEstimateMarketRateDTO the workEstimateMarketRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workEstimateMarketRateDTO,
     * or with status {@code 400 (Bad Request)} if the workEstimateMarketRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workEstimateMarketRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-market-rates/{id}")
    public ResponseEntity<WorkEstimateMarketRateDTO> updateWorkEstimateMarketRate(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkEstimateMarketRateDTO workEstimateMarketRateDTO) throws URISyntaxException {
        log.debug("REST request to update WorkEstimateMarketRate : {}, {}", id, workEstimateMarketRateDTO);
        
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
		
		workEstimateMarketRateService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateMarketRate.recordNotFound") + " - " + id, ENTITY_NAME));

		workEstimateMarketRateDTO.setWorkEstimateId(workEstimateId);
		workEstimateMarketRateDTO.setSubEstimateId(subEstimateId);
		workEstimateMarketRateDTO.setWorkEstimateItemId(itemId);
		workEstimateMarketRateDTO.setId(id);
        WorkEstimateMarketRateDTO result = workEstimateMarketRateService.save(workEstimateMarketRateDTO);
        
        return ResponseEntity.ok().headers(HeaderUtil
            .createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workEstimateMarketRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-estimate-market-rates} : get all the workEstimateMarketRates.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workEstimateMarketRates in body.
     */
    @GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-market-rates")
    public ResponseEntity<List<WorkEstimateMarketRateDTO>> getAllWorkEstimateMarketRates(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId, Pageable pageable) {
        log.debug("REST request to get a page of WorkEstimateMarketRates");
        
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

        Page<WorkEstimateMarketRateDTO> page = workEstimateMarketRateService.findAllByWorkEstimateItemId(itemId, pageable);
        if(page.isEmpty()) {
        	throw new RecordNotFoundException(
        			frameworkComponent.resolveI18n("workEstimateMarketRate.noRecords") + " - " + itemId, ENTITY_NAME);
        }
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-estimate-market-rates/:id} : get the "id" workEstimateMarketRate.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param id the id of the workEstimateMarketRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workEstimateMarketRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-market-rates/{id}")
    public ResponseEntity<WorkEstimateMarketRateDTO> getWorkEstimateMarketRate(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
    	@PathVariable Long id) {
        log.debug("REST request to get WorkEstimateMarketRate : {}", id);
        
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
		
		WorkEstimateMarketRateDTO workEstimateMarketRateDTO = workEstimateMarketRateService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateMarketRate.recordNotFound") + " - " + id, ENTITY_NAME));

        return ResponseEntity.ok(workEstimateMarketRateDTO);
    }

    /**
     * {@code DELETE  /work-estimate-market-rates/:id} : delete the "id" workEstimateMarketRate.
     *
     * @param workEstimateId the work estimate id
     * @param subEstimateId the sub estimate id
     * @param itemId the item id
     * @param id the id of the workEstimateMarketRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-estimate/{workEstimateId}/sub-estimate/{subEstimateId}/work-estimate-item/{itemId}/work-estimate-market-rates/{id}")
    public ResponseEntity<Void> deleteWorkEstimateMarketRate(
		@PathVariable(value = "workEstimateId", required = true) Long workEstimateId,
		@PathVariable(value = "subEstimateId", required = true) Long subEstimateId,
		@PathVariable(value = "itemId", required = true) Long itemId,
		@PathVariable Long id) {
        log.debug("REST request to delete WorkEstimateMarketRate : {}", id);
        
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

		workEstimateMarketRateService.findByWorkEstimateItemIdAndId(itemId, id)
				.orElseThrow(() -> new RecordNotFoundException(
						frameworkComponent.resolveI18n("workEstimateMarketRate.recordNotFound") + " - " + id, ENTITY_NAME));
		
        workEstimateMarketRateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
