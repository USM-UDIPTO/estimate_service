package com.dxc.eproc.estimate.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.LineEstimate;
import com.dxc.eproc.estimate.repository.LineEstimateRepository;
import com.dxc.eproc.estimate.service.LineEstimateService;
import com.dxc.eproc.estimate.service.dto.LineEstimateDTO;
import com.dxc.eproc.estimate.service.mapper.LineEstimateMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link LineEstimate}.
 */
@Service
@Transactional
public class LineEstimateServiceImpl implements LineEstimateService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(LineEstimateServiceImpl.class);

	/** The line estimate repository. */
	@Autowired
	private LineEstimateRepository lineEstimateRepository;

	/** The line estimate mapper. */
	@Autowired
	private LineEstimateMapper lineEstimateMapper;

	/**
	 * Instantiates a new line estimate service impl.
	 */
	public LineEstimateServiceImpl() {

	}

	/**
	 * Save.
	 *
	 * @param lineEstimateDTO the line estimate DTO
	 * @return the line estimate DTO
	 */
	@Override
	public LineEstimateDTO save(LineEstimateDTO lineEstimateDTO) {
		LineEstimate lineEstimate = null;
		if (lineEstimateDTO.getId() == null) {
			log.debug("Request to save LineEstimate");
			lineEstimate = lineEstimateMapper.toEntity(lineEstimateDTO);
		} else {
			log.debug("Request to save LineEstimate : {}", lineEstimateDTO.getId());
			Optional<LineEstimate> lineEstimateDTOOptional = lineEstimateRepository.findById(lineEstimateDTO.getId());
			if (lineEstimateDTOOptional.isPresent()) {
				lineEstimate = lineEstimateDTOOptional.get();
				lineEstimate.name(lineEstimateDTO.getName()).approxRate(lineEstimateDTO.getApproxRate());
			}
		}
		lineEstimate = lineEstimateRepository.save(lineEstimate);
		return lineEstimateMapper.toDto(lineEstimate);
	}

	/**
	 * Partial update.
	 *
	 * @param lineEstimateDTO the line estimate DTO
	 * @return the optional
	 */
	@Override
	public Optional<LineEstimateDTO> partialUpdate(LineEstimateDTO lineEstimateDTO) {
		log.debug("Request to partially update LineEstimate : {}", lineEstimateDTO);

		return lineEstimateRepository.findById(lineEstimateDTO.getId()).map(existingLineEstimate -> {
			lineEstimateMapper.partialUpdate(existingLineEstimate, lineEstimateDTO);
			return existingLineEstimate;
		}).map(lineEstimateRepository::save).map(lineEstimateMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<LineEstimateDTO> findAll(Pageable pageable) {
		log.debug("Request to get all LineEstimates");
		return lineEstimateRepository.findAll(pageable).map(lineEstimateMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<LineEstimateDTO> findOne(Long id) {
		log.debug("Request to get LineEstimate : {}", id);
		return lineEstimateRepository.findById(id).map(lineEstimateMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete LineEstimate : {}", id);
		lineEstimateRepository.deleteById(id);
	}

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param id             the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<LineEstimateDTO> findByWorkEstimateIdAndId(Long workEstimateId, Long id) {
		log.debug("Request to get WorkEstimateIdAndId : workEstimateId - {} -> id - {}", workEstimateId, id);
		return lineEstimateRepository.findByWorkEstimateIdAndId(workEstimateId, id).map(lineEstimateMapper::toDto);
	}

	/**
	 * Gets the all line estimates by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the all line estimates by work estimate id
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<LineEstimateDTO> getAllLineEstimatesByWorkEstimateId(Long workEstimateId, Pageable pageable) {
		log.debug(
				"Request to get getAllLineEstimatesByWorkEstimateId : workEstimateId - {} -> id - {}" + workEstimateId);
		return lineEstimateRepository.findByWorkEstimateIdOrderByLastModifiedTsDesc(workEstimateId, pageable)
				.map(lineEstimateMapper::toDto);
	}

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<LineEstimateDTO> findAllByWorkEstimateId(Long workEstimateId) {
		log.debug("Request to get findAllByWorkEstimateId : workEstimateId - {} -> id - {}" + workEstimateId);
		return lineEstimateMapper.toDto(lineEstimateRepository.findAllByWorkEstimateId(workEstimateId));
	}

	/**
	 * Sum approximate value by line estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	@Transactional(readOnly = true)
	public BigDecimal sumApproximateValueByLineEstimateId(Long workEstimateId) {
		log.debug(
				"Request to get sumApproximateValueByLineEstimateId : workEstimateId - {} -> id - {}" + workEstimateId);
		return lineEstimateRepository.sumApproximateValueByLineEstimateId(workEstimateId);
	}

}
