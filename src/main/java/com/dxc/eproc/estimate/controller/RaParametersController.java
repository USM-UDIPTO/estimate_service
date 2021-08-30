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
import com.dxc.eproc.estimate.service.RaParametersService;
import com.dxc.eproc.estimate.service.dto.RaParametersDTO;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.RaParameters}.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class RaParametersController {

	private final Logger log = LoggerFactory.getLogger(RaParametersController.class);

	private static final String ENTITY_NAME = "raParameters";

	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	@Autowired
	private RaParametersService raParametersService;

	@Autowired
	private FrameworkComponent frameworkComponent;

	/**
	 * {@code POST  /ra-parameters} : Create a new raParameters.
	 *
	 * @param raParametersDTO the raParametersDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new raParametersDTO, or with status
	 *         {@code 400 (Bad Request)} if the raParameters has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/ra-parameters")
	public ResponseEntity<RaParametersDTO> createRaParameters(@Valid @RequestBody RaParametersDTO raParametersDTO)
			throws URISyntaxException {
		log.debug("REST request to save RaParameters : {}", raParametersDTO);
		raParametersDTO.setId(null);
		RaParametersDTO result = raParametersService.save(raParametersDTO);
		return ResponseEntity
				.created(new URI("/api/ra-parameters/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /ra-parameters/:id} : Updates an existing raParameters.
	 *
	 * @param id              the id of the raParametersDTO to save.
	 * @param raParametersDTO the raParametersDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated raParametersDTO, or with status {@code 400 (Bad Request)}
	 *         if the raParametersDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the raParametersDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/ra-parameters/{id}")
	public ResponseEntity<RaParametersDTO> updateRaParameters(@PathVariable("id") Long id,
			@Valid @RequestBody RaParametersDTO raParametersDTO) throws URISyntaxException {
		log.debug("REST request to update RaParameters : {}, {}", id, raParametersDTO);

		raParametersService.findOne(id).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));
		raParametersDTO.setId(id);
		RaParametersDTO result = raParametersService.save(raParametersDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				raParametersDTO.getId().toString())).body(result);
	}

	@GetMapping("/ra-parameters")
	public ResponseEntity<List<RaParametersDTO>> getAllRaParameters() {
		log.debug("REST request to get a page of RaParameters");
		List<RaParametersDTO> raParametersList = raParametersService.findAll();
		if (raParametersList.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".noRecords"), ENTITY_NAME);
		}
		return ResponseEntity.ok().body(raParametersList);
	}

	/**
	 * {@code GET  /ra-parameters} : get all the raParameters.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of raParameters in body.
	 */
	@GetMapping("/ra-parameters/paged")
	public ResponseEntity<List<RaParametersDTO>> getAllRaParameters_withPagination(Pageable pageable) {
		log.debug("REST request to get a page of RaParameters");
		Page<RaParametersDTO> page = raParametersService.findAll(pageable);
		if (page.isEmpty()) {
			throw new RecordNotFoundException(frameworkComponent.resolveI18n(ENTITY_NAME + ".noRecords"), ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /ra-parameters/:id} : get the "id" raParameters.
	 *
	 * @param id the id of the raParametersDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the raParametersDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/ra-parameters/{id}")
	public ResponseEntity<RaParametersDTO> getRaParameters(@PathVariable Long id) {
		log.debug("REST request to get RaParameters : {}", id);
		RaParametersDTO raParametersDTO = raParametersService.findOne(id).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));
		return ResponseEntity.ok(raParametersDTO);
	}

	/**
	 * {@code DELETE  /ra-parameters/:id} : delete the "id" raParameters.
	 *
	 * @param id the id of the raParametersDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/ra-parameters/{id}")
	public ResponseEntity<Void> deleteRaParameters(@PathVariable Long id) {
		log.debug("REST request to delete RaParameters : {}", id);
		raParametersService.findOne(id).orElseThrow(() -> new RecordNotFoundException(
				frameworkComponent.resolveI18n(ENTITY_NAME + ".invalidId"), ENTITY_NAME));
		raParametersService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
