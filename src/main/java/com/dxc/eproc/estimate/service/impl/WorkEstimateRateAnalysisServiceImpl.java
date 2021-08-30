package com.dxc.eproc.estimate.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkEstimateRateAnalysis;
import com.dxc.eproc.estimate.repository.WorkEstimateRateAnalysisRepository;
import com.dxc.eproc.estimate.service.WorkEstimateRateAnalysisService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateRateAnalysisDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateRateAnalysisMapper;

/**
 * Service Implementation for managing {@link WorkEstimateRateAnalysis}.
 */
@Service
@Transactional
public class WorkEstimateRateAnalysisServiceImpl implements WorkEstimateRateAnalysisService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(WorkEstimateRateAnalysisServiceImpl.class);

    /** The work estimate rate analysis repository. */
    private final WorkEstimateRateAnalysisRepository workEstimateRateAnalysisRepository;

    /** The work estimate rate analysis mapper. */
    private final WorkEstimateRateAnalysisMapper workEstimateRateAnalysisMapper;

    /**
     * Instantiates a new work estimate rate analysis service impl.
     *
     * @param workEstimateRateAnalysisRepository the work estimate rate analysis repository
     * @param workEstimateRateAnalysisMapper the work estimate rate analysis mapper
     */
    public WorkEstimateRateAnalysisServiceImpl(
        WorkEstimateRateAnalysisRepository workEstimateRateAnalysisRepository,
        WorkEstimateRateAnalysisMapper workEstimateRateAnalysisMapper
    ) {
        this.workEstimateRateAnalysisRepository = workEstimateRateAnalysisRepository;
        this.workEstimateRateAnalysisMapper = workEstimateRateAnalysisMapper;
    }

    /**
     * Save.
     *
     * @param workEstimateRateAnalysisDTO the work estimate rate analysis DTO
     * @return the work estimate rate analysis DTO
     */
    @Override
    public WorkEstimateRateAnalysisDTO save(WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO) {
        log.debug("Request to save WorkEstimateRateAnalysis : {}", workEstimateRateAnalysisDTO);
        WorkEstimateRateAnalysis workEstimateRateAnalysis = workEstimateRateAnalysisMapper.toEntity(workEstimateRateAnalysisDTO);
        if(workEstimateRateAnalysis.getId() != null) {
        	WorkEstimateRateAnalysis workEstimateRateAnalysis_DB = workEstimateRateAnalysisRepository
        			.findById(workEstimateRateAnalysis.getId()).get();
        	BeanUtils.copyProperties(workEstimateRateAnalysis, workEstimateRateAnalysis_DB);
        	workEstimateRateAnalysis = workEstimateRateAnalysisRepository.save(workEstimateRateAnalysis_DB);
        } else {
        	workEstimateRateAnalysis = workEstimateRateAnalysisRepository.save(workEstimateRateAnalysis);
        }

        return workEstimateRateAnalysisMapper.toDto(workEstimateRateAnalysis);
    }

    /**
     * Partial update.
     *
     * @param workEstimateRateAnalysisDTO the work estimate rate analysis DTO
     * @return the optional
     */
    @Override
    public Optional<WorkEstimateRateAnalysisDTO> partialUpdate(WorkEstimateRateAnalysisDTO workEstimateRateAnalysisDTO) {
        log.debug("Request to partially update WorkEstimateRateAnalysis : {}", workEstimateRateAnalysisDTO);

        return workEstimateRateAnalysisRepository
            .findById(workEstimateRateAnalysisDTO.getId())
            .map(
                existingWorkEstimateRateAnalysis -> {
                    workEstimateRateAnalysisMapper.partialUpdate(existingWorkEstimateRateAnalysis, workEstimateRateAnalysisDTO);
                    return existingWorkEstimateRateAnalysis;
                }
            )
            .map(workEstimateRateAnalysisRepository::save)
            .map(workEstimateRateAnalysisMapper::toDto);
    }

    /**
     * Find one.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkEstimateRateAnalysisDTO> findOne(Long id) {
        log.debug("Request to get WorkEstimateRateAnalysis : {}", id);
        return workEstimateRateAnalysisRepository.findById(id).map(workEstimateRateAnalysisMapper::toDto);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkEstimateRateAnalysis : {}", id);
        workEstimateRateAnalysisRepository.deleteById(id);
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
	public Optional<WorkEstimateRateAnalysisDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id) {
		log.debug("Request to get all WorkEstimateRateAnalysis : {}, {}", workEstimateItemId, id);
		return workEstimateRateAnalysisRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateRateAnalysisMapper::toDto);
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
	public Page<WorkEstimateRateAnalysisDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable) {
		log.debug("Request to get all WorkEstimateRateAnalysis : {}", workEstimateItemId);
		return workEstimateRateAnalysisRepository.findAllByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateRateAnalysisMapper::toDto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateRateAnalysisDTO> findByWorkEstimateItemId(Long workEstimateItemId) {
		log.debug("Request to get WorkEstimateRateAnalysis : {}", workEstimateItemId);
		return workEstimateRateAnalysisRepository.findByWorkEstimateItemId(workEstimateItemId)
				.map(workEstimateRateAnalysisMapper::toDto);
	}
}
