package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.enumeration.RaChargeType;
import com.dxc.eproc.estimate.model.WorkEstimateOtherAddnLiftCharges;

/**
 * Spring Data SQL repository for the WorkEstimateOtherAddnLiftCharges entity.
 */
@Repository
public interface WorkEstimateOtherAddnLiftChargesRepository extends JpaRepository<WorkEstimateOtherAddnLiftCharges, Long> {
	
	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id the id
	 * @return the optional
	 */
	Optional<WorkEstimateOtherAddnLiftCharges> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkEstimateOtherAddnLiftCharges> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);

	List<WorkEstimateOtherAddnLiftCharges> findByWorkEstimateItemIdAndType(Long workEstimateItemId, RaChargeType type);
}
