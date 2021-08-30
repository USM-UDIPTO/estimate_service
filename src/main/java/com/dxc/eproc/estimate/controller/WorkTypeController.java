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
import com.dxc.eproc.estimate.repository.WorkTypeRepository;
import com.dxc.eproc.estimate.service.WorkTypeService;
import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing {@link com.dxc.eproc.estimate.model.WorkType}.
 */
@RestController
@RequestMapping("/v1/api")
public class WorkTypeController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "workType";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkTypeController.class);

	/** The work type service. */
	private final WorkTypeService workTypeService;

	/** The work type repository. */
	private final WorkTypeRepository workTypeRepository;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/**
	 * Instantiates a new work type controller.
	 *
	 * @param workTypeService    the work type service
	 * @param workTypeRepository the work type repository
	 * @param frameworkComponent the framework component
	 */
	public WorkTypeController(WorkTypeService workTypeService, WorkTypeRepository workTypeRepository,
			FrameworkComponent frameworkComponent) {
		this.workTypeService = workTypeService;
		this.workTypeRepository = workTypeRepository;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST  /work-types} : Create a new workType.
	 *
	 * @param workTypeDTO the workTypeDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workTypeDTO, or with status {@code 400 (Bad Request)} if
	 *         the workType has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-types")
	public ResponseEntity<WorkTypeDTO> createWorkType(@Valid @RequestBody WorkTypeDTO workTypeDTO)
			throws URISyntaxException {
		log.debug("REST request to save WorkType");
		if (workTypeDTO.getId() != null) {
			throw new BadRequestAlertException("A new workType cannot already have an ID", ENTITY_NAME, "idexists");
		}
		workTypeDTO.setActiveYn(true);
		WorkTypeDTO result = workTypeService.save(workTypeDTO);
		return ResponseEntity
				.created(new URI("/api/work-types/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-types/:id} : Updates an existing workType.
	 *
	 * @param id          the id of the workTypeDTO to save.
	 * @param workTypeDTO the workTypeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workTypeDTO, or with status {@code 400 (Bad Request)} if
	 *         the workTypeDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the workTypeDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-types/{id}")
	public ResponseEntity<WorkTypeDTO> updateWorkType(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody WorkTypeDTO workTypeDTO) throws URISyntaxException {
		log.debug("REST request to update WorkType : {}", id);

		if (!workTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}

		WorkTypeDTO result = workTypeService.save(workTypeDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workTypeDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /work-types/:id} : Partial updates given fields of an existing
	 * workType, field will ignore if it is null.
	 *
	 * @param id          the id of the workTypeDTO to save.
	 * @param workTypeDTO the workTypeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workTypeDTO, or with status {@code 400 (Bad Request)} if
	 *         the workTypeDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the workTypeDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the workTypeDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/work-types/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<WorkTypeDTO> partialUpdateWorkType(
			@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody WorkTypeDTO workTypeDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update WorkType partially : {}, {}", id, workTypeDTO);

		if (!workTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		workTypeDTO.setId(id);
		Optional<WorkTypeDTO> result = workTypeService.partialUpdate(workTypeDTO);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workTypeDTO.getId().toString()));
	}

	/**
	 * {@code GET  /work-types} : get all the workTypes.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workTypes in body.
	 */
	@GetMapping("/work-types")
	public ResponseEntity<List<WorkTypeDTO>> getAllWorkTypes(Pageable pageable) {
		log.debug("REST request to get a page of WorkTypes");
		Page<WorkTypeDTO> page = workTypeService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active work types.
	 *
	 * @param pageable the pageable
	 * @return the all active work types
	 */
	@GetMapping("/work-types-active")
	public ResponseEntity<List<WorkTypeDTO>> getAllActiveWorkTypes(Pageable pageable) {
		log.debug("REST request to get a page of WorkTypes");
		Page<WorkTypeDTO> page = workTypeService.findAllActive(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active estimate work types.
	 *
	 * @return the all active estimate work types
	 */
	@GetMapping("/active-work-types")
	public ResponseEntity<List<WorkTypeDTO>> getAllActiveEstimateWorkTypes() {
		log.debug("REST request to get a page of WorkTypes");
		List<WorkTypeDTO> workTypeDTOList = workTypeService.findAllActiveEstimateWorkTypes();

		return ResponseEntity.ok().body(workTypeDTOList);
	}

	/**
	 * {@code GET  /work-types/:id} : get the "id" workType.
	 *
	 * @param id the id of the workTypeDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workTypeDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-types/{id}")
	public ResponseEntity<WorkTypeDTO> getWorkType(@PathVariable Long id) {
		log.debug("REST request to get WorkType : {}", id);
		Optional<WorkTypeDTO> workTypeDTO = workTypeService.findOne(id);
		return ResponseUtil.wrapOrNotFound(workTypeDTO);
	}

	/**
	 * {@code DELETE  /work-types/:id} : delete the "id" workType.
	 *
	 * @param id the id of the workTypeDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-types/{id}")
	public ResponseEntity<Void> deleteWorkType(@PathVariable Long id) {
		log.debug("REST request to delete WorkType : {}", id);
		if (!workTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		workTypeService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
