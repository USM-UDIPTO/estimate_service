package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkEstimateLiftCharges;
import com.dxc.eproc.estimate.repository.WorkEstimateLiftChargesRepository;
import com.dxc.eproc.estimate.service.WorkEstimateLiftChargesService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLiftChargesDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateLiftChargesMapper;

/**
 * Service Implementation for managing {@link WorkEstimateLiftCharges}.
 */
@Service
@Transactional
public class WorkEstimateLiftChargesServiceImpl implements WorkEstimateLiftChargesService {

	private final Logger log = LoggerFactory.getLogger(WorkEstimateLiftChargesServiceImpl.class);

	@Autowired
	private WorkEstimateLiftChargesRepository workEstimateLiftChargesRepository;

	@Autowired
	private WorkEstimateLiftChargesMapper workEstimateLiftChargesMapper;

	@Override
	public WorkEstimateLiftChargesDTO save(WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO) {
		log.debug("Request to save WorkEstimateLiftCharges : {}", workEstimateLiftChargesDTO);
		WorkEstimateLiftCharges workEstimateLiftCharges = workEstimateLiftChargesMapper
				.toEntity(workEstimateLiftChargesDTO);
		if (workEstimateLiftCharges.getId() != null) {
			WorkEstimateLiftCharges dbData = workEstimateLiftChargesRepository.findById(workEstimateLiftCharges.getId()).get();
			BeanUtils.copyProperties(workEstimateLiftCharges, dbData);
			workEstimateLiftCharges = workEstimateLiftChargesRepository.save(dbData);
		} else {
			workEstimateLiftCharges = workEstimateLiftChargesRepository.save(workEstimateLiftCharges);
		}
		workEstimateLiftCharges = workEstimateLiftChargesRepository.save(workEstimateLiftCharges);
		return workEstimateLiftChargesMapper.toDto(workEstimateLiftCharges);
	}

	@Override
	public Optional<WorkEstimateLiftChargesDTO> partialUpdate(WorkEstimateLiftChargesDTO workEstimateLiftChargesDTO) {
		log.debug("Request to partially update WorkEstimateLiftCharges : {}", workEstimateLiftChargesDTO);

		return workEstimateLiftChargesRepository.findById(workEstimateLiftChargesDTO.getId())
				.map(existingWorkEstimateLiftCharges -> {
					workEstimateLiftChargesMapper.partialUpdate(existingWorkEstimateLiftCharges,
							workEstimateLiftChargesDTO);
					return existingWorkEstimateLiftCharges;
				}).map(workEstimateLiftChargesRepository::save).map(workEstimateLiftChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateLiftChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable) {
		log.debug("Request to get all WorkEstimateLiftCharges");
		return workEstimateLiftChargesRepository.findAllByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateLiftChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkEstimateLiftChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId) {
		log.debug("Request to get all WorkEstimateLiftCharges");
		return workEstimateLiftChargesMapper.toDto(workEstimateLiftChargesRepository.findByWorkEstimateItemId(workEstimateItemId));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateLiftChargesDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateLiftCharges : {}", id);
		return workEstimateLiftChargesRepository.findById(id).map(workEstimateLiftChargesMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateLiftCharges : {}", id);
		workEstimateLiftChargesRepository.deleteById(id);
	}

	@Override
	public Optional<WorkEstimateLiftChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id) {
		log.debug("Request to delete WorkEstimateLiftCharges : {} , {}", workEstimateItemId, id);
		return workEstimateLiftChargesRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateLiftChargesMapper::toDto);
	}
}
