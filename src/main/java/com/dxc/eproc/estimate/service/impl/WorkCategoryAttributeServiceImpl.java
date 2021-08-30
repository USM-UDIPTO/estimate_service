package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkCategoryAttribute;
import com.dxc.eproc.estimate.repository.WorkCategoryAttributeRepository;
import com.dxc.eproc.estimate.service.WorkCategoryAttributeService;
import com.dxc.eproc.estimate.service.dto.WorkCategoryAttributeDTO;
import com.dxc.eproc.estimate.service.mapper.WorkCategoryAttributeMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkCategoryAttribute}.
 */
@Service
@Transactional
public class WorkCategoryAttributeServiceImpl implements WorkCategoryAttributeService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkCategoryAttributeServiceImpl.class);

	/** The work category attribute repository. */
	private final WorkCategoryAttributeRepository workCategoryAttributeRepository;

	/** The work category attribute mapper. */
	private final WorkCategoryAttributeMapper workCategoryAttributeMapper;

	/**
	 * Instantiates a new work category attribute service impl.
	 *
	 * @param workCategoryAttributeRepository the work category attribute repository
	 * @param workCategoryAttributeMapper     the work category attribute mapper
	 */
	public WorkCategoryAttributeServiceImpl(WorkCategoryAttributeRepository workCategoryAttributeRepository,
			WorkCategoryAttributeMapper workCategoryAttributeMapper) {
		this.workCategoryAttributeRepository = workCategoryAttributeRepository;
		this.workCategoryAttributeMapper = workCategoryAttributeMapper;
	}

	/**
	 * Save.
	 *
	 * @param workCategoryAttributeDTO the work category attribute DTO
	 * @return the work category attribute DTO
	 */
	@Override
	public WorkCategoryAttributeDTO save(WorkCategoryAttributeDTO workCategoryAttributeDTO) {
		WorkCategoryAttribute workCategoryAttribute = null;
		if (workCategoryAttributeDTO.getId() == null) {
			log.debug("Request to save WorkCategoryAttribute");
			workCategoryAttribute = workCategoryAttributeMapper.toEntity(workCategoryAttributeDTO);
		} else {
			log.debug("Request to update WorkCategoryAttribute - Id : {}", workCategoryAttributeDTO.getId());
			Optional<WorkCategoryAttribute> workCategoryAttributeOptional = workCategoryAttributeRepository
					.findById(workCategoryAttributeDTO.getId());
			if (workCategoryAttributeOptional.isPresent()) {
				workCategoryAttribute = workCategoryAttributeOptional.get();
				workCategoryAttribute.activeYn(workCategoryAttributeDTO.getActiveYn())
						.attributeName(workCategoryAttributeDTO.getAttributeName())
						.workCategoryId(workCategoryAttributeDTO.getWorkCategoryId())
						.workTypeId(workCategoryAttributeDTO.getWorkTypeId());
			}
		}
		workCategoryAttribute = workCategoryAttributeRepository.save(workCategoryAttribute);
		return workCategoryAttributeMapper.toDto(workCategoryAttribute);
	}

	/**
	 * Partial update.
	 *
	 * @param workCategoryAttributeDTO the work category attribute DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkCategoryAttributeDTO> partialUpdate(WorkCategoryAttributeDTO workCategoryAttributeDTO) {
		log.debug("Request to partially update WorkCategoryAttribute : {}", workCategoryAttributeDTO);

		return workCategoryAttributeRepository.findById(workCategoryAttributeDTO.getId())
				.map(existingWorkCategoryAttribute -> {
					workCategoryAttributeMapper.partialUpdate(existingWorkCategoryAttribute, workCategoryAttributeDTO);
					return existingWorkCategoryAttribute;
				}).map(workCategoryAttributeRepository::save).map(workCategoryAttributeMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkCategoryAttributeDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkCategoryAttributes");
		return workCategoryAttributeRepository.findAll(pageable).map(workCategoryAttributeMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkCategoryAttributeDTO> findAllActive(Pageable pageable) {
		log.debug("Request to get all Active WorkCategoryAttributes");
		return workCategoryAttributeRepository.findAllByActiveYn(true, pageable)
				.map(workCategoryAttributeMapper::toDto);
	}

	/**
	 * Find all by work type id and work category id and active yn.
	 *
	 * @param workTypeId     the work type id
	 * @param workCategoryId the work category id
	 * @return the list
	 */
	@Override
	public List<WorkCategoryAttributeDTO> findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn(Long workTypeId,
			Long workCategoryId) {
		return workCategoryAttributeMapper.toDto(workCategoryAttributeRepository
				.findAllByWorkTypeIdAndWorkCategoryIdAndActiveYn(workTypeId, workCategoryId, true));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkCategoryAttributeDTO> findOne(Long id) {
		log.debug("Request to get WorkCategoryAttribute : {}", id);
		return workCategoryAttributeRepository.findById(id).map(workCategoryAttributeMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkCategoryAttribute : {}", id);
		Optional<WorkCategoryAttribute> workCategoryAttributeOptional = workCategoryAttributeRepository.findById(id);
		if (workCategoryAttributeOptional.isPresent()) {
			WorkCategoryAttribute workCategoryAttribute = workCategoryAttributeOptional.get();
			workCategoryAttribute.setActiveYn(false);
			workCategoryAttributeRepository.save(workCategoryAttribute);
		}
	}
}
