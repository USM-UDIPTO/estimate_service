package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkType;
import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {

	/**
	 * Find all by active yn.
	 *
	 * @param b        the b
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkType> findAllByActiveYn(boolean b, Pageable pageable);

	/**
	 * Find all by active yn.
	 *
	 * @param b the b
	 * @return the list
	 */
	List<WorkType> findAllByActiveYn(boolean b);

	Optional<WorkType> findByIdAndActiveYnTrue(Long id);
}
