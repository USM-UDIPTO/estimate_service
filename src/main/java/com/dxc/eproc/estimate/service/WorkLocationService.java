package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkLocation}.
 */
public interface WorkLocationService {
	/**
	 * Save a workLocation.
	 *
	 * @param workLocationDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkLocationDTO save(WorkLocationDTO workLocationDTO);

	/**
	 * Partially updates a workLocation.
	 *
	 * @param workLocationDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkLocationDTO> partialUpdate(WorkLocationDTO workLocationDTO);

	/**
	 * Get all the workLocations.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkLocationDTO> findAll(Pageable pageable);

	/**
	 * Gets the all work locations by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param pageable      the pageable
	 * @return the all work locations by sub estimate id
	 */
	Page<WorkLocationDTO> getAllWorkLocationsBySubEstimateId(Long subEstimateId, Pageable pageable);

	/**
	 * Gets the all work locations by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the all work locations by sub estimate id
	 */
	List<WorkLocationDTO> getAllWorkLocationsBySubEstimateId(Long subEstimateId);

	/**
	 * Get the "id" workLocation.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkLocationDTO> findOne(Long id);

	/**
	 * Find by sub estimate id and id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param id            the id
	 * @return the optional
	 */
	Optional<WorkLocationDTO> findBySubEstimateIdAndId(Long subEstimateId, Long id);

	/**
	 * Find by sub estimate id and id in.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param ids           the ids
	 * @return the list
	 */
	List<WorkLocationDTO> findBySubEstimateIdAndIdIn(Long subEstimateId, List<Long> ids);

	/**
	 * Delete the "id" workLocation.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
