package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.DeptEstimateType;
import com.dxc.eproc.estimate.repository.DeptEstimateTypeRepository;
import com.dxc.eproc.estimate.service.DeptEstimateTypeService;
import com.dxc.eproc.estimate.service.dto.DeptEstimateTypeDTO;
import com.dxc.eproc.estimate.service.mapper.DeptEstimateTypeMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link DeptEstimateType}.
 */
@Service
@Transactional
public class DeptEstimateTypeServiceImpl implements DeptEstimateTypeService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(DeptEstimateTypeServiceImpl.class);

	/** The dept estimate type repository. */
	private final DeptEstimateTypeRepository deptEstimateTypeRepository;

	/** The dept estimate type mapper. */
	private final DeptEstimateTypeMapper deptEstimateTypeMapper;

	/**
	 * Instantiates a new dept estimate type service impl.
	 *
	 * @param deptEstimateTypeRepository the dept estimate type repository
	 * @param deptEstimateTypeMapper     the dept estimate type mapper
	 */
	public DeptEstimateTypeServiceImpl(DeptEstimateTypeRepository deptEstimateTypeRepository,
			DeptEstimateTypeMapper deptEstimateTypeMapper) {
		this.deptEstimateTypeRepository = deptEstimateTypeRepository;
		this.deptEstimateTypeMapper = deptEstimateTypeMapper;
	}

	/**
	 * Save.
	 *
	 * @param deptEstimateTypeDTO the dept estimate type DTO
	 * @return the dept estimate type DTO
	 */
	@Override
	public DeptEstimateTypeDTO save(DeptEstimateTypeDTO deptEstimateTypeDTO) {
		DeptEstimateType deptEstimateType = null;
		if (deptEstimateTypeDTO.getId() == null) {
			log.debug("Request to save DeptEstimateType");
			deptEstimateType = deptEstimateTypeMapper.toEntity(deptEstimateTypeDTO);
		} else {
			log.debug("Request to update DeptEstimateType - Id : {}", deptEstimateTypeDTO.getId());

			Optional<DeptEstimateType> deptEstimateTypeOptional = deptEstimateTypeRepository
					.findById(deptEstimateTypeDTO.getId());
			if (deptEstimateTypeOptional.isPresent()) {
				deptEstimateType = deptEstimateTypeOptional.get();
				deptEstimateType.activeYn(deptEstimateTypeDTO.getActiveYn()).deptId(deptEstimateTypeDTO.getDeptId())
						.flowType(deptEstimateTypeDTO.getFlowType())
						.estimateTypeId(deptEstimateTypeDTO.getEstimateTypeId());
			}
		}
		deptEstimateType = deptEstimateTypeRepository.save(deptEstimateType);
		return deptEstimateTypeMapper.toDto(deptEstimateType);
	}

	/**
	 * Partial update.
	 *
	 * @param deptEstimateTypeDTO the dept estimate type DTO
	 * @return the optional
	 */
	@Override
	public Optional<DeptEstimateTypeDTO> partialUpdate(DeptEstimateTypeDTO deptEstimateTypeDTO) {
		log.debug("Request to partially update DeptEstimateType : {}", deptEstimateTypeDTO);

		return deptEstimateTypeRepository.findById(deptEstimateTypeDTO.getId()).map(existingDeptEstimateType -> {
			deptEstimateTypeMapper.partialUpdate(existingDeptEstimateType, deptEstimateTypeDTO);
			return existingDeptEstimateType;
		}).map(deptEstimateTypeRepository::save).map(deptEstimateTypeMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<DeptEstimateTypeDTO> findAll(Pageable pageable) {
		log.debug("Request to get all DeptEstimateTypes");
		return deptEstimateTypeRepository.findAll(pageable).map(deptEstimateTypeMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<DeptEstimateTypeDTO> findAllActive(Pageable pageable) {
		log.debug("Request to get all active DeptEstimateTypes");
		return deptEstimateTypeRepository.findAllByActiveYn(true, pageable).map(deptEstimateTypeMapper::toDto);
	}

	/**
	 * Gets the all active dept estimate types for work estimate.
	 *
	 * @param deptId the dept id
	 * @return the all active dept estimate types for work estimate
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DeptEstimateTypeDTO> getAllActiveDeptEstimateTypesForWorkEstimate(Long deptId) {
		log.debug("Request to get all active DeptEstimateTypes for WorkEstiamte By deptId");
		return deptEstimateTypeMapper
				.toDto(deptEstimateTypeRepository.findAllActiveDeptEstimateTypesForWorkEstimate(deptId));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DeptEstimateTypeDTO> findOne(Long id) {
		log.debug("Request to get DeptEstimateType : {}", id);
		return deptEstimateTypeRepository.findById(id).map(deptEstimateTypeMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete DeptEstimateType : {}", id);
		Optional<DeptEstimateType> deptEstimateTypeOptional = deptEstimateTypeRepository.findById(id);
		if (deptEstimateTypeOptional.isPresent()) {
			DeptEstimateType deptEstimateType = deptEstimateTypeOptional.get();
			deptEstimateType.activeYn(false);
			deptEstimateTypeRepository.save(deptEstimateType);
		}

	}
}
