package com.dxc.eproc.estimate.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkEstimateAdditionalCharges;
import com.dxc.eproc.estimate.repository.WorkEstimateAdditionalChargesRepository;
import com.dxc.eproc.estimate.service.WorkEstimateAdditionalChargesService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateAdditionalChargesDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateAdditionalChargesMapper;

/**
 * Service Implementation for managing {@link WorkEstimateAdditionalCharges}.
 */
@Service
@Transactional
public class WorkEstimateAdditionalChargesServiceImpl implements WorkEstimateAdditionalChargesService {

	private final Logger log = LoggerFactory.getLogger(WorkEstimateAdditionalChargesServiceImpl.class);

	@Autowired
	private WorkEstimateAdditionalChargesRepository workEstimateAdditionalChargesRepository;

	@Autowired
	private WorkEstimateAdditionalChargesMapper workEstimateAdditionalChargesMapper;

	@Override
	public WorkEstimateAdditionalChargesDTO save(WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO) {
		log.debug("Request to save WorkEstimateAdditionalCharges : {}", workEstimateAdditionalChargesDTO);
		WorkEstimateAdditionalCharges workEstimateAdditionalCharges = workEstimateAdditionalChargesMapper
				.toEntity(workEstimateAdditionalChargesDTO);
		if (workEstimateAdditionalCharges.getId() != null) {
			WorkEstimateAdditionalCharges dbData = workEstimateAdditionalChargesRepository
					.findById(workEstimateAdditionalCharges.getId()).get();
			BeanUtils.copyProperties(workEstimateAdditionalCharges, dbData);
			workEstimateAdditionalCharges = workEstimateAdditionalChargesRepository.save(dbData);
		} else {
			workEstimateAdditionalCharges = workEstimateAdditionalChargesRepository.save(workEstimateAdditionalCharges);
		}
		workEstimateAdditionalCharges = workEstimateAdditionalChargesRepository.save(workEstimateAdditionalCharges);
		return workEstimateAdditionalChargesMapper.toDto(workEstimateAdditionalCharges);
	}

	@Override
	public Optional<WorkEstimateAdditionalChargesDTO> partialUpdate(
			WorkEstimateAdditionalChargesDTO workEstimateAdditionalChargesDTO) {
		log.debug("Request to partially update WorkEstimateAdditionalCharges : {}", workEstimateAdditionalChargesDTO);

		return workEstimateAdditionalChargesRepository.findById(workEstimateAdditionalChargesDTO.getId())
				.map(existingWorkEstimateAdditionalCharges -> {
					workEstimateAdditionalChargesMapper.partialUpdate(existingWorkEstimateAdditionalCharges,
							workEstimateAdditionalChargesDTO);
					return existingWorkEstimateAdditionalCharges;
				}).map(workEstimateAdditionalChargesRepository::save).map(workEstimateAdditionalChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateAdditionalChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId,
			Pageable pageable) {
		log.debug("Request to get all WorkEstimateAdditionalCharges");
		return workEstimateAdditionalChargesRepository.findByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateAdditionalChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateAdditionalChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId) {
		log.debug("Request to get all WorkEstimateAdditionalCharges");
		return workEstimateAdditionalChargesRepository.findByWorkEstimateItemId(workEstimateItemId).map(workEstimateAdditionalChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateAdditionalChargesDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateAdditionalCharges : {}", id);
		return workEstimateAdditionalChargesRepository.findById(id).map(workEstimateAdditionalChargesMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateAdditionalCharges : {}", id);
		workEstimateAdditionalChargesRepository.deleteById(id);
	}

	@Override
	public Optional<WorkEstimateAdditionalChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id) {
		log.debug("Request to delete WorkEstimateAdditionalCharges : {} , {}", workEstimateItemId, id);
		return workEstimateAdditionalChargesRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateAdditionalChargesMapper::toDto);
	}
}
