package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.DeptEstimateType;
import com.dxc.eproc.estimate.model.EstimateType;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the EstimateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstimateTypeRepository extends JpaRepository<EstimateType, Long> {

	/**
	 * Find all by active yn.
	 *
	 * @param b        the b
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<EstimateType> findAllByActiveYn(boolean b, Pageable pageable);

	/**
	 * Find all by active yn.
	 *
	 * @param b the b
	 * @return the list
	 */
	List<EstimateType> findAllByActiveYn(boolean b);
}
