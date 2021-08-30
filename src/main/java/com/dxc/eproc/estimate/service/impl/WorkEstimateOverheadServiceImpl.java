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

import com.dxc.eproc.estimate.enumeration.OverHeadType;
import com.dxc.eproc.estimate.model.WorkEstimateOverhead;
import com.dxc.eproc.estimate.repository.WorkEstimateOverheadRepository;
import com.dxc.eproc.estimate.service.WorkEstimateOverheadService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateOverheadDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateOverheadMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkEstimateOverhead}.
 */
@Service
@Transactional
public class WorkEstimateOverheadServiceImpl implements WorkEstimateOverheadService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateOverheadServiceImpl.class);

	/** The work estimate overhead repository. */
	@Autowired
	private WorkEstimateOverheadRepository workEstimateOverheadRepository;

	/** The work estimate overhead mapper. */
	@Autowired
	private WorkEstimateOverheadMapper workEstimateOverheadMapper;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/**
	 * Instantiates a new work estimate overhead service impl.
	 */
	public WorkEstimateOverheadServiceImpl() {

	}

	/**
	 * Save.
	 *
	 * @param workEstimateOverheadDTO the work estimate overhead DTO
	 * @return the work estimate overhead DTO
	 */
	@Override
	public WorkEstimateOverheadDTO save(WorkEstimateOverheadDTO workEstimateOverheadDTO) {
		log.debug("Request to save WorkEstimateOverhead : {}", workEstimateOverheadDTO);
		WorkEstimateOverhead workEstimateOverhead = null;
		if (workEstimateOverheadDTO.getId() == null) {
			log.debug("Request to save WorkEstimateOverhead");
			workEstimateOverhead = workEstimateOverheadMapper.toEntity(workEstimateOverheadDTO);
		} else {
			log.debug("Request to save WorkEstimateOverhead : {}", workEstimateOverheadDTO.getId());
			Optional<WorkEstimateOverhead> workEstimateOverheadOptional = workEstimateOverheadRepository
					.findById(workEstimateOverheadDTO.getId());
			if (workEstimateOverheadOptional.isPresent()) {
				workEstimateOverhead = workEstimateOverheadOptional.get();
				workEstimateOverhead.overHeadType(workEstimateOverheadDTO.getOverHeadType())
						.name(workEstimateOverheadDTO.getName())
						.overHeadValue(workEstimateOverheadDTO.getOverHeadValue())
						.fixedYn(workEstimateOverheadDTO.getFixedYn());
			}

		}
		workEstimateOverhead = workEstimateOverheadRepository.save(workEstimateOverhead);
		return workEstimateOverheadMapper.toDto(workEstimateOverhead);
	}

	/**
	 * Partial update.
	 *
	 * @param workEstimateOverheadDTO the work estimate overhead DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateOverheadDTO> partialUpdate(WorkEstimateOverheadDTO workEstimateOverheadDTO) {
		log.debug("Request to partially update WorkEstimateOverhead : {}", workEstimateOverheadDTO);

		return workEstimateOverheadRepository.findById(workEstimateOverheadDTO.getId())
				.map(existingWorkEstimateOverhead -> {
					workEstimateOverheadMapper.partialUpdate(existingWorkEstimateOverhead, workEstimateOverheadDTO);
					return existingWorkEstimateOverhead;
				}).map(workEstimateOverheadRepository::save).map(workEstimateOverheadMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateOverheadDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkEstimateOverheads");
		return workEstimateOverheadRepository.findAll(pageable).map(workEstimateOverheadMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateOverheadDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateOverhead : {}", id);
		return workEstimateOverheadRepository.findById(id).map(workEstimateOverheadMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateOverhead : {}", id);
		workEstimateOverheadRepository.deleteById(id);
	}

	/**
	 * Gets the all work estimate overheads by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the all work estimate overheads by work estimate id
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateOverheadDTO> getAllWorkEstimateOverheadsByWorkEstimateId(Long workEstimateId,
			Pageable pageable) {
		log.debug("Request to get getAllWorkEstimateOverheadsByWorkEstimateId : workEstimateId - {} -> id - {}"
				+ workEstimateId);
		return workEstimateOverheadRepository.findByWorkEstimateIdOrderByLastModifiedTsDesc(workEstimateId, pageable)
				.map(workEstimateOverheadMapper::toDto);
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
	public Optional<WorkEstimateOverheadDTO> findByWorkEstimateIdAndId(Long workEstimateId, Long id) {
		log.debug("Request to get WorkEstimateIdAndId : workEstimateId - {} -> id - {}", workEstimateId, id);
		return workEstimateOverheadRepository.findByWorkEstimateIdAndId(workEstimateId, id)
				.map(workEstimateOverheadMapper::toDto);
	}

	/**
	 * Sum normal over head by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumNormalOverHeadByWorkEstimateId(Long workEstimateId) {
		log.debug("Request to get sumNormalOverHeadByWorkEstimateId : workEstimateId - {} -> id - {}" + workEstimateId);
		return workEstimateOverheadRepository.sumNormalOverHeadByWorkEstimateId(workEstimateId);
	}

	/**
	 * Sum additional over head by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumAdditionalOverHeadByWorkEstimateId(Long workEstimateId) {
		log.debug("Request to get sumAdditionalOverHeadByWorkEstimateId : workEstimateId - {} -> id - {}"
				+ workEstimateId);
		return workEstimateOverheadRepository.sumAdditionalOverHeadByWorkEstimateId(workEstimateId);
	}

	/**
	 * Find all by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the list
	 */
	@Override
	public List<WorkEstimateOverheadDTO> findAllByWorkEstimateId(Long workEstimateId) {
		log.debug("Request to get findAllByWorkEstimateId : workEstimateId - {} -> id - {}" + workEstimateId);
		return workEstimateOverheadMapper.toDto(workEstimateOverheadRepository.findAllByWorkEstimateId(workEstimateId));
	}

	/**
	 * Find all by work estimate id and over head type.
	 *
	 * @param workEstimateId the work estimate id
	 * @param overHeadType   the over head type
	 * @return the list
	 */
	@Override
	public List<WorkEstimateOverheadDTO> findAllByWorkEstimateIdAndOverHeadType(Long workEstimateId,
			OverHeadType overHeadType) {
		return workEstimateOverheadMapper.toDto(
				workEstimateOverheadRepository.findAllByWorkEstimateIdAndOverHeadType(workEstimateId, overHeadType));
	}

	/**
	 * Calculate work estimate normal overhead totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal calculateWorkEstimateNormalOverheadTotals(Long workEstimateId) {
		BigDecimal normalOverheadTotal = BigDecimal.ZERO;
		Optional<WorkEstimateDTO> workEstimateDTOList = workEstimateService.findOne(workEstimateId);
		if (workEstimateDTOList.isPresent()) {
			WorkEstimateDTO workEstimateDTO = workEstimateDTOList.get();
			// Normal Overhead
			List<WorkEstimateOverheadDTO> workEstimateNormalOverheadList = findAllByWorkEstimateIdAndOverHeadType(
					workEstimateId, OverHeadType.NORMAL);

			for (WorkEstimateOverheadDTO workEstimateOverheadDTO : workEstimateNormalOverheadList) {
				if (workEstimateOverheadDTO.getFixedYn().equals(true)) {
					normalOverheadTotal = normalOverheadTotal.add(workEstimateOverheadDTO.getOverHeadValue());
				} else if (workEstimateOverheadDTO.getFixedYn().equals(false)) {
					normalOverheadTotal = normalOverheadTotal.add(workEstimateDTO.getLineEstimateTotal()
							.multiply(workEstimateOverheadDTO.getOverHeadValue().divide(BigDecimal.valueOf(100))));
				}
			}
			;
			WorkEstimateDTO workEstimatePartialDTO = new WorkEstimateDTO();
			workEstimatePartialDTO.setId(workEstimateId);
			workEstimatePartialDTO.setNormalOveheadTotal(normalOverheadTotal);
			workEstimateService.partialUpdate(workEstimatePartialDTO);
		}

		return normalOverheadTotal;
	}

	/**
	 * Calculate work estimate additional overhead totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal calculateWorkEstimateAdditionalOverheadTotals(Long workEstimateId) {
		// Additional Overhead
		List<WorkEstimateOverheadDTO> workEstimateAdditionalOverheadList = findAllByWorkEstimateIdAndOverHeadType(
				workEstimateId, OverHeadType.ADDITIONAL);
		BigDecimal additionalOverheadTotal = BigDecimal.ZERO;
		BigDecimal totalWithoutAddlOverhead = getTotalWithoutAddlOverhead(workEstimateId);

		for (WorkEstimateOverheadDTO workEstimateOverheadDTO : workEstimateAdditionalOverheadList) {
			if (workEstimateOverheadDTO.getFixedYn().equals(true)) {
				additionalOverheadTotal = additionalOverheadTotal.add(workEstimateOverheadDTO.getOverHeadValue());
			} else if (workEstimateOverheadDTO.getFixedYn().equals(false)) {
				additionalOverheadTotal = additionalOverheadTotal.add(totalWithoutAddlOverhead
						.multiply(workEstimateOverheadDTO.getOverHeadValue().divide(BigDecimal.valueOf(100))));
			}
		}
		;
		WorkEstimateDTO workEstimatePartialDTO = new WorkEstimateDTO();
		workEstimatePartialDTO.setId(workEstimateId);
		workEstimatePartialDTO.setAddlOveheadTotal(additionalOverheadTotal);
		workEstimateService.partialUpdate(workEstimatePartialDTO);
		return additionalOverheadTotal;
	}

	/**
	 * Gets the total without addl overhead.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the total without addl overhead
	 */
	@Override
	public BigDecimal getTotalWithoutAddlOverhead(Long workEstimateId) {
		BigDecimal totalWithoutAddlOverhead = BigDecimal.ZERO;

		Optional<WorkEstimateDTO> workEstimateDTOList = workEstimateService.findOne(workEstimateId);
		if (workEstimateDTOList.isPresent()) {
			WorkEstimateDTO workEstimateDTO = workEstimateDTOList.get();

			if (workEstimateDTO.getApprovedBudgetYn().equals(true)) {
				if (workEstimateDTO.getEstimateTotal() != null
						&& workEstimateDTO.getEstimateTotal().compareTo(BigDecimal.ZERO) == 1) {
					totalWithoutAddlOverhead = totalWithoutAddlOverhead.add(workEstimateDTO.getEstimateTotal());
				}
				if (workEstimateDTO.getGroupLumpsumTotal() != null
						&& workEstimateDTO.getGroupLumpsumTotal().compareTo(BigDecimal.ZERO) == 1) {
					totalWithoutAddlOverhead = totalWithoutAddlOverhead.add(workEstimateDTO.getGroupLumpsumTotal());
				}
			} else {
				if (workEstimateDTO.getLineEstimateTotal() != null
						&& workEstimateDTO.getLineEstimateTotal().compareTo(BigDecimal.ZERO) == 1) {
					totalWithoutAddlOverhead = totalWithoutAddlOverhead.add(workEstimateDTO.getLineEstimateTotal());
				}
			}

			if (workEstimateDTO.getNormalOveheadTotal() != null
					&& workEstimateDTO.getNormalOveheadTotal().compareTo(BigDecimal.ZERO) == 1) {
				totalWithoutAddlOverhead = totalWithoutAddlOverhead.add(workEstimateDTO.getNormalOveheadTotal());
			}
		}
		return totalWithoutAddlOverhead;
	}
}
