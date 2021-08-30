package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkEstimateLeadChargesDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.model.WorkEstimateLeadCharges}.
 */
public interface WorkEstimateLeadChargesService {
    /**
     * Save a workEstimateLeadCharges.
     *
     * @param workEstimateLeadChargesDTO the entity to save.
     * @return the persisted entity.
     */
    WorkEstimateLeadChargesDTO save(WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO);

    /**
     * Partially updates a workEstimateLeadCharges.
     *
     * @param workEstimateLeadChargesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkEstimateLeadChargesDTO> partialUpdate(WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO);

    /**
     * Find by work estimate item id.
     *
     * @param workEstimateItemId the work estimate item id
     * @param pageable the pageable
     * @return the page
     */
    Page<WorkEstimateLeadChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

    /**
     * Get the "id" workEstimateLeadCharges.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkEstimateLeadChargesDTO> findOne(Long id);

    /**
     * Delete the "id" workEstimateLeadCharges.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id the id
	 * @return the optional
	 */
	Optional<WorkEstimateLeadChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	List<WorkEstimateLeadChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId);
}
