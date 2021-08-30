package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkCategoryAttribute;
import com.dxc.eproc.estimate.service.dto.WorkCategoryAttributeDTO;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkCategoryAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkCategoryAttributeRepository extends JpaRepository<WorkCategoryAttribute, Long> {

	/**
	 * Find all by active yn.
	 *
	 * @param b        the b
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkCategoryAttribute> findAllByActiveYn(boolean b, Pageable pageable);

	/**
	 * Find all by work type id and work category id and active yn.
	 *
	 * @param workTypeId     the work type id
	 * @param workCategoryId the work category id
	 * @param b              the b
	 * @return the list
	 */
	List<WorkCategoryAttribute> findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn(Long workTypeId, Long workCategoryId,
			boolean b);
}
