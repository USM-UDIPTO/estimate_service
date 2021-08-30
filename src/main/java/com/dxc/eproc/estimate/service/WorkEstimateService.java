package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.WorkEstimateSearch;
import com.dxc.eproc.estimate.response.dto.WorkEstimateResponseDTO;
import com.dxc.eproc.estimate.response.dto.WorkEstimateSearchResponseDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateSearchDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkEstimate}.
 */
public interface WorkEstimateService {
	/**
	 * Save a workEstimate.
	 *
	 * @param workEstimateDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkEstimateDTO save(WorkEstimateDTO workEstimateDTO);

	/**
	 * Partial update.
	 *
	 * @param workEstimateDTO the work estimate DTO
	 * @return the optional
	 */
	Optional<WorkEstimateDTO> partialUpdate(WorkEstimateDTO workEstimateDTO);

	/**
	 * Get all the workEstimates.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */

	Page<WorkEstimateDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" workEstimate.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkEstimateDTO> findOne(Long id);

	/**
	 * Find by dept id and file number.
	 *
	 * @param deptId     the dept id
	 * @param fileNumber the file number
	 * @return the optional
	 */
	// create WorkEstimate
	public Optional<WorkEstimateDTO> findByDeptIdAndFileNumber(Long deptId, String fileNumber);

	/**
	 * Find by dept id and file number and id not.
	 *
	 * @param deptId the dept id
	 * @param string the string
	 * @param id     the id
	 * @return the optional
	 */
	// update WorkEstimate
	public Optional<WorkEstimateDTO> findByDeptIdAndFileNumberAndIdNot(Long deptId, String string, Long id);

	/**
	 * Checkfor scheme id.
	 *
	 * @param schemeId the scheme id
	 * @return the optional
	 */
	// schemeId Check
	public Optional<WorkEstimateDTO> findBySchemeId(Long schemeId);

	/**
	 * Find by scheme id and id not.
	 *
	 * @param schemeId the scheme id
	 * @param id       the id
	 * @return the optional
	 */
	public Optional<WorkEstimateDTO> findBySchemeIdAndIdNot(Long schemeId, Long id);

	/**
	 * Modify estimate total.
	 *
	 * @param id            the id
	 * @param estimateTotal the estimate total
	 */
	void modifyEstimateTotal(Long id, BigDecimal estimateTotal);

	/**
	 * Find all by id.
	 *
	 * @param id       the id
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<WorkEstimateDTO> findAllById(Long id, Pageable pageable);

	/**
	 * Gets the estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the estimate totals
	 */
	WorkEstimateResponseDTO getEstimateTotals(Long workEstimateId);

	/**
	 * Calculate estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	BigDecimal calculateEstimateTotals(Long workEstimateId);

	/**
	 * Search work estimate by query DSL.
	 *
	 * @param pageable              the pageable
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @param createdBy             the created by
	 * @param workEstimateSearch    the work estimate search
	 * @return the page
	 * @throws Exception the exception
	 */
	Page<WorkEstimateSearchResponseDTO> searchWorkEstimateByQueryDSL(Pageable pageable,
			WorkEstimateSearchDTO workEstimateSearchDTO, String createdBy, WorkEstimateSearch workEstimateSearch)
			throws Exception;

	/**
	 * Search work estimate for copy estimate.
	 *
	 * @param of                    the of
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @param deptId                the dept id
	 * @param createdBy             the created by
	 * @return the page
	 */
	Page<WorkEstimateSearchResponseDTO> searchWorkEstimateForCopyEstimate(PageRequest of,
			WorkEstimateSearchDTO workEstimateSearchDTO, Long deptId, String createdBy);
}
