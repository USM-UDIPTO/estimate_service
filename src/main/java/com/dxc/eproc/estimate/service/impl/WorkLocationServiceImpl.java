package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkLocation;
import com.dxc.eproc.estimate.repository.WorkLocationRepository;
import com.dxc.eproc.estimate.service.WorkLocationService;
import com.dxc.eproc.estimate.service.dto.WorkLocationDTO;
import com.dxc.eproc.estimate.service.mapper.WorkLocationMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkLocation}.
 */
@Service
@Transactional
public class WorkLocationServiceImpl implements WorkLocationService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkLocationServiceImpl.class);

	/** The work location repository. */
	private final WorkLocationRepository workLocationRepository;

	/** The work location mapper. */
	private final WorkLocationMapper workLocationMapper;

	/**
	 * Instantiates a new work location service impl.
	 *
	 * @param workLocationRepository the work location repository
	 * @param workLocationMapper     the work location mapper
	 */
	public WorkLocationServiceImpl(WorkLocationRepository workLocationRepository,
			WorkLocationMapper workLocationMapper) {
		this.workLocationRepository = workLocationRepository;
		this.workLocationMapper = workLocationMapper;
	}

	/**
	 * Save.
	 *
	 * @param workLocationDTO the work location DTO
	 * @return the work location DTO
	 */
	@Override
	public WorkLocationDTO save(WorkLocationDTO workLocationDTO) {
		WorkLocation workLocation = null;
		if (workLocationDTO.getId() == null) {
			log.debug("Request to save WorkLocation");
			workLocation = workLocationMapper.toEntity(workLocationDTO);
		} else {
			log.debug("Request to update EstimateType - Id : {}", workLocationDTO.getId());
			Optional<WorkLocation> workLocationOptional = workLocationRepository.findById(workLocationDTO.getId());
			if (workLocationOptional.isPresent()) {
				workLocation = workLocationOptional.get();
				workLocation.assemblyContId(workLocationDTO.getAssemblyContId())
						.assemblyContName(workLocationDTO.getAssemblyContName())
						.countryId(workLocationDTO.getCountryId()).countryName(workLocationDTO.getCountryName())
						.stateId(workLocationDTO.getStateId()).stateName(workLocationDTO.getStateName())
						.districtId(workLocationDTO.getDistrictId()).districtName(workLocationDTO.getDistrictName())
						.latitudeDegrees(workLocationDTO.getLatitudeDegrees())
						.latitudeMinutes(workLocationDTO.getLatitudeMinutes())
						.latitudeSeconds(workLocationDTO.getLatitudeSeconds())
						.locationDescription(workLocationDTO.getLocationDescription())
						.loksabhaContId(workLocationDTO.getLoksabhaContId())
						.loksabhaContName(workLocationDTO.getLoksabhaContName())
						.longitudeDegrees(workLocationDTO.getLongitudeDegrees())
						.longitudeMinutes(workLocationDTO.getLongitudeMinutes())
						.longitudeSeconds(workLocationDTO.getLatitudeSeconds()).taluqId(workLocationDTO.getTaluqId())
						.taluqName(workLocationDTO.getTaluqName());
			}
		}
		workLocation = workLocationRepository.save(workLocation);
		return workLocationMapper.toDto(workLocation);
	}

	/**
	 * Partial update.
	 *
	 * @param workLocationDTO the work location DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkLocationDTO> partialUpdate(WorkLocationDTO workLocationDTO) {
		log.debug("Request to partially update WorkLocation : {}", workLocationDTO);

		return workLocationRepository.findById(workLocationDTO.getId()).map(existingWorkLocation -> {
			workLocationMapper.partialUpdate(existingWorkLocation, workLocationDTO);
			return existingWorkLocation;
		}).map(workLocationRepository::save).map(workLocationMapper::toDto);
	}

	/**
	 * Gets the all work locations by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param pageable      the pageable
	 * @return the all work locations by sub estimate id
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkLocationDTO> getAllWorkLocationsBySubEstimateId(Long subEstimateId, Pageable pageable) {

		return workLocationRepository.findBySubEstimateIdOrderByLastModifiedTsDesc(subEstimateId, pageable)
				.map(workLocationMapper::toDto);
	}

	/**
	 * Gets the all work locations by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the all work locations by sub estimate id
	 */
	@Override
	@Transactional(readOnly = true)
	public List<WorkLocationDTO> getAllWorkLocationsBySubEstimateId(Long subEstimateId) {
		return workLocationMapper
				.toDto(workLocationRepository.findBySubEstimateIdOrderByLastModifiedTsAsc(subEstimateId));
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkLocationDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkLocations");
		return workLocationRepository.findAll(pageable).map(workLocationMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkLocationDTO> findOne(Long id) {
		log.debug("Request to get WorkLocation : {}", id);
		return workLocationRepository.findById(id).map(workLocationMapper::toDto);
	}

	/**
	 * Find by sub estimate id and id in.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param ids           the ids
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<WorkLocationDTO> findBySubEstimateIdAndIdIn(Long subEstimateId, List<Long> ids) {
		log.debug("Request to get WorkLocation : {} - {}", subEstimateId, ids);
		return workLocationMapper.toDto(workLocationRepository.findBySubEstimateIdAndIdIn(subEstimateId, ids));
	}

	/**
	 * Find by sub estimate id and id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param id            the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkLocationDTO> findBySubEstimateIdAndId(Long subEstimateId, Long id) {
		log.debug("Request to get subEstimateIdAndId : subEstimateId - {} -> id - {}", subEstimateId, id);
		return workLocationRepository.findBySubEstimateIdAndId(subEstimateId, id).map(workLocationMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkLocation : {}", id);
		workLocationRepository.deleteById(id);
	}
}
