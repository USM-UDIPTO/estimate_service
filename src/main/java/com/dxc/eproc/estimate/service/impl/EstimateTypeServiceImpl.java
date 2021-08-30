package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.EstimateType;
import com.dxc.eproc.estimate.repository.EstimateTypeRepository;
import com.dxc.eproc.estimate.service.EstimateTypeService;
import com.dxc.eproc.estimate.service.dto.EstimateTypeDTO;
import com.dxc.eproc.estimate.service.mapper.EstimateTypeMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link EstimateType}.
 */
@Service
@Transactional
public class EstimateTypeServiceImpl implements EstimateTypeService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(EstimateTypeServiceImpl.class);

	/** The estimate type repository. */
	private final EstimateTypeRepository estimateTypeRepository;

	/** The estimate type mapper. */
	private final EstimateTypeMapper estimateTypeMapper;

	/**
	 * Instantiates a new estimate type service impl.
	 *
	 * @param estimateTypeRepository the estimate type repository
	 * @param estimateTypeMapper     the estimate type mapper
	 */
	public EstimateTypeServiceImpl(EstimateTypeRepository estimateTypeRepository,
			EstimateTypeMapper estimateTypeMapper) {
		this.estimateTypeRepository = estimateTypeRepository;
		this.estimateTypeMapper = estimateTypeMapper;
	}

	/**
	 * Save.
	 *
	 * @param estimateTypeDTO the estimate type DTO
	 * @return the estimate type DTO
	 */
	@Override
	public EstimateTypeDTO save(EstimateTypeDTO estimateTypeDTO) {
		EstimateType estimateType = null;
		if (estimateTypeDTO.getId() == null) {
			log.debug("Request to save EstimateType");
			estimateType = estimateTypeMapper.toEntity(estimateTypeDTO);
		} else {
			log.debug("Request to update EstimateType - Id : {}", estimateTypeDTO.getId());

			Optional<EstimateType> estimateTypeOptional = estimateTypeRepository.findById(estimateTypeDTO.getId());
			if (estimateTypeOptional.isPresent()) {
				estimateType = estimateTypeOptional.get();
				estimateType.estimateTypeValue(estimateTypeDTO.getEstimateTypeValue())
						.valueType(estimateTypeDTO.getValueType()).activeYn(estimateTypeDTO.getActiveYn());
			}
		}
		estimateType = estimateTypeRepository.save(estimateType);
		return estimateTypeMapper.toDto(estimateType);
	}

	/**
	 * Partial update.
	 *
	 * @param estimateTypeDTO the estimate type DTO
	 * @return the optional
	 */
	@Override
	public Optional<EstimateTypeDTO> partialUpdate(EstimateTypeDTO estimateTypeDTO) {
		log.debug("Request to partially update EstimateType : {}", estimateTypeDTO);

		return estimateTypeRepository.findById(estimateTypeDTO.getId()).map(existingEstimateType -> {
			estimateTypeMapper.partialUpdate(existingEstimateType, estimateTypeDTO);
			return existingEstimateType;
		}).map(estimateTypeRepository::save).map(estimateTypeMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<EstimateTypeDTO> findAll(Pageable pageable) {
		log.debug("Request to get all EstimateTypes");
		return estimateTypeRepository.findAll(pageable).map(estimateTypeMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<EstimateTypeDTO> findAllActive(Pageable pageable) {
		log.debug("Request to get all active EstimateTypes");
		return estimateTypeRepository.findAllByActiveYn(true, pageable).map(estimateTypeMapper::toDto);
	}

	/**
	 * Find all active.
	 *
	 * @return the list
	 */
	@Override
	public List<EstimateTypeDTO> findAllActive() {
		log.debug("Request to get all active EstimateTypes");
		return estimateTypeMapper.toDto(estimateTypeRepository.findAllByActiveYn(true));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<EstimateTypeDTO> findOne(Long id) {
		log.debug("Request to get EstimateType : {}", id);
		return estimateTypeRepository.findById(id).map(estimateTypeMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete EstimateType : {}", id);

		Optional<EstimateType> estimateTypeOptional = estimateTypeRepository.findById(id);
		if (estimateTypeOptional.isPresent()) {
			EstimateType estimateType = estimateTypeOptional.get();
			estimateType.setActiveYn(false);
			estimateTypeRepository.save(estimateType);
		}
	}
}
