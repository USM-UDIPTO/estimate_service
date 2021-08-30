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

import com.dxc.eproc.estimate.model.WorkEstimateLeadCharges;
import com.dxc.eproc.estimate.repository.WorkEstimateLeadChargesRepository;
import com.dxc.eproc.estimate.service.WorkEstimateLeadChargesService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLeadChargesDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateLeadChargesMapper;

/**
 * Service Implementation for managing {@link WorkEstimateLeadCharges}.
 */
@Service
@Transactional
public class WorkEstimateLeadChargesServiceImpl implements WorkEstimateLeadChargesService {

	private final Logger log = LoggerFactory.getLogger(WorkEstimateLeadChargesServiceImpl.class);

	@Autowired
	private WorkEstimateLeadChargesRepository workEstimateLeadChargesRepository;

	@Autowired
	private WorkEstimateLeadChargesMapper workEstimateLeadChargesMapper;

	@Override
	public WorkEstimateLeadChargesDTO save(WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO) {
		log.debug("Request to save WorkEstimateLeadCharges : {}", workEstimateLeadChargesDTO);
		WorkEstimateLeadCharges workEstimateLeadCharges = workEstimateLeadChargesMapper
				.toEntity(workEstimateLeadChargesDTO);
		if (workEstimateLeadCharges.getId() != null) {
			WorkEstimateLeadCharges dbData = workEstimateLeadChargesRepository.findById(workEstimateLeadCharges.getId()).get();
			BeanUtils.copyProperties(workEstimateLeadCharges, dbData);
			workEstimateLeadCharges = workEstimateLeadChargesRepository.save(dbData);
		} else {
			workEstimateLeadCharges = workEstimateLeadChargesRepository.save(workEstimateLeadCharges);
		}
		workEstimateLeadCharges = workEstimateLeadChargesRepository.save(workEstimateLeadCharges);
		return workEstimateLeadChargesMapper.toDto(workEstimateLeadCharges);
	}

	@Override
	public Optional<WorkEstimateLeadChargesDTO> partialUpdate(WorkEstimateLeadChargesDTO workEstimateLeadChargesDTO) {
		log.debug("Request to partially update WorkEstimateLeadCharges : {}", workEstimateLeadChargesDTO);

		return workEstimateLeadChargesRepository.findById(workEstimateLeadChargesDTO.getId())
				.map(existingWorkEstimateLeadCharges -> {
					workEstimateLeadChargesMapper.partialUpdate(existingWorkEstimateLeadCharges,
							workEstimateLeadChargesDTO);
					return existingWorkEstimateLeadCharges;
				}).map(workEstimateLeadChargesRepository::save).map(workEstimateLeadChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateLeadChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable) {
		log.debug("Request to get all WorkEstimateLeadCharges");
		return workEstimateLeadChargesRepository.findAllByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateLeadChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkEstimateLeadChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId) {
		log.debug("Request to get all WorkEstimateLeadCharges");
		return workEstimateLeadChargesMapper.toDto(workEstimateLeadChargesRepository.findByWorkEstimateItemId(workEstimateItemId));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateLeadChargesDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateLeadCharges : {}", id);
		return workEstimateLeadChargesRepository.findById(id).map(workEstimateLeadChargesMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateLeadCharges : {}", id);
		workEstimateLeadChargesRepository.deleteById(id);
	}

	@Override
	public Optional<WorkEstimateLeadChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id) {
		log.debug("Request to find WorkEstimateLeadCharges : {} , {}", workEstimateItemId, id);
		return workEstimateLeadChargesRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateLeadChargesMapper::toDto);
	}
}
