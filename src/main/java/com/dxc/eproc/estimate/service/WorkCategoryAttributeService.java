package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkCategoryAttributeDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkCategoryAttribute}.
 */
public interface WorkCategoryAttributeService {
	/**
	 * Save a workCategoryAttribute.
	 *
	 * @param workCategoryAttributeDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkCategoryAttributeDTO save(WorkCategoryAttributeDTO workCategoryAttributeDTO);

	/**
	 * Partially updates a workCategoryAttribute.
	 *
	 * @param workCategoryAttributeDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkCategoryAttributeDTO> partialUpdate(WorkCategoryAttributeDTO workCategoryAttributeDTO);

	/**
	 * Get all the workCategoryAttributes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkCategoryAttributeDTO> findAll(Pageable pageable);

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkCategoryAttributeDTO> findAllActive(Pageable pageable);

	/**
	 * Find all by work type id and work category id and active yn.
	 *
	 * @param workTypeId     the work type id
	 * @param workCategoryId the work category id
	 * @return the list
	 */
	List<WorkCategoryAttributeDTO> findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn(Long workTypeId,
			Long workCategoryId);

	/**
	 * Get the "id" workCategoryAttribute.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkCategoryAttributeDTO> findOne(Long id);

	/**
	 * Delete the "id" workCategoryAttribute.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
