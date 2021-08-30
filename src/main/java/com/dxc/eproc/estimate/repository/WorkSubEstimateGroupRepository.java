package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkSubEstimateGroup;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkSubEstimateGroup entity.
 */
@Repository
public interface WorkSubEstimateGroupRepository extends JpaRepository<WorkSubEstimateGroup, Long> {

	/**
	 * Sum overhead total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(g.overheadTotal) FROM WorkSubEstimateGroup g WHERE g.workEstimateId =:workEstimateId")
	BigDecimal sumOverheadTotalByWorkEstimateId(@Param("workEstimateId") Long workEstimateId);

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param Id             the id
	 * @return the optional
	 */
	Optional<WorkSubEstimateGroup> findByWorkEstimateIdAndId(Long workEstimateId, Long Id);

	/**
	 * Find by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the page
	 */
	Page<WorkSubEstimateGroup> findByWorkEstimateId(Long workEstimateId, Pageable pageable);

	/**
	 * Sum lumpsum total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(g.lumpsumTotal) FROM WorkSubEstimateGroup g WHERE g.workEstimateId =:workEstimateId")
	BigDecimal sumLumpsumTotalByWorkEstimateId(@Param("workEstimateId") Long workEstimateId);
}
