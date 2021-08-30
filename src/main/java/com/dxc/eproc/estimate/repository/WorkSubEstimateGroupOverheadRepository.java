package com.dxc.eproc.estimate.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.estimate.enumeration.ValueType;
import com.dxc.eproc.estimate.model.WorkSubEstimateGroupOverhead;

// TODO: Auto-generated Javadoc
/**
 * Spring Data SQL repository for the WorkSubEstimateGroupOverhead entity.
 */
@Repository
public interface WorkSubEstimateGroupOverheadRepository extends JpaRepository<WorkSubEstimateGroupOverhead, Long> {

	/**
	 * Find by work sub estimate group id and id in.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param selectedOverheads      the selected overheads
	 * @return the list
	 */
	List<WorkSubEstimateGroupOverhead> findByWorkSubEstimateGroupIdAndIdIn(Long workSubEstimateGroupId,
			List<Long> selectedOverheads);

	/**
	 * Sum overhead value by group id and id in.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param Id                     the id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(w.overheadValue) FROM WorkSubEstimateGroupOverhead w WHERE w.workSubEstimateGroupId =:workSubEstimateGroupId AND w.id IN :id")
	BigDecimal sumOverheadValueByGroupIdAndIdIn(@Param("workSubEstimateGroupId") Long workSubEstimateGroupId,
			@Param("id") List<Long> Id);

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
	@Query("SELECT SUM(w.overheadValue) FROM WorkSubEstimateGroupOverhead w WHERE w.workSubEstimateGroupId =:workSubEstimateGroupId AND w.finalYn = true")
	BigDecimal sumAddedOverheadValueByGroupIdAndFinalYnTrue(
			@Param("workSubEstimateGroupId") Long workSubEstimateGroupId);

	/**
	 * Sum other overhead value by group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @return the big decimal
	 */
	@Query("SELECT SUM(w.overheadValue) FROM WorkSubEstimateGroupOverhead w WHERE w.workSubEstimateGroupId =:workSubEstimateGroupId AND w.valueType = 'OTHERS'")
	BigDecimal sumOtherOverheadValueByGroupId(@Param("workSubEstimateGroupId") Long workSubEstimateGroupId);

	/**
	 * Find by work sub estimate group id and id and value type.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @param valueType              the value type
	 * @return the optional
	 */
	Optional<WorkSubEstimateGroupOverhead> findByWorkSubEstimateGroupIdAndIdAndValueType(Long workSubEstimateGroupId,
			Long id, ValueType valueType);

	/**
	 * Find by work sub estimate group id and value type and value fixed yn false.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	List<WorkSubEstimateGroupOverhead> findByWorkSubEstimateGroupIdAndValueTypeAndValueFixedYnFalse(
			Long workSubEstimateGroupId, ValueType valueType);

	/**
	 * Find by work sub estimate group id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param pageable               the pageable
	 * @return the page
	 */
	Page<WorkSubEstimateGroupOverhead> findByWorkSubEstimateGroupId(Long workSubEstimateGroupId, Pageable pageable);

	/**
	 * Find by work sub estimate group id and id.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param id                     the id
	 * @return the optional
	 */
	Optional<WorkSubEstimateGroupOverhead> findByWorkSubEstimateGroupIdAndId(Long workSubEstimateGroupId, Long id);

	/**
	 * Find by work sub estimate group id and value type.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	List<WorkSubEstimateGroupOverhead> findByWorkSubEstimateGroupIdAndValueType(Long workSubEstimateGroupId,
			ValueType valueType);

	/**
	 * Find by work sub estimate group id and value fixed yn false and value type
	 * order by code.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param valueType              the value type
	 * @return the list
	 */
	List<WorkSubEstimateGroupOverhead> findByWorkSubEstimateGroupIdAndValueFixedYnFalseAndValueTypeOrderByCode(
			Long workSubEstimateGroupId, ValueType valueType);

	/**
	 * Sum overhead value by group id and code in.
	 *
	 * @param workSubEstimateGroupId the work sub estimate group id
	 * @param codes                  the codes
	 * @return the big decimal
	 */
	@Query("SELECT SUM(w.overheadValue) FROM WorkSubEstimateGroupOverhead w WHERE w.workSubEstimateGroupId =:workSubEstimateGroupId AND w.code IN :codes")
	BigDecimal sumOverheadValueByGroupIdAndCodeIn(@Param("workSubEstimateGroupId") Long workSubEstimateGroupId,
			@Param("codes") List<String> codes);
}
