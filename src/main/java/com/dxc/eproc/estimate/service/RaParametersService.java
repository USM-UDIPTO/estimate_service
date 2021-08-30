package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.RaParametersDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.domain.RaParameters}.
 */
public interface RaParametersService {
	/**
	 * Save a raParameters.
	 *
	 * @param raParametersDTO the entity to save.
	 * @return the persisted entity.
	 */
	RaParametersDTO save(RaParametersDTO raParametersDTO);

	/**
	 * Partially updates a raParameters.
	 *
	 * @param raParametersDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<RaParametersDTO> partialUpdate(RaParametersDTO raParametersDTO);

	/**
	 * Get all the raParameters.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<RaParametersDTO> findAll(Pageable pageable);

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<RaParametersDTO> findAll();

	/**
	 * Get the "id" raParameters.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<RaParametersDTO> findOne(Long id);

	/**
	 * Delete the "id" raParameters.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	List<RaParametersDTO> findAllByIdIn(Set<Long> ids);
}
