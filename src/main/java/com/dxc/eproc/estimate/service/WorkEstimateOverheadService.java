package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.OverHeadType;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOverheadDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.domain.WorkEstimateOverhead}.
 */
public interface WorkEstimateOverheadService {
	/**
	 * Save a workEstimateOverhead.
	 *
	 * @param workEstimateOverheadDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkEstimateOverheadDTO save(WorkEstimateOverheadDTO workEstimateOverheadDTO);

	/**
	 * Partially updates a workEstimateOverhead.
	 *
	 * @param workEstimateOverheadDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkEstimateOverheadDTO> partialUpdate(WorkEstimateOverheadDTO workEstimateOverheadDTO);

	/**
	 * Get all the workEstimateOverheads.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkEstimateOverheadDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" workEstimateOverhead.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkEstimateOverheadDTO> findOne(Long id);

	/**
	 * Delete the "id" workEstimateOverhead.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Gets the all work estimate overheads by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the all work estimate overheads by work estimate id
	 */
	Page<WorkEstimateOverheadDTO> getAllWorkEstimateOverheadsByWorkEstimateId(Long workEstimateId, Pageable pageable);

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the optional
	 */
	Optional<WorkEstimateOverheadDTO> findByWorkEstimateIdAndId(Long workEstimateId, Long id);

	/**
	 * Sum normal over head by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal sumNormalOverHeadByWorkEstimateId(Long workEstimateId);

	/**
	 * Sum additional over head by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal sumAdditionalOverHeadByWorkEstimateId(Long workEstimateId);

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	List<WorkEstimateOverheadDTO> findAllByWorkEstimateId(Long workEstimateId);

	/**
	 * Find all by work estimate id and over head type.
	 *
	 * @param workEstimateId the work estimate id
	 * @param overHeadType   the over head type
	 * @return the list
	 */
	List<WorkEstimateOverheadDTO> findAllByWorkEstimateIdAndOverHeadType(Long workEstimateId,
			OverHeadType overHeadType);

	/**
	 * Calculate work estimate normal overhead totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal calculateWorkEstimateNormalOverheadTotals(Long workEstimateId);

	/**
	 * Calculate work estimate additional overhead totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal calculateWorkEstimateAdditionalOverheadTotals(Long workEstimateId);

	/**
	 * Gets the total without addl overhead.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the total without addl overhead
	 */
	BigDecimal getTotalWithoutAddlOverhead(Long workEstimateId);
}
