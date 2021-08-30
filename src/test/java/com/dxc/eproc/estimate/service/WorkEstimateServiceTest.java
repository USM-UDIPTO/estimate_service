package com.dxc.eproc.estimate.service;

import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.enumeration.WorkEstimateSearch;
import com.dxc.eproc.estimate.enumeration.WorkEstimateStatus;
import com.dxc.eproc.estimate.model.EstimateType;
import com.dxc.eproc.estimate.model.WorkCategory;
import com.dxc.eproc.estimate.model.WorkCategoryAttribute;
import com.dxc.eproc.estimate.model.WorkEstimate;
import com.dxc.eproc.estimate.model.WorkEstimateItemLBD;
import com.dxc.eproc.estimate.model.WorkType;
import com.dxc.eproc.estimate.repository.WorkEstimateRepository;
import com.dxc.eproc.estimate.repository.WorkEstimateSearchRepository;
import com.dxc.eproc.estimate.response.dto.WorkEstimateResponseDTO;
import com.dxc.eproc.estimate.response.dto.WorkEstimateSearchResponseDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateDTO;
import com.dxc.eproc.estimate.service.dto.WorkEstimateSearchDTO;
import com.dxc.eproc.estimate.service.impl.WorkEstimateServiceImpl;
import com.dxc.eproc.estimate.service.mapper.WorkEstimateMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkEstimateServiceTest.
 */
public class WorkEstimateServiceTest extends PowerMockTestCase {

	private static final Logger log = LoggerFactory.getLogger(WorkEstimateServiceTest.class);
	private static WorkEstimate workEstimate;
	private static WorkEstimateDTO workEstimateDTO;
	private static Page<WorkEstimate> workEstimatePage;
	private static Page<WorkEstimateSearchResponseDTO> workEstimateSearchDTOPage;
	private static WorkEstimateSearchDTO workEstimateSearchDTO;
	
	

	@InjectMocks
	private WorkEstimateServiceImpl workEstimateService;

	@Mock
	private SubEstimateService subEstimateService;

	@Mock
	private WorkSubEstimateGroupService workSubEstimateGroupService;

	@Mock
	private WorkEstimateRepository workEstimateRepository;

	@Mock
	private WorkEstimateMapper workEstimateMapper;
	
	@Mock
	private WorkEstimateSearchRepository workEstimateSearchRepository;

	@BeforeClass
	public void init() {

		workEstimate = new WorkEstimate();
		workEstimate.setId(1L);
		workEstimate.setWorkEstimateNumber("sd");
		workEstimate.setApprovedBudgetYn(true);
		workEstimate.setDeptId(1L);
		workEstimate.setFileNumber("KPWD/AEE/MNG/CRF/2009-10/1");
		workEstimate.setDescription("This is Road Construction-Phase-1");
		workEstimate.setName("Road Construction");
		workEstimate.locationId(1L);

		EstimateType estimateType = new EstimateType();
		estimateType.setId(1L);
		estimateType.setActiveYn(true);
		estimateType.setValueType("Administrative Approval of Original Works - Residential Buildings");
		estimateType.setEstimateTypeValue("Administrative Approval of Original Works - Residential Buildings Selected");

		WorkType workType = new WorkType();
		workType.setId(1L);
		workType.activeYn(true);
		workType.workTypeName("Road Construction");
		workType.setWorkTypeValue("Maintainence");
		workType.setValueType("Maintanience(Annual,Routine)");

		WorkCategory workCategory = new WorkCategory();
		workCategory.setId(1L);
		workCategory.setActiveYn(true);
		workCategory.setCategoryCode("BB");
		workCategory.setCategoryName("Bridge cum Barrages");

		WorkCategoryAttribute workCategoryAttribute = new WorkCategoryAttribute();
		workCategoryAttribute.setId(1L);
		workCategoryAttribute.activeYn(true);
		workCategoryAttribute.setAttributeName(" Annual repairs or Maintenance");
		workCategoryAttribute.setWorkCategoryId(workCategory.getId());
		workCategoryAttribute.setWorkTypeId(workType.getId());

		workEstimate.setEstimateTypeId(estimateType.getId());
		workEstimate.setWorkCategoryId(workCategory.getId());
		workEstimate.setWorkTypeId(workType.getId());
		workEstimate.setWorkCategoryAttribute(workCategoryAttribute.getId());
		workEstimate.setWorkCategoryAttributeValue(BigDecimal.valueOf(10000.00));
		workEstimate.provisionalAmount(BigDecimal.valueOf(1000000.00));
		workEstimate.grantAllocatedAmount(BigDecimal.valueOf(2000000.00));
		workEstimate.documentReference("2021-22");
		workEstimate.headOfAccount("SSAS");
		workEstimate.hkrdbFundedYn(true);
		workEstimate.schemeId(1L);
		workEstimate.setEstimateTotal(BigDecimal.valueOf(10.0000));
		workEstimate.setGroupLumpsumTotal(BigDecimal.valueOf(10.0000));
		workEstimate.setGroupOverheadTotal(BigDecimal.valueOf(10.0000));

		workEstimateDTO = new WorkEstimateDTO();
		BeanUtils.copyProperties(workEstimate, workEstimateDTO);
		List<WorkEstimate> workEstimateList = new ArrayList<>();
		workEstimateList.add(workEstimate);
		workEstimatePage = new PageImpl<>(workEstimateList);
		
	
		WorkEstimateSearchResponseDTO workEstimateSearchResponseDTO = new WorkEstimateSearchResponseDTO();
		workEstimateSearchResponseDTO.setFileNumber("f3321");
		workEstimateSearchResponseDTO.setId(1L);
		workEstimateSearchResponseDTO.setName("TestList");
		workEstimateSearchResponseDTO.setStatus(WorkEstimateStatus.DRAFT);
		workEstimateSearchResponseDTO.setWorkEstimateNumber("d555as");
		List<WorkEstimateSearchResponseDTO> workEstimateSearchDTOList = new ArrayList<>();
		workEstimateSearchDTOList.add(workEstimateSearchResponseDTO);
		workEstimateSearchDTOPage= new PageImpl<>(workEstimateSearchDTOList);

	}

