package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkType;
import com.dxc.eproc.estimate.repository.WorkTypeRepository;
import com.dxc.eproc.estimate.service.WorkTypeService;
import com.dxc.eproc.estimate.service.dto.WorkTypeDTO;
import com.dxc.eproc.estimate.service.mapper.WorkTypeMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkType}.
 */
@Service
@Transactional
public class WorkTypeServiceImpl implements WorkTypeService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkTypeServiceImpl.class);

	/** The work type repository. */
	private final WorkTypeRepository workTypeRepository;

	/** The work type mapper. */
	private final WorkTypeMapper workTypeMapper;

	/**
	 * Instantiates a new work type service impl.
	 *
	 * @param workTypeRepository the work type repository
	 * @param workTypeMapper     the work type mapper
	 */
	public WorkTypeServiceImpl(WorkTypeRepository workTypeRepository, WorkTypeMapper workTypeMapper) {
		this.workTypeRepository = workTypeRepository;
		this.workTypeMapper = workTypeMapper;
	}

	/**
	 * Save.
	 *
	 * @param workTypeDTO the work type DTO
	 * @return the work type DTO
	 */
	@Override
	public WorkTypeDTO save(WorkTypeDTO workTypeDTO) {
		WorkType workType = null;
		if (workTypeDTO.getId() == null) {
			log.debug("Request to save EstimateType");
			workType = workTypeMapper.toEntity(workTypeDTO);
		} else {
			log.debug("Request to update EstimateType - Id : {}", workTypeDTO.getId());
			Optional<WorkType> workTypeOptional = workTypeRepository.findById(workTypeDTO.getId());
			if (workTypeOptional.isPresent()) {
				workType = workTypeOptional.get();
				workType.activeYn(workTypeDTO.getActiveYn()).valueType(workTypeDTO.getValueType())
						.workTypeName(workTypeDTO.getWorkTypeName()).workTypeValue(workTypeDTO.getWorkTypeValue());
			}
		}

		workType = workTypeRepository.save(workType);
		return workTypeMapper.toDto(workType);
	}

	/**
	 * Partial update.
	 *
	 * @param workTypeDTO the work type DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkTypeDTO> partialUpdate(WorkTypeDTO workTypeDTO) {
		log.debug("Request to partially update WorkType : {}", workTypeDTO);

		return workTypeRepository.findById(workTypeDTO.getId()).map(existingWorkType -> {
			workTypeMapper.partialUpdate(existingWorkType, workTypeDTO);
			return existingWorkType;
		}).map(workTypeRepository::save).map(workTypeMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkTypeDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkTypes");
		return workTypeRepository.findAll(pageable).map(workTypeMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkTypeDTO> findAllActive(Pageable pageable) {
		log.debug("Request to get all Active WorkTypes");
		return workTypeRepository.findAllByActiveYn(true, pageable).map(workTypeMapper::toDto);
	}

	/**
	 * Find all active estimate work types.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<WorkTypeDTO> findAllActiveEstimateWorkTypes() {
		log.debug("Request to get all Active WorkTypes");
		return workTypeMapper.toDto(workTypeRepository.findAllByActiveYn(true));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkTypeDTO> findOne(Long id) {
		log.debug("Request to get WorkType : {}", id);
		return workTypeRepository.findById(id).map(workTypeMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkType : {}", id);
		Optional<WorkType> workTypeOptional = workTypeRepository.findById(id);
		if (workTypeOptional.isPresent()) {
			WorkType workType = workTypeOptional.get();
			workType.activeYn(true);
			workTypeRepository.save(workType);
		}

	}

	@Override
	public Optional<WorkTypeDTO> findByIdAndActiveYnTrue(Long id) {
		log.debug("Request to get WorkType : {}", id);
		return workTypeRepository.findByIdAndActiveYnTrue(id).map(workTypeMapper::toDto);
	}
}
