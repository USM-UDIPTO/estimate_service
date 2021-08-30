package com.dxc.eproc.estimate.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.WorkType;
import com.dxc.eproc.estimate.service.dto.RaFormulaDTO;

/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.domain.RaFormula}.
 */
public interface RaFormulaService {
    /**
     * Save a raFormula.
     *
     * @param raFormulaDTO the entity to save.
     * @return the persisted entity.
     */
    RaFormulaDTO save(RaFormulaDTO raFormulaDTO);

    /**
     * Partially updates a raFormula.
     *
     * @param raFormulaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RaFormulaDTO> partialUpdate(RaFormulaDTO raFormulaDTO);

    /**
     * Get all the raFormulas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RaFormulaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" raFormula.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RaFormulaDTO> findOne(Long id);

    /**
     * Delete the "id" raFormula.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	/**
	 * Find by dept id and id.
	 *
	 * @param deptId the dept id
	 * @param id the id
	 * @return the optional
	 */
	Optional<RaFormulaDTO> findByDeptIdAndId(Long deptId, Long id);

	/**
	 * Find by dept id.
	 *
	 * @param deptId the dept id
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<RaFormulaDTO> findByDeptId(Long deptId, Pageable pageable);

	Optional<RaFormulaDTO> findAllByDeptIdAndWorkType(Long deptId, WorkType workType);
}
