package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkEstimateLiftChargesDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.domain.WorkEstimateLiftCharges}.
 */
public interface WorkEstimateLiftChargesService {
	/**
	 * Save a workEstimateLiftCharges.
	 *
	 * @param workEstimateLiftChargesDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkEstimateLiftChargesDTO save(WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO);

	/**
	 * Partially updates a workEstimateLiftCharges.
	 *
	 * @param workEstimateLiftChargesDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkEstimateLiftChargesDTO> partialUpdate(WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO);

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable           the pageable
	 * @return the page
	 */
	Page<WorkEstimateLiftChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);
	
	List<WorkEstimateLiftChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId);

	/**
	 * Get the "id" workEstimateLiftCharges.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkEstimateLiftChargesDTO> findOne(Long id);

	/**
	 * Delete the "id" workEstimateLiftCharges.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id                 the id
	 * @return the optional
	 */
	Optional<WorkEstimateLiftChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);
}
