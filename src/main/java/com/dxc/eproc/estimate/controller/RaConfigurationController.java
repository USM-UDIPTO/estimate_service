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
import org.springframework.transaction.annotation.Transactional;
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
import com.dxc.eproc.estimate.service.RaConfigurationService;
import com.dxc.eproc.estimate.service.dto.RaConfigurationDTO;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.domain.RaConfiguration}.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class RaConfigurationController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(RaConfigurationController.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "raConfiguration";

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The ra configuration service. */
	@Autowired
	private RaConfigurationService raConfigurationService;

	/** The framework component. */
	@Autowired
	private FrameworkComponent frameworkComponent;

	/**
	 * {@code POST  /ra-configuration} : Create a new raConfiguration.
	 *
	 * @param raConfigurationDTO the raConfigurationDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new raConfigurationDTO, or with status
	 *         {@code 400 (Bad Request)} if the raConfiguration has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/ra-configuration")
	public ResponseEntity<RaConfigurationDTO> createRaConfiguration(
			@Valid @RequestBody RaConfigurationDTO raConfigurationDTO) throws URISyntaxException {
		log.debug("REST request to save RaConfiguration : {}", raConfigurationDTO);
		raConfigurationDTO.setId(null);
		RaConfigurationDTO result = raConfigurationService.save(raConfigurationDTO);
		return ResponseEntity
				.created(new URI("/api/ra-configurations/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /ra-configuration/:id} : Updates an existing raConfiguration.
	 *
	 * @param id                 the id of the raConfigurationDTO to save.
	 * @param raConfigurationDTO the raConfigurationDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated raConfigurationDTO, or with status
	 *         {@code 400 (Bad Request)} if the raConfigurationDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         raConfigurationDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/ra-configurations/{id}")
	public ResponseEntity<RaConfigurationDTO> updateRaConfiguration(
			@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody RaConfigurationDTO raConfigurationDTO) throws URISyntaxException {
		log.debug("REST request to update RaConfiguration : {}, {}", id, raConfigurationDTO);

		raConfigurationService.findOne(id).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));
		raConfigurationDTO.setId(id);
		RaConfigurationDTO result = raConfigurationService.save(raConfigurationDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				raConfigurationDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /ra-configuration} : get all the raConfigurations.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of raConfigurations in body.
	 */
	@GetMapping("/ra-configuration")
	public ResponseEntity<List<RaConfigurationDTO>> getAllRaConfigurations() {
		log.debug("REST request to get a page of RaConfigurations");
		List<RaConfigurationDTO> raConfigurationList = raConfigurationService.findAll();
		if (raConfigurationList.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".noRecords"), ENTITY_NAME);
		}
		return ResponseEntity.ok().body(raConfigurationList);
	}

	/**
	 * Gets the all ra configurations with pagination.
	 *
	 * @param pageable the pageable
	 * @return the all ra configurations with pagination
	 */
	@GetMapping("/ra-configuration/paged")
	public ResponseEntity<List<RaConfigurationDTO>> getAllRaConfigurations_withPagination(Pageable pageable) {
		log.debug("REST request to get a page of RaConfigurations");
		Page<RaConfigurationDTO> page = raConfigurationService.findAll(pageable);
		if (page.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".noRecords"), ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /ra-configurations/:id} : get the "id" raConfiguration.
	 *
	 * @param id the id of the raConfigurationDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the raConfigurationDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/ra-configuration/{id}")
	public ResponseEntity<RaConfigurationDTO> getRaConfiguration(@PathVariable Long id) {
		log.debug("REST request to get RaConfiguration : {}", id);
		RaConfigurationDTO raConfigurationDTO = raConfigurationService.findOne(id).orElseThrow(
				() -> new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"),
						ENTITY_NAME));
		return ResponseEntity.ok(raConfigurationDTO);
	}

	/**
	 * {@code DELETE  /ra-configurations/:id} : delete the "id" raConfiguration.
	 *
	 * @param id the id of the raConfigurationDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/ra-configuration/{id}")
	public ResponseEntity<Void> deleteRaConfiguration(@PathVariable Long id) {
		log.debug("REST request to delete RaConfiguration : {}", id);
		raConfigurationService.findOne(id).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));
		raConfigurationService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
