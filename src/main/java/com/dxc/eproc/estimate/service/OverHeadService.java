package com.dxc.eproc.estimate.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.OverHeadDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.domain.OverHead}.
 */
public interface OverHeadService {
    /**
     * Save a overHead.
     *
     * @param overHeadDTO the entity to save.
     * @return the persisted entity.
     */
    OverHeadDTO save(OverHeadDTO overHeadDTO);

    /**
     * Partially updates a overHead.
     *
     * @param overHeadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OverHeadDTO> partialUpdate(OverHeadDTO overHeadDTO);

    /**
     * Get all the overHeads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OverHeadDTO> findAll(Pageable pageable);

    /**
     * Get the "id" overHead.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OverHeadDTO> findOne(Long id);

    /**
     * Delete the "id" overHead.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<OverHeadDTO> findAllActive(Pageable pageable);
}
