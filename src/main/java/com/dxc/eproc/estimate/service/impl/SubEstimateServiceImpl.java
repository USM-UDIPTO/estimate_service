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

import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.response.dto.SubEstimateResponseDTO;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.mapper.SubEstimateMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link SubEstimate}.
 */
@Service
@Transactional
public class SubEstimateServiceImpl implements SubEstimateService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SubEstimateServiceImpl.class);

	/** The sub estimate repository. */
	@Autowired
	private SubEstimateRepository subEstimateRepository;

	/** The sub estimate mapper. */
	@Autowired
	private SubEstimateMapper subEstimateMapper;

	/** The work estimate item service. */
	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/**
	 * Instantiates a new sub estimate service impl.
	 */
	public SubEstimateServiceImpl() {
	}

	/**
	 * Save.
	 *
	 * @param subEstimateDTO the sub estimate DTO
	 * @return the sub estimate DTO
	 */
	@Override
	public SubEstimateDTO save(SubEstimateDTO subEstimateDTO) {
		SubEstimate subEstimate = null;
		if (subEstimateDTO.getId() == null) {
			log.debug("Request to save SubEstimate");
			subEstimate = subEstimateMapper.toEntity(subEstimateDTO);
		} else {
			log.debug("Request to update SubEstimate - Id : {}", subEstimateDTO.getId());
			Optional<SubEstimate> subEstimateDTOOptional = subEstimateRepository.findById(subEstimateDTO.getId());
			if (subEstimateDTOOptional.isPresent()) {
				subEstimate = subEstimateDTOOptional.get();
			}
			subEstimate.subEstimateName(subEstimateDTO.getSubEstimateName())
					.completedYn(subEstimateDTO.getCompletedYn());
		}
		subEstimate = subEstimateRepository.save(subEstimate);
		return subEstimateMapper.toDto(subEstimate);
	}

	/**
	 * Partial update.
	 *
	 * @param subEstimateDTO the sub estimate DTO
	 * @return the optional
	 */
	@Override
	public Optional<SubEstimateDTO> partialUpdate(SubEstimateDTO subEstimateDTO) {
		log.debug("Request to partially update SubEstimate : {}", subEstimateDTO);

		return subEstimateRepository.findById(subEstimateDTO.getId()).map(existingSubEstimate -> {
			subEstimateMapper.partialUpdate(existingSubEstimate, subEstimateDTO);
			return existingSubEstimate;
		}).map(subEstimateRepository::save).map(subEstimateMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<SubEstimateDTO> findAll(Pageable pageable) {
		log.debug("Request to get all SubEstimates");
		return subEstimateRepository.findAll(pageable).map(subEstimateMapper::toDto);
	}

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<SubEstimateDTO> findAllByWorkEstimateId(Long workEstimateId, Pageable pageable) {
		log.debug("Request to get all SubEstimates");
		return subEstimateRepository.findAllByWorkEstimateId(workEstimateId, pageable).map(subEstimateMapper::toDto);
	}

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<SubEstimateDTO> findAllByWorkEstimateId(Long workEstimateId) {
		log.debug("Request to get all SubEstimates");
		return subEstimateMapper.toDto(subEstimateRepository.findAllByWorkEstimateId(workEstimateId));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<SubEstimateDTO> findOne(Long id) {
		log.debug("Request to get SubEstimate : {}", id);
		return subEstimateRepository.findById(id).map(subEstimateMapper::toDto);
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
	public Optional<SubEstimateDTO> findByWorkEstimateIdAndId(Long workEstimateId, Long id) {
		log.debug("Request to get findByWorkEstimateIdAndId : workEstimateId - {} -> id - {}", workEstimateId, id);
		return subEstimateRepository.findByWorkEstimateIdAndId(workEstimateId, id).map(subEstimateMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete SubEstimate : {}", id);
		subEstimateRepository.deleteById(id);
	}

	/**
	 * Find by work estimate id and ids.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateIds the sub estimate ids
	 * @return the list
	 */
	@Override
	public List<SubEstimateDTO> findByWorkEstimateIdAndIds(Long workEstimateId, List<Long> subEstimateIds) {
		log.debug("Request to get findByWorkEstimateIdAndId : workEstimateId - {} -> ids - {}", workEstimateId,
				subEstimateIds);
		return subEstimateMapper
				.toDto(subEstimateRepository.findByWorkEstimateIdAndIdIn(workEstimateId, subEstimateIds));
	}

	/**
	 * Find all by work estimate id and id in and completed yn.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateIds the sub estimate ids
	 * @return the list
	 */
	@Override
	public List<SubEstimateDTO> findAllByWorkEstimateIdAndIdInAndCompletedYn(Long workEstimateId,
			List<Long> subEstimateIds) {
		log.debug("Request to get findAllByWorkEstimateIdAndIdInAndCompletedYn : workEstimateId - {} -> ids - {}",
				workEstimateId, subEstimateIds);
		return subEstimateMapper.toDto(subEstimateRepository
				.findAllByWorkEstimateIdAndIdInAndCompletedYn(workEstimateId, subEstimateIds, true));
	}

	/**
	 * Sum estimate total by work estimate id and ids.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateIds the sub estimate ids
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumEstimateTotalByWorkEstimateIdAndIds(Long workEstimateId, List<Long> subEstimateIds) {
		log.debug("Request to get sumEstimateTotalByWorkEstimateIdAndIds : workEstimateId - {} -> subEstimateIds - {}",
				workEstimateId, subEstimateIds);
		return subEstimateRepository.sumEstimateTotalByWorkEstimateIdAndIdIn(workEstimateId, subEstimateIds);
	}

	/**
	 * Find by work estimate id and work sub estimate group id.
	 *
	 * @param workEstimateId         the work estimate id
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the list
	 */
	@Override
	public List<SubEstimateDTO> findByWorkEstimateIdAndWorkSubEstimateGroupId(Long workEstimateId,
			Long workSubEstimateGroupId) {
		log.debug(
				"Request to get findByWorkEstimateIdAndWorkSubEstimateGroupId : workEstimateId - {} -> workSubEstimateGroupId - {}",
				workEstimateId, workSubEstimateGroupId);
		return subEstimateMapper.toDto(subEstimateRepository
				.findByWorkEstimateIdAndWorkSubEstimateGroupId(workEstimateId, workSubEstimateGroupId));
	}

	/**
	 * Clear group id.
	 *
	 * @param id the id
	 */
	@Override
	public void clearGroupId(Long id) {
		log.debug("Request to clear workSubEstimateGroupId");
		subEstimateRepository.findById(id).ifPresent(s -> {
			s.setWorkSubEstimateGroupId(null);
			subEstimateRepository.save(s);
		});
	}

	/**
	 * Modify estimate total.
	 *
	 * @param id            the id
	 * @param estimateTotal the estimate total
	 */
	@Override
	public void modifyEstimateTotal(Long id, BigDecimal estimateTotal) {
		SubEstimateDTO subEstimatePartialDTO = new SubEstimateDTO();
		subEstimatePartialDTO.setId(id);
		subEstimatePartialDTO.setEstimateTotal(estimateTotal);
		partialUpdate(subEstimatePartialDTO);
	}

	/**
	 * Sum of estimate total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumOfEstimateTotalByWorkEstimateId(Long workEstimateId) {
		log.debug("Request to get sumOfEstimateTotalByWorkEstimateId : workEstimateId - {}", workEstimateId);
		return subEstimateRepository.sumOfEstimateTotalByWorkEstimateId(workEstimateId);
	}

	/**
	 * Gets the overall work estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @return the overall work estimate totals
	 */
	@Override
	@Transactional(readOnly = true)
	public SubEstimateResponseDTO getSubEstimateTotals(Long workEstimateId, Long subEstimateId) {
		SubEstimateResponseDTO subEstimateResponseDTO = new SubEstimateResponseDTO();
		subEstimateResponseDTO.setWorkEstimateTotal(sumOfEstimateTotalByWorkEstimateId(workEstimateId));
		subEstimateResponseDTO.setSubEstimateTotal(workEstimateItemService.sumOfWorkEstimateItemTotal(subEstimateId));
		subEstimateResponseDTO
				.setSubEstimateSORTotal(workEstimateItemService.sumOfSORWorkEstimateItemTotal(subEstimateId));
		subEstimateResponseDTO
				.setSubEstimateNonSORTotal(workEstimateItemService.sumOfNonSORWorkEstimateItemTotal(subEstimateId));
		subEstimateResponseDTO.setSubEstimateId(subEstimateId);
		subEstimateResponseDTO.setWorkEstimateId(workEstimateId);
		return subEstimateResponseDTO;
	}

	/**
	 * Calculate sub estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @param subEstimateId  the sub estimate id
	 * @return the sub estimate response DTO
	 */
	@Override
	public SubEstimateResponseDTO calculateSubEstimateTotals(Long workEstimateId, Long subEstimateId) {
		SubEstimateResponseDTO subEstimateResponseDTO = new SubEstimateResponseDTO();
		BigDecimal subEstimateTotal = workEstimateItemService.sumOfWorkEstimateItemTotal(subEstimateId);

		BigDecimal subEstimateSORTotal = workEstimateItemService.sumOfSORWorkEstimateItemTotal(subEstimateId);

		BigDecimal subEstimateNonSORTotal = workEstimateItemService.sumOfNonSORWorkEstimateItemTotal(subEstimateId);

		modifyEstimateTotal(subEstimateId, subEstimateTotal);

		subEstimateResponseDTO.setWorkEstimateTotal(workEstimateService.calculateEstimateTotals(workEstimateId));
		subEstimateResponseDTO.setSubEstimateTotal(subEstimateTotal);
		subEstimateResponseDTO.setSubEstimateSORTotal(subEstimateSORTotal);
		subEstimateResponseDTO.setSubEstimateNonSORTotal(subEstimateNonSORTotal);
		subEstimateResponseDTO.setSubEstimateId(subEstimateId);
		subEstimateResponseDTO.setWorkEstimateId(workEstimateId);
		return subEstimateResponseDTO;
	}
}
