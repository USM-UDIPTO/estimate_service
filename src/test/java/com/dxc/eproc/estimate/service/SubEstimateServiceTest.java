package com.dxc.eproc.estimate.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.estimate.model.SubEstimate;
import com.dxc.eproc.estimate.repository.SubEstimateRepository;
import com.dxc.eproc.estimate.response.dto.SubEstimateResponseDTO;
import com.dxc.eproc.estimate.service.dto.SubEstimateDTO;
import com.dxc.eproc.estimate.service.impl.SubEstimateServiceImpl;
import com.dxc.eproc.estimate.service.mapper.SubEstimateMapperImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SubEstimateServiceTest.
 */
public class SubEstimateServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SubEstimateServiceTest.class);

	/** The sub estimate. */
	private static SubEstimate subEstimate;

	/** The sub estimate DTO. */
	private static SubEstimateDTO subEstimateDTO;

	/** The sub estimate service. */
	@InjectMocks
	private SubEstimateServiceImpl subEstimateService;

	/** The work estimate item service. */
	@Mock
	private WorkEstimateItemService workEstimateItemService;

	/** The work estimate service. */
	@Mock
	private WorkEstimateService workEstimateService;

	/** The sub estimate mapper. */
	@Mock
	private SubEstimateMapperImpl subEstimateMapper;

	/** The sub estimate repository. */
	@Mock
	private SubEstimateRepository subEstimateRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - SubEstimateServiceTest: init");

		subEstimate = new SubEstimate();
		subEstimate.setAreaWeightageCircle(1L);
		subEstimate.setAreaWeightageDescription("Description");
		subEstimate.setAreaWeightageId(1L);
		subEstimate.setCompletedYn(true);
		subEstimate.setEstimateTotal(BigDecimal.ONE);
		subEstimate.setId(1L);
		subEstimate.setSorWorCategoryId(1L);
		subEstimate.setSubEstimateName("test Name");
		subEstimate.setWorkEstimateId(1L);
		subEstimate.setWorkSubEstimateGroupId(1L);

		subEstimateDTO = new SubEstimateDTO();
		BeanUtils.copyProperties(subEstimate, subEstimateDTO);
		List<SubEstimate> subEstimateList = new ArrayList<>();
		subEstimateList.add(subEstimate);

	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - SubEstimateServiceTest: setUp");

		PowerMockito.when(subEstimateMapper.toEntity(subEstimateDTO)).thenReturn(subEstimate);
		PowerMockito.when(subEstimateMapper.toDto(subEstimate)).thenReturn(subEstimateDTO);

		// subEstimateService = new SubEstimateServiceImpl();
	}

	/**
	 * Save test.
	 */
	@Test
	public void saveTest() {
		log.info("==========================================================================");
		log.info("Test - saveTest - Start");

		PowerMockito.when(subEstimateRepository.findById(subEstimateDTO.getId())).thenReturn(Optional.of(subEstimate));

		PowerMockito.when(subEstimateRepository.save(subEstimate)).thenReturn(subEstimate);

		SubEstimateDTO result = subEstimateService.save(subEstimateDTO);
		Assert.assertNotNull(result.getId());
		log.info("saveTest successful!");

		log.info("Test - saveTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Creates the sub estimate.
	 *
	 * @return the sub estimate
	 */
	private SubEstimate createSubEstimate() {

		SubEstimate subEstimate = new SubEstimate().areaWeightageCircle(1L)
				.areaWeightageDescription("Area Weightage Description").areaWeightageId(1L).completedYn(true)
				.estimateTotal(BigDecimal.valueOf(12.0000)).sorWorCategoryId(1L).subEstimateName("Subestimate Name")
				.workEstimateId(1L);

		return subEstimate;
	}

	/**
	 * Save existing sub estimate test.
	 */
	@Test
	public void saveExistingSubEstimateTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingSubEstimateTest - Start");

		SubEstimateDTO subEstimateDTO2 = new SubEstimateDTO();
		BeanUtils.copyProperties(subEstimateDTO, subEstimateDTO2);
		subEstimateDTO2.setId(null);
		PowerMockito.when(subEstimateMapper.toEntity(subEstimateDTO2)).thenReturn(subEstimate);
		PowerMockito.when(subEstimateRepository.save(subEstimate)).thenReturn(subEstimate);

		SubEstimateDTO result = subEstimateService.save(subEstimateDTO2);
		Assert.assertNotNull(result);
		log.info("saveTest successful!");
		log.info("Test - saveExistingSubEstimateTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Partial update test.
	 */
	@Test
	public void partialUpdateTest() {
		log.info("==========================================================================");
		log.info("Test - saveExistingSubEstimateTest - Start");

		PowerMockito.when(subEstimateRepository.save(subEstimate)).thenReturn(subEstimate);

		PowerMockito.when(subEstimateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(subEstimate));

		Optional<SubEstimateDTO> result = subEstimateService.partialUpdate(subEstimateDTO);
		Assert.assertTrue(result.isPresent());
		log.info("partialUpdateTest successful!");
		log.info("Test - partialUpdateTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all test.
	 */
	@Test
	public void findAllTest() {
		log.info("==========================================================================");
		log.info("Test - findAllTest - Start");

		Page<SubEstimateDTO> responseDTO = null;

		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<SubEstimate> subEstimateList = createSubEstimateList();
		Page<SubEstimate> directSupplierInfoPage = new PageImpl<>(subEstimateList);

		PowerMockito.when(subEstimateRepository.findAll(pageable)).thenReturn(directSupplierInfoPage);

		responseDTO = subEstimateService.findAll(pageable);
		log.info("Response data:findAllTest: " + responseDTO);

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all by work estimate id test.
	 */
	@Test
	public void findAllByWorkEstimateIdTest() {
		log.info("==========================================================================");
		log.info("Test - findAllByWorkEstimateIdTest - Start");

		Page<SubEstimateDTO> responseDTO = null;

		final Long WORKESTIMATEID = 1L;
		final int PAGEINDEX = 0;
		final int PAGESIZE = 5;

		Pageable pageable = PageRequest.of(PAGEINDEX, PAGESIZE);

		List<SubEstimate> subEstimateList = createSubEstimateList();
		Page<SubEstimate> directSupplierInfoPage = new PageImpl<>(subEstimateList);

		PowerMockito.when(subEstimateRepository.findAllByWorkEstimateId(WORKESTIMATEID, pageable))
		.thenReturn(directSupplierInfoPage);

		responseDTO = subEstimateService.findAllByWorkEstimateId(WORKESTIMATEID, pageable);
		log.info("Response data:findAllByWorkEstimateIdTest: " + responseDTO);

		log.info("Test - findAllByWorkEstimateIdTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find all by work estimate id list test.
	 */
	@Test
	public void findAllByWorkEstimateIdListTest() {
		log.info("==========================================================================");
		log.info("Test - findAllByWorkEstimateIdListTest - Start");

		List<SubEstimateDTO> responseDTO = null;

		final Long WORKESTIMATEID = 1L;

		List<SubEstimate> subEstimateList = createSubEstimateList();

		PowerMockito.when(subEstimateRepository.findAllByWorkEstimateId(WORKESTIMATEID)).thenReturn(subEstimateList);

		responseDTO = subEstimateService.findAllByWorkEstimateId(WORKESTIMATEID);
		log.info("Response data:findAllByWorkEstimateIdListTest: " + responseDTO);

		log.info("Test - findAllByWorkEstimateIdListTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Creates the sub estimate list.
	 *
	 * @return the list
	 */
	private List<SubEstimate> createSubEstimateList() {
		List<SubEstimate> subEstimateList = new ArrayList<>();

		SubEstimate firstSubEstimate = new SubEstimate().areaWeightageCircle(1L)
				.areaWeightageDescription("Area Weightage Description").areaWeightageId(1L).completedYn(true)
				.estimateTotal(BigDecimal.valueOf(12.0000)).sorWorCategoryId(1L).subEstimateName("Subestimate Name")
				.workEstimateId(1L);

		SubEstimate secondSubEstimate = new SubEstimate().areaWeightageCircle(1L)
				.areaWeightageDescription("Area Weightage Description").areaWeightageId(1L).completedYn(true)
				.estimateTotal(BigDecimal.valueOf(12.0000)).sorWorCategoryId(1L).subEstimateName("Subestimate Name")
				.workEstimateId(1L);

		subEstimateList.add(firstSubEstimate);
		subEstimateList.add(secondSubEstimate);

		return subEstimateList;
	}

	/**
	 * Find one test.
	 */
	@Test
	public void findOneTest() {
		log.info("==========================================================================");
		log.info("Test - findOneTest - Start");

		PowerMockito.when(subEstimateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(subEstimate));

		Optional<SubEstimateDTO> result = subEstimateService.findOne(Mockito.anyLong());
		Assert.assertTrue(result.isPresent());
		log.info("findOneTest successful!");

		log.info("Test - findAllTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Delete test.
	 */
	@Test
	public void deleteTest() {
		log.info("==========================================================================");
		log.info("Test - deleteTest - Start");

		final Long ID = 1L;

		PowerMockito.doNothing().when(subEstimateRepository).deleteById(ID);

		subEstimateService.delete(ID);
		log.info("Response: deleteTest has been passed!");

		log.info("Test - deleteTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find by work estimate id and ids test.
	 */
	@Test
	public void findByWorkEstimateIdAndIdsTest() {
		log.info("==========================================================================");
		log.info("Test - findByWorkEstimateIdAndIdsTest - Start");

		final Long WORKESTIMATEID = 1L;
		final Long SUBESTIMATEID1 = 1L;
		final Long SUBESTIMATEID2 = 2L;

		List<SubEstimateDTO> subEstimateDTOList = null;
		SubEstimate subEstimate1 = createSubEstimate();
		subEstimate1.setId(SUBESTIMATEID1);
		SubEstimate subEstimate2 = createSubEstimate();
		subEstimate2.setId(SUBESTIMATEID2);

		List<SubEstimate> subEstimateList = createSubEstimateList();
		subEstimateList.add(subEstimate1);
		subEstimateList.add(subEstimate2);
		List<Long> subEstimateIds = new ArrayList<>();
		subEstimateIds.add(SUBESTIMATEID1);
		subEstimateIds.add(SUBESTIMATEID2);

		PowerMockito.when(subEstimateRepository.findByWorkEstimateIdAndIdIn(WORKESTIMATEID, subEstimateIds))
		.thenReturn(subEstimateList);

		subEstimateDTOList = subEstimateService.findByWorkEstimateIdAndIds(WORKESTIMATEID, subEstimateIds);
		log.info("Response data:findBySubEstimateIdAndIdTest: " + subEstimateDTOList);

		log.info("Test - findByWorkEstimateIdAndIdsTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find by work estimate id and work sub estimate group id test.
	 */
	@Test
	public void findByWorkEstimateIdAndWorkSubEstimateGroupIdTest() {
		log.info("==========================================================================");
		log.info("Test - findByWorkEstimateIdAndWorkSubEstimateGroupIdTest - Start");

		final Long WORKESTIMATEID = 1L;
		final Long WORKSUBESTIMATEGROUPID = 1L;

		List<SubEstimateDTO> subEstimateDTOList = null;
		List<SubEstimate> subEstimateList = createSubEstimateList();

		PowerMockito.when(subEstimateRepository.findByWorkEstimateIdAndWorkSubEstimateGroupId(WORKSUBESTIMATEGROUPID,
				WORKSUBESTIMATEGROUPID)).thenReturn(subEstimateList);

		subEstimateDTOList = subEstimateService.findByWorkEstimateIdAndWorkSubEstimateGroupId(WORKESTIMATEID,
				WORKSUBESTIMATEGROUPID);
		log.info("Response data:findByWorkEstimateIdAndWorkSubEstimateGroupIdTest: " + subEstimateDTOList);

		log.info("Test - findByWorkEstimateIdAndIdsTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Find by work estimate id and id test.
	 */
	@Test
	public void findByWorkEstimateIdAndIdTest() {
		log.info("==========================================================================");
		log.info("Test - findByWorkEstimateIdAndIdTest - Start");

		final Long WORKESTIMATEID = 1L;
		final Long ID = 1L;

		Optional<SubEstimateDTO> subEstimateOpt = null;

		SubEstimate subEstimate = createSubEstimate();
		subEstimate.setId(ID);

		PowerMockito.when(subEstimateRepository.findByWorkEstimateIdAndId(WORKESTIMATEID, ID))
		.thenReturn(Optional.of(subEstimate));

		subEstimateOpt = subEstimateService.findByWorkEstimateIdAndId(WORKESTIMATEID, ID);
		log.info("Response data:findByWorkEstimateIdAndIdTest: " + subEstimateOpt);

		log.info("Test - findByWorkEstimateIdAndIdTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Sum estimate total by work estimate id and ids.
	 */
	@Test(priority = 10)
	public void sumEstimateTotalByWorkEstimateIdAndIds() {
		log.info("==========================================================================");
		log.info("Test - sumEstimateTotalByWorkEstimateIdAndIds - Start");

		final Long WORKESTIMATEID = 1L;
		final Long SUBESTIMATEID1 = 1L;
		final Long SUBESTIMATEID2 = 2L;

		BigDecimal estimateTotal = BigDecimal.valueOf(1.0000);
		BigDecimal estimateTotalResponse = null;
		SubEstimate subEstimate1 = createSubEstimate();
		subEstimate1.setId(SUBESTIMATEID1);
		SubEstimate subEstimate2 = createSubEstimate();
		subEstimate2.setId(SUBESTIMATEID2);

		List<SubEstimate> subEstimateList = createSubEstimateList();
		subEstimateList.add(subEstimate1);
		subEstimateList.add(subEstimate2);

		List<Long> subEstimateIds = new ArrayList<>();
		subEstimateIds.add(SUBESTIMATEID1);
		subEstimateIds.add(SUBESTIMATEID2);

		PowerMockito.when(subEstimateRepository.sumEstimateTotalByWorkEstimateIdAndIdIn(WORKESTIMATEID, subEstimateIds))
		.thenReturn((estimateTotal));

		estimateTotalResponse = subEstimateService.sumEstimateTotalByWorkEstimateIdAndIds(WORKESTIMATEID,
				subEstimateIds);
		log.info("Response data:sumEstimateTotalByWorkEstimateIdAndIds: " + estimateTotalResponse);

		Assert.assertEquals(estimateTotalResponse, estimateTotal);

		log.info("Test - findByWorkEstimateIdAndIdTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Clear group id test.
	 */
	@Test
	public void clearGroupIdTest() {
		log.info("==========================================================================");
		log.info("Test - clearGroupIdTest - Start");

		final Long ID = 1L;

		SubEstimate subEstimate = createSubEstimate();
		subEstimate.setWorkSubEstimateGroupId(null);

		PowerMockito.when(subEstimateRepository.findById(ID)).thenReturn(Optional.of(subEstimate));

		subEstimateService.clearGroupId(ID);
		log.info("Response: deleteTest has been passed!");

		log.info("Test - deleteTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Sum of estimate total by work estimate id test.
	 */
	@Test
	public void sumOfEstimateTotalByWorkEstimateIdTest() {
		log.info("==========================================================================");
		log.info("Test - sumEstimateTotalByWorkEstimateIdAndIds - Start");

		final Long WORKESTIMATEID = 1L;

		BigDecimal estimateTotal = BigDecimal.valueOf(1.0000);
		BigDecimal estimateTotalResponse = null;

		PowerMockito.when(subEstimateRepository.sumOfEstimateTotalByWorkEstimateId(WORKESTIMATEID))
		.thenReturn((estimateTotal));

		estimateTotalResponse = subEstimateService.sumOfEstimateTotalByWorkEstimateId(WORKESTIMATEID);
		log.info("Response sumOfEstimateTotalByWorkEstimateIdTest: " + estimateTotalResponse);

		Assert.assertEquals(estimateTotalResponse, estimateTotal);

		log.info("Test - findByWorkEstimateIdAndIdTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Gets the sub estimate totals test.
	 *
	 * @return the sub estimate totals test
	 */
	@Test
	public void getSubEstimateTotalsTest() {
		log.info("==========================================================================");
		log.info("Test - findByWorkEstimateIdAndIdTest - Start");

		final Long WORKESTIMATEID = 1L;
		final Long SUBESTIMATEID = 1L;

		SubEstimateResponseDTO subEstimateDTO = null;

		BigDecimal subEstimateTotal = BigDecimal.valueOf(1.0000);
		BigDecimal subEstimateSORTotal = BigDecimal.valueOf(1.0000);
		BigDecimal subEstimateNonSORTotal = BigDecimal.valueOf(1.0000);

		PowerMockito.when(workEstimateItemService.sumOfWorkEstimateItemTotal(SUBESTIMATEID))
		.thenReturn(subEstimateTotal);

		PowerMockito.when(workEstimateItemService.sumOfSORWorkEstimateItemTotal(SUBESTIMATEID))
		.thenReturn(subEstimateSORTotal);

		PowerMockito.when(workEstimateItemService.sumOfNonSORWorkEstimateItemTotal(SUBESTIMATEID))
		.thenReturn(subEstimateNonSORTotal);

		subEstimateDTO = subEstimateService.getSubEstimateTotals(WORKESTIMATEID, SUBESTIMATEID);
		log.info("Response data:findByWorkEstimateIdAndIdTest: " + subEstimateDTO);

		log.info("Test - findByWorkEstimateIdAndIdTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Calculate sub estimate totals test.
	 */
	@Test
	public void calculateSubEstimateTotalsTest() {
		log.info("==========================================================================");
		log.info("Test - calculateSubEstimateTotalsTest - Start");

		SubEstimate subEstimate = createSubEstimate();

		PowerMockito.when(workEstimateItemService.sumOfWorkEstimateItemTotal(Mockito.anyLong()))
		.thenReturn(BigDecimal.ONE);
		PowerMockito.when(workEstimateItemService.sumOfSORWorkEstimateItemTotal(Mockito.anyLong()))
		.thenReturn(BigDecimal.ONE);
		PowerMockito.when(workEstimateItemService.sumOfNonSORWorkEstimateItemTotal(Mockito.anyLong()))
		.thenReturn(BigDecimal.ONE);
		PowerMockito.when(workEstimateService.calculateEstimateTotals(Mockito.anyLong())).thenReturn(BigDecimal.TEN);
		PowerMockito.when(subEstimateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(subEstimate));
		PowerMockito.when(subEstimateRepository.save(subEstimate)).thenReturn(subEstimate);

		SubEstimateResponseDTO subEstimateResponseDTO = subEstimateService.calculateSubEstimateTotals(1L, 1L);
		Assert.assertEquals(subEstimateResponseDTO.getWorkEstimateTotal(), BigDecimal.TEN);

		log.info("Test - calculateSubEstimateTotalsTest - End");
		log.info("==========================================================================");
	}

	/**
	 * Tear down.
	 */
	@AfterClass
	public static void tearDown() {
		log.info("==================================================================================");
		log.info("This is executed after once Per Test Class - SubEstimateServiceTest");
	}

}
