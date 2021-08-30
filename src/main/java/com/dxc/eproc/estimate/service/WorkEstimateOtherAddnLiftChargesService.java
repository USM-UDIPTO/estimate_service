package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.RaChargeType;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOtherAddnLiftChargesDTO;

/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.domain.WorkEstimateOtherAddnLiftCharges}.
 */
public interface WorkEstimateOtherAddnLiftChargesService {
	
    /**
     * Save a workEstimateOtherAddnLiftCharges.
     *
     * @param workEstimateOtherAddnLiftChargesDTO the entity to save.
     * @return the persisted entity.
     */
    WorkEstimateOtherAddnLiftChargesDTO save(WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO);

    /**
     * Partially updates a workEstimateOtherAddnLiftCharges.
     *
     * @param workEstimateOtherAddnLiftChargesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkEstimateOtherAddnLiftChargesDTO> partialUpdate(WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO);

    /**
     * Get the "id" workEstimateOtherAddnLiftCharges.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkEstimateOtherAddnLiftChargesDTO> findOne(Long id);

    /**
     * Delete the "id" workEstimateOtherAddnLiftCharges.
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
    Optional<WorkEstimateOtherAddnLiftChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);
    
    /**
     * Find by work estimate item id.
     *
     * @param workEstimateItemId the work estimate item id
     * @param pageable the pageable
     * @return the page
     */
    Page<WorkEstimateOtherAddnLiftChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);
    
    List<WorkEstimateOtherAddnLiftChargesDTO> findByWorkEstimateItemIdAndType(Long workEstimateItemId, RaChargeType type);
}
