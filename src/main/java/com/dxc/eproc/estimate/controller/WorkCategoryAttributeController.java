package com.dxc.eproc.estimate.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.dxc.eproc.estimate.repository.WorkCategoryAttributeRepository;
import com.dxc.eproc.estimate.service.WorkCategoryAttributeService;
import com.dxc.eproc.estimate.service.dto.WorkCategoryAttributeDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkCategoryAttribute}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkCategoryAttributeController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workCategoryAttribute";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkCategoryAttributeController.class);

	/** The work category attribute service. */
	private final WorkCategoryAttributeService workCategoryAttributeService;

	/** The work category attribute repository. */
	private final WorkCategoryAttributeRepository workCategoryAttributeRepository;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/**
	 * Instantiates a new work category attribute controller.
	 *
	 * @param workCategoryAttributeService    the work category attribute service
	 * @param workCategoryAttributeRepository the work category attribute repository
	 * @param frameworkComponent              the framework component
	 */
	public WorkCategoryAttributeController(WorkCategoryAttributeService workCategoryAttributeService,
			WorkCategoryAttributeRepository workCategoryAttributeRepository, FrameworkComponent frameworkComponent) {
		this.workCategoryAttributeService = workCategoryAttributeService;
		this.workCategoryAttributeRepository = workCategoryAttributeRepository;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST  /work-category-attributes} : Create a new workCategoryAttribute.
	 *
	 * @param workCategoryAttributeDTO the workCategoryAttributeDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workCategoryAttributeDTO, or with status
	 *         {@code 400 (Bad Request)} if the workCategoryAttribute has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-category-attributes")
	public ResponseEntity<WorkCategoryAttributeDTO> createWorkCategoryAttribute(
			@Valid @RequestBody WorkCategoryAttributeDTO workCategoryAttributeDTO) throws URISyntaxException {
		log.debug("REST request to save WorkCategoryAttribute : {}", workCategoryAttributeDTO);
		if (workCategoryAttributeDTO.getId() != null) {
			throw new BadRequestAlertException("A new workCategoryAttribute cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		workCategoryAttributeDTO.setActiveYn(true);
		WorkCategoryAttributeDTO result = workCategoryAttributeService.save(workCategoryAttributeDTO);
		return ResponseEntity
				.created(new URI("/api/work-category-attributes/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-category-attributes/:id} : Updates an existing
	 * workCategoryAttribute.
	 *
	 * @param id                       the id of the workCategoryAttributeDTO to
	 *                                 save.
	 * @param workCategoryAttributeDTO the workCategoryAttributeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workCategoryAttributeDTO, or with status
	 *         {@code 400 (Bad Request)} if the workCategoryAttributeDTO is not
	 *         valid, or with status {@code 500 (Internal Server Error)} if the
	 *         workCategoryAttributeDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-category-attributes/{id}")
	public ResponseEntity<WorkCategoryAttributeDTO> updateWorkCategoryAttribute(
			@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody WorkCategoryAttributeDTO workCategoryAttributeDTO) throws URISyntaxException {
		log.debug("REST request to update WorkCategoryAttribute : {}", id);

		if (!workCategoryAttributeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}

		WorkCategoryAttributeDTO result = workCategoryAttributeService.save(workCategoryAttributeDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workCategoryAttributeDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /work-category-attributes/:id} : Partial updates given fields
	 * of an existing workCategoryAttribute, field will ignore if it is null.
	 *
	 * @param id                       the id of the workCategoryAttributeDTO to
	 *                                 save.
	 * @param workCategoryAttributeDTO the workCategoryAttributeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workCategoryAttributeDTO, or with status
	 *         {@code 400 (Bad Request)} if the workCategoryAttributeDTO is not
	 *         valid, or with status {@code 404 (Not Found)} if the
	 *         workCategoryAttributeDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the workCategoryAttributeDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/work-category-attributes/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<WorkCategoryAttributeDTO> partialUpdateWorkCategoryAttribute(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody WorkCategoryAttributeDTO workCategoryAttributeDTO) throws URISyntaxException {
		log.debug("REST request to partial update WorkCategoryAttribute partially : {}, {}", id,
				workCategoryAttributeDTO);

		if (!workCategoryAttributeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		workCategoryAttributeDTO.setId(id);
		Optional<WorkCategoryAttributeDTO> result = workCategoryAttributeService
				.partialUpdate(workCategoryAttributeDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, workCategoryAttributeDTO.getId().toString()));
	}

	/**
	 * {@code GET  /work-category-attributes} : get all the workCategoryAttributes.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workCategoryAttributes in body.
	 */
	@GetMapping("/work-category-attributes")
	public ResponseEntity<List<WorkCategoryAttributeDTO>> getAllWorkCategoryAttributes(Pageable pageable) {
		log.debug("REST request to get a page of WorkCategoryAttributes");
		Page<WorkCategoryAttributeDTO> page = workCategoryAttributeService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active work category attributes.
	 *
	 * @param pageable the pageable
	 * @return the all active work category attributes
	 */
	@GetMapping("/work-category-attributes-active")
	public ResponseEntity<List<WorkCategoryAttributeDTO>> getAllActiveWorkCategoryAttributes(Pageable pageable) {
		log.debug("REST request to get a page of Active WorkCategoryAttributes");
		Page<WorkCategoryAttributeDTO> page = workCategoryAttributeService.findAllActive(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active work category attributes list.
	 *
	 * @param workTypeId     the work type id
	 * @param workCategoryId the work category id
	 * @return the all active work category attributes list
	 */
	@GetMapping("/estimate-work-type/{workTypeId}/work-category/{workCategoryId}/active-attributes")
	public ResponseEntity<List<WorkCategoryAttributeDTO>> getAllActiveWorkCategoryAttributesList(
			@PathVariable(value = "workTypeId", required = true) final Long workTypeId,
			@PathVariable(value = "workCategoryId", required = true) final Long workCategoryId) {
		log.debug("REST request to get a page of Active WorkCategoryAttributes");
		List<WorkCategoryAttributeDTO> workCategoryAttributeDTOList = workCategoryAttributeService
				.findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn(workTypeId, workCategoryId);

		return ResponseEntity.ok().body(workCategoryAttributeDTOList);
	}

	/**
	 * {@code GET  /work-category-attributes/:id} : get the "id"
	 * workCategoryAttribute.
	 *
	 * @param id the id of the workCategoryAttributeDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workCategoryAttributeDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-category-attributes/{id}")
	public ResponseEntity<WorkCategoryAttributeDTO> getWorkCategoryAttribute(@PathVariable Long id) {
		log.debug("REST request to get WorkCategoryAttribute : {}", id);
		Optional<WorkCategoryAttributeDTO> workCategoryAttributeDTO = workCategoryAttributeService.findOne(id);
		return ResponseUtil.wrapOrNotFound(workCategoryAttributeDTO);
	}

	/**
	 * {@code DELETE  /work-category-attributes/:id} : delete the "id"
	 * workCategoryAttribute.
	 *
	 * @param id the id of the workCategoryAttributeDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-category-attributes/{id}")
	public ResponseEntity<Void> deleteWorkCategoryAttribute(@PathVariable Long id) {
		log.debug("REST request to delete WorkCategoryAttribute : {}", id);
		if (!workCategoryAttributeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		workCategoryAttributeService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
