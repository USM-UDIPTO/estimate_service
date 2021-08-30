package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkCategory;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkCategoryRepository extends JpaRepository<WorkCategory, Long> {

	/**
	 * Find all by active yn.
	 *
	 * @param b        the b
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkCategory> findAllByActiveYn(boolean b, Pageable pageable);

	/**
	 * Find all by active yn.
	 *
	 * @param b the b
	 * @return the list
	 */
	List<WorkCategory> findAllByActiveYn(boolean b);
}
