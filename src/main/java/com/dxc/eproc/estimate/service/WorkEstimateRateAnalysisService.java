package com.dxc.eproc.estimate.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;

/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.domain.WorkEstimateRateAnalysis}.
 */
public interface WorkEstimateRateAnalysisService {
	
    /**
     * Save a workEstimateRateAnalysis.
     *
     * @param workEstimateRateAnalysisDTO the entity to save.
     * @return the persisted entity.
     */
    WorkEstimateRateAnalysisDTO save(WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO);

    /**
     * Partially updates a workEstimateRateAnalysis.
     *
     * @param workEstimateRateAnalysisDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkEstimateRateAnalysisDTO> partialUpdate(WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO);

    /**
     * Get the "id" workEstimateRateAnalysis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkEstimateRateAnalysisDTO> findOne(Long id);

    /**
     * Delete the "id" workEstimateRateAnalysis.
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
    Optional<WorkEstimateRateAnalysisDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);
    
    /**
     * Find by work estimate item id.
     *
     * @param workEstimateItemId the work estimate item id
     * @param pageable the pageable
     * @return the page
     */
    Page<WorkEstimateRateAnalysisDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

	Optional<WorkEstimateRateAnalysisDTO> findByWorkEstimateItemId(Long workEstimateItemId);
}
