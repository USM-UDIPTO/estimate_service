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

import com.dxc.eproc.estimate.model.WorkEstimateMarketRate;
import com.dxc.eproc.estimate.repository.WorkEstimateMarketRateRepository;
import com.dxc.eproc.estimate.service.WorkEstimateMarketRateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateMarketRateDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateMarketRateMapper;

/**
 * Service Implementation for managing {@link WorkEstimateMarketRate}.
 */
@Service
@Transactional
public class WorkEstimateMarketRateServiceImpl implements WorkEstimateMarketRateService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(WorkEstimateMarketRateServiceImpl.class);

    /** The work estimate market rate repository. */
    private final WorkEstimateMarketRateRepository workEstimateMarketRateRepository;

    /** The work estimate market rate mapper. */
    private final WorkEstimateMarketRateMapper workEstimateMarketRateMapper;

    /**
     * Instantiates a new work estimate market rate service impl.
     *
     * @param workEstimateMarketRateRepository the work estimate market rate repository
     * @param workEstimateMarketRateMapper the work estimate market rate mapper
     */
    public WorkEstimateMarketRateServiceImpl(
        WorkEstimateMarketRateRepository workEstimateMarketRateRepository,
        WorkEstimateMarketRateMapper workEstimateMarketRateMapper
    ) {
        this.workEstimateMarketRateRepository = workEstimateMarketRateRepository;
        this.workEstimateMarketRateMapper = workEstimateMarketRateMapper;
    }

    /**
     * Save.
     *
     * @param workEstimateMarketRateDTO the work estimate market rate DTO
     * @return the work estimate market rate DTO
     */
    @Override
    public WorkEstimateMarketRateDTO save(WorkEstimateMarketRateDTO workEstimateMarketRateDTO) {
        log.debug("Request to save WorkEstimateMarketRate : {}", workEstimateMarketRateDTO);
        WorkEstimateMarketRate workEstimateMarketRate = workEstimateMarketRateMapper.toEntity(workEstimateMarketRateDTO);
        if(workEstimateMarketRate.getId() != null) {
        	WorkEstimateMarketRate workEstimateMarketRate_DB = workEstimateMarketRateRepository.findById(workEstimateMarketRate.getId()).get();
        	BeanUtils.copyProperties(workEstimateMarketRate, workEstimateMarketRate_DB);
        	workEstimateMarketRate = workEstimateMarketRateRepository.save(workEstimateMarketRate_DB);
        } else {
        	workEstimateMarketRate = workEstimateMarketRateRepository.save(workEstimateMarketRate);
        }
        
        return workEstimateMarketRateMapper.toDto(workEstimateMarketRate);
    }

    /**
     * Partial update.
     *
     * @param workEstimateMarketRateDTO the work estimate market rate DTO
     * @return the optional
     */
    @Override
    public Optional<WorkEstimateMarketRateDTO> partialUpdate(WorkEstimateMarketRateDTO workEstimateMarketRateDTO) {
        log.debug("Request to partially update WorkEstimateMarketRate : {}", workEstimateMarketRateDTO);

        return workEstimateMarketRateRepository
            .findById(workEstimateMarketRateDTO.getId())
            .map(
                existingWorkEstimateMarketRate -> {
                    workEstimateMarketRateMapper.partialUpdate(existingWorkEstimateMarketRate, workEstimateMarketRateDTO);
                    return existingWorkEstimateMarketRate;
                }
            )
            .map(workEstimateMarketRateRepository::save)
            .map(workEstimateMarketRateMapper::toDto);
    }

    /**
     * Find one.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkEstimateMarketRateDTO> findOne(Long id) {
        log.debug("Request to get WorkEstimateMarketRate : {}", id);
        return workEstimateMarketRateRepository.findById(id).map(workEstimateMarketRateMapper::toDto);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkEstimateMarketRate : {}", id);
        workEstimateMarketRateRepository.deleteById(id);
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
	public Optional<WorkEstimateMarketRateDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id) {
		log.debug("Request to get all WorkEstimateMarketRate : {}, {}", workEstimateItemId, id);
		return workEstimateMarketRateRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateMarketRateMapper::toDto);
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
	public Page<WorkEstimateMarketRateDTO> findAllByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable) {
		log.debug("Request to get all WorkEstimateMarketRate : {}, {}", workEstimateItemId);
		return workEstimateMarketRateRepository.findAllByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateMarketRateMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WorkEstimateMarketRateDTO> findByWorkEstimateItemId(Long workEstimateItemId) {
		log.debug("Request to get all WorkEstimateMarketRate : {}, {}", workEstimateItemId);
		return workEstimateMarketRateMapper.toDto(workEstimateMarketRateRepository.findByWorkEstimateItemId(workEstimateItemId));
	}
}
