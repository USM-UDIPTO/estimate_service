package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkCategory;
import com.dxc.eproc.estimate.repository.WorkCategoryRepository;
import com.dxc.eproc.estimate.service.WorkCategoryService;
import com.dxc.eproc.estimate.service.dto.WorkCategoryDTO;
import com.dxc.eproc.estimate.service.mapper.WorkCategoryMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkCategory}.
 */
@Service
@Transactional
public class WorkCategoryServiceImpl implements WorkCategoryService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkCategoryServiceImpl.class);

	/** The work category repository. */
	private final WorkCategoryRepository workCategoryRepository;

	/** The work category mapper. */
	private final WorkCategoryMapper workCategoryMapper;

	/**
	 * Instantiates a new work category service impl.
	 *
	 * @param workCategoryRepository the work category repository
	 * @param workCategoryMapper     the work category mapper
	 */
	public WorkCategoryServiceImpl(WorkCategoryRepository workCategoryRepository,
			WorkCategoryMapper workCategoryMapper) {
		this.workCategoryRepository = workCategoryRepository;
		this.workCategoryMapper = workCategoryMapper;
	}

	/**
	 * Save.
	 *
	 * @param workCategoryDTO the work category DTO
	 * @return the work category DTO
	 */
	@Override
	public WorkCategoryDTO save(WorkCategoryDTO workCategoryDTO) {
		WorkCategory workCategory = null;
		if (workCategoryDTO.getId() == null) {
			log.debug("Request to save WorkCategoryAttribute");
			workCategory = workCategoryMapper.toEntity(workCategoryDTO);
		} else {
			log.debug("Request to update WorkCategoryAttribute - Id : {}", workCategoryDTO.getId());
			Optional<WorkCategory> workCategoryOptional = workCategoryRepository.findById(workCategoryDTO.getId());
			if (workCategoryOptional.isPresent()) {
				workCategory = workCategoryOptional.get();
				workCategory.activeYn(workCategoryDTO.getActiveYn()).categoryCode(workCategoryDTO.getCategoryCode())
						.categoryName(workCategoryDTO.getCategoryName());
			}
		}

		workCategory = workCategoryRepository.save(workCategory);
		return workCategoryMapper.toDto(workCategory);
	}

	/**
	 * Partial update.
	 *
	 * @param workCategoryDTO the work category DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkCategoryDTO> partialUpdate(WorkCategoryDTO workCategoryDTO) {
		log.debug("Request to partially update WorkCategory : {}", workCategoryDTO);

		return workCategoryRepository.findById(workCategoryDTO.getId()).map(existingWorkCategory -> {
			workCategoryMapper.partialUpdate(existingWorkCategory, workCategoryDTO);
			return existingWorkCategory;
		}).map(workCategoryRepository::save).map(workCategoryMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkCategoryDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkCategories");
		return workCategoryRepository.findAll(pageable).map(workCategoryMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkCategoryDTO> findAllActive(Pageable pageable) {
		log.debug("Request to get all WorkCategories");
		return workCategoryRepository.findAllByActiveYn(true, pageable).map(workCategoryMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<WorkCategoryDTO> findAllActive() {
		log.debug("Request to get all WorkCategories");
		return workCategoryMapper.toDto(workCategoryRepository.findAllByActiveYn(true));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkCategoryDTO> findOne(Long id) {
		log.debug("Request to get WorkCategory : {}", id);
		return workCategoryRepository.findById(id).map(workCategoryMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkCategory : {}", id);
		Optional<WorkCategory> workCategoryOptional = workCategoryRepository.findById(id);
		if (workCategoryOptional.isPresent()) {
			WorkCategory workCategory = workCategoryOptional.get();
			workCategory.activeYn(false);
			workCategoryRepository.save(workCategory);
		}
	}
}
