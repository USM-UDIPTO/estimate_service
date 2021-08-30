package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.LineEstimateDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.domain.LineEstimate}.
 */
public interface LineEstimateService {
	/**
	 * Save a lineEstimate.
	 *
	 * @param lineEstimateDTO the entity to save.
	 * @return the persisted entity.
	 */
	LineEstimateDTO save(LineEstimateDTO lineEstimateDTO);

	/**
	 * Partially updates a lineEstimate.
	 *
	 * @param lineEstimateDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<LineEstimateDTO> partialUpdate(LineEstimateDTO lineEstimateDTO);

	/**
	 * Get all the lineEstimates.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<LineEstimateDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" lineEstimate.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<LineEstimateDTO> findOne(Long id);

	/**
	 * Delete the "id" lineEstimate.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the optional
	 */
	Optional<LineEstimateDTO> findByWorkEstimateIdAndId(Long workEstimateId, Long id);

	/**
	 * Gets the all line estimates by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the all line estimates by work estimate id
	 */
	Page<LineEstimateDTO> getAllLineEstimatesByWorkEstimateId(Long workEstimateId, Pageable pageable);

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	List<LineEstimateDTO> findAllByWorkEstimateId(Long workEstimateId);

	/**
	 * Sum approximate value by line estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal sumApproximateValueByLineEstimateId(Long workEstimateId);

}
