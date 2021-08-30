package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.enumeration.OverHeadType;
import com.dxc.eproc.estimate.model.WorkEstimateOverhead;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkEstimateOverhead entity.
 */
@Repository
public interface WorkEstimateOverheadRepository extends JpaRepository<WorkEstimateOverhead, Long> {

	/**
	 * Find by work estimate id order by last modified ts desc.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the page
	 */
	Page<WorkEstimateOverhead> findByWorkEstimateIdOrderByLastModifiedTsDesc(Long workEstimateId, Pageable pageable);

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the optional
	 */
	Optional<WorkEstimateOverhead> findByWorkEstimateIdAndId(Long workEstimateId, Long id);

	/**
	 * Sum normal over head by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(s.overHeadValue) FROM WorkEstimateOverhead s WHERE s.workEstimateId =:workEstimateId AND s.overHeadType ='NORMAL'")
	BigDecimal sumNormalOverHeadByWorkEstimateId(Long workEstimateId);

	/**
	 * Sum additional over head by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(s.overHeadValue) FROM WorkEstimateOverhead s WHERE s.workEstimateId =:workEstimateId AND s.overHeadType = 'ADDITIONAL'")
	BigDecimal sumAdditionalOverHeadByWorkEstimateId(Long workEstimateId);

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	List<WorkEstimateOverhead> findAllByWorkEstimateId(Long workEstimateId);

	/**
	 * Find all by work estimate id and over head type.
	 *
	 * @param workEstimateId the work estimate id
	 * @param overHeadType   the over head type
	 * @return the list
	 */
	List<WorkEstimateOverhead> findAllByWorkEstimateIdAndOverHeadType(Long workEstimateId, OverHeadType overHeadType);

}
