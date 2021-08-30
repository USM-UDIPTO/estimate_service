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
import com.dxc.eproc.estimate.repository.EstimateTypeRepository;
import com.dxc.eproc.estimate.service.EstimateTypeService;
import com.dxc.eproc.estimate.service.dto.EstimateTypeDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.EstimateType}.
 */
@RestController
@RequestMapping("/v1/api")
public class EstimateTypeController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "estimateType";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(EstimateTypeController.class);

	/** The estimate type service. */
	private final EstimateTypeService estimateTypeService;

	/** The estimate type repository. */
	private final EstimateTypeRepository estimateTypeRepository;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/**
	 * Instantiates a new estimate type controller.
	 *
	 * @param estimateTypeService    the estimate type service
	 * @param estimateTypeRepository the estimate type repository
	 * @param frameworkComponent     the framework component
	 */
	public EstimateTypeController(EstimateTypeService estimateTypeService,
			EstimateTypeRepository estimateTypeRepository, FrameworkComponent frameworkComponent) {
		this.estimateTypeService = estimateTypeService;
		this.estimateTypeRepository = estimateTypeRepository;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST  /estimate-types} : Create a new estimateType.
	 *
	 * @param estimateTypeDTO the estimateTypeDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new estimateTypeDTO, or with status
	 *         {@code 400 (Bad Request)} if the estimateType has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/estimate-types")
	public ResponseEntity<EstimateTypeDTO> createEstimateType(@Valid @RequestBody EstimateTypeDTO estimateTypeDTO)
			throws URISyntaxException {
		log.debug("REST request to save EstimateType");
		if (estimateTypeDTO.getId() != null) {
			throw new BadRequestAlertException("A new estimateType cannot already have an ID", ENTITY_NAME, "idexists");
		}
		estimateTypeDTO.setActiveYn(true);
		EstimateTypeDTO result = estimateTypeService.save(estimateTypeDTO);
		return ResponseEntity
				.created(new URI("/api/estimate-types/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /estimate-types/:id} : Updates an existing estimateType.
	 *
	 * @param id              the id of the estimateTypeDTO to save.
	 * @param estimateTypeDTO the estimateTypeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated estimateTypeDTO, or with status {@code 400 (Bad Request)}
	 *         if the estimateTypeDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the estimateTypeDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/estimate-types/{id}")
	public ResponseEntity<EstimateTypeDTO> updateEstimateType(
			@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody EstimateTypeDTO estimateTypeDTO) throws URISyntaxException {
		log.debug("REST request to update EstimateType : {}", id);

		if (!estimateTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("estimateType.idNotAvailable"),
					ENTITY_NAME, "idnotfound");
		}

		EstimateTypeDTO result = estimateTypeService.save(estimateTypeDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				estimateTypeDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /estimate-types/:id} : Partial updates given fields of an
	 * existing estimateType, field will ignore if it is null.
	 *
	 * @param id              the id of the estimateTypeDTO to save.
	 * @param estimateTypeDTO the estimateTypeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated estimateTypeDTO, or with status {@code 400 (Bad Request)}
	 *         if the estimateTypeDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the estimateTypeDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the estimateTypeDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/estimate-types/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<EstimateTypeDTO> partialUpdateEstimateType(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody EstimateTypeDTO estimateTypeDTO) throws URISyntaxException {
		log.debug("REST request to partial update EstimateType partially : {}", id);

		if (!estimateTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("estimateType.idNotAvailable"),
					ENTITY_NAME, "idnotfound");
		}
		estimateTypeDTO.setId(id);
		Optional<EstimateTypeDTO> result = estimateTypeService.partialUpdate(estimateTypeDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, estimateTypeDTO.getId().toString()));
	}

	/**
	 * {@code GET  /estimate-types} : get all the estimateTypes.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of estimateTypes in body.
	 */
	@GetMapping("/estimate-types")
	public ResponseEntity<List<EstimateTypeDTO>> getAllEstimateTypes(Pageable pageable) {
		log.debug("REST request to get a page of EstimateTypes");
		Page<EstimateTypeDTO> page = estimateTypeService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active estimate types.
	 *
	 * @param pageable the pageable
	 * @return the all active estimate types
	 */
	@GetMapping("/estimate-types-active")
	public ResponseEntity<List<EstimateTypeDTO>> getAllActiveEstimateTypes(Pageable pageable) {
		log.debug("REST request to get a page of Active EstimateTypes");
		Page<EstimateTypeDTO> page = estimateTypeService.findAllActive(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /estimate-types/:id} : get the "id" estimateType.
	 *
	 * @param id the id of the estimateTypeDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the estimateTypeDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/estimate-types/{id}")
	public ResponseEntity<EstimateTypeDTO> getEstimateType(@PathVariable Long id) {
		log.debug("REST request to get EstimateType : {}", id);
		Optional<EstimateTypeDTO> estimateTypeDTO = estimateTypeService.findOne(id);
		return ResponseUtil.wrapOrNotFound(estimateTypeDTO);
	}

	/**
	 * {@code DELETE  /estimate-types/:id} : delete the "id" estimateType.
	 *
	 * @param id the id of the estimateTypeDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/estimate-types/{id}")
	public ResponseEntity<Void> deleteEstimateType(@PathVariable Long id) {
		log.debug("REST request to delete EstimateType : {}", id);
		if (!estimateTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("estimateType.idNotAvailable"),
					ENTITY_NAME, "idnotfound");
		}
		estimateTypeService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
