package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.EstimateTypeDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.EstimateType}.
 */
public interface EstimateTypeService {
	/**
	 * Save a estimateType.
	 *
	 * @param estimateTypeDTO the entity to save.
	 * @return the persisted entity.
	 */
	EstimateTypeDTO save(EstimateTypeDTO estimateTypeDTO);

	/**
	 * Partially updates a estimateType.
	 *
	 * @param estimateTypeDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<EstimateTypeDTO> partialUpdate(EstimateTypeDTO estimateTypeDTO);

	/**
	 * Get all the estimateTypes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<EstimateTypeDTO> findAll(Pageable pageable);

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<EstimateTypeDTO> findAllActive(Pageable pageable);

	/**
	 * Get the "id" estimateType.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<EstimateTypeDTO> findOne(Long id);

	/**
	 * Delete the "id" estimateType.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Find all active.
	 *
	 * @return the list
	 */
	List<EstimateTypeDTO> findAllActive();
}
