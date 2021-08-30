package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkEstimateLiftCharges;

/**
 * Spring Data SQL repository for the WorkEstimateLiftCharges entity.
 */
@Repository
public interface WorkEstimateLiftChargesRepository extends JpaRepository<WorkEstimateLiftCharges, Long> {

	Optional<WorkEstimateLiftCharges> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	Page<WorkEstimateLiftCharges> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);
	
	List<WorkEstimateLiftCharges> findByWorkEstimateItemId(Long workEstimateItemId);
}
