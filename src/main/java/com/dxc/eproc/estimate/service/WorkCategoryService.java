package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkCategory}.
 */
public interface WorkCategoryService {
	/**
	 * Save a workCategory.
	 *
	 * @param workCategoryDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkCategoryDTO save(WorkCategoryDTO workCategoryDTO);

	/**
	 * Partially updates a workCategory.
	 *
	 * @param workCategoryDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkCategoryDTO> partialUpdate(WorkCategoryDTO workCategoryDTO);

	/**
	 * Get all the workCategories.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkCategoryDTO> findAll(Pageable pageable);

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkCategoryDTO> findAllActive(Pageable pageable);

	/**
	 * Find all active.
	 *
	 * @return the list
	 */
	List<WorkCategoryDTO> findAllActive();

	/**
	 * Get the "id" workCategory.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkCategoryDTO> findOne(Long id);

	/**
	 * Delete the "id" workCategory.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
