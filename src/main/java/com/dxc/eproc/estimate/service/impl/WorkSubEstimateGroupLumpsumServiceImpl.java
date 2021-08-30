package com.dxc.eproc.estimate.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkSubEstimateGroupLumpsum;
import com.dxc.eproc.estimate.repository.WorkSubEstimateGroupLumpsumRepository;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupLumpsumService;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupLumpsumDTO;
import com.dxc.eproc.estimate.service.mapper.WorkSubEstimateGroupLumpsumMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkSubEstimateGroupLumpsum}.
 */
@Service
@Transactional
public class WorkSubEstimateGroupLumpsumServiceImpl implements WorkSubEstimateGroupLumpsumService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupLumpsumServiceImpl.class);

	/** The work sub estimate group lumpsum repository. */
	@Autowired
	private WorkSubEstimateGroupLumpsumRepository workSubEstimateGroupLumpsumRepository;

	/** The work sub estimate group lumpsum mapper. */
	@Autowired
	private WorkSubEstimateGroupLumpsumMapper workSubEstimateGroupLumpsumMapper;

	/**
	 * Save.
	 *
	 * @param workSubEstimateGroupLumpsumDTO the work sub estimate group lumpsum DTO
	 * @return the work sub estimate group lumpsum DTO
	 */
	@Override
	public WorkSubEstimateGroupLumpsumDTO save(WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO) {
		log.debug("Request to save WorkSubEstimateGroupLumpsum : {}", workSubEstimateGroupLumpsumDTO);
		WorkSubEstimateGroupLumpsum workSubEstimateGroupLumpsum = workSubEstimateGroupLumpsumMapper
				.toEntity(workSubEstimateGroupLumpsumDTO);
		workSubEstimateGroupLumpsum = workSubEstimateGroupLumpsumRepository.save(workSubEstimateGroupLumpsum);
		return workSubEstimateGroupLumpsumMapper.toDto(workSubEstimateGroupLumpsum);
	}

	/**
	 * Partial update.
	 *
	 * @param workSubEstimateGroupLumpsumDTO the work sub estimate group lumpsum DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkSubEstimateGroupLumpsumDTO> partialUpdate(
			WorkSubEstimateGroupLumpsumDTO workSubEstimateGroupLumpsumDTO) {
		log.debug("Request to partially update WorkSubEstimateGroupLumpsum : {}", workSubEstimateGroupLumpsumDTO);

		return workSubEstimateGroupLumpsumRepository.findById(workSubEstimateGroupLumpsumDTO.getId())
				.map(existingWorkSubEstimateGroupLumpsum -> {
					workSubEstimateGroupLumpsumMapper.partialUpdate(existingWorkSubEstimateGroupLumpsum,
							workSubEstimateGroupLumpsumDTO);
					return existingWorkSubEstimateGroupLumpsum;
				}).map(workSubEstimateGroupLumpsumRepository::save).map(workSubEstimateGroupLumpsumMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkSubEstimateGroupLumpsumDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkSubEstimateGroupLumpsums");
		return workSubEstimateGroupLumpsumRepository.findAll(pageable).map(workSubEstimateGroupLumpsumMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkSubEstimateGroupLumpsumDTO> findOne(Long id) {
		log.debug("Request to get WorkSubEstimateGroupLumpsum : {}", id);
		return workSubEstimateGroupLumpsumRepository.findById(id).map(workSubEstimateGroupLumpsumMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkSubEstimateGroupLumpsum : {}", id);
		workSubEstimateGroupLumpsumRepository.deleteById(id);
	}

	/**
	 * Find by work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param pageable               the pageable
	 * @return the page
	 */
	@Override
	public Page<WorkSubEstimateGroupLumpsumDTO> findByWorkSubEstimateGroupId(Long workSubEstimateGroupId,
			Pageable pageable) {
		log.debug("Request to get findByWorkSubEstimateGroupId : {}", workSubEstimateGroupId);
		return workSubEstimateGroupLumpsumRepository.findByWorkSubEstimateGroupId(workSubEstimateGroupId, pageable)
				.map(workSubEstimateGroupLumpsumMapper::toDto);
	}

	/**
	 * Find by work sub estimate group id and id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @return the optional
	 */
	@Override
	public Optional<WorkSubEstimateGroupLumpsumDTO> findByWorkSubEstimateGroupIdAndId(Long workSubEstimateGroupId,
			Long id) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndId : {}", workSubEstimateGroupId, id);
		return workSubEstimateGroupLumpsumRepository.findByWorkSubEstimateGroupIdAndId(workSubEstimateGroupId, id)
				.map(workSubEstimateGroupLumpsumMapper::toDto);
	}

	/**
	 * Sum approx rate by group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumApproxRateByGroupId(Long workSubEstimateGroupId) {
		log.debug("Request to get sumApproxRateByGroupId : {}", workSubEstimateGroupId);
		return workSubEstimateGroupLumpsumRepository.sumApproxRateByGroupId(workSubEstimateGroupId);
	}
}
