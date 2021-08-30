package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.LineEstimate;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the LineEstimate entity.
 */
@Repository
public interface LineEstimateRepository extends JpaRepository<LineEstimate, Long> {

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the optional
	 */
	Optional<LineEstimate> findByWorkEstimateIdAndId(Long workEstimateId, Long id);

	/**
	 * Find by work estimate id order by last modified ts desc.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the page
	 */
	Page<LineEstimate> findByWorkEstimateIdOrderByLastModifiedTsDesc(Long workEstimateId, Pageable pageable);

	/**
	 * Find by work estimate id order by last modified ts desc.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	List<LineEstimate> findByWorkEstimateIdOrderByLastModifiedTsDesc(Long workEstimateId);

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	List<LineEstimate> findAllByWorkEstimateId(Long workEstimateId);

	/**
	 * Sum approximate value by line estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(s.approxRate) FROM LineEstimate s WHERE s.workEstimateId =:workEstimateId")
	BigDecimal sumApproximateValueByLineEstimateId(Long workEstimateId);

}
