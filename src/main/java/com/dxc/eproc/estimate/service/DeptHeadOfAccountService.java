package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.DeptHeadOfAccountDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.DeptHeadOfAccount}.
 */
public interface DeptHeadOfAccountService {
	/**
	 * Save a deptHeadOfAccount.
	 *
	 * @param deptHeadOfAccountDTO the entity to save.
	 * @return the persisted entity.
	 */
	DeptHeadOfAccountDTO save(DeptHeadOfAccountDTO deptHeadOfAccountDTO);

	/**
	 * Partially updates a deptHeadOfAccount.
	 *
	 * @param deptHeadOfAccountDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<DeptHeadOfAccountDTO> partialUpdate(DeptHeadOfAccountDTO deptHeadOfAccountDTO);

	/**
	 * Get all the deptHeadOfAccounts.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<DeptHeadOfAccountDTO> findAll(Pageable pageable);

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<DeptHeadOfAccountDTO> findAllActive(Pageable pageable);

	/**
	 * Get the "id" deptHeadOfAccount.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<DeptHeadOfAccountDTO> findOne(Long id);

	/**
	 * Delete the "id" deptHeadOfAccount.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Find all dept head of account by dept id and active yn.
	 *
	 * @param deptId the dept id
	 * @return the list
	 */
	List<DeptHeadOfAccountDTO> findAllDeptHeadOfAccountByDeptIdAndActiveYn(Long deptId);
}
