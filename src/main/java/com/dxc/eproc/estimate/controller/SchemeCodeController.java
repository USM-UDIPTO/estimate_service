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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.config.FrameworkComponent;
import com.dxc.eproc.estimate.enumeration.SchemeType;
import com.dxc.eproc.estimate.repository.SchemeCodeRepository;
import com.dxc.eproc.estimate.service.SchemeCodeService;
import com.dxc.eproc.estimate.service.dto.SchemeCodeDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing {@link com.dxc.eproc.estimate.model.SchemeCode}.
 */
@RestController
@RequestMapping("/v1/api")
public class SchemeCodeController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "schemeCode";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SchemeCodeController.class);

	/** The scheme code service. */
	private final SchemeCodeService schemeCodeService;

	/** The scheme code repository. */
	private final SchemeCodeRepository schemeCodeRepository;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/**
	 * Instantiates a new scheme code controller.
	 *
	 * @param schemeCodeService    the scheme code service
	 * @param schemeCodeRepository the scheme code repository
	 * @param frameworkComponent   the framework component
	 */
	public SchemeCodeController(SchemeCodeService schemeCodeService, SchemeCodeRepository schemeCodeRepository,
			FrameworkComponent frameworkComponent) {
		this.schemeCodeService = schemeCodeService;
		this.schemeCodeRepository = schemeCodeRepository;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST  /scheme-codes} : Create a new schemeCode.
	 *
	 * @param schemeCodeDTO the schemeCodeDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new schemeCodeDTO, or with status {@code 400 (Bad Request)}
	 *         if the schemeCode has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/scheme-codes")
	public ResponseEntity<SchemeCodeDTO> createSchemeCode(@Valid @RequestBody SchemeCodeDTO schemeCodeDTO)
			throws URISyntaxException {
		log.debug("REST request to save SchemeCode");
		if (schemeCodeDTO.getId() != null) {
			throw new BadRequestAlertException("A new schemeCode cannot already have an ID", ENTITY_NAME, "idexists");
		}
		schemeCodeDTO.setActiveYn(true);
		SchemeCodeDTO result = schemeCodeService.save(schemeCodeDTO);
		return ResponseEntity
				.created(new URI("/api/scheme-codes/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /scheme-codes/:id} : Updates an existing schemeCode.
	 *
	 * @param id            the id of the schemeCodeDTO to save.
	 * @param schemeCodeDTO the schemeCodeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated schemeCodeDTO, or with status {@code 400 (Bad Request)}
	 *         if the schemeCodeDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the schemeCodeDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/scheme-codes/{id}")
	public ResponseEntity<SchemeCodeDTO> updateSchemeCode(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody SchemeCodeDTO schemeCodeDTO) throws URISyntaxException {
		log.debug("REST request to update SchemeCode : {}", id);

		if (!schemeCodeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}

		SchemeCodeDTO result = schemeCodeService.save(schemeCodeDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				schemeCodeDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /scheme-codes/:id} : Partial updates given fields of an
	 * existing schemeCode, field will ignore if it is null.
	 *
	 * @param id            the id of the schemeCodeDTO to save.
	 * @param schemeCodeDTO the schemeCodeDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated schemeCodeDTO, or with status {@code 400 (Bad Request)}
	 *         if the schemeCodeDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the schemeCodeDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the schemeCodeDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/scheme-codes/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<SchemeCodeDTO> partialUpdateSchemeCode(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody SchemeCodeDTO schemeCodeDTO) throws URISyntaxException {
		log.debug("REST request to partial update SchemeCode partially : {}", id);

		if (!schemeCodeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		schemeCodeDTO.setId(id);
		Optional<SchemeCodeDTO> result = schemeCodeService.partialUpdate(schemeCodeDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, schemeCodeDTO.getId().toString()));
	}

	/**
	 * {@code GET  /scheme-codes} : get all the schemeCodes.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of schemeCodes in body.
	 */
	@GetMapping("/scheme-codes")
	public ResponseEntity<List<SchemeCodeDTO>> getAllSchemeCodes(Pageable pageable) {
		log.debug("REST request to get a page of SchemeCodes");
		Page<SchemeCodeDTO> page = schemeCodeService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active scheme codes.
	 *
	 * @param pageable the pageable
	 * @return the all active scheme codes
	 */
	@GetMapping("/scheme-codes-active")
	public ResponseEntity<List<SchemeCodeDTO>> getAllActiveSchemeCodes(Pageable pageable) {
		log.debug("REST request to get a page of SchemeCodes");
		Page<SchemeCodeDTO> page = schemeCodeService.findAllActive(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /scheme-codes/:id} : get the "id" schemeCode.
	 *
	 * @param id the id of the schemeCodeDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the schemeCodeDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/scheme-codes/{id}")
	public ResponseEntity<SchemeCodeDTO> getSchemeCode(@PathVariable Long id) {
		log.debug("REST request to get SchemeCode : {}", id);
		Optional<SchemeCodeDTO> schemeCodeDTO = schemeCodeService.findOne(id);
		return ResponseUtil.wrapOrNotFound(schemeCodeDTO);
	}

	/**
	 * Gets the all by scheme type and scheme code.
	 *
	 * @param locationId the location id
	 * @param schemeType the scheme type
	 * @param schemeCode the scheme code
	 * @return the all by scheme type and scheme code
	 */
	@GetMapping("/search-scheme-codes")
	public ResponseEntity<List<SchemeCodeDTO>> getAllBySchemeTypeAndSchemeCode(
			@RequestParam(value = "location-id", required = true) Long locationId,
			@RequestParam(value = "scheme-type", required = true) SchemeType schemeType,
			@RequestParam(value = "scheme-code", required = true) String schemeCode) {
		log.debug("REST request to get getAllBySchemeTypeAndSchemeCode. SchemeType : {} - SchemeCode : {} ", schemeType,
				schemeCode);
		List<SchemeCodeDTO> schemeCodeDTOList = schemeCodeService
				.findAllByLocationIdAndSchemeTypeAndSchemeCode(locationId, schemeType, schemeCode);
		if (schemeCodeDTOList.size() == 0) {
			throw new BadRequestAlertException(
					frameworkComponent.resolveI18n("badRequestAlertException.noSearchResult"), ENTITY_NAME,
					"nosearchresult");
		}
		return ResponseEntity.ok().body(schemeCodeDTOList);
	}

	/**
	 * {@code DELETE  /scheme-codes/:id} : delete the "id" schemeCode.
	 *
	 * @param id the id of the schemeCodeDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/scheme-codes/{id}")
	public ResponseEntity<Void> deleteSchemeCode(@PathVariable Long id) {
		log.debug("REST request to delete SchemeCode : {}", id);
		if (!schemeCodeRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		schemeCodeService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
