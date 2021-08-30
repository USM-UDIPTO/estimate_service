package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkEstimateLoadUnloadCharges;

/**
 * Spring Data SQL repository for the WorkEstimateLoadUnloadCharges entity.
 */
@Repository
public interface WorkEstimateLoadUnloadChargesRepository extends JpaRepository<WorkEstimateLoadUnloadCharges, Long> {

	Optional<WorkEstimateLoadUnloadCharges> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	Page<WorkEstimateLoadUnloadCharges> findByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

	List<WorkEstimateLoadUnloadCharges> findByWorkEstimateItemId(Long workEstimateItemId);
}
