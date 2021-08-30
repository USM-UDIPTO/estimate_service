package com.dxc.eproc.estimate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead;
import com.dxc.eproc.estimate.repository.WorkSubEstimateGroupOverheadRepository;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupOverheadService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import com.dxc.eproc.estimate.service.mapper.WorkSubEstimateGroupOverheadMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkSubEstimateGroupOverhead}.
 */
@Service
@Transactional
public class WorkSubEstimateGroupOverheadServiceImpl implements WorkSubEstimateGroupOverheadService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupOverheadServiceImpl.class);

	/** The work sub estimate group overhead repository. */
	@Autowired
	private WorkSubEstimateGroupOverheadRepository workSubEstimateGroupOverheadRepository;

	/** The work sub estimate group overhead mapper. */
	@Autowired
	private WorkSubEstimateGroupOverheadMapper workSubEstimateGroupOverheadMapper;

	/** The work sub estimate group service. */
	@Autowired
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/**
	 * Save.
	 *
	 * @param workSubEstimateGroupOverheadDTO the work sub estimate group overhead
	 *                                        DTO
	 * @return the work sub estimate group overhead DTO
	 */
	@Override
	public WorkSubEstimateGroupOverheadDTO save(WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO) {
		log.debug("Request to save WorkSubEstimateGroupOverhead : {}", workSubEstimateGroupOverheadDTO);
		WorkSubEstimateGroupOverhead workSubEstimateGroupOverhead = workSubEstimateGroupOverheadMapper
				.toEntity(workSubEstimateGroupOverheadDTO);
		workSubEstimateGroupOverhead = workSubEstimateGroupOverheadRepository.save(workSubEstimateGroupOverhead);
		return workSubEstimateGroupOverheadMapper.toDto(workSubEstimateGroupOverhead);
	}

	/**
	 * Partial update.
	 *
	 * @param workSubEstimateGroupOverheadDTO the work sub estimate group overhead
	 *                                        DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkSubEstimateGroupOverheadDTO> partialUpdate(
			WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO) {
		log.debug("Request to partially update WorkSubEstimateGroupOverhead : {}", workSubEstimateGroupOverheadDTO);

		return workSubEstimateGroupOverheadRepository.findById(workSubEstimateGroupOverheadDTO.getId())
				.map(existingWorkSubEstimateGroupOverhead -> {
					workSubEstimateGroupOverheadMapper.partialUpdate(existingWorkSubEstimateGroupOverhead,
							workSubEstimateGroupOverheadDTO);
					return existingWorkSubEstimateGroupOverhead;
				}).map(workSubEstimateGroupOverheadRepository::save).map(workSubEstimateGroupOverheadMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkSubEstimateGroupOverheadDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkSubEstimateGroupOverheads");
		return workSubEstimateGroupOverheadRepository.findAll(pageable).map(workSubEstimateGroupOverheadMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkSubEstimateGroupOverheadDTO> findOne(Long id) {
		log.debug("Request to get WorkSubEstimateGroupOverhead : {}", id);
		return workSubEstimateGroupOverheadRepository.findById(id).map(workSubEstimateGroupOverheadMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkSubEstimateGroupOverhead : {}", id);
		workSubEstimateGroupOverheadRepository.deleteById(id);
	}

	/**
	 * Find by work sub estimate group id and id in.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param selectedOverheadIds    the selected overhead ids
	 * @return the list
	 */
	@Override
	public List<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndIdIn(Long workSubEstimateGroupId,
			List<Long> selectedOverheadIds) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndIdIn : {}", workSubEstimateGroupId,
				selectedOverheadIds);
		return workSubEstimateGroupOverheadMapper.toDto(workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndIdIn(workSubEstimateGroupId, selectedOverheadIds));
	}

	/**
	 * Sum overhead value by group id and id in.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param selectedOverheadIds    the selected overhead ids
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumOverheadValueByGroupIdAndIdIn(Long workSubEstimateGroupId, List<Long> selectedOverheadIds) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndIdIn : {}", workSubEstimateGroupId,
				selectedOverheadIds);
		return workSubEstimateGroupOverheadRepository.sumOverheadValueByGroupIdAndIdIn(workSubEstimateGroupId,
				selectedOverheadIds);
	}

	/**
	 * Count by work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the int
	 */
	@Override
	public int countByWorkSubEstimateGroupId(Long workSubEstimateGroupId) {
		log.debug("Request to get countByWorkSubEstimateGroupId : {}", workSubEstimateGroupId);
		return workSubEstimateGroupOverheadRepository.countByWorkSubEstimateGroupId(workSubEstimateGroupId);
	}

	/**
	 * Sum added overhead value by group id and final yn true.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumAddedOverheadValueByGroupIdAndFinalYnTrue(Long workSubEstimateGroupId) {
		log.debug("Request to get sumAddedOverheadValueByGroupIdAndFinalYnTrue : {}", workSubEstimateGroupId);
		return workSubEstimateGroupOverheadRepository
				.sumAddedOverheadValueByGroupIdAndFinalYnTrue(workSubEstimateGroupId);
	}

	/**
	 * Sum other overhead value by group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumOtherOverheadValueByGroupId(Long workSubEstimateGroupId) {
		log.debug("Request to get sumOtherOverheadValueByGroupId : {}", workSubEstimateGroupId);
		return workSubEstimateGroupOverheadRepository.sumOtherOverheadValueByGroupId(workSubEstimateGroupId);
	}

	/**
	 * Find by work sub estimate group id and id and value type.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @param valueType              the value type
	 * @return the optional
	 */
	@Override
	public Optional<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndIdAndValueType(
			Long workSubEstimateGroupId, Long id, ValueType valueType) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndIdAndValueType : {}", workSubEstimateGroupId, id,
				valueType);
		return workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndIdAndValueType(workSubEstimateGroupId, id, valueType)
				.map(workSubEstimateGroupOverheadMapper::toDto);
	}

	/**
	 * Find by work sub estimate group id and value type and value fixed yn false.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	@Override
	public List<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(
			Long workSubEstimateGroupId, ValueType valueType) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse : {}",
				workSubEstimateGroupId, valueType);
		return workSubEstimateGroupOverheadMapper.toDto(workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(workSubEstimateGroupId, valueType));
	}

	/**
	 * Find by work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param pageable               the pageable
	 * @return the page
	 */
	@Override
	public Page<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupId(Long workSubEstimateGroupId,
			Pageable pageable) {
		log.debug("Request to get findByWorkSubEstimateGroupId : {}", workSubEstimateGroupId);
		return workSubEstimateGroupOverheadRepository.findByWorkSubEstimateGroupId(workSubEstimateGroupId, pageable)
				.map(workSubEstimateGroupOverheadMapper::toDto);
	}

	/**
	 * Find by work sub estimate group id and id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @return the optional
	 */
	@Override
	public Optional<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndId(Long workSubEstimateGroupId,
			Long id) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndId : {}", workSubEstimateGroupId, id);
		return workSubEstimateGroupOverheadRepository.findByWorkSubEstimateGroupIdAndId(workSubEstimateGroupId, id)
				.map(workSubEstimateGroupOverheadMapper::toDto);
	}

	/**
	 * Find by work sub estimate group id and value type.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	@Override
	public List<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndValueType(Long workSubEstimateGroupId,
			ValueType valueType) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndValueType : {} {}", workSubEstimateGroupId, valueType);
		return workSubEstimateGroupOverheadMapper.toDto(workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndValueType(workSubEstimateGroupId, valueType));
	}

	/**
	 * Find by work sub estimate group id and value fixed yn false and value type
	 * order by code.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	@Override
	public List<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode(
			Long workSubEstimateGroupId, ValueType valueType) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode : {} {}",
				workSubEstimateGroupId, valueType);
		return workSubEstimateGroupOverheadMapper.toDto(workSubEstimateGroupOverheadRepository
				.findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode(workSubEstimateGroupId,
						valueType));
	}

	/**
	 * Sum overhead value by group id and code in.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param codeList               the code list
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumOverheadValueByGroupIdAndCodeIn(Long workSubEstimateGroupId, List<String> codeList) {
		log.debug("Request to get findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode : {} {}",
				workSubEstimateGroupId, codeList);
		return workSubEstimateGroupOverheadRepository.sumOverheadValueByGroupIdAndCodeIn(workSubEstimateGroupId,
				codeList);
	}

	/**
	 * Calculate percentage overhead.
	 *
	 * @param workSubEstimateGroupOverheadDTO the work sub estimate group overhead
	 *                                        DTO
	 * @param overheadDTOList                 the overhead DTO list
	 * @param groupId                         the group id
	 * @throws Exception the exception
	 */
	public void calculatePercentageOverhead(WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO,
			List<WorkSubEstimateGroupOverheadDTO> overheadDTOList, Long groupId) throws Exception {
		BigDecimal sum = sumOverheadValueByGroupIdAndIdIn(groupId,
				workSubEstimateGroupOverheadDTO.getSelectedOverheads());
		BigDecimal total = (workSubEstimateGroupOverheadDTO.getEnteredValue().multiply(sum))
				.divide(new BigDecimal(100));
		total = total.setScale(4, RoundingMode.HALF_EVEN);
		workSubEstimateGroupOverheadDTO.setOverheadValue(total);
		// construct
		String construct = overheadDTOList.stream().map(o -> {
			String codes = "";
			codes = o.getCode() + "+";
			return codes;
		}).reduce("", String::concat);
		construct = "% of (" + construct.substring(0, construct.length() - 1) + ")";
		workSubEstimateGroupOverheadDTO.setConstruct(construct);
	}

	/**
	 * Recalculate and update WRT overhead.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 */
	public void recalculateAndUpdateWRTOverhead(Long workEstimateId, Long groupId) {
		BigDecimal totalOverheadsValue = sumAddedOverheadValueByGroupIdAndFinalYnTrue(groupId);
		if (totalOverheadsValue == null) {
			totalOverheadsValue = BigDecimal.ZERO;
		}
		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setId(groupId);
		workSubEstimateGroupDTO.setOverheadTotal(totalOverheadsValue);
		workSubEstimateGroupService.partialUpdate(workSubEstimateGroupDTO);

		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setId(workEstimateId);
		BigDecimal newGroupOverheadTotal = workSubEstimateGroupService.sumOverheadTotalByWorkEstimateId(workEstimateId);
		if (newGroupOverheadTotal == null) {
			newGroupOverheadTotal = BigDecimal.ZERO;
		}
		workEstimateDTO.setGroupOverheadTotal(newGroupOverheadTotal);
		workEstimateService.partialUpdate(workEstimateDTO);
	}
}
