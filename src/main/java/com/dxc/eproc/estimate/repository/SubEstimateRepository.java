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

import com.dxc.eproc.estimate.model.SubEstimate;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the SubEstimate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubEstimateRepository extends JpaRepository<SubEstimate, Long> {

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the optional
	 */
	Optional<SubEstimate> findByWorkEstimateIdAndId(Long workEstimateId, Long id);

	/**
	 * Find by work estimate id and id in.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the list
	 */
	List<SubEstimate> findByWorkEstimateIdAndIdIn(Long workEstimateId, List<Long> id);

	/**
	 * Sum estimate total by work estimate id and id in.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(s.estimateTotal) FROM SubEstimate s WHERE s.workEstimateId =:workEstimateId AND s.id IN :id")
	BigDecimal sumEstimateTotalByWorkEstimateIdAndIdIn(@Param("workEstimateId") Long workEstimateId,
			@Param("id") List<Long> id);

	/**
	 * Find by work estimate id and work sub estimate group id.
	 *
	 * @param workEstimateId         the work estimate id
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the list
	 */
	List<SubEstimate> findByWorkEstimateIdAndWorkSubEstimateGroupId(Long workEstimateId, Long workSubEstimateGroupId);

	/**
	 * Sum of estimate total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(s.estimateTotal) FROM SubEstimate s WHERE s.workEstimateId =:workEstimateId")
	BigDecimal sumOfEstimateTotalByWorkEstimateId(@Param("workEstimateId") Long workEstimateId);

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the page
	 */
	Page<SubEstimate> findAllByWorkEstimateId(Long workEstimateId, Pageable pageable);

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	List<SubEstimate> findAllByWorkEstimateId(Long workEstimateId);

	/**
	 * Find all by work estimate id and id in and completed yn.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateIds the sub estimate ids
	 * @param b              the b
	 * @return the list
	 */
	List<SubEstimate> findAllByWorkEstimateIdAndIdInAndCompletedYn(Long workEstimateId, List<Long> subEstimateIds,
			boolean b);
}
