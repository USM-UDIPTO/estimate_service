package com.dxc.eproc.estimate.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkEstimateAdditionalChargesDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.domain.WorkEstimateAdditionalCharges}.
 */
public interface WorkEstimateAdditionalChargesService {
	/**
	 * Save a workEstimateAdditionalCharges.
	 *
	 * @param workEstimateAdditionalChargesDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkEstimateAdditionalChargesDTO save(WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO);

	/**
	 * Partially updates a workEstimateAdditionalCharges.
	 *
	 * @param workEstimateAdditionalChargesDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkEstimateAdditionalChargesDTO> partialUpdate(
			WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO);

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable           the pageable
	 * @return the page
	 */
	Page<WorkEstimateAdditionalChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);
	
	Optional<WorkEstimateAdditionalChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId);

	/**
	 * Get the "id" workEstimateAdditionalCharges.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkEstimateAdditionalChargesDTO> findOne(Long id);

	/**
	 * Delete the "id" workEstimateAdditionalCharges.
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
	Optional<WorkEstimateAdditionalChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);
}
