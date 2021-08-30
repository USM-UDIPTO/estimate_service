package com.dxc.eproc.estimate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.model.WorkLocation;
import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkLocationRepository extends JpaRepository<WorkLocation, Long> {

	/**
	 * Find by sub estimate id order by last modified ts desc.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param pageable      the pageable
	 * @return the page
	 */
	Page<WorkLocation> findBySubEstimateIdOrderByLastModifiedTsDesc(Long subEstimateId, Pageable pageable);

	/**
	 * Find by sub estimate id and id in.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param ids           the ids
	 * @return the list
	 */
	List<WorkLocation> findBySubEstimateIdAndIdIn(Long subEstimateId, List<Long> ids);

	/**
	 * Find by sub estimate id and id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param id            the id
	 * @return the optional
	 */
	Optional<WorkLocation> findBySubEstimateIdAndId(Long subEstimateId, Long id);

	/**
	 * Find by sub estimate id order by last modified ts asc.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	List<WorkLocation> findBySubEstimateIdOrderByLastModifiedTsAsc(Long subEstimateId);

}
