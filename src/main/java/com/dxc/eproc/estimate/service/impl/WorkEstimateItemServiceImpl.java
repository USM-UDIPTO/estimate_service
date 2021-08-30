package com.dxc.eproc.estimate.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.model.WorkEstimateItem;
import com.dxc.eproc.estimate.repository.WorkEstimateItemRepository;
import com.dxc.eproc.estimate.service.WorkEstimateItemService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateItemDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateItemMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkEstimateItem}.
 */
@Service
@Transactional
public class WorkEstimateItemServiceImpl implements WorkEstimateItemService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateItemServiceImpl.class);

	/** The work estimate item repository. */
	private final WorkEstimateItemRepository workEstimateItemRepository;

	/** The work estimate item mapper. */
	private final WorkEstimateItemMapper workEstimateItemMapper;

	/**
	 * Instantiates a new work estimate item service impl.
	 *
	 * @param workEstimateItemRepository the work estimate item repository
	 * @param workEstimateItemMapper     the work estimate item mapper
	 */
	public WorkEstimateItemServiceImpl(WorkEstimateItemRepository workEstimateItemRepository,
			WorkEstimateItemMapper workEstimateItemMapper) {
		this.workEstimateItemRepository = workEstimateItemRepository;
		this.workEstimateItemMapper = workEstimateItemMapper;
	}

	/**
	 * Save.
	 *
	 * @param workEstimateItemDTO the work estimate item DTO
	 * @return the work estimate item DTO
	 */
	@Override
	public WorkEstimateItemDTO save(WorkEstimateItemDTO workEstimateItemDTO) {
		WorkEstimateItem workEstimateItem = null;
		if (workEstimateItemDTO.getId() == null) {
			log.debug("Request to save WorkEstimateItem : {}", workEstimateItemDTO);
			workEstimateItem = workEstimateItemMapper.toEntity(workEstimateItemDTO);
		} else {
			log.debug("Request to update WorkEstimateItem - Id : {}", workEstimateItemDTO.getId());
			Optional<WorkEstimateItem> workEstimateItemOpt = workEstimateItemRepository
					.findById(workEstimateItemDTO.getId());
			if (workEstimateItemOpt.isPresent()) {
				workEstimateItem = workEstimateItemOpt.get();
				workEstimateItem.entryOrder(workEstimateItemDTO.getEntryOrder())
						.description(workEstimateItemDTO.getDescription());
			}
		}

		workEstimateItem = workEstimateItemRepository.save(workEstimateItem);
		return workEstimateItemMapper.toDto(workEstimateItem);
	}

	/**
	 * Partial update.
	 *
	 * @param workEstimateItemDTO the work estimate item DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateItemDTO> partialUpdate(WorkEstimateItemDTO workEstimateItemDTO) {
		log.debug("Request to partially update WorkEstimateItem : {}", workEstimateItemDTO);

		return workEstimateItemRepository.findById(workEstimateItemDTO.getId()).map(existingWorkEstimateItem -> {
			workEstimateItemMapper.partialUpdate(existingWorkEstimateItem, workEstimateItemDTO);
			return existingWorkEstimateItem;
		}).map(workEstimateItemRepository::save).map(workEstimateItemMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateItemDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkEstimateItems");
		return workEstimateItemRepository.findAll(pageable).map(workEstimateItemMapper::toDto);
	}

	/**
	 * Find all items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	@Override
	public List<WorkEstimateItemDTO> findAllItems(Long subEstimateId) {
		log.debug("Request to get all WorkEstimateItems By SubEstimateId");
		return workEstimateItemMapper
				.toDto(workEstimateItemRepository.findAllBySubEstimateIdOrderByEntryOrderAsc(subEstimateId));
	}

	/**
	 * Find all SOR items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<WorkEstimateItemDTO> findAllSORItems(Long subEstimateId) {
		log.debug("Request to get all SOR WorkEstimateItems By SubEstimateId");
		return workEstimateItemMapper.toDto(workEstimateItemRepository
				.findAllBySubEstimateIdAndCatWorkSorItemIdNotNullOrderByEntryOrderAsc(subEstimateId));
	}

	/**
	 * Find all SOR items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param pageable      the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateItemDTO> findAllSORItems(Long subEstimateId, Pageable pageable) {
		return workEstimateItemRepository
				.findAllBySubEstimateIdAndCatWorkSorItemIdNotNullOrderByEntryOrderAsc(subEstimateId, pageable)
				.map(workEstimateItemMapper::toDto);
	}

	/**
	 * Find all non SOR items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the list
	 */
	@Override
	public List<WorkEstimateItemDTO> findAllNonSORItems(Long subEstimateId) {
		log.debug("Request to get all Non SOR WorkEstimateItems By SubEstimateId");
		return workEstimateItemMapper.toDto(workEstimateItemRepository
				.findAllBySubEstimateIdAndCatWorkSorItemIdNullOrderByEntryOrderAsc(subEstimateId));
	}

	/**
	 * Find all non SOR items.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param pageable      the pageable
	 * @return the page
	 */
	@Override
	public Page<WorkEstimateItemDTO> findAllNonSORItems(Long subEstimateId, Pageable pageable) {
		log.debug("Request to get all Non SOR WorkEstimateItems By SubEstimateId and Pageable");
		return workEstimateItemRepository
				.findAllBySubEstimateIdAndCatWorkSorItemIdNullOrderByEntryOrderAsc(subEstimateId, pageable)
				.map(workEstimateItemMapper::toDto);

	}

	/**
	 * Find all SOR item by sub estimate id and id in.
	 *
	 * @param subEstimateId           the sub estimate id
	 * @param workEstimateItemWithIds the work estimate item with ids
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<WorkEstimateItemDTO> findAllSORItemBySubEstimateIdAndIdIn(Long subEstimateId,
			List<Long> workEstimateItemWithIds) {
		return workEstimateItemMapper.toDto(workEstimateItemRepository
				.findBySubEstimateIdAndCatWorkSorItemIdNotNullAndIdIn(subEstimateId, workEstimateItemWithIds));
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateItemDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimateItem : {}", id);
		return workEstimateItemRepository.findById(id).map(workEstimateItemMapper::toDto);
	}

	/**
	 * Find by sub estimate id and id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param id            the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateItemDTO> findBySubEstimateIdAndId(Long subEstimateId, Long id) {
		log.debug("Request to get WorkEstimateItem : subEstimateId - {} -> id - {}", subEstimateId, id);
		return workEstimateItemRepository.findBySubEstimateIdAndId(subEstimateId, id)
				.map(workEstimateItemMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete WorkEstimateItem : {}", id);
		workEstimateItemRepository.deleteById(id);
	}

	/**
	 * Find by sub estimate id and item code.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param itemCode      the item code
	 * @return the list
	 */
	@Override
	public List<WorkEstimateItemDTO> findBySubEstimateIdAndItemCode(Long subEstimateId, String itemCode) {
		log.debug("Request to get WorkEstimateItemCode : subEstimateId - {} -> id - {}", subEstimateId, itemCode);
		return workEstimateItemMapper
				.toDto(workEstimateItemRepository.findBySubEstimateIdAndItemCode(subEstimateId, itemCode));

	}

	/**
	 * Sum of work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	@Override
	@Transactional(readOnly = true)
	public BigDecimal sumOfWorkEstimateItemTotal(Long subEstimateId) {
		return workEstimateItemRepository.sumOfWorkEstimateItemTotal(subEstimateId);
	}

	/**
	 * Sum of SOR work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	@Override
	@Transactional(readOnly = true)
	public BigDecimal sumOfSORWorkEstimateItemTotal(Long subEstimateId) {
		return workEstimateItemRepository.sumOfSORWorkEstimateItemTotal(subEstimateId);
	}

	/**
	 * Sum of non SOR work estimate item total.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the big decimal
	 */
	@Override
	@Transactional(readOnly = true)
	public BigDecimal sumOfNonSORWorkEstimateItemTotal(Long subEstimateId) {
		return workEstimateItemRepository.sumOfNonSORWorkEstimateItemTotal(subEstimateId);
	}

	/**
	 * Find max SOR entry order by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the integer
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer findMaxSOREntryOrderBySubEstimateId(Long subEstimateId) {
		return workEstimateItemRepository.findMaxSOREntryOrderBySubEstimateId(subEstimateId);
	}

	/**
	 * Find max non SOR entry order by sub estimate id.
	 *
	 * @param subEstimateId the sub estimate id
	 * @return the integer
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer findMaxNonSOREntryOrderBySubEstimateId(Long subEstimateId) {
		return workEstimateItemRepository.findMaxNonSOREntryOrderBySubEstimateId(subEstimateId);
	}

	/**
	 * Find max floor by sub estimate id and category id.
	 *
	 * @param subEstimateId    the sub estimate id
	 * @param catWorkSorItemId the cat work sor item id
	 * @return the integer
	 */
	@Override
	@Transactional(readOnly = true)
	public Integer findMaxFloorBySubEstimateIdAndCatWorkSorItemId(Long subEstimateId, Long catWorkSorItemId) {
		log.debug(
				"Request to findMaxFloorBySubEstimateIdAndCatWorkSorItemId : subEstimateId - {} -> catWorkSorItemId - {}",
				subEstimateId, catWorkSorItemId);
		return workEstimateItemRepository.findMaxFloorBySubEstimateIdAndCatWorkSorItemId(subEstimateId,
				catWorkSorItemId);
	}

	/**
	 * Find all by sub estimate id and category id and cat work sor item id is not
	 * null.
	 *
	 * @param subEstimateId the sub estimate id
	 * @param categoryId    the category id
	 * @return the list
	 */
	@Override
	public List<WorkEstimateItemDTO> findAllBySubEstimateIdAndCategoryIdAndCatWorkSorItemIdIsNotNull(Long subEstimateId,
			Long categoryId) {
		log.debug("Request to findAllBySubEstimateIdAndCategoryIdAndCatWorkSorItemIdIsNotNull");
		return workEstimateItemMapper.toDto(workEstimateItemRepository
				.findAllBySubEstimateIdAndCategoryIdAndCatWorkSorItemIdIsNotNull(subEstimateId, categoryId));
	}

}
