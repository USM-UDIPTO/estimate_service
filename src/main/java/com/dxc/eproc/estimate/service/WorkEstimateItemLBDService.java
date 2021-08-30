package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.LBDOperation;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimateItemLBD}.
 */
public interface WorkEstimateItemLBDService {
	/**
	 * Save a workEstimateItemLBD.
	 *
	 * @param workEstimateItemLBDDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkEstimateItemLBDDTO save(WorkEstimateItemLBDDTO workEstimateItemLBDDTO);

	/**
	 * Partially updates a workEstimateItemLBD.
	 *
	 * @param workEstimateItemLBDDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkEstimateItemLBDDTO> partialUpdate(WorkEstimateItemLBDDTO workEstimateItemLBDDTO);

	/**
	 * Get all the workEstimateItemLBDS.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkEstimateItemLBDDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" workEstimateItemLBD.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkEstimateItemLBDDTO> findOne(Long id);

	/**
	 * Delete the "id" workEstimateItemLBD.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Find all by work estimate item id.
	 *
	 * @param sourceItemId the source item id
	 * @return the list
	 */
	List<WorkEstimateItemLBDDTO> findAllByWorkEstimateItemId(Long sourceItemId);

	/**
	 * Sum by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param additionDeduction  the addition deduction
	 * @return the big decimal
	 */
	BigDecimal sumByWorkEstimateItemId(Long workEstimateItemId, LBDOperation additionDeduction);

	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id                 the id
	 * @return the optional
	 */
	Optional<WorkEstimateItemLBDDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable           the pageable
	 * @return the page
	 */
	Page<WorkEstimateItemLBDDTO> findByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

	/**
	 * Gets the item LB ds total.
	 *
	 * @param itemId the item id
	 * @return the item LB ds total
	 */
	BigDecimal getItemLBDsTotal(Long itemId);

	/**
	 * Recalculate and update item WRTLBD.
	 *
	 * @param estimateId    the estimate id
	 * @param subEstimateId the sub estimate id
	 * @param itemId        the item id
	 */
	void recalculateAndUpdateItemWRTLBD(Long estimateId, Long subEstimateId, Long itemId);
}
