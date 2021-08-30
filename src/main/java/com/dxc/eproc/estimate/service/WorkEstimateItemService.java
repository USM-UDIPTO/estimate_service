package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimateItem}.
 */
public interface WorkEstimateItemService {
	/**
	 * Save a workEstimateItem.
	 *
	 * @param workEstimateItemDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkEstimateItemDTO save(WorkEstimateItemDTO workEstimateItemDTO);

	/**
	 * Partially updates a workEstimateItem.
	 *
	 * @param workEstimateItemDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkEstimateItemDTO> partialUpdate(WorkEstimateItemDTO workEstimateItemDTO);

	/**
	 * Get all the workEstimateItems.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkEstimateItemDTO> findAll(Pageable pageable);

	/**
	 * Find all SOR items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	List<WorkEstimateItemDTO> findAllSORItems(Long subEstimateId);

	/**
	 * Find all SOR items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param pageable      the pageable
	 * @return the page
	 */
	Page<WorkEstimateItemDTO> findAllSORItems(Long subEstimateId, Pageable pageable);

	/**
	 * Find all non SOR items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	List<WorkEstimateItemDTO> findAllNonSORItems(Long subEstimateId);

	/**
	 * Find all non SOR items.
	 *
	 * @param id       the id
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkEstimateItemDTO> findAllNonSORItems(Long id, Pageable pageable);

	/**
	 * Find all SOR item by sub estimate id and id in.
	 *
	 * @param subEstimateId           the sub estimate id
	 * @param workEstimateItemWithIds the work estimate item with ids
	 * @return the list
	 */
	List<WorkEstimateItemDTO> findAllSORItemBySubEstimateIdAndIdIn(Long subEstimateId,
			List<Long> workEstimateItemWithIds);

	/**
	 * Get the "id" workEstimateItem.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkEstimateItemDTO> findOne(Long id);

	/**
	 * Find by sub estimate id and id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param id            the id
	 * @return the optional
	 */
	Optional<WorkEstimateItemDTO> findBySubEstimateIdAndId(Long subEstimateId, Long id);

	/**
	 * Find by sub estimate id and item code.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param itemCode      the item code
	 * @return the list
	 */
	List<WorkEstimateItemDTO> findBySubEstimateIdAndItemCode(Long subEstimateId, String itemCode);

	/**
	 * Delete the "id" workEstimateItem.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Sum of work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	BigDecimal sumOfWorkEstimateItemTotal(Long subEstimateId);

	/**
	 * Sum of SOR work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	BigDecimal sumOfSORWorkEstimateItemTotal(Long subEstimateId);

	/**
	 * Sum of non SOR work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	BigDecimal sumOfNonSORWorkEstimateItemTotal(Long subEstimateId);

	/**
	 * Find max SOR entry order by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the integer
	 */
	Integer findMaxSOREntryOrderBySubEstimateId(Long subEstimateId);

	/**
	 * Find max non SOR entry order by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the integer
	 */
	Integer findMaxNonSOREntryOrderBySubEstimateId(Long subEstimateId);

	/**
	 * Find max floor by sub estimate id and category id.
	 *
	 * @param subEstimateId    the sub estimate id
	 * @param catWorkSorItemId the cat work sor item id
	 * @return the integer
	 */
	Integer findMaxFloorBySubEstimateIdAndCatWorkSorItemId(Long subEstimateId, Long catWorkSorItemId);

	/**
	 * Find all items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	List<WorkEstimateItemDTO> findAllItems(Long subEstimateId);

	/**
	 * Find all by sub estimate id and category id and cat work sor item id is not
	 * null.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param categoryId    the category id
	 * @return the list
	 */
	List<WorkEstimateItemDTO> findAllBySubEstimateIdAndCategoryIdAndCatWorkSorItemIdIsNotNull(Long subEstimateId,
			Long categoryId);

}
