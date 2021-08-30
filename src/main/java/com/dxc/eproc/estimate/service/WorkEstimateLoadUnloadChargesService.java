package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkEstimateLoadUnloadChargesDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.domain.WorkEstimateLoadUnloadCharges}.
 */
public interface WorkEstimateLoadUnloadChargesService {
    /**
     * Save a workEstimateLoadUnloadCharges.
     *
     * @param workEstimateLoadUnloadChargesDTO the entity to save.
     * @return the persisted entity.
     */
    WorkEstimateLoadUnloadChargesDTO save(WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO);

    /**
     * Partially updates a workEstimateLoadUnloadCharges.
     *
     * @param workEstimateLoadUnloadChargesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkEstimateLoadUnloadChargesDTO> partialUpdate(WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO);

    /**
     * Find by work estimate item id.
     *
     * @param workEstimateItemId the work estimate item id
     * @param pageable the pageable
     * @return the page
     */
    Page<WorkEstimateLoadUnloadChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);
    
    List<WorkEstimateLoadUnloadChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId);

    /**
     * Get the "id" workEstimateLoadUnloadCharges.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkEstimateLoadUnloadChargesDTO> findOne(Long id);

    /**
     * Delete the "id" workEstimateLoadUnloadCharges.
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
	Optional<WorkEstimateLoadUnloadChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);
}
