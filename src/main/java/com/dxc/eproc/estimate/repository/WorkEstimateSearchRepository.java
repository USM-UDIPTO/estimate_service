package com.dxc.eproc.estimate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkEstimate;

/**
 * The Interface WorkEstimateSearchRepository.
 */
@Transactional
public interface WorkEstimateSearchRepository extends JpaRepository<WorkEstimate, Long>, CustomWorkEstimateSearch {

}
