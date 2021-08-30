package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkEstimateCategory;
import com.dxc.eproc.estimate.repository.WorkEstimateCategoryRepository;
import com.dxc.eproc.estimate.service.WorkEstimateCategoryService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateCategoryDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateCategoryMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateCategoryServiceImpl.
 */
@Service
@Transactional
public class WorkEstimateCategoryServiceImpl implements WorkEstimateCategoryService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkCategoryServiceImpl.class);

	/** The work estimate category repository. */
	@Autowired
	private WorkEstimateCategoryRepository workEstimateCategoryRepository;

	/** The work estimate category mapper. */
	@Autowired
	private WorkEstimateCategoryMapper workEstimateCategoryMapper;

	/**
	 * Save.
	 *
	 * @param workEstimateCategoryDTO the work estimate category DTO
	 * @return the work estimate category DTO
	 */
	@Override
	public WorkEstimateCategoryDTO save(WorkEstimateCategoryDTO workEstimateCategoryDTO) {
		WorkEstimateCategory workEstimateCategory = null;
		if (workEstimateCategoryDTO.getId() == null) {
			log.debug("Request to save WorkEstimateCategory");
			workEstimateCategory = workEstimateCategoryMapper.toEntity(workEstimateCategoryDTO);
		} else {
			log.debug("Request to update WorkEstimateCategory - Id : {}", workEstimateCategoryDTO.getId());
			Optional<WorkEstimateCategory> workEstimateCategoryOptional = workEstimateCategoryRepository
					.findById(workEstimateCategoryDTO.getId());
			if (workEstimateCategoryOptional.isPresent()) {
				workEstimateCategory = workEstimateCategoryOptional.get();
				workEstimateCategory.categoryCode(workEstimateCategoryDTO.getCategoryCode())
						.categoryName(workEstimateCategoryDTO.getCategoryName())
						.parentId(workEstimateCategoryDTO.getParentId())
						.referenceId(workEstimateCategoryDTO.getReferenceId())
						.itemYn(workEstimateCategoryDTO.getItemYn());
			}
		}

		workEstimateCategory = workEstimateCategoryRepository.save(workEstimateCategory);
		return workEstimateCategoryMapper.toDto(workEstimateCategory);
	}

	/**
	 * Partial update.
	 *
	 * @param workEstimateCategoryDTO the work estimate category DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateCategoryDTO> partialUpdate(WorkEstimateCategoryDTO workEstimateCategoryDTO) {
		log.debug("Request to partially update WorkEstimateCategory : {}", workEstimateCategoryDTO);

		return workEstimateCategoryRepository.findById(workEstimateCategoryDTO.getId()).map(existingWorkCategory -> {
			workEstimateCategoryMapper.partialUpdate(existingWorkCategory, workEstimateCategoryDTO);
			return existingWorkCategory;
		}).map(workEstimateCategoryRepository::save).map(workEstimateCategoryMapper::toDto);
	}

	/**
	 * Find all by sub estimate.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	@Override
	public List<WorkEstimateCategoryDTO> findAllBySubEstimateId(Long subEstimateId) {
		log.debug("Request to findAllBySubEstimateId");
		return workEstimateCategoryMapper.toDto(workEstimateCategoryRepository.findAllBySubEstimateId(subEstimateId));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateCategoryDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateCategory : {}", id);
		return workEstimateCategoryRepository.findById(id).map(workEstimateCategoryMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateCategory : {}", id);
		workEstimateCategoryRepository.deleteById(id);
	}

	@Override
	public List<WorkEstimateCategoryDTO> findAllBySubEstimateIdAndReferenceId(Long subEstimateId, Long sorId) {
		log.debug("Request to findAllBySubEstimateIdAndReferenceId");
		return workEstimateCategoryMapper
				.toDto(workEstimateCategoryRepository.findAllBySubEstimateIdAndReferenceId(subEstimateId, sorId));
	}

	@Override
	public WorkEstimateCategoryDTO findOneBySubEstimateIdAndReferenceIdNotAndParentIdIsNull(Long subEstimateId,
			Long sorId) {
		log.debug("Request to findOneBySubEstimateIdAndReferenceIdNotAndParentIdIsNull");
		return workEstimateCategoryMapper.toDto(workEstimateCategoryRepository
				.findOneBySubEstimateIdAndReferenceIdNotAndParentIdIsNull(subEstimateId, sorId));
	}

	@Override
	public List<WorkEstimateCategoryDTO> findAllBySubEstimateIdAndReferenceIdAndParentIdIsNotNull(Long subEstimateId,
			Long sorId) {
		log.debug("Request to findAllBySubEstimateIdAndReferenceIdAndParentIdIsNotNull");
		return workEstimateCategoryMapper.toDto(workEstimateCategoryRepository
				.findAllBySubEstimateIdAndReferenceIdAndParentIdIsNotNull(subEstimateId, sorId));
	}

	@Override
	public WorkEstimateCategoryDTO findOneBySubEstimateIdAndParentIdIsNull(Long subEstimateId) {
		log.debug("Request to findOneBySubEstimateIdAndParentIdIsNull");
		return workEstimateCategoryMapper
				.toDto(workEstimateCategoryRepository.findOneBySubEstimateIdAndParentIdIsNull(subEstimateId));
	}

}
