package com.dxc.eproc.estimate.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
import com.dxc.eproc.estimate.repository.OverHeadRepository;
import com.dxc.eproc.estimate.service.OverHeadService;
import com.dxc.eproc.estimate.service.dto.OverHeadDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing {@link com.dxc.eproc.estimate.domain.OverHead}.
 */
@RestController
@RequestMapping("/v1/api")
public class OverHeadController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(OverHeadController.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "overHead";

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The over head service. */
	@Autowired
	private OverHeadService overHeadService;

	/** The over head repository. */
	@Autowired
	private OverHeadRepository overHeadRepository;

	/** The frame work component. */
	@Autowired
	private FrameworkComponent frameWorkComponent;

	/**
	 * Instantiates a new over head controller.
	 */
	public OverHeadController() {

	}

	/**
	 * {@code POST  /over-heads} : Create a new overHead.
	 *
	 * @param overHeadDTO the overHeadDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new overHeadDTO, or with status {@code 400 (Bad Request)} if
	 *         the overHead has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/overhead")
	public ResponseEntity<OverHeadDTO> createOverHead(@Valid @RequestBody OverHeadDTO overHeadDTO)
			throws URISyntaxException {
		log.debug("REST request to save OverHead : {}", overHeadDTO);
		if (overHeadDTO.getId() != null) {
			throw new BadRequestAlertException("A new overHead cannot already have an ID", ENTITY_NAME, "idexists");
		}
		overHeadDTO.setActiveYn(true);
		OverHeadDTO result = overHeadService.save(overHeadDTO);
		return ResponseEntity
				.created(new URI("/v1/api/overhead/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /over-heads/:id} : Updates an existing overHead.
	 *
	 * @param id          the id of the overHeadDTO to save.
	 * @param overHeadDTO the overHeadDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated overHeadDTO, or with status {@code 400 (Bad Request)} if
	 *         the overHeadDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the overHeadDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/overhead/{id}")
	public ResponseEntity<OverHeadDTO> updateOverHead(@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody OverHeadDTO overHeadDTO) throws URISyntaxException {
		log.debug("REST request to update OverHead : {}, {}", id, overHeadDTO);

		if (!overHeadRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
		overHeadDTO.setId(id);
		OverHeadDTO result = overHeadService.save(overHeadDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, overHeadDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /over-heads/:id} : Partial updates given fields of an existing
	 * overHead, field will ignore if it is null.
	 *
	 * @param id          the id of the overHeadDTO to save.
	 * @param overHeadDTO the overHeadDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated overHeadDTO, or with status {@code 400 (Bad Request)} if
	 *         the overHeadDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the overHeadDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the overHeadDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/overhead/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<OverHeadDTO> partialUpdateOverHead(
			@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody OverHeadDTO overHeadDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update OverHead partially : {}, {}", id, overHeadDTO);

		if (!overHeadRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
		overHeadDTO.setId(id);
		Optional<OverHeadDTO> result = overHeadService.partialUpdate(overHeadDTO);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, overHeadDTO.getId().toString()));
	}

	/**
	 * {@code GET  /over-heads} : get all the overHeads.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of overHeads in body.
	 */
	@GetMapping("/overheads")
	public ResponseEntity<List<OverHeadDTO>> getAllOverHeads(Pageable pageable) {
		log.debug("REST request to get a page of OverHeads");
		Page<OverHeadDTO> page = overHeadService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active over heads.
	 *
	 * @param pageable the pageable
	 * @return the all active over heads
	 */
	@GetMapping("/overheads-active")
	public ResponseEntity<List<OverHeadDTO>> getAllActiveOverHeads(Pageable pageable) {
		log.debug("REST request to get a page of Active OverHeads");
		Page<OverHeadDTO> page = overHeadService.findAllActive(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /over-heads/:id} : get the "id" overHead.
	 *
	 * @param id the id of the overHeadDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the overHeadDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/overhead/{id}")
	public ResponseEntity<OverHeadDTO> getOverHead(@PathVariable Long id) {
		log.debug("REST request to get OverHead : {}", id);
		Optional<OverHeadDTO> overHeadDTO = overHeadService.findOne(id);
		return ResponseUtil.wrapOrNotFound(overHeadDTO);
	}

	/**
	 * {@code DELETE  /over-heads/:id} : delete the "id" overHead.
	 *
	 * @param id the id of the overHeadDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/overhead/{id}")
	public ResponseEntity<Void> deleteOverHead(@PathVariable Long id) {
		log.debug("REST request to delete OverHead : {}", id);
		Optional<OverHeadDTO> overHeadOptionalDTO = overHeadService.findOne(id);
		if (overHeadOptionalDTO.isPresent()) {
			OverHeadDTO overHeadDTO = overHeadOptionalDTO.get();
			overHeadDTO.setActiveYn(false);
			overHeadService.save(overHeadDTO);
		} else {
			throw new BadRequestAlertException(frameWorkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
