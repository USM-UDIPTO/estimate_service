package com.dxc.eproc.estimate.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.RaParameters;
import com.dxc.eproc.estimate.repository.RaParametersRepository;
import com.dxc.eproc.estimate.service.RaParametersService;
import com.dxc.eproc.estimate.service.dto.RaParametersDTO;
import com.dxc.eproc.estimate.service.mapper.RaParametersMapper;

/**
 * Service Implementation for managing {@link RaParameters}.
 */
@Service
@Transactional
public class RaParametersServiceImpl implements RaParametersService {

	private final Logger log = LoggerFactory.getLogger(RaParametersServiceImpl.class);

	@Autowired
	private RaParametersRepository raParametersRepository;

	@Autowired
	private RaParametersMapper raParametersMapper;

	@Override
	public RaParametersDTO save(RaParametersDTO raParametersDTO) {
		log.debug("Request to save RaParameters : {}", raParametersDTO);
		RaParameters raParameters = raParametersMapper.toEntity(raParametersDTO);
		if (raParameters.getId() != null) {
			RaParameters dbData = raParametersRepository.findById(raParameters.getId()).get();
			BeanUtils.copyProperties(raParameters, dbData);
			raParameters = raParametersRepository.save(dbData);
		} else {
			raParameters = raParametersRepository.save(raParameters);
		}
		return raParametersMapper.toDto(raParameters);
	}

	@Override
	public Optional<RaParametersDTO> partialUpdate(RaParametersDTO raParametersDTO) {
		log.debug("Request to partially update RaParameters : {}", raParametersDTO);

		return raParametersRepository.findById(raParametersDTO.getId()).map(existingRaParameters -> {
			raParametersMapper.partialUpdate(existingRaParameters, raParametersDTO);
			return existingRaParameters;
		}).map(raParametersRepository::save).map(raParametersMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<RaParametersDTO> findAll(Pageable pageable) {
		log.debug("Request to get all RaParameters");
		return raParametersRepository.findAll(pageable).map(raParametersMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RaParametersDTO> findAll() {
		log.debug("Request to get all RaParameters");
		return raParametersMapper.toDto(raParametersRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<RaParametersDTO> findOne(Long id) {
		log.debug("Request to get RaParameters : {}", id);
		return raParametersRepository.findById(id).map(raParametersMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete RaParameters : {}", id);
		raParametersRepository.deleteById(id);
	}

	@Override
	public List<RaParametersDTO> findAllByIdIn(Set<Long> ids) {
		log.debug("Request to get all RaParameters : {}", ids);
		return raParametersMapper.toDto(raParametersRepository.findAllById(ids));
	}
}
