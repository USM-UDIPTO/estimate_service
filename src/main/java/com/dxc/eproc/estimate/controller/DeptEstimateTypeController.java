package com.dxc.eproc.estimate.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.repository.DeptEstimateTypeRepository;
import com.dxc.eproc.estimate.service.DeptEstimateTypeService;
import com.dxc.eproc.estimate.service.EstimateTypeService;
import com.dxc.eproc.estimate.service.dto.DeptEstimateTypeDTO;
import com.dxc.eproc.estimate.service.dto.EstimateTypeDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.DeptEstimateType}.
 */
@RestController
@RequestMapping("/v1/api")
public class DeptEstimateTypeController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "deptEstimateType";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(DeptEstimateTypeController.class);

	/** The dept estimate type service. */
	private final DeptEstimateTypeService deptEstimateTypeService;

	/** The dept estimate type repository. */
	private final DeptEstimateTypeRepository deptEstimateTypeRepository;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The estimate type service. */
	@Autowired
	private EstimateTypeService estimateTypeService;

	/**
	 * Instantiates a new dept estimate type controller.
	 *
	 * @param deptEstimateTypeService    the dept estimate type service
	 * @param deptEstimateTypeRepository the dept estimate type repository
	 * @param frameworkComponent         the framework component
	 */
	public DeptEstimateTypeController(DeptEstimateTypeService deptEstimateTypeService, DeptEstimateTypeRepository deptEstimateTypeRepository, FrameworkComponent frameworkComponent) {
		this.deptEstimateTypeService = deptEstimateTypeService;
		this.deptEstimateTypeRepository = deptEstimateTypeRepository;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST  /dept-estimate-types} : Create a new deptEstimateType.
	 *
	 * @param deptEstimateTypeDTO the deptEstimateTypeDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new deptEstimateTypeDTO, or with status
	 *         {@code 400 (Bad Request)} if the deptEstimateType has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/dept-estimate-types")
	public ResponseEntity<DeptEstimateTypeDTO> createDeptEstimateType(
			@Valid @RequestBody DeptEstimateTypeDTO deptEstimateTypeDTO) throws URISyntaxException {
		log.debug("REST request to save DeptEstimateType");
		if (deptEstimateTypeDTO.getId() != null) {
			throw new BadRequestAlertException("A new deptEstimateType cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		deptEstimateTypeDTO.setActiveYn(true);
		DeptEstimateTypeDTO result = deptEstimateTypeService.save(deptEstimateTypeDTO);
		return ResponseEntity
				.created(new URI("/api/dept-estimate-types/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /dept-estimate-types/:id} : Updates an existing deptEstimateType.
	 *
	 * @param id                  the id of the deptEstimateTypeDTO to save.
	 * @param deptEstimateTypeDTO the deptEstimateTypeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated deptEstimateTypeDTO, or with status
	 *         {@code 400 (Bad Request)} if the deptEstimateTypeDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         deptEstimateTypeDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/dept-estimate-types/{id}")
	public ResponseEntity<DeptEstimateTypeDTO> updateDeptEstimateType(
			@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody DeptEstimateTypeDTO deptEstimateTypeDTO) throws URISyntaxException {
		log.debug("REST request to update DeptEstimateType : {}", id);

		if (!deptEstimateTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		deptEstimateTypeDTO.setId(id);
		DeptEstimateTypeDTO result = deptEstimateTypeService.save(deptEstimateTypeDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				deptEstimateTypeDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /dept-estimate-types/:id} : Partial updates given fields of an
	 * existing deptEstimateType, field will ignore if it is null.
	 *
	 * @param id                  the id of the deptEstimateTypeDTO to save.
	 * @param deptEstimateTypeDTO the deptEstimateTypeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated deptEstimateTypeDTO, or with status
	 *         {@code 400 (Bad Request)} if the deptEstimateTypeDTO is not valid, or
	 *         with status {@code 404 (Not Found)} if the deptEstimateTypeDTO is not
	 *         found, or with status {@code 500 (Internal Server Error)} if the
	 *         deptEstimateTypeDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/dept-estimate-types/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<DeptEstimateTypeDTO> partialUpdateDeptEstimateType(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody DeptEstimateTypeDTO deptEstimateTypeDTO) throws URISyntaxException {
		log.debug("REST request to partial update DeptEstimateType partially : {}", id);

		if (!deptEstimateTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		deptEstimateTypeDTO.setId(id);
		Optional<DeptEstimateTypeDTO> result = deptEstimateTypeService.partialUpdate(deptEstimateTypeDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, deptEstimateTypeDTO.getId().toString()));
	}

	/**
	 * {@code GET  /dept-estimate-types} : get all the deptEstimateTypes.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of deptEstimateTypes in body.
	 */
	@GetMapping("/dept-estimate-types")
	public ResponseEntity<List<DeptEstimateTypeDTO>> getAllDeptEstimateTypes(Pageable pageable) {
		log.debug("REST request to get a page of DeptEstimateTypes");
		Page<DeptEstimateTypeDTO> page = deptEstimateTypeService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active dept estimate types.
	 *
	 * @param pageable the pageable
	 * @return the all active dept estimate types
	 */
	@GetMapping("/dept-estimate-types-active")
	public ResponseEntity<List<DeptEstimateTypeDTO>> getAllActiveDeptEstimateTypes(Pageable pageable) {
		log.debug("REST request to get a page of Active DeptEstimateTypes");
		Page<DeptEstimateTypeDTO> page = deptEstimateTypeService.findAllActive(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active dept estimate types for work estimate.
	 *
	 * @param deptId the dept id
	 * @return the all active dept estimate types for work estimate
	 */
	@GetMapping("/dept-master/{deptId}/active-estimate-types")
	public ResponseEntity<List<DeptEstimateTypeDTO>> getAllActiveDeptEstimateTypesForWorkEstimate(
			@PathVariable(value = "deptId", required = true) Long deptId) {
		log.debug("REST request to get all Active DeptEstimateTypes for Work Estimate By deptId - {}" + deptId);
		List<DeptEstimateTypeDTO> deptEstimateTypeDTOList = deptEstimateTypeService
				.getAllActiveDeptEstimateTypesForWorkEstimate(deptId);

		List<EstimateTypeDTO> estimateTypeDTOList = estimateTypeService.findAllActive();

		Map<Long, String> result = new HashMap<>();

		estimateTypeDTOList.stream().forEach(entry -> result.put(entry.getId(), entry.getEstimateTypeValue()));

		deptEstimateTypeDTOList.forEach(deptEstimateType -> {
			deptEstimateType.setEstimateTypeValue(result.get(deptEstimateType.getEstimateTypeId()));
		});

		return ResponseEntity.ok().body(deptEstimateTypeDTOList);
	}

	/**
	 * {@code GET  /dept-estimate-types/:id} : get the "id" deptEstimateType.
	 *
	 * @param id the id of the deptEstimateTypeDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the deptEstimateTypeDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/dept-estimate-types/{id}")
	public ResponseEntity<DeptEstimateTypeDTO> getDeptEstimateType(@PathVariable Long id) {
		log.debug("REST request to get DeptEstimateType : {}", id);
		Optional<DeptEstimateTypeDTO> deptEstimateTypeDTO = deptEstimateTypeService.findOne(id);
		return ResponseUtil.wrapOrNotFound(deptEstimateTypeDTO);
	}

	/**
	 * {@code DELETE  /dept-estimate-types/:id} : delete the "id" deptEstimateType.
	 *
	 * @param id the id of the deptEstimateTypeDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/dept-estimate-types/{id}")
	public ResponseEntity<Void> deleteDeptEstimateType(@PathVariable Long id) {
		log.debug("REST request to delete DeptEstimateType : {}", id);
		if (!deptEstimateTypeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		deptEstimateTypeService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
