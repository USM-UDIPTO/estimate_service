package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkEstimateMarketRateDTO;

/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.domain.WorkEstimateMarketRate}.
 */
public interface WorkEstimateMarketRateService {
    /**
     * Save a workEstimateMarketRate.
     *
     * @param workEstimateMarketRateDTO the entity to save.
     * @return the persisted entity.
     */
    WorkEstimateMarketRateDTO save(WorkEstimateMarketRateDTO workEstimateMarketRateDTO);

    /**
     * Partially updates a workEstimateMarketRate.
     *
     * @param workEstimateMarketRateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkEstimateMarketRateDTO> partialUpdate(WorkEstimateMarketRateDTO workEstimateMarketRateDTO);

    /**
     * Get the "id" workEstimateMarketRate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkEstimateMarketRateDTO> findOne(Long id);

    /**
     * Delete the "id" workEstimateMarketRate.
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
    Optional<WorkEstimateMarketRateDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);
    
    /**
     * Find by work estimate item id.
     *
     * @param workEstimateItemId the work estimate item id
     * @param pageable the pageable
     * @return the page
     */
    Page<WorkEstimateMarketRateDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

	List<WorkEstimateMarketRateDTO> findByWorkEstimateItemId(Long workEstimateItemId);
}
