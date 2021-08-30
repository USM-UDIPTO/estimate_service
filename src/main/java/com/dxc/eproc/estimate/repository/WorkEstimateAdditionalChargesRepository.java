package com.dxc.eproc.estimate.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkEstimateAdditionalCharges;

/**
 * Spring Data SQL repository for the WorkEstimateAdditionalCharges entity.
 */
@Repository
public interface WorkEstimateAdditionalChargesRepository extends JpaRepository<WorkEstimateAdditionalCharges, Long> {

	Optional<WorkEstimateAdditionalCharges> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	Page<WorkEstimateAdditionalCharges> findByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

	Optional<WorkEstimateAdditionalCharges> findByWorkEstimateItemId(Long workEstimateItemId);
}
