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
import com.dxc.eproc.estimate.repository.WorkCategoryRepository;
import com.dxc.eproc.estimate.service.WorkCategoryService;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.WorkCategory}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkCategoryController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workCategory";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkCategoryController.class);

	/** The work category service. */
	private final WorkCategoryService workCategoryService;

	/** The work category repository. */
	private final WorkCategoryRepository workCategoryRepository;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/**
	 * Instantiates a new work category controller.
	 *
	 * @param workCategoryService    the work category service
	 * @param workCategoryRepository the work category repository
	 * @param frameworkComponent     the framework component
	 */
	public WorkCategoryController(WorkCategoryService workCategoryService,
			WorkCategoryRepository workCategoryRepository, FrameworkComponent frameworkComponent) {
		this.workCategoryService = workCategoryService;
		this.workCategoryRepository = workCategoryRepository;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST  /work-categories} : Create a new workCategory.
	 *
	 * @param workCategoryDTO the workCategoryDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workCategoryDTO, or with status
	 *         {@code 400 (Bad Request)} if the workCategory has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-categories")
	public ResponseEntity<WorkCategoryDTO> createWorkCategory(@Valid @RequestBody WorkCategoryDTO workCategoryDTO)
			throws URISyntaxException {
		log.debug("REST request to save WorkCategory");
		if (workCategoryDTO.getId() != null) {
			throw new BadRequestAlertException("A new workCategory cannot already have an ID", ENTITY_NAME, "idexists");
		}
		workCategoryDTO.setActiveYn(true);
		WorkCategoryDTO result = workCategoryService.save(workCategoryDTO);
		return ResponseEntity
				.created(new URI("/api/work-categories/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-categories/:id} : Updates an existing workCategory.
	 *
	 * @param id              the id of the workCategoryDTO to save.
	 * @param workCategoryDTO the workCategoryDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workCategoryDTO, or with status {@code 400 (Bad Request)}
	 *         if the workCategoryDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the workCategoryDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-categories/{id}")
	public ResponseEntity<WorkCategoryDTO> updateWorkCategory(
			@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody WorkCategoryDTO workCategoryDTO) throws URISyntaxException {
		log.debug("REST request to update WorkCategory : {}", id);

		if (!workCategoryRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}

		WorkCategoryDTO result = workCategoryService.save(workCategoryDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workCategoryDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /work-categories/:id} : Partial updates given fields of an
	 * existing workCategory, field will ignore if it is null.
	 *
	 * @param id              the id of the workCategoryDTO to save.
	 * @param workCategoryDTO the workCategoryDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workCategoryDTO, or with status {@code 400 (Bad Request)}
	 *         if the workCategoryDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the workCategoryDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the workCategoryDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/work-categories/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<WorkCategoryDTO> partialUpdateWorkCategory(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody WorkCategoryDTO workCategoryDTO) throws URISyntaxException {
		log.debug("REST request to partial update WorkCategory partially : {}", id);

		if (!workCategoryRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		workCategoryDTO.setId(id);
		Optional<WorkCategoryDTO> result = workCategoryService.partialUpdate(workCategoryDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, workCategoryDTO.getId().toString()));
	}

	/**
	 * {@code GET  /work-categories} : get all the workCategories.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workCategories in body.
	 */
	@GetMapping("/work-categories")
	public ResponseEntity<List<WorkCategoryDTO>> getAllWorkCategories(Pageable pageable) {
		log.debug("REST request to get a page of WorkCategories");
		Page<WorkCategoryDTO> page = workCategoryService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active work categories.
	 *
	 * @param pageable the pageable
	 * @return the all active work categories
	 */
	@GetMapping("/work-categories-active")
	public ResponseEntity<List<WorkCategoryDTO>> getAllActiveWorkCategories(Pageable pageable) {
		log.debug("REST request to get a page of WorkCategories");
		Page<WorkCategoryDTO> page = workCategoryService.findAllActive(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active work categories list.
	 *
	 * @return the all active work categories list
	 */
	@GetMapping("/active-work-categories")
	public ResponseEntity<List<WorkCategoryDTO>> getAllActiveWorkCategoriesList() {
		log.debug("REST request to get a page of WorkCategories");
		List<WorkCategoryDTO> workCategoryDTOList = workCategoryService.findAllActive();
		return ResponseEntity.ok().body(workCategoryDTOList);
	}

	/**
	 * {@code GET  /work-categories/:id} : get the "id" workCategory.
	 *
	 * @param id the id of the workCategoryDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workCategoryDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-categories/{id}")
	public ResponseEntity<WorkCategoryDTO> getWorkCategory(@PathVariable Long id) {
		log.debug("REST request to get WorkCategory : {}", id);
		Optional<WorkCategoryDTO> workCategoryDTO = workCategoryService.findOne(id);
		return ResponseUtil.wrapOrNotFound(workCategoryDTO);
	}

	/**
	 * {@code DELETE  /work-categories/:id} : delete the "id" workCategory.
	 *
	 * @param id the id of the workCategoryDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-categories/{id}")
	public ResponseEntity<Void> deleteWorkCategory(@PathVariable Long id) {
		log.debug("REST request to delete WorkCategory : {}", id);
		if (!workCategoryRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		workCategoryService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
