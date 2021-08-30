package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.DeptEstimateTypeDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.DeptEstimateType}.
 */
public interface DeptEstimateTypeService {
	/**
	 * Save a deptEstimateType.
	 *
	 * @param deptEstimateTypeDTO the entity to save.
	 * @return the persisted entity.
	 */
	DeptEstimateTypeDTO save(DeptEstimateTypeDTO deptEstimateTypeDTO);

	/**
	 * Partially updates a deptEstimateType.
	 *
	 * @param deptEstimateTypeDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<DeptEstimateTypeDTO> partialUpdate(DeptEstimateTypeDTO deptEstimateTypeDTO);

	/**
	 * Get all the deptEstimateTypes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<DeptEstimateTypeDTO> findAll(Pageable pageable);

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<DeptEstimateTypeDTO> findAllActive(Pageable pageable);

	/**
	 * Get the "id" deptEstimateType.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<DeptEstimateTypeDTO> findOne(Long id);

	/**
	 * Delete the "id" deptEstimateType.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Gets the all active dept estimate types for work estimate.
	 *
	 * @param deptId the dept id
	 * @return the all active dept estimate types for work estimate
	 */
	List<DeptEstimateTypeDTO> getAllActiveDeptEstimateTypesForWorkEstimate(Long deptId);
}
