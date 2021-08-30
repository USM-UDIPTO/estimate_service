package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.SchemeType;
import com.dxc.eproc.estimate.service.dto.SchemeCodeDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.SchemeCode}.
 */
public interface SchemeCodeService {
	/**
	 * Save a schemeCode.
	 *
	 * @param schemeCodeDTO the entity to save.
	 * @return the persisted entity.
	 */
	SchemeCodeDTO save(SchemeCodeDTO schemeCodeDTO);

	/**
	 * Partially updates a schemeCode.
	 *
	 * @param schemeCodeDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<SchemeCodeDTO> partialUpdate(SchemeCodeDTO schemeCodeDTO);

	/**
	 * Get all the schemeCodes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<SchemeCodeDTO> findAll(Pageable pageable);

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<SchemeCodeDTO> findAllActive(Pageable pageable);

	/**
	 * Find all by location id and scheme type and scheme code.
	 *
	 * @param locationId the location id
	 * @param schemeType the scheme type
	 * @param schemeCode the scheme code
	 * @return the list
	 */
	List<SchemeCodeDTO> findAllByLocationIdAndSchemeTypeAndSchemeCode(Long locationId, SchemeType schemeType,
			String schemeCode);

	/**
	 * Get the "id" schemeCode.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<SchemeCodeDTO> findOne(Long id);

	/**
	 * Delete the "id" schemeCode.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
}
