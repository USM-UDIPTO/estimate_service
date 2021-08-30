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

import com.dxc.eproc.estimate.enumeration.RaChargeType;
import com.dxc.eproc.estimate.model.WorkEstimateOtherAddnLiftCharges;
import com.dxc.eproc.estimate.repository.WorkEstimateOtherAddnLiftChargesRepository;
import com.dxc.eproc.estimate.service.WorkEstimateOtherAddnLiftChargesService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOtherAddnLiftChargesDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateOtherAddnLiftChargesMapper;

/**
 * Service Implementation for managing {@link WorkEstimateOtherAddnLiftCharges}.
 */
@Service
@Transactional
public class WorkEstimateOtherAddnLiftChargesServiceImpl implements WorkEstimateOtherAddnLiftChargesService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateOtherAddnLiftChargesServiceImpl.class);

	/** The work estimate other addn lift charges repository. */
	private final WorkEstimateOtherAddnLiftChargesRepository workEstimateOtherAddnLiftChargesRepository;

	/** The work estimate other addn lift charges mapper. */
	private final WorkEstimateOtherAddnLiftChargesMapper workEstimateOtherAddnLiftChargesMapper;

	/**
	 * Instantiates a new work estimate other addn lift charges service impl.
	 *
	 * @param workEstimateOtherAddnLiftChargesRepository the work estimate other
	 *                                                   addn lift charges
	 *                                                   repository
	 * @param workEstimateOtherAddnLiftChargesMapper     the work estimate other
	 *                                                   addn lift charges mapper
	 */
	public WorkEstimateOtherAddnLiftChargesServiceImpl(
			WorkEstimateOtherAddnLiftChargesRepository workEstimateOtherAddnLiftChargesRepository,
			WorkEstimateOtherAddnLiftChargesMapper workEstimateOtherAddnLiftChargesMapper) {
		this.workEstimateOtherAddnLiftChargesRepository = workEstimateOtherAddnLiftChargesRepository;
		this.workEstimateOtherAddnLiftChargesMapper = workEstimateOtherAddnLiftChargesMapper;
	}

	/**
	 * Save.
	 *
	 * @param workEstimateOtherAddnLiftChargesDTO the work estimate other addn lift
	 *                                            charges DTO
	 * @return the work estimate other addn lift charges DTO
	 */
	@Override
	public WorkEstimateOtherAddnLiftChargesDTO save(
			WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO) {
		log.debug("Request to save WorkEstimateOtherAddnLiftCharges : {}", workEstimateOtherAddnLiftChargesDTO);

		WorkEstimateOtherAddnLiftCharges workEstimateOtherAddnLiftCharges = workEstimateOtherAddnLiftChargesMapper
				.toEntity(workEstimateOtherAddnLiftChargesDTO);

		if (workEstimateOtherAddnLiftCharges.getId() != null) {
			WorkEstimateOtherAddnLiftCharges workEstimateOtherAddnLiftCharges_DB = workEstimateOtherAddnLiftChargesRepository
					.findById(workEstimateOtherAddnLiftCharges.getId()).get();

			BeanUtils.copyProperties(workEstimateOtherAddnLiftCharges, workEstimateOtherAddnLiftCharges_DB);
			workEstimateOtherAddnLiftCharges = workEstimateOtherAddnLiftChargesRepository
					.save(workEstimateOtherAddnLiftCharges_DB);
		} else {
			workEstimateOtherAddnLiftCharges = workEstimateOtherAddnLiftChargesRepository
					.save(workEstimateOtherAddnLiftCharges);
		}

		return workEstimateOtherAddnLiftChargesMapper.toDto(workEstimateOtherAddnLiftCharges);
	}

	/**
	 * Partial update.
	 *
	 * @param workEstimateOtherAddnLiftChargesDTO the work estimate other addn lift
	 *                                            charges DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateOtherAddnLiftChargesDTO> partialUpdate(
			WorkEstimateOtherAddnLiftChargesDTO workEstimateOtherAddnLiftChargesDTO) {
		log.debug("Request to partially update WorkEstimateOtherAddnLiftCharges : {}",
				workEstimateOtherAddnLiftChargesDTO);

		return workEstimateOtherAddnLiftChargesRepository.findById(workEstimateOtherAddnLiftChargesDTO.getId())
				.map(existingWorkEstimateOtherAddnLiftCharges -> {
					workEstimateOtherAddnLiftChargesMapper.partialUpdate(existingWorkEstimateOtherAddnLiftCharges,
							workEstimateOtherAddnLiftChargesDTO);
					return existingWorkEstimateOtherAddnLiftCharges;
				}).map(workEstimateOtherAddnLiftChargesRepository::save)
				.map(workEstimateOtherAddnLiftChargesMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateOtherAddnLiftChargesDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateOtherAddnLiftCharges : {}", id);
		return workEstimateOtherAddnLiftChargesRepository.findById(id)
				.map(workEstimateOtherAddnLiftChargesMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateOtherAddnLiftCharges : {}", id);
		workEstimateOtherAddnLiftChargesRepository.deleteById(id);
	}

	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id                 the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateOtherAddnLiftChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId,
			Long id) {
		log.debug("Request to get WorkEstimateOtherAddnLiftCharges : {}, {}", workEstimateItemId, id);

		return workEstimateOtherAddnLiftChargesRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateOtherAddnLiftChargesMapper::toDto);
	}

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable           the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateOtherAddnLiftChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId,
			Pageable pageable) {
		log.debug("Request to get all WorkEstimateOtherAddnLiftCharges : {}", workEstimateItemId);

		return workEstimateOtherAddnLiftChargesRepository.findAllByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateOtherAddnLiftChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkEstimateOtherAddnLiftChargesDTO> findByWorkEstimateItemIdAndType(Long workEstimateItemId, RaChargeType type) {
		log.debug("Request to get all WorkEstimateOtherAddnLiftCharges : {}", workEstimateItemId);

		return workEstimateOtherAddnLiftChargesMapper
				.toDto(workEstimateOtherAddnLiftChargesRepository.findByWorkEstimateItemIdAndType(workEstimateItemId, type));
	}
}
