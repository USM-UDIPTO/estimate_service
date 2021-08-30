package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.DeptEstimateType;
import com.dxc.eproc.estimate.service.dto.DeptEstimateTypeDTO;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the DeptEstimateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeptEstimateTypeRepository extends JpaRepository<DeptEstimateType, Long> {

	/**
	 * Find all by active yn.
	 *
	 * @param b        the b
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<DeptEstimateType> findAllByActiveYn(boolean b, Pageable pageable);

	/**
	 * Find all active dept estimate types for work estimate.
	 *
	 * @param deptId the dept id
	 * @return the list
	 */
	@Query("SELECT det FROM DeptEstimateType det WHERE det.activeYn = true AND (det.deptId =:deptId OR det.deptId IS NULL)")
	List<DeptEstimateType> findAllActiveDeptEstimateTypesForWorkEstimate(@Param("deptId") Long deptId);
}
