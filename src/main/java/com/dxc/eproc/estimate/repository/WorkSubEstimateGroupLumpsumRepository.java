package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkSubEstimateGroupLumpsum entity.
 */
@Repository
public interface WorkSubEstimateGroupLumpsumRepository extends JpaRepository<WorkSubEstimateGroupLumpsum, Long> {

	/**
	 * Find by work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param pageable               the pageable
	 * @return the page
	 */
	Page<WorkSubEstimateGroupLumpsum> findByWorkSubEstimateGroupId(Long workSubEstimateGroupId, Pageable pageable);

	/**
	 * Find by work sub estimate group id and id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @return the optional
	 */
	Optional<WorkSubEstimateGroupLumpsum> findByWorkSubEstimateGroupIdAndId(Long workSubEstimateGroupId, Long id);

	/**
	 * Sum approx rate by group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(l.approxRate) FROM WorkSubEstimateGroupLumpsum l WHERE l.workSubEstimateGroupId =:workSubEstimateGroupId")
	BigDecimal sumApproxRateByGroupId(@Param("workSubEstimateGroupId") Long workSubEstimateGroupId);
}
