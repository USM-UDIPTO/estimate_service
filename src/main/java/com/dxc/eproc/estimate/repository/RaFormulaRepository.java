package com.dxc.eproc.estimate.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.enumeration.WorkType;
import com.dxc.eproc.estimate.model.RaFormula;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the RaFormula entity.
 */
@Repository
public interface RaFormulaRepository extends JpaRepository<RaFormula, Long> {

	/**
	 * Find by department id.
	 *
	 * @param deptId   the dept id
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<RaFormula> findByDeptId(Long deptId, Pageable pageable);

	/**
	 * Find by department id and id.
	 *
	 * @param deptId the dept id
	 * @param id     the id
	 * @return the optional
	 */
	Optional<RaFormula> findByDeptIdAndId(Long deptId, Long id);

	Optional<RaFormula> findByDeptId(Long deptId);

	Optional<RaFormula> findByDeptIdAndWorkType(Long deptId, WorkType workType);
}
