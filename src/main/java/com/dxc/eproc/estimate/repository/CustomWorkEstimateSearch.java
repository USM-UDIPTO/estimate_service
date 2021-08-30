package com.dxc.eproc.estimate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.WorkEstimateSearch;
import com.dxc.eproc.estimate.response.dto.WorkEstimateSearchResponseDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateSearchDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface CustomWorkEstimateSearch.
 */
public interface CustomWorkEstimateSearch {

	/**
	 * Search work estimate query DSL.
	 *
	 * @param pageable              the pageable
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @param createdBy             the created by
	 * @param workEstimateSearch    the work estimate search
	 * @return the page
	 */
	public Page<WorkEstimateSearchResponseDTO> searchWorkEstimateQueryDSL(Pageable pageable,
			WorkEstimateSearchDTO workEstimateSearchDTO, String createdBy, WorkEstimateSearch workEstimateSearch);

	/**
	 * Search work estimate for copy estimate.
	 *
	 * @param pageable              the pageable
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @param deptId                the dept id
	 * @param createdBy             the created by
	 * @return the page
	 */
	public Page<WorkEstimateSearchResponseDTO> searchWorkEstimateForCopyEstimate(PageRequest pageable,
			WorkEstimateSearchDTO workEstimateSearchDTO, Long deptId, String createdBy);
}
