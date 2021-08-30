package com.dxc.eproc.estimate.service.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.enumeration.LBDOperation;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;
import com.dxc.eproc.estimate.repository.WorkEstimateItemLBDRepository;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateItemLBDService;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemLBDDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateItemLBDMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkEstimateItemLBD}.
 */
@Service
@Transactional
public class WorkEstimateItemLBDServiceImpl implements WorkEstimateItemLBDService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateItemLBDServiceImpl.class);

	/** The work estimate item LBD repository. */
	private final WorkEstimateItemLBDRepository workEstimateItemLBDRepository;

	/** The work estimate item LBD mapper. */
	private final WorkEstimateItemLBDMapper workEstimateItemLBDMapper;

	/** The work estimate item service. */
	@Autowired
	private WorkEstimateItemService workEstimateItemService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/**
	 * Instantiates a new work estimate item LBD service impl.
	 *
	 * @param workEstimateItemLBDRepository the work estimate item LBD repository
	 * @param workEstimateItemLBDMapper     the work estimate item LBD mapper
	 * @param workEstimateItemService       the work estimate item service
	 * @param workEstimateService           the work estimate service
	 * @param subEstimateService            the sub estimate service
	 */
	public WorkEstimateItemLBDServiceImpl(WorkEstimateItemLBDRepository workEstimateItemLBDRepository,
			WorkEstimateItemLBDMapper workEstimateItemLBDMapper, WorkEstimateItemService workEstimateItemService,
			WorkEstimateService workEstimateService, SubEstimateService subEstimateService) {
		this.workEstimateItemLBDRepository = workEstimateItemLBDRepository;
		this.workEstimateItemLBDMapper = workEstimateItemLBDMapper;
		this.workEstimateItemService = workEstimateItemService;
		this.subEstimateService = subEstimateService;
	}

	/**
	 * Save.
	 *
	 * @param workEstimateItemLBDDTO the work estimate item LBDDTO
	 * @return the work estimate item LBDDTO
	 */
	@Override
	public WorkEstimateItemLBDDTO save(WorkEstimateItemLBDDTO workEstimateItemLBDDTO) {
		log.debug("Request to save WorkEstimateItemLBD : {}", workEstimateItemLBDDTO);
		WorkEstimateItemLBD workEstimateItemLBD = workEstimateItemLBDMapper.toEntity(workEstimateItemLBDDTO);
		if (workEstimateItemLBD.getId() != null) {
			WorkEstimateItemLBD dbLBD = workEstimateItemLBDRepository.findById(workEstimateItemLBD.getId()).get();
			BeanUtils.copyProperties(workEstimateItemLBD, dbLBD);
			workEstimateItemLBD = workEstimateItemLBDRepository.save(dbLBD);
		} else {
			workEstimateItemLBD = workEstimateItemLBDRepository.save(workEstimateItemLBD);
		}
		return workEstimateItemLBDMapper.toDto(workEstimateItemLBD);
	}

	/**
	 * Partial update.
	 *
	 * @param workEstimateItemLBDDTO the work estimate item LBDDTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateItemLBDDTO> partialUpdate(WorkEstimateItemLBDDTO workEstimateItemLBDDTO) {
		log.debug("Request to partially update WorkEstimateItemLBD : {}", workEstimateItemLBDDTO);

		return workEstimateItemLBDRepository.findById(workEstimateItemLBDDTO.getId())
				.map(existingWorkEstimateItemLBD -> {
					workEstimateItemLBDMapper.partialUpdate(existingWorkEstimateItemLBD, workEstimateItemLBDDTO);
					return existingWorkEstimateItemLBD;
				}).map(workEstimateItemLBDRepository::save).map(workEstimateItemLBDMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateItemLBDDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkEstimateItemLBDS");
		return workEstimateItemLBDRepository.findAll(pageable).map(workEstimateItemLBDMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateItemLBDDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateItemLBD : {}", id);
		return workEstimateItemLBDRepository.findById(id).map(workEstimateItemLBDMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateItemLBD : {}", id);
		workEstimateItemLBDRepository.deleteById(id);
	}

	/**
	 * Find all by work estimate item id.
	 *
	 * @param sourceItemId the source item id
	 * @return the list
	 */
	@Override
	public List<WorkEstimateItemLBDDTO> findAllByWorkEstimateItemId(Long sourceItemId) {
		log.debug("Request to get findAllByWorkEstimate : {}", sourceItemId);

		return workEstimateItemLBDRepository.findAllByWorkEstimateItemId(sourceItemId).stream()
				.map(workEstimateItemLBDMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Sum by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param additionDeduction  the addition deduction
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumByWorkEstimateItemId(Long workEstimateItemId, LBDOperation additionDeduction) {
		log.debug("Request to get sumByWorkEstimateItemId : {}", workEstimateItemId, additionDeduction);
		return workEstimateItemLBDRepository.sumByWorkEstimateItemId(workEstimateItemId, additionDeduction);
	}

	/**
	 * Find by work estimate item id and id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param id                 the id
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateItemLBDDTO> findByWorkEstimateItemIdAndId(Long workEstimateItemId, Long id) {
		log.debug("Request to get findByWorkEstimateItemIdAndId : {}", workEstimateItemId, id);
		return workEstimateItemLBDRepository.findByWorkEstimateItemIdAndId(workEstimateItemId, id)
				.map(workEstimateItemLBDMapper::toDto);
	}

	/**
	 * Find by work estimate item id.
	 *
	 * @param workEstimateItemId the work estimate item id
	 * @param pageable           the pageable
	 * @return the page
	 */
	@Override
	public Page<WorkEstimateItemLBDDTO> findByWorkEstimateItemId(Long workEstimateItemId, Pageable pageable) {

		log.debug("Request to get findByWorkEstimateItemId : {}", workEstimateItemId, pageable);
		return workEstimateItemLBDRepository.findByWorkEstimateItemId(workEstimateItemId, pageable)
				.map(workEstimateItemLBDMapper::toDto);
	}

	/**
	 * Gets the LBD total.
	 *
	 * @param itemId the item id
	 * @return the LBD total
	 */
	@Override
	public BigDecimal getItemLBDsTotal(Long itemId) {
		BigDecimal totalAddition = sumByWorkEstimateItemId(itemId, LBDOperation.ADDITION);
		if (totalAddition == null) {
			totalAddition = BigDecimal.ZERO;
		}
		BigDecimal totalDeduction = sumByWorkEstimateItemId(itemId, LBDOperation.DEDUCTION);
		if (totalDeduction == null) {
			totalDeduction = BigDecimal.ZERO;
		}
		BigDecimal totalLBD = totalAddition.subtract(totalDeduction);
		return totalLBD;
	}

	/**
	 * Recalculate and update item WTHLBD.
	 *
	 * @param estimateId    the estimate id
	 * @param subEstimateId the sub estimate id
	 * @param itemId        the item id
	 */
	@Override
	public void recalculateAndUpdateItemWRTLBD(Long estimateId, Long subEstimateId, Long itemId) {
		WorkEstimateItemDTO workEstimateItemDTO = new WorkEstimateItemDTO();
		WorkEstimateItemDTO dbItemDTO = workEstimateItemService.findBySubEstimateIdAndId(subEstimateId, itemId).get();
		workEstimateItemDTO.setId(itemId);

		BigDecimal itemLBDsTotal = getItemLBDsTotal(itemId);

		if (findByWorkEstimateItemId(itemId, PageRequest.of(0, 5)).isEmpty()) {
			workEstimateItemDTO.setLbdPerformedYn(false);
		} else {
			workEstimateItemDTO.setLbdPerformedYn(true);
		}
		workEstimateItemDTO.setQuantity(itemLBDsTotal);
		if (dbItemDTO.getRaPerformedYn().equals(true)) {

			workEstimateItemDTO.setFinalRate(dbItemDTO.getBaseRate().multiply(workEstimateItemDTO.getQuantity()));
		}
		workEstimateItemService.partialUpdate(workEstimateItemDTO);
		subEstimateService.calculateSubEstimateTotals(estimateId, subEstimateId);
	}
}
