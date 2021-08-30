package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.enumeration.LBDOperation;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkEstimateItemLBD entity.
 */
@Repository
public interface WorkEstimateItemLBDRepository extends JpaRepository<WorkEstimateItemLBD, Long> {

	/**
	 * Find all by work estimate item id.
	 *
	 * @param sourceItemId the source item id
	 * @return the list
	 */
	List<WorkEstimateItemLBD> findAllByWorkEstimateItemId(Long sourceItemId);

	/**
	 * Sum by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param additionDeduction  the addition deduction
	 * @return the big decimal
	 */
	@Query("SELECT SUM(lbd.lbdTotal) FROM WorkEstimateItemLBD lbd WHERE lbd.workEstimateItemId =:workEstimateItemId AND lbd.additionDeduction =:additionDeduction")
	BigDecimal sumByWorkEstimateItemId(@Param("workEstimateItemId") Long workEstimateItemId,
			@Param("additionDeduction") LBDOperation additionDeduction);

	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id                 the id
	 * @return the optional
	 */
	Optional<WorkEstimateItemLBD> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id);

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable           the pageable
	 * @return the page
	 */
	Page<WorkEstimateItemLBD> findByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable);
}
