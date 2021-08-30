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

import com.dxc.eproc.estimate.model.WorkEstimateLoadUnloadCharges;
import com.dxc.eproc.estimate.repository.WorkEstimateLoadUnloadChargesRepository;
import com.dxc.eproc.estimate.service.WorkEstimateLoadUnloadChargesService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateLoadUnloadChargesDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateLoadUnloadChargesMapper;

/**
 * Service Implementation for managing {@link WorkEstimateLoadUnloadCharges}.
 */
@Service
@Transactional
public class WorkEstimateLoadUnloadChargesServiceImpl implements WorkEstimateLoadUnloadChargesService {

	private final Logger log = LoggerFactory.getLogger(WorkEstimateLoadUnloadChargesServiceImpl.class);

	@Autowired
	private WorkEstimateLoadUnloadChargesRepository workEstimateLoadUnloadChargesRepository;

	@Autowired
	private WorkEstimateLoadUnloadChargesMapper workEstimateLoadUnloadChargesMapper;

	@Override
	public WorkEstimateLoadUnloadChargesDTO save(WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO) {
		log.debug("Request to save WorkEstimateLoadUnloadCharges : {}", workEstimateLoadUnloadChargesDTO);
		WorkEstimateLoadUnloadCharges workEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesMapper
				.toEntity(workEstimateLoadUnloadChargesDTO);
		if (workEstimateLoadUnloadCharges.getId() != null) {
			WorkEstimateLoadUnloadCharges dbData = workEstimateLoadUnloadChargesRepository.findById(workEstimateLoadUnloadChargesDTO.getId()).get();
			BeanUtils.copyProperties(workEstimateLoadUnloadCharges, dbData);
			workEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesRepository.save(dbData);
		} else {
			workEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesRepository.save(workEstimateLoadUnloadCharges);
		}
		workEstimateLoadUnloadCharges = workEstimateLoadUnloadChargesRepository.save(workEstimateLoadUnloadCharges);
		return workEstimateLoadUnloadChargesMapper.toDto(workEstimateLoadUnloadCharges);
	}

	@Override
	public Optional<WorkEstimateLoadUnloadChargesDTO> partialUpdate(
			WorkEstimateLoadUnloadChargesDTO workEstimateLoadUnloadChargesDTO) {
		log.debug("Request to partially update WorkEstimateLoadUnloadCharges : {}", workEstimateLoadUnloadChargesDTO);

		return workEstimateLoadUnloadChargesRepository.findById(workEstimateLoadUnloadChargesDTO.getId())
				.map(existingWorkEstimateLoadUnloadCharges -> {
					workEstimateLoadUnloadChargesMapper.partialUpdate(existingWorkEstimateLoadUnloadCharges,
							workEstimateLoadUnloadChargesDTO);
					return existingWorkEstimateLoadUnloadCharges;
				}).map(workEstimateLoadUnloadChargesRepository::save).map(workEstimateLoadUnloadChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateLoadUnloadChargesDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable) {
		log.debug("Request to get all WorkEstimateLoadUnloadCharges");
		return workEstimateLoadUnloadChargesRepository.findByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateLoadUnloadChargesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkEstimateLoadUnloadChargesDTO> findByWorkEstimateItemId(Long workEstimateItemId) {
		log.debug("Request to get all WorkEstimateLoadUnloadCharges");
		return workEstimateLoadUnloadChargesMapper.toDto(workEstimateLoadUnloadChargesRepository.findByWorkEstimateItemId(workEstimateItemId));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateLoadUnloadChargesDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateLoadUnloadCharges : {}", id);
		return workEstimateLoadUnloadChargesRepository.findById(id).map(workEstimateLoadUnloadChargesMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateLoadUnloadCharges : {}", id);
		workEstimateLoadUnloadChargesRepository.deleteById(id);
	}

	@Override
	public Optional<WorkEstimateLoadUnloadChargesDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id) {
		log.debug("Request to delete WorkEstimateLoadUnloadCharges : {} , {}", workEstimateItemId, id);
		return workEstimateLoadUnloadChargesRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateLoadUnloadChargesMapper::toDto);
	}
}
