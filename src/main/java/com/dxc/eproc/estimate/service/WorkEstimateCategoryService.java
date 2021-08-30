package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import com.dxc.eproc.estimate.service.dto.WorkEstimateCategoryDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface WorkEstimateCategoryService.
 */
public interface WorkEstimateCategoryService {

	/**
	 * Save.
	 *
	 * @param workEstimateCategoryDTO the work estimate category DTO
	 * @return the work estimate category DTO
	 */
	WorkEstimateCategoryDTO save(WorkEstimateCategoryDTO workEstimateCategoryDTO);

	/**
	 * Partially updates a workCategory.
	 *
	 * @param workEstimateCategoryDTO the work estimate category DTO
	 * @return the persisted entity.
	 */
	Optional<WorkEstimateCategoryDTO> partialUpdate(WorkEstimateCategoryDTO workEstimateCategoryDTO);

	/**
	 * Get all the workCategories.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list of entities.
	 */
	List<WorkEstimateCategoryDTO> findAllBySubEstimateId(Long subEstimateId);

	/**
	 * Get the "id" workCategory.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkEstimateCategoryDTO> findOne(Long id);

	/**
	 * Delete the "id" workCategory.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	List<WorkEstimateCategoryDTO> findAllBySubEstimateIdAndReferenceId(Long subEstimateId, Long sorId);

	WorkEstimateCategoryDTO findOneBySubEstimateIdAndReferenceIdNotAndParentIdIsNull(Long subEstimateId, Long sorId);

	List<WorkEstimateCategoryDTO> findAllBySubEstimateIdAndReferenceIdAndParentIdIsNotNull(Long subEstimateId, Long sorId);

	WorkEstimateCategoryDTO findOneBySubEstimateIdAndParentIdIsNull(Long subEstimateId);
}
