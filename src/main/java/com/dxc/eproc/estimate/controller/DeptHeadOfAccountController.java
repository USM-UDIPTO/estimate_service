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
import com.dxc.eproc.estimate.repository.DeptHeadOfAccountRepository;
import com.dxc.eproc.estimate.service.DeptHeadOfAccountService;
import com.dxc.eproc.estimate.service.dto.DeptHeadOfAccountDTO;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.ResponseUtil;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing
 * {@link com.dxc.eproc.estimate.model.DeptHeadOfAccount}.
 */
@RestController
@RequestMapping("/v1/api")
public class DeptHeadOfAccountController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "deptHeadOfAccount";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(DeptHeadOfAccountController.class);

	/** The dept head of account service. */
	private final DeptHeadOfAccountService deptHeadOfAccountService;

	/** The dept head of account repository. */
	private final DeptHeadOfAccountRepository deptHeadOfAccountRepository;

	/** The framework component. */
	private final FrameworkComponent frameworkComponent;

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/**
	 * Instantiates a new dept head of account controller.
	 *
	 * @param deptHeadOfAccountService    the dept head of account service
	 * @param deptHeadOfAccountRepository the dept head of account repository
	 * @param frameworkComponent          the framework component
	 */
	public DeptHeadOfAccountController(DeptHeadOfAccountService deptHeadOfAccountService,
			DeptHeadOfAccountRepository deptHeadOfAccountRepository, FrameworkComponent frameworkComponent) {
		this.deptHeadOfAccountService = deptHeadOfAccountService;
		this.deptHeadOfAccountRepository = deptHeadOfAccountRepository;
		this.frameworkComponent = frameworkComponent;
	}

	/**
	 * {@code POST  /dept-head-of-accounts} : Create a new deptHeadOfAccount.
	 *
	 * @param deptHeadOfAccountDTO the deptHeadOfAccountDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new deptHeadOfAccountDTO, or with status
	 *         {@code 400 (Bad Request)} if the deptHeadOfAccount has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/dept-head-of-accounts")
	public ResponseEntity<DeptHeadOfAccountDTO> createDeptHeadOfAccount(
			@Valid @RequestBody DeptHeadOfAccountDTO deptHeadOfAccountDTO) throws URISyntaxException {
		log.debug("REST request to save DeptHeadOfAccount");
		if (deptHeadOfAccountDTO.getId() != null) {
			throw new BadRequestAlertException("A new deptHeadOfAccount cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		deptHeadOfAccountDTO.setActiveYn(true);
		DeptHeadOfAccountDTO result = deptHeadOfAccountService.save(deptHeadOfAccountDTO);
		return ResponseEntity
				.created(new URI("/api/dept-head-of-accounts/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /dept-head-of-accounts/:id} : Updates an existing
	 * deptHeadOfAccount.
	 *
	 * @param id                   the id of the deptHeadOfAccountDTO to save.
	 * @param deptHeadOfAccountDTO the deptHeadOfAccountDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated deptHeadOfAccountDTO, or with status
	 *         {@code 400 (Bad Request)} if the deptHeadOfAccountDTO is not valid,
	 *         or with status {@code 500 (Internal Server Error)} if the
	 *         deptHeadOfAccountDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/dept-head-of-accounts/{id}")
	public ResponseEntity<DeptHeadOfAccountDTO> updateDeptHeadOfAccount(
			@PathVariable(value = "id", required = false) final Long id,
			@Valid @RequestBody DeptHeadOfAccountDTO deptHeadOfAccountDTO) throws URISyntaxException {
		log.debug("REST request to update DeptHeadOfAccount : {}", id);

		if (!deptHeadOfAccountRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}

		DeptHeadOfAccountDTO result = deptHeadOfAccountService.save(deptHeadOfAccountDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				deptHeadOfAccountDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /dept-head-of-accounts/:id} : Partial updates given fields of
	 * an existing deptHeadOfAccount, field will ignore if it is null.
	 *
	 * @param id                   the id of the deptHeadOfAccountDTO to save.
	 * @param deptHeadOfAccountDTO the deptHeadOfAccountDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated deptHeadOfAccountDTO, or with status
	 *         {@code 400 (Bad Request)} if the deptHeadOfAccountDTO is not valid,
	 *         or with status {@code 404 (Not Found)} if the deptHeadOfAccountDTO is
	 *         not found, or with status {@code 500 (Internal Server Error)} if the
	 *         deptHeadOfAccountDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/dept-head-of-accounts/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<DeptHeadOfAccountDTO> partialUpdateDeptHeadOfAccount(
			@PathVariable(value = "id", required = false) final Long id,
			@NotNull @RequestBody DeptHeadOfAccountDTO deptHeadOfAccountDTO) throws URISyntaxException {
		log.debug("REST request to partial update DeptHeadOfAccount partially : {}", id);

		if (!deptHeadOfAccountRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		deptHeadOfAccountDTO.setId(id);
		Optional<DeptHeadOfAccountDTO> result = deptHeadOfAccountService.partialUpdate(deptHeadOfAccountDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, deptHeadOfAccountDTO.getId().toString()));
	}

	/**
	 * {@code GET  /dept-head-of-accounts} : get all the deptHeadOfAccounts.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of deptHeadOfAccounts in body.
	 */
	@GetMapping("/dept-head-of-accounts")
	public ResponseEntity<List<DeptHeadOfAccountDTO>> getAllDeptHeadOfAccounts(Pageable pageable) {
		log.debug("REST request to get a page of DeptHeadOfAccounts");
		Page<DeptHeadOfAccountDTO> page = deptHeadOfAccountService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active dept head of accounts.
	 *
	 * @param pageable the pageable
	 * @return the all active dept head of accounts
	 */
	@GetMapping("/dept-head-of-accounts-active")
	public ResponseEntity<List<DeptHeadOfAccountDTO>> getAllActiveDeptHeadOfAccounts(Pageable pageable) {
		log.debug("REST request to get a page of DeptHeadOfAccounts");
		Page<DeptHeadOfAccountDTO> page = deptHeadOfAccountService.findAllActive(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all active dept head of accounts list.
	 *
	 * @param deptId the dept id
	 * @return the all active dept head of accounts list
	 */
	@GetMapping("/dept-master/{deptId}/active-head-of-accounts")
	public ResponseEntity<List<DeptHeadOfAccountDTO>> getAllActiveDeptHeadOfAccountsList(
			@PathVariable(value = "deptId", required = true) Long deptId) {
		log.debug("REST request to get a page of DeptHeadOfAccounts");
		List<DeptHeadOfAccountDTO> deptHeadOfAccountDTOList = deptHeadOfAccountService
				.findAllDeptHeadOfAccountByDeptIdAndActiveYn(deptId);
		return ResponseEntity.ok().body(deptHeadOfAccountDTOList);
	}

	/**
	 * {@code GET  /dept-head-of-accounts/:id} : get the "id" deptHeadOfAccount.
	 *
	 * @param id the id of the deptHeadOfAccountDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the deptHeadOfAccountDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/dept-head-of-accounts/{id}")
	public ResponseEntity<DeptHeadOfAccountDTO> getDeptHeadOfAccount(@PathVariable Long id) {
		log.debug("REST request to get DeptHeadOfAccount : {}", id);
		Optional<DeptHeadOfAccountDTO> deptHeadOfAccountDTO = deptHeadOfAccountService.findOne(id);
		return ResponseUtil.wrapOrNotFound(deptHeadOfAccountDTO);
	}

	/**
	 * {@code DELETE  /dept-head-of-accounts/:id} : delete the "id"
	 * deptHeadOfAccount.
	 *
	 * @param id the id of the deptHeadOfAccountDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/dept-head-of-accounts/{id}")
	public ResponseEntity<Void> deleteDeptHeadOfAccount(@PathVariable Long id) {
		log.debug("REST request to delete DeptHeadOfAccount : {}", id);
		if (!deptHeadOfAccountRepository.existsById(id)) {
			throw new BadRequestAlertException(frameworkComponent.resolveI18n("entity.notFound"), ENTITY_NAME,
					"idnotfound");
		}
		deptHeadOfAccountService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
