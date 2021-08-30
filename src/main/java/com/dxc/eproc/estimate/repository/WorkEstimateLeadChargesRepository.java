package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkEstimateLeadCharges;

/**
 * Spring Data SQL repository for the WorkEstimateLeadCharges entity.
 */
@Repository
public interface WorkEstimateLeadChargesRepository extends JpaRepository<WorkEstimateLeadCharges, Long> {

	Optional<WorkEstimateLeadCharges> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	Page<WorkEstimateLeadCharges> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

	List<WorkEstimateLeadCharges> findByWorkEstimateItemId(Long workEstimateItemId);
}
