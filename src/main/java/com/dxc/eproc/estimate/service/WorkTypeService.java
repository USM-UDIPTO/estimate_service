package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.model.WorkType}.
 */
public interface WorkTypeService {
	/**
	 * Save a workType.
	 *
	 * @param workTypeDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkTypeDTO save(WorkTypeDTO workTypeDTO);

	/**
	 * Partially updates a workType.
	 *
	 * @param workTypeDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkTypeDTO> partialUpdate(WorkTypeDTO workTypeDTO);

	/**
	 * Get all the workTypes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkTypeDTO> findAll(Pageable pageable);

	/**
	 * Find all active estimate work types.
	 *
	 * @return the list
	 */
	List<WorkTypeDTO> findAllActiveEstimateWorkTypes();

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkTypeDTO> findAllActive(Pageable pageable);

	/**
	 * Get the "id" workType.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkTypeDTO> findOne(Long id);

	/**
	 * Delete the "id" workType.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	Optional<WorkTypeDTO> findByIdAndActiveYnTrue(Long id);
}
