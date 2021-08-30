package com.dxc.eproc.estimate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkEstimateCategory;

/**
 * The Interface WorkEstimateCategoryRepository.
 */
@Repository
public interface WorkEstimateCategoryRepository extends JpaRepository<WorkEstimateCategory, Long> {

	List<WorkEstimateCategory> findAllBySubEstimateId(Long subEstimateId);

	List<WorkEstimateCategory> findAllBySubEstimateIdAndReferenceId(Long subEstimateId, Long sorId);

	WorkEstimateCategory findOneBySubEstimateIdAndReferenceIdNotAndParentIdIsNull(Long subEstimateId, Long sorId);

	List<WorkEstimateCategory> findAllBySubEstimateIdAndReferenceIdAndParentIdIsNotNull(Long subEstimateId, Long sorId);

	WorkEstimateCategory findOneBySubEstimateIdAndParentIdIsNull(Long subEstimateId);

}
