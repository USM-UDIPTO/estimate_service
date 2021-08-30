package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroup}.
 */
public interface WorkSubEstimateGroupService {
	/**
	 * Save a workSubEstimateGroup.
	 *
	 * @param workSubEstimateGroupDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkSubEstimateGroupDTO save(WorkSubEstimateGroupDTO workSubEstimateGroupDTO);

	/**
	 * Partially updates a workSubEstimateGroup.
	 *
	 * @param workSubEstimateGroupDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkSubEstimateGroupDTO> partialUpdate(WorkSubEstimateGroupDTO workSubEstimateGroupDTO);

	/**
	 * Get all the workSubEstimateGroups.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkSubEstimateGroupDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" workSubEstimateGroup.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkSubEstimateGroupDTO> findOne(Long id);

	/**
	 * Delete the "id" workSubEstimateGroup.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Sum overhead total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal sumOverheadTotalByWorkEstimateId(Long workEstimateId);

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param Id             the id
	 * @return the optional
	 */
	Optional<WorkSubEstimateGroupDTO> findByWorkEstimateIdAndId(Long workEstimateId, Long Id);

	/**
	 * Find by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the page
	 */
	Page<WorkSubEstimateGroupDTO> findByWorkEstimateId(Long workEstimateId, Pageable pageable);

	/**
	 * Sum lumpsum total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal sumLumpsumTotalByWorkEstimateId(Long workEstimateId);

	/**
	 * Recalculate and update WRT lumpsum.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 */
	void recalculateAndUpdateWRTLumpsum(Long workEstimateId, Long groupId);

	/**
	 * Sets the sub estimate details in group.
	 *
	 * @param workEstimateId          the work estimate id
	 * @param workSubEstimateGroupDTO the work sub estimate group DTO
	 */
	void setSubEstimateDetailsInGroup(Long workEstimateId, WorkSubEstimateGroupDTO workSubEstimateGroupDTO);

	/**
	 * Recalculate group WRT sub estimate.
	 *
	 * @param workEstimateId the work estimate id
	 */
	void recalculateGroupWRTSubEstimate(Long workEstimateId);

	/**
	 * Recalculate group.
	 *
	 * @param subEstimateIds the sub estimate ids
	 * @param finalGroupDTO  the final group DTO
	 */
	void recalculateGroup(List<Long> subEstimateIds, WorkSubEstimateGroupDTO finalGroupDTO);

	/**
	 * Delete.
	 *
	 * @param workSubEstimateGroupDTO the work sub estimate group DTO
	 */
	void deleteGroup(WorkSubEstimateGroupDTO workSubEstimateGroupDTO);
}