	@BeforeMethod
	public void commonMocking() {
		PowerMockito.when(workEstimateMapper.toEntity(workEstimateDTO)).thenReturn(workEstimate);
		PowerMockito.when(workEstimateMapper.toDto(workEstimate)).thenReturn(workEstimateDTO);

	}

	@Test
	public void saveTest() throws Exception {

		PowerMockito.when(workEstimateRepository.findById(workEstimateDTO.getId()))
				.thenReturn(Optional.of(workEstimate));

		PowerMockito.when(workEstimateRepository.save(workEstimate)).thenReturn(workEstimate);

		WorkEstimateDTO result = workEstimateService.save(workEstimateDTO);
		Assert.assertNotNull(result.getId());
		log.info("saveTest successful!");
	}

	@Test
	public void saveNullTest() throws Exception {

		WorkEstimateDTO workEstimateDTO2 = new WorkEstimateDTO();
		BeanUtils.copyProperties(workEstimateDTO, workEstimateDTO2);
		workEstimateDTO2.setId(null);
		PowerMockito.when(workEstimateMapper.toEntity(workEstimateDTO2)).thenReturn(workEstimate);
		PowerMockito.when(workEstimateRepository.save(workEstimate)).thenReturn(workEstimate);

		WorkEstimateDTO result = workEstimateService.save(workEstimateDTO2);
		Assert.assertNotNull(result);
		log.info("saveTest successful!");
	}

	@Test
	public void partialUpdateTest() throws Exception {
		PowerMockito.when(workEstimateRepository.save(workEstimate)).thenReturn(workEstimate);

		PowerMockito.when(workEstimateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(workEstimate));

		Optional<WorkEstimateDTO> result = workEstimateService.partialUpdate(workEstimateDTO);
		Assert.assertTrue(result.isPresent());
		log.info("partialUpdateTest successful!");
	}

