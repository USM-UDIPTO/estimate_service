package com.dxc.eproc.estimate.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.estimate.enumeration.WorkEstimateSearch;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateSearchRepository;
import com.dxc.eproc.estimate.response.dto.WorkEstimateResponseDTO;
import com.dxc.eproc.estimate.response.dto.WorkEstimateSearchResponseDTO;
import com.dxc.eproc.estimate.service.SubEstimateService;
import com.dxc.eproc.estimate.service.WorkEstimateService;
import com.dxc.eproc.estimate.service.WorkSubEstimateGroupService;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateSearchDTO;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing {@link WorkEstimate}.
 */
@Service
@Transactional
public class WorkEstimateServiceImpl implements WorkEstimateService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(WorkEstimateServiceImpl.class);

	/** The work estimate repository. */
	@Autowired
	private WorkEstimateRepository workEstimateRepository;

	/** The work estimate mapper. */
	@Autowired
	private WorkEstimateMapper workEstimateMapper;

	/** The sub estimate service. */
	@Autowired
	private SubEstimateService subEstimateService;

	/** The work sub estimate group service. */
	@Autowired
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	/** The work estimate search repository. */
	@Autowired
	private WorkEstimateSearchRepository workEstimateSearchRepository;

	/**
	 * Save.
	 *
	 * @param workEstimateDTO the work estimate DTO
	 * @return the work estimate DTO
	 */
	@Override
	public WorkEstimateDTO save(WorkEstimateDTO workEstimateDTO) {
		log.debug("Request to save WorkEstimate : {}", workEstimateDTO);

		WorkEstimate workEstimate = null;
		if (workEstimateDTO.getId() == null) {
			log.debug("Request to save WorkEstimate");
			workEstimate = workEstimateMapper.toEntity(workEstimateDTO);
		} else {
			log.debug("Request to update WorkEstimate - Id : {}", workEstimateDTO.getId());

			Optional<WorkEstimate> workEstimateOptional = workEstimateRepository.findById(workEstimateDTO.getId());
			if (workEstimateOptional.isPresent()) {
				workEstimate = workEstimateOptional.get();
				workEstimate.fileNumber(workEstimateDTO.getFileNumber()).name(workEstimateDTO.getName())
						.description(workEstimateDTO.getDescription())
						.estimateTypeId(workEstimateDTO.getEstimateTypeId()).workTypeId(workEstimateDTO.getWorkTypeId())
						.workCategoryId(workEstimateDTO.getWorkCategoryId())
						.workCategoryAttribute(workEstimateDTO.getWorkCategoryAttribute())
						.workCategoryAttributeValue(workEstimateDTO.getWorkCategoryAttributeValue())
						.hkrdbFundedYn(workEstimateDTO.getHkrdbFundedYn())
						.grantAllocatedAmount(workEstimateDTO.getGrantAllocatedAmount())
						.provisionalAmount(workEstimateDTO.getProvisionalAmount())
						.headOfAccount(workEstimateDTO.getHeadOfAccount()).schemeId(workEstimateDTO.getSchemeId())
						.approvedBudgetYn(workEstimateDTO.getApprovedBudgetYn())
						.documentReference(workEstimateDTO.getDocumentReference());
			}
		}
		workEstimate = workEstimateRepository.save(workEstimate);
		return workEstimateMapper.toDto(workEstimate);

	}

	/**
	 * Partial update.
	 *
	 * @param workEstimateDTO the work estimate DTO
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateDTO> partialUpdate(WorkEstimateDTO workEstimateDTO) {
		log.debug("Request to partially update WorkEstimate : {}", workEstimateDTO);

		return workEstimateRepository.findById(workEstimateDTO.getId()).map(existingWorkEstimate -> {
			workEstimateMapper.partialUpdate(existingWorkEstimate, workEstimateDTO);
			return existingWorkEstimate;
		}).map(workEstimateRepository::save).map(workEstimateMapper::toDto);
	}

	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateDTO> findAll(Pageable pageable) {
		log.debug("Request to get all WorkEstimates");
		return workEstimateRepository.findAll(pageable).map(workEstimateMapper::toDto);
	}

	/**
	 * Find all by id.
	 *
	 * @param id       the id
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<WorkEstimateDTO> findAllById(Long id, Pageable pageable) {
		log.debug("Request to get all By Id WorkEstimates");
		return workEstimateRepository.findAllById(id, pageable).map(workEstimateMapper::toDto);
	}

	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<WorkEstimateDTO> findOne(Long id) {
		log.debug("Request to get WorkEstimate : {}", id);
		return workEstimateRepository.findById(id).map(workEstimateMapper::toDto);
	}

	/**
	 * Find by dept id and file number.
	 *
	 * @param deptId     the dept id
	 * @param fileNumber the file number
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateDTO> findByDeptIdAndFileNumber(Long deptId, String fileNumber) {

		log.debug("Service: Request to check fileNumber for the Department : {}", fileNumber);
		return workEstimateRepository.findByDeptIdAndFileNumber(deptId, fileNumber).map(workEstimateMapper::toDto);
	}

	/**
	 * Find by dept id and file number and id not.
	 *
	 * @param deptId     the dept id
	 * @param fileNumber the file number
	 * @param id         the id
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateDTO> findByDeptIdAndFileNumberAndIdNot(Long deptId, String fileNumber, Long id) {

		log.debug("Service: Request to check fileNumber for the Department for update : {}", fileNumber);
		return workEstimateRepository.findByDeptIdAndFileNumberAndIdNot(deptId, fileNumber, id)
				.map(workEstimateMapper::toDto);
	}

	/**
	 * Find by scheme id.
	 *
	 * @param schemeId the scheme id
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateDTO> findBySchemeId(Long schemeId) {

		log.debug("Service: Request to check schemeId for the workEstimate : {}", schemeId);
		return workEstimateRepository.findBySchemeId(schemeId).map(workEstimateMapper::toDto);
	}

	/**
	 * Find by scheme id and id not.
	 *
	 * @param schemeId the scheme id
	 * @param id       the id
	 * @return the optional
	 */
	@Override
	public Optional<WorkEstimateDTO> findBySchemeIdAndIdNot(Long schemeId, Long id) {
		log.debug("Service: Request to check schemeId for the workEstimate : {}", schemeId);
		return workEstimateRepository.findBySchemeIdAndIdNot(schemeId, id).map(workEstimateMapper::toDto);
	}

	/**
	 * Modify estimate total.
	 *
	 * @param id            the id
	 * @param estimateTotal the estimate total
	 */
	@Override
	public void modifyEstimateTotal(Long id, BigDecimal estimateTotal) {
		WorkEstimateDTO workEstimateDTO = new WorkEstimateDTO();
		workEstimateDTO.setId(id);
		workEstimateDTO.setEstimateTotal(estimateTotal);
		partialUpdate(workEstimateDTO);
	}

	/**
	 * Gets the estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the estimate totals
	 */
	@Override
	public WorkEstimateResponseDTO getEstimateTotals(Long workEstimateId) {
		WorkEstimateResponseDTO workEstimateResponseDTO = new WorkEstimateResponseDTO();
		BigDecimal subEstimateTotal = subEstimateService.sumOfEstimateTotalByWorkEstimateId(workEstimateId);
		BigDecimal lumpsumTotal = workSubEstimateGroupService.sumLumpsumTotalByWorkEstimateId(workEstimateId);
		BigDecimal overheadTotal = workSubEstimateGroupService.sumOverheadTotalByWorkEstimateId(workEstimateId);
		BigDecimal workEstimateECV = new BigDecimal(0);
		if (subEstimateTotal != null) {
			workEstimateECV = workEstimateECV.add(subEstimateTotal);
		}
		if (lumpsumTotal != null) {
			workEstimateECV = workEstimateECV.add(lumpsumTotal);
		}
		if (overheadTotal != null) {
			workEstimateECV = workEstimateECV.add(overheadTotal);
		}
		workEstimateResponseDTO.setWorkEstimateId(workEstimateId);
		workEstimateResponseDTO.setSubEstimateTotal(subEstimateTotal == null ? BigDecimal.ZERO : subEstimateTotal);
		workEstimateResponseDTO.setLumpsumTotal(lumpsumTotal == null ? BigDecimal.ZERO : lumpsumTotal);
		workEstimateResponseDTO.setOverheadTotal(overheadTotal == null ? BigDecimal.ZERO : overheadTotal);
		workEstimateResponseDTO.setWorkEstimateECV(workEstimateECV == null ? BigDecimal.ZERO : workEstimateECV);
		return workEstimateResponseDTO;
	}

	/**
	 * Calculate estimate totals.
	 *
	 * @param workEstimateId the work estimate id
	 * @return the big decimal
	 */
	@Override
	public BigDecimal calculateEstimateTotals(Long workEstimateId) {
		BigDecimal subEstimateTotal = subEstimateService.sumOfEstimateTotalByWorkEstimateId(workEstimateId);
		// BigDecimal lumpsumTotal =
		// workSubEstimateGroupService.sumLumpsumTotalByWorkEstimateId(workEstimateId);
		// BigDecimal overheadTotal =
		// workSubEstimateGroupService.sumOverheadTotalByWorkEstimateId(workEstimateId);
		BigDecimal workEstimateTotal = new BigDecimal(0);
		workEstimateTotal = workEstimateTotal.add(subEstimateTotal);
		// workEstimateTotal = workEstimateTotal.add(lumpsumTotal);
		// workEstimateTotal = workEstimateTotal.add(overheadTotal);
		modifyEstimateTotal(workEstimateId, workEstimateTotal);
		return workEstimateTotal;
	}

	/**
	 * Search work estimate by query DSL.
	 *
	 * @param pageable              the pageable
	 * @param workEstimateSearchDTO the work estimate search DTO
	 * @param createdBy             the created by
	 * @param workEstimateSearch    the work estimate search
	 * @return the page
	 * @throws Exception the exception
	 */
	@Override
	public Page<WorkEstimateSearchResponseDTO> searchWorkEstimateByQueryDSL(Pageable pageable,
			WorkEstimateSearchDTO workEstimateSearchDTO, String createdBy, WorkEstimateSearch workEstimateSearch)
			throws Exception {
		Page<WorkEstimateSearchResponseDTO> workEstimateSearchResponseDTOPage = null;
		workEstimateSearchResponseDTOPage = workEstimateSearchRepository.searchWorkEstimateQueryDSL(pageable,
				workEstimateSearchDTO, createdBy, workEstimateSearch);
		return workEstimateSearchResponseDTOPage;
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
		Page<WorkEstimateSearchResponseDTO> workEstimateSearchResponseDTOPage = null;
		workEstimateSearchResponseDTOPage = workEstimateSearchRepository.searchWorkEstimateForCopyEstimate(pageable,
				workEstimateSearchDTO, deptId, createdBy);
		return workEstimateSearchResponseDTOPage;
	}

}
