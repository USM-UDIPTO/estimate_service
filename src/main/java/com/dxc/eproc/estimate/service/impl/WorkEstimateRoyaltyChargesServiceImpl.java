package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkEstimateRoyaltyCharges;
import com.dxc.eproc.estimate.repository.WorkEstimateRoyaltyChargesRepository;
import com.dxc.eproc.estimate.service.WorkEstimateRoyaltyChargesService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRoyaltyChargesDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateRoyaltyChargesMapper;

/**
 * Service Implementation for managing {@link WorkEstimateRoyaltyCharges}.
 */
@Service
@Transactional
public class WorkEstimateRoyaltyChargesServiceImpl implements WorkEstimateRoyaltyChargesService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateRoyaltyChargesServiceImpl.class);

	/** The work estimate royalty charges repository. */
	private final WorkEstimateRoyaltyChargesRepository workEstimateRoyaltyChargesRepository;

	/** The work estimate royalty charges mapper. */
	private final WorkEstimateRoyaltyChargesMapper workEstimateRoyaltyChargesMapper;

	/**
	 * Instantiates a new work estimate royalty charges service impl.
	 *
	 * @param workEstimateRoyaltyChargesRepository the work estimate royalty charges repository
	 * @param workEstimateRoyaltyChargesMapper the work estimate royalty charges mapper
	 */
	public WorkEstimateRoyaltyChargesServiceImpl(
			WorkEstimateRoyaltyChargesRepository workEstimateRoyaltyChargesRepository,
			WorkEstimateRoyaltyChargesMapper workEstimateRoyaltyChargesMapper) {
		this.workEstimateRoyaltyChargesRepository = workEstimateRoyaltyChargesRepository;
		this.workEstimateRoyaltyChargesMapper = workEstimateRoyaltyChargesMapper;
	}

	/**
	 * Save.
	 *
	 * @param workEstimateRoyaltyChargesDTO the work estimate royalty charges DTO
	 * @return the work estimate royalty charges DTO
	 */
	@Override
	public WorkEstimateRoyaltyChargesDTO save(WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO) {
		log.debug("Request to save WorkEstimateRoyaltyCharges : {}", workEstimateRoyaltyChargesDTO);
		WorkEstimateRoyaltyCharges workEstimateRoyaltyCharges = workEstimateRoyaltyChargesMapper
				.toEntity(workEstimateRoyaltyChargesDTO);
		if(workEstimateRoyaltyCharges.getId() != null) {
			WorkEstimateRoyaltyCharges workEstimateRoyaltyCharges_DB = workEstimateRoyaltyChargesRepository
					.findById(workEstimateRoyaltyCharges.getId()).get();
			
			BeanUtils.copyProperties(workEstimateRoyaltyCharges, workEstimateRoyaltyCharges_DB);
			workEstimateRoyaltyCharges = workEstimateRoyaltyChargesRepository.save(workEstimateRoyaltyCharges_DB);
		} else {
			workEstimateRoyaltyCharges = workEstimateRoyaltyChargesRepository.save(workEstimateRoyaltyCharges);
		}
		
		return workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyCharges);
	}

	/**
	 * Partial update.
	 *
	 * @param workEstimateRoyaltyChargesDTO the work estimate royalty charges DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateRoyaltyChargesDTO> partialUpdate(
			WorkEstimateRoyaltyChargesDTO workEstimateRoyaltyChargesDTO) {
		log.debug("Request to partially update WorkEstimateRoyaltyCharges : {}", workEstimateRoyaltyChargesDTO);

		return workEstimateRoyaltyChargesRepository.findById(workEstimateRoyaltyChargesDTO.getId())
				.map(existingWorkEstimateRoyaltyCharges -> {
					workEstimateRoyaltyChargesMapper.partialUpdate(existingWorkEstimateRoyaltyCharges,
							workEstimateRoyaltyChargesDTO);
					return existingWorkEstimateRoyaltyCharges;
				}).map(workEstimateRoyaltyChargesRepository::save).map(workEstimateRoyaltyChargesMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateRoyaltyChargesDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateRoyaltyCharges : {}", id);
		return workEstimateRoyaltyChargesRepository.findById(id).map(workEstimateRoyaltyChargesMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateRoyaltyCharges : {}", id);
		workEstimateRoyaltyChargesRepository.deleteById(id);
	}

	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateRoyaltyChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id) {
		log.debug("Request to get all WorkEstimateRoyaltyCharges : {}, {}", workEstimateItemId, id);
		return workEstimateRoyaltyChargesRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateRoyaltyChargesMapper::toDto);
	}

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateRoyaltyChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable) {
		log.debug("Request to get all WorkEstimateRoyaltyCharges : {}", workEstimateItemId);
		return workEstimateRoyaltyChargesRepository.findAllByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateRoyaltyChargesMapper::toDto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<WorkEstimateRoyaltyChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId) {
		log.debug("Request to get all WorkEstimateRoyaltyCharges : {}", workEstimateItemId);
		return workEstimateRoyaltyChargesMapper.toDto(workEstimateRoyaltyChargesRepository.findByWorkEstimateItemId(workEstimateItemId));
	}
}
