package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.response.dto.SubEstimateResponseDTO;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.SubEstimate}.
 */
public interface SubEstimateService {
	/**
	 * Save a subEstimate.
	 *
	 * @param subEstimateDTO the entity to save.
	 * @return the persisted entity.
	 */
	SubEstimateDTO save(SubEstimateDTO subEstimateDTO);

	/**
	 * Partially updates a subEstimate.
	 *
	 * @param subEstimateDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<SubEstimateDTO> partialUpdate(SubEstimateDTO subEstimateDTO);

	/**
	 * Get all the subEstimates.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<SubEstimateDTO> findAll(Pageable pageable);

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the page
	 */
	Page<SubEstimateDTO> findAllByWorkEstimateId(Long workEstimateId, Pageable pageable);

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	List<SubEstimateDTO> findAllByWorkEstimateId(Long workEstimateId);

	/**
	 * Get the "id" subEstimate.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<SubEstimateDTO> findOne(Long id);

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the optional
	 */
	Optional<SubEstimateDTO> findByWorkEstimateIdAndId(Long workEstimateId, Long id);

	/**
	 * Delete the "id" subEstimate.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Find by work estimate id and ids.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateIds the sub estimate ids
	 * @return the list
	 */
	List<SubEstimateDTO> findByWorkEstimateIdAndIds(Long workEstimateId, List<Long> subEstimateIds);

	/**
	 * Sum estimate total by work estimate id and ids.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateIds the sub estimate ids
	 * @return the big decimal
	 */
	BigDecimal sumEstimateTotalByWorkEstimateIdAndIds(Long workEstimateId, List<Long> subEstimateIds);

	/**
	 * Find by work estimate id and work sub estimate group id.
	 *
	 * @param workEstimateId         the work estimate id
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the list
	 */
	List<SubEstimateDTO> findByWorkEstimateIdAndWorkSubEstimateGroupId(Long workEstimateId,
			Long workSubEstimateGroupId);

	/**
	 * Clear group id.
	 *
	 * @param id the id
	 */
	void clearGroupId(Long id);

	/**
	 * Modify estimate total.
	 *
	 * @param id            the id
	 * @param estimateTotal the estimate total
	 */
	void modifyEstimateTotal(Long id, BigDecimal estimateTotal);

	/**
	 * Sum of estimate total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal sumOfEstimateTotalByWorkEstimateId(Long workEstimateId);

	/**
	 * Gets the overall work estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @return the overall work estimate totals
	 */
	SubEstimateResponseDTO getSubEstimateTotals(Long workEstimateId, Long subEstimateId);

	/**
	 * Calculate sub estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @return the sub estimate response DTO
	 */
	SubEstimateResponseDTO calculateSubEstimateTotals(Long workEstimateId, Long subEstimateId);

	/**
	 * Find all by work estimate id and id in and completed yn.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateIds the sub estimate ids
	 * @return the list
	 */
	List<SubEstimateDTO> findAllByWorkEstimateIdAndIdInAndCompletedYn(Long workEstimateId, List<Long> subEstimateIds);

}
