package com.dxc.eproc.estimate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.service.dto.RaConfigurationDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing {@link com.dxc.eproc.estimate.domain.RaConfiguration}.
 */
public interface RaConfigurationService {
    /**
     * Save a raConfiguration.
     *
     * @param raConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    RaConfigurationDTO save(RaConfigurationDTO raConfigurationDTO);

    /**
     * Partially updates a raConfiguration.
     *
     * @param raConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RaConfigurationDTO> partialUpdate(RaConfigurationDTO raConfigurationDTO);

    /**
     * Get all the raConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RaConfigurationDTO> findAll(Pageable pageable);

    /**
     * Find all.
     *
     * @return the page
     */
    List<RaConfigurationDTO> findAll();

    /**
     * Get the "id" raConfiguration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RaConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" raConfiguration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<RaConfigurationDTO> findAllByDeptId(Long deptId);
}
