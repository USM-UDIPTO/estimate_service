package com.dxc.eproc.estimate.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.estimate.enumeration.WorkEstimateSearch;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.QWorkEstimate;
import com.dxc.eproc.estimate.repository.CustomWorkEstimateSearch;
import com.dxc.eproc.estimate.response.dto.WorkEstimateSearchResponseDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateSearchDTO;
import com.dxc.eproc.utils.Utility;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateSearchRepositoryImpl.
 */
public class WorkEstimateSearchRepositoryImpl implements CustomWorkEstimateSearch {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateSearchRepositoryImpl.class);

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Search work estimate query DSL.
	 *
	 * @param pageable              the pageable
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @param createdBy             the created by
	 * @param workEstimateSearch    the work estimate search
	 * @return the page
	 */
	@Override
	public Page<WorkEstimateSearchResponseDTO> searchWorkEstimateQueryDSL(Pageable pageable,
			WorkEstimateSearchDTO workEstimateSearchDTO, String createdBy, WorkEstimateSearch workEstimateSearch) {
		List<WorkEstimateSearchResponseDTO> workEstimateSearchResponseDTOList = new ArrayList<>();

		long total = 0;
		QWorkEstimate qWorkEstimate = QWorkEstimate.workEstimate;
		JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);

		query.select(qWorkEstimate.id, qWorkEstimate.workEstimateNumber, qWorkEstimate.name, qWorkEstimate.status,
				qWorkEstimate.fileNumber, qWorkEstimate.createdTs).from(qWorkEstimate);

		query.where(qWorkEstimate.createdBy.eq(createdBy));

		if (WorkEstimateSearch.RECENT_ESTIMATES.equals(workEstimateSearch)) {
			List<WorkEstimateStatus> workEstimateStatusList = new ArrayList<>();
			workEstimateStatusList.add(WorkEstimateStatus.INITIAL);
			workEstimateStatusList.add(WorkEstimateStatus.INITIAL_DETAIL_ESTIMATE);
			workEstimateStatusList.add(WorkEstimateStatus.DRAFT);
			workEstimateStatusList.add(WorkEstimateStatus.DRAFT_DETAIL_ESTIMATE);
			query.where((qWorkEstimate.status.in(workEstimateStatusList)));
		} else if (WorkEstimateSearch.SEARCH_ESTIMATES.equals(workEstimateSearch)) {
			if (Utility.isValidCollection(workEstimateSearchDTO.getWorkEstimateStatusList())) {
				query.where((qWorkEstimate.status.in(workEstimateSearchDTO.getWorkEstimateStatusList())));
			}
			if (Utility.isValidString(workEstimateSearchDTO.getWorkEstimateNumber())) {
				query.where((qWorkEstimate.workEstimateNumber.eq(workEstimateSearchDTO.getWorkEstimateNumber())));
			}
			if (Utility.isValidString(workEstimateSearchDTO.getName())) {
				query.where((qWorkEstimate.name.eq(workEstimateSearchDTO.getName())));
			}
			if (Utility.isValidString(workEstimateSearchDTO.getFileNumber())) {
				query.where((qWorkEstimate.fileNumber.eq(workEstimateSearchDTO.getFileNumber())));
			}

			List<WorkEstimateStatus> workEstimateStatusList = new ArrayList<>();
			workEstimateStatusList.add(WorkEstimateStatus.ADMIN_SANCTION_APPROVED);
			workEstimateStatusList.add(WorkEstimateStatus.GRANT_ALLOCATED);
			workEstimateStatusList.add(WorkEstimateStatus.REJECTED);
			workEstimateStatusList.add(WorkEstimateStatus.SENT_FOR_ADMIN_SANCTION);
			workEstimateStatusList.add(WorkEstimateStatus.SENT_FOR_GRANT_ALLOCATION);
			workEstimateStatusList.add(WorkEstimateStatus.SENT_FOR_TECH_SANCTION);
			workEstimateStatusList.add(WorkEstimateStatus.TECH_SANCTIONED);
			workEstimateStatusList.add(WorkEstimateStatus.TENDERED);
			query.where((qWorkEstimate.status.notIn(workEstimateStatusList)));
		}
		query.orderBy(qWorkEstimate.id.desc());
		if (pageable != null) {
			query.offset(pageable.getPageNumber() * pageable.getPageSize());
			query.limit(pageable.getPageSize());
		}
		total = query.fetchCount();
		log.debug("workEstimateSearchQueryDSL count - " + total);
		List<Tuple> content = query.fetch();

		for (Tuple tuple : content) {
			WorkEstimateSearchResponseDTO resultWorkEstimateSearchResponseDTO = new WorkEstimateSearchResponseDTO();
			resultWorkEstimateSearchResponseDTO.setFileNumber(tuple.get(qWorkEstimate.fileNumber));
			resultWorkEstimateSearchResponseDTO.setWorkEstimateNumber(tuple.get(qWorkEstimate.workEstimateNumber));
			resultWorkEstimateSearchResponseDTO.setName(tuple.get(qWorkEstimate.name));
			resultWorkEstimateSearchResponseDTO.setStatus(tuple.get(qWorkEstimate.status));
			resultWorkEstimateSearchResponseDTO.setId(tuple.get(qWorkEstimate.id));
			resultWorkEstimateSearchResponseDTO.setCreatedTs(tuple.get(qWorkEstimate.createdTs));
			workEstimateSearchResponseDTOList.add(resultWorkEstimateSearchResponseDTO);
		}
		return new PageImpl<WorkEstimateSearchResponseDTO>(workEstimateSearchResponseDTOList, pageable, total);
	}

	/**
	 * Search work estimate for copy estimate.
	 *
	 * @param pageable              the pageable
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @param deptId                the dept id
	 * @param createdBy             the created by
	 * @return the page
	 */
	@Override
	public Page<WorkEstimateSearchResponseDTO> searchWorkEstimateForCopyEstimate(PageRequest pageable,
			WorkEstimateSearchDTO workEstimateSearchDTO, Long deptId, String createdBy) {
		List<WorkEstimateSearchResponseDTO> workEstimateSearchResponseDTOList = new ArrayList<>();

		long total = 0;
		QWorkEstimate qWorkEstimate = QWorkEstimate.workEstimate;
		JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);

		query.select(qWorkEstimate.id, qWorkEstimate.workEstimateNumber, qWorkEstimate.name, qWorkEstimate.status,
				qWorkEstimate.fileNumber, qWorkEstimate.createdTs, qWorkEstimate.createdBy, qWorkEstimate.estimateTotal)
				.from(qWorkEstimate);

		query.where(qWorkEstimate.deptId.eq(deptId));

		if (Utility.isValidCollection(workEstimateSearchDTO.getWorkEstimateStatusList())
				&& workEstimateSearchDTO.getWorkEstimateStatusList().contains(WorkEstimateStatus.DRAFT)) {
			query.where((qWorkEstimate.createdBy.eq(createdBy)));
		}

		if (Utility.isValidString(workEstimateSearchDTO.getWorkEstimateNumber())) {
			query.where((qWorkEstimate.workEstimateNumber.like(workEstimateSearchDTO.getWorkEstimateNumber())));
		}
		if (Utility.isValidString(workEstimateSearchDTO.getName())) {
			query.where((qWorkEstimate.name.like(workEstimateSearchDTO.getName())));
		}
		if (Utility.isValidString(workEstimateSearchDTO.getFileNumber())) {
			query.where((qWorkEstimate.fileNumber.like(workEstimateSearchDTO.getFileNumber())));
		}
		query.orderBy(qWorkEstimate.id.desc());
		if (pageable != null) {
			query.offset(pageable.getPageNumber() * pageable.getPageSize());
			query.limit(pageable.getPageSize());
		}
		total = query.fetchCount();
		log.debug("searchWorkEstimateForCopyEstimate count - " + total);
		List<Tuple> content = query.fetch();

		for (Tuple tuple : content) {
			WorkEstimateSearchResponseDTO resultWorkEstimateSearchResponseDTO = new WorkEstimateSearchResponseDTO();
			resultWorkEstimateSearchResponseDTO.setFileNumber(tuple.get(qWorkEstimate.fileNumber));
			resultWorkEstimateSearchResponseDTO.setWorkEstimateNumber(tuple.get(qWorkEstimate.workEstimateNumber));
			resultWorkEstimateSearchResponseDTO.setName(tuple.get(qWorkEstimate.name));
			resultWorkEstimateSearchResponseDTO.setEstimateTotal(tuple.get(qWorkEstimate.estimateTotal));
			resultWorkEstimateSearchResponseDTO.setStatus(tuple.get(qWorkEstimate.status));
			resultWorkEstimateSearchResponseDTO.setId(tuple.get(qWorkEstimate.id));
			resultWorkEstimateSearchResponseDTO.setCreatedTs(tuple.get(qWorkEstimate.createdTs));
			resultWorkEstimateSearchResponseDTO.setCreatedBy(tuple.get(qWorkEstimate.createdBy));
			workEstimateSearchResponseDTOList.add(resultWorkEstimateSearchResponseDTO);
		}
		return new PageImpl<WorkEstimateSearchResponseDTO>(workEstimateSearchResponseDTOList, pageable, total);
	}

}
