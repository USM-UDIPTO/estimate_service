package com.dxc.eproc.estimate.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.service.dto.WorkSubEstimateGroupOverheadDTO;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing
 * {@link com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead}.
 */
public interface WorkSubEstimateGroupOverheadService {
	/**
	 * Save a workSubEstimateGroupOverhead.
	 *
	 * @param workSubEstimateGroupOverheadDTO the entity to save.
	 * @return the persisted entity.
	 */
	WorkSubEstimateGroupOverheadDTO save(WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO);

	/**
	 * Partially updates a workSubEstimateGroupOverhead.
	 *
	 * @param workSubEstimateGroupOverheadDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	Optional<WorkSubEstimateGroupOverheadDTO> partialUpdate(
			WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO);

	/**
	 * Get all the workSubEstimateGroupOverheads.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<WorkSubEstimateGroupOverheadDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" workSubEstimateGroupOverhead.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<WorkSubEstimateGroupOverheadDTO> findOne(Long id);

	/**
	 * Delete the "id" workSubEstimateGroupOverhead.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Find by work sub estimate group id and id in.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param selectedOverheadIds    the selected overhead ids
	 * @return the list
	 */
	List<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndIdIn(Long workSubEstimateGroupId,
			List<Long> selectedOverheadIds);

	/**
	 * Sum overhead value by group id and id in.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param selectedOverheadIds    the selected overhead ids
	 * @return the big decimal
	 */
	BigDecimal sumOverheadValueByGroupIdAndIdIn(Long workSubEstimateGroupId, List<Long> selectedOverheadIds);

	/**
	 * Count by work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the int
	 */
	int countByWorkSubEstimateGroupId(Long workSubEstimateGroupId);

	/**
	 * Sum added overhead value by group id and final yn true.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the big decimal
	 */
	BigDecimal sumAddedOverheadValueByGroupIdAndFinalYnTrue(Long workSubEstimateGroupId);

	/**
	 * Sum other overhead value by group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the big decimal
	 */
	BigDecimal sumOtherOverheadValueByGroupId(Long workSubEstimateGroupId);

	/**
	 * Find by work sub estimate group id and id and value type.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @param valueType              the value type
	 * @return the optional
	 */
	Optional<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndIdAndValueType(Long workSubEstimateGroupId,
			Long id, ValueType valueType);

	/**
	 * Find by work sub estimate group id and value type and value fixed yn false.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	List<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(
			Long workSubEstimateGroupId, ValueType valueType);

	/**
	 * Find by work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param pageable               the pageable
	 * @return the page
	 */
	Page<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupId(Long workSubEstimateGroupId, Pageable pageable);

	/**
	 * Find by work sub estimate group id and id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @return the optional
	 */
	Optional<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndId(Long workSubEstimateGroupId, Long id);

	/**
	 * Find by work sub estimate group id and value type.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	List<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndValueType(Long workSubEstimateGroupId,
			ValueType valueType);

	/**
	 * Find by work sub estimate group id and value fixed yn false and value type
	 * order by code.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	List<WorkSubEstimateGroupOverheadDTO> findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode(
			Long workSubEstimateGroupId, ValueType valueType);

	/**
	 * Sum overhead value by group id and code in.
	 *
	 * @param id       the id
	 * @param codeList the code list
	 * @return the big decimal
	 */
	BigDecimal sumOverheadValueByGroupIdAndCodeIn(Long id, List<String> codeList);

	/**
	 * Calculate percentage overhead.
	 *
	 * @param workSubEstimateGroupOverheadDTO the work sub estimate group overhead
	 *                                        DTO
	 * @param overheadDTOList                 the overhead DTO list
	 * @param groupId                         the group id
	 * @throws Exception the exception
	 */
	void calculatePercentageOverhead(WorkSubEstimateGroupOverheadDTO workSubEstimateGroupOverheadDTO,
			List<WorkSubEstimateGroupOverheadDTO> overheadDTOList, Long groupId) throws Exception;

	/**
	 * Recalculate and update WRT overhead.
	 *
	 * @param workEstimateId the work estimate id
	 * @param groupId        the group id
	 */
	void recalculateAndUpdateWRTOverhead(Long workEstimateId, Long groupId);
}