	@Test
	public void findAllTest() throws Exception {
		PowerMockito.when(workEstimateRepository.findAll(PageRequest.of(0, 5))).thenReturn(workEstimatePage);

		Page<WorkEstimateDTO> result = workEstimateService.findAll(PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findAllTest successful!");
	}

	@Test
	public void findOneTest() throws Exception {
		PowerMockito.when(workEstimateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(workEstimate));

		Optional<WorkEstimateDTO> result = workEstimateService.findOne(Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findOneTest successful!");
	}

	@Test
	public void findAllByIdTest() throws Exception {
		PowerMockito.when(workEstimateRepository.findAllById(1L, PageRequest.of(0, 5))).thenReturn(workEstimatePage);

		Page<WorkEstimateDTO> result = workEstimateService.findAllById(1L, PageRequest.of(0, 5));
		Assert.assertFalse(result.isEmpty());
		log.info("findAllByIdTest successful!");
	}

	@Test
	public void findByDeptIdAndFileNumberTest() throws Exception {
		PowerMockito.when(workEstimateRepository.findByDeptIdAndFileNumber(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(Optional.of(workEstimate));

		Optional<WorkEstimateDTO> result = workEstimateService.findByDeptIdAndFileNumber(Mockito.anyLong(),
				Mockito.anyString());
		Assert.assertTrue(result.isPresent());
		log.info("findByDeptIdAndFileNumberTest successful!");
	}

	@Test
	public void findByDeptIdAndFileNumberAndIdNotTest() throws Exception {
		PowerMockito.when(workEstimateRepository.findByDeptIdAndFileNumberAndIdNot(Mockito.anyLong(),
				Mockito.anyString(), Mockito.anyLong())).thenReturn(Optional.of(workEstimate));

		Optional<WorkEstimateDTO> result = workEstimateService.findByDeptIdAndFileNumberAndIdNot(Mockito.anyLong(),
				Mockito.anyString(), Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findByDeptIdAndFileNumberAndIdNotTest successful!");
	}

	@Test
	public void findBySchemeIdTest() throws Exception {
		PowerMockito.when(workEstimateRepository.findBySchemeId(Mockito.anyLong()))
				.thenReturn(Optional.of(workEstimate));

		Optional<WorkEstimateDTO> result = workEstimateService.findBySchemeId(Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findBySchemeIdTest successful!");
	}

	@Test
	public void findBySchemeIdAndIdNotTest() throws Exception {
		PowerMockito.when(workEstimateRepository.findBySchemeIdAndIdNot(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.of(workEstimate));

		Optional<WorkEstimateDTO> result = workEstimateService.findBySchemeIdAndIdNot(Mockito.anyLong(),
				Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findBySchemeIdAndIdNotTest successful!");
	}

	@Test
	public void getEstimateTotals_SuccessTest() {

		PowerMockito.when(subEstimateService.sumOfEstimateTotalByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(BigDecimal.ONE);
		PowerMockito.when(workSubEstimateGroupService.sumLumpsumTotalByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(BigDecimal.ONE);
		PowerMockito.when(workSubEstimateGroupService.sumOverheadTotalByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(BigDecimal.ONE);

		WorkEstimateResponseDTO workEstimateResponseDTO = workEstimateService.getEstimateTotals(1L);
		Assert.assertNotEquals(workEstimateResponseDTO.getLumpsumTotal(), BigDecimal.ZERO);
	}

	@Test
	public void calculateEstimateTotals() throws Exception {

		PowerMockito.when(subEstimateService.sumOfEstimateTotalByWorkEstimateId(Mockito.anyLong()))
				.thenReturn(BigDecimal.ONE);

		BigDecimal calculateEstimateTotals = workEstimateService.calculateEstimateTotals(1L);
		Assert.assertNotEquals(calculateEstimateTotals, BigDecimal.ZERO);
	}
	@Test
	public void searchWorkEstimateByQueryDSL() throws Exception {
		
		String createdBy ="TestCreate";

		List<WorkEstimateStatus> statusList = new ArrayList<>();
		statusList.add(WorkEstimateStatus.DRAFT);
		statusList.add(WorkEstimateStatus.GRANT_ALLOCATED);
		
		WorkEstimateSearchDTO workEstimateSearchDTO = new WorkEstimateSearchDTO();
		workEstimateSearchDTO.setFileNumber("TestFile");
		workEstimateSearchDTO.setName("WorkEstimate");
		workEstimateSearchDTO.setWorkEstimateNumber("2a11");
		workEstimateSearchDTO.setWorkEstimateStatusList(statusList);
		
		
		PowerMockito.when(workEstimateSearchRepository.searchWorkEstimateQueryDSL(PageRequest.of(0, 5), workEstimateSearchDTO, createdBy, WorkEstimateSearch.RECENT_ESTIMATES)).thenReturn(workEstimateSearchDTOPage);
		
		Page<WorkEstimateSearchResponseDTO> result = workEstimateService.searchWorkEstimateByQueryDSL(PageRequest.of(0, 5), workEstimateSearchDTO, createdBy, WorkEstimateSearch.RECENT_ESTIMATES);
		
		assertNotNull(result);
	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public void tearDown() {
		log.info("==========================================================================");
		log.info("This is executed after once Per Test Class - WorkEstimateServiceTest: tearDown");
	}

}
