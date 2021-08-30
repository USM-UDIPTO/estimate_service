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
import com.dxc.eproc.estimate.service.RaFormulaService;
import com.dxc.eproc.estimate.service.dto.RaFormulaDTO;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing {@link com.dxc.eproc.estimate.domain.RaFormula}.
 */
@RestController
@RequestMapping("/v1/api")
public class RaFormulaController {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(RaFormulaController.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "raFormula";

    /** The application name. */
    @Value("${eprocurement.clientApp.name}")
    private String applicationName;

    /** The ra formula service. */
    @Autowired
    private RaFormulaService raFormulaService;

	/** The framework component. */
	@Autowired
	private FrameworkComponent frameworkComponent;

    /**
     * {@code POST  /ra-formulas} : Create a new raFormula.
     *
     * @param deptId the dept id
     * @param raFormulaDTO the raFormulaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raFormulaDTO, or with status {@code 400 (Bad Request)} if the raFormula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/department/{deptId}/ra-formulas")
    public ResponseEntity<RaFormulaDTO> createRaFormula(
		@PathVariable(value = "deptId", required = true) final Long deptId,
		@Valid @RequestBody RaFormulaDTO raFormulaDTO) throws URISyntaxException {
        log.debug("REST request to save RaFormula : {}", raFormulaDTO);
        raFormulaDTO.setId(null);
        raFormulaDTO.setDeptId(deptId);
        RaFormulaDTO result = raFormulaService.save(raFormulaDTO);
        return ResponseEntity
            .created(new URI("/v1/api/department/" + deptId + "/ra-formulas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code PUT  /ra-formulas/:id} : Updates an existing raFormula.
     *
     * @param deptId the dept id
     * @param id the id of the raFormulaDTO to save.
     * @param raFormulaDTO the raFormulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raFormulaDTO,
     * or with status {@code 400 (Bad Request)} if the raFormulaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raFormulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/department/{deptId}/ra-formulas/{id}")
    public ResponseEntity<RaFormulaDTO> updateRaFormula(
		@PathVariable(value = "deptId", required = true) final Long deptId,
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RaFormulaDTO raFormulaDTO) throws URISyntaxException {
        log.debug("REST request to update RaFormula : {}, {}", id, raFormulaDTO);

        raFormulaService.findByDeptIdAndId(deptId, id).orElseThrow(() -> new RecordNotFoundException(
        		frameworkComponent.resolveI18n("raFormula.invalidId") + " - " + id, ENTITY_NAME));
        
        raFormulaDTO.setDeptId(deptId);
        raFormulaDTO.setId(id);
        RaFormulaDTO result = raFormulaService.save(raFormulaDTO);
        return ResponseEntity.ok().headers(HeaderUtil
            .createEntityUpdateAlert(applicationName, true, ENTITY_NAME, raFormulaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ra-formulas} : get all the raFormulas.
     *
     * @param deptId the dept id
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of raFormulas in body.
     */
    @GetMapping("/department/{deptId}/ra-formulas")
    public ResponseEntity<List<RaFormulaDTO>> getAllRaFormulas(
		@PathVariable(value = "deptId", required = true) final Long deptId, Pageable pageable) {
        log.debug("REST request to get a page of RaFormulas");
        
        Page<RaFormulaDTO> page = raFormulaService.findByDeptId(deptId, pageable);
        if(page.isEmpty()) {
        	throw new RecordNotFoundException(frameworkComponent.resolveI18n("raFormula.noRecords") + " - " + deptId, ENTITY_NAME);
        }
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ra-formulas/:id} : get the "id" raFormula.
     *
     * @param deptId the dept id
     * @param id the id of the raFormulaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raFormulaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/department/{deptId}/ra-formulas/{id}")
    public ResponseEntity<RaFormulaDTO> getRaFormula(
		@PathVariable(value = "deptId", required = true) final Long deptId,
		@PathVariable Long id) {
        log.debug("REST request to get RaFormula : {}", id);
        
        RaFormulaDTO raFormulaDTO = raFormulaService.findByDeptIdAndId(deptId, id).orElseThrow(() -> new RecordNotFoundException(
        		frameworkComponent.resolveI18n("raFormula.invalidId") + " - " + id, ENTITY_NAME));
 
        return ResponseEntity.ok(raFormulaDTO);
    }

    /**
     * {@code DELETE  /ra-formulas/:id} : delete the "id" raFormula.
     *
     * @param deptId the dept id
     * @param id the id of the raFormulaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/department/{deptId}/ra-formulas/{id}")
    public ResponseEntity<Void> deleteRaFormula(
		@PathVariable(value = "deptId", required = true) final Long deptId,
		@PathVariable Long id) {
        log.debug("REST request to delete RaFormula : {}", id);
        
        raFormulaService.findByDeptIdAndId(deptId, id).orElseThrow(() -> new RecordNotFoundException(
        		frameworkComponent.resolveI18n("raFormula.invalidId") + " - " + id, ENTITY_NAME));
 
        raFormulaService.delete(id);
        
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
