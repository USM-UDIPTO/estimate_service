package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupLumpsumDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum}.
 */
public interface WorkSubEstimateGroupLumpsumService {
	/**
	 * Save a workSubEstimateGroupLumpsum.
	 *
	 * @param workSubEstimateGroupLumpsumDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkSubEstimateGroupLumpsumDTO save(WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO);

	/**
	 * Partially updates a workSubEstimateGroupLumpsum.
	 *
	 * @param workSubEstimateGroupLumpsumDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkSubEstimateGroupLumpsumDTO> partialUpdate(
			WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO);

	/**
	 * Get all the workSubEstimateGroupLumpsums.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkSubEstimateGroupLumpsumDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" workSubEstimateGroupLumpsum.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkSubEstimateGroupLumpsumDTO> findOne(Long id);

	/**
	 * Delete the "id" workSubEstimateGroupLumpsum.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Find by work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param pageable               the pageable
	 * @return the page
	 */
	Page<WorkSubEstimateGroupLumpsumDTO> findByWorkSubEstimateGroupId(Long workSubEstimateGroupId, Pageable pageable);

	/**
	 * Find by work sub estimate group id and id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @return the optional
	 */
	Optional<WorkSubEstimateGroupLumpsumDTO> findByWorkSubEstimateGroupIdAndId(Long workSubEstimateGroupId, Long id);

	/**
	 * Sum approx rate by group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the big decimal
	 */
	BigDecimal sumApproxRateByGroupId(Long workSubEstimateGroupId);
}
