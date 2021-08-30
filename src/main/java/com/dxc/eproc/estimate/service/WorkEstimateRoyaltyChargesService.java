package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkEstimateRoyaltyChargesDTO;

/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.model.WorkEstimateRoyaltyCharges}.
 */
public interface WorkEstimateRoyaltyChargesService {
	
    /**
     * Save a workEstimateRoyaltyCharges.
     *
     * @param workEstimateRoyaltyChargesDTO the entity to save.
     * @return the persisted entity.
     */
    WorkEstimateRoyaltyChargesDTO save(WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO);

    /**
     * Partially updates a workEstimateRoyaltyCharges.
     *
     * @param workEstimateRoyaltyChargesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkEstimateRoyaltyChargesDTO> partialUpdate(WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO);

    /**
     * Get the "id" workEstimateRoyaltyCharges.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkEstimateRoyaltyChargesDTO> findOne(Long id);

    /**
     * Delete the "id" workEstimateRoyaltyCharges.
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
    Optional<WorkEstimateRoyaltyChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);
    
    /**
     * Find by work estimate item id.
     *
     * @param workEstimateItemId the work estimate item id
     * @param pageable the pageable
     * @return the page
     */
    Page<WorkEstimateRoyaltyChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);
    
    List<WorkEstimateRoyaltyChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId);
}
