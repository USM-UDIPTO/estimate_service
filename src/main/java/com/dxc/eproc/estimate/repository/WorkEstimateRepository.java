package com.dxc.eproc.estimate.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.WorkEstimate;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkEstimate entity.
 */
@Repository
public interface WorkEstimateRepository extends JpaRepository<WorkEstimate, Long> {

	/**
	 * Find by dept id and file number.
	 *
	 * @param deptId     the dept id
	 * @param fileNumber the file number
	 * @return the optional
	 */
	Optional<WorkEstimate> findByDeptIdAndFileNumber(Long deptId, String fileNumber);

	/**
	 * Find by dept id and file number and id not.
	 *
	 * @param deptId     the dept id
	 * @param filenumber the filenumber
	 * @param id         the id
	 * @return the optional
	 */
	Optional<WorkEstimate> findByDeptIdAndFileNumberAndIdNot(Long deptId, String filenumber, Long id);

	/**
	 * Find by scheme id.
	 *
	 * @param schemeId the scheme id
	 * @return the optional
	 */
	Optional<WorkEstimate> findBySchemeId(Long schemeId);

	/**
	 * Find by scheme id and id not.
	 *
	 * @param schemeId the scheme id
	 * @param id       the id
	 * @return the optional
	 */
	Optional<WorkEstimate> findBySchemeIdAndIdNot(Long schemeId, Long id);

	/**
	 * Find all by id.
	 *
	 * @param id       the id
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkEstimate> findAllById(Long id, Pageable pageable);

}
