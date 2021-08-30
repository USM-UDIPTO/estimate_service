package com.dxc.eproc.estimate.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.enumeration.WorkType;
import com.dxc.eproc.estimate.model.RaFormula;
import com.dxc.eproc.estimate.repository.RaFormulaRepository;
import com.dxc.eproc.estimate.service.RaFormulaService;
import com.dxc.eproc.estimate.service.dto.RaFormulaDTO;
import com.dxc.eproc.estimate.service.mapper.RaFormulaMapper;

/**
 * Service Implementation for managing {@link RaFormula}.
 */
@Service
@Transactional
public class RaFormulaServiceImpl implements RaFormulaService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(RaFormulaServiceImpl.class);

	/** The ra formula repository. */
	private final RaFormulaRepository raFormulaRepository;

	/** The ra formula mapper. */
	private final RaFormulaMapper raFormulaMapper;

	/**
	 * Instantiates a new ra formula service impl.
	 *
	 * @param raFormulaRepository the ra formula repository
	 * @param raFormulaMapper     the ra formula mapper
	 */
	public RaFormulaServiceImpl(RaFormulaRepository raFormulaRepository, RaFormulaMapper raFormulaMapper) {
		this.raFormulaRepository = raFormulaRepository;
		this.raFormulaMapper = raFormulaMapper;
	}

	/**
	 * Save.
	 *
	 * @param raFormulaDTO the ra formula DTO
	 * @return the ra formula DTO
	 */
	@Override
	public RaFormulaDTO save(RaFormulaDTO raFormulaDTO) {
		log.debug("Request to save RaFormula : {}", raFormulaDTO);
		RaFormula raFormula = raFormulaMapper.toEntity(raFormulaDTO);
		if (raFormula.getId() != null) {
			RaFormula raFormula_DB = raFormulaRepository.findById(raFormula.getId()).get();
			BeanUtils.copyProperties(raFormula, raFormula_DB);
			raFormula = raFormulaRepository.save(raFormula_DB);
		} else {
			raFormula = raFormulaRepository.save(raFormula);
		}

		return raFormulaMapper.toDto(raFormula);
	}

	/**
	 * Partial update.
	 *
	 * @param raFormulaDTO the ra formula DTO
	 * @return the optional
	 */
	@Override
	public Optional<RaFormulaDTO> partialUpdate(RaFormulaDTO raFormulaDTO) {
		log.debug("Request to partially update RaFormula : {}", raFormulaDTO);

		return raFormulaRepository.findById(raFormulaDTO.getId()).map(existingRaFormula -> {
			raFormulaMapper.partialUpdate(existingRaFormula, raFormulaDTO);
			return existingRaFormula;
		}).map(raFormulaRepository::save).map(raFormulaMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<RaFormulaDTO> findAll(Pageable pageable) {
		log.debug("Request to get all RaFormulas");
		return raFormulaRepository.findAll(pageable).map(raFormulaMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<RaFormulaDTO> findOne(Long id) {
		log.debug("Request to get RaFormula : {}", id);
		return raFormulaRepository.findById(id).map(raFormulaMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete RaFormula : {}", id);
		raFormulaRepository.deleteById(id);
	}

	/**
	 * Find by dept id and id.
	 *
	 * @param deptId the dept id
	 * @param id     the id
	 * @return the optional
	 */
	@Override
	public Optional<RaFormulaDTO> findByDeptIdAndId(Long deptId, Long id) {
		log.debug("Request to get all RaFormula : {}, {}", deptId, id);
		return raFormulaRepository.findByDeptIdAndId(deptId, id).map(raFormulaMapper::toDto);
	}

	/**
	 * Find by dept id.
	 *
	 * @param deptId   the dept id
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<RaFormulaDTO> findByDeptId(Long deptId, Pageable pageable) {
		log.debug("Request to get all RaFormula : {}", deptId);
		return raFormulaRepository.findByDeptId(deptId, pageable).map(raFormulaMapper::toDto);
	}

	@Override
	public Optional<RaFormulaDTO> findAllByDeptIdAndWorkType(Long deptId, WorkType workType) {
		log.debug("Request to get all RaFormula : {} {}", deptId, workType);
		Optional<RaFormula> raFormulaOptional = Optional.empty();
		if (workType.equals(WorkType.LABOURCONTRACTWORKS) || workType.equals(WorkType.TURNKEYWORKS)) {
			raFormulaOptional = raFormulaRepository.findByDeptIdAndWorkType(deptId, workType);
		} else {
			raFormulaOptional = raFormulaRepository.findByDeptId(deptId);
		}
		return raFormulaOptional.map(raFormulaMapper::toDto);
	}
}
