package com.dxc.eproc.estimate.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkEstimateRateAnalysis;

/**
 * Spring Data SQL repository for the WorkEstimateRateAnalysis entity.
 */
@Repository
public interface WorkEstimateRateAnalysisRepository extends JpaRepository<WorkEstimateRateAnalysis, Long> {
	
	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id the id
	 * @return the optional
	 */
	Optional<WorkEstimateRateAnalysis> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkEstimateRateAnalysis> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

	Optional<WorkEstimateRateAnalysis> findByWorkEstimateItemId(Long workEstimateItemId);
}
