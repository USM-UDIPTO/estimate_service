package com.dxc.eproc.estimate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.model.WorkSubEstimateGroup;
import com.dxc.eproc.estimate.repository.WorkSubEstimateGroupRepository;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupLumpsumService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupOverheadService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupDTO;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;
import com.dxc.eproc.estimate.service.mapper.WorkSubEstimateGroupMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkSubEstimateGroup}.
 */
@Service
@Transactional
public class WorkSubEstimateGroupServiceImpl implements WorkSubEstimateGroupService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkSubEstimateGroupServiceImpl.class);

	/** The work sub estimate group repository. */
	@Autowired
	private WorkSubEstimateGroupRepository workSubEstimateGroupRepository;

	/** The work sub estimate group mapper. */
	@Autowired
	private WorkSubEstimateGroupMapper workSubEstimateGroupMapper;

	/** The work sub estimate group lumpsum service. */
	@Autowired
	private WorkSubEstimateGroupLumpsumService workSubEstimateGroupLumpsumService;

	/** The work estimate service. */
	@Autowired
	private WorkEstimateService workEstimateService;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The work sub estimate group overhead service. */
	@Autowired
	private WorkSubEstimateGroupOverheadService workSubEstimateGroupOverheadService;

	/**
	 * Save.
	 *
	 * @param workSubEstimateGroupDTO the work sub estimate group DTO
	 * @return the work sub estimate group DTO
	 */
	@Override
	public WorkSubEstimateGroupDTO save(WorkSubEstimateGroupDTO workSubEstimateGroupDTO) {
		log.debug("Request to save WorkSubEstimateGroup : {}", workSubEstimateGroupDTO);
		WorkSubEstimateGroup workSubEstimateGroup = workSubEstimateGroupMapper.toEntity(workSubEstimateGroupDTO);
		workSubEstimateGroup = workSubEstimateGroupRepository.save(workSubEstimateGroup);
		return workSubEstimateGroupMapper.toDto(workSubEstimateGroup);
	}

	/**
	 * Partial update.
	 *
	 * @param workSubEstimateGroupDTO the work sub estimate group DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkSubEstimateGroupDTO> partialUpdate(WorkSubEstimateGroupDTO workSubEstimateGroupDTO) {
		log.debug("Request to partially update WorkSubEstimateGroup : {}", workSubEstimateGroupDTO);

		return workSubEstimateGroupRepository.findById(workSubEstimateGroupDTO.getId())
				.map(existingWorkSubEstimateGroup -> {
					workSubEstimateGroupMapper.partialUpdate(existingWorkSubEstimateGroup, workSubEstimateGroupDTO);
					return existingWorkSubEstimateGroup;
				}).map(workSubEstimateGroupRepository::save).map(workSubEstimateGroupMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkSubEstimateGroupDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkSubEstimateGroups");
		return workSubEstimateGroupRepository.findAll(pageable).map(workSubEstimateGroupMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkSubEstimateGroupDTO> findOne(Long id) {
		log.debug("Request to get WorkSubEstimateGroup : {}", id);
		return workSubEstimateGroupRepository.findById(id).map(workSubEstimateGroupMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkSubEstimateGroup : {}", id);
		workSubEstimateGroupRepository.deleteById(id);
	}

	/**
	 * Sum overhead total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumOverheadTotalByWorkEstimateId(Long workEstimateId) {
		log.debug("Request to get sumOverheadTotalByWorkEstimateId : {}", workEstimateId);
		return workSubEstimateGroupRepository.sumOverheadTotalByWorkEstimateId(workEstimateId);
	}

	/**
	 * Find by work estimate id and id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param Id             the id
	 * @return the optional
	 */
	@Override
	public Optional<WorkSubEstimateGroupDTO> findByWorkEstimateIdAndId(Long workEstimateId, Long Id) {
		log.debug("Request to get findByWorkEstimateIdAndId : {}", workEstimateId, Id);
		return workSubEstimateGroupRepository.findByWorkEstimateIdAndId(workEstimateId, Id)
				.map(workSubEstimateGroupMapper::toDto);
	}

	/**
	 * Find by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @param pageable       the pageable
	 * @return the page
	 */
	@Override
	public Page<WorkSubEstimateGroupDTO> findByWorkEstimateId(Long workEstimateId, Pageable pageable) {
		log.debug("Request to get findByWorkEstimateId : {}", workEstimateId);
		return workSubEstimateGroupRepository.findByWorkEstimateId(workEstimateId, pageable)
				.map(workSubEstimateGroupMapper::toDto);
	}

	/**
	 * Sum lumpsum total by work estimate id.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal sumLumpsumTotalByWorkEstimateId(Long workEstimateId) {
		log.debug("Request to get findByWorkEstimateId : {}", workEstimateId);
		return workSubEstimateGroupRepository.sumLumpsumTotalByWorkEstimateId(workEstimateId);
	}

	/**
	 * Recalculate and update WRT lumpsum.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 */
	public void recalculateAndUpdateWRTLumpsum(Long workEstimateId, Long groupId) {
		BigDecimal totalLumpsumValue = workSubEstimateGroupLumpsumService.sumApproxRateByGroupId(groupId);
		if (totalLumpsumValue == null) {
			totalLumpsumValue = BigDecimal.ZERO;
		}
		WorkSubEstimateGroupDTO workSubEstimateGroupDTO = new WorkSubEstimateGroupDTO();
		workSubEstimateGroupDTO.setId(groupId);
		workSubEstimateGroupDTO.setLumpsumTotal(totalLumpsumValue);
		partialUpdate(workSubEstimateGroupDTO);

		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setId(workEstimateId);
		BigDecimal newLumpsumTotal = sumLumpsumTotalByWorkEstimateId(workEstimateId);
		if (newLumpsumTotal == null) {
			newLumpsumTotal = BigDecimal.ZERO;
		}
		workEstimateDTO.setGroupLumpsumTotal(newLumpsumTotal);
		workEstimateService.partialUpdate(workEstimateDTO);
	}

	/**
	 * Sets the sub estimate details.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupDTO       the group DTO
	 */
	public void setSubEstimateDetailsInGroup(Long workEstimateId, WorkSubEstimateGroupDTO groupDTO) {
		List<SubEstimateDTO> subEstimates = subEstimateService
				.findByWorkEstimateIdAndWorkSubEstimateGroupId(workEstimateId, groupDTO.getId());
		groupDTO.setSubEstimates(subEstimates);
		List<Long> subEstimateIds = subEstimates.stream().map(s -> {
			return s.getId();
		}).collect(Collectors.toList());
		groupDTO.setSubEstimateIds(subEstimateIds);
	}

	/**
	 * Recalculate group WRT sub estimate.
	 *
	 * @param workEstimateId the work estimate id
	 */
	public void recalculateGroupWRTSubEstimate(Long workEstimateId) {
		Page<WorkSubEstimateGroupDTO> groupPage = findByWorkEstimateId(workEstimateId, null);
		if (!groupPage.isEmpty()) {
			groupPage.forEach(finalGroupDTO -> {
				List<SubEstimateDTO> subEstimateDTOList = subEstimateService
						.findByWorkEstimateIdAndWorkSubEstimateGroupId(finalGroupDTO.getWorkEstimateId(),
								finalGroupDTO.getId());
				if (CollectionUtils.isEmpty(subEstimateDTOList)) {
					deleteGroup(finalGroupDTO);
				} else {
					List<Long> subEstimateIds = subEstimateDTOList.stream().map(s -> {
						return s.getId();
					}).collect(Collectors.toList());
					recalculateGroup(subEstimateIds, finalGroupDTO);
				}
			});
		}
	}

	/**
	 * Recalculate group.
	 *
	 * @param subEstimateIds the sub estimate ids
	 * @param finalGroupDTO  the final group DTO
	 */
	public void recalculateGroup(List<Long> subEstimateIds, WorkSubEstimateGroupDTO finalGroupDTO) {
		BigDecimal sum = subEstimateService.sumEstimateTotalByWorkEstimateIdAndIds(finalGroupDTO.getWorkEstimateId(),
				new ArrayList<>(subEstimateIds));
		// update Base Estimate overhead
		List<WorkSubEstimateGroupOverheadDTO> overheadList = workSubEstimateGroupOverheadService
				.findByWorkSubEstimateGroupIdAndValueType(finalGroupDTO.getId(), ValueType.OTHERS);
		if (!overheadList.isEmpty()) {
			WorkSubEstimateGroupOverheadDTO overheadDTO = new WorkSubEstimateGroupOverheadDTO();
			overheadDTO.setId(overheadList.get(0).getId());
			overheadDTO.setOverheadValue(sum);
			workSubEstimateGroupOverheadService.partialUpdate(overheadDTO);
			// update added overheads if present
			overheadList = workSubEstimateGroupOverheadService
					.findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode(finalGroupDTO.getId(),
							ValueType.ADDED);
			if (!CollectionUtils.isEmpty(overheadList)) {
				overheadList.forEach(o -> {
					List<String> codeList = Arrays
							.asList(o.getConstruct().substring(6, o.getConstruct().length() - 1).split("\\+"));
					BigDecimal overheadSum = workSubEstimateGroupOverheadService
							.sumOverheadValueByGroupIdAndCodeIn(finalGroupDTO.getId(), codeList);
					BigDecimal total = (o.getEnteredValue().multiply(overheadSum)).divide(new BigDecimal(100));
					total = total.setScale(4, RoundingMode.HALF_EVEN);
					WorkSubEstimateGroupOverheadDTO addedOverheadDTO = new WorkSubEstimateGroupOverheadDTO();
					addedOverheadDTO.setId(o.getId());
					addedOverheadDTO.setOverheadValue(total);
					workSubEstimateGroupOverheadService.partialUpdate(addedOverheadDTO);
				});
			}
			workSubEstimateGroupOverheadService.recalculateAndUpdateWRTOverhead(finalGroupDTO.getWorkEstimateId(),
					finalGroupDTO.getId());
		}
	}

	/**
	 * Delete group.
	 *
	 * @param groupDTO the group DTO
	 */
	public void deleteGroup(WorkSubEstimateGroupDTO groupDTO) {
		List<SubEstimateDTO> subEstimateDTOList = subEstimateService
				.findByWorkEstimateIdAndWorkSubEstimateGroupId(groupDTO.getWorkEstimateId(), groupDTO.getId());

		if (!CollectionUtils.isEmpty(subEstimateDTOList)) {
			subEstimateDTOList.forEach(s -> {
				subEstimateService.clearGroupId(s.getId());
			});
		}

		workSubEstimateGroupOverheadService.findByWorkSubEstimateGroupId(groupDTO.getId(), null).forEach(o -> {
			workSubEstimateGroupOverheadService.delete(o.getId());
		});

		workSubEstimateGroupLumpsumService.findByWorkSubEstimateGroupId(groupDTO.getId(), null).forEach(l -> {
			workSubEstimateGroupLumpsumService.delete(l.getId());
		});

		WorkEstimateDTO dbWorkEstimateDTO = workEstimateService.findOne(groupDTO.getWorkEstimateId()).get();

		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setId(groupDTO.getWorkEstimateId());
		if (dbWorkEstimateDTO.getGroupOverheadTotal() != null) {
			workEstimateDTO.setGroupOverheadTotal(
					dbWorkEstimateDTO.getGroupOverheadTotal().subtract(groupDTO.getOverheadTotal()));
		}
		if (dbWorkEstimateDTO.getGroupLumpsumTotal() != null) {
			workEstimateDTO.setGroupLumpsumTotal(
					dbWorkEstimateDTO.getGroupLumpsumTotal().subtract(groupDTO.getLumpsumTotal()));
		}
		workEstimateService.partialUpdate(workEstimateDTO);

		delete(groupDTO.getId());
	}
}
