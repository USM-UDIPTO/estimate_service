package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkEstimateItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkEstimateItemRepository extends JpaRepository<WorkEstimateItem, Long> {

	/**
	 * Find by sub estimate id and id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param id            the id
	 * @return the optional
	 */
	Optional<WorkEstimateItem> findBySubEstimateIdAndId(Long subEstimateId, Long id);

	/**
	 * Find by sub estimate id and item code.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param itemCode      the item code
	 * @return the list
	 */
	List<WorkEstimateItem> findBySubEstimateIdAndItemCode(Long subEstimateId, String itemCode);

	/**
	 * Find all by sub estimate id order by entry order asc.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	List<WorkEstimateItem> findAllBySubEstimateIdOrderByEntryOrderAsc(Long subEstimateId);

	/**
	 * Find all by sub estimate id and cat work sor item id not null order by entry
	 * order asc.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	List<WorkEstimateItem> findAllBySubEstimateIdAndCatWorkSorItemIdNotNullOrderByEntryOrderAsc(Long subEstimateId);

	/**
	 * Find all by sub estimate id and cat work sor item id not null order by entry
	 * order asc.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param pageable      the pageable
	 * @return the page
	 */
	Page<WorkEstimateItem> findAllBySubEstimateIdAndCatWorkSorItemIdNotNullOrderByEntryOrderAsc(Long subEstimateId,
			Pageable pageable);

	/**
	 * Find all by sub estimate id and cat work sor item id null order by entry
	 * order asc.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	List<WorkEstimateItem> findAllBySubEstimateIdAndCatWorkSorItemIdNullOrderByEntryOrderAsc(Long subEstimateId);

	/**
	 * Find all by sub estimate id and cat work sor item id null order by entry
	 * order asc.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param pageable      the pageable
	 * @return the page
	 */
	Page<WorkEstimateItem> findAllBySubEstimateIdAndCatWorkSorItemIdNullOrderByEntryOrderAsc(Long subEstimateId,
			Pageable pageable);

	/**
	 * Find by sub estimate id and cat work sor item id not null and id in.
	 *
	 * @param subEstimateId           the sub estimate id
	 * @param workEstimateItemWithIds the work estimate item with ids
	 * @return the list
	 */
	List<WorkEstimateItem> findBySubEstimateIdAndCatWorkSorItemIdNotNullAndIdIn(Long subEstimateId,
			List<Long> workEstimateItemWithIds);

	/**
	 * Sum of work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(wet.baseRate * wet.quantity) FROM WorkEstimateItem wet WHERE wet.subEstimateId =:subEstimateId")
	BigDecimal sumOfWorkEstimateItemTotal(@Param("subEstimateId") Long subEstimateId);

	/**
	 * Sum of SOR work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(wet.baseRate * wet.quantity) FROM WorkEstimateItem wet WHERE wet.catWorkSorItemId != null and wet.subEstimateId =:subEstimateId")
	BigDecimal sumOfSORWorkEstimateItemTotal(@Param("subEstimateId") Long subEstimateId);

	/**
	 * Sum of non SOR work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(wet.baseRate * wet.quantity) FROM WorkEstimateItem wet WHERE wet.catWorkSorItemId = null and wet.subEstimateId =:subEstimateId")
	BigDecimal sumOfNonSORWorkEstimateItemTotal(@Param("subEstimateId") Long subEstimateId);

	/**
	 * Find max SOR entry order by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the integer
	 */
	@Query("SELECT MAX(wet.entryOrder) FROM WorkEstimateItem wet WHERE wet.subEstimateId =:subEstimateId")
	Integer findMaxSOREntryOrderBySubEstimateId(@Param("subEstimateId") Long subEstimateId);

	/**
	 * Find all by sub estimate id and category id.
	 *
	 * @param subEstimateId    the sub estimate id
	 * @param catWorkSorItemId the cat work sor item id
	 * @return the list
	 */
	List<WorkEstimateItem> findAllBySubEstimateIdAndCatWorkSorItemId(Long subEstimateId, Long catWorkSorItemId);

	/**
	 * Find max floor by sub estimate id and category id.
	 *
	 * @param subEstimateId    the sub estimate id
	 * @param catWorkSorItemId the cat work sor item id
	 * @return the integer
	 */
	@Query("SELECT MAX(wet.floorNumber) FROM WorkEstimateItem wet WHERE wet.subEstimateId =:subEstimateId and wet.catWorkSorItemId =:catWorkSorItemId")
	Integer findMaxFloorBySubEstimateIdAndCatWorkSorItemId(@Param("subEstimateId") Long subEstimateId,
			@Param("catWorkSorItemId") Long catWorkSorItemId);

	/**
	 * Find all by sub estimate id and category id and cat work sor item id is not
	 * null.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param categoryId    the category id
	 * @return the list
	 */
	List<WorkEstimateItem> findAllBySubEstimateIdAndCategoryIdAndCatWorkSorItemIdIsNotNull(Long subEstimateId,
			Long categoryId);

	/**
	 * Find max non SOR entry order by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the integer
	 */
	@Query("SELECT MAX(wet.entryOrder) FROM WorkEstimateItem wet WHERE wet.catWorkSorItemId = null and wet.subEstimateId =:subEstimateId")
	Integer findMaxNonSOREntryOrderBySubEstimateId(Long subEstimateId);
}
